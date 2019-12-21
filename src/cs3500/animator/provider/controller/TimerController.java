package cs3500.animator.provider.controller;

/**
 * The interface for the controller which cares about time.
 */
public interface TimerController {

  /**
   * Starts the animation from the beginning, meaning setting the tick to 1. Does not affecting the
   * pause state of the timer but will manually issue a
   * {@link cs3500.animator.provider.view.AnimatorView#refresh(int)}
   * call.
   */
  void startFromBeginning();

  /**
   * Pauses the animation at the current time.
   */
  void pause();

  /**
   * Resumes the animation at the current time.
   */
  void resume();

  /**
   * Toggles whether of not the animation is looping.
   *
   * @return the boolean of whether or not the animation is looping.
   */
  boolean toggleLooping();

  /**
   * Checks whether the animation is looping or not.
   *
   * @return the boolean value of whether or not the animation is looping.
   */
  boolean isLooping();

  /**
   * Sets the speed of the animation to the given speed in ticks per second.
   *
   * @param ticksPerSecond the desired speed for the animation to be set to in terms of ticks per
   *                       second.
   * @throws IllegalArgumentException if the ticks per second is not positive
   */
  void setSpeed(int ticksPerSecond) throws IllegalArgumentException;

  /**
   * Gets the speed of the animation.
   *
   * @return the integer represents the speed of the animation in ticks per second.
   */
  int getSpeed();
}
