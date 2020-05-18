package com.aarrelaakso.drawl;

/**
 * Represents a square (box) line ending.
 *
 * The area of this line ending is 16, so its sides are of length 4.
 */
public class LineEndingSquare extends LineEndingRectangle {

    @Override
    protected Type getLineEndingType() {
        return Type.BOX;
    }

    @Override
    protected String getSVG() {
        StringBuilder svg = new StringBuilder();
        svg.append(" viewBox='0 0 6 6' markerWidth='6' markerHeight='6' refX='3' refY='3'>" + newLine);
        svg.append("<path d='M1,1 L1,5 L5,5 L5,1 z'");
        return svg.toString();
    }

}
