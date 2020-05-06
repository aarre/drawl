/*
 * Drawl, the world's best drawing language.
 *
 * Copyright (c) 2020 Aarre Laakso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.aarrelaakso.drawl;

import com.google.common.flogger.FluentLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Abstract class represents shapes such as circles, rectangles, and lines.
 */
public class Shape {

    private static final String CANNOT_BE_ADJACENT_TO_ITSELF = "A circle cannot be adjacent to itself";
    private static final @NotNull FluentLogger logger;

    static {
        logger = FluentLogger.forEnclosingClass();

    }

    /**
     * The angle, in degrees, to a neighbor. 0 represents up, and 90 degrees represents to the right.
     */
    private DrawlNumber angleToNeighbor;

    /**
     * The explicit height of a Shape defaults to <code>null</code> to indicate it that has not yet been set.
     */
    private @Nullable DrawlNumber explicitHeight;

    /**
     * The explicit width of a Shape defaults to <code>null</code> to indicate that it has not yet been set.
     */
    private @Nullable DrawlNumber explicitWidth;

    /**
     * A default Shape is centered at (0,0) in both explicit and implicit coordinates.
     */
    private DrawlNumber explicitXPositionCenter = DrawlNumber.ZERO;

    /**
     * A default Shape is centered at (0,0) in both explicit and implicit coordinates.
     */
    private DrawlNumber explicitYPositionCenter = DrawlNumber.ZERO;

    /**
     * The fill of this Shape. Defaults to null, meaning the SVG default.
     */
    private String fill;

    /**
     * The implicit height of a default Shape is 1.
     */
    private DrawlNumber implicitHeight = DrawlNumber.ONE;

    /**
     * The implicit width of a default Shape is 1.
     */
    private DrawlNumber implicitWidth = DrawlNumber.ONE;

    /**
     * A default Shape is centered at (0,0) in both explicit and implicit coordinates.
     */
    private DrawlNumber implicitXPositionCenter = DrawlNumber.ZERO;

    /**
     * A default Shape is centered at (0,0) in both explicit and implicit coordinates.
     */
    private DrawlNumber implicitYPositionCenter = DrawlNumber.ZERO;

    /**
     * A shape adjacent to this one, if any
     */
    private Shape neighbor;

    /**
     * The stroke of this Shape. Defaults to null, meaning the SVG default.
     */
    private String stroke;

    /**
     * Text inside this Shape. Defaults to null, meaning no Text in this Shape.
     */
    private @Nullable Text text;

    /**
     * Adds Text inside this Shape.
     *
     * @param text a Text object representing the text to be drawn inside this Shape.
     */
    public void addText(@Nullable Text text) {
        this.text = text;
    }

    /**
     * Gets this Shape's neighbor above (this Shape is below that one), if any.
     *
     * @return the Shape to the right of this one, if any;
     * <code>null</code> otherwise.
     */
    public @Nullable Shape getAbove() {
        @Nullable Shape returnValue = null;
        if (this.angleToNeighbor.equals(DrawlNumber.ZERO)) {
            returnValue = this.neighbor;
        }
        return returnValue;
    }

    /**
     * Sets this Shape above another Shape.
     *
     * @param shape The Shape that will be below this one.
     */
    public void setAbove(@NotNull Shape shape) {
        this.setAbove(shape, new Measure(0));
    }

    /**
     * Gets this Shape's neighbor below (this Shape is above that one), if any.
     *
     * @return the Shape to below this one, if any;
     * <code>null</code> otherwise.
     */
    public @Nullable Shape getBelow() {
        @Nullable Shape returnValue = null;
        if (this.angleToNeighbor.equals(DrawlNumber.valueOf(180))) {
            returnValue = this.neighbor;
        }
        return returnValue;
    }

    /**
     * Sets this Shape below another Shape.
     *
     * @param shape The Shape that will be above this one.
     */
    public void setBelow(@NotNull Shape shape) {
        this.setBelow(shape, new Measure(0));
    }

