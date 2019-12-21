package cs3500.animator.model.classes;

import cs3500.animator.model.interfaces.EasyAnimatorModel;
import cs3500.animator.model.interfaces.EasyAnimatorModelReadOnly;
import cs3500.animator.model.interfaces.Shape;
import java.util.List;

/**
 * Implementation of EasyAnimatorModelReadOnly. This delegates to the given EasyAnimatorModel.
 * This is to restrict the view access to any mutation abilities of a EasyAnimatorModel.
 */
public class EasyAnimatorModelReadOnlyImpl implements EasyAnimatorModelReadOnly {

  private EasyAnimatorModel mutableModel;

  public EasyAnimatorModelReadOnlyImpl(EasyAnimatorModel model) {
    this.mutableModel = model;
  }

  @Override
  public int getTempo() {
    return mutableModel.getTempo();
  }

  @Override
  public List<Shape> getShapes() {
    return mutableModel.getShapes();
  }

  @Override
  public Bounds getBounds() {
    return mutableModel.getBounds();
  }

}
