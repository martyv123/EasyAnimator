package cs3500.animator.model.classes;

import cs3500.animator.AnimatorUtils;
import cs3500.animator.model.interfaces.Shape;
import java.util.Objects;

/**
 * To hold the animation information about changing a {@link Shape} dimensions.
 */
public class AnimationResize extends AAnimation {

  private final int endFirstDimension;
  private final int endSecondDimension;

  /**
   * Creates a resizing animation object.
   *
   * @param shapeAnimated the shape the animation is manipulating.
   * @param startTime the start time the animation should begin.
   * @param endTime the end time the animation should finish.
   * @param endFirstDimension the ending first dimension of the shape being manipulated.
   * @param endSecondDimension the ending second dimension of the shape being manipulated.
   * @throws IllegalArgumentException if startTime or endTime are negative numbers, if the endTime
   *                                  is before the startTime, if shape being manipulated is null,
   *                                  if the endFirstDimension or the
   *                                  endSecondDimension are non positive numbers.
   */
  public AnimationResize(Shape shapeAnimated, int startTime,
      int endTime, int endFirstDimension, int endSecondDimension) throws IllegalArgumentException {
    super(shapeAnimated, startTime, endTime);
    AnimatorUtils.guardAgainstNonPositiveDimension(endFirstDimension);
    AnimatorUtils.guardAgainstNonPositiveDimension(endSecondDimension);
    this.endFirstDimension = endFirstDimension;
    this.endSecondDimension = endSecondDimension;
  }

  @Override
  public String toString() {
    return generateAnimationState(super.toString(), endTime,
        shapeAnimated.getX(),
        shapeAnimated.getY(),
        endFirstDimension,
        endSecondDimension,
        shapeAnimated.getColor(), true);
  }

  @Override
  public String displayAnimation(int tps) {
    return generateAnimationState(super.displayAnimation(tps), endTime / tps,
        shapeAnimated.getX(),
        shapeAnimated.getY(),
        endFirstDimension,
        endSecondDimension,
        shapeAnimated.getColor(), true);
  }

  @Override
  public String rectangleSVG(int tempo) {
    StringBuilder output = new StringBuilder("");

    output.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\" " +
            "attributeType=\"xml\" " +
            "begin=\"base.begin+%dms\" dur=\"%dms\" attributeName=\"width\"" +
            " from=\"%d\" to=\"%d\" fill=\"freeze\"/>\n",
        (startTime / tempo) * 1000,
        ((endTime - startTime) / tempo) * 1000,
        shapeAnimated.getX(),
        shapeAnimated.getX()));

    output.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\"" +
            " attributeType=\"xml\" " +
            "begin=\"base.begin+%dms\" dur=\"%dms\" attributeName=\"height\"" +
            " from=\"%d\" to=\"%d\" fill=\"freeze\"/>\n",
        (startTime / tempo) * 1000,
        ((endTime - startTime) / tempo) * 1000,
        shapeAnimated.getFirstDimension(),
        endFirstDimension));

    return output.toString();
  }

  @Override
  public String ellipseSVG(int tempo) {
    StringBuilder output = new StringBuilder("");

    output.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\" " +
            "attributeType=\"xml\" " +
            "begin=\"base.begin+%dms\" dur=\"%dms\" attributeName=\"rx\" from=\"%f\"" +
            " to=\"%f\" fill=\"freeze\"/>\n",
        (startTime / tempo) * 1000,
        ((endTime - startTime) / tempo) * 1000,
        shapeAnimated.getX(),
        shapeAnimated.getX()));

    output.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\"" +
            " attributeType=\"xml\" " +
            "begin=\"base.begin+%dms\" dur=\"%dms\" attributeName=\"ry\" from=\"%d\"" +
            " to=\"%d\" fill=\"freeze\"/>\n",
        (startTime / tempo) * 1000,
        ((endTime - startTime) / tempo) * 1000,
        shapeAnimated.getSecondDimension(),
        endSecondDimension));

    return output.toString();
  }

  @Override
  public AnimationResize copy() {
    AnimationResize copy = new AnimationResize(this.shapeAnimated, this.startTime, this.endTime,
        this.endFirstDimension, this.endSecondDimension);
    return copy;
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (!(other instanceof AnimationResize)) {
      return false;
    }
    AnimationResize that = (AnimationResize) other;
    return this.startTime == that.getStartTime() &&
        this.endTime == that.getEndTime() &&
        this.shapeAnimated.equals(that.shapeAnimated);
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
    int startWidth = shapeAnimated.getFirstDimension();
    int startHeight = shapeAnimated.getSecondDimension();

    double newWidth = tweenCalculator(startWidth, endFirstDimension, startTime, endTime, tick);
    double newHeight = tweenCalculator(startHeight, endSecondDimension, startTime, endTime, tick);

    int newWidthDimension = (int)Math.round(newWidth);
    int newHeightDimension = (int)Math.round(newHeight);
    shapeAnimated.setDimensions(newWidthDimension, newHeightDimension);
  }
}
