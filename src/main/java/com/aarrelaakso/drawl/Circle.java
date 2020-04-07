package com.aarrelaakso.drawl;

public class Circle {

    private Measurement radius;
    private Integer x;
    private Integer y;

    /**
     * Construct a circle with an implicit radius.
     */
    public Circle() {
        radius = new Measurement();
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

    public Measurement getRadius() {
        return this.radius;
    }

    public String getSVG() {
        int radiusValue = this.radius.getValue();
        String svg;
        svg = "<circle ";
        svg += "r=\"";
        svg += this.radius.getValue();
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

    public void setRadius(int radius) {
        this.radius = new Measurement(radius);
    }

    public void setX(int x) {
        this.x = new Integer(x);
    }

    public void setY(int y) {
        this.y = new Integer(y);
    }
}
