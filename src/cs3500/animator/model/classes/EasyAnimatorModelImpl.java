package cs3500.animator.model.classes;

import cs3500.animator.AnimatorUtils;
import cs3500.animator.model.interfaces.EasyAnimatorModel;
import cs3500.animator.model.interfaces.Shape;
import cs3500.animator.util.AnimationBuilder;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the implementation of {@link EasyAnimatorModel}. It provides a concrete
 * model to hold information about the animations. The model holds a list of shapes, and shapes
 * hold their specific animations. It also contains a field representing the tempo at which
 * the animations would be played. The model holds several methods that allow the client to add and
 * remove shapes or animations.
 */
public class EasyAnimatorModelImpl implements EasyAnimatorModel {

  private final List<Shape> shapes;
  private Bounds boundBox = new Bounds();
  private final Map<String, Shape> shapesMap;
  private int tempo;

  /**
   * Default constructor to create an instance of EasyAnimatorModelImpl with
   * no starting shapes, and with a default speed and boundsBox.
   */
  public EasyAnimatorModelImpl() {
    this.tempo = 1;
    this.shapes = new ArrayList<>();
    this.shapesMap = new HashMap<>();
  }

  /**
   * One argument constructor that allows a caller to specify starting shapes and their
   * animations that are within a new instance of a EasyAnimatorModelImpl.
   *
   * @param shapes the starting shapes that the user wants in the model.
   * @throws IllegalArgumentException if either in the list of shapes or list of animations have a
   *                                  null value as a member of the respective list.
   */
  public EasyAnimatorModelImpl(List<Shape> shapes)
      throws IllegalArgumentException {
    this.tempo = 1;
    this.shapes = new ArrayList<>();
    for (Shape shape : shapes) {
      addShape(shape);
    }
    // Create a hash map to easily access shapes
    this.shapesMap = new HashMap<>();
    for (Shape s : this.shapes) {
      shapesMap.put(s.getName(), s);
    }
  }

  /**
   * Builder method used to construct instances of an EasyAnimatorModelImpl.
   */
  public static final class Builder implements AnimationBuilder<EasyAnimatorModel> {

    private List<Shape> shapes;
    // default bounds box
    private Bounds bounds = new Bounds();
    private Map<String, Shape> shapesMap;

    /**
     * Default constructor for the builder of this EasyAnimatorModelImpl.
     */
    public Builder() {
      shapes = new ArrayList<>();
      // Create a hash map to easily access shapes
      shapesMap = new HashMap<>();
    }

    @Override
    public EasyAnimatorModel build() {
      EasyAnimatorModel output = new EasyAnimatorModelImpl();
      output.setBounds(bounds);
      for (Shape shape : shapes) {
        output.addShape(shape);
      }
      return output;
    }

    @Override
    public AnimationBuilder<EasyAnimatorModel> setBounds(int x, int y, int width, int height) {
      bounds = new Bounds(x, y, width, height);
      return this;
    }

    @Override
    public AnimationBuilder<EasyAnimatorModel> declareShape(String name, String type) {
      if (type.equalsIgnoreCase("ellipse")) {
        Shape e = new Ellipse(name, 50, 50, 1, 1,
                0, 0, Color.black);
        shapes.add(e);
        shapesMap.put(e.getName(), e);
      }
      if (type.equalsIgnoreCase("rectangle")) {
        Shape r = new Rectangle(name, 50, 50, 1, 1,
                0, 0, Color.black);
        shapes.add(r);
        shapesMap.put(r.getName(), r);
      }
      return this;
    }

    @Override
    public AnimationBuilder<EasyAnimatorModel> addMotion(String name, int t1, int x1, int y1,
        int w1, int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2,
        int g2, int b2) {
      Shape shape = shapesMap.get(name);
      // check if motion is move
      if (x1 != x2 || y1 != y2) {
        AnimationMove move =
                new AnimationMove(shape, t1, t2, x2, y2);
      }

      // check if motion is resize
      if (w1 != w2 || h1 != h2) {
        AnimationResize resize =
                new AnimationResize(shape, t1,t2, w2, h2);
      }

      // check if motion is color change
      if (r1 != r2 || g1 != g2 || b1 != b2) {
        AnimationColorChange colorChange =
                new AnimationColorChange(shape, t1, t2, new Color(r2, g2, b2));
      }
      return this;
    }

