package com.aarrelaakso.drawl;

import java.math.BigDecimal;

public class Rectangle extends Shape {

    public String getSVG() {
        String radiusStringValue;
        BigDecimal radiusExplicitValue = this.getImplicitHalfHeight();
        if (radiusExplicitValue == null) {
            throw new UnsupportedOperationException("Cannot draw a Square with no height");
        } else {
            radiusStringValue = SVG.toString(radiusExplicitValue);
        }
        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("<rect");
        svgBuilder.append(" width=\"");
        svgBuilder.append(this.getExplicitWidth());
        svgBuilder.append("\"");
        svgBuilder.append(" height=\"");
        svgBuilder.append(this.getExplicitHeight());
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


}
