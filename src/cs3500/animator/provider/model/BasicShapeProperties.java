package cs3500.animator.provider.model;

/**
 * <p>Provides an enumeration of properties associated with general-purpose basic shapes. These
 * properties have interpretations and a natural order as follows:</p>
 * <ol>
 * <li>{@code X}: the x position of the shape, as the top left corner of the rectangular bounding
 * box with origin at the top left</li>
 * <li>{@code Y}: the y position of the shape, as the top left corner of the rectangular bounding
 * box with origin at the top left</li>
 * <li>{@code W}: the horizontal width of the shape from edge to edge</li>
 * <li>{@code H}: the vertical height of the shape from edge to edge</li>
 * <li>{@code R}: the red component of the RGB fill color of the shape</li>
 * <li>{@code G}: the green component of the RGB fill color of the shape</li>
 * <li>{@code B}: the blue component of the RGB fill color of the shape</li>
 * </ol>
 */
public enum BasicShapeProperties {
  X, Y, W, H, R, G, B
}

