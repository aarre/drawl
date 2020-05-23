package com.aarrelaakso.drawl;

import static java.lang.Math.sqrt;

public class LineEndingEllipse extends LineEnding {

    @Override
    protected Type getLineEndingType() {
        return Type.ELLIPSE;
    }

    @Override
    protected String getSVG() {
        StringBuilder svg = new StringBuilder();
        double radiusX = this.getWidth() * 3.0;
        double radiusY = this.getHeight() * 2.0;
        double diameterX = 2.0 * radiusX;
        double diameterY = 2.0 * radiusY;
        svg.append(" viewBox='0 0 ");
        svg.append(new DrawlNumber(diameterX + 2).toSVG());
        svg.append(" ");
        svg.append(new DrawlNumber(diameterY + 2).toSVG());
        svg.append("'");
        svg.append(" markerWidth='");
        svg.append(new DrawlNumber(diameterX + 2).toSVG());
        svg.append("' markerHeight='");
        svg.append(new DrawlNumber(diameterY + 2).toSVG());
        svg.append("'");
        svg.append(" refX='");
        svg.append(new DrawlNumber(radiusX + 1).toSVG());
        svg.append("' refY='");
        svg.append(new DrawlNumber(radiusY + 1).toSVG());
        svg.append("'>");
        svg.append(newLine);
        svg.append("<ellipse cx='");
        svg.append(new DrawlNumber(radiusX + 1).toSVG());
        svg.append("' cy='");
        svg.append(new DrawlNumber(radiusY + 1).toSVG());
        svg.append("' rx='");
        svg.append(new DrawlNumber(radiusX).toSVG());
        svg.append("' ry='");
        svg.append(new DrawlNumber(radiusY).toSVG());
        svg.append("'");
        svg.append(" stroke='black'");
        svg.append(" fill='");
        svg.append(this.getFill());
        svg.append("'");
        return svg.toString();
    }
}
