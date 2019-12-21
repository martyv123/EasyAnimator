package cs3500.animator.model.classes;

import cs3500.animator.model.interfaces.Shape;
import java.util.Objects;

/**
 * To hold the animation information about changing a {@link Shape} position.
 */
public class AnimationMove extends AAnimation {

  private final int endX;
  private final int endY;

  /**
   * Constructs a new instance of AnimationMove.
   *
   * @param shapeAnimated the shape the animation is manipulating.
   * @param startTime the start time the animation should begin.
   * @param endTime the end time the animation should finish.
   * @param endX the ending x coordinate of the shape being manipulated.
   * @param endY the ending y coordinate of the shape being manipulated.
   * @throws IllegalArgumentException if startTime or endTime are negative numbers, if the endTime
   *                                  is before the startTime, if shape being manipulated is null.
   */
  public AnimationMove(Shape shapeAnimated, int startTime, int endTime, int endX, int endY)
      throws IllegalArgumentException {
    super(shapeAnimated, startTime, endTime);
    this.endX = endX;
    this.endY = endY;
  }

  @Override
  public String displayAnimation(int tps) {
    return generateAnimationState(super.displayAnimation(tps), endTime / tps,
        endX,
        endY,
        shapeAnimated.getFirstDimension(),
        shapeAnimated.getSecondDimension(),
        shapeAnimated.getColor(), true);
  }

  @Override
  public String toString() {
    return generateAnimationState(super.toString(), endTime,
        endX,
        endY,
        shapeAnimated.getFirstDimension(),
        shapeAnimated.getSecondDimension(),
        shapeAnimated.getColor(), true);
  }

  @Override
  public String rectangleSVG(int tempo) {

    StringBuilder output = new StringBuilder("");
    output.append(
        String.format("<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" " +
                "begin=\"base.begin+%dms\" dur=\"%dms\" attributeName=\"x\" from=\"%d\" to=\"%d\""
                + " fill=\"freeze\"/>\n",
            startTime / tempo * 1000,
            (endTime - startTime / tempo) * 1000,
            shapeAnimated.getX(),
            endX) +
            String.format("<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" " +
                    "begin=\"base.begin+%dms\" dur=\"%dms\" attributeName=\"y\" from=\"%d\" "
                    + "to=\"%d\" fill=\""
                    + "freeze\"/>\n",
                startTime / tempo * 1000,
                (endTime - startTime / tempo) * 1000,
                shapeAnimated.getY(),
                endY));
    return output.toString();
  }

  @Override
  public String ellipseSVG(int tempo) {
    StringBuilder output = new StringBuilder("");

    output.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\"" +
            " attributeType=\"xml\" " +
            "begin=\"base.begin%dms\" dur=\"%dms\" attributeName=\"cx\"" +
            " from=\"%d\" to=\"%d\" fill=\"freeze\"/>\n",
        startTime / tempo * 1000,
        (endTime - startTime) / tempo * 1000,
        shapeAnimated.getX(),
        endX));
    output.append(String.format("<animate xmlns=\"http://www.w3.org/2000/svg\" " +
            "attributeType=\"xml\" " +
            "begin=\"base.begin+%dms\" dur=\"%dms\" attributeName=\"cy\" from=\"%d\" " +
            "to=\"%d\" fill=\"freeze\"/>\n",
        startTime / tempo * 1000,
        (endTime - startTime) / tempo * 1000,
        shapeAnimated.getY(),
        endX));

    return output.toString();
  }

  @Override
  public AnimationMove copy() {
    AnimationMove copy = new AnimationMove(this.shapeAnimated, this.startTime, this.endTime,
        this.endX, this.endY);
    return copy;
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (!(other instanceof AnimationMove)) {
      return false;
    }
    AnimationMove that = (AnimationMove) other;
    return this.startTime == that.getStartTime() &&
        this.endTime == that.getEndTime() &&
        this.shapeAnimated.equals(that.shapeAnimated);
  }

  /**
   * Returns the hashcode of the object. Names of shapes are unique, not ignoring case.
   *
   * @return an integer representing the hashcode
   */
  public int hashCode() {
    return Objects.hash(this.shapeAnimated, startTime, endTime);
  }

  @Override
  public void apply(int tick) {
    int startX = this.getShape().getX();
    int startY = this.getShape().getY();

    double newX = tweenCalculator(startX, endX, startTime, endTime, tick);
    double newY = tweenCalculator(startY, endY, startTime, endTime, tick);

    int newXCoord = (int) Math.round(newX);
    int newYCoord = (int) Math.round(newY);

    getShape().setX(newXCoord);
    getShape().setY(newYCoord);
  }
}



