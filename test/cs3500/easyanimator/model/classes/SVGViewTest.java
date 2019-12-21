package cs3500.easyanimator.model.classes;

import static junit.framework.TestCase.assertEquals;

import cs3500.animator.model.classes.AnimationColorChange;
import cs3500.animator.model.classes.AnimationMove;
import cs3500.animator.model.classes.AnimationResize;
import cs3500.animator.model.classes.EasyAnimatorModelImpl;
import cs3500.animator.model.classes.EasyAnimatorModelReadOnlyImpl;
import cs3500.animator.model.classes.Ellipse;
import cs3500.animator.model.classes.Rectangle;
import cs3500.animator.model.interfaces.Animation;
import cs3500.animator.model.interfaces.EasyAnimatorModelReadOnly;
import cs3500.animator.view.classes.SVGView;

import java.awt.Color;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * TO Test the SVGView view.
 */
public class SVGViewTest {

  EasyAnimatorModelReadOnly readOnlyImpl;
  EasyAnimatorModelImpl impl;
  Rectangle r;
  Ellipse c;
  Animation move;
  Animation resize;
  Appendable out;
  Animation colorChange;


  /**
   * To keep test data consistent.
   */
  @Before
  public void textFixture() {
    out = new StringBuffer();
    impl = new EasyAnimatorModelImpl();
    readOnlyImpl = new EasyAnimatorModelReadOnlyImpl(impl);
    r = new Rectangle("r", 1, 1, 1, 1, 0, 100, Color.BLUE);
    c = new Ellipse("c", 1, 1, 1, 1, 0, 100, Color.RED);
    move = new AnimationMove(r, 1, 1, 1, 1);
    resize = new AnimationResize(r, 3, 4, 3, 4);
    colorChange = new AnimationColorChange(c, 1, 10, Color.CYAN);
    impl.addShape(r);
    impl.addShape(c);
  }

  @Test
  public void SVGVTest() throws IOException {
    SVGView svg = new SVGView(readOnlyImpl, out, 1);
    svg.display();
    assertEquals("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"1000\" height=\"1000\""
        + "viewBox=\"0 0 100 100\" version=\"1.1\">\n"
        + "<rect xmlns=\"http://www.w3.org/2000/svg\" id=\"r\" x=\"1\" y=\"1\" width=\"1\" "
        + "height=\"1\" fill=\"rgb(0,0,255)\" visibility=\"hidden\">\n"
        + "<set attributeType=\"xml\" begin=\"0ms\" attributeName=\"visibility\" to=\"visible\"/>\n"
        + "<set attributeType=\"xml\" begin=\"100000ms\" attributeName=\"visibility\" "
        + "to=\"hidden\"/>\n"
        + "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" begin=\"1000ms\""
        + " dur=\"0ms\" attributeName=\"x\" from=\"1\" to=\"1\" fill=\"freeze\"/>\n"
        + "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" begin=\"1000ms\""
        + " dur=\"0ms\" attributeName=\"y\" from=\"1\" to=\"1\" fill=\"freeze\"/>\n"
        + "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" begin=\"3000ms\" "
        + "dur=\"1000ms\" attributeName=\"width\" from=\"1\" to=\"1\" fill=\"freeze\"/>\n"
        + "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" begin=\"3000ms\""
        + " dur=\"1000ms\" attributeName=\"height\" from=\"1\" to=\"3\" fill=\"freeze\"/>\n"
        + "</rect>\n"
        + "<ellipse xmlns=\"http://www.w3.org/2000/svg\" id=\"c\" cx=\"1\" cy=\"1\" rx=\"1\" "
        + "ry=\"1\" fill=\"rgb(255,0,0)\" visibility=\"hidden\">\n"
        + "<set attributeType=\"xml\" begin=\"0ms\" attributeName=\"visibility\" to=\"visible\"/>\n"
        + "<set attributeType=\"xml\" begin=\"100000ms\" attributeName=\"visibility\" "
        + "to=\"hidden\"/>\n"
        + "<animate xmlns=\"http://www.w3.org/2000/svg\" attributeType=\"xml\" begin=\"1000ms\" "
        + "dur=\"9000ms\" attributeName=\"fill\" from=\"255\" to=\"0\" fill=\"freeze\"/>\n"
        + "</ellipse>\n"
        + "</svg>", out.toString());
  }

}
