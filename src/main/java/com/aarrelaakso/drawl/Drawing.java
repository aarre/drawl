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

public class Drawing
{

    private static final FluentLogger logger;

    static
    {
        logger = FluentLogger.forEnclosingClass();

    }

    private final HashSet<Shape> contents;
    private DrawlNumber explicitHeight;
    private DrawlNumber explicitWidth;

    public Drawing()
    {
        contents = new HashSet<>();
        LoggingConfig loggingConfig = new LoggingConfig();
    }

    /**
     * Add a shape to this drawing
     *
     * @param shape The shape to add
     */
    public void add(@NotNull Shape shape)
    {
        if (this.isExplicitSet()) {
            throw new UnsupportedOperationException("Cannot add shapes after setting a drawing's explicit dimensions");
        }
        contents.add(shape);
        this.updateShape(shape);
    }

    /**
     * Get the explicit height of this Drawing.
     *
     * @return The explicit height of this Drawing, or null if the drawing has length zero or the explicit
     * dimensions have not been set.
     */
    @Nullable
    public DrawlNumber getExplicitHeight()
    {
        return this.explicitHeight;
    }

    /**
     * Get the ratio of the explicit height of this Drawing to the implicit height of its contents.
     *
     * @return The ratio of the explicit height of this Drawing to the implicit height of its contents
     */
    private DrawlNumber getExplicitHeightPerImplicitHeight()
    {
        DrawlNumber implicitHeightOfContents = this.getImplicitHeightOfContents();
        DrawlNumber explicitHeightPerImplicitHeight;
        if (implicitHeightOfContents.isNotEqualTo(DrawlNumber.ZERO))
        {
            if (this.getExplicitHeight() == null)
            {
                // If the explicit height has no value, try to calculate using the explicit width
                if (this.getExplicitWidth() == null)
                {
                    // If the explicit width also has no value, the ratio is 0
                    explicitHeightPerImplicitHeight = DrawlNumber.ZERO;
                }
                else
                {
                    // The explicit width exceeds 0, so use it to calculate the ratio
                    explicitHeightPerImplicitHeight = this.getExplicitWidth().divide(this.getImplicitWidthOfContents(),
                            DrawlNumber.mcOperations);
                }
            }
            else
            {
                // The explicit height has a value, so calculate using the explicit height
                explicitHeightPerImplicitHeight = this.getExplicitHeight().divide(implicitHeightOfContents,
                        DrawlNumber.mcOperations);
            }
        }
        else
        {
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
    public DrawlNumber getExplicitToImplicitRatio()
    {
        DrawlNumber explicitHeightPerImplicitHeight = this.getExplicitHeightPerImplicitHeight();
        DrawlNumber explicitWidthPerImplicitWidth = this.getExplicitWidthPerImplicitWidth();
        if (explicitHeightPerImplicitHeight.isLessThanOrEqualTo(explicitWidthPerImplicitWidth))
        {
            return explicitHeightPerImplicitHeight;
        }
        else
        {
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
    public DrawlNumber getExplicitWidth()
    {
        return this.explicitWidth;
    }

    /**
     * Get the ratio of the explicit width of this drawing to the implicit width of its contents.
     *
     * @return The ratio of the explicit width of this drawing to the implicit width of its contents.
     */
    // TODO [Issue No. 1] Make this method private and factor out of unit tests.
    @NotNull
    public DrawlNumber getExplicitWidthPerImplicitWidth()
    {
        DrawlNumber implicitWidthOfContents = this.getImplicitWidthOfContents();
        assert implicitWidthOfContents != null :
                "The implicit width of the contents of a drawing cannot be null.";
        assert implicitWidthOfContents.isGreaterThanOrEqualTo(DrawlNumber.ZERO) :
                "The implicit width of the contents of a drawing must be positive (or zero).";
        DrawlNumber explicitWidthPerImplicitWidth;
        if (implicitWidthOfContents.isNotEqualTo(DrawlNumber.ZERO))
        {
            if (this.getExplicitWidth() == null)
            {
                // If the explicit width is null, try to use it to calculate using the explicit height
                if (this.getExplicitHeight() == null)
                {
                    // The explicit height is also null, so the ratio is 0
                    explicitWidthPerImplicitWidth = DrawlNumber.ZERO;
                }
                else
                {
                    // Use the explicit height to calculate the ratio
                    explicitWidthPerImplicitWidth = this.getExplicitHeight().divide(this.getImplicitHeightOfContents(),
                            DrawlNumber.mcOperations);
                }
            }
            else
            {
                // The explicit width is not null, so calculate using the explicit width
                explicitWidthPerImplicitWidth = this.getExplicitWidth().divide(implicitWidthOfContents,
                        DrawlNumber.mcOperations);
            }
        }
        else
        {
            // The implicit width of the contents is 0, so the ratio is 0
            explicitWidthPerImplicitWidth = DrawlNumber.ZERO;
        }
        assert explicitWidthPerImplicitWidth != null :
                "The ratio of the explicit width of a drawing to the implicit width of its contents cannot be null.";
        return explicitWidthPerImplicitWidth;
    }

    /**
     * Get the implicit total height of all contents in the drawing.
     * <p>
     * Provides public access to the implicit height of the contents.
     *
     */
    // TODO [Issue No. 1] Make this method private and factor out of unit tests.
    public DrawlNumber getImplicitHeight()
    {
        return this.getImplicitHeightOfContents();
    }

    /**
     * Get the implicit total height of all contents in the drawing.
     *
     * @return the implicit total height of all contents in this drawing
     */
    private DrawlNumber getImplicitHeightOfContents()
    {
        return this.getImplicitYMaximum().subtract(this.getImplicitYMinimum());
    }

    /**
     * Get the implicit total width of all contents in this Drawing.
     * <p>
     * Provides public access to the implicit width of the contents for testing.
     *
     */
    // TODO [Issue No. 1] Make this method private and factor out of unit tests.
    public DrawlNumber getImplicitWidth()
    {
        return this.getImplicitWidthOfContents();
    }

    /**
     * Get the implicit total width of all contents in the drawing
     *
     * @return the implicit total width of all contents in this drawing
     */
    private DrawlNumber getImplicitWidthOfContents()
    {
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
    private DrawlNumber getImplicitXMaximum()
    {
        DrawlNumber xMaximum = DrawlNumber.valueOf(Double.MIN_VALUE);
        for (Shape content : this.contents)
        {
            DrawlNumber xMaximumCurrent = content.getImplicitXMaximum();
            if (xMaximumCurrent.isGreaterThan(xMaximum))
            {
                xMaximum = xMaximumCurrent;
            }
        }
        if (xMaximum.isEqualTo(DrawlNumber.valueOf(Double.MIN_VALUE)))
        {
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
    private DrawlNumber getImplicitXMinimum()
    {
        DrawlNumber xMinimum = DrawlNumber.valueOf(Double.MAX_VALUE);
        for (Shape content : this.contents)
        {
            DrawlNumber xMinimumCurrent = content.getImplicitXMinimum();
            if (xMinimumCurrent.isLessThan(xMinimum))
            {
                xMinimum = xMinimumCurrent;
            }
        }
        if (xMinimum.isEqualTo(DrawlNumber.valueOf(Double.MAX_VALUE)))
        {
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
    private DrawlNumber getImplicitYMaximum()
    {
        DrawlNumber yMaximum = DrawlNumber.valueOf(Double.MIN_VALUE);
        for (Shape content : this.contents)
        {
            DrawlNumber yMaximumCurrent = content.getImplicitYPositionTop();
            if (yMaximumCurrent.isGreaterThan(yMaximum))
            {
                yMaximum = yMaximumCurrent;
            }
        }
        if (yMaximum.isEqualTo(DrawlNumber.valueOf(Double.MIN_VALUE)))
        {
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
    private DrawlNumber getImplicitYMinimum()
    {
        DrawlNumber yMinimum = DrawlNumber.valueOf(Double.MAX_VALUE);
        for (Shape content : this.contents)
        {
            DrawlNumber yMinimumCurrent = content.getImplicitYPositionBottom();
            if (yMinimumCurrent.isLessThan(yMinimum))
            {
                yMinimum = yMinimumCurrent;
            }
        }
        if (yMinimum.isEqualTo(DrawlNumber.valueOf(Double.MAX_VALUE)))
        {
            yMinimum = DrawlNumber.ZERO;
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
    public String getSVG(Float drawingWidth, Float drawingHeight)
    {
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
    public String getSVG(Integer drawingWidth, Integer drawingHeight)
    {
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
    public @NotNull String getSVG()
    {

        StringBuilder svgBuilder = new StringBuilder("<?xml version=\"1.0\" standalone=\"no\"?>");
        //noinspection SpellCheckingInspection
        svgBuilder.append("<svg xmlns=\"http://www.w3.org/2000/svg\"");

        DrawlNumber bdWidth = this.getExplicitWidth();
        if (bdWidth != null)
        {
            svgBuilder.append(" width=\"");
            String width = SVG.toString(this.getExplicitWidth());
            svgBuilder.append(width);
            svgBuilder.append("\"");
        }

        DrawlNumber bdHeight = this.getExplicitHeight();
        if (bdHeight != null)
        {
            svgBuilder.append(" height=\"");
            String height = SVG.toString(this.getExplicitHeight());
            svgBuilder.append(height);
            svgBuilder.append("\"");
        }

        svgBuilder.append(">");

        if (this.contents != null)
        {
            for (Shape content : this.contents)
            {
                svgBuilder.append(content.getSVG());
            }
        }
        svgBuilder.append("</svg>");
        return svgBuilder.toString();
    }

    /**
     * Indicate whether the explicit dimensions of this Drawing have been set.
     *
     * @return
     */
    private boolean isExplicitSet()
    {
        if ((this.explicitWidth != null) || (this.explicitHeight != null))
        {
            return TRUE;
        }
        else
        {
            return FALSE;
        }
    }

    /**
     * Gets the number of items in this Drawing
     *
     * @return the number of items in this Drawing
     */
    public Integer getLength()
    {
        return contents.size();
    }

    /**
     * Sets the explicit dimensions of this Drawing.
     * <p>
     * It is preferable to do it this way rather than setting width and height separately.
     *
     * @param explicitWidthOfDrawingFloat  The explicit width of the Drawing.
     * @param explicitHeightOfDrawingFloat The explicit height of the Drawing.
     */
    public void setExplicitDimensions(Float explicitWidthOfDrawingFloat, Float explicitHeightOfDrawingFloat)
    {
        logger.atFine().log("Entering setExplicitDimensions");
        // Calculate implicit aspect ratio
        DrawlNumber implicitHeightOfContents = this.getImplicitHeightOfContents();
        DrawlNumber implicitWidthOfContents = this.getImplicitWidthOfContents();
        DrawlNumber implicitAspectRatio = DrawlNumber.ZERO;
        if (implicitHeightOfContents.isGreaterThan(DrawlNumber.ZERO))
        {
            implicitAspectRatio = implicitWidthOfContents.divide(implicitHeightOfContents,
                    DrawlNumber.mcOperations);
        }

        // Calculate explicit aspect ratio
        this.setExplicitHeightInternal(DrawlNumber.valueOf(explicitWidthOfDrawingFloat));
        this.setExplicitWidthInternal(DrawlNumber.valueOf(explicitWidthOfDrawingFloat));
        DrawlNumber explicitAspectRatio = this.getExplicitWidth().divide(this.getExplicitHeight(),
                DrawlNumber.mcOperations);
        logger.atFine().log("Explicit aspect ratio: " + explicitAspectRatio);

        if (FALSE)
        {
            if (implicitAspectRatio.isGreaterThan(explicitAspectRatio))
            {
                // The implicit aspect ratio is greater than the explicit aspect ratio.
                // Therefore, we are constrained by width.
                // Adjust the height to match.
                logger.atFine().log("Adjusting height");
                DrawlNumber adjustedHeight = this.getExplicitWidth().divide(explicitAspectRatio,
                        DrawlNumber.mcOperations);
                logger.atFine().log("Setting adjusted height, adjusted height: " + adjustedHeight.toPlainString());
                this.setExplicitHeight(adjustedHeight);
                logger.atFine().log("Setting explicit width");
                this.setExplicitWidth(DrawlNumber.valueOf(explicitWidthOfDrawingFloat));
            }
            else if (implicitAspectRatio.isLessThan(explicitAspectRatio))
            {
                // The implicit aspect ratio is less than or equal to the explicit aspect ratio.
                // Therefore, we are constrained by height.
                // Adjust the width to match.
                logger.atFine().log("Adjusting width");
                DrawlNumber adjustedWidth = this.getExplicitHeight().multiply(explicitAspectRatio,
                        DrawlNumber.mcOperations);
                this.setExplicitHeight(DrawlNumber.valueOf(explicitHeightOfDrawingFloat));
                this.setExplicitWidth(adjustedWidth);
            }
        }
        else
        {
            // The two aspect ratios are equal, so no adjustment is necessary
            logger.atFine().log("No adjustment necessary");
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
    public void setExplicitDimensions(Integer explicitWidthOfDrawing, Integer explicitHeightOfDrawing)
    {
        this.setExplicitDimensions(explicitWidthOfDrawing.floatValue(), explicitHeightOfDrawing.floatValue());
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
    private void setExplicitHeight(@NotNull DrawlNumber drawingExplicitHeight)
    {
        logger.atFine().log("Setting explicit height");
        // Note that this line, by changing this.explicitWidth, can change the explicit to implicit ratio
        this.setExplicitHeightInternal(drawingExplicitHeight);
        if (this.getExplicitWidth() == null)
        {
            // If this Drawing does not have an explicit width, make the explicit width of the Drawing as wide as it
            // needs to be to accommodate the contents.
            logger.atFine().log("Adjusting explicit width to fit");
            this.setExplicitWidthInternal(this.getImplicitWidthOfContents().multiply(this.getExplicitToImplicitRatio(),
                    DrawlNumber.mcOperations));
        }
        for (Shape shape : this.contents)
        {
            this.updateShape(shape);
        }
    }

    /**
     * Use this to allow logging changes to internal height
     * @param drawingExplicitHeight
     */
    private void setExplicitHeightInternal(@NotNull DrawlNumber drawingExplicitHeight)
    {
        this.explicitHeight = drawingExplicitHeight;
    }

    /**
     * Set the explicit height of this Drawing
     *
     * @param height the explicit height of the Drawing; it is a Float to match
     *               the precision allowed by the SVG spec.
     */
    public void setExplicitHeight(Float height)
    {
        this.setExplicitHeight(DrawlNumber.valueOf(height));
    }

    /**
     * Set the explicit height of this Drawing.
     *
     * @param height The explicit height of the Drawing; it is an Integer to allow for common use cases.
     */
    public void setExplicitHeight(Integer height)
    {
        this.setExplicitHeight(DrawlNumber.valueOf(height));
    }

    /**
     * Set the explicit width of this drawing.
     *
     * This method also calculates the explicit widths and x positions of all the drawing's contents.
     *
     * @param drawingExplicitWidth The explicit width of the drawing.
     */
    private void setExplicitWidth(@NotNull DrawlNumber drawingExplicitWidth)
    {
        logger.atFine().log("Setting explicit width");
        // Note that this line, by changing this.explicitWidth, can change the explicit to implicit ratio
        this.setExplicitWidthInternal(drawingExplicitWidth);
        if (this.getExplicitHeight() == null)
        {
            // If this Drawing does not have an explicit height, make the explicit height of the Drawing as tall as
            // necessary to accommodate the contents.
            this.setExplicitHeightInternal(this.getImplicitWidthOfContents().multiply(this.getExplicitToImplicitRatio(),
                    DrawlNumber.mcOperations));
        }
        for (Shape shape : this.contents)
        {
            this.updateShape(shape);
        }
    }

    /**
     * Set the explicit width of this Drawing.
     *
     * @param width the explicit width of the Drawing; it is a Float
     *              to match the precision allowed by the SVG spec.
     */
    public void setExplicitWidth(Float width)
    {
        this.setExplicitWidth(DrawlNumber.valueOf(width));

    }

    /**
     * Set the explicit width of this Drawing.
     *
     * @param width The explicit width of the Drawing; it is an Integer to allow for common use cases.
     */
    public void setExplicitWidth(Integer width)
    {
        this.setExplicitWidth(DrawlNumber.valueOf(width));
    }

    /**
     * Use this to allow logging changes to internal height
     * @param drawingExplicitWidth
     */
    private void setExplicitWidthInternal(@NotNull DrawlNumber drawingExplicitWidth)
    {
        this.explicitWidth = drawingExplicitWidth;
    }

    /**
     * Update the width and x-coordinate, height and y-coordinate of a single shape
     *
     * Called by setExplicitWidth()
     *
     */
    private void updateShape(@NotNull Shape shape)
    {
        logger.atFine().log("Updating shape");
        this.updateExplicitHeightOfShape(shape);
        this.updateExplicitWidthOfShape(shape);
        this.updateExplicitYPositionOfShape(shape);
        this.updateExplicitXPositionOfShape(shape);
    }


    private void updateExplicitHeightOfShape(@NotNull Shape shape)
    {
        // Update explicit height of shape
        DrawlNumber implicitHeightOfShape = shape.getImplicitHeight();
        DrawlNumber explicitHeightOfShape = implicitHeightOfShape.multiply(this.getExplicitToImplicitRatio(),
                DrawlNumber.mcOperations);
        shape.setExplicitHeight(explicitHeightOfShape);
    }

    private void updateExplicitWidthOfShape(@NotNull Shape shape)
    {
        // Update explicit width of shape
        logger.atFine().log("Updating explicit width of shape");
        DrawlNumber implicitWidthOfShape = shape.getImplicitWidth();
        DrawlNumber explicitWidthOfShape = implicitWidthOfShape.multiply(this.getExplicitToImplicitRatio(),
                DrawlNumber.mcOperations);
        shape.setExplicitWidth(explicitWidthOfShape);
    }
    private void updateExplicitXPositionOfShape(@NotNull Shape shape)
    {
        // Update the explicit x position of the Shape
        logger.atFine().log("Updating explicit x position");
        DrawlNumber implicitXPositionOfShape = shape.getImplicitXPositionCenter();
        // The fudge factor is to shift the diagram right so that all x coordinates are positive in explicit coordinate
        // space
        DrawlNumber fudgeFactorX = this.getImplicitXMinimum();
        DrawlNumber fudgedImplicitXPositionOfShape = implicitXPositionOfShape.subtract(fudgeFactorX,
                DrawlNumber.mcOperations);
        DrawlNumber explicitXPositionOfShape = fudgedImplicitXPositionOfShape.multiply(this.getExplicitToImplicitRatio(),
                DrawlNumber.mcOperations);
        shape.setExplicitXPositionCenter(explicitXPositionOfShape);
        DrawlNumber implicitWidthOfContents = this.getImplicitWidth();
        DrawlNumber explicitWidthOfContents = implicitWidthOfContents.multiply(this.getExplicitToImplicitRatio(),
                DrawlNumber.mcOperations);
        if (this.isExplicitSet())
        {
            DrawlNumber explicitWidthOfDrawing = this.getExplicitWidth();
            assert explicitWidthOfDrawing != null;
            DrawlNumber explicitHorizontalWhitespace = explicitWidthOfDrawing.subtract(explicitWidthOfContents,
                    DrawlNumber.mcOperations);
            DrawlNumber explicitHorizontalWhitespaceLeft = explicitHorizontalWhitespace.divide(DrawlNumber.TWO,
                    DrawlNumber.mcOperations);
            DrawlNumber finalExplicitXPositionOfShape = explicitXPositionOfShape.add(explicitHorizontalWhitespaceLeft,
                    DrawlNumber.mcOperations);
            shape.setExplicitXPositionCenter(finalExplicitXPositionOfShape);
        }
    }

    private void updateExplicitYPositionOfShape(@NotNull Shape shape)
    {
        // Update the explicit y position of the Shape
        DrawlNumber implicitYPositionOfShape = shape.getImplicitYPositionCenter();
        // The fudge factor is to shift the diagram down so that all y coordinates are positive in explicit coordinate space
        DrawlNumber fudgeFactorY = this.getImplicitYMaximum();
        DrawlNumber fudgedImplicitYPositionOfShape = implicitYPositionOfShape.subtract(fudgeFactorY);
        fudgedImplicitYPositionOfShape = fudgedImplicitYPositionOfShape.negate();
        DrawlNumber explicitToImplicitRatio = this.getExplicitToImplicitRatio();
        DrawlNumber explicitYPositionOfShape = fudgedImplicitYPositionOfShape.multiply(explicitToImplicitRatio,
                DrawlNumber.mcOperations);
        // TODO Add code parallel to code below to updateExplicitXPositionOfShape()
        DrawlNumber implicitHeightOfContents = this.getImplicitHeight();
        DrawlNumber explicitHeightOfContents = implicitHeightOfContents.multiply(explicitToImplicitRatio,
                DrawlNumber.mcOperations);
        if (this.isExplicitSet())
        {
            DrawlNumber explicitHeightOfDrawing = this.getExplicitHeight();
            DrawlNumber explicitVerticalWhitespace = explicitHeightOfDrawing.subtract(explicitHeightOfContents,
                    DrawlNumber.mcOperations);
            DrawlNumber explicitVerticalWhitespaceBelow = explicitVerticalWhitespace.divide(DrawlNumber.TWO,
                    DrawlNumber.mcOperations);
            DrawlNumber finalExplicitYPositionOfShape = explicitYPositionOfShape.add(explicitVerticalWhitespaceBelow,
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
    public void writeToFile(String filename, Integer width, Integer height) throws IOException
    {
        String svg = this.getSVG(width, height);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename)))
        {
            writer.write(svg);
        }
    }
}
