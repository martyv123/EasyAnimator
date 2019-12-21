package cs3500.animator.model.classes;

import cs3500.animator.model.interfaces.Animation;
import cs3500.animator.view.interfaces.IViewPanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

import cs3500.animator.model.interfaces.Shape;

/**
 * Represents a rectangle shape.
 */
public class Rectangle extends AShape {

  /**
   * Constructs a Rectangle object. The positional coordinates describe where the bottom left corner
   * of the rectangle is in a positional grid.
   *
   * @param length represents the first dimension: length of the shape
   * @param width  represents the second dimension: width of the shape
   * @param x      represents the x positional coordinate
   * @param y      represents the y positional coordinate
   * @param color  represents the color
   * @throws IllegalArgumentException if the name is null or an empty string, if either dimension of
   *                                  the shape is a non positive number, if the color given is
   *                                  null.
   */
  public Rectangle(String name, int length, int width, int x, int y, int startTime, int endTime,
                   Color color)
          throws IllegalArgumentException {
    super(name, length, width, x, y, startTime, endTime, color);
    type = ShapeType.RECTANGLE;
  }

  /**
   * Copy constructor.
   *
   * @param shapeToCopy the given shape to copy. By copy we mean the given shape to construct an
   *                    identical shape, with every field the same as the given shape.
   * @throws IllegalArgumentException if the given shape is null.
   */
  public Rectangle(Rectangle shapeToCopy)
          throws IllegalArgumentException {
    this(shapeToCopy.getName(), shapeToCopy.getFirstDimension(), shapeToCopy.getSecondDimension(),
            shapeToCopy.getX(), shapeToCopy.getY(), shapeToCopy.getStartTime(),
            shapeToCopy.getEndTime(), shapeToCopy.getColor());
    this.keyframes = shapeToCopy.getKeyframes();
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    } else if (!(other instanceof Rectangle)) {
      return false;
    } else {
      return this.name.equals((((AShape) other).name));
    }
  }


  @Override
  public int hashCode() {
    return Objects.hash(this.name);
  }

  @Override
  public Shape copy() {
    Rectangle copy = new Rectangle(this);
    return copy;
  }

  /**
   * Returns the SVG description of a rectangle and its animations.
   *
   * @param tempo the tempo the animation is playing at.
   * @param isLooping the boolean flag of if the svg should be looping or not.
   * @return the SVG description
   */
  public String toSVG(int tempo, boolean isLooping) {
    StringBuilder output = new StringBuilder();

    output.append(String.format("<rect xmlns=\"http://www.w3.org/2000/svg\" id=\"%s\" x=\"%d\"" +
                    " y=\"%d\" width=\"%d\" height=\"%d\" fill=\"rgb(%d,%d,%d)\" " +
                    "visibility=\"hidden\">\n",
            name, this.getX(), this.getY(), this.firstDimension,
            this.secondDimension, this.color.getRed(), this.color.getGreen(),
        this.color.getBlue()));

    // set when shape should appear.
    output.append(String.format("<set attributeType=\"xml\" " +
                    "begin=\"%sms\" attributeName=\"visibility\" " +
                    "to=\"visible\"/>\n",
            (startTime / tempo) * 1000));

    // set when shape should disappear.
    output.append(String.format("<set attributeType=\"xml\" " +
                    "begin=\"%sms\" attributeName=\"visibility\" " +
                    "to=\"hidden\"/>\n",
            endTime / tempo * 1000));

    for (Animation animation : animations) {
      output.append(animation.toSVG(tempo));
    }

    output.append("</rect>\n");

    return output.toString();
  }

  @Override
  public String generateProperSVG(Animation a, int tempo) {
    return a.rectangleSVG(tempo);
  }

  @Override
  public void drawShape(IViewPanel viewPanel, Graphics2D graphics2D) {
    viewPanel.drawShape(this, graphics2D);
  }

  /**
   * Gets the enumeration type of the Shape.
   *
   * @return the enumeration
   */
  public ShapeType getType() {
    return this.type;
  }
}
