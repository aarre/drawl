package com.aarrelaakso.drawl;

public class Circle {

    private Measurement radius;
    private Integer x = 0;
    private Integer y = 0;

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
     * Get the implicit width of this Circle
     * 
     * @return the implicit width of this Circle
     */
    public Double getImplicitWidth() {
        Double width = 2 * this.radius.getImplicitValue();
        return width;
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
        if (this.x != null) {
            svg += " cx=\"";
            svg += this.x.toString();
            svg += "\"";
        }
        if (this.y != null) {
            svg += " cy=\"";
            svg += this.y.toString();
            svg += "\"";
        }
        svg += " />";
        return svg;
    }

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    public void rightOf(Circle circle) {

    }

    /**
     * Set the radius to a fixed value
     *
     * @param radius the fixed value
     */
    public void setRadiusFixed(Integer radius) {
        this.radius.setFixedValue(radius);
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
