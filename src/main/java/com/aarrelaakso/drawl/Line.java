/*
 * Copyright (c) 2020. Aarre Laakso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents lines.
 */
public class Line extends Shape {

    @NotNull
    private Point point1Implicit = new Point(0, 0);

    @NotNull
    private Point point2Implicit = new Point(0, 0);

    @Nullable
    private Point point1Explicit;

    @Nullable
    private Point point2Explicit;

    /**
     * Constructs a default line whose ends are unknown.
     */
    public Line() {

    }

    /**
     * Constructs a line from one point to another
     *
     * @param point1Implicit The origin of the line in implicit coordinates
     * @param point2Implicit The end of the line in implicit coordinates
     */
    public Line(@NotNull Point point1Implicit, @NotNull Point point2Implicit) {
        this.point1Implicit = point1Implicit;
        this.point2Implicit = point2Implicit;

        DrawlNumber implicitWidth = point2Implicit.getX().subtract(point1Implicit.getX());
        DrawlNumber implicitHeight = point2Implicit.getY().subtract(point1Implicit.getY());

        DrawlNumber implicitCenterX = point1Implicit.getX().add(implicitWidth.divide(DrawlNumber.TWO,
                DrawlNumber.mcOperations));
        DrawlNumber implicitCenterY = point1Implicit.getY().add(implicitHeight.divide(DrawlNumber.TWO,
                DrawlNumber.mcOperations));

        this.setImplicitWidth(implicitWidth);
        this.setImplicitHeight(implicitHeight);
        this.setImplicitXPositionCenter(implicitCenterX);
        this.setImplicitYPositionCenter(implicitCenterY);
    }

    @Nullable
    protected Point getPoint1Explicit() {
        DrawlNumber point1ExplicitX = this.getExplicitXPositionLeft();
        @NotNull DrawlNumber point1ExplicitY = this.getExplicitYPositionBottom();

        return new Point(point1ExplicitX, point1ExplicitY);
    }

    @Nullable
    protected Point getPoint2Explicit() {
        DrawlNumber point2ExplicitX = this.getExplicitXPositionRight();
        @NotNull DrawlNumber point2ExplicitY = this.getExplicitYPositionTop();

        return new Point(point2ExplicitX, point2ExplicitY);
    }

    @NotNull
    protected Point getPoint1Implicit() {
        return this.point1Implicit;
    }

    @NotNull
    protected Point getPoint2Implicit() {
        return this.point2Implicit;
    }

    public @NotNull String getSVG() {
        if (this.getExplicitWidth() == null || this.getExplicitHeight() == null) {
            throw new UnsupportedOperationException("Cannot get SVG without setting explicit dimensions");
        }
        @NotNull StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("\n<line");
        svgBuilder.append(" x1=\"");
        svgBuilder.append(this.getPoint1Explicit().getX().toSVG());
        svgBuilder.append("\"");
        svgBuilder.append(" y1=\"");
        svgBuilder.append(this.getPoint1Explicit().getY().toSVG());
        svgBuilder.append("\"");
        svgBuilder.append(" x2=\"");
        svgBuilder.append(this.getPoint2Explicit().getX().toSVG());
        svgBuilder.append("\"");
        svgBuilder.append(" y2=\"");
        svgBuilder.append(this.getPoint2Explicit().getY().toSVG());
        svgBuilder.append("\"");

        if (this.getFill() != null) {
            svgBuilder.append(" fill=\"");
            svgBuilder.append(this.getFill());
            svgBuilder.append("\"");
        }
        if (this.getStroke() != null) {
            svgBuilder.append(" stroke=\"");
            svgBuilder.append(this.getStroke());
            svgBuilder.append("\"");
        }
        svgBuilder.append(" />");
        if (this.getText() != null) {
            svgBuilder.append(this.getText().getSVG());
        }
        return svgBuilder.toString();
    }

}
