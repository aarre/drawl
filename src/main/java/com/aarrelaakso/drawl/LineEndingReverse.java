package com.aarrelaakso.drawl;

import static java.lang.Math.pow;

/**
 * Represents a line ending that is the reverse of the default (triangle) line ending.
 * Reverse means that the line ending is reflected about the axis formed by the line.
 */
public class LineEndingReverse extends LineEnding {


    @Override
    protected Type getLineEndingType() {
        return Type.REVERSE;
    }

    @Override
    protected String getSVG() {
        StringBuilder svg = new StringBuilder();
        // See the API documentation for the rationale for these calculations
        double quotient = 4096.0 / 15.0;
        // Take the 4th root of of the quotient
        double height = pow(quotient, 1.0 / 4.0);                      // approx. 4.07
        double width = 32.0 / height;                                    // approx. 7.87
        width = width * this.getWidth();
        height = height * this.getHeight();
        svg.append(" viewBox='0 0 " + (width + 2) + " " + (height + 2) + "'");
        svg.append(" markerWidth='" + (width + 2) + "' markerHeight='" + (height + 2) + "'");
        svg.append(" refX='" + (width / 2.0 + 1) + "' refY='" + (height / 2.0 + 1) + "'>" + newLine);
        svg.append("<path d='M1," + (height / 2.0 + 1) + "L" + (width + 1) + "," + (height + 1) + " L" + (width + 1) + "," + 1 + " z'");
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
