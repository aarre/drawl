package com.aarrelaakso.drawl;

import java.math.BigDecimal;

public class Circle {

    private BigDecimal angleToNeighbor = null;
    private BigDecimal explicitRadius = BigDecimal.valueOf(0.5);    // By default, the explicit radius is the same as the implicit radius
    private BigDecimal explicitXPosition = BigDecimal.ZERO;
    private BigDecimal explicitYPosition = BigDecimal.ZERO;
    private BigDecimal implicitRadius = BigDecimal.valueOf(0.5);
    private BigDecimal implicitXPosition = BigDecimal.ZERO;
    private BigDecimal implicitYPosition = BigDecimal.ZERO;
    private Circle neighbor = null;           // A circle adjacent to this one, if any

    private static final String CANNOT_BE_ADJACENT_TO_ITSELF = "A circle cannot be adjacent to itself";

    /**
     * Construct a circle with a default implicit radius.
     *
     */
    public Circle() {

    }

    /**
     * Construct a circle with an implicit radius.
     *
     * @param implicitRadius The implicit radius of the new circle.
     */
    public Circle(BigDecimal implicitRadius) {
        this.implicitRadius = implicitRadius;
    }

    /**
     * Get this Circle's neighbor above (this Circle is below that one), if any.
     *
     * @return the Circle to the right of this one, if any;
     * <code>null</code> otherwise.
     */
    public Circle getAbove() {
        Circle returnValue = null;
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
    public Circle getBelow() {
        Circle returnValue = null;
        if (this.angleToNeighbor.equals(BigDecimal.valueOf(180))) {
            returnValue = this.neighbor;
        }
        return returnValue;
    }

    /**
     * Get the explicit diameter of this Circle.
     *
     * @return The explicit diameter of this Circle, or <code>null</code> if the explicit diameter of this Circle has
     * not been set.
     */

    private BigDecimal getExplicitDiameter() {
        BigDecimal result = null;
        BigDecimal radiusExplicitValue = this.getExplicitRadius();
        if (radiusExplicitValue != null) {
            result = radiusExplicitValue.multiply(BigDecimal.valueOf(2), BigDecimalMath.mathContext);
        }
        return result;
    }
    /**
     * Get the explicit height of this Circle.
     *
     * @return the explicit height of this Circle,
     * or null if the explicit height of this Circle has not been set.
     */
    public BigDecimal getExplicitHeight() {
        return this.getExplicitDiameter();
    }

    public BigDecimal getExplicitRadius() {
        return this.explicitRadius;
    }

    /**
     * Get the explicit width of this Circle.
     *
     * @return the explicit width of this Circle,
     * or null if the explicit width of this Circle has not been set.
     */
    public BigDecimal getExplicitWidth() {
        return this.getExplicitDiameter();
    }

    public BigDecimal getExplicitXPosition() {
        return this.explicitXPosition;
    }

    public BigDecimal getExplicitYPosition() {
        return this.explicitYPosition;
    }

    /**
     * Get the implicit height of this Circle
     *
     * @return the implicit height of this Circle
     */
    public BigDecimal getImplicitHeight() {
        return this.getImplicitRadius().multiply(BigDecimal.valueOf(2), BigDecimalMath.mathContext);
    }

    public BigDecimal getImplicitRadius() {
        return this.implicitRadius;
    }

    /**
     * Get the implicit width of this Circle
     *
     * @return the implicit width of this Circle
     */
    public BigDecimal getImplicitWidth() {
        BigDecimal implicitRadius = this.getImplicitRadius();
        assert implicitRadius.compareTo(BigDecimal.ZERO) != 0 : "Implicit radius cannot be zero.";
        return implicitRadius.multiply(BigDecimal.valueOf(2), BigDecimalMath.mathContext);
    }

    /**
     * Get the implicit maximum (rightmost) x-position of this Circle.
     *
     * @return The implicit maximum (rightmost) x-position of this Circle.
     */
    protected BigDecimal getImplicitXMaximum() {
        return this.getImplicitXPosition().add(this.getImplicitRadius());
    }

    /**
     * Get the implicit minimum (leftmost) x-position of this Circle.
     *
     * @return The implicit minimum (leftmost) x-position of this Circle.
     */
    protected BigDecimal getImplicitXMinimum() {
        return this.getImplicitXPosition().subtract(this.getImplicitRadius());
    }

    public BigDecimal getImplicitXPosition() {
        return this.implicitXPosition;
    }

    /**
     * Get the implicit maximum (topmost) y-position of this Circle.
     *
     * @return The implicit maximum (topmost) x-position of this Circle.
     */
    protected BigDecimal getImplicitYMaximum() {
        return this.getImplicitYPosition().add(this.getImplicitRadius());
    }

    /**
     * Get the implicit minimum (bottommost) y-position of this Circle.
     *
     * @return The implicit minimum (bottommost) y-position of this Circle.
     */
    protected BigDecimal getImplicitYMinimum() {
        return this.getImplicitYPosition().subtract(this.getImplicitRadius());
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
    public Circle getLeftOf() {
        Circle returnValue = null;
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
    public Circle getRightOf() {
        Circle returnValue = null;
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
        String radiusStringValue;
        BigDecimal radiusExplicitValue = this.getExplicitRadius();
        if (radiusExplicitValue == null) {
            throw new UnsupportedOperationException("Cannot draw a Circle with no radius");
        } else {
            radiusStringValue = SVG.toString(radiusExplicitValue);
        }
        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("<circle ");
        svgBuilder.append("r=\"");
        svgBuilder.append(radiusStringValue);
        svgBuilder.append("\"");
        if (this.explicitXPosition != null) {
            svgBuilder.append(" cx=\"");
            svgBuilder.append(SVG.toString(this.explicitXPosition));
            svgBuilder.append("\"");
        }
        if (this.explicitYPosition != null) {
            svgBuilder.append(" cy=\"");
            svgBuilder.append(SVG.toString(this.explicitYPosition));
            svgBuilder.append("\"");
        }
        svgBuilder.append(" />");
        return svgBuilder.toString();
    }

    /**
     * Set this circle above another circle.
     *
     * @param circle The circle that will be below this one.
     */
    public void setAbove(Circle circle) {
        if (circle == this) {
            throw new UnsupportedOperationException(CANNOT_BE_ADJACENT_TO_ITSELF);
        }
        this.neighbor = circle;
        this.angleToNeighbor = BigDecimal.valueOf(0);
        BigDecimal topBoundaryOfCircle = circle.getImplicitYMaximum();
        BigDecimal thisImplicitYPosition = topBoundaryOfCircle.add(this.getImplicitRadius(), BigDecimalMath.mathContext);
        this.setImplicitYPosition(thisImplicitYPosition);
    }

    /**
     * Set this circle below another circle.
     *
     * @param circle The circle that will be above this one.
     */
    public void setBelow(Circle circle) {
        if (circle == this) {
            throw new UnsupportedOperationException(CANNOT_BE_ADJACENT_TO_ITSELF);
        }
        this.neighbor = circle;
        this.angleToNeighbor = BigDecimal.valueOf(180);
        BigDecimal bottomBoundaryOfCircle = circle.getImplicitYMinimum();
        BigDecimal thisImplicitYPosition = bottomBoundaryOfCircle.subtract(this.getImplicitRadius(), BigDecimalMath.mathContext);
        this.setImplicitYPosition(thisImplicitYPosition);
    }


    /**
     * Set the radius to a fixed value
     *
     * @param radius the fixed value
     */
    public void setExplicitRadius(BigDecimal radius) {
        this.explicitRadius = radius;
    }

    /**
     * Set the height of this Circle to a fixed value
     *
     * @param height The new height of this Circle.
     */
    public void setExplicitHeight(BigDecimal height) {
        if (height == null) {
            this.setExplicitRadius(null);
        } else {
            this.setExplicitRadius(height.divide(BigDecimal.valueOf(2), BigDecimalMath.mathContext));
        }
    }
    /**
     * Set the width of this Circle to a fixed value
     *
     * @param width the new width of this Circle
     */
    public void setExplicitWidth(BigDecimal width) {
        if (width == null) {
            this.setExplicitRadius(null);
        } else {
            this.setExplicitRadius(width.divide(BigDecimal.valueOf(2), BigDecimalMath.mathContext));
        }
    }

    public void setExplicitXPosition(BigDecimal x) {
        this.explicitXPosition = x;
    }

    public void setExplicitYPosition(BigDecimal y) {
        this.explicitYPosition = y;
    }

    public void setImplicitXPosition(BigDecimal x) {
        this.implicitXPosition = x;
    }

    /**
     * Set the implicit y position of this circle.
     *
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
     * @param circle the Circle to the right of this one
     */
    public void setLeftOf(Circle circle) {
        if (circle == this) {
            throw new UnsupportedOperationException(CANNOT_BE_ADJACENT_TO_ITSELF);
        }
        this.neighbor = circle;
        this.angleToNeighbor = BigDecimal.valueOf(90);
        BigDecimal leftBoundaryOfCircle = circle.getImplicitXMinimum();
        BigDecimal thisImplicitXPosition = leftBoundaryOfCircle.subtract(this.getImplicitRadius(), BigDecimalMath.mathContext);
        this.setImplicitXPosition(thisImplicitXPosition);
    }

    /**
     * Set this Circle's neighbor to the left (this Circle is to the right of that one).
     *
     * @param circle the Circle to the left of this one
     */
    public void setRightOf(Circle circle) {
        if (circle == this) {
            throw new UnsupportedOperationException(CANNOT_BE_ADJACENT_TO_ITSELF);
        }
        this.neighbor = circle;
        this.angleToNeighbor = BigDecimal.valueOf(270);
        BigDecimal rightBoundaryOfCircle = circle.getImplicitXMaximum();
        BigDecimal thisImplicitXPosition = rightBoundaryOfCircle.add(this.getImplicitRadius(), BigDecimalMath.mathContext);
        this.setImplicitXPosition(thisImplicitXPosition);
    }
}
