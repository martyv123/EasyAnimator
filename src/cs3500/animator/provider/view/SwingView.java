package cs3500.animator.provider.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import cs3500.animator.provider.controller.TimerController;
import cs3500.animator.provider.model.BasicShapeProperties;
import cs3500.animator.provider.model.BasicShapes;
import cs3500.animator.provider.model.ROAnimatorModel;

/**
 * The swing view for the given model. It creates then draws the given model and plays the
 * animation.
 */
public class SwingView extends JFrame
    implements AnimatorView<Integer, BasicShapeProperties, BasicShapes> {

  private final ViewPanel pan;

  /**
   * The constructor which takes in all three possible arguments for a view and disregards that
   * which is not necessary: the output.
   *
   * @param model the given model to be accessed for data
   * @param out   unused
   */
  @SuppressWarnings("unused")
  public SwingView(ROAnimatorModel<Integer, BasicShapeProperties, BasicShapes> model,
                   Appendable out) {
    super();
    Integer[] canvas = model.getCanvas();
    int width = Objects.isNull(canvas[2]) ? 1000 : canvas[2];
    int height = Objects.isNull(canvas[3]) ? 1000 : canvas[3];
    this.setSize(width, height);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Animations!!");

    this.setLayout(new BorderLayout());
    //the panel for drawing
    pan = new ViewPanel(model, width, height);
    pan.setPreferredSize(new Dimension(width, height));
    //the panel for scrolling
    JScrollPane scrollPan = new JScrollPane(pan);

    this.add(scrollPan, BorderLayout.CENTER);

    this.pack();
  }

  @Override
  public boolean addTimerController(TimerController timerController) {
    return true;
  }

  @Override
  public void refresh(int tick) {
    this.pan.refreshScreen(tick);
  }

  /**
   * Creates the animation by redrawing the frame with the drawn sprites.
   *
   * @throws IOException to be consistent with the method signature
   */
  @Override
  public void launch() throws IOException {
    this.setVisible(true);
  }

}
