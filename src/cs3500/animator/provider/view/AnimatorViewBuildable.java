package cs3500.animator.provider.view;

import cs3500.animator.provider.model.AnimatorModel;
import cs3500.animator.provider.model.ROAnimatorModel;

/**
 * Specifies an interface for function objects modeling a general constructor for {@link
 * AnimatorView} objects. This interface provides a type to allow referencing of constructors for
 * implementations of {@link AnimatorView}, though it may of course be used with any method
 * maintaining the given signature.
 *
 * @param <T> the type of value returned by properties in the model
 * @param <S> the type of identifier used to specify properties in the model
 * @param <R> the type of identifier to specify between different types of sprites
 */
public interface AnimatorViewBuildable<T, S, R> {

  /**
   * Method for constructing implementations of {@link AnimatorView}. It is assumed that basic
   * invariants on the parameters (like nullness) are checked by the caller.
   *
   * @param in  the non-null {@link AnimatorModel} to draw data from
   * @param out the non-null {@link Appendable} to send text-based output to
   * @return a constructed {@link AnimatorView}
   */
  AnimatorView<T, S, R> build(ROAnimatorModel<T, S, R> in, Appendable out);
}
