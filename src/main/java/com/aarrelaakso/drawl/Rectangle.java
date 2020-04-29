package com.aarrelaakso.drawl;

public class Rectangle extends Shape {

    private final SisuBigDecimal height;
    private final SisuBigDecimal width;

    /**
     * Creates a new Rectangle with the default width and height.
     */
    public Rectangle()
    {
        this.height = SisuBigDecimal.ONE;
        this.width = SisuBigDecimal.ONE;
    }

    /**
     * Creates a new Rectangle with a given width and height.
     *
     * @param implicitWidth the width of the new Rectangle.
     * @param implicitHeight the height of the new Rectangle.
     */
    public Rectangle(Integer implicitWidth, Integer implicitHeight)
    {
        this.height = SisuBigDecimal.valueOf(implicitHeight);
        this.width = SisuBigDecimal.valueOf(implicitWidth);
    }

    /**
     * Creates a new Rectangle with a given width and height.
     *
     * @param implicitWidth the width of the new Rectangle.
     * @param implicitHeight the height of the new Rectangle.
     */
    public Rectangle(SisuBigDecimal implicitWidth, SisuBigDecimal implicitHeight)
    {
        this.height = implicitHeight;
        this.width = implicitWidth;
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
