package cs3500.animator.model.classes;

import cs3500.animator.AnimatorUtils;
import cs3500.animator.model.interfaces.Animation;
import cs3500.animator.model.interfaces.Shape;
import java.awt.Color;

/**
 * Abstract base class for implementations of {@link Animation}. Represents an abstract animation
 * on a shape that begins and ends a specific time.
 */
public abstract class AAnimation implements Animation {

  protected final Shape shapeAnimated;
  protected int startTime;
  protected int endTime;

  /**
   * For invocation by subclass constructors.
   * Generic constructor that sets the instance variables common to all animations of
   * {@link Animation}.
   *
   * @param shapeAnimated the shape the animation is manipulating.
   * @param startTime the start time the animation should begin.
   * @param endTime the end time the animation should finish.
   * @throws IllegalArgumentException if startTime or endTime are negative numbers, if the endTime
   *                                  is before the startTime, if shape being manipulated is null.
   */
  public AAnimation(Shape shapeAnimated, int startTime, int endTime)
      throws IllegalArgumentException {
    AnimatorUtils.guardAgainstInvalidShape(shapeAnimated);
    AnimatorUtils.guardAgainstInvalidStartTime(startTime);
    AnimatorUtils.guardAgainstInvalidEndTime(startTime, endTime);

    this.shapeAnimated = shapeAnimated;
    this.startTime = startTime;
    this.endTime = endTime;
    // add this animation to the list of animations on the shape.
    shapeAnimated.addAnimation(this);
  }

  /**
   * Every animation should override the toString method. This is
   * done to help ensure it happens.
   */
  @Override
  public String toString() {
    StringBuilder motion = new StringBuilder("motion ");
    motion.append(getShapeName());
    return generateAnimationState(motion.toString(), startTime,
        shapeAnimated.getX(),
        shapeAnimated.getY(),
        shapeAnimated.getFirstDimension(), shapeAnimated.getSecondDimension(),
        shapeAnimated.getColor(), false);
  }

  @Override
  public String displayAnimation(int tps) {
    StringBuilder motion = new StringBuilder("motion ");
    motion.append(getShapeName());
    return generateAnimationState(motion.toString(), startTime / tps,
        shapeAnimated.getX(),
        shapeAnimated.getY(),
        shapeAnimated.getFirstDimension(), shapeAnimated.getSecondDimension(),
        shapeAnimated.getColor(), false);
  }

  /**
   * Generates the textual description of a Animation.
   *
   * @param message Can be any needed message to prepend to the animation.
   * @param time the time to use in printing the animation.
   * @param xcoord the x coordinate to use in printing the animation.
   * @param ycoord the y coordinate to use in printing the animation.
   * @param dim1 the first dimension to use in printing the animation.
   * @param dim2 the second dimension to use in printing the animation.
   * @param color the color to use in printing the animation.
   * @param newLineAtEnd the boolean flag to indicate whether a new line at the end is
   *                     needed of printing or not.
   * @return the textual representation of an animation state.
   */
  protected String generateAnimationState(String message, long time, int xcoord, int ycoord,
      int dim1, int dim2, Color color, boolean newLineAtEnd) {
    StringBuilder motion = new StringBuilder(message);
    motion.append(" ");
    motion.append(time);
    motion.append(" ");
    motion.append(xcoord);
    motion.append(" ");
    motion.append(ycoord);
    motion.append(" ");
    motion.append(dim1);
    motion.append(" ");
    motion.append(dim2);
    motion.append(" ");
    motion.append(color.getRed());
    motion.append(" ");
    motion.append(color.getGreen());
    motion.append(" ");
    motion.append(color.getBlue());
    if (newLineAtEnd) {
      motion.append("\n");
    }
    return motion.toString();
  }

  @Override
  public Shape getShape() {
    Shape shapeAnimatedCopy = this.shapeAnimated;
    return shapeAnimatedCopy;
  }

  @Override
  public String getShapeName() {
    return shapeAnimated.getName();
  }

  @Override
  public int getStartTime() {
    return startTime;
  }

  @Override
  public int getEndTime() {
    return endTime;
  }

  @Override
  public int compareTo(Object o) {
    if (!(o instanceof Animation)) {
      throw new IllegalArgumentException("object passes is not an instance of this family of "
          + "classes");
    }
    Animation animation = (Animation) o;
    return (this.startTime - animation.getStartTime() + this.endTime - animation
        .getEndTime());
  }

  @Override
  public String toSVG(int tempo) {
    return shapeAnimated.generateProperSVG(this, tempo);
  }

  /**
   * A method that calculates the actual numbers to find the intermediary states of shapes.
   *
   * @param startState The start state of the object (can be any property).
   * @param endState The end state of the object (can be any property).
   * @param startTime The start time of the animation.
   * @param endTime The end time of the animation.
   * @return The correct intermediary state.
   */
  protected double tweenCalculator(double startState, double endState, int startTime,
      int endTime, int tick) {
    return startState * (endTime * 1.0 - tick) / (endTime * 1.0 - startTime)
        + endState * (tick * 1.0 - startTime) / (endTime * 1.0 - startTime);
  }

  /**
   * Equality means having same start and end times and type.
   *
   * @param o to check object
   * @return true if equal object. equals defined in subclasses.
   */
  @Override
  public abstract boolean equals(Object o);

  @Override
  public abstract int hashCode();

  @Override
  public void setStartTime(int start) {
    this.startTime = start;
  }

  @Override
  public void setEndTime(int end) {
    this.endTime = end;
  }
}
