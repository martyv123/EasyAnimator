package cs3500.animator.model.classes;

import cs3500.animator.model.interfaces.Animation;
import cs3500.animator.view.interfaces.IViewPanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

import cs3500.animator.model.interfaces.Shape;

/**
 * Represents an Ellipse shape in animations.
 */
public class Ellipse extends AShape {

  /**
   * Constructs an Ellipse shape object. The positional coordinates describe where the center of the
   * ellipse is in a positional grid. The center is defined as where the majorAxis and minorAxis
   * intersect.
   *
   * @param name      represents the name of a shape object.
   * @param majorAxis represents the first dimension: major radius of an oval.
   * @param minorAxis represents the second dimension: minor radius of an oval.
   * @param x         represents the x positional coordinate
   * @param y         represents the y positional coordinate
   * @param color     represents the color
   * @throws IllegalArgumentException if the name is null or an empty string, if either dimension of
   *                                  the shape is a non positive number, if the color given is
   *                                  null.
   */
  public Ellipse(String name, int majorAxis, int minorAxis, int x, int y, int startTime,
                 int endTime, Color color)
          throws IllegalArgumentException {
    super(name, majorAxis, minorAxis, x, y, startTime, endTime, color);
    this.type = ShapeType.ELLIPSE;
  }

  /**
   * This is a copy constructor.
   *
   * @param shapeToCopy the shape to copy. By copying we mean to create a new shape with the same
   *                    exact everything as the given shape.
   * @throws IllegalArgumentException if the inputted shape is null.
   */
  public Ellipse(Shape shapeToCopy)
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
    } else if (!(other instanceof Ellipse)) {
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
    Ellipse copy = new Ellipse(this);
    return copy;
  }

  /**
   * Generates the SVG description of an ellipse and its animations.
   *
   * @param tempo the integer speed to loop at.
   * @param isLooping the boolean flag to see if the svg should loop or not.
   * @return the SVG description
   */
  public String toSVG(int tempo, boolean isLooping) {
    StringBuilder output = new StringBuilder();
    output
            .append(String.format("<ellipse xmlns=\"http://www.w3.org/2000/svg\" id=\"%s\" "
                    + "cx=\"%d\"" +
                            " cy=\"%d\" rx=\"%d\" ry=\"%d\" fill=\"rgb(%d,%d,%d)\" "
                    + "visibility=\"hidden\">\n",
                    name, this.getX(), this.getY(), this.firstDimension,
                    this.secondDimension, this.color.getRed(), this.color.getGreen(),
                    this.color.getBlue()));

    output.append(String.format("<set attributeType=\"xml\" " +
            "begin=\"%sms\" attributeName=\"visibility\" " +
            "to=\"visible\"/>\n", startTime / tempo * 1000));

    output.append(String.format("<set attributeType=\"xml\" " +
                    "begin=\"%sms\" attributeName=\"visibility\" " +
                    "to=\"hidden\"/>\n",
            endTime / tempo * 1000));

    for (Animation animation : animations) {
      output.append(animation.toSVG(tempo));
    }

    output.append("</ellipse>\n");

    return output.toString();
  }

  @Override
  public String generateProperSVG(Animation a, int tempo) {
    return a.ellipseSVG(tempo);
  }

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