    @Override
    public AnimationBuilder<EasyAnimatorModel> addKeyframe(String name, int t, int x, int y,
        int w, int h, int r, int g, int b) {
      Keyframe current;
      Shape shape = shapesMap.get(name);
      if (shape.getKeyframes().isEmpty()) {
        current = new Keyframe(shape, t, t, x ,y ,w, h, r, g, b);
        shape.addKeyframe(current);
        shape.setStartTime(shape.getKeyframes().get(0).getStartTime());
        shape.setEndTime(shape.getKeyframes().get(shape.getKeyframes().size() - 1).getEndTime());
      }
      else {
        current = new Keyframe(shape, t, t, x, y, w, h, r, g, b);
        int index = shape.getKeyframes().indexOf(current);
        current.setStartTime(shape.getKeyframes().get(index - 1).getEndTime());
      }
      return this;
    }
  }

  @Override
  public void addShape(Shape shape) throws IllegalArgumentException {
    AnimatorUtils.guardAgainstNullShape(shape);
    guardAgainstConflictingShape(shape);
    shapes.add(shape);
  }

  @Override
  public void removeShape(Shape shape) throws IllegalArgumentException {
    AnimatorUtils.guardAgainstNullShape(shape);
    if (!shapes.contains(shape)) {
      throw new IllegalArgumentException("Trying to remove shape that does not exist.");
    }
    shapes.remove(shape);
  }

  /**
   * Guard against a conflicting shape.
   *
   * @param newShape shape to check.
   * @throws IllegalArgumentException if the new shape has the same name of a shape already in the
   *                                  model of the same type. Shapes of the same type cannot have
   *                                  the same name.
   */
  private void guardAgainstConflictingShape(Shape newShape) throws IllegalArgumentException {
    if (shapes.contains(newShape)) {
      throw new IllegalArgumentException("There already exists a Shape of that name and type in "
          + "the animation. Shapes of the same type must have a unique name");
    }
  }

  @Override
  public List<Shape> getShapes() {
    List<Shape> shapesCopy = new ArrayList<Shape>();
    for (Shape s : this.shapes) {
      shapesCopy.add(s.copy());
    }
    return shapesCopy;
  }

  @Override
  public Bounds getBounds() {
    return new Bounds(this.boundBox);
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

  /**
   * Deletes a keyframe from the list of keyframes.
   *
   * @param keyframe  keyframe to be removed
   * @throws IllegalArgumentException if Keyframe is null or does not exist
   */
  public void deleteKeyframe(Keyframe keyframe) throws IllegalArgumentException {
    AnimatorUtils.guardAgainstNullKeyframe(keyframe);
    for (Shape s : this.shapes) {
      // case 0: keyframe is the only keyframe
      if (s.getKeyframes().size() == 1) {
        s.removeKeyframe(keyframe);
      }

      // case 1: keyframe is the first keyframe
      else if (s.getKeyframes().size() > 1 && s.getKeyframes().indexOf(keyframe) == 0) {
        int current = s.getKeyframes().indexOf(keyframe);
        Keyframe next = s.getKeyframes().get(current + 1);
        next.setStartTime(keyframe.getStartTime());
        s.removeKeyframe(keyframe);
      }

      // case 2: keyframe is the last keyframe
      else if (s.getKeyframes().size() > 1
              && s.getKeyframes().indexOf(keyframe) == s.getKeyframes().size() - 1) {
        s.removeKeyframe(keyframe);
      }

      // case 3: keyframe is in the middle of other keyframes
      else {
        int current = s.getKeyframes().indexOf(keyframe);
        Keyframe previous = s.getKeyframes().get(current - 1);
        previous.setEndTime(keyframe.getEndTime());
        s.removeKeyframe(keyframe);
      }
    }
  }

  /**
   * Inserts a keyframe to the list of keyframes.
   *
   * @param keyframe  keyframe to be added
   * @throws IllegalArgumentException if Keyframe is null
   */
  public void insertKeyframe(Keyframe keyframe) throws IllegalArgumentException {
    AnimatorUtils.guardAgainstNullKeyframe(keyframe);
    Shape s = keyframe.getShape();
    s.addKeyframe(keyframe);
    List<Keyframe> keyframes = s.getKeyframes();
    Collections.sort(keyframes, new KeyframeComparator());

    if (keyframes.size() > 2) {
      int current = keyframes.indexOf(keyframe);
      Keyframe previous = keyframes.get(current - 1);
      Keyframe next = keyframes.get(current + 1);
      previous.setEndTime(keyframe.getEndTime());
      next.setStartTime(keyframe.getStartTime());
    }

    if (keyframes.size() == 2) {
      if (keyframes.indexOf(keyframes) == 1) {
        keyframes.get(0).setEndTime(keyframe.getStartTime());
        keyframes.get(1).setStartTime(keyframe.getEndTime());
      }
    }
  }
  
  @Override
  public void setBounds(Bounds bounds) {
    this.boundBox = bounds;
  }

}
