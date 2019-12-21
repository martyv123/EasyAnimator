package cs3500.animator.adapters;

import cs3500.animator.model.classes.Keyframe;
import cs3500.animator.model.classes.ShapeType;
import cs3500.animator.model.interfaces.Shape;
import cs3500.animator.provider.model.AnimatorSprite;
import cs3500.animator.provider.model.BasicShapeProperties;
import cs3500.animator.provider.model.BasicShapes;
import cs3500.animator.provider.model.MotionBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Adapter class for provider's Sprite interface. The Provider's Sprite is equivalent, albeit
 * a more advanced, version of our Shapes.
 */
public class SpriteAdapter implements AnimatorSprite<Integer, BasicShapeProperties, BasicShapes> {

  private Shape shape;

  /**
   * Constructor for SpriteAdapter. Takes in our representation of animation objects, Shape.
   *
   * @param shape the object being adapted to our provider's representation.
   */
  public SpriteAdapter(Shape shape) {
    this.shape = shape;
  }


  @Override
  public MotionBuilder<Integer, BasicShapeProperties, BasicShapes> setMotions() {
    throw new UnsupportedOperationException("not supported");
  }

  @Override
  public AnimatorSprite<Integer, BasicShapeProperties, BasicShapes> setKeyframes(
      TreeMap<Integer, List<Integer>> keyframes) {

    for (Map.Entry<Integer, List<Integer>> entry : keyframes.entrySet()) {
      Integer key = entry.getKey();
      List<Integer> value = entry.getValue();

      shape.addKeyframe(new Keyframe(shape, value.get(0), value.get(1),
          value.get(1), value.get(1), value.get(1), value.get(1), value.get(1),
          value.get(1), value.get(1)));
    }
    return this;
  }

  @Override
  public Integer getVal(BasicShapeProperties property, int time)
      throws IllegalStateException, IllegalArgumentException {

    switch (property) {
      case X:
        return shape.getX();
      case B:
        return shape.getColor().getBlue();
      case G:
        return shape.getColor().getGreen();
      case H:
        return shape.getSecondDimension();
      case R:
        return shape.getColor().getRed();
      case W:
        return shape.getFirstDimension();
      case Y:
        return shape.getY();
      default:
        throw new IllegalArgumentException("Unsupported Property");
    }
  }

  @Override
  public List<Integer> getTemporalVertices() {
    List<Integer> kfEndTimes = new ArrayList<>();

    for (Keyframe kf: shape.getKeyframes()) {
      kfEndTimes.add(kf.getEndTime());
    }
    Collections.sort(kfEndTimes);

    return kfEndTimes;
  }

  @Override
  public String getName() {
    return shape.getName();
  }

  @Override
  public BasicShapes getType() {
    if (shape.getType().equals(ShapeType.ELLIPSE)) {
      return BasicShapes.ELLIPSE;
    }
    if (shape.getType().equals(ShapeType.RECTANGLE)) {
      return BasicShapes.RECTANGLE;
    }
    else {
      throw new IllegalArgumentException("Not supported Sprite type yet");
    }
  }

  @Override
  public boolean isVisible(int time) {
    return shape.getStartTime() <= time  && time <= shape.getEndTime();
  }

  @Override
  public TreeMap<Integer, List<Integer>> getKeyframes() {
    TreeMap<Integer, List<Integer>> map = new TreeMap<>();

    // decompose our keyframe class to a list of integers.
    for (Keyframe kf: shape.getKeyframes()) {
      List<Integer> keyframeValues = new ArrayList<>();
      keyframeValues.add(kf.getX());
      keyframeValues.add(kf.getY());
      keyframeValues.add(kf.getWidth());
      keyframeValues.add(kf.getHeight());
      keyframeValues.add(kf.getRed());
      keyframeValues.add(kf.getGreen());
      keyframeValues.add(kf.getBlue());
      map.put(kf.getEndTime(), keyframeValues);
    }

    return map;
  }
}
