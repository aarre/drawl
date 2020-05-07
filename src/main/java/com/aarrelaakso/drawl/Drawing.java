/*
 * Drawl, the world's best drawing language.
 *
 * Copyright (c) 2020 Aarre Laakso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.aarrelaakso.drawl;

import com.google.common.flogger.FluentLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Represents drawings.
 */
public class Drawing {

    private static final @NotNull FluentLogger logger;

    static {
        logger = FluentLogger.forEnclosingClass();

    }

    private final @NotNull HashSet<Shape> contents;
    private Number explicitHeight;
    private Number explicitWidth;
    private static final int initialCapacity = 16;
    private static final float loadFactor = 0.75f;

    public Drawing() {
        this.contents = new HashSet<>(Drawing.initialCapacity, Drawing.loadFactor);
    }

    /**
     * Add a shape to this drawing
     *
     * @param shape The shape to add
     */
    public final void add(@NotNull final Shape shape) {
        if (this.isExplicitSet()) {
            throw new UnsupportedOperationException("Cannot add shapes after setting a drawing's explicit dimensions");
        }
        this.contents.add(shape);
        this.updateShape(shape);
    }

    /**
     * Get the explicit height of this Drawing.
     *
     * @return The explicit height of this Drawing, or null if the drawing has length zero or the explicit
     * dimensions have not been set.
     */
    @Nullable
    public final Number getExplicitHeight() {
        return this.explicitHeight;
    }

    /**
     * Set the explicit height of this Drawing.
     * <p>
     * This method also calculates the explicit heights and y positions of all the drawing's contents.
     * If the explicit width of this Drawing is not known, then this method also sets the explicit width
     * to match the aspect ratio of the contents.
     *
     * @param drawingExplicitHeight The explicit height of the drawing.
     */
    private void setExplicitHeight(@NotNull final Number drawingExplicitHeight) {
        Drawing.logger.atFine().log("Setting explicit height");
        // Note that this line, by changing this.explicitWidth, can change the explicit to implicit ratio
        this.setExplicitHeightInternal(drawingExplicitHeight);
        if (this.getExplicitWidth() == null) {
            // If this Drawing does not have an explicit width, make the explicit width of the Drawing as wide as it
            // needs to be to accommodate the contents.
            Drawing.logger.atFine().log("Adjusting explicit width to fit");
            this.setExplicitWidthInternal(this.getImplicitWidthOfContents().multiply(this.getExplicitToImplicitRatio(),
                    DrawlNumber.mcOperations));
        }
        for (@NotNull final Shape shape : this.contents) {
            this.updateShape(shape);
        }
    }

    /**
     * Set the explicit height of this Drawing
     *
     * @param height the explicit height of the Drawing; it is a Float to match
     *               the precision allowed by the SVG spec.
     */
    public final void setExplicitHeight(final Float height) {
        this.setExplicitHeight(DrawlNumber.valueOf(height));
    }

    /**
     * Set the explicit height of this Drawing.
     *
     * @param height The explicit height of the Drawing; it is an Integer to allow for common use cases.
     */
    public final void setExplicitHeight(final Integer height) {
        this.setExplicitHeight(DrawlNumber.valueOf(height));
    }

    /**
     * Get the ratio of the explicit height of this Drawing to the implicit height of its contents.
     *
     * @return The ratio of the explicit height of this Drawing to the implicit height of its contents
     */
    private Number getExplicitHeightPerImplicitHeight() {
        final Number implicitHeightOfContents = this.getImplicitHeightOfContents();
        final Number explicitHeightPerImplicitHeight;
        if (implicitHeightOfContents.isNotEqualTo(DrawlNumber.ZERO)) {
            if (this.getExplicitHeight() == null) {
                // If the explicit height has no value, try to calculate using the explicit width
                if (this.getExplicitWidth() == null) {
                    // If the explicit width also has no value, the ratio is 0
                    explicitHeightPerImplicitHeight = DrawlNumber.ZERO;
                } else {
                    // The explicit width exceeds 0, so use it to calculate the ratio
                    explicitHeightPerImplicitHeight = this.getExplicitWidth().divide(this.getImplicitWidthOfContents(),
                            DrawlNumber.mcOperations);
                }
            } else {
                // The explicit height has a value, so calculate using the explicit height
                explicitHeightPerImplicitHeight = this.getExplicitHeight().divide(implicitHeightOfContents,
                        DrawlNumber.mcOperations);
            }
        } else {
            // The implicit height of the contents is 0, so the ratio is 0
            explicitHeightPerImplicitHeight = DrawlNumber.ZERO;
        }
        return explicitHeightPerImplicitHeight;
    }

