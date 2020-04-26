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

import java.math.BigDecimal;

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
    private BigDecimal angleToNeighbor;

    /**
     * The explicit height of a Shape defaults to <code>null</code> to indicate it that has not yet been set.
     */
    private BigDecimal explicitHeight;

    /**
     * The explicit width of a Shape defaults to <code>null</code> to indicate that it has not yet been set.
     */
    private BigDecimal explicitWidth;

    /**
     * A default Shape is centered at (0,0) in both explicit and implicit coordinates.
     */
    private BigDecimal explicitXPositionCenter = BigDecimal.ZERO;

    /**
     * A default Shape is centered at (0,0) in both explicit and implicit coordinates.
     */
    private BigDecimal explicitYPositionCenter = BigDecimal.ZERO;

    /**
     * The implicit height of a default Shape is 1.
     */
    private BigDecimal implicitHeight = BigDecimal.ONE;

    /**
     * The implicit width of a default Shape is 1.
     */
    private BigDecimal implicitWidth = BigDecimal.ONE;

    /**
     * A default Shape is centered at (0,0) in both explicit and implicit coordinates.
     */
    private BigDecimal implicitXPositionCenter = BigDecimal.ZERO;

    /**
     * A default Shape is centered at (0,0) in both explicit and implicit coordinates.
     */
    private BigDecimal implicitYPositionCenter = BigDecimal.ZERO;

    /**
     * A shape adjacent to this one, if any
     */
    private Shape neighbor;

    /**
     * Get this Shape's neighbor above (this Shape is below that one), if any.
     *
     * @return the Shape to the right of this one, if any;
     * <code>null</code> otherwise.
     */
    public Shape getAbove()
    {
        Shape returnValue = null;
        if (this.angleToNeighbor.equals(BigDecimal.valueOf(0)))
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
        if (this.angleToNeighbor.equals(BigDecimal.valueOf(180)))
        {
            returnValue = this.neighbor;
        }
        return returnValue;
    }

    protected BigDecimal getExplicitHalfHeight()
    {
        if (this.getExplicitHeight() == null)
        {
            throw new UnsupportedOperationException("Cannot calculate explicit height without dimensions");
        }
        return this.getExplicitHeight().divide(BigDecimal.valueOf(2), SisuBigDecimal.mcOperations);
    }



    protected BigDecimal getExplicitHalfWidth()
    {
        if (this.getExplicitWidth() == null)
        {
            throw new UnsupportedOperationException("Cannot calculate explicit width without dimensions");
        }
        return this.getExplicitWidth().divide(BigDecimal.valueOf(2), SisuBigDecimal.mcOperations);
    }

    /**
     * Get the explicit height of this Shape.
     *
     * @return the explicit height of this Shape, or <code>null</code> if this Shape has not yet been assigned an
     * explicit height.
     */
    // TODO [Issue #1] Make this method protected and factor out of unit tests.
    @Nullable
    public BigDecimal getExplicitHeight()
    {
        return this.explicitHeight;
    }

    /**
     * Get the explicit width of this Shape.
     *
     * @return the explicit width of this Shape, or <code>null</code> if this Shape has not yet been assigned an
     * explicit width.
     */
    // TODO [Issue #1] Make this method protected and factor out of unit tests.
    @Nullable
    public BigDecimal getExplicitWidth()
    {
        return this.explicitWidth;
    }

    /**
     * Get the explicit x-position of this Shape.
     *
     * @return the explicit x-position of this Shape.
     */
    // TODO [Issue #1] Make this method protected and factor out of unit tests.
    @NotNull
    public BigDecimal getExplicitXPositionCenter()
    {
        return this.explicitXPositionCenter;
    }

    public BigDecimal getExplicitXPositionLeft()
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
    public BigDecimal getExplicitYPositionCenter()
    {
        logger.atFine().log("Returning explicit y position of Shape %s as: %f", this.toString(),
                this.explicitYPositionCenter.floatValue());
        return this.explicitYPositionCenter;
    }

    public BigDecimal getExplicitYPositionTop()
    {
        return this.explicitYPositionCenter.subtract(this.getExplicitHalfHeight());
    }

    protected BigDecimal getImplicitHalfHeight()
    {
        return this.getImplicitHeight().divide(BigDecimal.valueOf(2), SisuBigDecimal.mcOperations);
    }

    protected BigDecimal getImplicitHalfWidth()
    {
        return this.getImplicitWidth().divide(BigDecimal.valueOf(2), SisuBigDecimal.mcOperations);
    }

    /**
     * Get the implicit height of this Shape
     *
     * @return the implicit height of this Shape
     */
    public BigDecimal getImplicitHeight()
    {
        return this.implicitHeight;
    }

    /**
     * Get the implicit width of this Shape
     *
     * @return the implicit width of this Shape
     */
    public BigDecimal getImplicitWidth()
    {
        return this.getImplicitHeight();
    }

    /**
     * Get the implicit maximum (rightmost) x-position of this Shape.
     *
     * @return The implicit maximum (rightmost) x-position of this Shape.
     */
    protected BigDecimal getImplicitXMaximum()
    {
        return this.getImplicitXPositionCenter().add(this.getImplicitHalfWidth());
    }

    /**
     * Get the implicit minimum (leftmost) x-position of this Shape.
     *
     * @return The implicit minimum (leftmost) x-position of this Shape.
     */
    protected BigDecimal getImplicitXMinimum()
    {
        return this.getImplicitXPositionCenter().subtract(this.getImplicitHalfWidth());
    }

    /**
     * Get the implicit x position of the center of this Shape.
     *
     * @return The implicit x position of the center of this Shape.
     */
    public BigDecimal getImplicitXPositionCenter()
    {
        return this.implicitXPositionCenter;
    }

    /**
     * Get the implicit x position of the left edge of this Shape.
     *
     * @return The implicit x position of the left edge of this Shape.
     */
    public BigDecimal getImplicitXPositionLeft()
    {
        return this.getImplicitXPositionCenter().subtract(this.getImplicitHalfWidth());
    }

    /**
     * Get the implicit maximum (topmost) y-position of this Shape.
     *
     * @return The implicit maximum (topmost) x-position of this Shape.
     */
    protected BigDecimal getImplicitYMaximum()
    {
        return this.getImplicitYPositionCenter().add(this.getImplicitHalfHeight());
    }

    /**
     * Get the implicit minimum (bottommost) y-position of this Shape.
     *
     * @return The implicit minimum (bottommost) y-position of this Shape.
     */
    protected BigDecimal getImplicitYMinimum()
    {
        return this.getImplicitYPositionCenter().subtract(this.getImplicitHalfHeight());
    }

    /**
     * Get the implicit y position of the center of this Shape.
     *
     * @return The implicit y position of the center of this Shape.
     */
    public BigDecimal getImplicitYPositionCenter()
    {
        return this.implicitYPositionCenter;
    }

    /**
     * Get the implicit y position of the top of this Shape.
     *
     * @return The implicit y position of the top of this Shape.
     */
    public BigDecimal getImplicitYPositionTop()
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
        else if (this.angleToNeighbor.equals(BigDecimal.valueOf(90)))
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
        else if (this.angleToNeighbor.compareTo(BigDecimal.valueOf(270)) == 0)
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
        this.angleToNeighbor = BigDecimal.valueOf(0);
        BigDecimal topBoundaryOfShape = shape.getImplicitYMaximum();
        BigDecimal thisImplicitYPosition = topBoundaryOfShape.add(this.getImplicitHalfHeight(),
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
        this.angleToNeighbor = BigDecimal.valueOf(180);
        BigDecimal bottomBoundaryOfShape = shape.getImplicitYMinimum();
        BigDecimal thisImplicitYPosition = bottomBoundaryOfShape.subtract(this.getImplicitHalfHeight(),
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
    // TODO [Issue #1] Make this method protected and factor out of unit tests.
    public void setExplicitHeight(@Nullable BigDecimal height)
    {
        this.explicitHeight = height;
    }

    /**
     * Set the width of this Shape to an explicit value.
     * <p>
     *
     * @param width the new width of this Shape, or <code>null</code> to indicate that this Shape has not yet
     *              been assigned an explicit width.
     */
    // TODO [Issue #1] Make this method protected and factor out of unit tests.
    public void setExplicitWidth(@Nullable BigDecimal width)
    {
        this.explicitWidth = width;
    }

    protected void setExplicitXPositionCenter(BigDecimal x)
    {
        this.explicitXPositionCenter = x;
    }

    public void setExplicitXPositionCenter(Integer x)
    {
        this.explicitXPositionCenter = BigDecimal.valueOf(x);
    }

    public void setExplicitYPosition(Integer y)
    {
        this.setExplicitYPositionCenter(BigDecimal.valueOf(y));
    }

    /**
     * Set the explicit y position of this Shape.
     * <p>
     * The y position marks the vertical position of the Shape's center. The explicit coordinate system is the
     * common Cartesian coordinate system, with higher values of y upward and lower values of y downward.
     *
     * @param y The explicit y position of this Shape.
     */
    protected void setExplicitYPositionCenter(BigDecimal y)
    {
        this.explicitYPositionCenter = y;
        logger.atFine().log("Setting explicit y position of Shape %s to: %f", this.toString(), y.floatValue());
    }

    public void setImplicitXPositionCenter(BigDecimal x)
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
    public void setImplicitYPositionCenter(BigDecimal y)
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
        this.angleToNeighbor = BigDecimal.valueOf(90);

        // Set the x position of this shape
        BigDecimal leftBoundaryOfShape = shape.getImplicitXMinimum();
        BigDecimal thisImplicitXPosition = leftBoundaryOfShape.subtract(this.getImplicitHalfWidth(),
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
        this.angleToNeighbor = BigDecimal.valueOf(270);

        // Set this x position of this shape
        BigDecimal rightBoundaryOfShape = shape.getImplicitXMaximum();
        BigDecimal thisImplicitXPosition = rightBoundaryOfShape.add(this.getImplicitHalfWidth(),
                SisuBigDecimal.mcOperations);
        this.setImplicitXPositionCenter(thisImplicitXPosition);

    }
}
