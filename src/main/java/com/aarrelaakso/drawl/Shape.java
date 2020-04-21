package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class Shape {
    private static final String CANNOT_BE_ADJACENT_TO_ITSELF = "A circle cannot be adjacent to itself";
    private BigDecimal angleToNeighbor = null;
    /**
     * The explicit height of a default Shape is 1 in both explicit and implicit coordinates.
     */
    private BigDecimal explicitHeight = BigDecimal.ONE;
    private BigDecimal explicitWidth = BigDecimal.ONE;
    private BigDecimal implicitHeight = BigDecimal.ONE;
    private BigDecimal implicitWidth = BigDecimal.ONE;
    private BigDecimal implicitXPosition = BigDecimal.ZERO;
    private BigDecimal implicitYPosition = BigDecimal.ZERO;
    private Shape neighbor = null;           // A shape adjacent to this one, if any
    /**
     * A default Shape is centered at (0,0) in both explicit and implicit coordinates.
     */
    protected BigDecimal explicitXPosition = BigDecimal.ZERO;
    protected BigDecimal explicitYPosition = BigDecimal.ZERO;

    /**
     * Get this Circle's neighbor above (this Circle is below that one), if any.
     *
     * @return the Circle to the right of this one, if any;
     * <code>null</code> otherwise.
     */
    public Shape getAbove() {
        Shape returnValue = null;
        if (this.angleToNeighbor.equals(BigDecimal.valueOf(0))) {
            returnValue = this.neighbor;
        }
        return returnValue;
    }

    /**
     * Get this Circle's neighbor below (this Circle is below that one), if any.
     *
     * @return the Circle to below this one, if any;
     * <code>null</code> otherwise.
     */
    public Shape getBelow() {
        Shape returnValue = null;
        if (this.angleToNeighbor.equals(BigDecimal.valueOf(180))) {
            returnValue = this.neighbor;
        }
        return returnValue;
    }


    /**
     * Get the explicit height of this Shape.
     *
     * @return the explicit height of this Shape
     */
    @NotNull
    public BigDecimal getExplicitHeight() {
        return this.explicitHeight;
    }

    /**
     * Get the explicit width of this Circle.
     *
     * @return the explicit width of this Circle,
     * or null if the explicit width of this Circle has not been set.
     */
    public BigDecimal getExplicitWidth() {
        return this.explicitWidth;
    }

    public BigDecimal getExplicitXPosition() {
        return this.explicitXPosition;
    }

    public BigDecimal getExplicitYPosition() {
        return this.explicitYPosition;
    }

    protected BigDecimal getImplicitHalfHeight() {
        return this.getImplicitHeight().divide(BigDecimal.valueOf(2), BigDecimalMath.mathContext);
    }

    protected BigDecimal getImplicitHalfWidth() {
        return this.getImplicitWidth().divide(BigDecimal.valueOf(2), BigDecimalMath.mathContext);
    }

    /**
     * Get the implicit height of this Shape
     *
     * @return the implicit height of this Shape
     */
    public BigDecimal getImplicitHeight() {
        return this.implicitHeight;
    }

    /**
     * Get the implicit width of this Circle
     *
     * @return the implicit width of this Circle
     */
    public BigDecimal getImplicitWidth() {
        return this.getImplicitHeight();
    }

    /**
     * Get the implicit maximum (rightmost) x-position of this Shape.
     *
     * @return The implicit maximum (rightmost) x-position of this Shape.
     */
    protected BigDecimal getImplicitXMaximum() {
        return this.getImplicitXPosition().add(this.getImplicitHalfWidth());
    }

    /**
     * Get the implicit minimum (leftmost) x-position of this Shape.
     *
     * @return The implicit minimum (leftmost) x-position of this Shape.
     */
    protected BigDecimal getImplicitXMinimum() {
        return this.getImplicitXPosition().subtract(this.getImplicitHalfWidth());
    }

    public BigDecimal getImplicitXPosition() {
        return this.implicitXPosition;
    }

    /**
     * Get the implicit maximum (topmost) y-position of this Shape.
     *
     * @return The implicit maximum (topmost) x-position of this Shape.
     */
    protected BigDecimal getImplicitYMaximum() {
        return this.getImplicitYPosition().add(this.getImplicitHalfHeight());
    }

    /**
     * Get the implicit minimum (bottommost) y-position of this Circle.
     *
     * @return The implicit minimum (bottommost) y-position of this Circle.
     */
    protected BigDecimal getImplicitYMinimum() {
        return this.getImplicitYPosition().subtract(this.getImplicitHalfHeight());
    }

    public BigDecimal getImplicitYPosition() {
        return this.implicitYPosition;
    }

    /**
     * Get this Circle's neighbor to the right (this Circle is to the left of that one), if any.
     *
     * @return the Circle to the right of this one, if any;
     * <code>null</code> otherwise.
     */
    public Shape getLeftOf() {
        Shape returnValue = null;
        if (this.angleToNeighbor == null) {
            returnValue = null;
        } else if (this.angleToNeighbor.equals(BigDecimal.valueOf(90))) {
            returnValue = this.neighbor;
        } else {
            returnValue = null;
        }
        return returnValue;
    }

    /**
     * Get this Circle's neighbor to the left (this Circle is to the right of that one), if any.
     *
     * @return the Circle to the left of this one, if any;
     * <code>null</code> otherwise.
     */
    public Shape getRightOf() {
        Shape returnValue = null;
        if (this.angleToNeighbor == null) {
            returnValue = null;
        } else if (this.angleToNeighbor.compareTo(BigDecimal.valueOf(270)) == 0) {
            returnValue = this.neighbor;
        } else {
            returnValue = null;
        }
        return returnValue;
    }

    public String getSVG() {
        return "oops";
    }

    /**
     * Set this Shape above another Shape.
     *
     * @param shape The circle that will be below this one.
     */
    public void setAbove(Shape shape) {
        if (shape == this) {
            throw new UnsupportedOperationException(CANNOT_BE_ADJACENT_TO_ITSELF);
        }
        this.neighbor = shape;
        this.angleToNeighbor = BigDecimal.valueOf(0);
        BigDecimal topBoundaryOfCircle = shape.getImplicitYMaximum();
        BigDecimal thisImplicitYPosition = topBoundaryOfCircle.add(this.getImplicitHalfHeight(), BigDecimalMath.mathContext);
        this.setImplicitYPosition(thisImplicitYPosition);
    }

    /**
     * Set this circle below another circle.
     *
     * @param shape The circle that will be above this one.
     */
    public void setBelow(Shape shape) {
        if (shape == this) {
            throw new UnsupportedOperationException(CANNOT_BE_ADJACENT_TO_ITSELF);
        }
        this.neighbor = shape;
        this.angleToNeighbor = BigDecimal.valueOf(180);
        BigDecimal bottomBoundaryOfCircle = shape.getImplicitYMinimum();
        BigDecimal thisImplicitYPosition = bottomBoundaryOfCircle.subtract(this.getImplicitHalfHeight(), BigDecimalMath.mathContext);
        this.setImplicitYPosition(thisImplicitYPosition);
    }

    /**
     * Set the height of this Circle to a fixed value
     *
     * @param height The new height of this Circle.
     */
    protected void setExplicitHeight(BigDecimal height) {
        this.explicitHeight = height;
    }

    /**
     * Set the width of this Circle to an explicit value.
     *
     * This method is protected because API users should not be able to set explicit dimensions.
     *
     * @param width the new width of this Circle
     */
    protected void setExplicitWidth(BigDecimal width) {
        this.explicitWidth = width;
    }

    protected void setExplicitXPosition(BigDecimal x) {
        this.explicitXPosition = x;
    }

    public void setExplicitXPosition(Integer x) {
        this.explicitXPosition = BigDecimal.valueOf(x);
    }

    protected void setExplicitYPosition(BigDecimal y) {
        this.explicitYPosition = y;
    }

    public void setExplicitYPosition(Integer y) {
        this.explicitYPosition = BigDecimal.valueOf(y);
    }

    protected void setImplicitXPosition(BigDecimal x) {
        this.implicitXPosition = x;
    }

    /**
     * Set the implicit y position of this circle.
     * <p>
     * The y position is the vertical position of the circle's center. In the implicit coordinate system, higher values
     * of y are upward whereas lower values of y are downward.
     *
     * @param y The implicit y position of this circle.
     */
    public void setImplicitYPosition(BigDecimal y) {
        this.implicitYPosition = y;
    }

    /**
     * Set this Circle's neighbor to the right (this Circle is to the left of that one).
     *
     * @param shape the Circle to the right of this one
     */
    public void setLeftOf(Shape shape) {
        if (shape == this) {
            throw new UnsupportedOperationException(CANNOT_BE_ADJACENT_TO_ITSELF);
        }
        this.neighbor = shape;
        this.angleToNeighbor = BigDecimal.valueOf(90);
        BigDecimal leftBoundaryOfShape = shape.getImplicitXMinimum();
        BigDecimal thisImplicitXPosition = leftBoundaryOfShape.subtract(this.getImplicitHalfWidth(), BigDecimalMath.mathContext);
        this.setImplicitXPosition(thisImplicitXPosition);
    }

    /**
     * Set this Circle's neighbor to the left (this Circle is to the right of that one).
     *
     * @param shape the Circle to the left of this one
     */
    public void setRightOf(Shape shape) {
        if (shape == this) {
            throw new UnsupportedOperationException(CANNOT_BE_ADJACENT_TO_ITSELF);
        }
        this.neighbor = shape;
        this.angleToNeighbor = BigDecimal.valueOf(270);
        BigDecimal rightBoundaryOfShape = shape.getImplicitXMaximum();
        BigDecimal thisImplicitXPosition = rightBoundaryOfShape.add(this.getImplicitHalfWidth(), BigDecimalMath.mathContext);
        this.setImplicitXPosition(thisImplicitXPosition);
    }
}
