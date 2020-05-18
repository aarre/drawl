package com.aarrelaakso.drawl;

/**
 * Represents a kite line ending.
 */
public class LineEndingKite extends LineEndingRectangle {

    @Override
    protected Type getLineEndingType() {
        return Type.KITE;
    }

    @Override
    protected String getSVG() {
        StringBuilder svg = new StringBuilder();
        svg.append(" viewBox='0 0 8 8' markerWidth='8' markerHeight='8' refX='4' refY='4'>" + newLine);
        svg.append("<path d='M1,4 L3,7 L7,4 L3,1 z'");
        return svg.toString();
    }

}
