package cs3500.animator.provider.model;

/**
 * Represents an animation that can be mutated.
 *
 * @param <T> the return type of properties in this
 * @param <S> the type of identifier used to specify properties
 * @param <R> the type of identifier used to specify types of sprites
 */
public interface AnimatorModel<T, S, R> extends ROAnimatorModel<T, S, R> {
  /**
   * Adds a {@link AnimatorSprite} with the specified layer to the model builder.
   *
   * @param layer the layer to set the sprite at
   * @param toAdd the sprite to add
   * @return this {@link AnimatorModel}, for method chaining
   * @throws IllegalArgumentException if the layer given is already occupied
   */
  AnimatorModel<T, S, R> addSprite(int layer, AnimatorSprite<T, S, R> toAdd)
      throws IllegalArgumentException;

  /**
   * Moves a sprite from its current layer to a new unoccupied layer.
   *
   * @param from the occupied layer to make empty by moving the sprite
   * @param to   the unoccipied layer to make full by moving the sprite
   * @return this {@link AnimatorModel}, for method chaining
   * @throws IllegalArgumentException if the layer to move from isn't occupied or if the layer to
   *                                  move to is occupied
   */
  AnimatorModel<T, S, R> moveSpriteLayer(int from, int to)
      throws IllegalArgumentException;

  /**
   * Deletes a sprite from its layer, making the layer empty.
   *
   * @param toRemove the occupied layer to clear by deleting the contained sprite
   * @return this {@link AnimatorModel}, for method chaining
   * @throws IllegalArgumentException if the layer to clear is already empty
   */
  AnimatorModel<T, S, R> removeSprite(int toRemove) throws IllegalArgumentException;

  /**
   * Set new data for the canvas to use for this model.
   *
   * @param xLeft        the x coordinate of the left edge to set
   * @param yTop         the y coordinate of the top edge to set
   * @param canvasWidth  the width to set, to be checked for non-negativity upon building
   * @param canvasHeight the height to set, to be checked for non-negativity upon building
   * @return this {@link AnimatorModel}, for method chaining
   */
  AnimatorModel<T, S, R> setCanvas(int xLeft, int yTop, int canvasWidth,
      int canvasHeight);

  /**
   * Get a mutable {@link AnimatorSprite} based on its name. Will throw an {@link
   * IllegalArgumentException} if there is no sprite matching that name.
   *
   * @param name the name to get the sprite for
   * @return the {@link AnimatorSprite}
   * @throws IllegalArgumentException if a sprite could not be found with that name
   */
  AnimatorSprite<T, S, R> getSpriteByName(String name) throws IllegalArgumentException;

}
