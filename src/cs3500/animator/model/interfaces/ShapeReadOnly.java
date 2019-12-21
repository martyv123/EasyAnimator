package cs3500.animator.model.interfaces;

import cs3500.animator.model.classes.ShapeType;
import cs3500.animator.view.interfaces.IViewPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

/**
 * A read only representation of a 2D shape.
 */
public interface ShapeReadOnly {

  /**
   * Gets the name of the shape object.
   *
   * @return returns the name of the shape object.
   */
  String getName();

  /**
   * Gets the x-coordinate of the shape object.
   *
   * @return returns the x coordinate of the shape object.
   */
  int getX();


  /**
   * Gets the y-coordinate of the shape object.
   *
   * @return returns the  y coordinate of the shape object.
   */
  int getY();

  /**
   * Gets the first dimension of the shape object. The first dimension will represent different
   * measurements in different 2D shapes.
   *
   * @return returns the first dimension of the shape object.
   */
  int getFirstDimension();

  /**
   * Gets the second dimension of the shape object. The second dimension will represent different
   * measurements in different 2D shapes.
   *
   * @return returns the second dimension of the shape object.
   */
  int getSecondDimension();

  /**
   * Gets the color of the shape object.
   *
   * @return returns the color of the shape object.
   */
  Color getColor();

  /**
   * Gets the animations associated with the shape.
   *
   * @return a list of animations associated with the shape.
   */
  List<Animation> getAnimations();

  /**
   * Creates a copy of the shape by creating a new Shape object with the same information as the
   * current shape.
   *
   * @return a copy of this shape
   */
  Shape copy();

  /**
   * Creates an SVG representation describing the Shape.
   *
   * @return the SVG output of the shape as a String.
   */
  String toSVG(int tempo, boolean isLooping);

  /**
   * Shapes can generate their proper SVG output.
   *
   * @param a     animation of shape.
   * @param tempo speed of SVG.
   * @return String representing SVG output.
   */
  String generateProperSVG(Animation a, int tempo);

  /**
   * Draws a shape with Swing.
   *
   * @param p          Swing JPanel to draw on.
   * @param graphics2D Draw object.
   */
  void drawShape(IViewPanel p, Graphics2D graphics2D);

  /**
   * Gets start time of shape.
   *
   * @return gets start time of shape.
   */
  int getStartTime();

  /**
   * Gets end time of shape.
   *
   * @return gets end time of shape.
   */
  int getEndTime();

  /**
   * Gets the type of shape.
   *
   * @return the shape type.
   */
  ShapeType getType();
}