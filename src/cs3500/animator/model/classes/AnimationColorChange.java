package cs3500.animator.model.classes;

import cs3500.animator.AnimatorUtils;
import cs3500.animator.model.interfaces.Shape;
import java.awt.Color;
import java.util.Objects;

/**
 * To hold the animation information about changing a {@link Shape} color.
 */
public class AnimationColorChange extends AAnimation {

  private final Color toColor;

  /**
   * Constructs a new AnimationColorChange object.
   *
   * @param shapeAnimated the shape the animation is manipulating.
   * @param startTime the start time the animation should begin.
   * @param endTime the end time the animation should finish.
   * @param color represents the color the shape being manipulated will have at the end of this
   *              animation
   * @throws IllegalArgumentException if startTime or endTime are negative numbers, if the endTime
   *                                  is before the startTime, if shape being manipulated is null.
   */
  public AnimationColorChange(Shape shapeAnimated, int startTime, int endTime, Color color)
      throws IllegalArgumentException {
    super(shapeAnimated, startTime, endTime);
    AnimatorUtils.guardAgainstNullColor(color);
    this.toColor = color;
  }

  @Override
  public String toString() {
    return generateAnimationState(super.toString(), endTime,
        shapeAnimated.getX(),
        shapeAnimated.getY(),
        shapeAnimated.getFirstDimension(),
        shapeAnimated.getSecondDimension(),
        toColor, true);
  }

  @Override
  public String displayAnimation(int tps) {
    return generateAnimationState(super.displayAnimation(tps), endTime / tps,
        shapeAnimated.getX(),
        shapeAnimated.getY(),
        shapeAnimated.getFirstDimension(),
        shapeAnimated.getSecondDimension(),
        toColor, true);
  }

  @Override
  public String rectangleSVG(int tempo) {
    StringBuilder output = new StringBuilder("");

    output.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\"" +
            " attributeType=\"xml\" " +
            "begin=\"base.begin+%dms\" dur=\"%dms\" attributeName=\"fill\" from=\"%s\" to=\"%s\"" +
            " fill=\"freeze\"/>\n",
        (startTime / tempo) * 1000,
        ((endTime - startTime) / tempo) * 1000,
        shapeAnimated.getColor().getRed(), shapeAnimated.getColor().getGreen(),
        shapeAnimated.getColor().getBlue(),
        toColor.getRed(), toColor.getGreen(), toColor.getBlue()));

    return output.toString();
  }

  @Override
  public String ellipseSVG(int tempo) {
    StringBuilder output = new StringBuilder("");

    output.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\"" +
            " attributeType=\"xml\" " +
            "begin=\"%dms\" dur=\"base.begin+%dms\" attributeName=\"fill\" from=\"%s\" to=\"%s\"" +
            " fill=\"freeze\"/>\n",
        (startTime / tempo) * 1000,
        ((endTime - startTime) / tempo) * 1000,
        shapeAnimated.getColor().getRed(), shapeAnimated.getColor().getGreen(),
        shapeAnimated.getColor().getBlue(),
        toColor.getRed(), toColor.getGreen(), toColor.getBlue()));

    return output.toString();
  }

  @Override
  public AnimationColorChange copy() {
    AnimationColorChange copy = new AnimationColorChange(this.shapeAnimated, this.startTime,
        this.endTime, this.toColor);
    return copy;
  }

  /**
   * Returns the hashcode of the object. Names of shapes are unique, not ignoring case.
   *
   * @return an integer representing the hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.shapeAnimated, startTime, endTime);
  }

  @Override
  public void apply(int tick) {
    Color startColor = shapeAnimated.getColor();

    double newR = tweenCalculator(startColor.getRed(), toColor.getRed(), startTime, endTime, tick);
    double newG =
        tweenCalculator(startColor.getGreen(), toColor.getGreen(), startTime, endTime, tick);
    double newB =
        tweenCalculator(startColor.getBlue(), toColor.getBlue(), startTime, endTime, tick);

    int newRColor = (int)Math.round(newR);
    int newGColor = (int)Math.round(newG);
    int newBColor = (int)Math.round(newB);

    shapeAnimated.setColor(new Color(newRColor, newGColor, newBColor));
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    } else if (!(other instanceof AnimationColorChange)) {
      return false;
    } else {
      AnimationColorChange that = (AnimationColorChange) other;
      return this.getStartTime() == that.getStartTime()
          && this.getEndTime() == that.getEndTime();
    }

  }

}
