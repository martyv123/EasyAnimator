package cs3500.animator.provider.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import cs3500.animator.provider.controller.EditController;
import cs3500.animator.provider.controller.SpawnableController;
import cs3500.animator.provider.controller.TimerController;
import cs3500.animator.provider.model.BasicShapeProperties;
import cs3500.animator.provider.model.BasicShapes;
import cs3500.animator.provider.model.ROAnimatorModel;

/**
 * <p>The view that allows the user to see the animation, but also edit it in multiple different
 * aspects.</p>
 *
 * <p>This view contains four different panels working in tandem, which each handle a component of
 * the functionality:</p>
 * <ul>
 * <li>Viewing the animation via {@link ViewPanel}</li>
 * <li>Editing the animation via {@link EditPanel}</li>
 * <li>Changing the playback of the animation via {@link TimePanel}</li>
 * <li>Saving the animation via {@link SavePanel}</li>
 * </ul>
 *
 * <p>This class itself serves to organize and initialize those panels and provide them the
 * required
 * information so that they may operate without direct communication with either each or other or
 * this class.</p>
 */
public class EditView extends JFrame
    implements AnimatorView<Integer, BasicShapeProperties, BasicShapes> {

  /**
   * The stored {@link TimerController} to provide to panels upon launching.
   */
  private TimerController timerController;

  /**
   * The stored {@link EditController} to provide to panels upon launching.
   */
  private EditController<Integer, BasicShapeProperties, BasicShapes> editController;

  /**
   * The stored {@link SpawnableController} to provide to panels upon launching.
   */
  private SpawnableController<Integer, BasicShapeProperties, BasicShapes> spawnableController;

  /**
   * The stored {@link ViewPanel} to show the animation.
   */
  private final ViewPanel showPan;

  /**
   * The stored {@link EditPanel} to edit the animation.
   */
  private final EditPanel editPan;

  /**
   * The stored {@link TimePanel} to change the playback of the animation.
   */
  private final TimePanel timePan;

  /**
   * The stored {@link SavePanel} to save the animation.
   */
  private final SavePanel savePan;

  /**
   * The constructor which takes in all three possible arguments for a view and disregards that
   * which is not necessary: the output.
   *
   * @param model the given model to be accessed for data
   * @param out   unused
   */
  @SuppressWarnings("unused")
  public EditView(ROAnimatorModel<Integer, BasicShapeProperties, BasicShapes> model,
                  Appendable out) {
    super();

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      System.err.println("Couldn't determine look and feel from system, using default");
      e.printStackTrace();
    }

    Integer[] canvas = model.getCanvas();
    int width = Objects.isNull(canvas[2]) ? 1000 : canvas[2];
    int height = Objects.isNull(canvas[3]) ? 1000 : canvas[3];
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Animations!!");

    //The panel which houses all panels capable of some form of editing
    JPanel optionsPan = new JPanel();
    optionsPan.setLayout(new BoxLayout(optionsPan, BoxLayout.Y_AXIS));
    //the panel for drawing
    showPan = new ViewPanel(model, width, height);
    showPan.setPreferredSize(new Dimension(width, height));
    //the panel for editing elements of the animation
    editPan = new EditPanel(model);

    //the panel for dealing with time
    timePan = new TimePanel();

    //the panel for saving capabilities
    savePan = new SavePanel();


    this.add(showPan, BorderLayout.EAST);

    optionsPan.add(savePan);
    optionsPan.add(editPan);
    optionsPan.add(timePan);

    this.add(optionsPan, BorderLayout.WEST);


    this.pack();
  }

  @Override
  public boolean addTimerController(TimerController timerController) {
    this.timerController = timerController;
    return true;
  }

  @Override
  public void addEditController(
      EditController<Integer, BasicShapeProperties, BasicShapes> controller) {
    this.editController = controller;
  }

  @Override
  public void addSpawnableController(
      SpawnableController<Integer, BasicShapeProperties, BasicShapes> controller) {
    this.spawnableController = controller;
  }

  @Override
  public void refresh(int tick) {
    this.showPan.refreshScreen(tick);
    this.repaint(tick, 0, 0, this.getWidth(), this.getHeight());
  }

  /**
   * Creates the animation by redrawing the frame with the drawn sprites.
   *
   * @throws IOException to be consistent with the method signature
   */
  @Override
  public void launch() throws IOException {
    this.editPan.setController(editController);
    this.timePan.setTimerController(timerController);
    this.savePan.setController(spawnableController);
    this.setVisible(true);
  }
}
