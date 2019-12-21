package cs3500.easyanimator.model.classes;

import cs3500.animator.model.classes.AnimationMove;
import cs3500.animator.model.classes.Ellipse;
import cs3500.animator.model.classes.Rectangle;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;

import cs3500.animator.model.interfaces.Animation;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

/**
 * Contains the tests for the Ellipse class.
 */
public class EllipseTests {

  private Ellipse c;
  private Ellipse oval;

  /**
   * To keep the testing data consistent throughout all the tests.
   */
  @Before
  public void testFixture() {
    c = new Ellipse("c", 3, 4, 0, 1, 0,1, Color.RED);
    oval = new Ellipse("oval", 2, 3, 5, 5, 1,20, new Color(0,1,0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeMajorRadius() {
    new Ellipse("x", -4, 5, 1, 2,0,1, Color.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeMinorRadius() {
    new Ellipse("x", 4, -5, 1, 2,0,1, Color.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void zeroMajorRadius() {
    new Ellipse("x", 0, 5, 1, 2, 0,1,Color.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void zeroMinorRadius() {
    new Ellipse("x", 4, 0, 1, 2,0,1, Color.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullName() {
    new Ellipse(null, 1, 1, 1, 1,0,1, Color.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyStringName() {
    new Ellipse("", 1, 1, 1, 1,0,1, Color.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullColor() {
    new Ellipse("hi", 1, 1, 1, 1,0,1, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullAnimation() {
    c.addAnimation(null);
  }

  @Test
  public void testGetFirstDimension() {
    assertEquals(3, c.getFirstDimension());
  }

  @Test
  public void testGetSecondDimension() {
    assertEquals(4, c.getSecondDimension());
  }

  @Test
  public void testGetColor() {
    assertEquals(Color.RED, c.getColor());
  }

  @Test
  public void testGetName() {
    assertEquals("c", c.getName());
  }

  @Test
  public void testGetCoordinates() {
    assertEquals(0, c.getX());
    assertEquals(1, c.getY());
  }

  @Test
  public void testGetAnimation() {
    AnimationMove move = new AnimationMove(c, 1, 1, 3, 2);
    c.addAnimation(move);
    ArrayList<Animation> testList = new ArrayList<Animation>();
    testList.add(move);
    // test below does not compile for some reason
    assertEquals(testList, c.getAnimations());
  }

  @Test
  public void testSetColor() {
    assertEquals(Color.RED, c.getColor());
    c.setColor(Color.BLACK);
    assertEquals(Color.BLACK, c.getColor());
  }

  @Test
  public void testSetPosition() {
    assertEquals(0, c.getX());
    assertEquals(1, c.getY());
    c.setX(10);
    c.setY(11);
    assertEquals(10, c.getX());
    assertEquals(11, c.getY());
  }

  @Test
  public void testSetDimensions() {
    assertEquals(3, c.getFirstDimension());
    assertEquals(4, c.getSecondDimension());
    c.setDimensions(10, 11);
    assertEquals(10, c.getFirstDimension());
    assertEquals(11, c.getSecondDimension());
  }

  @Test
  public void testAddAnimation() {
    AnimationMove move = new AnimationMove(c, 1, 1, 3, 2);
    c.addAnimation(move);
    assertTrue(c.getAnimations().contains(move));
  }

  @Test
  public void testToString() {
    assertEquals("shape c ellipse\n", c.toString());
  }

  @Test
  public void testCopy() {
    assertEquals(c, c.copy());
  }

  @Test
  public void testEquals() {
    Ellipse a = new Ellipse("a", 4, 5, 0, 0,0,1, Color.RED);
    Ellipse b = new Ellipse("b", 4, 5, 0, 0, 0,1,Color.RED);
    Ellipse a2 = new Ellipse("a", 6, 7, 8, 9,0,1, Color.RED);
    Rectangle a3 = new Rectangle("a", 6, 7, 8, 9, 0,1,Color.RED);
    assertTrue(a.equals(a2));
    assertFalse(a.equals(b));
    assertFalse(a2.equals(a3));
  }

  @Test
  public void testHashCode() {
    Ellipse x = new Ellipse("x", 4, 5, 0, 0,0,1, Color.RED);
    Ellipse y = new Ellipse("y", 4, 5, 0, 0,0,1, Color.BLUE);
    Ellipse z = new Ellipse("x", 5, 6, 7, 9, 0,1,Color.YELLOW);
    assertNotEquals(x.hashCode(), y.hashCode());
    assertEquals(x.hashCode(), z.hashCode());
  }

  @Test
  public void testTOSVG() {
    assertEquals("<ellipse xmlns=\"http://www.w3.org/2000/svg\" id=\"oval\" cx=\"5\" "
        + "cy=\"5\" rx=\"2\" ry=\"3\" fill=\"rgb(0,1,0)\" visibility=\"hidden\">\n"
        + "<set attributeType=\"xml\" begin=\"1000ms\" attributeName=\"visibility\" "
        + "to=\"visible\"/>\n"
        + "<set attributeType=\"xml\" begin=\"20000ms\" attributeName=\"visibility\" "
        + "to=\"hidden\"/>\n"
        + "</ellipse>\n", oval.toSVG(1, true));
  }
}
