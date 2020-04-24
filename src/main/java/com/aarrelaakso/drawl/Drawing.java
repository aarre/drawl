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
import java.math.BigDecimal;
import java.util.HashSet;

public class Drawing {

    private HashSet<Shape> contents;
    private BigDecimal explicitHeight;
    private BigDecimal explicitWidth;
    private static final FluentLogger logger;

    static {
        logger = FluentLogger.forEnclosingClass();

    }

    public Drawing() {
        contents = new HashSet<>();
         LoggingConfig loggingConfig = new LoggingConfig();
    }

    /**
     * Add a shape to this drawing
     *
     * @param shape The shape to add
     */
    public void add(Shape shape) {
        contents.add(shape);
    }

    /**
     * Get the explicit height of this Drawing.
     *
     * @return The explicit height of this Drawing. Can be null if the drawing has length zero or the explicit
     * dimensions have not been set.
     */
    @Nullable
    public BigDecimal getExplicitHeight() {
            return this.explicitHeight;
    }

    /**
     * Get the ratio of explicit height to implicit height in this diagram.
     *
     * @return The ratio of the explicit height of this drawing to the implicit height of its contents
     */
    private BigDecimal getExplicitHeightPerImplicitHeight() {
        logger.atFine().log("Entering:getExplicitHeightPerImplicitHeight");
        BigDecimal implicitHeightOfContents = this.getImplicitHeightOfContents();
        BigDecimal explicitHeightPerImplicitHeight;
        if (implicitHeightOfContents.compareTo(BigDecimal.ZERO) != 0) {
            if (this.explicitHeight == null) {
                // If the explicit height is null, calculate using the explicit width
                explicitHeightPerImplicitHeight = this.explicitWidth.divide(this.getImplicitWidthOfContents(), BigDecimalMath.mathContext);
            } else {
                // The explicit height is not null, so calculate using the explicit height
                explicitHeightPerImplicitHeight = this.explicitHeight.divide(implicitHeightOfContents, BigDecimalMath.mathContext);
            }
        } else {
            // The implicit height of the contents is 0, so the ratio is 0
            explicitHeightPerImplicitHeight = BigDecimal.ZERO;
        }
        return explicitHeightPerImplicitHeight;
    }

    /**
     * Get the ratio of explicit measures to implicit measures for this drawing as a whole.
     *
     * This method is different from getExplicitHeightPerImplicitHeight() and getExplicitWidthPerImplicitWidth()
     * in that it takes into account both height constraints and width constraints.
     *
     * @return The ratio of explicit measures to implicit measures for this diagram as a whole.
     */
    private BigDecimal getExplicitToImplicitRatio() {
        BigDecimal explicitHeightPerImplicitHeight = this.getExplicitHeightPerImplicitHeight();
        BigDecimal explicitWidthPerImplicitWidth = this.getExplicitWidthPerImplicitWidth();
        if (explicitHeightPerImplicitHeight.compareTo(explicitWidthPerImplicitWidth) <= 0) {
            return explicitHeightPerImplicitHeight;
        } else {
            return explicitWidthPerImplicitWidth;
        }
    }

    /**
     * Get the explicit width of this Drawing.
     *
     * @return The explicit width of this Drawing. Can be null if the drawing has length zero or the explicit
     * dimensions have not been set.
     */
    @Nullable
    public BigDecimal getExplicitWidth() {
            return this.explicitWidth;
    }

    /**
     * Get the ratio of the explicit width of this drawing to the implicit width of its contents.
     *
     * @return The ratio of the explicit width of this drawing to the implicit width of its contents.
     */
    @NotNull
    private BigDecimal getExplicitWidthPerImplicitWidth() {
        BigDecimal implicitWidthOfContents = this.getImplicitWidthOfContents();
        assert implicitWidthOfContents != null :
                "The implicit width of the contents of a drawing cannot be null.";
        assert implicitWidthOfContents.compareTo(BigDecimal.ZERO) >= 0 :
                "The implicit width of the contents of a drawing must be positive (or zero).";
        BigDecimal explicitWidthPerImplicitWidth;
        if (implicitWidthOfContents.compareTo(BigDecimal.ZERO) != 0) {
            if (this.explicitWidth == null) {
                // If the explicit width is null, calculate using the explicit height
                explicitWidthPerImplicitWidth = this.explicitHeight.divide(this.getImplicitHeightOfContents(),
                        BigDecimalMath.mathContext);
            } else {
                // The explicit width is not null, so calculate using the explicit width
                explicitWidthPerImplicitWidth = this.explicitWidth.divide(implicitWidthOfContents,
                        BigDecimalMath.mathContext);
            }
        } else {
            // The implicit width of the contents is 0, so the ratio is 0
            explicitWidthPerImplicitWidth = BigDecimal.ZERO;
        }
        assert explicitWidthPerImplicitWidth != null :
                "The ratio of the explicit width of a drawing to its explicit width cannot be null.";
        return explicitWidthPerImplicitWidth;
    }

