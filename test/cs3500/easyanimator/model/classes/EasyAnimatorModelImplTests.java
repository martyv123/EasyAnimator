package cs3500.easyanimator.model.classes;

import cs3500.animator.model.classes.AnimationMove;
import cs3500.animator.model.classes.AnimationResize;
import cs3500.animator.model.classes.EasyAnimatorModelImpl;
import cs3500.animator.model.classes.Ellipse;
import cs3500.animator.model.classes.Rectangle;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;

import cs3500.animator.model.interfaces.Animation;
import cs3500.animator.model.interfaces.Shape;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Contains the tests for the Easy Animator Model implementation.
 */
public class EasyAnimatorModelImplTests {
  EasyAnimatorModelImpl impl;
  Rectangle r;
  Ellipse c;
  Animation move;
  Animation resize;

  /**
   * To keep test data consistent.
   */
  @Before
  public void textFixture() {
    impl = new EasyAnimatorModelImpl();
    r = new Rectangle("r", 1, 1, 1, 1, 0, 100, Color.BLUE);
    c = new Ellipse("c", 1, 1, 1, 1, 0, 100, Color.RED);
    move = new AnimationMove(r, 1, 1, 1, 1);
    resize = new AnimationResize(r, 3, 4, 3, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullShape() {
    ArrayList<Shape> bad = new ArrayList<Shape>();
    bad.add(null);
    EasyAnimatorModelImpl impl = new EasyAnimatorModelImpl(bad);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNullShape() {
    impl.addShape(null);
  }

  @Test
  public void testEmptyEasyAnimatorModel() {
    assertTrue(impl.getShapes().isEmpty());
  }

  @Test
  public void testAddShape() {
    assertTrue(impl.getShapes().isEmpty());
    impl.addShape(r);
    assertTrue(impl.getShapes().contains(r));
  }

  @Test
  public void testGetShapes() {
    assertTrue(impl.getShapes().isEmpty());
    impl.addShape(r);
    impl.addShape(c);
    ArrayList<Shape> test = new ArrayList<Shape>();
    test.add(r);
    test.add(c);
    assertTrue(test.equals(impl.getShapes()));
  }

  @Test
  public void testGetTempo() {
    assertEquals(1, impl.getTempo());
    impl.setTempo(5);
    assertEquals(5, impl.getTempo());
  }

  @Test
  public void testToString() {
    assertEquals("",impl.toString());
    impl.addShape(r);
    assertEquals("shape r rectangle\n"
            + "motion r 1 1 1 1 1 0 0 255 1 1 1 1 1 0 0 255\n"
        + "motion r 3 1 1 1 1 0 0 255 4 1 1 3 4 0 0 255\n\n",
            impl.toString());
  }
}
