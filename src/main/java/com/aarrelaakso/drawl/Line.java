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

public class Line extends Shape {

    @NotNull
    private Point point1Implicit = new Point(0,0);

    @NotNull
    private Point point2Implicit = new Point(0, 0);

    @Nullable
    private Point point1Explicit;

    @Nullable
    private Point point2Explicit;

    /**
     * Constructs a default line whose ends are unknown.
     */
    public Line()
    {

    }

    /**
     * Constructs a line from one point to another
     *
     * @param point1Implicit The origin of the line in implicit coordinates
     * @param point2Implicit The end of the line in implicit coordinates
     */
    public Line(@NotNull Point point1Implicit, @NotNull Point point2Implicit)
    {
        this.point1Implicit = point1Implicit;
        this.point2Implicit = point2Implicit;
    }

    @Nullable
    protected Point getPoint1Explicit()
    {
        DrawlNumber point1ExplicitX = getExplicitXPositionLeft();
        DrawlNumber point1ExplicitY = getExplicitYPositionBottom();

        return new Point(point1ExplicitX, point1ExplicitY);
    }

    @Nullable
    protected Point getPoint2Explicit()
    {
        DrawlNumber point2ExplicitX = getExplicitXPositionRight();
        DrawlNumber point2ExplicitY = getExplicitYPositionTop();

        return new Point(point2ExplicitX, point2ExplicitY);
    }

    @NotNull
    protected Point getPoint1Implicit()
    {
        return this.point1Implicit;
    }

    @NotNull
    protected Point getPoint2Implicit()
    {
        return this.point2Implicit;
    }

    public String getSVG() {
        if (this.getExplicitWidth() == null || this.getExplicitHeight() == null)
        {
            throw new UnsupportedOperationException("Cannot get SVG without setting explicit dimensions");
        }
        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("\n<line");
        svgBuilder.append(" x1=\"");
        svgBuilder.append(this.getPoint1Explicit().getX().toPlainString());
        svgBuilder.append("\"");
        svgBuilder.append(" y1=\"");
        svgBuilder.append(this.getPoint1Explicit().getY().toPlainString());
        svgBuilder.append("\"");
        svgBuilder.append(" x2=\"");
        svgBuilder.append(this.getPoint2Explicit().getX().toPlainString());
        svgBuilder.append("\"");
        svgBuilder.append(" y2=\"");
        svgBuilder.append(this.getPoint2Explicit().getY().toPlainString());
        svgBuilder.append("\"");

        if (this.getFill() != null)
        {
            svgBuilder.append(" fill=\"");
            svgBuilder.append(this.getFill());
            svgBuilder.append("\"");
        }
        if (this.getStroke() != null)
        {
            svgBuilder.append(" stroke=\"");
            svgBuilder.append(this.getStroke());
            svgBuilder.append("\"");
        }
        //svgBuilder.append(" style=\"stroke:rgb(255,0,0);stroke-width:2\"");
        svgBuilder.append(" />");
        if (this.getText() != null)
        {
            svgBuilder.append(this.getText().getSVG());
        }
        return svgBuilder.toString();
    }

}
