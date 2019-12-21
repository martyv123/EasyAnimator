package cs3500.easyanimator.model.classes;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.classes.AnimationMove;
import cs3500.animator.model.classes.Rectangle;
import cs3500.animator.model.interfaces.Animation;
import cs3500.animator.model.interfaces.Shape;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

/**
 * Contains the tests for the AnimationMove class.
 */
public class AnimationMoveTests {

  private Animation animationMove;
  private Shape rectangle;

  /**
   * To keep the testing data consistent for all tests.
   */
  @Before
  public void testFixture() {
    rectangle = new Rectangle("r", 3, 4, 0, 1, 0,100, Color.RED);
    animationMove = new AnimationMove(rectangle, 0, 10, 10,10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullShape() {
    new AnimationMove(null, 1,2,1,1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeStartTime() {
    new AnimationMove(rectangle, -1,2,1,1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeEndTime() {
    new AnimationMove(rectangle, 1,-2,1,1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void endTimeBeforeStartTime() {
    new AnimationMove(rectangle, 5,2,1,1);
  }

  @Test
  public void testToString() {
    String expected = "motion r 0 0 1 3 4 255 0 0 10 10 10 3 4 255 0 0\n";
    assertEquals(expected, animationMove.toString());
  }

  @Test
  public void testGetShapeName() {
    assertEquals("r", animationMove.getShapeName());
  }

  @Test
  public void testGetShape() {
    assertEquals(rectangle, animationMove.getShape());
  }

  @Test
  public void testGetStartTime() {
    assertEquals(0,animationMove.getStartTime());
  }

  @Test
  public void testGetEndTime() {
    assertEquals(10, animationMove.getEndTime());
  }

  @Test
  public void testCopy() {
    AnimationMove copy = (AnimationMove) animationMove.copy();
    assertEquals(animationMove.getStartTime(), copy.getStartTime());
    assertEquals(animationMove.getEndTime(), copy.getEndTime());
  }

  @Test
  public void testTOSVG() {
    assertEquals("<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" "
        + "begin=\"0ms\" dur=\"10000ms\" attributeName=\"x\" from=\"0\" to=\"10\" "
        + "fill=\"freeze\"/>\n"
        + "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" begin=\"0ms\""
        + " dur=\"10000ms\" attributeName=\"y\" from=\"1\" to=\"10\" fill=\"freeze\"/>\n",
        animationMove.toSVG(1));
  }
}
