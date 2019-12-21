package cs3500.animator.provider.model;

import java.util.List;
import java.util.TreeMap;

/**
 * Represents a sprite that can only be accessed, not mutated.
 *
 * @param <T> the return type of properties in this
 * @param <S> the type of identifier used to specify properties
 * @param <R> the type of identifier used to specify types of sprites
 */
public interface ROAnimatorSprite<T, S, R> {
  /**
   * <p>Gets a value from the specified property at the given time. The type {@link S} is used to
   * match to the property to set, for example, a {@link String} name.</p>
   *
   * <p>If the properties have not been set when this method is called, an {@link
   * IllegalStateException} will be thrown.</p>
   *
   * <p>If the specification given does not match an actual property, an {@link
   * IllegalArgumentException} will be thrown.</p>
   *
   * @param property the name of the property to access
   * @param time     the time to pass as the input
   * @return the value of type {@link T} of the property at the given time
   */
  T getVal(S property, int time) throws IllegalStateException, IllegalArgumentException;

  /**
   * Returns a list of the temporal vertices applicable to the properties of this sprite. The list
   * is guaranteed to be sorted from least to greatest.
   *
   * @return the {@link List}
   */
  List<Integer> getTemporalVertices();

  /**
   * Returns the name of this sprite as a String.
   *
   * @return the name
   */
  String getName();

  /**
   * Returns the type of this sprite as a {@link R}.
   *
   * @return the type
   */
  R getType();

  /**
   * Determine if this shape is "visible" at the given time, which is defined as being within the
   * range of time intervals that properties are defined for.
   *
   * @param time the time to check
   * @return true if the shape is visible, false otherwise
   */
  boolean isVisible(int time);

  /**
   * Return the keyframes of this sprite. Such keyframes are given in a {@link TreeMap} type to be
   * transparent as to how the keyframes are ordered. Each entry is from {@link Integer} of the
   * temporal vertex to a {@link List} of type {@link T} of the properties in the natural ordering
   * of type parameter {@link S}.
   *
   * @return the keyframes as a map
   */
  TreeMap<Integer, List<T>> getKeyframes();
}
