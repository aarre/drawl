package com.aarrelaakso.drawl;

public class Rectangle extends Shape {

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
