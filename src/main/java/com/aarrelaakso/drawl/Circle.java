package com.aarrelaakso.drawl;

public class Circle {

    private Measurement radius;
    private Circle neighbor = null;           // A circle adjacent to this one, if any
    private Double angleToNeighbor = null;
    private Integer explicitXPosition = 0;
    private Integer explicitYPosition = 0;
    private Double implicitXPosition = new Double(0.0);

    /**
     * Construct a circle with an implicit radius.
     */
    public Circle() {
        radius = new Measurement(0.5);
    }

    /**
     * Construct a circle with a fixed radius.
     *
     * @param radius The radius of the circle to be constructed (in pixels).
     */
    public Circle(int radius) {
        this.radius = new Measurement(radius);
    }

    /**
     * Construct a circle with an implicit radius matching another measurement.
     *
     * @param radius The radius of the circle to be constructed (as a Measurement object).
     */
    public Circle(Measurement radius) {
        this.radius = radius;
    }

    /**
     * Get the implicit height of this Circle
     *
     * @return the implicit height of this Circle
     */
    public Double getImplicitHeight() {
        Double height = 2 * this.radius.getImplicitValue();
        return height;
    }
    /**
     * Get the implicit width of this Circle
     * 
     * @return the implicit width of this Circle
     */
    public Double getImplicitWidth() {
        Double width = 2 * this.radius.getImplicitValue();
        return width;
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

    public Measurement getRadius() {
        return this.radius;
    }

    public String getSVG() {
        Measurement radius = this.radius;
        String svg;
        svg = "<circle ";
        svg += "r=\"";
        svg += this.radius.getFixedValue();
        svg += "\"";
        if (this.explicitXPosition != null) {
            svg += " cx=\"";
            svg += this.explicitXPosition.toString();
            svg += "\"";
        }
        if (this.explicitYPosition != null) {
            svg += " cy=\"";
            svg += this.explicitYPosition.toString();
            svg += "\"";
        }
        svg += " />";
        return svg;
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

    public Integer getExplicitXPosition() {
        return this.explicitXPosition;
    }

    public Integer getExplicitYPosition() {
        return this.explicitYPosition;
    }

    public Double getImplicitXPosition() {
        return this.implicitXPosition;
    }

    /**
     * Set the radius to a fixed value
     *
     * @param radius the fixed value
     */
    public void setRadiusFixed(Integer radius) {
        this.radius.setFixedValue(radius);
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

    public void setExplicitXPosition(Integer x) {
        this.explicitXPosition = x;
    }

    public void setExplicitYPosition(Integer y) {
        this.explicitYPosition = y;
    }

    public void setImplicitXPosition(Double x) {
        this.implicitXPosition = x;
    }
}
