package cs3500.animator.provider.view;

import java.io.IOException;

import cs3500.animator.provider.controller.EditController;
import cs3500.animator.provider.controller.SpawnableController;
import cs3500.animator.provider.controller.TimerController;

/**
 * Represents a simple view for animations. Configuration is intended to occur via an interface
 * which resolves the parameterized {@link cs3500.animator.provider.model.AnimatorModel} to a more
 * specific form required for interpretation by a view implementation.
 *
 * @param <T> the return type of properties
 * @param <S> the type of properties uses by sprites
 * @param <R> the type of sprites
 */
public interface AnimatorView<T, S, R> {

  /**
   * Launch the view. The behavior of having a view "launch" should be defined in the implementation
   * documentation.
   *
   * @throws IOException if there is a problem with showing the view
   */
  void launch() throws IOException;

  /**
   * Add a {@link TimerController} to the view. The default behavior of a view is to ignore this
   * action, but a view should override this method if it is interested in a temporal component. The
   * return type of this method indicates whether the controller should use a timer to keep this
   * view alive and ticking, with the default provided here being {@code false}.
   *
   * @param timerController the {@link TimerController} to add
   * @return if this view should be kept alive by the controller
   */
  default boolean addTimerController(TimerController timerController) {
    return false;
  }

  /**
   * Add a {@link EditController} to the view. The default behavior of a view is to ignore this
   * action, but a view should override this method if it is interested in sending commands with the
   * intent of editing the animation in some way.
   *
   * @param editController the {@link EditController} to add
   */
  default void addEditController(EditController<T, S, R> editController) {
    // The default behavior for this method is to do nothing, so the default is empty
  }

  /**
   * Add a {@link SpawnableController} to the view. The default behavior of a view is to ignore this
   * action, but a view should override this method if it is interested in sending commands with the
   * intent of launching another view (like either opening the same animation in a new window or
   * saving the animation to a file).
   *
   * @param spawnableController the {@link SpawnableController} to add
   */
  default void addSpawnableController(SpawnableController<T, S, R> spawnableController) {
    // The default behavior for this method is to do nothing, so the default is empty
  }

  /**
   * Refresh a view with a given tick. The default behavior of a view is to ignore this method, but
   * a view should override this method if it is interested in updating its state based on ticks.
   *
   * @param tick the tick to use as the new state
   */
  default void refresh(int tick) {
    // The default behavior for this method is to do nothing, so the default is empty
  }
}
