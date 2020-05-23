package com.aarrelaakso.drawl;

import static java.lang.Math.sqrt;

/**
 * A dot-shaped line ending. Synonyms: DISK
 * <p>
 * For the dot/disk line ending, we need
 * <p>
 * \f[\pi r^2 = 16\f]
 * <p>
 * so
 * <p>
 * \f[r^2 = 16/\pi\f]
 * <p>
 * \f[r = \sqrt{\frac{16}{\pi}}\f]
 * <p>
 * \f[r = \frac{4}{\sqrt \pi}\f]
 * <p>
 * \f[r \approx 2.26\f]
 */


public class LineEndingDot extends LineEnding {

    LineEndingDot() {
        this.setStroke("black");
    }

    @Override
    protected Type getLineEndingType() {
        return Type.DOT;
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
