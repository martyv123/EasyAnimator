package cs3500.animator.provider.controller;

/**
 * Interface extension of {@link AnimatorController} that allows spawning a copy of this controller
 * with the same model but a different view. In parameterized to the model type since being able to
 * construct a view is dependent on the model type stored by a controller.
 *
 * @param <T> the return type of properties in the model
 * @param <S> the type of properties used by sprites in the model
 * @param <R> the type of sprites in the model
 */
public interface SpawnableController<T, S, R> extends AnimatorController {

  /**
   * Create a copy of this controller, creating a new view with the buildable but using the existing
   * model. Specifies the {@link Appendable} to provide to the view.
   *
   * @param toBuild the method to use to create the new view
   * @param out       the appendable to provide to the new view for text-based output
   * @return the new {@link SpawnableController}
   */
  SpawnableController<T, S, R> spawn(String toBuild, Appendable out);
}
