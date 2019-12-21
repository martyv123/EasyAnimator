package cs3500.easyanimator.model.classes.provider;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs3500.animator.adapters.AnimatorModelAdapter;
import cs3500.animator.adapters.SpriteAdapter;
import cs3500.animator.model.classes.Rectangle;
import cs3500.animator.model.interfaces.Shape;
import cs3500.animator.provider.model.AnimatorSprite;

import static org.junit.Assert.assertEquals;

/**
 * Contains the tests for the model adapter.
 */
public class AnimatorModelAdapterTests {
  AnimatorModelAdapter adapter;
  Shape rect;
  AnimatorSprite sprite;

  /**
   * Test fixture to keep testing data consistent.
   */
  @Before
  public void testFixture() {
    adapter = new AnimatorModelAdapter(null);
    rect = new Rectangle("r", 1, 1, 1, 1, 1, 1,
            Color.RED);
    sprite = new SpriteAdapter(rect);
  }

  @Test
  public void testAddSprite() {
    adapter.addSprite(1, sprite);
    assertEquals(sprite, adapter.getSpriteByName("r"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveSprite() {
    adapter.addSprite(1, sprite);
    adapter.removeSprite(1);
    adapter.getSpriteByName("r");
  }

  @Test(expected = IllegalArgumentException.class)
  public void getSpriteByName() {
    adapter.getSpriteByName("");
  }
}
