package cs3500.animator.provider.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Objects;
import java.util.OptionalInt;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cs3500.animator.provider.controller.TimerController;

/**
 * Panel containing buttons for changing the timing and speed of the animation. None of the buttons
 * will have an effect until this panel is given a {@link TimerController} via the {@link
 * TimePanel#setTimerController(TimerController)} method.
 *
 * <p>This panel supports five high-level actions:</p>
 * <ul>
 * <li>Restart the animation from the beginning via {@link TimerController#startFromBeginning()}.
 * That method will set the tick to 1 and refresh the view but will not affect whether the animation
 * is currently paused or playing.</li>
 * <li>Pause the animation via {@link TimerController#pause()}. Has no effect if the animation is
 * already paused.</li>
 * <li>Play the animation via {@link TimerController#resume()}. Has no effect if the animation is
 * already playing.</li>
 * <li>Toggle whether the animation should loop back to tick 1 at the end or whether the last valid
 * tick should be repeated, via {@link TimerController#toggleLooping()}. Note that this action will
 * not ever pause the animation even if that is the apparent behavior of repeating the last valid
 * tick.</li>
 * <li>Change the speed of the animation via a modal flow using the {@link
 * TimerController#setSpeed(int)} method.</li>
 * </ul>
 *
 * <p>The interface of this panel simply consists of five buttons corresponding to the actions
 * above. The toggle-looping button wil be green in the case of looping and red in the case of not
 * looping. Clicking the button to change the speed will launch a modal flow to obtain and validate
 * input from the user.</p>
 */
class TimePanel extends JPanel {

  /**
   * The stored {@link TimerController} to direct high-level events to. The default is null and the
   * buttons on this panel will have no effect until it is changed.
   */
  private TimerController controller;

  /**
   * The button that toggles looping. Stored as a field so that its background may be updated after
   * it has been constructed.
   */
  private final JButton looping;

  /**
   * Constructor for this panel. Takes no arguments and sets the buttons up.
   */
  TimePanel() {
    this.controller = null;
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    this.setBorder(BorderFactory.createTitledBorder("Time"));

    JButton restart = new JButton("Restart");
    restart.addActionListener(this::actionPerformed);
    this.add(restart);

    JButton pause = new JButton("Pause");
    pause.addActionListener(this::actionPerformed);
    this.add(pause);

    JButton play = new JButton("Play");
    play.addActionListener(this::actionPerformed);
    this.add(play);

    looping = new JButton("Toggle Looping");
    looping.setBorderPainted(false);
    looping.addActionListener(this::actionPerformed);
    this.add(looping);

    JButton changeSpeed = new JButton("Change Speed");
    changeSpeed.addActionListener(this::actionPerformed);
    this.add(changeSpeed);
  }

  /**
   * Set the {@link TimerController} this should use when requesting changed to the time behavior.
   *
   * @param timerController the {@link TimerController} to store
   */
  void setTimerController(TimerController timerController) {
    this.controller = timerController;
    looping.setBackground(this.controller.isLooping() ? Color.GREEN : Color.RED);
  }

  /**
   * A low-level handler of individual button presses. Largely delegates to the corresponding
   * methods in the {@link TimerController}.
   *
   * @param e the action event sent from a button
   */
  private void actionPerformed(ActionEvent e) {
    if (!Objects.isNull(this.controller)) {
      switch (e.getActionCommand()) {
        case "Restart":
          this.controller.startFromBeginning();
          break;
        case "Pause":
          this.controller.pause();
          break;
        case "Play":
          this.controller.resume();
          break;
        case "Toggle Looping":
          this.looping.setBackground(this.controller.toggleLooping() ? Color.GREEN : Color.RED);
          break;
        case "Change Speed":
          this.controller.setSpeed(this.getNewSpeed(this.controller.getSpeed()));
          break;
        default:
          break;
      }
    }
  }

  /**
   * Dialog flow to get a new speed from the user, checking that it is an actual valid and positive
   * integer. If it isn't, a error box will be shown and the user will be returned to the first
   * phase of entering a number again. If the user presses the cancel button, the flow will be
   * exited, and the speed will be unchanged.
   *
   * @param speed the existing speed, to use in the event that the flow is canceled
   * @return the speed to set
   */
  private int getNewSpeed(int speed) {
    int toReturn = speed;
    String response = "";
    while (!IntTools.parseToInt(response).isPresent()) {
      response = JOptionPane
          .showInputDialog(this, "Set a new ticks per second (positive integer) for the animation:",
              String.valueOf(speed));
      if (response == null) {
        break;
      }
      OptionalInt parsed = IntTools.parseToInt(response);
      if (parsed.isPresent() && parsed.getAsInt() > 0) {
        toReturn = parsed.getAsInt();
      } else {
        JOptionPane
            .showMessageDialog(this, "The speed must be a positive integer, please try again",
                "Try again", JOptionPane.WARNING_MESSAGE);
      }
    }
    return toReturn;
  }
}
