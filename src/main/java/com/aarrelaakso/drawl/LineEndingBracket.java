package com.aarrelaakso.drawl;

public class LineEndingBracket extends LineEnding {

    public LineEndingBracket() {
        this.setStroke("black");
        this.setFill("white");
    }

    @Override
    protected Type getLineEndingType() {
        return Type.BRACKET;
    }

    @Override
    protected String getSVG() {
        StringBuffer svg = new StringBuffer();
        double height = 6.0 * this.getHeight();
        double width = 3.0 * this.getHeight();
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
        svg.append(new DrawlNumber(width + 1.0).toSVG());
        svg.append("'");
        svg.append(" refY='");
        svg.append(new DrawlNumber(height / 2.0 + 1.0).toSVG());
        svg.append("'>");
        svg.append(LineEnding.newLine);
        svg.append("<path d='M1,1 L");
        svg.append(new DrawlNumber(width + 1.0).toSVG());
        svg.append(",1 L");
        svg.append(new DrawlNumber(width + 1.0).toSVG());
        svg.append(",");
        svg.append(new DrawlNumber(height + 1.0).toSVG());
        svg.append(" L1,");
        svg.append(new DrawlNumber(height + 1.0).toSVG());
        svg.append("'");
        if (this.getStroke() != "") {
            svg.append(" stroke='");
            svg.append(this.getStroke());
            svg.append("'");
        }
        svg.append(" fill='");
        svg.append(this.getFill());
        svg.append("'");
        svg.append(" fill-opacity='0.0'");
        return svg.toString();
    }
}