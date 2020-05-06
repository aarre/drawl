package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents circles.
 */
public class Circle extends Shape
{

    /**
     * The explicit radius of a Circle is null by default.
     */
    private @Nullable DrawlNumber explicitRadius;

    /**
     * The implicit radius of a Circle is 0.5 by default, giving the default Circle an implicit diameter of 1.
     */
    private DrawlNumber implicitRadius = DrawlNumber.HALF;

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
    protected Circle(DrawlNumber implicitRadius)
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
    private DrawlNumber getExplicitDiameter()
    {
        @Nullable DrawlNumber result = null;
        @Nullable DrawlNumber radiusExplicitValue = this.getExplicitRadius();
        if (radiusExplicitValue != null)
        {
            result = radiusExplicitValue.multiply(DrawlNumber.TWO, DrawlNumber.mcOperations);
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
    public DrawlNumber getExplicitHeight()
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
    protected DrawlNumber getExplicitRadius()
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
    protected DrawlNumber getExplicitWidth()
    {
        return this.getExplicitDiameter();
    }

    /**
     * Get the implicit diameter of this Circle.
     *
     * @return the implicit diameter of this Circle.
     */
    protected DrawlNumber getImplicitDiameter()
    {
        DrawlNumber myImplicitRadius = this.getImplicitRadius();
        assert !myImplicitRadius.equals(DrawlNumber.ZERO) : "Implicit radius cannot be zero.";
        return myImplicitRadius.multiply(DrawlNumber.TWO, DrawlNumber.mcOperations);
    }

    /**
     * Get the implicit height of this Circle.
     *
     * @return the implicit height of this Circle, or <code>null</code> if the implicit radius of this Circle has
     * not been set.
     */
    @Nullable
    @Override
    protected DrawlNumber getImplicitHeight()
    {
        return this.getImplicitDiameter();
    }

    protected DrawlNumber getImplicitRadius()
    {
        return this.implicitRadius;
    }

    /**
     * Get the implicit width of this Circle
     *
     * @return the implicit width of this Circle
     */
    @Override
    protected DrawlNumber getImplicitWidth()
    {
        return this.getImplicitDiameter();
    }

    /**
     * Get the implicit maximum (rightmost) x-position of this Circle.
     *
     * @return The implicit maximum (rightmost) x-position of this Circle.
     */
    @Override
    protected DrawlNumber getImplicitXMaximum()
    {
        return this.getImplicitXPositionCenter().add(this.getImplicitRadius());
    }

    /**
     * Get the implicit minimum (leftmost) x-position of this Circle.
     *
     * @return The implicit minimum (leftmost) x-position of this Circle.
     */
    @Override
    protected DrawlNumber getImplicitXMinimum()
    {
        return this.getImplicitXPositionCenter().subtract(this.getImplicitRadius());
    }

    /**
     * Get the implicit maximum (topmost) y-position of this Circle.
     *
     * @return The implicit maximum (topmost) x-position of this Circle.
     */
    @Override
    protected DrawlNumber getImplicitYPositionTop()
    {
        return this.getImplicitYPositionCenter().add(this.getImplicitRadius());
    }

    /**
     * Get SVG representing this Circle.
     *
     * @return A string containing SVG representing this Circle.
     */
    @Override
    public @NotNull String getSVG()
    {
        String radiusStringValue;
        @Nullable DrawlNumber radiusExplicitValue = this.getExplicitRadius();
        if (radiusExplicitValue == null)
        {
            // If the explicit radius has not been set, use the implicit radius
            this.setExplicitRadius(this.getImplicitRadius());
        }
        radiusStringValue = SVG.toString(this.getExplicitRadius());

        @NotNull StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("<circle ");
        svgBuilder.append("r=\"");
        svgBuilder.append(radiusStringValue);
        svgBuilder.append("\"");
        svgBuilder.append(" cx=\"");
        svgBuilder.append(SVG.toString(this.getExplicitXPositionCenter()));
        svgBuilder.append("\"");
        svgBuilder.append(" cy=\"");
        svgBuilder.append(SVG.toString(this.getExplicitYPositionCenter()));
        svgBuilder.append("\"");
        if (this.getFill() != null)
        {
            svgBuilder.append(" fill=\"");
            svgBuilder.append(this.getFill());
            svgBuilder.append("\"");
        }
        if (this.getStroke() != null)
        {
            svgBuilder.append(" stroke=\"");
            svgBuilder.append(this.getStroke());
            svgBuilder.append("\"");
        }
        svgBuilder.append(" />");
        if (Boolean.TRUE.equals(this.hasText()))
        {
            svgBuilder.append(Objects.requireNonNull(this.getText()).getSVG());
        }
        return svgBuilder.toString();
    }

    /**
     * Set the height of this Circle to a fixed value
     *
     * @param height The new height of this Circle. This value can be <code>null</code> to indicate that the explicit
     *               height has not yet been determined.
     */
    @Override
    protected void setExplicitHeight(@Nullable DrawlNumber height)
    {
        if (height == null)
        {
            this.setExplicitRadiusToNull();
        }
        else
        {
            this.setExplicitRadius(height.divide(DrawlNumber.TWO, DrawlNumber.mcOperations));
            if (Boolean.TRUE.equals(this.hasText()))
            {
                Objects.requireNonNull(this.getText()).setExplicitHeight(height);
            }
        }
    }

    /**
     * Set the radius to a fixed value
     * <p>
     * This is a protected method because users of the API should not have to deal with DrawlNumber.
     *
     * @param radius the fixed value
     */
    protected void setExplicitRadius(@NotNull DrawlNumber radius)
    {
        this.explicitRadius = radius;
    }

    /**
     * Set the radius of this Circle to a fixed value.
     *
     * @param radius The fixed value for the radius of this Circle. This method allows providing the value as an
     *               Integer to support common use cases.
     */
    protected void setExplicitRadius(@NotNull Integer radius)
    {
        this.explicitRadius = DrawlNumber.valueOf(radius);
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
    @Override
    protected void setExplicitWidth(@Nullable DrawlNumber width)
    {
        if (width == null)
        {
            this.setExplicitRadiusToNull();
        }
        else
        {
            this.setExplicitRadius(width.divide(DrawlNumber.TWO, DrawlNumber.mcOperations));
            if (Boolean.TRUE.equals(this.hasText()))
            {
                Objects.requireNonNull(this.getText()).setExplicitWidth(width);
            }
        }
    }

}