    /**
     * Returns a Point object representing this Shape's bottom port.
     *
     * @return
     */
    public @NotNull Point getBottomPort() {
        DrawlNumber xCoordinate = this.getImplicitXPositionCenter();
        DrawlNumber yCoordinate = this.getImplicitYPositionBottom();
        return new Point(xCoordinate, yCoordinate);
    }

    /**
     * Gets half the explicit height of this Shape.
     *
     * @return half the explicit height of this Shape.
     */
    private DrawlNumber getExplicitHalfHeight() {
        if (this.getExplicitHeight() == null) {
            throw new UnsupportedOperationException("Cannot calculate explicit height without dimensions");
        }
        return this.getExplicitHeight().divide(DrawlNumber.valueOf(2), DrawlNumber.mcOperations);
    }

    /**
     * Gets half the explicit width of this Shape.
     *
     * @return half the explicit width of this Shape.
     */
    protected DrawlNumber getExplicitHalfWidth() {
        if (this.getExplicitWidth() == null) {
            throw new UnsupportedOperationException("Cannot calculate explicit width without dimensions");
        }
        return this.getExplicitWidth().divide(DrawlNumber.valueOf(2), DrawlNumber.mcOperations);
    }

    /**
     * Get the explicit height of this Shape.
     *
     * @return the explicit height of this Shape, or <code>null</code> if this Shape has not yet been assigned an
     * explicit height.
     */
    protected @Nullable DrawlNumber getExplicitHeight() {
        return this.explicitHeight;
    }

    /**
     * Set the height of this Shape to an explicit value.
     * <p>
     *
     * @param height The new height of this Shape. Can be <code>null</code> to indicate that this Shape has not yet
     *               been assigned an explicit height.
     */
    protected void setExplicitHeight(@Nullable DrawlNumber height) {
        this.explicitHeight = height;
        if (Boolean.TRUE.equals(this.hasText())) {
            Objects.requireNonNull(this.getText()).setExplicitHeight(height);
        }
    }

    /**
     * Get the explicit width of this Shape.
     *
     * @return the explicit width of this Shape, or <code>null</code> if this Shape has not yet been assigned an
     * explicit width.
     */
    protected @Nullable DrawlNumber getExplicitWidth() {
        return this.explicitWidth;
    }

    /**
     * Set the width of this Shape to an explicit value.
     * <p>
     *
     * @param width the new width of this Shape, or <code>null</code> to indicate that this Shape has not yet
     *              been assigned an explicit width.
     */
    protected void setExplicitWidth(@Nullable DrawlNumber width) {
        this.explicitWidth = width;
        if (Boolean.TRUE.equals(this.hasText())) {
            Objects.requireNonNull(this.getText()).setExplicitWidth(width);
        }
    }

    /**
     * Gets the explicit x-position of the center of this Shape.
     *
     * @return the explicit x-position of the center of this Shape.
     */
    @NotNull
    protected DrawlNumber getExplicitXPositionCenter() {
        return this.explicitXPositionCenter;
    }

    /**
     * Sets the explicit center position of this Shape.
     *
     * @param x the explicit x position of the center of this Shape.
     */
    protected void setExplicitXPositionCenter(DrawlNumber x) {
        this.explicitXPositionCenter = x;
        if (Boolean.TRUE.equals(this.hasText())) {
            Objects.requireNonNull(this.getText()).setExplicitXPositionCenter(x);
        }
    }

    /**
     * Sets the explicit x position of the center of this Shape.
     *
     * @param x the explicit x position of the center of this Shape.
     */
    protected void setExplicitXPositionCenter(Integer x) {
        setExplicitXPositionCenter(DrawlNumber.valueOf(x));
    }

    /**
     * Gets the explicit x position of the left edge of this Shape.
     *
     * @return the explicit x position of the left edge of this Shape.
     */
    protected DrawlNumber getExplicitXPositionLeft() {
        return this.explicitXPositionCenter.subtract(this.getExplicitHalfWidth());
    }

