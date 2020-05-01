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
    private SisuBigDecimal angleToNeighbor;

    /**
     * The explicit height of a Shape defaults to <code>null</code> to indicate it that has not yet been set.
     */
    private SisuBigDecimal explicitHeight;

    /**
     * The explicit width of a Shape defaults to <code>null</code> to indicate that it has not yet been set.
     */
    private SisuBigDecimal explicitWidth;

    /**
     * A default Shape is centered at (0,0) in both explicit and implicit coordinates.
     */
    private SisuBigDecimal explicitXPositionCenter = SisuBigDecimal.ZERO;

    /**
     * A default Shape is centered at (0,0) in both explicit and implicit coordinates.
     */
    private SisuBigDecimal explicitYPositionCenter = SisuBigDecimal.ZERO;

    /**
     * The implicit height of a default Shape is 1.
     */
    private SisuBigDecimal implicitHeight = SisuBigDecimal.ONE;
    //private SisuBigDecimal implicitHeight;

    /**
     * The implicit width of a default Shape is 1.
     */
    private SisuBigDecimal implicitWidth = SisuBigDecimal.ONE;
    //private SisuBigDecimal implicitWidth;

    /**
     * A default Shape is centered at (0,0) in both explicit and implicit coordinates.
     */
    private SisuBigDecimal implicitXPositionCenter = SisuBigDecimal.ZERO;

    /**
     * A default Shape is centered at (0,0) in both explicit and implicit coordinates.
     */
    private SisuBigDecimal implicitYPositionCenter = SisuBigDecimal.ZERO;

    /**
     * A shape adjacent to this one, if any
     */
    private Shape neighbor;

    /**
     * The fill of this Shape. Defaults to null, meaning the SVG default.
     */
    private String fill;

    /**
     * The stroke of this Shape. Defaults to null, meaning the SVG default.
     */
    private String stroke;

    /**
     * Get this Shape's neighbor above (this Shape is below that one), if any.
     *
     * @return the Shape to the right of this one, if any;
     * <code>null</code> otherwise.
     */
    public Shape getAbove()
    {
        Shape returnValue = null;
        if (this.angleToNeighbor.equals(SisuBigDecimal.ZERO))
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
        if (this.angleToNeighbor.equals(SisuBigDecimal.valueOf(180)))
        {
            returnValue = this.neighbor;
        }
        return returnValue;
    }

    protected SisuBigDecimal getExplicitHalfHeight()
    {
        if (this.getExplicitHeight() == null)
        {
            throw new UnsupportedOperationException("Cannot calculate explicit height without dimensions");
        }
        return this.getExplicitHeight().divide(SisuBigDecimal.valueOf(2), SisuBigDecimal.mcOperations);
    }



    protected SisuBigDecimal getExplicitHalfWidth()
    {
        if (this.getExplicitWidth() == null)
        {
            throw new UnsupportedOperationException("Cannot calculate explicit width without dimensions");
        }
        return this.getExplicitWidth().divide(SisuBigDecimal.valueOf(2), SisuBigDecimal.mcOperations);
    }

    /**
     * Get the explicit height of this Shape.
     *
     * @return the explicit height of this Shape, or <code>null</code> if this Shape has not yet been assigned an
     * explicit height.
     */
    // TODO [Issue #1] Make this method protected and factor out of unit tests.
    @Nullable
    protected SisuBigDecimal getExplicitHeight()
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
    protected SisuBigDecimal getExplicitWidth()
    {
        return this.explicitWidth;
    }

    /**
     * Get the explicit x-position of this Shape.
     *
     * @return the explicit x-position of this Shape.
     */
    @NotNull
    protected SisuBigDecimal getExplicitXPositionCenter()
    {
        return this.explicitXPositionCenter;
    }

    protected SisuBigDecimal getExplicitXPositionLeft()
    {
        return this.explicitXPositionCenter.subtract(this.getExplicitHalfWidth());
    }

    /**
     * Get the explicit y-position of this Shape.
     *
     * @return the explicit y-position of this Shape.
     */
    // TODO [Issue #1] Make this method protected and factor out of unit tests.
    @NotNull
    protected SisuBigDecimal getExplicitYPositionCenter()
    {
        return this.explicitYPositionCenter;
    }

    protected SisuBigDecimal getExplicitYPositionTop()
    {
        return this.explicitYPositionCenter.subtract(this.getExplicitHalfHeight());
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

    protected SisuBigDecimal getImplicitHalfHeight()
    {
        return this.getImplicitHeight().divide(SisuBigDecimal.valueOf(2), SisuBigDecimal.mcOperations);
    }

    protected SisuBigDecimal getImplicitHalfWidth()
    {
        return this.getImplicitWidth().divide(SisuBigDecimal.valueOf(2), SisuBigDecimal.mcOperations);
    }

    /**
     * Get the implicit height of this Shape
     *
     * @return the implicit height of this Shape
     */
    protected SisuBigDecimal getImplicitHeight()
    {
        return this.implicitHeight;
    }

    /**
     * Get the implicit width of this Shape
     *
     * @return the implicit width of this Shape
     */
    protected SisuBigDecimal getImplicitWidth()
    {
        return this.implicitWidth;
    }

    /**
     * Get the implicit maximum (rightmost) x-position of this Shape.
     *
     * @return The implicit maximum (rightmost) x-position of this Shape.
     */
    protected SisuBigDecimal getImplicitXMaximum()
    {
        return this.getImplicitXPositionCenter().add(this.getImplicitHalfWidth());
    }

    /**
     * Get the implicit minimum (leftmost) x-position of this Shape.
     *
     * @return The implicit minimum (leftmost) x-position of this Shape.
     */
    protected SisuBigDecimal getImplicitXMinimum()
    {
        return this.getImplicitXPositionCenter().subtract(this.getImplicitHalfWidth());
    }

    /**
     * Get the implicit x position of the center of this Shape.
     *
     * @return The implicit x position of the center of this Shape.
     */
    protected SisuBigDecimal getImplicitXPositionCenter()
    {
        return this.implicitXPositionCenter;
    }

    /**
     * Get the implicit x position of the left edge of this Shape.
     *
     * @return The implicit x position of the left edge of this Shape.
     */
    protected SisuBigDecimal getImplicitXPositionLeft()
    {
        return this.getImplicitXPositionCenter().subtract(this.getImplicitHalfWidth());
    }

    /**
     * Get the implicit maximum (topmost) y-position of this Shape.
     *
     * @return The implicit maximum (topmost) x-position of this Shape.
     */
    protected SisuBigDecimal getImplicitYMaximum()
    {
        return this.getImplicitYPositionCenter().add(this.getImplicitHalfHeight());
    }

    /**
     * Get the implicit minimum (bottommost) y-position of this Shape.
     *
     * @return The implicit minimum (bottommost) y-position of this Shape.
     */
    protected SisuBigDecimal getImplicitYMinimum()
    {
        return this.getImplicitYPositionCenter().subtract(this.getImplicitHalfHeight());
    }

    /**
     * Get the implicit y position of the center of this Shape.
     *
     * @return The implicit y position of the center of this Shape.
     */
    protected SisuBigDecimal getImplicitYPositionCenter()
    {
        return this.implicitYPositionCenter;
    }

    /**
     * Get the implicit y position of the top of this Shape.
     *
     * @return The implicit y position of the top of this Shape.
     */
    protected SisuBigDecimal getImplicitYPositionTop()
    {
        return this.getImplicitYPositionCenter().subtract(this.getImplicitHalfHeight());
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
        else if (this.angleToNeighbor.equals(SisuBigDecimal.valueOf(90)))
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
        else if (this.angleToNeighbor.isEqualTo(SisuBigDecimal.valueOf(270)))
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
     * Gets the stroke of this Shape.
     *
     * @return The stroke of this shape, or null if the stroke of this Shape has not been set.
     */
    @Nullable
    public String getStroke()
    {
        return this.stroke;
    }

    public String getSVG()
    {
        return "oops";
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
        this.angleToNeighbor = SisuBigDecimal.valueOf(0);
        SisuBigDecimal topBoundaryOfShape = shape.getImplicitYMaximum();
        SisuBigDecimal thisImplicitYPosition = topBoundaryOfShape.add(this.getImplicitHalfHeight(),
                SisuBigDecimal.mcOperations);
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
        this.angleToNeighbor = SisuBigDecimal.valueOf(180);
        SisuBigDecimal bottomBoundaryOfShape = shape.getImplicitYMinimum();
        SisuBigDecimal thisImplicitYPosition = bottomBoundaryOfShape.subtract(this.getImplicitHalfHeight(),
                SisuBigDecimal.mcOperations);
        this.setImplicitYPositionCenter(thisImplicitYPosition);
    }

    /**
     * Set the height of this Shape to an explicit value.
     * <p>
     *
     * @param height The new height of this Shape. Can be <code>null</code> to indicate that this Shape has not yet
     *               been assigned an explicit height.
     */
    protected void setExplicitHeight(@Nullable SisuBigDecimal height)
    {
        logger.atFine().log("Setting explicit height of shape to %s", height.toPlainString());
        this.explicitHeight = height;
    }

    /**
     * Set the width of this Shape to an explicit value.
     * <p>
     *
     * @param width the new width of this Shape, or <code>null</code> to indicate that this Shape has not yet
     *              been assigned an explicit width.
     */
    protected void setExplicitWidth(@Nullable SisuBigDecimal width)
    {
        logger.atFine().log("Setting explicit width of shape to %s", width.toPlainString());
        this.explicitWidth = width;
    }

    protected void setExplicitXPositionCenter(SisuBigDecimal x)
    {
        this.explicitXPositionCenter = x;
        logger.atFine().log("Setting explicit x position of Shape %s to: %f", this.toString(), x.floatValue());
    }

    protected void setExplicitXPositionCenter(Integer x)
    {
        this.explicitXPositionCenter = SisuBigDecimal.valueOf(x);
    }

    protected void setExplicitYPosition(Integer y)
    {
        this.setExplicitYPositionCenter(SisuBigDecimal.valueOf(y));
    }

    /**
     * Set the explicit y position of this Shape.
     * <p>
     * The y position marks the vertical position of the Shape's center. The explicit coordinate system is the
     * common Cartesian coordinate system, with higher values of y upward and lower values of y downward.
     *
     * @param y The explicit y position of this Shape.
     */
    protected void setExplicitYPositionCenter(SisuBigDecimal y)
    {
        this.explicitYPositionCenter = y;
        logger.atFine().log("Setting explicit y position of Shape %s to: %f", this.toString(), y.floatValue());
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

    protected final void setImplicitHeight(SisuBigDecimal implicitHeight)
    {
        logger.atFine().log("Setting implicit height to %s", implicitHeight.toPlainString());
        this.implicitHeight = implicitHeight;
    }

    protected final void setImplicitWidth(SisuBigDecimal implicitWidth)
    {
        logger.atFine().log("Setting implicit width to %s", implicitWidth.toPlainString());
        this.implicitWidth = implicitWidth;
    }

    protected void setImplicitXPositionCenter(SisuBigDecimal x)
    {
        this.implicitXPositionCenter = x;
    }

    /**
     * Set the implicit y position of this Shape.
     * <p>
     * The y position marks the vertical position of the Shape's center. In the implicit coordinate system, higher
     * values of y are upward whereas lower values of y are downward.
     *
     * @param y The implicit y position of this Shape.
     */
    protected void setImplicitYPositionCenter(SisuBigDecimal y)
    {
        this.implicitYPositionCenter = y;
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
        this.angleToNeighbor = SisuBigDecimal.valueOf(90);

        // Set the x position of this shape
        SisuBigDecimal leftBoundaryOfShape = shape.getImplicitXMinimum();
        SisuBigDecimal thisImplicitXPosition = leftBoundaryOfShape.subtract(this.getImplicitHalfWidth(),
                SisuBigDecimal.mcOperations);
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
        this.angleToNeighbor = SisuBigDecimal.valueOf(270);

        // Set this x position of this shape
        SisuBigDecimal rightBoundaryOfShape = shape.getImplicitXMaximum();
        SisuBigDecimal thisImplicitXPosition = rightBoundaryOfShape.add(this.getImplicitHalfWidth(),
                SisuBigDecimal.mcOperations);
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
