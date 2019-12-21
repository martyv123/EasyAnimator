package cs3500.animator.provider.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import javax.swing.JPanel;

import cs3500.animator.provider.model.BasicShapeProperties;
import cs3500.animator.provider.model.BasicShapes;
import cs3500.animator.provider.model.ROAnimatorModel;
import cs3500.animator.provider.model.ROAnimatorSprite;

/**
 * The panel for the Swing View. Houses the animations of the shapes.
 */
class ViewPanel extends JPanel {
  private final ROAnimatorModel<Integer, BasicShapeProperties, BasicShapes> model;
  private int tick;
  private final int leftX;
  private final int topY;
  private final int canvasWidth;
  private final int canvasHeight;

  /**
   * The constructor for a View Panel.
   *
   * @param model          the model from which data will be accessed.
   * @param width          the width of the panel
   * @param height         the height of the panel
   */
  public ViewPanel(ROAnimatorModel<Integer, BasicShapeProperties, BasicShapes> model,
                   int width, int height) {
    this.model = model;
    this.canvasWidth = width;
    this.canvasHeight = height;
    this.leftX = Objects.isNull(model.getCanvas()[0]) ? 0 : model.getCanvas()[0];
    this.topY = Objects.isNull(model.getCanvas()[1]) ? 0 : model.getCanvas()[1];
    this.refreshScreen(0);
  }

  /**
   * Draws all the sprites by obtaining a Graphics2D object and using that to draw each sprite.
   *
   * @param g the Graphics object to be used for drawing.
   */
  @Override
  protected void paintComponent(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, canvasWidth, canvasHeight);
    g.setColor(Color.BLACK);
    Graphics2D graphic = (Graphics2D) g;
    model.getROSprites().forEach(sprite -> {
      if (sprite.isVisible(this.tick)) {
        graphic.setColor(new Color(sprite.getVal(BasicShapeProperties.R, tick),
            sprite.getVal(BasicShapeProperties.G, tick),
            sprite.getVal(BasicShapeProperties.B, tick)));
        doShape(sprite, graphic);
      }
    });
  }

  /**
   * Starts the timer with which to draw the animation over time.
   */
  void refreshScreen(int tick) {
    this.tick = tick;
    this.repaint();
  }

  /**
   * Determines whether the given sprite is a rectangle or ellipse, and calls the correct method to
   * build each respective shape.
   *
   * @param shape   the given sprite to draw.
   * @param graphic the graphics object with which to draw the given sprite.
   */
  private void doShape(ROAnimatorSprite<Integer, BasicShapeProperties, BasicShapes> shape,
                       Graphics2D graphic) {
    switch (shape.getType()) {
      case RECTANGLE:
        doRect(shape, graphic);
        break;
      case ELLIPSE:
        doEllipse(shape, graphic);
        break;
      default:
        throw new IllegalStateException("You need a shape");
    }
  }

  /**
   * Draws a single given rectangle at the current tick.
   *
   * @param shape   the rectangle to draw.
   * @param graphic the graphics object with which to draw the given rectangle.
   */
  private void doRect(ROAnimatorSprite<Integer, BasicShapeProperties, BasicShapes> shape,
                      Graphics2D graphic) {
    Shape r = new Rectangle2D.Double(shape.getVal(BasicShapeProperties.X, this.tick) - leftX,
        shape.getVal(BasicShapeProperties.Y, this.tick) - topY,
        shape.getVal(BasicShapeProperties.W, this.tick),
        shape.getVal(BasicShapeProperties.H, this.tick));
    graphic.fill(r);
    graphic.draw(r);
  }

  /**
   * Draws a single given ellipse at the current tick.
   *
   * @param shape   the given ellipse to draw
   * @param graphic the given graphics object with which to draw the ellipse
   */
  private void doEllipse(ROAnimatorSprite<Integer, BasicShapeProperties, BasicShapes> shape,
                         Graphics2D graphic) {
    Shape e = new Ellipse2D.Double(shape.getVal(BasicShapeProperties.X, this.tick) - leftX,
        shape.getVal(BasicShapeProperties.Y, this.tick) - topY,
        shape.getVal(BasicShapeProperties.W, this.tick),
        shape.getVal(BasicShapeProperties.H, this.tick));
    graphic.fill(e);
    graphic.draw(e);
  }

}