    /**
     * Gets the explicit x position of the right edge of this Shape.
     *
     * @return the explicit x position of the right edge of this Shape.
     */
    protected DrawlNumber getExplicitXPositionRight() {
        return this.explicitXPositionCenter.add(this.getExplicitHalfWidth());
    }

    /**
     * Gets the explicit y position of the bottom of this Shape.
     *
     * @return the explicit y position of the bottom of this Shape.
     */
    @NotNull
    protected DrawlNumber getExplicitYPositionBottom() {
        return this.getExplicitYPositionCenter().add(this.getExplicitHalfHeight());
    }

    /**
     * Gets the explicit y-position of the center of this Shape.
     *
     * @return the explicit y-position of the center of this Shape.
     */
    @NotNull
    protected DrawlNumber getExplicitYPositionCenter() {
        return this.explicitYPositionCenter;
    }

    /**
     * Sets the explicit y-position of the center of this Shape. This convenience allows passing the argument as an
     * Integer rather than a DrawlNumber.
     *
     * @param y The explicit y position of this shape.
     */
    protected void setExplicitYPositionCenter(Integer y) {
        this.setExplicitYPositionCenter(DrawlNumber.valueOf(y));
    }

    /**
     * Sets the explicit y position of this Shape.
     * <p>
     * The y position marks the vertical position of the Shape's center. The explicit coordinate system is the
     * common Cartesian coordinate system, with higher values of y upward and lower values of y downward.
     *
     * @param y The explicit y position of this Shape.
     */
    protected void setExplicitYPositionCenter(DrawlNumber y) {
        this.explicitYPositionCenter = y;
        if (Boolean.TRUE.equals(this.hasText())) {
            Objects.requireNonNull(this.getText()).setExplicitYPositionCenter(y);
        }
    }

    /**
     * Gets the explicit y-position of the top of this Shape.
     *
     * @return the explicit y-position of the top of this Shape.
     */
    @NotNull
    protected DrawlNumber getExplicitYPositionTop() {
        return this.getExplicitYPositionCenter().subtract(this.getExplicitHalfHeight());
    }

    /**
     * Returns the fill associated with this Shape, if any.
     *
     * @return the fill associated with this Shape, or null if no fill has been associated with this Shape.
     */
    @Nullable
    public String getFill() {
        return this.fill;
    }

    /**
     * Set the fill of this Shape.
     *
     * @param s A string representing a fill color, e.g., "white".
     */
    public void setFill(String s) {
        this.fill = s;
    }

    /**
     * Returns a Measure object that represents the height of this Shape.
     *
     * @return a Measure object that represents the height of this Shape.
     */
    public @NotNull Measure getHeight() {
        @Nullable DrawlNumber height = this.getImplicitHeight();
        return new Measure(height);
    }

    /**
     * Gets half of the implicit height of this Shape.
     *
     * @return half of the implicit height of this Shape.
     */
    protected DrawlNumber getImplicitHalfHeight() {
        return Objects.requireNonNull(this.getImplicitHeight()).divide(DrawlNumber.TWO, DrawlNumber.mcOperations);
    }

    /**
     * Gets half of the implicit width of this Shape.
     *
     * @return half of the implicit width of this Shape.
     */
    protected DrawlNumber getImplicitHalfWidth() {
        return this.getImplicitWidth().divide(DrawlNumber.TWO, DrawlNumber.mcOperations);
    }

    /**
     * Gets the implicit height of this Shape.
     *
     * @return the implicit height of this Shape.
     */
    protected @Nullable DrawlNumber getImplicitHeight() {
        return this.implicitHeight;
    }

    /**
     * Sets the implicit height of this Shape.
     *
     * @param implicitHeight
     */
    protected final void setImplicitHeight(@NotNull DrawlNumber implicitHeight) {
        logger.atFine().log("Setting implicit height to %s", implicitHeight.toPlainString());
        this.implicitHeight = implicitHeight;
    }

    /**
     * Gets the implicit width of this Shape.
     *
     * @return the implicit width of this Shape.
     */
    protected DrawlNumber getImplicitWidth() {
        return this.implicitWidth;
    }

