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

    /** Get the explicit height of this Circle.
     *
     * @return the explicit height of this Circle
     */
    public Integer getExplicitHeight() {
        Integer height = 2 * this.radius.getExplicitValue();
        return height;
    }

    /** Get the explicit width of this Circle.
     *
     * @return the explicit width of this Circle,
     *         or null if the explicit width of this Circle has not been set.
     */
    public Integer getExplicitWidth() {
        Integer result = null;
        Integer radiusExplicitValue = this.radius.getExplicitValue();
        if (radiusExplicitValue != null) {
            result = 2 * radiusExplicitValue;
        }
        return result;
    }

    public Integer getExplicitXPosition() {
        return this.explicitXPosition;
    }

    public Integer getExplicitYPosition() {
        return this.explicitYPosition;
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

    public Measurement getRadius() {
        return this.radius;
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
        Measurement radius = this.radius;
        Integer radiusExplicitValue = radius.getExplicitValue();
        if (radiusExplicitValue == null) {
            throw new UnsupportedOperationException("Cannot draw a Circle with no radius");
        }
        String svg;
        svg = "<circle ";
        svg += "r=\"";
        svg += radiusExplicitValue;
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
     * Set the width of this Circle to a fixed value
     *
     * @param width the new width of this Circle
     */
    public void setExplicitWidth(Integer width) {
        if (width == null) {
            this.setExplicitRadius(null);
        } else {
            this.setExplicitRadius(width / 2); // TODO: What if width is odd?
        }
    }

    /**
     * Set the radius to a fixed value
     *
     * @param radius the fixed value
     */
    public void setExplicitRadius(Integer radius) {
        this.radius.setExplicitValue(radius);
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
