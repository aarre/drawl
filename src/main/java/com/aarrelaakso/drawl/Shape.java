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

public class Shape
{

    private static final String CANNOT_BE_ADJACENT_TO_ITSELF = "A circle cannot be adjacent to itself";
    private static final FluentLogger logger;

    static
    {
        logger = FluentLogger.forEnclosingClass();

    }

    /**
     * The angle, in degrees, to a neighbor. 0 represents up, and 90 degrees represents to the right.
     */
    private DrawlNumber angleToNeighbor;

    /**
     * The explicit height of a Shape defaults to <code>null</code> to indicate it that has not yet been set.
     */
    private DrawlNumber explicitHeight;

    /**
     * The explicit width of a Shape defaults to <code>null</code> to indicate that it has not yet been set.
     */
    private DrawlNumber explicitWidth;

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
    private Text text;

    public void addText(@Nullable Text text)
    {
        this.text = text;
    }

    /**
     * Get this Shape's neighbor above (this Shape is below that one), if any.
     *
     * @return the Shape to the right of this one, if any;
     * <code>null</code> otherwise.
     */
    public Shape getAbove()
    {
        Shape returnValue = null;
        if (this.angleToNeighbor.equals(DrawlNumber.ZERO))
        {
            returnValue = this.neighbor;
        }
        return returnValue;
    }

    /**
     * Get this Shape's neighbor below (this Shape is below that one), if any.
     *
     * @return the Shape to below this one, if any;
     * <code>null</code> otherwise.
     */
    public Shape getBelow()
    {
        Shape returnValue = null;
        if (this.angleToNeighbor.equals(DrawlNumber.valueOf(180)))
        {
            returnValue = this.neighbor;
        }
        return returnValue;
    }

    protected DrawlNumber getExplicitHalfHeight()
    {
        if (this.getExplicitHeight() == null)
        {
            throw new UnsupportedOperationException("Cannot calculate explicit height without dimensions");
        }
        return this.getExplicitHeight().divide(DrawlNumber.valueOf(2), DrawlNumber.mcOperations);
    }


