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
        double height = 6.0 * this.getHeight();
        double width = 6.0 * this.getWidth();
        svg.append(" viewBox='0 0 ");
        svg.append(new DrawlNumber(width + 2.0).toSVG());
        svg.append(" ");
        svg.append(new DrawlNumber(height + 2.0).toSVG());
        svg.append("'");
        svg.append(" markerWidth='");
        svg.append(new DrawlNumber(width + 2.0).toSVG());
        svg.append("'");
        svg.append(" markerHeight='");
        svg.append(new DrawlNumber(height + 2.0).toSVG());
        svg.append("'");
        svg.append(" refX='");
        svg.append(new DrawlNumber(width / 2.0 + 1.0).toSVG());
        svg.append("'");
        svg.append(" refY='");
        svg.append(new DrawlNumber(height / 2.0 + 1.0).toSVG());
        svg.append("'>");
        svg.append(newLine);
        svg.append("<path d='M1,1");
        svg.append(" L");
        svg.append(new DrawlNumber(width / 2.0 + 1.0).toSVG());
        svg.append(",");
        svg.append(new DrawlNumber(height / 2.0 + 1.0).toSVG());
        svg.append(" L1,");
        svg.append(new DrawlNumber(height + 1.0).toSVG());
        svg.append(" L");
        svg.append(new DrawlNumber(width + 1.0).toSVG());
        svg.append(",");
        svg.append(new DrawlNumber(height / 2.0 + 1.0).toSVG());
        svg.append(" z'");
        return svg.toString();
    }

}
