package com.aarrelaakso.drawl;

/**
 * Represents a kite line ending.
 */
public class LineEndingKite extends LineEndingRectangle {

    public LineEndingKite() {
        this.setStroke("black");
    }

    @Override
    protected Type getLineEndingType() {
        return Type.KITE;
    }

    @Override
    protected String getSVG() {
        StringBuilder svg = new StringBuilder();
        double height = 6.0 * this.getHeight();
        double width = 6.0 * this.getWidth();
        double inset = 3.0 * this.getWidth();
        svg.append(" viewBox='0 0 ");
        svg.append(new DrawlNumber(width + 2.0).toSVG());
        svg.append(" ");
        svg.append(new DrawlNumber(height + 2.0).toSVG());
        svg.append("' markerWidth='");
        svg.append(new DrawlNumber(width + 2.0).toSVG());
        svg.append("' markerHeight='");
        svg.append(new DrawlNumber(height + 2.0).toSVG());
        svg.append("' refX='");
        svg.append(new DrawlNumber(width / 2.0 + 1).toSVG());
        svg.append("' refY='");
        svg.append(new DrawlNumber(height / 2.0 + 1).toSVG());
        svg.append("'>").append(newLine);
        svg.append("<path d='M1,");
        svg.append(new DrawlNumber(height / 2.0 + 1.0).toSVG());
        svg.append(" L");
        svg.append(new DrawlNumber(inset).toSVG());
        svg.append(",");
        svg.append(new DrawlNumber(height + 1.0).toSVG());
        svg.append(" L");
        svg.append(new DrawlNumber(width + 1.0).toSVG());
        svg.append(",");
        svg.append(new DrawlNumber(height / 2.0 + 1.0).toSVG());
        svg.append(" L");
        svg.append(3);
        svg.append(",1 z'");
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