    /**
     * Get the ratio of explicit measures to implicit measures for this Drawing as a whole.
     * <p>
     * This is the ratio of the explicit dimension of the Drawing to the implicit dimension of its contents.
     * <p>
     * This method is different from getExplicitHeightPerImplicitHeight() and getExplicitWidthPerImplicitWidth()
     * in that it takes into account both height constraints and width constraints.
     *
     * @return The ratio of explicit measures to implicit measures for this diagram as a whole.
     */
    // TODO [Issue No. 1] Make this method private and factor out of unit tests.
    public final Number getExplicitToImplicitRatio() {
        final Number explicitHeightPerImplicitHeight = this.getExplicitHeightPerImplicitHeight();
        @NotNull final Number explicitWidthPerImplicitWidth = this.getExplicitWidthPerImplicitWidth();
        if (explicitHeightPerImplicitHeight.isLessThanOrEqualTo(explicitWidthPerImplicitWidth)) {
            return explicitHeightPerImplicitHeight;
        } else {
            return explicitWidthPerImplicitWidth;
        }
    }

    /**
     * Get the explicit width of this Drawing.
     *
     * @return The explicit width of this Drawing, or null if the drawing has length zero or the explicit
     * dimensions have not been set.
     */
    @Nullable
    public final Number getExplicitWidth() {
        return this.explicitWidth;
    }

    /**
     * Set the explicit width of this drawing.
     * <p>
     * This method also calculates the explicit widths and x positions of all the drawing's contents.
     *
     * @param drawingExplicitWidth The explicit width of the drawing.
     */
    private void setExplicitWidth(@NotNull final Number drawingExplicitWidth) {
        // Note that this line, by changing this.explicitWidth, can change the explicit to implicit ratio
        this.setExplicitWidthInternal(drawingExplicitWidth);
        if (this.getExplicitHeight() == null) {
            // If this Drawing does not have an explicit height, make the explicit height of the Drawing as tall as
            // necessary to accommodate the contents.
            this.setExplicitHeightInternal(this.getImplicitWidthOfContents().multiply(this.getExplicitToImplicitRatio(),
                    DrawlNumber.mcOperations));
        }
        for (@NotNull final Shape shape : this.contents) {
            this.updateShape(shape);
        }
    }

    /**
     * Set the explicit width of this Drawing.
     *
     * @param width the explicit width of the Drawing; it is a Float
     *              to match the precision allowed by the SVG spec.
     */
    public final void setExplicitWidth(final Float width) {
        this.setExplicitWidth(DrawlNumber.valueOf(width));

    }

    /**
     * Set the explicit width of this Drawing.
     *
     * @param width The explicit width of the Drawing; it is an Integer to allow for common use cases.
     */
    public final void setExplicitWidth(final Integer width) {
        this.setExplicitWidth(DrawlNumber.valueOf(width));
    }

    /**
     * Get the ratio of the explicit width of this drawing to the implicit width of its contents.
     *
     * @return The ratio of the explicit width of this drawing to the implicit width of its contents.
     */
    // TODO [Issue No. 1] Make this method private and factor out of unit tests.
    @NotNull
    public final Number getExplicitWidthPerImplicitWidth() {
        final Number implicitWidthOfContents = this.getImplicitWidthOfContents();
        assert implicitWidthOfContents.isGreaterThanOrEqualTo(DrawlNumber.ZERO) :
                "The implicit width of the contents of a drawing must be positive (or zero).";
        final Number explicitWidthPerImplicitWidth;
        if (implicitWidthOfContents.isNotEqualTo(DrawlNumber.ZERO)) {
            if (this.getExplicitWidth() == null) {
                // If the explicit width is null, try to use it to calculate using the explicit height
                if (this.getExplicitHeight() == null) {
                    // The explicit height is also null, so the ratio is 0
                    explicitWidthPerImplicitWidth = DrawlNumber.ZERO;
                } else {
                    // Use the explicit height to calculate the ratio
                    explicitWidthPerImplicitWidth = this.getExplicitHeight().divide(this.getImplicitHeightOfContents(),
                            DrawlNumber.mcOperations);
                }
            } else {
                // The explicit width is not null, so calculate using the explicit width
                explicitWidthPerImplicitWidth = this.getExplicitWidth().divide(implicitWidthOfContents,
                        DrawlNumber.mcOperations);
            }
        } else {
            // The implicit width of the contents is 0, so the ratio is 0
            explicitWidthPerImplicitWidth = DrawlNumber.ZERO;
        }
        return explicitWidthPerImplicitWidth;
    }

