package cs3500.easyanimator.model.classes;

import static junit.framework.TestCase.assertEquals;

import cs3500.animator.model.classes.Bounds;
import org.junit.Test;

/**
 * TO test the Bounds implementation.
 */
public class BoundsTests {

  Bounds bounds = new Bounds();
  Bounds customBounds = new Bounds(1,2,3,4);

  @Test
  public void testGetX() {
    assertEquals(0, bounds.getX());
  }

  @Test
  public void testGetY() {
    assertEquals(0, bounds.getY());
  }

  @Test
  public void testGetWidth() {
    assertEquals(100, bounds.getWidth());
  }

  @Test
  public void testGetHeight() {
    assertEquals(100, bounds.getHeight());
  }

  @Test
  public void testToString() {
    assertEquals("canvas 1 2 3 4", customBounds.toString());
  }

}
