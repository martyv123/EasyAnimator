package cs3500.easyanimator.model.classes;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.classes.AnimationResize;
import cs3500.animator.model.classes.Rectangle;
import cs3500.animator.model.interfaces.Animation;
import cs3500.animator.model.interfaces.Shape;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

/**
 * To Hold testing for {@link AnimationResize}.
 */
public class AnimationResizeTests {

  private Animation resizeAnimation;
  private Shape rectangle;

  /**
   * To keep the testing data consistent for all tests.
   */
  @Before
  public void testFixture() {
    rectangle = new Rectangle("R", 50, 100, 200, 200,0,1,
        new Color(255, 0, 0));
    resizeAnimation = new AnimationResize(rectangle, 1, 10, 10,10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullShape() {
    new AnimationResize(null, 0, 10, 10,10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeStartTime() {
    new AnimationResize(rectangle, -1, 2, 10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeEndTime() {
    new AnimationResize(rectangle, 1, -2, 10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void endTimeBeforeStartTime() {
    new AnimationResize(rectangle, 5, 2, 10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void endFirstDimensionIs0() {
    new AnimationResize(rectangle, 1, 2,  0, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void endSecondDimensionIs0() {
    new AnimationResize(rectangle, 1, 2,  1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void endFirstDimensionIsNegative() {
    new AnimationResize(rectangle, 1, 2,  -4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void endSecondDimensionIsNegative() {
    new AnimationResize(rectangle, 1, 2,  1, -2);
  }

  @Test
  public void testToString() {
    String expected = "motion R 1 200 200 50 100 255 0 0 10 200 200 10 10 255 0 0\n";
    assertEquals(expected, resizeAnimation.toString());
  }

  @Test
  public void testGetShapeName() {
    assertEquals("R", resizeAnimation.getShapeName());
  }

  @Test
  public void testGetShape() {
    assertEquals(rectangle, resizeAnimation.getShape());
  }

  @Test
  public void testGetStartTime() {
    assertEquals(1, resizeAnimation.getStartTime());
  }

  @Test
  public void testGetEndTime() {
    assertEquals(10, resizeAnimation.getEndTime());
  }

  @Test
  public void testCopy() {
    AnimationResize copy = (AnimationResize) resizeAnimation.copy();
    assertEquals(resizeAnimation.getStartTime(), copy.getStartTime());
    assertEquals(resizeAnimation.getEndTime(), copy.getEndTime());
  }

  @Test
  public void testTOSVG() {
    assertEquals("<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\""
        + " begin=\"1000ms\" dur=\"9000ms\" attributeName=\"width\" from=\"200\" to=\"200\" "
        + "fill=\"freeze\"/>\n"
        + "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" begin=\"1000ms\""
        + " dur=\"9000ms\" attributeName=\"height\" from=\"50\" to=\"10\" fill=\"freeze\"/>\n",
        resizeAnimation.toSVG(1));
  }
}
