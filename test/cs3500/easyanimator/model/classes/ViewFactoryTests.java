package cs3500.easyanimator.model.classes;

import cs3500.animator.model.classes.AnimationMove;
import cs3500.animator.model.classes.EasyAnimatorModelImpl;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.interfaces.Animation;
import cs3500.animator.model.interfaces.EasyAnimatorModel;
import cs3500.animator.model.interfaces.Shape;
import cs3500.animator.view.classes.SVGView;
import cs3500.animator.view.classes.TextualView;
import cs3500.animator.view.classes.ViewFactory;
import cs3500.animator.view.classes.VisualViewImpl;

import static junit.framework.TestCase.assertTrue;

/**
 * Contains the tests for the class that implements a factory method that creates views.
 */
public class ViewFactoryTests {
  Shape r;
  Animation move;
  List<Shape> shapes = new ArrayList<Shape>();
  List<Animation> animations = new ArrayList<Animation>();
  EasyAnimatorModel model;
  ViewFactory factory;

  /**
   * Initializes data for test usage and resets the data so that it can be used again.
   */
  @Before
  public void testFixture() {
    r = new cs3500.animator.model.classes.Rectangle("r", 1, 1, 1, 1, 1, 1, Color.BLUE);
    move = new AnimationMove(r, 1, 1, 2, 2);
    shapes.add(r);
    animations.add(move);
    model = new EasyAnimatorModelImpl(shapes);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullView() {
    factory = new ViewFactory(null, model, System.out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullModel() {
    factory = new ViewFactory("text", null, System.out);
  }

  @Test
  public void testTextualView() {
    factory = new ViewFactory("text", model, System.out);
    assertTrue(factory.getView() instanceof TextualView);
  }

  @Test
  public void testSVGView() {
    factory = new ViewFactory("svg", model, System.out);
    assertTrue(factory.getView() instanceof SVGView);
  }

  @Test
  public void testVisualView() {
    factory = new ViewFactory("visual", model, System.out);
    assertTrue(factory.getView() instanceof VisualViewImpl);
  }
}