    /**
     * Get the implicit total height of all contents in the drawing.
     * <p>
     * Provides public access to the implicit height of the contents.
     */
    private Number getImplicitHeight() {
        return this.getImplicitHeightOfContents();
    }

    /**
     * Get the implicit total height of all contents in the drawing.
     *
     * @return the implicit total height of all contents in this drawing
     */
    private Number getImplicitHeightOfContents() {
        return this.getImplicitYMaximum().subtract(this.getImplicitYMinimum());
    }

// --Commented out by Inspection START (5/6/2020 12:34 PM):
//    /**
//     * Get the SVG for this Drawing.
//     *
//     * @param drawingWidth  The desired width of the output;
//     *                      the type is Float to match the SVG spec for numeric precision.
//     * @param drawingHeight The desired height of the output;
//     *                      the type is Float to match the SVG spec for numeric precision.
//     * @return A string of valid SVG that depicts the drawing within the bounds of drawingWidth and drawingHeight.
//     */
//    public @NotNull String getSVG(Float drawingWidth, Float drawingHeight)
//    {
//        this.setExplicitDimensions(drawingWidth, drawingHeight);
//        return this.getSVG();
//    }
// --Commented out by Inspection STOP (5/6/2020 12:34 PM)

    /**
     * Get the implicit total width of all contents in this Drawing.
     * <p>
     * Provides public access to the implicit width of the contents for testing.
     */
    // TODO [Issue No. 1] Make this method private and factor out of unit tests.
    public final Number getImplicitWidth() {
        return this.getImplicitWidthOfContents();
    }

    /**
     * Get the implicit total width of all contents in the drawing
     *
     * @return the implicit total width of all contents in this drawing
     */
    Number getImplicitWidthOfContents() {
        assert this.getImplicitXMaximum().isGreaterThanOrEqualTo(this.getImplicitXMinimum()) :
                "The implicit x-coordinate maximum must be greater than or equal to the implicit x-coordinate minimum.";
        return this.getImplicitXMaximum().subtract(this.getImplicitXMinimum());
    }

    /**
     * Get the maximum x-coordinate of this drawing in implicit terms.
     * <p>
     * The maximum x-coordinate is the right edge of the drawing.
     */
    @NotNull
    private Number getImplicitXMaximum() {
        Number xMaximum = DrawlNumber.valueOf(Double.MIN_VALUE);
        for (@NotNull final Shape content : this.contents) {
            final Number xMaximumCurrent = content.getImplicitXMaximum();
            if (xMaximumCurrent.isGreaterThan(xMaximum)) {
                xMaximum = xMaximumCurrent;
            }
        }
        if (xMaximum.isEqualTo(DrawlNumber.valueOf(Double.MIN_VALUE))) {
            xMaximum = DrawlNumber.ZERO;
        }
        return xMaximum;
    }

    /**
     * Get the minimum x-coordinate of this drawing in implicit terms.
     * <p>
     * The minimum x-coordinate is the leftmost edge of the drawing.
     */
    @NotNull
    private Number getImplicitXMinimum() {
        Number xMinimum = DrawlNumber.valueOf(Double.MAX_VALUE);
        for (@NotNull final Shape content : this.contents) {
            final Number xMinimumCurrent = content.getImplicitXMinimum();
            if (xMinimumCurrent.isLessThan(xMinimum)) {
                xMinimum = xMinimumCurrent;
            }
        }
        if (xMinimum.isEqualTo(DrawlNumber.valueOf(Double.MAX_VALUE))) {
            xMinimum = DrawlNumber.ZERO;
        }
        return xMinimum;
    }

    /**
     * Get the maximum y-coordinate of this drawing in implicit terms.
     * <p>
     * The maximum y-coordinate is the top edge of the drawing.
     */
    @NotNull
    private Number getImplicitYMaximum() {
        Number yMaximum = DrawlNumber.valueOf(Double.MIN_VALUE);
        for (@NotNull final Shape content : this.contents) {
            final Number yMaximumCurrent = content.getImplicitYPositionTop();
            if (yMaximumCurrent.isGreaterThan(yMaximum)) {
                yMaximum = yMaximumCurrent;
            }
        }
        if (yMaximum.isEqualTo(DrawlNumber.valueOf(Double.MIN_VALUE))) {
            yMaximum = DrawlNumber.ZERO;
        }
        return yMaximum;
    }

