package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class Circle extends Shape {

    private BigDecimal explicitRadius = BigDecimal.valueOf(0.5);    // By default, the explicit radius is the same as the implicit radius
    private BigDecimal implicitRadius = BigDecimal.valueOf(0.5);

    /**
     * Construct a circle with a default implicit radius.
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
     * @return the explicit height of this Circle.
     */
    @NotNull
    public BigDecimal getExplicitHeight() {
        return this.getExplicitDiameter();
    }

    /**
     * Get the explicit radius of this Circle.
     *
     * @return The explicit radius of this Circle.
     */
    @NotNull
    public BigDecimal getExplicitRadius() {
        return this.explicitRadius;
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
        BigDecimal myImplicitRadius = this.getImplicitRadius();
        assert myImplicitRadius.compareTo(BigDecimal.ZERO) != 0 : "Implicit radius cannot be zero.";
        return myImplicitRadius.multiply(BigDecimal.valueOf(2), BigDecimalMath.mathContext);
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

    /**
     * Get the implicit maximum (topmost) y-position of this Circle.
     *
     * @return The implicit maximum (topmost) x-position of this Circle.
     */
    protected BigDecimal getImplicitYMaximum() {
        return this.getImplicitYPosition().add(this.getImplicitRadius());
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
     * Set the height of this Circle to a fixed value
     *
     * @param height The new height of this Circle.
     */
    protected void setExplicitHeight(@NotNull BigDecimal height) {
            this.setExplicitRadius(height.divide(BigDecimal.valueOf(2), BigDecimalMath.mathContext));
    }

    /**
     * Set the radius to a fixed value
     *
     * This is a protected method because users of the API should not have to deal with BigDecimal.
     *
     * @param radius the fixed value
     */
    protected void setExplicitRadius(BigDecimal radius) {
        this.explicitRadius = radius;
    }

    /**
     * Set the radius of this Circle to a fixed value.
     *
     * @param radius The fixed value for the radius of this Circle. This method allows providing the value as an
     *               Integer to support common use cases.
     */
    public void setExplicitRadius(Integer radius) {
        this.explicitRadius = BigDecimal.valueOf(radius);
    }

    /**
     * Set the width of this Circle to a fixed value
     *
     * @param width the new width of this Circle
     */
    protected void setExplicitWidth(@NotNull BigDecimal width) {
            this.setExplicitRadius(width.divide(BigDecimal.valueOf(2), BigDecimalMath.mathContext));
    }

}
