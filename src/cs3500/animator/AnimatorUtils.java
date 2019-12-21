package cs3500.animator;



import cs3500.animator.model.classes.Keyframe;
import cs3500.animator.model.interfaces.Animation;
import cs3500.animator.model.interfaces.Shape;
import java.awt.Color;
import java.util.List;

public final class AnimatorUtils {
  /**
   * Guards against an invalid shape.
   *
   * @param shape shape to check.
   * @throws IllegalArgumentException if the given shape is null.
   */
  public static final void guardAgainstInvalidShape(Shape shape) throws IllegalArgumentException {
    if (shape == null) {
      throw new IllegalArgumentException("Shape being animated cannot be null");
    }
  }

  /**
   * Guards against a non positive start time.
   *
   * @param startTime startTime to check for being positive.
   * @throws IllegalArgumentException if the startTime is a non positive number.
   */
  public static final void guardAgainstInvalidStartTime(long startTime)
      throws IllegalArgumentException {
    if (startTime < 0) {
      throw new IllegalArgumentException("Start time cannot be before 0, since that means "
              + "before time starts.");
    }
  }

  /**
   * Guards against an invalid end time.
   *
   * @param startTime startTime of animation.
   * @param endTime end time of animation.
   * @throws IllegalArgumentException if the end time is non positive or if end time is before
   *                                  startTime.
   */
  public static final void guardAgainstInvalidEndTime(long startTime, long endTime)
          throws IllegalArgumentException {
    if (endTime < 0) {
      throw new IllegalArgumentException("End time cannot be before 0, since that means"
              + "end before time starts");
    }
    if (endTime < startTime) {
      throw new IllegalArgumentException("End time cannot be before start time.");
    }
  }

  /**
   * Guards against null color.
   *
   * @param color color to check.
   * @throws IllegalArgumentException if the color is null.
   */
  public static void guardAgainstNullColor(Color color) throws IllegalArgumentException {
    if (color == null) {
      throw new IllegalArgumentException("Cannot have a null color for a shape");
    }
  }

  /**
   * Guards against a null shape.
   *
   * @throws IllegalArgumentException if shape is null.
   */
  public static void guardAgainstNullShape(Shape shape) throws IllegalArgumentException {
    if (shape == null) {
      throw new IllegalArgumentException("Cannot have null shape.");
    }
  }

  /**
   * Guards against a null animation.
   *
   * @throws IllegalArgumentException if animation is null
   */
  public static void guardAgainstNullAnimation(Animation animation)
      throws IllegalArgumentException {
    if (animation == null) {
      throw new IllegalArgumentException("Cannot have null animation.");
    }
  }

  /**
   * Guards against a null or empty name.
   *
   * @throws IllegalArgumentException if name is null or empty
   */
  public static void guardAgainstNullOrEmptyName(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Cannot have null for the name of a shape");
    }
    if (name.isEmpty()) {
      throw new IllegalArgumentException("Shape has to have at least a one character name");
    }
  }

  /**
   * Prevents adding an animation that manipulates a Shape object that another Animation also
   * manipulates in the same way at an overlapping time interval, or if there are different
   * Animations that try to manipulate an animation at conflicting time intervals.
   *
   * @param newAnimation animation to be checked if conflicting.
   * @param animations List of current animations to check against.
   * @throws IllegalArgumentException if the given Animation conflicts in the way specified above
   *                                  with any animations in the given list of animations.
   */
  public static void guardAgainstConflictingAnimation(Animation newAnimation,
      List<Animation> animations)
      throws IllegalArgumentException {
    for (Animation existingsAnimations : animations) {
      if ((newAnimation.getStartTime() >= existingsAnimations.getStartTime()
          && newAnimation.getEndTime() < existingsAnimations.getEndTime())
          || (newAnimation.getStartTime() > existingsAnimations.getStartTime()
          && newAnimation.getEndTime() <= existingsAnimations.getEndTime())) {
        throw new IllegalArgumentException("Cannot have overlapping animations.");
      }
      if (newAnimation.equals(existingsAnimations)) {
        throw new IllegalArgumentException("Cannot have animations at the same time of the same"
            + "type");
      }
    }
  }

  /**
   * Guards against a non positive dimension.
   *
   * @param dimension dimension to check.
   * @throws IllegalArgumentException if the given dimension is less than or equal to zero.
   */
  public static void guardAgainstNonPositiveDimension(int dimension)
      throws IllegalArgumentException {
    if (dimension <= 0) {
      throw new IllegalArgumentException("Cannot create a shape with a zero or negative dimension");
    }
  }

  /**
   * Guards against a null keyframe.
   *
   * @param kf  keyframe to check
   * @throws IllegalArgumentException if the given keyframe is null
   */
  public static void guardAgainstNullKeyframe(Keyframe kf) {
    if (kf == null) {
      throw new IllegalArgumentException("Keyframe can not be null.");
    }
  }
}
