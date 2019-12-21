package cs3500.animator.provider.model;

import java.util.List;

/**
 * Represents a post-instantiation builder to assemble motions for the sprite that created this.
 *
 * @param <T> the return type of the properties of the parent sprite
 * @param <S> the type of identifier used to specify properties
 * @param <R> the type of identifier used to specify sprite types
 */
public interface MotionBuilder<T, S, R> {
  /**
   * <p>Add a definition to each property being created by providing a start and end value for
   * each and a start time and end time for all. These given values should come in pairs in the
   * order that the properties were defined. For example, if the identifier type {@link S} was a
   * {@link String} and this builder was creating properties {@code "x"} and {@code "y"}, this
   * method would expect the following arguments: {@code (int tStart, int tEnd, T xStart, T xEnd, T
   * yStart, T yEnd)}.</p>
   *
   * <p>If the given arguments have the incorrect parity, an {@link IllegalArgumentException}
   * will be thrown.</p>
   *
   * @param tStart the start of the interval
   * @param tEnd   the end of the interval
   * @param values the values to set for each property for the interval
   * @return this {@link MotionBuilder}, for method chaining
   * @throws IllegalArgumentException if the argument parity is incorrect
   */
  MotionBuilder<T, S, R> addDef(int tStart, int tEnd, T... values)
      throws IllegalArgumentException;

  /**
   * Identical to {@link MotionBuilder#addDef(int, int, Object[])}, but
   * takes in values via a {@link List} rather than as an arbitrary number of arguments.
   *
   * @param tStart the start of the interval
   * @param tEnd   the end of the interval
   * @param values the values to set for each property for the interval
   * @return this {@link MotionBuilder}, for method chaining
   * @throws IllegalArgumentException if the argument parity is incorrect
   */
  MotionBuilder<T, S, R> addDef(int tStart, int tEnd, List<T> values)
      throws IllegalArgumentException;

  /**
   * Return a cleared, reset version of this motion builder. It refers to the same sprite, builds
   * the same properties, and interpolates the same way, but maintains none of the other data that
   * has been built in this after constructing.
   *
   * @return a fresh {@link MotionBuilder}
   */
  MotionBuilder<T, S, R> reset();

  /**
   * Have each stored builder construct and add the result to the stored calling {@link
   * AnimatorSprite}.
   */
  AnimatorSprite<T, S, R> finish();
}
