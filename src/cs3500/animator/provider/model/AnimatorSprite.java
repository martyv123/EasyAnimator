package cs3500.animator.provider.model;

import java.util.List;
import java.util.TreeMap;

/**
 * Provides methods for interacting with individual sprites in a {@link AnimatorModel}. Implementors
 * of this interface are strongly recommended to instead extend {@link AnimatorSprite}, as
 * that class provides a framework the methods herein.
 *
 * @param <T> the type of the values returned by properties of this sprite
 * @param <S> the type of identifier to use to specify properties within this sprite
 * @param <R> the type of identifier to specify between different types of sprites
 */
public interface AnimatorSprite<T, S, R> extends ROAnimatorSprite<T, S, R> {

  /**
   * Returns a builder class to assist in defining properties for a common type. Will create a new
   * builder if one has not been made before, or the existing one otherwise. Uses the {@code
   * propertyIdentifiers} and {@code interable} defined in the constructor for this abstract class.
   *
   * @return the {@link MotionBuilder}
   */
  MotionBuilder<T, S, R> setMotions();

  /**
   * Update the animation of this sprite based on the given keyframes. The entire existing animation
   * of this sprite will be reset and replaced by the value of the keyframes when interpolated out
   * to motions between themselves.
   *
   * @param keyframes the keyframes to set
   * @return this {@link AnimatorSprite} for method chaining
   */
  AnimatorSprite<T, S, R> setKeyframes(TreeMap<Integer, List<T>> keyframes);
}