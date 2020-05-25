/*
 * Copyright (c) 2020. Aarre Laakso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/**
 * \file
 * Defines the Line class and associated constructs.
 */

package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents lines.
 */
public class Line extends Shape {

    /**
     * Sets options for how a line can be oriented.
     */
    public enum Orientation {
        HORIZONTAL,       /*!< Orient a Line horizontally. */
        VERTICAL          /*!< Orient a Line vertically. */
    }

    /**
     * Determines the type of arrowhead on a line.
     */
    @Nullable
    private LineEnding lineEnding;

    /**
     * Determines how a line should be oriented.
     */
    @Nullable
    private Orientation orientation;

    /**
     * Stores the implicit coordinates of one end of this Line.
     */
    @NotNull
    private Point point1Implicit = new Point(0, 0);

    /**
     * Stores the implicit coordinates of the other end of this this line.
     */
    @NotNull
    private Point point2Implicit = new Point(0, 0);

    /**
     * The line thickness (stroke width)
     */
    private double thickness = 1.0;


    /**
     * Constructs a default line whose ends are unknown.
     */
    public Line() {

    }

    /**
     * Constructs a line at a particular orientation.
     *
     * @param orientation how the line should be oriented.
     */
    public Line(Orientation orientation) {

        this.orientation = orientation;
        if (this.orientation == Orientation.HORIZONTAL) {
            //this.point1Implicit = new Point(0.0, 0.49);
            this.point1Implicit = new Point(0.0, 0.50);
            //this.point2Implicit = new Point(1.0, 0.51);
            this.point2Implicit = new Point(1.0, 0.50);
        } else if (this.orientation == Orientation.VERTICAL) {
            this.point1Implicit = new Point(0.5, 0.0);
            this.point2Implicit = new Point(0.5, 1.0);
        } else {
            throw new UnsupportedOperationException("Orientation must be horizontal or vertical");
        }
        this.constructFromPoints(point1Implicit, point2Implicit);
    }

    /**
     * Constructs a line from one point to another
     *
     * @param point1Implicit The origin of the line in implicit coordinates
     * @param point2Implicit The end of the line in implicit coordinates
     */
    public Line(@NotNull final Point point1Implicit, @NotNull final Point point2Implicit) {
        this.constructFromPoints(point1Implicit, point2Implicit);
    }

    /**
     * Constructs a Line from one point to another. Called by multiple constructors.
     *
     * @param point1Implicit The origin of the Line in implicit coordinates.
     * @param point2Implicit The end of the Line in implicit coordinates.
     */
    private void constructFromPoints(@NotNull Point point1Implicit, @NotNull Point point2Implicit) {
        this.point1Implicit = point1Implicit;
        this.point2Implicit = point2Implicit;

        final Number implicitWidth = point2Implicit.getX().subtract(point1Implicit.getX());
        this.setImplicitWidth(implicitWidth);

        //final Number implicitHeight = point2Implicit.getY().subtract(point1Implicit.getY());
        final Number implicitHeight = DrawlNumber.ZERO;
        this.setImplicitHeight(implicitHeight);

        final Number implicitCenterX = point1Implicit.getX().add(implicitWidth.divide(DrawlNumber.TWO,
                DrawlNumber.mcOperations));
        this.setImplicitXPositionCenter(implicitCenterX);

        final Number implicitCenterY = point1Implicit.getY().add(implicitHeight.divide(DrawlNumber.TWO,
                DrawlNumber.mcOperations));
        this.setImplicitYPositionCenter(implicitCenterY);
    }

    public void addLineEnding(final LineEnding lineEnding) {
        this.lineEnding = lineEnding;
    }

    private LineEnding getLineEnding() {
        return this.lineEnding;
    }

    @Nullable
    protected Point getPoint1Explicit() {
        final Number point1ExplicitX = this.getExplicitXPositionLeft();
        @NotNull final Number point1ExplicitY = this.getExplicitYPositionBottom();

        return new Point(point1ExplicitX, point1ExplicitY);
    }

    @Nullable
    protected Point getPoint2Explicit() {
        final Number point2ExplicitX = this.getExplicitXPositionRight();
        @NotNull final Number point2ExplicitY = this.getExplicitYPositionTop();

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

        String newLine = System.getProperty("line.separator");
        @NotNull final StringBuilder svgBuilder = new StringBuilder();
        if (this.hasArrowhead()) {
            svgBuilder.append(this.getLineEnding().getSVGDef(this.getThickness()));
        }
        svgBuilder.append(newLine + "<line");
        svgBuilder.append(" x1='");
        svgBuilder.append(this.getPoint1Explicit().getX().toSVG());
        svgBuilder.append("'");
        svgBuilder.append(" y1='");
        svgBuilder.append(this.getPoint1Explicit().getY().toSVG());
        svgBuilder.append("'");
        svgBuilder.append(" x2='");
        svgBuilder.append(this.getPoint2Explicit().getX().toSVG());
        svgBuilder.append("'");
        svgBuilder.append(" y2='");
        svgBuilder.append(this.getPoint2Explicit().getY().toSVG());
        svgBuilder.append("'");

        if (this.getFill() != null) {
            svgBuilder.append(" fill='");
            svgBuilder.append(this.getFill());
            svgBuilder.append("'");
        }
        svgBuilder.append(" stroke='");
        if (this.getStroke() != null) {
            svgBuilder.append(this.getStroke());
        } else {
            //Our lines are visible by default....
            svgBuilder.append("black");
        }
        svgBuilder.append("'");

        svgBuilder.append(" stroke-width='");
        svgBuilder.append(DrawlNumber.valueOf(this.getThickness()).toSVG());
        svgBuilder.append("'");

        if (this.hasArrowhead()) {
            svgBuilder.append(" marker-end='url(#");
            svgBuilder.append(this.lineEnding.getLineEndingType());
            svgBuilder.append("-");
            svgBuilder.append(this.lineEnding.getUniqueId());
            svgBuilder.append(")'");
        }

        svgBuilder.append(" />");
        svgBuilder.append(newLine);
        if (this.getText() != null) {
            svgBuilder.append(this.getText().getSVG());
        }
        return svgBuilder.toString();
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public double getThickness() {
        return this.thickness;
    }

    public boolean hasArrowhead() {
        if (this.lineEnding != null) {
            return true;
        } else {
            return false;
        }
    }

}