    /**
     * Get the explicit width per object in this Drawing.
     *
     * @return the explicit width per object in this Drawing.
     *
     * @todo Make this method private and adjust unit tests accordingly
     */
    public BigDecimal getExplicitWidthPerObject() {
        BigDecimal implicitWidth = this.getImplicitWidthOfContents();
        if ((implicitWidth == null) || implicitWidth.equals(BigDecimal.ZERO)) {
            // The drawing has no contents
            return BigDecimal.ZERO;
        } else {
            return this.getExplicitWidth().divide(implicitWidth, BigDecimalMath.mathContext);
        }
    }

    /**
     * Get the implicit total height of all contents in the drawing.
     *
     * Provides public access to the implicit height of the contents.
     *
     * @todo Remove this function and factor out of test cases.
     */
    public BigDecimal getImplicitHeight() {
        return this.getImplicitHeightOfContents();
    }

    /**
     * Get the implicit total height of all contents in the drawing.
     *
     * @return the implicit total height of all contents in this drawing
     */
    private BigDecimal getImplicitHeightOfContents() {
        return this.getImplicitYMaximum().subtract(this.getImplicitYMinimum());
    }

    /**
     * Get the implicit total width of all contents in this Drawing.
     *
     * Provides public access to the implicit width of the contents for testing.
     *
     * @todo Remove this function and factor out of test cases.
     *
     */
    public BigDecimal getImplicitWidth() {
        return this.getImplicitWidthOfContents();
    }

    /**
     * Get the implicit total width of all contents in the drawing
     *
     * @return the implicit total width of all contents in this drawing
     */
    private BigDecimal getImplicitWidthOfContents() {
        assert this.getImplicitXMaximum().compareTo(this.getImplicitXMinimum()) >= 0 :
                "The implicit x-coordinate maximum must be greater than or equal to the implicit x-coordinate minimum.";
        return this.getImplicitXMaximum().subtract(this.getImplicitXMinimum());
    }

    /**
     * Get the maximum x-coordinate of this drawing in implicit terms.
     * <p>
     * The maximum x-coordinate is the right edge of the drawing.
     */
    @NotNull
    private BigDecimal getImplicitXMaximum() {
        BigDecimal xMaximum = BigDecimal.ZERO;
        for (Shape content : this.contents) {
            BigDecimal xMaximumCurrent = content.getImplicitXMaximum();
            if (xMaximumCurrent.compareTo(xMaximum) > 0) {
                xMaximum = xMaximumCurrent;
            }
        }
        return xMaximum;
    }

    /**
     * Get the minimum x-coordinate of this drawing in implicit terms.
     * <p>
     * The minimum x-coordinate is the leftmost edge of the drawing.
     */
    @NotNull
    private BigDecimal getImplicitXMinimum() {
        BigDecimal xMinimum = BigDecimal.ZERO;
        for (Shape content : this.contents) {
            BigDecimal xMinimumCurrent = content.getImplicitXMinimum();
            if (xMinimumCurrent.compareTo(xMinimum) < 0) {
                xMinimum = xMinimumCurrent;
            }
        }
        return xMinimum;
    }

    /**
     * Get the maximum y-coordinate of this drawing in implicit terms.
     * <p>
     * The maximum y-coordinate is the top edge of the drawing.
     */
    @NotNull
    private BigDecimal getImplicitYMaximum() {
        BigDecimal yMaximum = BigDecimal.valueOf(Double.MIN_VALUE);
        for (Shape content : this.contents) {
            BigDecimal yMaximumCurrent = content.getImplicitYMaximum();
            if (yMaximumCurrent.compareTo(yMaximum) > 0) {
                yMaximum = yMaximumCurrent;
            }
        }
        return yMaximum;
    }

    /**
     * Get the minimum y-coordinate of this drawing in implicit terms.
     * <p>
     * The minimum y-coordinate is the leftmost edge of the drawing.
     */
    @NotNull
    private BigDecimal getImplicitYMinimum() {
        BigDecimal yMinimum = BigDecimal.valueOf(Double.MAX_VALUE);
        for (Shape content : this.contents) {
            BigDecimal yMinimumCurrent = content.getImplicitXMinimum();
            if (yMinimumCurrent.compareTo(yMinimum) < 0) {
                yMinimum = yMinimumCurrent;
            }
        }
        return yMinimum;
    }

