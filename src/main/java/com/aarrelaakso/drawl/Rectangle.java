package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;

/**
 * Represents rectangles.
 */
public class Rectangle extends Shape
{

    /**
     * Creates a Rectangle with the default width and height.
     */
    public Rectangle()
    {
        this.setImplicitHeight(DrawlNumber.ONE);
        this.setImplicitWidth(DrawlNumber.ONE);
    }

    /**
     * Creates a rectangle with a given aspect ratio.
     *
     * @param aspectRatio The aspect ratio of the new rectangle. The aspect ratio is expressed as width over height.
     */
    public Rectangle (Double aspectRatio)
    {
        // This method is public because the user can deal with a ratio, which is dimensionless.
        this.setImplicitHeight(DrawlNumber.ONE);
        this.setImplicitWidth(DrawlNumber.valueOf(aspectRatio));
    }

    /**
     * Creates a new Rectangle with a given width and height.
     *
     * @param implicitWidth the width of the new Rectangle.
     * @param implicitHeight the height of the new Rectangle.
     */
    protected Rectangle(Integer implicitWidth, Integer implicitHeight)
    {
        // This method is protected because the user should never have to deal with implicit dimensions.
        this.setImplicitHeight(DrawlNumber.valueOf(implicitHeight));
        this.setImplicitWidth(DrawlNumber.valueOf(implicitWidth));
    }

    /**
     * Creates a new Rectangle with a given width and height.
     *
     * @param implicitWidth the width of the new Rectangle.
     * @param implicitHeight the height of the new Rectangle.
     */
    protected Rectangle(@NotNull DrawlNumber implicitWidth, @NotNull DrawlNumber implicitHeight)
    {
        // This method is protected because the user should never have to deal with implicit dimensions.
        this.setImplicitHeight(implicitHeight);
        this.setImplicitWidth(implicitWidth);
    }

    public @NotNull String getSVG() {
        if (this.getExplicitWidth() == null || this.getExplicitHeight() == null)
        {
            throw new UnsupportedOperationException("Cannot get SVG without setting explicit dimensions");
        }
        @NotNull StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("<rect");
        svgBuilder.append(" width=\"");
        svgBuilder.append(this.getExplicitWidth().toPlainString());
        svgBuilder.append("\"");
        svgBuilder.append(" height=\"");
        svgBuilder.append(this.getExplicitHeight().toPlainString());
        svgBuilder.append("\"");
        if (this.getExplicitXPositionLeft() != null) {
            svgBuilder.append(" x=\"");
            svgBuilder.append(this.getExplicitXPositionLeft().toSVG());
            svgBuilder.append("\"");
        }
        if (this.getExplicitYPositionTop() != null) {
            svgBuilder.append(" y=\"");
            svgBuilder.append(this.getExplicitYPositionTop().toSVG());
            svgBuilder.append("\"");
        }
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
        if (this.getText() != null)
        {
            svgBuilder.append(this.getText().getSVG());
        }
        return svgBuilder.toString();
    }


}
