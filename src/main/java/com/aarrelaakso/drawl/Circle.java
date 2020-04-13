package com.aarrelaakso.drawl;

public class Circle {

    private ConstraintType constraint;
    private Double implicitRadius;
    private Double explicitRadius;
    private Circle neighbor = null;           // A circle adjacent to this one, if any
    private Double angleToNeighbor = null;
    private Double explicitXPosition = Double.valueOf(0.0);
    private Double explicitYPosition = Double.valueOf(0.0);
    private Double implicitXPosition = Double.valueOf(0.0);

    /**
     * Construct a circle with an implicit radius.
     */
    public Circle() {
        this.implicitRadius = Double.valueOf(0.5);
    }

    /**
     * Construct a circle given a radius.
     *
     * @param radius The radius of the circle to be constructed.
     * @param constraintType Whether the given radius is explicit or implicit.
     */
    public Circle(Double radius, ConstraintType constraintType) {
        this.constraint = constraintType;
        if (constraintType == ConstraintType.EXPLICIT) {
            this.explicitRadius = radius;
            this.implicitRadius = Double.valueOf(0.5);
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
        this(Double.valueOf(radius), constraintType);
    }

    /** Get the explicit height of this Circle.
     *
     * @return the explicit height of this Circle
     */
    public Double getExplicitHeight() {
        return 2 * this.getExplicitRadius();
    }

    public Double getExplicitRadius() {
        return this.explicitRadius;
    }

    /** Get the explicit width of this Circle.
     *
     * @return the explicit width of this Circle,
     *         or null if the explicit width of this Circle has not been set.
     */
    public Double getExplicitWidth() {
        Double result = null;
        Double radiusExplicitValue = this.getExplicitRadius();
        if (radiusExplicitValue != null) {
            result = 2 * radiusExplicitValue;
        }
        return result;
    }

    public Double getExplicitXPosition() {
        return this.explicitXPosition;
    }

    public Double getExplicitYPosition() {
        return this.explicitYPosition;
    }

    /**
     * Get the implicit height of this Circle
     *
     * @return the implicit height of this Circle
     */
    public Double getImplicitHeight() {
        return 2 * this.getImplicitRadius();
    }

    public Double getImplicitRadius() {
        return this.implicitRadius;
    }

    /**
     * Get the implicit width of this Circle
     * 
     * @return the implicit width of this Circle
     */
    public Double getImplicitWidth() {
        return 2 * this.getImplicitRadius();
    }

    public Double getImplicitXPosition() {
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
        } else if (this.angleToNeighbor.equals(Double.valueOf(90))) {
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
        } else if (this.angleToNeighbor.equals(Double.valueOf(270))) {
            returnValue = this.neighbor;
        } else {
            returnValue = null;
        }
        return returnValue;
    }

    public String getSVG() {
        String radiusStringValue;
        Double radiusExplicitValue = this.getExplicitRadius();
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
    public void setExplicitWidth(Double width) {
        if (width == null) {
            this.setExplicitRadius(null);
        } else {
            this.setExplicitRadius(width / 2);
        }
    }

    /**
     * Set the radius to a fixed value
     *
     * @param radius the fixed value
     */
    public void setExplicitRadius(Double radius) {
        this.explicitRadius = radius;
    }

    /**
     * Set this Circle's neighbor to the right (this Circle is to the left of that one).
     *
     * @param circle the Circle to the right of this one
     */
    public void setLeftOf(Circle circle) {
        this.neighbor = circle;
        this.angleToNeighbor = Double.valueOf(90);
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
        this.neighbor = circle;
        this.angleToNeighbor = Double.valueOf(270);
        if (circle.getLeftOf() != this) {
            circle.setLeftOf(this);
        }
        this.setImplicitXPosition(circle.getImplicitXPosition() + circle.getImplicitWidth()/2 + this.getImplicitWidth()/2);
    }

    public void setExplicitXPosition(Double x) {
        this.explicitXPosition = x;
    }

    public void setExplicitYPosition(Double y) {
        this.explicitYPosition = y;
    }

    public void setImplicitXPosition(Double x) {
        this.implicitXPosition = x;
    }
}
