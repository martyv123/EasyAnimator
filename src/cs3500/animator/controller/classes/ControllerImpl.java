package cs3500.animator.controller.classes;

import static java.awt.event.KeyEvent.VK_SPACE;

import cs3500.animator.controller.interfaces.Controller;
import cs3500.animator.view.interfaces.EditorView;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

/**
 * Represents an implementation of a controller for our EasyAnimatorModel. This controller handles
 * any keys that are typed, pressed, and released. It handles any actions that need to be performed
 * throughout the controller as well as the changing of item states.
 */
public class ControllerImpl implements Controller {

  private EditorView view;

  public ControllerImpl(EditorView view) {
    this.view = view;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // this can be empty as we currently have no cases in which we can type letters
    return;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    try {
      if (e.getKeyCode() == VK_SPACE) {
        view.acceptCommand(CommandType.PLAY_PAUSE, null, 30);
      }
    } catch (IOException exception) {
      throw new IllegalStateException("I/O error");
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // we have no cases where the release of a key triggers an event s we can do nothing here
    return;
  }

  // This is the method that applies to the changing of buttons around
  @Override
  public void actionPerformed(ActionEvent e) {
    // we need to use a try/catch loop because the acceptCommand method can throw
    // an IOException and we need to be able to handle that case
    try {
      switch (e.getActionCommand()) {
        case ("PLAY_PAUSE"):
          view.acceptCommand(CommandType.PLAY_PAUSE, null, 0);
          break;
        case ("RESTART"):
          view.acceptCommand(CommandType.RESTART, null, 0);
          break;
        case ("LOOP"):
          view.acceptCommand(CommandType.LOOP, null, 0);
          break;
        case ("EXPORT"):
          view.acceptCommand(CommandType.EXPORT, null, 0);
          break;
        case ("SET_SPEED"):
          view.acceptCommand(CommandType.SET_SPEED, null, 0);
          break;
        default:
          throw new IllegalStateException("Case should not be reached");
      }
    } catch (IOException exception) {
      throw new IllegalStateException("I/O Error");
    }
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    try {
      switch (e.getStateChange()) {
        case ItemEvent.SELECTED:
          view.acceptCommand(CommandType.LOOP, null, 0);
          break;
        case ItemEvent.DESELECTED:
          view.acceptCommand(CommandType.LOOP, null, 0);
          break;
        default:
          view.acceptCommand(CommandType.LOOP, null, 0);
          break;
      }
    } catch (IOException e1) {
      throw new IllegalStateException("I/O error");
    }
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    try {
      JSlider slider = (JSlider) e.getSource();
      view.acceptCommand(CommandType.SET_SPEED, null, slider.getValue());
    } catch (IOException e1) {
      throw new IllegalStateException("I/O error");
    } catch (Exception e2) {
      throw new IllegalArgumentException("We don't know how you got here.....");
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    JPanel triggeringPanel = (JPanel) e.getSource();
    JLabel text = (JLabel) triggeringPanel.getComponent(0);
    if (text.getForeground().equals(new Color(69, 155, 20))) {
      text.setForeground(Color.RED);
    } else {
      text.setForeground(new Color(69, 155, 20));
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    return;
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    return;
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    JPanel triggeringPanel = (JPanel) e.getSource();
    triggeringPanel.setBackground(new Color(80, 80, 80));
  }

  @Override
  public void mouseExited(MouseEvent e) {
    JPanel triggeringPanel = (JPanel) e.getSource();
    triggeringPanel.setBackground(new Color(51, 51, 51));
  }
}
