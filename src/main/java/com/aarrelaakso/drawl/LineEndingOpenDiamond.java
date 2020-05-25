package com.aarrelaakso.drawl;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Represents an open (unfilled) diamond-shaped line ending.
 */
public class LineEndingOpenDiamond extends LineEnding {

    LineEndingOpenDiamond() {
        this.setFill("white");
        this.setStroke("black");
    }

    @Override
    protected Type getLineEndingType() {
        return Type.OPEN_DIAMOND;
    }

    @Override
    protected String getSVG() {
        StringBuilder svg = new StringBuilder();
        double height = this.getHeight() * sqrt(32.0) / pow(3.0, 1.0 / 4.0);                       // Approx. 4.3
        double width = this.getWidth() * sqrt(3.0) * height;                                      // Approx. 7.44
        svg.append(" viewBox='0 0 " + (width + 2) + " " + (height + 2) + "'");
        svg.append(" markerWidth='" + (width + 2) + "' markerHeight='" + (height + 2) + "'");
        svg.append(" refX='" + (width / 2.0 + 1) + "' refY='" + (height / 2.0 + 1) + "'>" + newLine);
        svg.append("<path d='M1," + (height / 2.0 + 1) + " L" + (width / 2.0 + 1) + "," + (height + 1) + " L" + (width + 1) + "," + (height / 2.0 + 1) + "L" + (width / 2.0 + 1) + ",1 z'");
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