    /**
     * Get the minimum y-coordinate of this drawing in implicit terms.
     * <p>
     * The minimum y-coordinate is the bottom edge of the drawing.
     */
    @NotNull
    private Number getImplicitYMinimum() {
        Number yMinimum = DrawlNumber.valueOf(Double.MAX_VALUE);
        for (@NotNull final Shape content : this.contents) {
            final Number yMinimumCurrent = content.getImplicitYPositionBottom();
            if (yMinimumCurrent.isLessThan(yMinimum)) {
                yMinimum = yMinimumCurrent;
            }
        }
        if (yMinimum.isEqualTo(DrawlNumber.valueOf(Double.MAX_VALUE))) {
            yMinimum = DrawlNumber.ZERO;
        }
        return yMinimum;
    }

    /**
     * Get the SVG for this drawing.
     *
     * @param drawingWidth  The desired width of the output; the type is Integer to allow for common use cases.
     * @param drawingHeight The desired height of the output; the type is Integer to allow for common use cases.
     * @return A string of valid SVG that depicts the drawing within the bounds of drawingWidth and drawingHeight.
     */
    public final @NotNull String getSVG(@NotNull final Integer drawingWidth, @NotNull final Integer drawingHeight) {
        this.setExplicitDimensions(drawingWidth.floatValue(), drawingHeight.floatValue());
        return this.getSVG();
    }

    /**
     * Get the SVG for this Drawing
     * <p>
     * Assumes that the explicit width and height have been set
     *
     * @return A string of valid SVG that depicts the drawing within the bounds of the explicit width and height
     */
    public final @NotNull String getSVG() {

        @NotNull final StringBuilder svgBuilder = new StringBuilder("<?xml version=\"1.0\" standalone=\"no\"?>");
        svgBuilder.append("<svg xmlns=\"http://www.w3.org/2000/svg\"");

        @Nullable final Number bdWidth = this.getExplicitWidth();
        if (bdWidth != null) {
            svgBuilder.append(" width=\"");
            @NotNull final String width = bdWidth.toSVG();
            svgBuilder.append(width);
            svgBuilder.append("\"");
        }

        @Nullable final Number bdHeight = this.getExplicitHeight();
        if (bdHeight != null) {
            svgBuilder.append(" height=\"");
            @NotNull final String height = bdHeight.toSVG();
            svgBuilder.append(height);
            svgBuilder.append("\"");
        }

        svgBuilder.append(">");

        for (@NotNull final Shape content : this.contents) {
            svgBuilder.append(content.getSVG());
        }

        svgBuilder.append("</svg>");
        return svgBuilder.toString();
    }

