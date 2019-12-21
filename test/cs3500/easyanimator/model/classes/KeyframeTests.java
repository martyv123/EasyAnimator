package cs3500.easyanimator.model.classes;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs3500.animator.model.classes.Keyframe;
import cs3500.animator.model.classes.Rectangle;
import cs3500.animator.model.interfaces.Shape;

import static org.junit.Assert.assertEquals;

/**
 * Contains the tests for the Keyframe class.
 */
public class KeyframeTests {
  Shape rect;
  Keyframe kf;

  /**
   * Test fixture to keep testing data consistent.
   */
  @Before
  public void testFixture() {
    rect = new Rectangle("r", 1, 1, 1, 1, 0, 1,
            Color.RED);
    kf = new Keyframe(rect, 1, 1, 1, 1, 1, 1, 0, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullShape() {
    Keyframe kf = new Keyframe(null, 1, 1, 1, 1, 1, 1, 1,
            1, 1);
  }

  @Test
  public void testSetX() {
    assertEquals(1, kf.getX());
    kf.setX(2);
    assertEquals(2, kf.getX());
  }

  @Test
  public void testSetY() {
    assertEquals(1, kf.getY());
    kf.setY(2);
    assertEquals(2, kf.getY());
  }

  @Test
  public void testSetWidth() {
    assertEquals(1, kf.getWidth());
    kf.setWidth(2);
    assertEquals(2, kf.getWidth());
  }

  @Test
  public void testSetHeight() {
    assertEquals(1, kf.getHeight());
    kf.setHeight(2);
    assertEquals(2, kf.getHeight());
  }

  @Test
  public void testSetRed() {
    assertEquals(1, kf.getRed());
    kf.setRed(2);
    assertEquals(2, kf.getRed());
  }

  @Test
  public void testSetGreen() {
    assertEquals(1, kf.getGreen());
    kf.setGreen(2);
    assertEquals(2, kf.getGreen());
  }

  @Test
  public void testSetBlue() {
    assertEquals(1, kf.getBlue());
    kf.setBlue(2);
    assertEquals(2, kf.getBlue());
  }

  @Test
  public void testGetShape() {
    assertEquals(rect, kf.getShape());
  }

  @Test
  public void getStartTime() {
    assertEquals(1, kf.getStartTime());
  }

  @Test
  public void getEndTime() {
    assertEquals(1, kf.getEndTime());
  }
}
