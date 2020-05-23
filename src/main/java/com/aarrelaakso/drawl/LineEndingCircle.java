package com.aarrelaakso.drawl;

import static java.lang.Math.sqrt;

public class LineEndingCircle extends LineEnding {

    LineEndingCircle() {
        this.setFill("white");
        this.setStroke("black");
    }

    @Override
    protected Type getLineEndingType() {
        return Type.CIRCLE;
    }

    @Override
    protected String getSVG() {
        StringBuilder svg = new StringBuilder();
        double radius = this.getWidth() * 4.0 / sqrt(Math.PI);
        double diameter = 2 * radius;
        svg.append(" viewBox='0 0 " + (diameter + 2) + " " + (diameter + 2) + "'");
        svg.append(" markerWidth='" + (diameter + 2) + "' markerHeight='" + (diameter + 2) + "'");
        svg.append(" refX='" + (radius + 1) + "' refY='" + (radius + 1) + "'>" + newLine);
        svg.append("<circle cx='" + (radius + 1) + "' cy='" + (radius + 1) + "' r='" + radius + "'");
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
