package com.aarrelaakso.drawl;

public class Rectangle extends Shape {

    /**
     * Creates a Rectangle with the default width and height.
     */
    public Rectangle()
    {
        this.setImplicitHeight(SisuBigDecimal.ONE);
        this.setImplicitWidth(SisuBigDecimal.ONE);
    }

    /**
     * Creates a rectangle with a given aspect ratio.
     *
     * @param aspectRatio The aspect ratio of the new rectangle. The aspect ratio is expressed as width over height.
     */
    public Rectangle (Double aspectRatio)
    {
        // This method is public because the user can deal with a ratio, which is dimensionless.
        this.setImplicitHeight(SisuBigDecimal.ONE);
        this.setImplicitWidth(SisuBigDecimal.valueOf(aspectRatio));
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
        this.setImplicitHeight(SisuBigDecimal.valueOf(implicitHeight));
        this.setImplicitWidth(SisuBigDecimal.valueOf(implicitWidth));
    }

    /**
     * Creates a new Rectangle with a given width and height.
     *
     * @param implicitWidth the width of the new Rectangle.
     * @param implicitHeight the height of the new Rectangle.
     */
    protected Rectangle(SisuBigDecimal implicitWidth, SisuBigDecimal implicitHeight)
    {
        // This method is protected because the user should never have to deal with implicit dimensions.
        this.setImplicitHeight(implicitHeight);
        this.setImplicitWidth(implicitWidth);
    }

    public String getSVG() {
        if (this.getExplicitWidth() == null || this.getExplicitHeight() == null)
        {
            throw new UnsupportedOperationException("Cannot get SVG without setting explicit dimensions");
        }
        String radiusStringValue;
        SisuBigDecimal radiusExplicitValue = this.getImplicitHalfHeight();
        radiusStringValue = SVG.toString(radiusExplicitValue);
        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("<rect");
        svgBuilder.append(" width=\"");
        svgBuilder.append(this.getExplicitWidth().toPlainString());
        svgBuilder.append("\"");
        svgBuilder.append(" height=\"");
        svgBuilder.append(this.getExplicitHeight().toPlainString());
        svgBuilder.append("\"");
        if (this.getExplicitXPositionLeft() != null) {
            svgBuilder.append(" x=\"");
            svgBuilder.append(SVG.toString(this.getExplicitXPositionLeft()));
            svgBuilder.append("\"");
        }
        if (this.getExplicitYPositionTop() != null) {
            svgBuilder.append(" y=\"");
            svgBuilder.append(SVG.toString(this.getExplicitYPositionTop()));
            svgBuilder.append("\"");
        }
        svgBuilder.append(" />");
        return svgBuilder.toString();
    }


}
