package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

public class Circle extends Shape
{

    /**
     * The explicit radius of a Circle is null by default.
     */
    private SisuBigDecimal explicitRadius;

    /**
     * The implicit radius of a Circle is 0.5 by default, giving the default Circle an implicit diameter of 1.
     */
    private SisuBigDecimal implicitRadius = SisuBigDecimal.HALF;

    /**
     * Construct a circle with a default implicit radius.
     */
    public Circle()
    {

    }

    /**
     * Construct a circle with an implicit radius.
     *
     * @param implicitRadius The implicit radius of the new circle.
     */
    protected Circle(SisuBigDecimal implicitRadius)
    {
        this.implicitRadius = implicitRadius;
    }

    /**
     * Get the explicit diameter of this Circle.
     *
     * @return The explicit diameter of this Circle, or <code>null</code> if the explicit radius of this Circle has
     * not been set.
     */
    @Nullable
    private SisuBigDecimal getExplicitDiameter()
    {
        SisuBigDecimal result = null;
        SisuBigDecimal radiusExplicitValue = this.getExplicitRadius();
        if (radiusExplicitValue != null)
        {
            result = radiusExplicitValue.multiply(SisuBigDecimal.TWO, SisuBigDecimal.mcOperations);
        }
        return result;
    }

    /**
     * Get the explicit height of this Circle.
     *
     * @return the explicit height of this Circle, or <code>null</code> if the explicit radius of this Circle has
     * not been set.
     */
    @Nullable
    @Override
    public SisuBigDecimal getExplicitHeight()
    {
        return this.getExplicitDiameter();
    }

    /**
     * Get the explicit radius of this Circle.
     *
     * @return The explicit radius of this Circle, or <code>null</code> if the explicit radius of this Circle has
     * not been set.
     */
    @Nullable
    protected SisuBigDecimal getExplicitRadius()
    {
        return this.explicitRadius;
    }

    /**
     * Get the explicit width of this Circle.
     *
     * @return The explicit width of this circle, or <code>null</code> if the explicit radius of this
     * Circle has not been set.
     */
    @Nullable
    @Override
    protected SisuBigDecimal getExplicitWidth()
    {
        return this.getExplicitDiameter();
    }

    /**
     * Get the implicit diameter of this Circle
     *
     * @return
     */
    protected SisuBigDecimal getImplicitDiameter()
    {
        SisuBigDecimal myImplicitRadius = this.getImplicitRadius();
        assert !myImplicitRadius.equals(SisuBigDecimal.ZERO) : "Implicit radius cannot be zero.";
        return myImplicitRadius.multiply(SisuBigDecimal.TWO, SisuBigDecimal.mcOperations);
    }

    /**
     * Get the implicit height of this Circle
     *
     * @return the implicit height of this Circle, or <code>null</code> if the implicit radius of this Circle has
     * not been set.
     */
    @Nullable
    @Override
    protected SisuBigDecimal getImplicitHeight()
    {
        return this.getImplicitDiameter();
    }

    protected SisuBigDecimal getImplicitRadius()
    {
        return this.implicitRadius;
    }

    /**
     * Get the implicit width of this Circle
     *
     * @return the implicit width of this Circle
     */
    @Override
    protected SisuBigDecimal getImplicitWidth()
    {
        return this.getImplicitDiameter();
    }

    /**
     * Get the implicit maximum (rightmost) x-position of this Circle.
     *
     * @return The implicit maximum (rightmost) x-position of this Circle.
     */
    @Override
    protected SisuBigDecimal getImplicitXMaximum()
    {
        return this.getImplicitXPositionCenter().add(this.getImplicitRadius());
    }

    /**
     * Get the implicit minimum (leftmost) x-position of this Circle.
     *
     * @return The implicit minimum (leftmost) x-position of this Circle.
     */
    @Override
    protected SisuBigDecimal getImplicitXMinimum()
    {
        return this.getImplicitXPositionCenter().subtract(this.getImplicitRadius());
    }

    /**
     * Get the implicit maximum (topmost) y-position of this Circle.
     *
     * @return The implicit maximum (topmost) x-position of this Circle.
     */
    @Override
    protected SisuBigDecimal getImplicitYMaximum()
    {
        return this.getImplicitYPositionCenter().add(this.getImplicitRadius());
    }

    /**
     * Get SVG representing this Circle.
     *
     * @return A string containing SVG representing this Circle.
     */
    @Override
    public String getSVG()
    {
        String radiusStringValue;
        SisuBigDecimal radiusExplicitValue = this.getExplicitRadius();
        if (radiusExplicitValue == null)
        {
            // If the explicit radius has not been set, use the implicit radius
            this.setExplicitRadius(this.getImplicitRadius());
        }
        radiusStringValue = SVG.toString(this.getExplicitRadius());

        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("<circle ");
        svgBuilder.append("r=\"");
        svgBuilder.append(radiusStringValue);
        svgBuilder.append("\"");
        svgBuilder.append(" cx=\"");
        svgBuilder.append(SVG.toString(this.getExplicitXPositionCenter()));
        svgBuilder.append("\"");
        this.getExplicitYPositionCenter();
        svgBuilder.append(" cy=\"");
        svgBuilder.append(SVG.toString(this.getExplicitYPositionCenter()));
        svgBuilder.append("\"");
        svgBuilder.append(" />");
        return svgBuilder.toString();
    }

    /**
     * Set the height of this Circle to a fixed value
     *
     * @param height The new height of this Circle. This value can be <code>null</code> to indicate that the explict
     *               height has not yet been determined.
     */
    @Override
    protected void setExplicitHeight(@Nullable SisuBigDecimal height)
    {
        if (height == null)
        {
            this.setExplicitRadiusToNull();
        }
        else
        {
            this.setExplicitRadius(height.divide(SisuBigDecimal.TWO, SisuBigDecimal.mcOperations));
        }
    }

    /**
     * Set the radius to a fixed value
     * <p>
     * This is a protected method because users of the API should not have to deal with SisuBigDecimal.
     *
     * @param radius the fixed value
     */
    protected void setExplicitRadius(@NotNull SisuBigDecimal radius)
    {
        this.explicitRadius = radius;
    }

    /**
     * Set the radius of this Circle to a fixed value.
     *
     * @param radius The fixed value for the radius of this Circle. This method allows providing the value as an
     *               Integer to support common use cases.
     */
    public void setExplicitRadius(@NotNull Integer radius)
    {
        this.explicitRadius = SisuBigDecimal.valueOf(radius);
    }

    private void setExplicitRadiusToNull()
    {
        this.explicitRadius = null;
    }

    /**
     * Set the width of this Circle to a fixed value
     *
     * @param width the new width of this Circle
     */
    // TODO [Issue #1] Make this method protected because API users cannot set explicit dimensions. Adjust unit tests.
    @Override
    public void setExplicitWidth(@Nullable SisuBigDecimal width)
    {
        if (width == null)
        {
            this.setExplicitRadiusToNull();
        }
        else
        {
            this.setExplicitRadius(width.divide(SisuBigDecimal.TWO, SisuBigDecimal.mcOperations));
        }
    }

}