    /**
     * Indicate whether the explicit dimensions of this Drawing have been set.
     *
     * @return <code>TRUE</code> if the explicit dimensions of this Drawing have been set, <code>FALSE</code> otherwise.
     */
    private boolean isExplicitSet() {
        if ((this.explicitWidth != null) || (this.explicitHeight != null)) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    /**
     * Gets the number of items in this Drawing
     *
     * @return the number of items in this Drawing
     */
    public final Integer getLength() {
        return this.contents.size();
    }

    /**
     * Sets the explicit dimensions of this Drawing.
     * <p>
     * It is preferable to do it this way rather than setting width and height separately.
     *
     * @param explicitWidthOfDrawingFloat  The explicit width of the Drawing.
     * @param explicitHeightOfDrawingFloat The explicit height of the Drawing.
     */
    public final void setExplicitDimensions(final Float explicitWidthOfDrawingFloat, final Float explicitHeightOfDrawingFloat) {
        Drawing.logger.atFine().log("Entering setExplicitDimensions");
        // Calculate implicit aspect ratio
        final Number implicitHeightOfContents = this.getImplicitHeightOfContents();
        final Number implicitWidthOfContents = this.getImplicitWidthOfContents();
        Number implicitAspectRatio = DrawlNumber.ZERO;
        if (implicitHeightOfContents.isGreaterThan(DrawlNumber.ZERO)) {
            implicitAspectRatio = implicitWidthOfContents.divide(implicitHeightOfContents,
                    DrawlNumber.mcOperations);
        }

        // Calculate explicit aspect ratio
        this.setExplicitHeightInternal(DrawlNumber.valueOf(explicitWidthOfDrawingFloat));
        this.setExplicitWidthInternal(DrawlNumber.valueOf(explicitWidthOfDrawingFloat));
        assert this.getExplicitWidth() != null;
        assert this.getExplicitHeight() != null;
        final Number explicitAspectRatio = this.getExplicitWidth().divide(this.getExplicitHeight(),
                DrawlNumber.mcOperations);
        Drawing.logger.atFine().log("Explicit aspect ratio: " + explicitAspectRatio);

        if (FALSE) {
            if (implicitAspectRatio.isGreaterThan(explicitAspectRatio)) {
                // The implicit aspect ratio is greater than the explicit aspect ratio.
                // Therefore, we are constrained by width.
                // Adjust the height to match.
                final Number adjustedHeight = this.getExplicitWidth().divide(explicitAspectRatio,
                        DrawlNumber.mcOperations);
                this.setExplicitHeight(adjustedHeight);
                this.setExplicitWidth(DrawlNumber.valueOf(explicitWidthOfDrawingFloat));
            } else if (implicitAspectRatio.isLessThan(explicitAspectRatio)) {
                // The implicit aspect ratio is less than or equal to the explicit aspect ratio.
                // Therefore, we are constrained by height.
                // Adjust the width to match.
                final Number adjustedWidth = this.getExplicitHeight().multiply(explicitAspectRatio,
                        DrawlNumber.mcOperations);
                this.setExplicitHeight(DrawlNumber.valueOf(explicitHeightOfDrawingFloat));
                this.setExplicitWidth(adjustedWidth);
            }
        } else {
            // The two aspect ratios are equal, so no adjustment is necessary
            this.setExplicitWidth(DrawlNumber.valueOf(explicitWidthOfDrawingFloat));
            this.setExplicitHeight(DrawlNumber.valueOf(explicitHeightOfDrawingFloat));

        }

    }

    /**
     * Set the explicit dimensions of this Drawing.
     * <p>
     * It is preferable to do it this way rather than setting the dimensions separately.
     * <p>
     * This convenience method takes Integer parameters to allow for common use cases.
     *
     * @param explicitWidthOfDrawing  The explicit width of the Drawing.
     * @param explicitHeightOfDrawing The explicit height of the Drawing.
     */
    public final void setExplicitDimensions(@NotNull final Integer explicitWidthOfDrawing, @NotNull final Integer explicitHeightOfDrawing) {
        this.setExplicitDimensions(explicitWidthOfDrawing.floatValue(), explicitHeightOfDrawing.floatValue());
    }

    /**
     * Sets the explicit height of this Drawing. Use this to allow logging changes to internal height.
     *
     * @param drawingExplicitHeight the new explicit height of the Drawing.
     */
    private void setExplicitHeightInternal(@NotNull final Number drawingExplicitHeight) {
        this.explicitHeight = drawingExplicitHeight;
    }

    /**
     * Set the explicit width of this Drawing. Use this to allow logging changes to internal height.
     *
     * @param drawingExplicitWidth the new explicit width of the Drawing.
     */
    private void setExplicitWidthInternal(@NotNull final Number drawingExplicitWidth) {
        this.explicitWidth = drawingExplicitWidth;
    }

    /**
     * Update the width and x-coordinate, height and y-coordinate of a single shape
     * <p>
     * Called by setExplicitWidth()
     */
    private void updateShape(@NotNull final Shape shape) {
        Drawing.logger.atFine().log("Updating shape");
        this.updateExplicitHeightOfShape(shape);
        this.updateExplicitWidthOfShape(shape);
        this.updateExplicitYPositionOfShape(shape);
        this.updateExplicitXPositionOfShape(shape);
    }


    private void updateExplicitHeightOfShape(@NotNull final Shape shape) {
        // Update explicit height of shape
        @Nullable final Number implicitHeightOfShape = shape.getImplicitHeight();
        assert implicitHeightOfShape != null;
        final Number explicitHeightOfShape = implicitHeightOfShape.multiply(this.getExplicitToImplicitRatio(),
                DrawlNumber.mcOperations);
        shape.setExplicitHeight(explicitHeightOfShape);
    }

    private void updateExplicitWidthOfShape(@NotNull final Shape shape) {
        // Update explicit width of shape
        Drawing.logger.atFine().log("Updating explicit width of shape");
        final Number implicitWidthOfShape = shape.getImplicitWidth();
        final Number explicitWidthOfShape = implicitWidthOfShape.multiply(this.getExplicitToImplicitRatio(),
                DrawlNumber.mcOperations);
        shape.setExplicitWidth(explicitWidthOfShape);
    }

    private void updateExplicitXPositionOfShape(@NotNull final Shape shape) {
        // Update the explicit x position of the Shape
        Drawing.logger.atFine().log("Updating explicit x position");
        final Number implicitXPositionOfShape = shape.getImplicitXPositionCenter();
        // The fudge factor is to shift the diagram right so that all x coordinates are positive in explicit coordinate
        // space
        @NotNull final Number fudgeFactorX = this.getImplicitXMinimum();
        final Number fudgedImplicitXPositionOfShape = implicitXPositionOfShape.subtract(fudgeFactorX,
                DrawlNumber.mcOperations);
        final Number explicitXPositionOfShape = fudgedImplicitXPositionOfShape.multiply(this.getExplicitToImplicitRatio(),
                DrawlNumber.mcOperations);
        shape.setExplicitXPositionCenter(explicitXPositionOfShape);
        final Number implicitWidthOfContents = this.getImplicitWidth();
        final Number explicitWidthOfContents = implicitWidthOfContents.multiply(this.getExplicitToImplicitRatio(),
                DrawlNumber.mcOperations);
        if (this.isExplicitSet()) {
            @Nullable final Number explicitWidthOfDrawing = this.getExplicitWidth();
            assert explicitWidthOfDrawing != null;
            final Number explicitHorizontalWhitespace = explicitWidthOfDrawing.subtract(explicitWidthOfContents,
                    DrawlNumber.mcOperations);
            final Number explicitHorizontalWhitespaceLeft = explicitHorizontalWhitespace.divide(DrawlNumber.TWO,
                    DrawlNumber.mcOperations);
            final Number finalExplicitXPositionOfShape = explicitXPositionOfShape.add(explicitHorizontalWhitespaceLeft,
                    DrawlNumber.mcOperations);
            shape.setExplicitXPositionCenter(finalExplicitXPositionOfShape);
        }
    }

    private void updateExplicitYPositionOfShape(@NotNull final Shape shape) {
        // Update the explicit y position of the Shape
        final Number implicitYPositionOfShape = shape.getImplicitYPositionCenter();
        // The fudge factor is to shift the diagram down so that all y coordinates are positive in explicit coordinate space
        @NotNull final Number fudgeFactorY = this.getImplicitYMaximum();
        Number fudgedImplicitYPositionOfShape = implicitYPositionOfShape.subtract(fudgeFactorY);
        fudgedImplicitYPositionOfShape = fudgedImplicitYPositionOfShape.negate();
        final Number explicitToImplicitRatio = this.getExplicitToImplicitRatio();
        final Number explicitYPositionOfShape = fudgedImplicitYPositionOfShape.multiply(explicitToImplicitRatio,
                DrawlNumber.mcOperations);
        final Number implicitHeightOfContents = this.getImplicitHeight();
        final Number explicitHeightOfContents = implicitHeightOfContents.multiply(explicitToImplicitRatio,
                DrawlNumber.mcOperations);
        if (this.isExplicitSet()) {
            @Nullable final Number explicitHeightOfDrawing = this.getExplicitHeight();
            assert explicitHeightOfDrawing != null;
            final Number explicitVerticalWhitespace = explicitHeightOfDrawing.subtract(explicitHeightOfContents,
                    DrawlNumber.mcOperations);
            final Number explicitVerticalWhitespaceBelow = explicitVerticalWhitespace.divide(DrawlNumber.TWO,
                    DrawlNumber.mcOperations);
            final Number finalExplicitYPositionOfShape = explicitYPositionOfShape.add(explicitVerticalWhitespaceBelow,
                    DrawlNumber.mcOperations);
            shape.setExplicitYPositionCenter(finalExplicitYPositionOfShape);
        }
    }

    /**
     * Write SVG representing this drawing to a file.
     *
     * @param filename The name of the file to which to write.
     * @throws IOException If there is a problem writing to the file.
     */
    public final void writeToFile(@NotNull final String filename, @NotNull final Integer width, @NotNull final Integer height) throws IOException {
        @NotNull final String svg = this.getSVG(width, height);
        try (@NotNull final BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(svg);
        }
    }
}
