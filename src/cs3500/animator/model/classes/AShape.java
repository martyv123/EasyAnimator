package cs3500.animator.model.classes;

import cs3500.animator.AnimatorUtils;
import cs3500.animator.model.interfaces.Animation;
import cs3500.animator.model.interfaces.DrawableShape;
import cs3500.animator.model.interfaces.Shape;
import java.awt.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents an abstract shape. Any shape should have a name, first dimension, second dimension,
 * positional coordinates, and a color. It should also have a start time and end time if it should
 * be animated. If it is not animated, these values should be defaulted to 0. A shape also holds a
 * list of its animations, and its type.
 */
public abstract class AShape implements Shape, DrawableShape {

  protected String name;
  protected int firstDimension;
  protected int secondDimension;
  protected int x;
  protected int y;
  protected Color color;
  // to add when shapes are visible.
  protected int startTime;
  protected int endTime;
  protected List<Animation> animations;
  protected List<Keyframe> keyframes;
  protected ShapeType type;


  /**
   * Constructs an abstract shape. Coordinate position, the firstDimension, and secondDimension
   * interpretations are left to the subclasses.
   *
   * @param name            represents the name of a shape object.
   * @param firstDimension  represents the first dimension of a 2 dimensional shape.
   * @param secondDimension represents the second dimension of a 2 dimensional shape.
   * @param x               represents the x positional coordinate
   * @param y               represents the y positional coordinate
   * @param color           represents the color
   * @throws IllegalArgumentException if the name is null or an empty string, if either dimension of
   *                                  the shape is a non positive number, if the color given is
   *                                  null.
   */
  public AShape(String name, int firstDimension, int secondDimension, int x, int y,
                int startTime, int endTime, Color color)
          throws IllegalArgumentException {
    AnimatorUtils.guardAgainstNullOrEmptyName(name);
    AnimatorUtils.guardAgainstNonPositiveDimension(firstDimension);
    AnimatorUtils.guardAgainstNonPositiveDimension(secondDimension);
    AnimatorUtils.guardAgainstInvalidStartTime(startTime);
    AnimatorUtils.guardAgainstInvalidEndTime(startTime, endTime);
    AnimatorUtils.guardAgainstNullColor(color);

    this.name = name;
    this.firstDimension = firstDimension;
    this.secondDimension = secondDimension;
    this.x = x;
    this.y = y;
    this.color = color;
    this.startTime = startTime;
    this.endTime = endTime;
    this.animations = new ArrayList<Animation>();
    this.keyframes = new ArrayList<Keyframe>();
    // abstract animations cannot have a ShapeType
    type = null;
  }

  /**
   * Guards against a non positive start time.
   *
   * @param startTime startTime to check for being positive.
   * @throws IllegalArgumentException if the startTime is a non positive number.
   */
  private final void guardAgainstInvalidStartTime(int startTime) throws IllegalArgumentException {
    if (startTime < 0) {
      throw new IllegalArgumentException("Start time cannot be before 0, since that means "
              + "before time starts.");
    }
  }

  /**
   * Guards against an invalid end time.
   *
   * @param startTime startTime of animation.
   * @param endTime   end time of animation.
   * @throws IllegalArgumentException if the end time is non positive or if end time is before
   *                                  startTime.
   */
  private final void guardAgainstInvalidEndTime(int startTime, long endTime)
          throws IllegalArgumentException {
    if (endTime < 0) {
      throw new IllegalArgumentException("End time cannot be before 0, since that means"
              + "end before time starts");
    }
    if (endTime < startTime) {
      throw new IllegalArgumentException("End time cannot be before start time.");
    }
  }

  @Override
  public int getFirstDimension() {
    return firstDimension;
  }