    /**
     * Sets the implicit width of this Shape.
     *
     * @param implicitWidth the implicit width of this Shape.
     */
    protected final void setImplicitWidth(@NotNull DrawlNumber implicitWidth) {
        logger.atFine().log("Setting implicit width to %s", implicitWidth.toPlainString());
        this.implicitWidth = implicitWidth;
    }

    /**
     * Get the implicit maximum (rightmost) x-position of this Shape.
     *
     * @return The implicit maximum (rightmost) x-position of this Shape.
     */
    protected DrawlNumber getImplicitXMaximum() {
        return this.getImplicitXPositionCenter().add(this.getImplicitHalfWidth());
    }

    /**
     * Get the implicit minimum (leftmost) x-position of this Shape.
     *
     * @return The implicit minimum (leftmost) x-position of this Shape.
     */
    protected DrawlNumber getImplicitXMinimum() {
        return this.getImplicitXPositionCenter().subtract(this.getImplicitHalfWidth());
    }

    /**
     * Get the implicit x position of the center of this Shape.
     *
     * @return The implicit x position of the center of this Shape.
     */
    protected DrawlNumber getImplicitXPositionCenter() {
        return this.implicitXPositionCenter;
    }

    /**
     * Sets the implicit x position of the center of this Shape.
     *
     * @param x implicit x position of the center of this Shape.
     */
    protected void setImplicitXPositionCenter(DrawlNumber x) {
        this.implicitXPositionCenter = x;
        if (Boolean.TRUE.equals(this.hasText())) {
            Objects.requireNonNull(this.getText()).setImplicitXPositionCenter(x);
        }
    }

    /**
     * Gets the implicit x position of the left edge of this Shape.
     *
     * @return The implicit x position of the left edge of this Shape.
     */
    protected DrawlNumber getImplicitXPositionLeft() {
        return this.getImplicitXPositionCenter().subtract(this.getImplicitHalfWidth());
    }

    /**
     * Gets the implicit x position of the right edge of this Shape.
     *
     * @return The implicit x position of the right edge of this Shape.
     */
    protected DrawlNumber getImplicitXPositionRight() {
        return this.getImplicitXPositionCenter().add(this.getImplicitHalfWidth());
    }

    /**
     * Gets the implicit bottommost y-position of this Shape. In implicit coordinates, this is the minimum y-position.
     *
     * @return The implicit minimum (bottommost) y-position of this Shape.
     */
    protected DrawlNumber getImplicitYPositionBottom() {
        return this.getImplicitYPositionCenter().subtract(this.getImplicitHalfHeight());
    }

    /**
     * Gets the implicit y position of the center of this Shape.
     *
     * @return The implicit y position of the center of this Shape.
     */
    protected DrawlNumber getImplicitYPositionCenter() {
        return this.implicitYPositionCenter;
    }

    /**
     * Sets the implicit y position of this Shape.
     * <p>
     * The y position marks the vertical position of the Shape's center. In the implicit coordinate system, higher
     * values of y are upward whereas lower values of y are downward.
     *
     * @param y The implicit y position of this Shape.
     */
    protected void setImplicitYPositionCenter(DrawlNumber y) {
        this.implicitYPositionCenter = y;
        if (Boolean.TRUE.equals(this.hasText())) {
            Objects.requireNonNull(this.getText()).setImplicitYPositionCenter(y);
        }
    }

    /**
     * Gets the implicit topmost y position of this Shape. In implicit coordinates, this is the maximum y position.
     *
     * @return The implicit maximum (topmost) y-position of this Shape.
     */
    protected DrawlNumber getImplicitYPositionTop() {
        return this.getImplicitYPositionCenter().add(this.getImplicitHalfHeight());
    }

    /**
     * Gets this Shape's neighbor to the right (this Shape is to the left of that one), if any.
     *
     * @return the Shape to the right of this one, if any;
     * <code>null</code> otherwise.
     */
    public @Nullable Shape getLeftOf() {
        @Nullable Shape returnValue = null;
        if ((this.angleToNeighbor != null) && (this.angleToNeighbor.equals(DrawlNumber.valueOf(90)))) {
            returnValue = this.neighbor;
        }
        return returnValue;
    }

