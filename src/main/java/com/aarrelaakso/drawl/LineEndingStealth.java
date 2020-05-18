package com.aarrelaakso.drawl;

/**
 * Represents a square (box) line ending.
 *
 * The area of this line ending is 16, so its sides are of length 4.
 */
public class LineEndingStealth extends LineEndingRectangle {

    @Override
    protected Type getLineEndingType() {
        return Type.STEALTH;
    }

    @Override
    protected String getSVG() {
        StringBuilder svg = new StringBuilder();
        svg.append(" viewBox='0 0 8 8' markerWidth='8' markerHeight='8' refX='4' refY='4'>" + newLine);
        svg.append("<path d='M1,1 L4,4 L1,7 L7,4 z'");
        return svg.toString();
    }

}
