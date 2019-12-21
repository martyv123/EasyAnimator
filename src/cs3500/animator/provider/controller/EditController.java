package cs3500.animator.provider.controller;

import java.util.List;

/**
 * The Editor controller allows the user to edit the animation of the model but does not give direct
 * access for the user to mutate the model.
 *
 * @param <T> the return type of properties in the model of this controller
 * @param <S> the type of identifier used to specify properties
 * @param <R> the type of identifier used to specify types of sprites
 */
public interface EditController<T, S, R> {
  /**
   * Adds a shape to the animation.
   *
   * @param layer the layer at which to add the shape
   * @param type  the type of shape to add
   * @param name  the name of the shape to add
   */
  void addShape(int layer, R type, String name);

  /**
   * Moves a shape from one layer to another, by its layer.
   *
   * @param fromLayer the layer to move the shape from
   * @param toLayer   the layer to move the shape to
   */
  void moveShape(int fromLayer, int toLayer);

  /**
   * Moves a shape from one layer to another, by its name.
   *
   * @param name    the name of the shape to move
   * @param toLayer the layer to move the shape to
   * @throws IllegalArgumentException if the name is not found
   */
  void moveShape(String name, int toLayer) throws IllegalArgumentException;

  /**
   * Removes the shape at the given layer.
   *
   * @param layer the layer at which to remove the shape
   */
  void removeShape(int layer);

  /**
   * Removes a shape by its name.
   * @param name the name of the shape to remove
   * @throws IllegalArgumentException if the name is not found
   */
  void removeShape(String name) throws IllegalArgumentException;

  /**
   * Adds a keyframe to the desired shape. If possible, the preset values of the new keyframe will
   * be based on interpolated values such that adding a keyframe should have no discernible effect
   * on the end product (save for rounding that may occur depending on the data type). If the new
   * keyframe is outside the range of existing keyframes, it is left to implementations of this
   * interface to determine the behavior, though such behavior should always be "defined," in that
   * exceptions should not be thrown.
   *
   * @param name The name of the shape of which to add a keyframe
   * @param time the time at which to add the keyframe
   */
  void addKeyFrame(String name, int time);

  /**
   * Edits the keyframe at the given time. If no keyframe exists at that time, it will be created.
   *
   * @param name   The name of the shape whose keyframe is to be edited
   * @param time   the time at which the keyframe to be edited is
   * @param values the values for which to change the values of the existing keyframe to.
   */
  void editKeyFrame(String name, int time, List<T> values);

  /**
   * Deletes the keyframe at the given time. If no keyframe exists at that time, this has no
   * effect.
   *
   * @param name the name of the shape for which the given keyframe is to be deleted
   * @param time the time at which the keyframe to be deleted is
   */
  void deleteKeyFrame(String name, int time);
}
