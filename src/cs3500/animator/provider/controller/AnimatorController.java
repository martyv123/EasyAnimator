package cs3500.animator.provider.controller;

import java.io.IOException;

/**
 * The basic controller interface. The controller interacts with both the view and the model in
 * order to link their functionality.
 */
public interface AnimatorController {
  /**
   * The launch method of the basic Animator controller is specific to each implementation of this
   * interface, but is the method which links the view functionality and the model.
   * @throws IOException If the launch method of the view throws an IOException
   */
  void launch() throws IOException;
}