  @Override
  public int getSecondDimension() {
    return secondDimension;
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public List<Animation> getAnimations() {
    List<Animation> animationsCopy = this.animations;
    return animationsCopy;
  }

  @Override
  public void addAnimation(Animation animation) throws IllegalArgumentException {
    AnimatorUtils.guardAgainstNullAnimation(animation);
    guardAgainstConflictingAnimation(animation);
    Collections.sort(this.animations);
    animations.add(animation);
    Collections.sort(this.animations);
  }

  @Override
  public void setX(int x) {
    this.x = x;
  }

  @Override
  public void setY(int y) {
    this.y = y;
  }

  @Override
  public void setDimensions(int firstDimension, int secondDimension)
          throws IllegalArgumentException {
    AnimatorUtils.guardAgainstNonPositiveDimension(firstDimension);
    AnimatorUtils.guardAgainstNonPositiveDimension(secondDimension);
    this.firstDimension = firstDimension;
    this.secondDimension = secondDimension;
  }

  @Override
  public String toString() {
    StringBuilder out = new StringBuilder();
    Keyframe kfPrev;
    Keyframe kfCurr;
    out.append("shape " + name + " " + type.name().toLowerCase() + "\n");
    out.append("motion ").append(name).append(" ");

    for (int i = 0; i <= this.keyframes.size() - 1; i++) {
      if (i == 0) {
        kfPrev = keyframes.get(i);
        kfCurr = keyframes.get(i);
      } else {
        kfCurr = keyframes.get(i);
        kfPrev = keyframes.get(i - 1);
      }
      System.out.println(kfCurr);

      out.append(kfPrev.getEndTime()).append(" ").append(kfPrev.getX()).append(" ")
          .append(kfPrev.getY()).append(" ").append(kfPrev.getWidth()).append(" ")
          .append(kfPrev.getHeight()).append(" ")
          .append(kfPrev.getRed()).append(" ").append(kfPrev.getGreen()).append(" ")
          .append(kfPrev.getBlue()).append(" ")
          .append(kfCurr.getEndTime()).append(" ")
          .append(kfCurr.getX()).append(" ").append(kfCurr.getY())
          .append(" ").append(kfCurr.getWidth()).append(" ").append(kfCurr.getHeight()).append(" ")
          .append(kfCurr.getRed()).append(" ").append(kfCurr.getGreen()).append(" ")
          .append(kfCurr.getBlue()).append("\n");
    }
    return out.toString();
  }

  @Override
  public void setColor(Color color) throws IllegalArgumentException {
    AnimatorUtils.guardAgainstNullColor(color);
    this.color = color;
  }

  @Override
  public int getStartTime() {
    return this.startTime;
  }

  @Override
  public int getEndTime() {
    return this.endTime;
  }

  @Override
  public void setStartTime(int time) {
    this.startTime = time;
  }

  @Override
  public void setEndTime(int time) {
    this.endTime = time;
  }

  /**
   * Equality means having same name and type.
   *
   * @param o to check object
   * @return true if equal object. equals defined in subclasses.
   */
  public abstract boolean equals(Object o);

  /**
   * Returns the hashcode of the object. Names of shapes are unique, not ignoring case.
   *
   * @return an integer representing the hashcode
   */
  public int hashCode() {
    return Objects.hash(this.name);
  }

  /**
   * Prevents adding an animation that manipulates a Shape object that another Animation also
   * manipulates in the same way at an overlapping time interval.
   *
   * @param newAnimation animation to be checked if conflicting.
   * @throws IllegalArgumentException if the given Animation conflicts in the way specified above
   *                                  with another Animation.
   */
  private void guardAgainstConflictingAnimation(Animation newAnimation)
          throws IllegalArgumentException {
    for (Animation existingAnimations : animations) {
      if ((newAnimation.getStartTime() >= existingAnimations.getStartTime()
              && newAnimation.getEndTime() < existingAnimations.getEndTime())
              || (newAnimation.getStartTime() > existingAnimations.getStartTime()
              && newAnimation.getEndTime() <= existingAnimations.getEndTime())) {
        throw new IllegalArgumentException("Cannot have overlapping animations.");
      }
    }
  }

  @Override
  public void addKeyframe(Keyframe kf) throws IllegalArgumentException {
    AnimatorUtils.guardAgainstNullKeyframe(kf);
    this.keyframes.add(kf);
    Collections.sort(this.keyframes, new KeyframeComparator());
  }

  @Override
  public void removeKeyframe(Keyframe kf) throws IllegalArgumentException {
    AnimatorUtils.guardAgainstNullKeyframe(kf);
    if (!this.keyframes.contains(kf)) {
      throw new IllegalArgumentException("Keyframe does not exist.");
    }
    this.keyframes.remove(kf);
  }

  @Override
  public ArrayList<Keyframe> getKeyframes() {
    ArrayList<Keyframe> copy = new ArrayList<Keyframe>();
    for (Keyframe kf : this.keyframes) {
      copy.add(kf);
    }
    return copy;
  }
}

