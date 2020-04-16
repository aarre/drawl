package com.aarrelaakso.drawl;

import java.math.BigDecimal;

public class Circle {

    private ConstraintType constraint;
    private BigDecimal implicitRadius;
    private BigDecimal explicitRadius;
    private Circle neighbor = null;           // A circle adjacent to this one, if any
    private BigDecimal angleToNeighbor = null;
    private BigDecimal explicitXPosition = BigDecimal.ZERO;
    private BigDecimal explicitYPosition = BigDecimal.ZERO;
    private BigDecimal implicitXPosition = BigDecimal.ZERO;

    /**
     * Construct a circle with an implicit radius.
     */
    public Circle() {
        this.implicitRadius = BigDecimal.valueOf(0.5);
    }

    /**
     * Construct a circle given a radius.
     *
     * @param radius The radius of the circle to be constructed.
     * @param constraintType Whether the given radius is explicit or implicit.
     */
    public Circle(BigDecimal radius, ConstraintType constraintType) {
        this.constraint = constraintType;
        if (constraintType == ConstraintType.EXPLICIT) {
            this.explicitRadius = radius;
            this.implicitRadius = BigDecimal.valueOf(0.5);
        } else {
            this.implicitRadius = radius;
        }
    }

    /**
     * Construct a circle given an Integer radius.
     *
     * @param radius The radius of the circle to be constructed.
     * @param constraintType Whether the given radius is explicit or implicit.
     */
    public Circle(Integer radius, ConstraintType constraintType) {
        this(BigDecimal.valueOf(radius), constraintType);
    }

    /** Get the explicit height of this Circle.
     *
     * @return the explicit height of this Circle
     */
    public BigDecimal getExplicitHeight() {
        return this.getExplicitRadius().multiply(BigDecimal.valueOf(2), BigDecimalMath.mathContext);
    }

    public BigDecimal getExplicitRadius() {
        return this.explicitRadius;
    }

    /** Get the explicit width of this Circle.
     *
     * @return the explicit width of this Circle,
     *         or null if the explicit width of this Circle has not been set.
     */
    public BigDecimal getExplicitWidth() {
        BigDecimal result = null;
        BigDecimal radiusExplicitValue = this.getExplicitRadius();
        if (radiusExplicitValue != null) {
            result = radiusExplicitValue.multiply(BigDecimal.valueOf(2), BigDecimalMath.mathContext);
        }
        return result;
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
        return this.getImplicitRadius().multiply(BigDecimal.valueOf(2), BigDecimalMath.mathContext);
    }

    public BigDecimal getImplicitXPosition() {
        return this.implicitXPosition;
    }

    /**
     * Get this Circle's neighbor to the right (this Circle is to the left of that one), if any.
     *
     * @return the Circle to the right of this one, if any;
     *         <code>null</code> otherwise.
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
     *         <code>null</code> otherwise.
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
        String svg;
        svg = "<circle ";
        svg += "r=\"";
        svg += radiusStringValue;
        svg += "\"";
        if (this.explicitXPosition != null) {
            svg += " cx=\"";
            svg += SVG.toString(this.explicitXPosition);
            svg += "\"";
        }
        if (this.explicitYPosition != null) {
            svg += " cy=\"";
            svg += SVG.toString(this.explicitYPosition);
            svg += "\"";
        }
        svg += " />";
        return svg;
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

    /**
     * Set the radius to a fixed value
     *
     * @param radius the fixed value
     */
    public void setExplicitRadius(BigDecimal radius) {
        this.explicitRadius = radius;
    }

    /**
     * Set this Circle's neighbor to the right (this Circle is to the left of that one).
     *
     * @param circle the Circle to the right of this one
     */
    public void setLeftOf(Circle circle) {
        if (circle == this) {
            throw new UnsupportedOperationException("A circle cannot be adjacent to itself");
        }
        this.neighbor = circle;
        this.angleToNeighbor = BigDecimal.valueOf(90);
        if (circle.getRightOf() != this) {
            circle.setRightOf(this);
        }
    }

    /**
     * Set this Circle's neighbor to the left (this Circle is to the right of that one).
     *
     * @param circle the Circle to the left of this one
     */
    public void setRightOf(Circle circle) {
        if (circle == this) {
            throw new UnsupportedOperationException("A circle cannot be adjacent to itself");
        }
        this.neighbor = circle;
        this.angleToNeighbor = BigDecimal.valueOf(270);
        if (circle.getLeftOf() != this) {
            circle.setLeftOf(this);
        }
        BigDecimal circleImplicitRadius = circle.getImplicitWidth().divide(BigDecimal.valueOf(2), BigDecimalMath.mathContext);
        BigDecimal thisImplicitRadius =  this.getImplicitWidth().divide(BigDecimal.valueOf(2), BigDecimalMath.mathContext);
        BigDecimal rightBoundaryOfCircle = circle.getImplicitXPosition().add(circleImplicitRadius, BigDecimalMath.mathContext);
        BigDecimal thisImplicitXPosition = rightBoundaryOfCircle.add(thisImplicitRadius, BigDecimalMath.mathContext);
        this.setImplicitXPosition(thisImplicitXPosition);
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
}
