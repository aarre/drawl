package com.aarrelaakso.drawl;

/**
 * Represents a line ending consisting of a bar at the end of the line.
 */
public class LineEndingBar extends LineEnding {

    @Override
    protected Type getLineEndingType() {
        return Type.BAR;
    }

    @Override
    protected String getSVG() {
        StringBuffer svg = new StringBuffer();
        double height = 6.0 * this.getHeight();
        double width = 1.0 * this.getHeight();
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