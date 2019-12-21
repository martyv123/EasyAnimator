package cs3500.animator.provider.view;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cs3500.animator.provider.controller.SpawnableController;
import cs3500.animator.provider.model.BasicShapeProperties;
import cs3500.animator.provider.model.BasicShapes;

/**
 * <p>Panel containing buttons for saving the current model. The buttons have no effect until this
 * is
 * given a {@link SpawnableController} by {@link SavePanel#setController(SpawnableController)}.</p>
 *
 * <p>This panel supports two high-level actions:</p>
 * <ul>
 * <li>Saving the animation as a text file matching the specification of {@link
 * cs3500.animator.util.AnimationReader}.</li>
 * <li>Saving the animation as an SVG file. The resulting file will maintain not only the behavior
 * of the shapes and keyframes but also the ticks per second and whether the animation is currently
 * set to be looping or not.</li>
 * </ul>
 *
 * <p>This panel contains exactly two buttons corresponding to the above behaviors. Upon clicking
 * either button, the user will be prompted with a file save window that will allow them to navigate
 * the file system to decide where to save the file. The file extension will default to that of the
 * selected action: {@code .txt} for saving as a text file and {@code .svg} for saving as an SVG
 * file. If the user affirmatively closes the save dialog, the specified file will be written.</p>
 */
class SavePanel extends JPanel {

  /**
   * The stored {@link SpawnableController}. Is initialized to null by the constructor and the
   * buttons will have no effect until that is changed.
   */
  private SpawnableController<Integer, BasicShapeProperties, BasicShapes> controller;

  /**
   * Constructor for {@link SavePanel}. Takes no arguments and sets up the buttons.
   */
  public SavePanel() {
    this.controller = null;

    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    this.setBorder(BorderFactory.createTitledBorder("Save"));

    JButton text = new JButton("Save as Text");
    text.addActionListener(this::actionPerformed);
    this.add(text);

    JButton svg = new JButton("Save as SVG");
    svg.addActionListener(this::actionPerformed);
    this.add(svg);
  }

  /**
   * Update the stored {@link SpawnableController}.
   *
   * @param spawnControl the {@link SpawnableController} to store
   */
  public void setController(
      SpawnableController<Integer, BasicShapeProperties, BasicShapes> spawnControl) {
    this.controller = spawnControl;
  }

  /**
   * If there is a {@link SpawnableController} stored, save a file of the type according to the
   * activated button.
   *
   * @param e the low-level action from the button
   */
  private void actionPerformed(ActionEvent e) {
    if (!Objects.isNull(this.controller)) {
      switch (e.getActionCommand()) {
        case "Save as Text":
          this.saveFile(".txt", "text");
          break;
        case "Save as SVG":
          this.saveFile(".svg", "svg");
          break;
        default:
          break;
      }
    }
  }

  /**
   * Save the file with the given extension using the given {@link AnimatorViewBuildable} to export
   * the file.
   *
   * @param extension the extension to show as the default for the saved file
   * @param type    the type of file to create
   */
  private void saveFile(String extension,
                        String type) {
    JFileChooser browser = new JFileChooser();
    browser.setSelectedFile(new File("myAnimation" + extension));
    int returnValue = browser.showSaveDialog(this);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      try {
        this.controller.spawn(type,
            new PrintStream(new FileOutputStream(
                browser.getCurrentDirectory()
                    + "/" +
                    browser.getSelectedFile().getName()), true))
            .launch();
      } catch (IOException e) {
        JOptionPane
            .showMessageDialog(this, "Couldn't save the file!",
                "Save failed", JOptionPane.WARNING_MESSAGE);
      }
    }
  }
}