    /**
     * Get the SVG for this Drawing.
     *
     * @param drawingWidth  The desired width of the output;
     *                      the type is Float to match the SVG spec for numeric precision.
     * @param drawingHeight The desired height of the output;
     *                      the type is Float to match the SVG spec for numeric precision.
     * @return A string of valid SVG that depicts the drawing within the bounds of drawingWidth and drawingHeight.
     */
    public String getSVG(Float drawingWidth, Float drawingHeight) {
        this.setExplicitDimensions(drawingWidth, drawingHeight);
        return this.getSVG();
    }

    /**
     * Get the SVG for this drawing.
     *
     * @param drawingWidth  The desired width of the output; the type is Integer to allow for common use cases.
     * @param drawingHeight The desired height of the output; the type is Integer to allow for common use cases.
     * @return A string of valid SVG that depicts the drawing within the bounds of drawingWidth and drawingHeight.
     */
    public String getSVG(Integer drawingWidth, Integer drawingHeight) {
        this.setExplicitDimensions(drawingWidth.floatValue(), drawingHeight.floatValue());
        return this.getSVG();
    }

    /**
     * Get the SVG for this Drawing
     * <p>
     * Assumes that the explicit width and height have been set
     *
     * @return A string of valid SVG that depicts the drawing within the bounds of the explicit width and height
     * @todo Make this method protected and adjust unit tests accordingly.
     */
    public @NotNull String getSVG() {

        StringBuilder svgBuilder = new StringBuilder("<?xml version=\"1.0\" standalone=\"no\"?>");
        svgBuilder.append("<svg xmlns=\"http://www.w3.org/2000/svg\"");

        BigDecimal bdWidth = this.getExplicitWidth();
        if (bdWidth != null) {
            svgBuilder.append(" width=\"");
            String width = SVG.toString(this.getExplicitWidth());
            svgBuilder.append(width);
            svgBuilder.append("\"");
        }

        BigDecimal bdHeight = this.getExplicitHeight();
        if (bdHeight != null) {
            svgBuilder.append(" height=\"");
            String height = SVG.toString(this.getExplicitHeight());
            svgBuilder.append(height);
            svgBuilder.append("\"");
        }

        svgBuilder.append(">");

        if (this.contents != null) {
            for (Shape content : this.contents) {
                svgBuilder.append(content.getSVG());
            }
        }
        svgBuilder.append("</svg>");
        String svg = svgBuilder.toString();
        return (svg);
    }

    /**
     * Get the number of items in this Drawing
     *
     * @return the number of items in this Drawing
     */
    public Integer length() {
        return Integer.valueOf(contents.size());
    }

    /**
     * Set the explicit dimensions of this Drawing.
     * <p>
     * It is preferable to do it this way rather than setting width and height separately.
     *
     * @param explicitWidthOfDrawingFloat  The explicit width of the Drawing.
     * @param explicitHeightOfDrawingFloat The explicit height of the Drawing.
     */
    public void setExplicitDimensions(Float explicitWidthOfDrawingFloat, Float explicitHeightOfDrawingFloat) {

        this.setExplicitHeight(BigDecimal.valueOf(explicitHeightOfDrawingFloat));
        this.setExplicitWidth(BigDecimal.valueOf(explicitWidthOfDrawingFloat));

        if (this.length() > 0) {

            BigDecimal implicitHeightOfContents = this.getImplicitHeightOfContents();
            assert implicitHeightOfContents.compareTo(BigDecimal.ZERO) > 0 :
                    "Implicit height of contents cannot be zero or negative.";

            BigDecimal implicitWidthOfContents = this.getImplicitWidthOfContents();
            assert implicitWidthOfContents.compareTo(BigDecimal.ZERO) > 0 :
                    "Implicit width of contents cannot be zero or negative.";

            BigDecimal implicitAspectRatio = implicitWidthOfContents.divide(implicitHeightOfContents,
                    BigDecimalMath.mathContext);
            BigDecimal explicitAspectRatio = this.getExplicitWidth().divide(this.getExplicitHeight(),
                    BigDecimalMath.mathContext);

            if (implicitAspectRatio.compareTo(explicitAspectRatio) > 0) {
                // The implicit aspect ratio is greater than the explicit aspect ratio
                // Therefore, we are constrained by width
                BigDecimal adjustedHeight = this.getExplicitWidth().divide(explicitAspectRatio,
                        BigDecimalMath.mathContext);
                this.setExplicitHeight(adjustedHeight);
                this.setExplicitWidth(this.getExplicitWidth());

            } else {
                // The implicit aspect ratio is less than or equal to the explicit aspect ratio
                // Therefore, we are constrained by height
                BigDecimal adjustedWidth = this.getExplicitHeight().multiply(explicitAspectRatio);
                this.setExplicitWidth(adjustedWidth);
                this.setExplicitHeight(this.getExplicitHeight());
            }
        } else {
            // The drawing has no contents, so just set its dimensions
            this.setExplicitWidth(this.getExplicitWidth());
            this.setExplicitHeight(this.getExplicitHeight());
        }
    }

