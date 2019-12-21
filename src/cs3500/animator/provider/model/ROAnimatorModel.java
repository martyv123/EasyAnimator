package cs3500.animator.provider.model;

import java.util.Map;
import java.util.stream.Stream;

/**
 * Represents a model that can only be accessed, not mutated.
 *
 * @param <T> the return type of properties in this
 * @param <S> the type of identifier used to specify properties
 * @param <R> the type of identifier used to specify types of sprites
 */
public interface ROAnimatorModel<T, S, R> {
  /**
   * <p>Provides a 4-integer-long array of the data associated with the canvas for this model. The
   * data should be interpeted as follows, with the first item being at index zero:</p>
   * <ol>
   * <li>The x-coordinate of the left of the canvas, given that (0,0) is located in the
   * top-left</li>
   * <li>The y-coordinate of the top of the canvas, given that (0,0) is located in the
   * top-left</li>
   * <li>The width of the canvas</li>
   * <li>The height of the canvas</li>
   * </ol>
   * <p>If the data for to return here has not been initialized, this method <b>will not</b>
   * attempt to calculate a canvas by itself due to being blind to the interpretations of the
   * arbitrary fields of each sprite. Instead, {@code null} will be returned for that value in the
   * array. The array is still guaranteed to be four elements long, but if the canvas is completely
   * uninitialized, each of those four elements will be {@code null}.</p>
   *
   * @return the {@link Integer} array as given above
   */
  Integer[] getCanvas();

  /**
   * Return an immutable stream of sprites in the order of their layer, from least to greatest.
   *
   * @return the {@link Stream} of type {@link ROAnimatorSprite}
   */
  Stream<ROAnimatorSprite<T, S, R>> getROSprites();

  /**
   * Return an immutable stream of sprites in the order of their layer, from least to greatest.
   * Returns the sprites in a pair that provides their layer.
   *
   * @return the {@link Stream} of type {@link Map.Entry} containing {@link Integer} layers and
   * {@link ROAnimatorSprite}
   */
  Stream<Map.Entry<Integer, ROAnimatorSprite<T, S, R>>> getLayerROSprites();

  /**
   * Get a immutable {@link AnimatorSprite} based on its name. Will throw an {@link
   * IllegalArgumentException} if there is no sprite matching that name.
   *
   * @param name the name to get the sprite for
   * @return the {@link ROAnimatorSprite}
   * @throws IllegalArgumentException if a sprite could not be found with that name
   */
  ROAnimatorSprite<T, S, R> getROSpriteByName(String name) throws IllegalArgumentException;
}
