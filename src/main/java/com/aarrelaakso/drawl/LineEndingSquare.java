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
        double height = 4.0 * this.getHeight();
        double width = 4.0 * this.getWidth();
        StringBuilder svg = new StringBuilder();
        svg.append(" viewBox='0 0 ");
        svg.append(new DrawlNumber(width + 2.0).toSVG());
        svg.append(" ");
        svg.append(new DrawlNumber(height + 2.0).toSVG());
        svg.append("'");
        svg.append(" markerWidth='");
        svg.append(new DrawlNumber(width + 2.0).toSVG());
        svg.append("' markerHeight='");
        svg.append(new DrawlNumber(height + 2.0).toSVG());
        svg.append("'");
        svg.append(" refX='");
        svg.append(new DrawlNumber(width / 2.0 + 1.0).toSVG());
        svg.append("' refY='");
        svg.append(new DrawlNumber(height / 2.0 + 1.0).toSVG());
        svg.append("'>");
        svg.append(newLine);
        svg.append("<path d='M1,1 L1,");
        svg.append(new DrawlNumber(width + 1.0).toSVG());
        svg.append(" L");
        svg.append(new DrawlNumber(width + 1.0).toSVG());
        svg.append(",");
        svg.append(new DrawlNumber(width + 1.0).toSVG());
        svg.append(" L");
        svg.append(new DrawlNumber(width + 1.0).toSVG());
        svg.append(",1 z'");
        return svg.toString();
    }

}
