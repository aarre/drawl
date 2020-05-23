package com.aarrelaakso.drawl;

import static java.lang.Math.sqrt;

/**
 * < A special case of the DIAMOND/RHOMBUS line ending in which the angles are all 90 degrees.
 *
 * To get a turned square shape with an area of 16, we set the crossways dimensions at
 *
 * \f[4 sqrt(2) \approx 5.66\f]
 */
public class LineEndingTurnedSquare extends LineEnding {

    @Override
    protected Type getLineEndingType() {
        return Type.TURNED_SQUARE;
    }

    @Override
    protected String getSVG() {
        StringBuilder svg = new StringBuilder();
        double diagonal = this.getWidth() * 4.0 * sqrt(2.0);
        double half_diag = diagonal / 2.0;
        svg.append(" viewBox='0 0 " + (diagonal + 2) + " " + (diagonal + 2) + "'");
        svg.append(" markerWidth='" + (diagonal + 2) + "' markerHeight='" + (diagonal + 2) + "'");
        svg.append(" refX='" + (half_diag + 1) + "' refY='" + (half_diag + 1) + "'>" + newLine);
        svg.append("<path d='M" + (half_diag + 1) + ",1 L" + (diagonal + 1) + "," + (half_diag + 1) + " L" + (half_diag + 1) + "," + (diagonal + 1) + " L1," + (half_diag + 1) + " z'");
        return svg.toString();
    }


}
