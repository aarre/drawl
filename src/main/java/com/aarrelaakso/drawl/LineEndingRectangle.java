package com.aarrelaakso.drawl;

import static java.lang.Math.sqrt;

/**
 * Represents a rectangular line ending.
 * A rectangle is a generalization of a square that has different width and height.
 */
public class LineEndingRectangle extends LineEnding {

    @Override
    protected Type getLineEndingType() {
        return Type.RECTANGLE;
    }

    @Override
    protected String getSVG() {
        StringBuffer svg = new StringBuffer();
        double height = this.getHeight() * 2.0 * sqrt(2.0);
        double width = this.getWidth() * 2.0 * height;
        svg.append(" viewBox='0 0 " + (width + 2) + " " + (height + 2) + "'");
        svg.append(" markerWidth='" + (width + 2) + "'");
        svg.append(" markerHeight='" + (height + 2) + "'");
        svg.append(" refX='" + (width / 2.0 + 1) + "'");
        svg.append(" refY='" + (height / 2.0 + 1) + "'>" + LineEnding.newLine);
        svg.append("<path d='M1,1 L1," + (height + 1) + " L" + (width + 1) + "," + (height + 1) + " L" + (width + 1) + ",1 z'");
        if (this.getStroke() != "") {
            svg.append(" stroke='");
            svg.append(this.getStroke());
            svg.append("'");
        }
        svg.append(" fill='");
        svg.append(this.getFill());
        svg.append("'");
        return svg.toString();
    }

}
