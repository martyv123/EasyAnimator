package cs3500.easyanimator.model.classes;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.classes.AnimationColorChange;
import cs3500.animator.model.classes.Rectangle;
import cs3500.animator.model.interfaces.Animation;
import cs3500.animator.model.interfaces.Shape;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

/**
 * To Hold testing for {@link AnimationColorChange}.
 */
public class AnimationColorChangeTests {

  private Animation colorChangeAnimation;
  private Shape rectangle;

  /**
   * To keep the testing data consistent for all tests.
   */
  @Before
  public void testFixture() {
    rectangle = new Rectangle("r", 3, 4, 0, 1, 0,1,new Color(255,0,0));
    colorChangeAnimation = new AnimationColorChange(rectangle, 0, 10,
        new Color(1,1,1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullShape() {
    new AnimationColorChange(null, 0, 10, Color.BLACK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeStartTime() {
    new AnimationColorChange(rectangle, -1,2, Color.BLACK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeEndTime() {
    new AnimationColorChange(rectangle, 1,-2, Color.BLACK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void endTimeBeforeStartTime() {
    new AnimationColorChange(rectangle, 5,2,Color.BLACK);
  }

  @Test
  public void testToString() {
    String expected = "motion r 0 0 1 3 4 255 0 0 10 0 1 3 4 1 1 1\n";
    assertEquals(expected, colorChangeAnimation.toString());
  }

  @Test
  public void testGetShape() {
    assertEquals(rectangle, colorChangeAnimation.getShape());
  }

  @Test
  public void testGetShapeName() {
    assertEquals("r", colorChangeAnimation.getShapeName());
  }

  @Test
  public void testGetStartTime() {
    assertEquals(0, colorChangeAnimation.getStartTime());
  }

  @Test
  public void testGetEndTime() {
    assertEquals(10, colorChangeAnimation.getEndTime());
  }

  @Test
  public void testCopy() {
    AnimationColorChange copy = (AnimationColorChange) colorChangeAnimation.copy();
    assertEquals(colorChangeAnimation.getStartTime(), copy.getStartTime());
    assertEquals(colorChangeAnimation.getEndTime(), copy.getEndTime());
  }

  @Test
  public void testTOSVG() {
    assertEquals("<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" "
        + "begin=\"0ms\" dur=\"10000ms\" attributeName=\"fill\" from=\"255\" to=\"0\" fill=\""
        + "freeze\"/>\n", colorChangeAnimation.toSVG(1));
  }
}
