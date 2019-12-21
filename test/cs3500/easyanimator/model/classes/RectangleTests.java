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
 * Contains the tests for the Rectangle class.
 */
public class RectangleTests {
  private Rectangle r;
  private Animation a;

  /**
   * To keep the testing data consistent throughout all the tests.
   */
  @Before
  public void testFixture() {
    r = new Rectangle("r", 3, 4, 0, 1, 0,1, Color.RED);
    a = new AnimationMove(r, 0, 1, 10,10);
  }


  @Test(expected = IllegalArgumentException.class)
  public void negativeLength() {
    new Rectangle("x", -4, 5, 1, 2, 0,1,Color.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeWidth() {
    new Rectangle("x", 4, -5, 1, 2,0,1, Color.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void zeroLength() {
    new Rectangle("x", 0, 5, 1, 2, 0,1,Color.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void zeroWidth() {
    new Rectangle("x", 4, 0, 1, 2, 0,1,Color.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullName() {
    new Rectangle(null, 1, 1, 1, 1,0,1, Color.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyStringName() {
    new Rectangle("", 1, 1, 1, 1, 0,1,Color.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullColor() {
    new Rectangle("hi", 1, 1, 1, 1, 0,1,null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullAnimation() {
    r.addAnimation(null);
  }

  @Test
  public void testGetFirstDimension() {
    assertEquals(3, r.getFirstDimension());
  }

  @Test
  public void testGetSecondDimension() {
    assertEquals(4, r.getSecondDimension());
  }

  @Test
  public void testGetColor() {
    assertEquals(Color.RED, r.getColor());
  }

  @Test
  public void testGetName() {
    assertEquals("r", r.getName());
  }

  @Test
  public void testGetCoordinates() {
    assertEquals(0, r.getX());
    assertEquals(1, r.getY());
  }

  @Test
  public void testGetAnimation() {
    AnimationMove move = new AnimationMove(r, 1, 1, 1, 2);
    r.addAnimation(move);
    ArrayList<Animation> testList = new ArrayList<Animation>();
    testList.add(move);
    // test below does not compile for some reason
    assertEquals(testList, (r.getAnimations()));
  }

  @Test
  public void testSetColor() {
    assertEquals(Color.RED, r.getColor());
    r.setColor(Color.BLACK);
    assertEquals(Color.BLACK, r.getColor());
  }

  @Test
  public void testSetPosition() {
    assertEquals(0, r.getX());
    assertEquals(1, r.getY());
    r.setX(10);
    r.setY(11);
    assertEquals(10, r.getX());
    assertEquals(11, r.getY());
  }

  @Test
  public void testSetDimensions() {
    assertEquals(3, r.getFirstDimension());
    assertEquals(4, r.getSecondDimension());
    r.setDimensions(10, 11);
    assertEquals(10, r.getFirstDimension());
    assertEquals(11, r.getSecondDimension());
  }

  @Test
  public void testAddAnimation() {
    AnimationMove move = new AnimationMove(r, 1, 1, 1, 2);
    r.addAnimation(move);
    assertTrue(r.getAnimations().contains(move));
  }

  @Test
  public void testToString() {
    assertEquals("shape r rectangle\n" + "motion r 0 0 1 3 4 255 0 0 1 10 10 3 4 255 0 0\n"
            , r.toString());
  }

  @Test
  public void testCopy() {
    assertEquals(r, r.copy());
  }

  @Test
  public void testEquals() {
    Rectangle a = new Rectangle("a", 4, 5, 0, 0,0,1, Color.RED);
    Rectangle b = new Rectangle("b", 4, 5, 0, 0, 0,1,Color.RED);
    Rectangle a2 = new Rectangle("a", 6, 7, 8, 9, 0,1,Color.RED);
    Ellipse a3 = new Ellipse("a", 6, 7, 8, 9,0,1, Color.RED);
    assertTrue(a.equals(a2));
    assertFalse(a.equals(b));
    assertFalse(a2.equals(a3));
  }

  @Test
  public void testHashCode() {
    Rectangle x = new Rectangle("x", 4, 5, 0, 0, 0,1,Color.RED);
    Rectangle y = new Rectangle("y", 4, 5, 0, 0,0,1, Color.BLUE);
    Rectangle z = new Rectangle("x", 5, 6, 7, 9, 0,1,Color.YELLOW);
    assertNotEquals(x.hashCode(), y.hashCode());
    assertEquals(x.hashCode(), z.hashCode());
  }

  @Test
  public void testTOSVG() {
    assertEquals("<rect xmlns=\"http://www.w3.org/2000/svg\" id=\"r\" x=\"0\" y=\"1\" "
        + "width=\"3\" height=\"4\" fill=\"rgb(255,0,0)\" visibility=\"hidden\">\n"
        + "<set attributeType=\"xml\" begin=\"0ms\" attributeName=\"visibility\" to=\"visible\"/>\n"
        + "<set attributeType=\"xml\" begin=\"1000ms\" attributeName=\"visibility\" t"
        + "o=\"hidden\"/>\n"
        + "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" begin=\"0ms\""
        + " dur=\"1000ms\" attributeName=\"x\" from=\"0\" to=\"10\" fill=\"freeze\"/>\n"
        + "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" begin=\"0ms\" "
        + "dur=\"1000ms\" attributeName=\"y\" from=\"1\" to=\"10\" fill=\"freeze\"/>\n"
        + "</rect>\n", r.toSVG(1, true));
  }

  @Test
  public void testGenerateProperSVG() {
    assertEquals("<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\""
        + " begin=\"0ms\" dur=\"1000ms\" attributeName=\"x\" from=\"0\" to=\"10\" "
        + "fill=\"freeze\"/>\n"
        + "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" begin=\"0ms\" "
        + "dur=\"1000ms\" attributeName=\"y\" from=\"1\" to=\"10\" fill=\"freeze\"/>\n",
        r.generateProperSVG(a, 4));
  }

}
