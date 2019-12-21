package cs3500.easyanimator.model.classes;

import cs3500.animator.model.classes.Keyframe;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.IOException;

import cs3500.animator.model.classes.EasyAnimatorModelImpl;
import cs3500.animator.model.classes.Ellipse;
import cs3500.animator.model.classes.Rectangle;
import cs3500.animator.model.interfaces.EasyAnimatorModelReadOnly;
import cs3500.animator.view.classes.TextualView;

import static org.junit.Assert.assertEquals;

/**
 * Contains the tests for the textual view implementation.
 */
public class TextualViewTests {

  EasyAnimatorModelReadOnly readOnlyImpl;
  EasyAnimatorModelImpl impl;
  Rectangle r;
  Ellipse c;
  Appendable out;
  Keyframe kf1;
  Keyframe kf2;
  TextualView textualView;

  /**
   * To keep test data consistent.
   */
  @Before
  public void textFixture() {
    out = new StringBuilder();
    impl = new EasyAnimatorModelImpl();
    r = new Rectangle("r", 1, 1, 1, 1, 0, 100, Color.BLUE);
    c = new Ellipse("c", 1, 1, 1, 1, 0, 100, Color.RED);
    kf1 = new Keyframe(r, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    kf2 = new Keyframe(c, 2, 20, 30, 40, 50, 60, 70, 80, 90);
    impl.addShape(r);
    impl.addShape(c);
    readOnlyImpl = impl;
    textualView = new TextualView(impl, out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullModel() {
    textualView = new TextualView(null, out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullOutput() {
    textualView = new TextualView(impl, null);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void textRefresh() {
    textualView.refresh();
  }

  @Test
  public void testDisplay() {
    try {
      textualView.display();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals("canvas 0 0 100 100\n"
            + "shape r rectangle\n"
            + "motion r 2 3 4 5 6 7 8 9 2 3 4 5 6 7 8 9\n"
            + "shape c ellipse\n"
            + "motion c 20 30 40 50 60 70 80 90 20 30 40 50 60 70 80 90",
        out.toString());
  }
}