    protected DrawlNumber getExplicitHalfWidth()
    {
        if (this.getExplicitWidth() == null)
        {
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
    @Nullable
    protected DrawlNumber getExplicitHeight()
    {
        return this.explicitHeight;
    }

    /**
     * Get the explicit width of this Shape.
     *
     * @return the explicit width of this Shape, or <code>null</code> if this Shape has not yet been assigned an
     * explicit width.
     */
    @Nullable
    protected DrawlNumber getExplicitWidth()
    {
        return this.explicitWidth;
    }

    /**
     * Gets the explicit x-position of this Shape.
     *
     * @return the explicit x-position of this Shape.
     */
    @NotNull
    protected DrawlNumber getExplicitXPositionCenter()
    {
        return this.explicitXPositionCenter;
    }

    protected DrawlNumber getExplicitXPositionLeft()
    {
        return this.explicitXPositionCenter.subtract(this.getExplicitHalfWidth());
    }

    /**
     * Gets the explicit y position of the bottom of this Shape.
     *
     * @return the explicit y position of the bottom of this Shape.
     */
    @NotNull
    protected DrawlNumber getExplicitYPositionBottom()
    {
        return this.getExplicitYPositionCenter().subtract(this.getExplicitHalfHeight());
    }

    /**
     * Gets the explicit y-position of the center of this Shape.
     *
     * @return the explicit y-position of the center of this Shape.
     */
    @NotNull
    protected DrawlNumber getExplicitYPositionCenter()
    {
        return this.explicitYPositionCenter;
    }

    /**
     * Gets the explicit y-position of the top of this Shape.
     *
     * @return the explicit y-position of the top of this Shape.
     */
    @NotNull
    protected DrawlNumber getExplicitYPositionTop()
    {
        return this.getExplicitYPositionCenter().subtract(this.getExplicitHalfHeight());
    }

    /**
     * Returns the fill associated with this Shape, if any.
     *
     * @return the fill associated with this Shape, or null if no fill has been associated with this Shape.
     */
    @Nullable
    public String getFill()
    {
        return this.fill;
    }

    protected DrawlNumber getImplicitHalfHeight()
    {
        return this.getImplicitHeight().divide(DrawlNumber.TWO, DrawlNumber.mcOperations);
    }

    protected DrawlNumber getImplicitHalfWidth()
    {
        return this.getImplicitWidth().divide(DrawlNumber.TWO, DrawlNumber.mcOperations);
    }

    /**
     * Get the implicit height of this Shape
     *
     * @return the implicit height of this Shape
     */
    protected DrawlNumber getImplicitHeight()
    {
        return this.implicitHeight;
    }

    /**
     * Get the implicit width of this Shape
     *
     * @return the implicit width of this Shape
     */
    protected DrawlNumber getImplicitWidth()
    {
        return this.implicitWidth;
    }

    /**
     * Get the implicit maximum (rightmost) x-position of this Shape.
     *
     * @return The implicit maximum (rightmost) x-position of this Shape.
     */
    protected DrawlNumber getImplicitXMaximum()
    {
        return this.getImplicitXPositionCenter().add(this.getImplicitHalfWidth());
    }

    /**
     * Get the implicit minimum (leftmost) x-position of this Shape.
     *
     * @return The implicit minimum (leftmost) x-position of this Shape.
     */
    protected DrawlNumber getImplicitXMinimum()
    {
        return this.getImplicitXPositionCenter().subtract(this.getImplicitHalfWidth());
    }

    /**
     * Get the implicit x position of the center of this Shape.
     *
     * @return The implicit x position of the center of this Shape.
     */
    protected DrawlNumber getImplicitXPositionCenter()
    {
        return this.implicitXPositionCenter;
    }

    /**
     * Get the implicit x position of the left edge of this Shape.
     *
     * @return The implicit x position of the left edge of this Shape.
     */
    protected DrawlNumber getImplicitXPositionLeft()
    {
        return this.getImplicitXPositionCenter().subtract(this.getImplicitHalfWidth());
    }

    /**
     * Get the implicit bottommost y-position of this Shape. In implicit coordinates, this is the minimum y-position.
     *
     * @return The implicit minimum (bottommost) y-position of this Shape.
     */
    protected DrawlNumber getImplicitYPositionBottom()
    {
        return this.getImplicitYPositionCenter().subtract(this.getImplicitHalfHeight());
    }

    /**
     * Get the implicit y position of the center of this Shape.
     *
     * @return The implicit y position of the center of this Shape.
     */
    protected DrawlNumber getImplicitYPositionCenter()
    {
        return this.implicitYPositionCenter;
    }

    /**
     * Gets the implicit topmost y position of this Shape. In implicit coordinates, this is the maximum y position.
     *
     * @return The implicit maximum (topmost) y-position of this Shape.
     */
    protected DrawlNumber getImplicitYPositionTop()
    {
        return this.getImplicitYPositionCenter().add(this.getImplicitHalfHeight());
    }

    /**
     * Get this Shape's neighbor to the right (this Shape is to the left of that one), if any.
     *
     * @return the Shape to the right of this one, if any;
     * <code>null</code> otherwise.
     */
    public Shape getLeftOf()
    {
        Shape returnValue = null;
        if (this.angleToNeighbor == null)
        {
            returnValue = null;
        }
        else if (this.angleToNeighbor.equals(DrawlNumber.valueOf(90)))
        {
            returnValue = this.neighbor;
        }
        else
        {
            returnValue = null;
        }
        return returnValue;
    }

    /**
     * Get this Shape's neighbor to the left (this Shape is to the right of that one), if any.
     *
     * @return the Shape to the left of this one, if any;
     * <code>null</code> otherwise.
     */
    public Shape getRightOf()
    {
        Shape returnValue = null;
        if (this.angleToNeighbor == null)
        {
            returnValue = null;
        }
        else if (this.angleToNeighbor.isEqualTo(DrawlNumber.valueOf(270)))
        {
            returnValue = this.neighbor;
        }
        else
        {
            returnValue = null;
        }
        return returnValue;
    }

    public String getSVG()
    {
        return "oops";
    }

    /**
     * Gets the stroke of this Shape.
     *
     * @return The stroke of this shape, or null if the stroke of this Shape has not been set.
     */
    @Nullable
    public String getStroke()
    {
        return this.stroke;
    }

    /**
     * Returns a Text object that belongs to this Shape, if there is one.
     *
     * @return A Text object that belongs to this Shape, or <code>null</code> if this Shape does not have a Text object
     * associated with it.
     */
    @Nullable
    public Text getText()
    {
        return this.text;
    }

    /**
     * Indicates whether this shape has a Text object associated with it.
     *
     * @return TRUE if this shape has a Text object associated with it, FALSE otherwise.
     */
    @NotNull
    public Boolean hasText()
    {
        if (this.getText() == null)
        {
            return Boolean.FALSE;
        }
        else
        {
            return Boolean.TRUE;
        }
    }

    /**
     * Set this Shape above another Shape.
     *
     * @param shape The circle that will be below this one.
     */
    public void setAbove(Shape shape)
    {
        if (shape == this)
        {
            throw new UnsupportedOperationException(CANNOT_BE_ADJACENT_TO_ITSELF);
        }
        this.neighbor = shape;
        this.angleToNeighbor = DrawlNumber.valueOf(0);
        DrawlNumber topBoundaryOfShape = shape.getImplicitYPositionTop();
        DrawlNumber thisImplicitYPosition = topBoundaryOfShape.add(this.getImplicitHalfHeight(),
                DrawlNumber.mcOperations);
        this.setImplicitYPositionCenter(thisImplicitYPosition);
    }

    /**
     * Set this circle below another circle.
     *
     * @param shape The circle that will be above this one.
     */
    public void setBelow(Shape shape)
    {
        if (shape == this)
        {
            throw new UnsupportedOperationException(CANNOT_BE_ADJACENT_TO_ITSELF);
        }
        this.neighbor = shape;
        this.angleToNeighbor = DrawlNumber.valueOf(180);
        DrawlNumber bottomBoundaryOfShape = shape.getImplicitYPositionBottom();
        DrawlNumber thisImplicitYPosition = bottomBoundaryOfShape.subtract(this.getImplicitHalfHeight(),
                DrawlNumber.mcOperations);
        this.setImplicitYPositionCenter(thisImplicitYPosition);
    }

    /**
     * Set the height of this Shape to an explicit value.
     * <p>
     *
     * @param height The new height of this Shape. Can be <code>null</code> to indicate that this Shape has not yet
     *               been assigned an explicit height.
     */
    protected void setExplicitHeight(@Nullable DrawlNumber height)
    {
        this.explicitHeight = height;
        if (Boolean.TRUE.equals(this.hasText()))
        {
            this.getText().setExplicitHeight(height);
        }
    }

    /**
     * Set the width of this Shape to an explicit value.
     * <p>
     *
     * @param width the new width of this Shape, or <code>null</code> to indicate that this Shape has not yet
     *              been assigned an explicit width.
     */
    protected void setExplicitWidth(@Nullable DrawlNumber width)
    {
        this.explicitWidth = width;
        if (Boolean.TRUE.equals(this.hasText()))
        {
            this.getText().setExplicitWidth(width);
        }
    }

    /**
     * Sets the explicit center position of this Shape.
     * @param x
     */
    protected void setExplicitXPositionCenter(DrawlNumber x)
    {
        this.explicitXPositionCenter = x;
        if(Boolean.TRUE.equals(this.hasText()))
        {
            this.getText().setExplicitXPositionCenter(x);
        }
    }

    protected void setExplicitXPositionCenter(Integer x)
    {
        setExplicitXPositionCenter(DrawlNumber.valueOf(x));
    }

    protected void setExplicitYPositionCenter(Integer y)
    {
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
    protected void setExplicitYPositionCenter(DrawlNumber y)
    {
        this.explicitYPositionCenter = y;
        if(Boolean.TRUE.equals(this.hasText()))
        {
            this.getText().setExplicitYPositionCenter(y);
        }
    }

    /**
     * Set the fill of this Shape.
     *
     * @param s A string representing a fill color, e.g., "white".
     */
    public void setFill(String s)
    {
        this.fill = s;
    }

    protected final void setImplicitHeight(DrawlNumber implicitHeight)
    {
        logger.atFine().log("Setting implicit height to %s", implicitHeight.toPlainString());
        this.implicitHeight = implicitHeight;
    }

    protected final void setImplicitWidth(DrawlNumber implicitWidth)
    {
        logger.atFine().log("Setting implicit width to %s", implicitWidth.toPlainString());
        this.implicitWidth = implicitWidth;
    }

    protected void setImplicitXPositionCenter(DrawlNumber x)
    {
        this.implicitXPositionCenter = x;
        if(Boolean.TRUE.equals(this.hasText()))
        {
            this.getText().setImplicitXPositionCenter(x);
        }
    }

    /**
     * Set the implicit y position of this Shape.
     * <p>
     * The y position marks the vertical position of the Shape's center. In the implicit coordinate system, higher
     * values of y are upward whereas lower values of y are downward.
     *
     * @param y The implicit y position of this Shape.
     */
    protected void setImplicitYPositionCenter(DrawlNumber y)
    {
        this.implicitYPositionCenter = y;
        if(Boolean.TRUE.equals(this.hasText()))
        {
            this.getText().setImplicitYPositionCenter(y);
        }
    }

    /**
     * Set this Shape's neighbor to the right (this Shape is to the left of that one).
     *
     * @param shape the Shape to the right of this one
     */
    public void setLeftOf(Shape shape)
    {
        if (shape == this)
        {
            throw new UnsupportedOperationException(CANNOT_BE_ADJACENT_TO_ITSELF);
        }
        this.neighbor = shape;
        this.angleToNeighbor = DrawlNumber.valueOf(90);

        // Set the x position of this shape
        DrawlNumber leftBoundaryOfShape = shape.getImplicitXMinimum();
        DrawlNumber thisImplicitXPosition = leftBoundaryOfShape.subtract(this.getImplicitHalfWidth(),
                DrawlNumber.mcOperations);
        this.setImplicitXPositionCenter(thisImplicitXPosition);

        // Set the y position of this shape to match the one it is to the left of
        this.setImplicitYPositionCenter(shape.getImplicitYPositionCenter());
        this.setExplicitYPositionCenter(shape.getExplicitYPositionCenter());
    }

    /**
     * Set this Shape's neighbor to the left (this Shape is to the right of that one).
     *
     * @param shape the Shape to the left of this one
     */
    public void setRightOf(Shape shape)
    {
        if (shape == this)
        {
            throw new UnsupportedOperationException(CANNOT_BE_ADJACENT_TO_ITSELF);
        }
        this.neighbor = shape;
        this.angleToNeighbor = DrawlNumber.valueOf(270);

        // Set this x position of this shape
        DrawlNumber rightBoundaryOfShape = shape.getImplicitXMaximum();
        DrawlNumber thisImplicitXPosition = rightBoundaryOfShape.add(this.getImplicitHalfWidth(),
                DrawlNumber.mcOperations);
        this.setImplicitXPositionCenter(thisImplicitXPosition);

    }

    /**
     * Sets the stroke of this shape.
     *
     * @param s A stroke name.
     */
    public void setStroke(String s)
    {
        this.stroke = s;
    }
}
