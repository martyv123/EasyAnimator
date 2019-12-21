package cs3500.animator.adapters;

import cs3500.animator.model.classes.Bounds;
import cs3500.animator.model.interfaces.EasyAnimatorModel;
import cs3500.animator.model.interfaces.Shape;
import cs3500.animator.provider.model.AnimatorModel;
import cs3500.animator.provider.model.AnimatorSprite;
import cs3500.animator.provider.model.ROAnimatorSprite;
import java.util.Map.Entry;
import java.util.stream.Stream;

/**
 * Adapter class for provider's AnimatorModel interface. The Provider's Model is equivalent, albeit
 * a more advanced, version of our Model.
 */
public class AnimatorModelAdapter implements AnimatorModel {

  private final EasyAnimatorModel easyAnimatorModel;

  /**
   * Constructs this adapter class from an EasyAnimatorModel. This takes in our representation
   * of a animation's model.
   *
   * @param easyAnimatorModel Our representation of a model.
   */
  public AnimatorModelAdapter(EasyAnimatorModel easyAnimatorModel) {
    this.easyAnimatorModel = easyAnimatorModel;
  }

  @Override
  public AnimatorModel addSprite(int layer, AnimatorSprite toAdd) throws IllegalArgumentException {
    return this;
  }

  @Override
  public AnimatorModel moveSpriteLayer(int from, int to) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Our model does not support sprite layers");
  }

  @Override
  public AnimatorModel removeSprite(int toRemove) throws IllegalArgumentException {
    easyAnimatorModel.removeShape(easyAnimatorModel.getShapes().get(toRemove));
    return this;
  }

  @Override
  public AnimatorModel setCanvas(int xLeft, int yTop, int canvasWidth, int canvasHeight) {
    easyAnimatorModel.setBounds(new Bounds(xLeft, yTop, canvasWidth, canvasHeight));
    return this;
  }

  @Override
  public AnimatorSprite getSpriteByName(String name) throws IllegalArgumentException {
    for (Shape shape : easyAnimatorModel.getShapes()) {
      if (shape.getName().equals(name)) {
        return new SpriteAdapter(shape);
      }
    }

    throw new IllegalArgumentException("Shape is not in the model");
  }

  @Override
  public Integer[] getCanvas() {
    Integer[] canvas = new Integer[4];
    canvas[0] = easyAnimatorModel.getBounds().getX();
    canvas[1] = easyAnimatorModel.getBounds().getY();
    canvas[2] = easyAnimatorModel.getBounds().getWidth();
    canvas[3] = easyAnimatorModel.getBounds().getHeight();
    return canvas;
  }

  @Override
  public Stream<ROAnimatorSprite> getROSprites() {
    throw new UnsupportedOperationException("We do not support Streams.");
  }

  @Override
  public Stream<Entry<Integer, ROAnimatorSprite>> getLayerROSprites() {
    throw new UnsupportedOperationException("We do not support layering");
  }

  @Override
  public ROAnimatorSprite getROSpriteByName(String name) throws IllegalArgumentException {
    for (Shape shape : easyAnimatorModel.getShapes()) {
      if (shape.getName().equals(name)) {
        return new SpriteAdapter(shape);
      }
    }

    throw new IllegalArgumentException("Shape is not in the model");
  }
}
