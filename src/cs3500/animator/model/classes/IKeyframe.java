package cs3500.animator.model.classes;

import cs3500.animator.model.interfaces.Shape;

/**
 * Represents a general keyframe. A keyframe is meant the represent the start or
 * end state of a shape or object after animation.
 */
public interface IKeyframe {
  /**
   * Sets the x position of the shape.
   *
   * @param x the x value
   */
  void setX(int x);

  /**
   * Sets the y position of the shape.
   *
   * @param y the y value
   */
  void setY(int y);

  /**
   * Sets the width of the shape.
   *
   * @param width the width
   */
  void setWidth(int width);

  /**
   * Sets the height of the shape.
   *
   * @param height  the height
   */
  void setHeight(int height);

  /**
   * Sets the red color value of the shape.
   *
   * @param value the red color value
   */
  void setRed(int value);

  /**
   * Sets the green color value of the shape.
   *
   * @param value the green color value
   */
  void setGreen(int value);

  /**
   * Sets the blue color value of the shape.
   *
   * @param value the blue color value
   */
  void setBlue(int value);

  /**
   * Gets Shape the Keyframe is acting on.
   *
   * @return the Shape
   */
  Shape getShape();

  /**
   * Gets the start time of the keyframe.
   *
   * @return the time
   */
  int getStartTime();

  /**
   * Gets the end time of the keyframe.
   *
   * @return the time
   */
  int getEndTime();

  /**
   * Gets the x coordinate of the shape.
   *
   * @return the x coordinate
   */
  int getX();

  /**
   * Gets the y coordinate of the shape.
   *
   * @return the y coordinate
   */
  int getY();

  /**
   * Gets the width of the shape.
   *
   * @return the width of the shape
   */
  int getWidth();

  /**
   * Gets the height of the shape.
   *
   * @return the height of the shape
   */
  int getHeight();

  /**
   * Gets the red color value of the shape.
   *
   * @return the red color value
   */
  int getRed();

  /**
   * Gets the blue color value of the shape.
   *
   * @return the blue color value
   */
  int getBlue();

  /**
   * Gets the green color value of the shape.
   *
   * @return the green color value
   */
  int getGreen();

  /**
   * Sets the start time of the keyframe.
   *
   * @param time  the time
   */
  void setStartTime(int time);

  /**
   * Sets the end time of the keyframe.
   *
   * @param time  the time
   */
  void setEndTime(int time);
}
