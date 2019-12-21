package cs3500.easyanimator.model.classes.provider;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;

import cs3500.animator.adapters.SpriteAdapter;
import cs3500.animator.model.classes.Rectangle;
import cs3500.animator.model.interfaces.Shape;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Contains the tests for the Sprite Adapter.
 */
public class SpriteAdapterTests {
  Shape rect;
  SpriteAdapter sprite;

  /**
   * Consistently initializes the test data.
   */
  @Before
  public void testFixture() {
    rect = new Rectangle("r", 1, 1, 1, 1, 1, 1,
            Color.RED);
    sprite = new SpriteAdapter(rect);
  }

  @Test
  public void testGetTemporalVertices() {
    assertEquals(new ArrayList<Integer>().add(1), sprite.getTemporalVertices());
  }


  @Test
  public void testIsVisible() {
    assertFalse(sprite.isVisible(1));
    assertTrue(sprite.isVisible(1));
  }

  @Test
  public void testGetName() {
    assertEquals("r", sprite.getName());
  }
}
