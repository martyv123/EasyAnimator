package cs3500.animator.adapters;

import cs3500.animator.provider.model.BasicShapeProperties;
import cs3500.animator.provider.model.BasicShapes;
import cs3500.animator.provider.model.ROAnimatorSprite;
import java.util.List;
import java.util.TreeMap;

/**
 * Adapter class for provider's ROAnimator interface. The Provider's Sprite is equivalent, albeit
 * a more advanced, version of our Shapes. This delegates to the given SpriteAdapter.
 * This is to restrict the view access of any mutation abilities of SpriteAdapter, and by
 * implication, Provider's sprites.
 *
 */
public class ROAnimatorSpriteAdapter implements
    ROAnimatorSprite<Integer, BasicShapeProperties, BasicShapes> {

  private SpriteAdapter spriteAdapter;

  /**
   * Constructs an instance of a ROAnimatorSpriteAdapter from the given spriteAdapter.
   * This will be exactly the same as a sprite adapter but it will restrict access and
   * give no mutation methods.
   *
   * @param spriteAdapter the sprite adapter that needs to be a read only copy.
   */
  public ROAnimatorSpriteAdapter(SpriteAdapter spriteAdapter) {
    this.spriteAdapter = spriteAdapter;
  }

  @Override
  public Integer getVal(BasicShapeProperties property, int time)
      throws IllegalStateException, IllegalArgumentException {
    return spriteAdapter.getVal(property, time);
  }

  @Override
  public List<Integer> getTemporalVertices() {
    return spriteAdapter.getTemporalVertices();
  }

  @Override
  public String getName() {
    return spriteAdapter.getName();
  }

  @Override
  public BasicShapes getType() {
    return spriteAdapter.getType();
  }

  @Override
  public boolean isVisible(int time) {
    return spriteAdapter.isVisible(time);
  }

  @Override
  public TreeMap<Integer, List<Integer>> getKeyframes() {
    return spriteAdapter.getKeyframes();
  }
}