    /**
     * Sets this Shape to the left of another one.
     *
     * @param shape the Shape that will be to the right of this one.
     */
    public void setLeftOf(@NotNull Shape shape) {
        this.setLeftOf(shape, new Measure(0));
    }

    /**
     * Returns a Point object representing this Shape's left port.
     *
     * @return
     */
    public @NotNull Point getLeftPort() {
        DrawlNumber xCoordinate = this.getImplicitXPositionLeft();
        DrawlNumber yCoordinate = this.getImplicitYPositionCenter();
        return new Point(xCoordinate, yCoordinate);
    }

    /**
     * Gets this Shape's neighbor to the left (this Shape is to the right of that one), if any.
     *
     * @return the Shape to the left of this one, if any;
     * <code>null</code> otherwise.
     */
    public @Nullable Shape getRightOf() {
        @Nullable Shape returnValue = null;
        if ((this.angleToNeighbor != null) && (this.angleToNeighbor.isEqualTo(DrawlNumber.valueOf(270)))) {
            returnValue = this.neighbor;
        }
        return returnValue;
    }

    /**
     * Sets this Shape's neighbor to the left (this Shape is to the right of that one).
     *
     * @param shape the Shape to the left of this one
     */
    public void setRightOf(@NotNull Shape shape) {
        this.setRightOf(shape, new Measure(0));
    }

    /**
     * Returns a Point object representing this Shape's left port.
     *
     * @return
     */
    public @NotNull Point getRightPort() {
        DrawlNumber xCoordinate = this.getImplicitXPositionRight();
        DrawlNumber yCoordinate = this.getImplicitYPositionCenter();
        return new Point(xCoordinate, yCoordinate);
    }

    public String getSVG() {
        return "oops";
    }

    /**
     * Gets the stroke of this Shape.
     *
     * @return The stroke of this shape, or null if the stroke of this Shape has not been set.
     */
    @Nullable
    public String getStroke() {
        return this.stroke;
    }

    /**
     * Sets the stroke of this shape.
     *
     * @param s A stroke name.
     */
    public void setStroke(String s) {
        this.stroke = s;
    }

    /**
     * Returns a Text object that belongs to this Shape, if there is one.
     *
     * @return A Text object that belongs to this Shape, or <code>null</code> if this Shape does not have a Text object
     * associated with it.
     */
    @Nullable
    public Text getText() {
        return this.text;
    }

    /**
     * Returns a Point object representing this Shape's top port.
     *
     * @return
     */
    public @NotNull Point getTopPort() {
        DrawlNumber xCoordinate = this.getImplicitXPositionCenter();
        DrawlNumber yCoordinate = this.getImplicitYPositionTop();
        return new Point(xCoordinate, yCoordinate);
    }

    /**
     * Returns a Measure object that represents the width of this Shape.
     *
     * @return
     */
    public @NotNull Measure getWidth() {
        DrawlNumber width = this.getImplicitWidth();
        return new Measure(width);

    }