    /**
     * Set the explicit dimensions of this Drawing.
     *
     * It is preferable to do it this way rather than setting the dimensions separately.
     *
     * This convenience method takes Integer parameters to allow for common use cases.
     *
     * @param explicitWidthOfDrawing The explicit width of the Drawing.
     * @param explicitHeightOfDrawing The explicit height of the Drawing.
     */
    public void setExplicitDimensions(Integer explicitWidthOfDrawing, Integer explicitHeightOfDrawing) {
        this.setExplicitDimensions(explicitWidthOfDrawing.floatValue(), explicitHeightOfDrawing.floatValue());
    }

    /**
     * Set the explicit height of this Drawing.
     *
     * This method also calculates the explicit heights and y positions of all the drawing's contents.
     * If the explicit width of this Drawing is not known, then this method also sets the explicit width
     * to match the aspect ratio of the contents.
     *
     * @param drawingExplicitHeight The explicit height of the drawing.
     */
    private void setExplicitHeight(@NotNull BigDecimal drawingExplicitHeight) {
        this.explicitHeight = drawingExplicitHeight;
        if (this.getExplicitWidth() == null) {
            // If we don't have an explicit width, make the explicit width as wide as it needs to be to accommodate the contents
            this.explicitWidth = this.getImplicitWidthOfContents().multiply(this.getExplicitToImplicitRatio());
        }
        BigDecimal explicitToImplicitRatio = this.getExplicitToImplicitRatio();
        assert explicitToImplicitRatio != null : "The explicit to implicit ratio of a drawing cannot be null.";
        if (this.length() > 0) {
            BigDecimal explicitHeightOfShape;
            BigDecimal explicitWidthOfShape;
            BigDecimal implicitHeightOfShape;
            BigDecimal implicitWidthOfShape;
            // Set the heights and explicit y positions of all contents
            for (Shape shape : this.contents) {

                implicitHeightOfShape = shape.getImplicitHeight();
                assert implicitHeightOfShape != null : "The implicit height of a shape cannot be null.";
                explicitHeightOfShape = implicitHeightOfShape.multiply(this.getExplicitToImplicitRatio(),
                        BigDecimalMath.mathContext);
                shape.setExplicitHeight(explicitHeightOfShape);

                implicitWidthOfShape = shape.getImplicitWidth();
                explicitWidthOfShape = implicitWidthOfShape.multiply(this.getExplicitToImplicitRatio(),
                        BigDecimalMath.mathContext);
                assert explicitWidthOfShape != null :
                        "The explicit width of a shape should not be null after it has been set";
                shape.setExplicitWidth(explicitWidthOfShape);
                assert shape.getExplicitWidth() != null :
                        "The explicit width of a shape should not be null after it has been set";
                assert shape.getExplicitWidth().doubleValue() == explicitWidthOfShape.doubleValue() :
                        "A shape should not change width for no reason. Was " + explicitWidthOfShape.toPlainString() +
                        ", now " + shape.getExplicitWidth().toPlainString();

                BigDecimal implicitYPositionOfShape = shape.getImplicitYPosition();
                // The fudge factor is to shift the diagram down so that all y-coordinates are positive
                BigDecimal fudgeFactorY = this.getImplicitYMaximum();
                BigDecimal fudgedImplicitYPositionOfShape = implicitYPositionOfShape.subtract(fudgeFactorY);
                fudgedImplicitYPositionOfShape = fudgedImplicitYPositionOfShape.multiply(BigDecimal.valueOf(-1));
                assert fudgedImplicitYPositionOfShape.compareTo(BigDecimal.ZERO) >= 0 :
                        "All y-coordinates must be positive.";
                shape.setExplicitYPosition(fudgedImplicitYPositionOfShape.multiply(this.getExplicitToImplicitRatio(),
                        BigDecimalMath.mathContext));
                assert shape.getExplicitWidth().doubleValue() == explicitWidthOfShape.doubleValue() :
                        "A shape should not change width for no reason. Was " + explicitWidthOfShape.toPlainString() +
                                ", now " + shape.getExplicitWidth().toPlainString();

                BigDecimal implicitXPositionOfShape = shape.getImplicitXPosition();
                BigDecimal fudgeFactorX = this.getImplicitXMinimum();
                BigDecimal fudgedImplicitXPositionOfShape = implicitXPositionOfShape.subtract(fudgeFactorX);
                assert fudgedImplicitXPositionOfShape.compareTo(BigDecimal.ZERO) >= 0 :
                        "All x-coordinates must be positive.";
                shape.setExplicitXPosition(fudgedImplicitXPositionOfShape.multiply(this.getExplicitToImplicitRatio(), BigDecimalMath.mathContext));
                assert shape.getExplicitWidth().doubleValue() == explicitWidthOfShape.doubleValue() :
                        "A shape should not change width for no reason. Was " + explicitWidthOfShape.toPlainString() +
                                ", now " + shape.getExplicitWidth().toPlainString();
            }
        }
    }

