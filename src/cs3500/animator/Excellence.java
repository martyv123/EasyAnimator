package cs3500.animator;

import cs3500.animator.controller.classes.ControllerImpl;
import cs3500.animator.controller.interfaces.Controller;
import cs3500.animator.model.classes.EasyAnimatorModelImpl;
import cs3500.animator.model.interfaces.EasyAnimatorModel;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.classes.EditorViewImpl;
import cs3500.animator.view.classes.ViewFactory;
import cs3500.animator.view.interfaces.EditorView;
import cs3500.animator.view.interfaces.View;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Entry into our animator.
 */
public final class Excellence {

  /**
   * main function that parses commands from the command line, decides which file
   * to read from and generates a model representing the data, parses the inputs
   * for the speed of the animation, the output of the destination and how the
   * animations should be represented: text, svg or visual. default output is System.out.
   * @param args <-in name-of-animation-file>
   *             <-view type-of-view>
   *             <-out where-output-show-go>
   *             <-speed integer-ticks-per-second>
   * @throws IOException Appendable used in outputting file is null.
   */
  public static void main(String[] args) throws IOException {
    Readable file = null;
    String viewType = null;
    String outDest = "System.out";
    Appendable output = System.out;
    double speed = 1;
    int counter = 0;
    JFrame popup = new JFrame();
    popup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    while (counter < args.length) {
      try {
        switch (args[counter]) {
          case "-in":
            file = new FileReader(args[counter + 1]);
            break;
          case "-view":
            viewType = args[counter + 1];
            break;
          case "-out":
            outDest = args[counter + 1];
            break;
          case "-speed":
            speed = Integer.parseInt(args[counter + 1]);
            break;
          default:
            JOptionPane.showMessageDialog(popup,"Not a valid input");
            throw new IllegalArgumentException("Not a valid input");
        }
      } catch (IndexOutOfBoundsException e) {
        JOptionPane.showMessageDialog(popup,"Not a valid input");
        throw new IllegalArgumentException("Not a valid input");
      }
      counter += 2;
    }

    if (file == null) {
      JOptionPane.showMessageDialog(popup,"Invalid input file.");
      throw new IllegalArgumentException("Invalid input file.");
    }

    if (viewType == null) {
      JOptionPane.showMessageDialog(popup,"Invalid view type.");
      throw new IllegalArgumentException("Invalid view type.");
    }

    if (outDest.equals("out")) {
      output = System.out;
      outDest = "System.out";
    }
    else if (!outDest.equals("System.out")) {
      output = new StringBuilder();
    }

    AnimationReader inputReader = new AnimationReader();
    EasyAnimatorModelImpl.Builder builder = new EasyAnimatorModelImpl.Builder();
    inputReader.parseFile(file, builder);
    EasyAnimatorModel model = builder.build();
    View view = new ViewFactory(viewType, model, output).getView();
    if (view instanceof EditorViewImpl) {
      Controller controller = new ControllerImpl((EditorView) view);
      ((EditorViewImpl) view).addController(controller);
    }
    view.display();

    if (!outDest.equals("System.out")) {
      BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outDest)));
      writer.write(output.toString());
      writer.flush();
      writer.close();
    }
  }
}