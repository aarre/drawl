package com.aarrelaakso.drawl;

import static java.lang.Math.sqrt;

public class LineEndingDot extends LineEnding {

    @Override
    protected Type getLineEndingType() {
        return Type.DOT;
    }

    @Override
    protected String getSVG() {
        StringBuffer svg = new StringBuffer();

        double radius = 4 / sqrt(Math.PI);
        double diameter = 2 * radius;
        svg.append(" viewBox='0 0 " + (diameter + 2) + " " + (diameter + 2) + "'");
        svg.append(" markerWidth='" + (diameter + 2) + "' markerHeight='" + (diameter + 2) + "'");
        svg.append(" refX='" + (radius + 1) + "' refY='" + (radius + 1) + "'>" + newLine);
        svg.append("<circle cx='" + (radius + 1) + "' cy='" + (radius + 1) + "' r='" + radius + "'");

        return svg.toString();
    }

}