    /**
     * Indicates whether this shape has a Text object associated with it.
     *
     * @return TRUE if this shape has a Text object associated with it, FALSE otherwise.
     */
    @NotNull
    public Boolean hasText() {
        if (this.getText() == null) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * Sets this Shape above another Shape.
     *
     * @param shape The circle that will be below this one.
     */
    public void setAbove(@NotNull Shape shape, @NotNull Measure offset) {
        if (shape == this) {
            throw new UnsupportedOperationException(CANNOT_BE_ADJACENT_TO_ITSELF);
        }
        this.neighbor = shape;
        this.angleToNeighbor = DrawlNumber.valueOf(0);

        // Set the y position of this Shape
        DrawlNumber topBoundaryOfShape = shape.getImplicitYPositionTop();
        DrawlNumber thisImplicitYPosition = topBoundaryOfShape.add(this.getImplicitHalfHeight(),
                DrawlNumber.mcOperations);
        thisImplicitYPosition = thisImplicitYPosition.add(offset.toDrawlNumber());
        this.setImplicitYPositionCenter(thisImplicitYPosition);

        // Set the x position of this Shape to match the one it is above
        this.setImplicitXPositionCenter(shape.getImplicitXPositionCenter());
        this.setExplicitXPositionCenter(shape.getExplicitXPositionCenter());
    }

    /**
     * Sets this circle below another circle.
     *
     * @param shape The circle that will be above this one.
     */
    public void setBelow(@NotNull Shape shape, @NotNull Measure offset) {
        if (shape == this) {
            throw new UnsupportedOperationException(CANNOT_BE_ADJACENT_TO_ITSELF);
        }
        this.neighbor = shape;
        this.angleToNeighbor = DrawlNumber.valueOf(180);
        DrawlNumber bottomBoundaryOfShape = shape.getImplicitYPositionBottom();
        DrawlNumber thisImplicitYPosition = bottomBoundaryOfShape.subtract(this.getImplicitHalfHeight(),
                DrawlNumber.mcOperations);
        thisImplicitYPosition = thisImplicitYPosition.subtract(offset.toDrawlNumber());
        this.setImplicitYPositionCenter(thisImplicitYPosition);

        // Set the x position of this Shape to match the one it is above
        this.setImplicitXPositionCenter(shape.getImplicitXPositionCenter());
        this.setExplicitXPositionCenter(shape.getExplicitXPositionCenter());
    }

    /**
     * Sets this Shape's neighbor to the right (this Shape is to the left of that one).
     *
     * @param shape the Shape to the right of this one
     */
    public void setLeftOf(@NotNull Shape shape, @NotNull Measure offset) {
        if (shape == this) {
            throw new UnsupportedOperationException(CANNOT_BE_ADJACENT_TO_ITSELF);
        }
        this.neighbor = shape;
        this.angleToNeighbor = DrawlNumber.valueOf(90);

        // Set the x position of this shape
        DrawlNumber leftBoundaryOfShape = shape.getImplicitXMinimum();
        DrawlNumber thisImplicitXPosition = leftBoundaryOfShape.subtract(this.getImplicitHalfWidth(),
                DrawlNumber.mcOperations);

        thisImplicitXPosition = thisImplicitXPosition.subtract(offset.toDrawlNumber());
        this.setImplicitXPositionCenter(thisImplicitXPosition);


        // Set the y position of this shape to match the one it is to the left of
        this.setImplicitYPositionCenter(shape.getImplicitYPositionCenter());
        this.setExplicitYPositionCenter(shape.getExplicitYPositionCenter());
    }

    /**
     * Sets this Shape's neighbor to the left with an offset. (This Shape is to the right of that one by an offset.)
     *
     * @param shape  The Shape to set to the right of which this one will be set.
     * @param offset The distance to offset the other Shape from this one.
     */
    public void setRightOf(@NotNull Shape shape, @NotNull Measure offset) {
        if (shape == this) {
            throw new UnsupportedOperationException(CANNOT_BE_ADJACENT_TO_ITSELF);
        }
        this.neighbor = shape;
        this.angleToNeighbor = DrawlNumber.valueOf(270);

        // Set the x position of this Shape
        DrawlNumber rightBoundaryOfShape = shape.getImplicitXMaximum();
        DrawlNumber thisImplicitXPosition = rightBoundaryOfShape.add(this.getImplicitHalfWidth(),
                DrawlNumber.mcOperations);
        thisImplicitXPosition = thisImplicitXPosition.add(offset.toDrawlNumber());
        this.setImplicitXPositionCenter(thisImplicitXPosition);

        // Set the y position of this shape to match the one it is to the left of
        this.setImplicitYPositionCenter(shape.getImplicitYPositionCenter());
        this.setExplicitYPositionCenter(shape.getExplicitYPositionCenter());
    }
}