    /**
     * Set the explicit height of this Drawing
     *
     * @param height the explicit height of the Drawing; it is a Float to match
     *               the precision allowed by the SVG spec.
     */
    public void setExplicitHeight(Float height) {
        this.setExplicitHeight(BigDecimal.valueOf(height));
    }

    /**
     * Set the explicit height of this Drawing.
     *
     * @param height The explicit height of the Drawing; it is an Integer to allow for common use cases.
     */
    public void setExplicitHeight(Integer height) {
        this.setExplicitHeight(BigDecimal.valueOf(height));
    }

    /**
     * Set the explicit width of this drawing.
     * <p>
     * This method also calculates the explicit widths and x positions of all the drawing's contents.
     *
     * @param drawingExplicitWidth The explicit width of the drawing.
     */
    private void setExplicitWidth(@NotNull BigDecimal drawingExplicitWidth) {
        assert drawingExplicitWidth != null : "The implicit width of a drawing cannot be null.";
        this.explicitWidth = drawingExplicitWidth;
        if (this.length() > 0) {
            BigDecimal explicitWidthPerImplicitWidth = this.getExplicitToImplicitRatio();
            if (this.getExplicitHeight() == null) {
                this.explicitHeight = this.getImplicitHeightOfContents().multiply(this.getExplicitToImplicitRatio());
            }
            BigDecimal implicitWidthOfShape;
            BigDecimal explicitWidthOfShape;
            // Set the explicit widths and x positions of all contents
            for (Shape shape : this.contents) {
                implicitWidthOfShape = shape.getImplicitWidth();
                explicitWidthOfShape = implicitWidthOfShape.multiply(explicitWidthPerImplicitWidth, BigDecimalMath.mathContext);
                shape.setExplicitWidth(explicitWidthOfShape);
                BigDecimal implicitXPositionOfShape = shape.getImplicitXPosition();
                // The fudge factor is to shift the diagram right so that all x-coordinates are positive
                BigDecimal fudgeFactor = BigDecimal.ZERO.subtract(this.getImplicitXMinimum());
                BigDecimal fudgedImplicitXPositionOfShape = implicitXPositionOfShape.add(fudgeFactor);
                shape.setExplicitXPosition(fudgedImplicitXPositionOfShape.multiply(explicitWidthPerImplicitWidth, BigDecimalMath.mathContext));
            }
        }
    }

    /**
     * Set the explicit width of this Drawing.
     *
     * @param width the explicit width of the Drawing; it is a Float
     *              to match the precision allowed by the SVG spec.
     */
    public void setExplicitWidth(Float width) {
        this.setExplicitWidth(BigDecimal.valueOf(width));

    }

    /**
     * Set the explicit width of this Drawing.
     *
     * @param width The explicit width of the Drawing; it is an Integer to allow for common use cases.
     */
    public void setExplicitWidth(Integer width) {
        this.setExplicitWidth(BigDecimal.valueOf(width));
    }

    /**
     * Write SVG representing this drawing to a file.
     *
     * @param filename The name of the file to which to write.
     * @throws IOException If there is a problem writing to the file.
     */
    public void writeToFile(String filename) throws IOException {
        String str = this.getSVG();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(str);
        } catch (IOException e) {
            throw (e);
        }
    }
}
