package cs3500.animator.model.classes;

import cs3500.animator.AnimatorUtils;
import java.awt.Color;
import cs3500.animator.model.interfaces.Animation;
import cs3500.animator.model.interfaces.Shape;


/**
 * A keyframe represents the desired end state of a Shape that it is acting upon at a specified
 * end time.
 *
 * <p>
 * INVARIANT: A Keyframe is defined as an array of three distinct {@link Animation} implementations.
 * These are never exposed and are managed internally, and as such should be safe from undesired
 * manipulation. The Animations are held as an Array of size 3, where each slot in the array is a
 * distinct type of Animation. We feel this is appropriate for our design and though brittle and
 * not easily extensible, works for our purposes as of now. If KeyFrames do change and more
 * manipulations and end state manipulation of Shapes, we would have to change how Shapes are
 * defined, add Animation types, and then keyframes, and as such, the internally held
 * details of keyframes would justifiably have to change to incorporate those newly needed elements.
 * Lastly, we realize that we are creating many Animation elements in every operation on Keyframe
 * and that may cause performance issues and memory space issues, but since the only references
 * to any created Animation in the Keyframe class is within the Keyframe class, we manipulate
 * which Animation elements are referenced, and Java's garbage collection should take care of
 * the unreferenced Animation objects.
 * </p>
 */
public class Keyframe implements IKeyframe {
  private Shape name;
  private int timeStart;
  private int timeEnd;
  private int x;
  private int y;
  private int w;
  private int h;
  private int r;
  private int g;
  private int b;
  private Animation[] animations;

  /**
   * Constructs a keyframe.
   *
   * @param name  the name of the shape the keyframe acts on
   * @param timeStart     the time of the keyframe
   * @param timeEnd the endTime of this keyframe, dictates when the keyframe should fully describe
   *                the state of the shape it acts upon.
   * @param x     the x-position of the shape
   * @param y     the y-position of the shape
   * @param w     the first dimension of the shape being acted upon.
   * @param h     the second dimension of the shape being acted upon.
   * @param r     the red color-value of the shape
   * @param g     the green color-value of the shape
   * @param b     the blue color-value of the shape
   * @throws      IllegalArgumentException if the shape is null
   */
  public Keyframe(Shape name, int timeStart, int timeEnd, int x, int y, int w, int h, int r,
      int g, int b) {
    AnimatorUtils.guardAgainstNullShape(name);
    this.name = name;
    this.timeStart = timeStart;
    this.timeEnd = timeEnd;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.r = r;
    this.g = g;
    this.b = b;
    this.animations = new Animation[3];
    animations[0] = new AnimationColorChange(name, timeStart, timeEnd, new Color(r,g,b));
    animations[1] = new AnimationMove(name, timeStart, timeEnd, x, y);
    animations[2] = new AnimationResize(name, timeStart, timeEnd, w, h);
    name.addKeyframe(this);
  }

  @Override
  public void setX(int x) {
    this.x = x;
    animations[1] = new AnimationMove(name, timeStart, timeEnd, x, this.y);
  }

  @Override
  public void setY(int y) {
    this.y = y;
    animations[1] = new AnimationMove(name, timeStart, timeEnd, x, y);
  }

  @Override
  public void setWidth(int width) {
    this.w = width;
    animations[2] = new AnimationResize(name, timeStart, timeEnd, width, h);
  }

  @Override
  public void setHeight(int height) {
    this.h = height;
    animations[2] = new AnimationResize(name, timeStart, timeEnd, w, height);

  }

  @Override
  public void setRed(int value) {
    this.r = value;
    animations[3] = new AnimationColorChange(name, timeStart, timeEnd, new Color(value, g, b));
  }

  @Override
  public void setGreen(int value) {
    this.g = value;
    animations[3] = new AnimationColorChange(name, timeStart, timeEnd, new Color(r, value, b));

  }

  @Override
  public void setBlue(int value) {
    this.b = value;
    animations[3] = new AnimationColorChange(name, timeStart, timeEnd, new Color(r, g, value));
  }

  @Override
  public Shape getShape() {
    return this.name;
  }

  @Override
  public int getStartTime() {
    return this.timeStart;
  }

  @Override
  public int getEndTime() {
    return this.timeEnd;
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public int getWidth() {
    return w;
  }

  @Override
  public int getHeight() {
    return h;
  }

  @Override
  public int getRed() {
    return r;
  }

  @Override
  public int getBlue() {
    return b;
  }

  @Override
  public int getGreen() {
    return g;
  }

  @Override
  public void setStartTime(int time) {
    this.timeStart = time;
    animations[0] = new AnimationColorChange(this.getShape(), this.getStartTime(),
            this.getEndTime(), new Color(this.getRed(), this.getBlue(), this.getGreen()));
    animations[1] = new AnimationMove(this.getShape(), this.getStartTime(),
            this.getEndTime(), this.getX(), this.getY());
    animations[2] = new AnimationResize(this.getShape(), this.getStartTime(),
            this.getEndTime(), this.getWidth(), this.getHeight());
  }

  @Override
  public void setEndTime(int time) {
    this.timeEnd = time;
    animations[0] = new AnimationColorChange(this.getShape(), this.getStartTime(),
            this.getEndTime(), new Color(this.getRed(), this.getBlue(), this.getGreen()));
    animations[1] = new AnimationMove(this.getShape(), this.getStartTime(),
            this.getEndTime(), this.getX(), this.getY());
    animations[2] = new AnimationResize(this.getShape(), this.getStartTime(),
            this.getEndTime(), this.getWidth(), this.getHeight());
  }
}
