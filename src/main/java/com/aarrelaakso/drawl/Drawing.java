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
import java.util.logging.Level;

public class Drawing
{

    private static final FluentLogger logger;

    static
    {
        logger = FluentLogger.forEnclosingClass();

    }

    private final HashSet<Shape> contents;
    private BigDecimal explicitHeight;
    private BigDecimal explicitWidth;

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
    public void add(Shape shape)
    {
        contents.add(shape);
    }

    /**
     * Get the explicit height of this Drawing.
     *
     * @return The explicit height of this Drawing. Can be null if the drawing has length zero or the explicit
     * dimensions have not been set.
     */
    @Nullable
    public BigDecimal getExplicitHeight()
    {
        return this.explicitHeight;
    }

    /**
     * Get the ratio of the explicit height of this Drawing to the implicit height of its contents.
     *
     * @return The ratio of the explicit height of this Drawing to the implicit height of its contents
     */
    private BigDecimal getExplicitHeightPerImplicitHeight()
    {
        BigDecimal implicitHeightOfContents = this.getImplicitHeightOfContents();
        BigDecimal explicitHeightPerImplicitHeight;
        if (implicitHeightOfContents.compareTo(BigDecimal.ZERO) != 0)
        {
            if (this.getExplicitHeight() == null)
            {
                // If the explicit height has no value, try to calculate using the explicit width
                if (this.getExplicitWidth() == null)
                {
                    // If the explicit width also has no value, the ratio is 0
                    explicitHeightPerImplicitHeight = BigDecimal.ZERO;
                }
                else
                {
                    // The explicit width exceeds 0, so use it to calculate the ratio
                    explicitHeightPerImplicitHeight = this.getExplicitWidth().divide(this.getImplicitWidthOfContents(),
                            SisuBigDecimal.mcOperations);
                }
            }
            else
            {
                // The explicit height has a value, so calculate using the explicit height
                explicitHeightPerImplicitHeight = this.getExplicitHeight().divide(implicitHeightOfContents,
                        SisuBigDecimal.mcOperations);
            }
        }
        else
        {
            // The implicit height of the contents is 0, so the ratio is 0
            explicitHeightPerImplicitHeight = BigDecimal.ZERO;
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
     * @todo Make private and refactor test cases accordingly.
     */
    public BigDecimal getExplicitToImplicitRatio()
    {
        BigDecimal explicitHeightPerImplicitHeight = this.getExplicitHeightPerImplicitHeight();
        BigDecimal explicitWidthPerImplicitWidth = this.getExplicitWidthPerImplicitWidth();
        if (explicitHeightPerImplicitHeight.compareTo(explicitWidthPerImplicitWidth) <= 0)
        {
            logger.at(Level.FINE).log("Setting explicitToImplicitRatio based on height");
            return explicitHeightPerImplicitHeight;
        }
        else
        {
            logger.at(Level.FINE).log("Setting explicitToImplicitRatio based on width");
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
    public BigDecimal getExplicitWidth()
    {
        return this.explicitWidth;
    }

    /**
     * Get the ratio of the explicit width of this drawing to the implicit width of its contents.
     *
     * @return The ratio of the explicit width of this drawing to the implicit width of its contents.
     * @todo Make private and factor out of unit tests.
     */
    @NotNull
    public BigDecimal getExplicitWidthPerImplicitWidth()
    {
        BigDecimal implicitWidthOfContents = this.getImplicitWidthOfContents();
        assert implicitWidthOfContents != null :
                "The implicit width of the contents of a drawing cannot be null.";
        assert implicitWidthOfContents.compareTo(BigDecimal.ZERO) >= 0 :
                "The implicit width of the contents of a drawing must be positive (or zero).";
        BigDecimal explicitWidthPerImplicitWidth;
        if (implicitWidthOfContents.compareTo(BigDecimal.ZERO) != 0)
        {
            if (this.getExplicitWidth() == null)
            {
                // If the explicit width is null, try to use it to calculate using the explicit height
                if (this.getExplicitHeight() == null)
                {
                    // The explicit height is also null, so the ratio is 0
                    explicitWidthPerImplicitWidth = BigDecimal.ZERO;
                }
                else
                {
                    // Use the explicit height to calculate the ratio
                    explicitWidthPerImplicitWidth = this.getExplicitHeight().divide(this.getImplicitHeightOfContents(),
                            SisuBigDecimal.mcOperations);
                }
            }
            else
            {
                // The explicit width is not null, so calculate using the explicit width
                explicitWidthPerImplicitWidth = this.getExplicitWidth().divide(implicitWidthOfContents,
                        SisuBigDecimal.mcOperations);
            }
        }
        else
        {
            // The implicit width of the contents is 0, so the ratio is 0
            explicitWidthPerImplicitWidth = BigDecimal.ZERO;
        }
        assert explicitWidthPerImplicitWidth != null :
                "The ratio of the explicit width of a drawing to the implicit width of its contents cannot be null.";
        return explicitWidthPerImplicitWidth;
    }

    /**
     * Get the explicit width per object in this Drawing.
     *
     * @return the explicit width per object in this Drawing.
     * @todo Make this method private and adjust unit tests accordingly
     */
    public BigDecimal getExplicitWidthPerObject()
    {
        BigDecimal implicitWidth = this.getImplicitWidthOfContents();
        if ((implicitWidth == null) || implicitWidth.equals(BigDecimal.ZERO))
        {
            // The drawing has no contents
            return BigDecimal.ZERO;
        }
        else
        {
            return this.getExplicitWidth().divide(implicitWidth, SisuBigDecimal.mcOperations);
        }
    }

    /**
     * Get the implicit total height of all contents in the drawing.
     * <p>
     * Provides public access to the implicit height of the contents.
     *
     * @todo Remove this function and factor out of test cases.
     */
    public BigDecimal getImplicitHeight()
    {
        return this.getImplicitHeightOfContents();
    }

    /**
     * Get the implicit total height of all contents in the drawing.
     *
     * @return the implicit total height of all contents in this drawing
     */
    private BigDecimal getImplicitHeightOfContents()
    {
        return this.getImplicitYMaximum().subtract(this.getImplicitYMinimum());
    }

    /**
     * Get the implicit total width of all contents in this Drawing.
     * <p>
     * Provides public access to the implicit width of the contents for testing.
     *
     * @todo Remove this function and factor out of test cases.
     */
    public BigDecimal getImplicitWidth()
    {
        return this.getImplicitWidthOfContents();
    }

    /**
     * Get the implicit total width of all contents in the drawing
     *
     * @return the implicit total width of all contents in this drawing
     */
    private BigDecimal getImplicitWidthOfContents()
    {
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
    private BigDecimal getImplicitXMaximum()
    {
        BigDecimal xMaximum = BigDecimal.ZERO;
        for (Shape content : this.contents)
        {
            BigDecimal xMaximumCurrent = content.getImplicitXMaximum();
            if (xMaximumCurrent.compareTo(xMaximum) > 0)
            {
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
    private BigDecimal getImplicitXMinimum()
    {
        BigDecimal xMinimum = BigDecimal.ZERO;
        for (Shape content : this.contents)
        {
            BigDecimal xMinimumCurrent = content.getImplicitXMinimum();
            if (xMinimumCurrent.compareTo(xMinimum) < 0)
            {
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
    private BigDecimal getImplicitYMaximum()
    {
        BigDecimal yMaximum = BigDecimal.ZERO;
        for (Shape content : this.contents)
        {
            BigDecimal yMaximumCurrent = content.getImplicitYMaximum();
            if (yMaximumCurrent.compareTo(yMaximum) > 0)
            {
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
    private BigDecimal getImplicitYMinimum()
    {
        BigDecimal yMinimum = BigDecimal.valueOf(Double.MAX_VALUE);
        for (Shape content : this.contents)
        {
            BigDecimal yMinimumCurrent = content.getImplicitXMinimum();
            if (yMinimumCurrent.compareTo(yMinimum) < 0)
            {
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
     * @todo Make this method protected and adjust unit tests accordingly.
     */
    public @NotNull String getSVG()
    {

        StringBuilder svgBuilder = new StringBuilder("<?xml version=\"1.0\" standalone=\"no\"?>");
        svgBuilder.append("<svg xmlns=\"http://www.w3.org/2000/svg\"");

        BigDecimal bdWidth = this.getExplicitWidth();
        if (bdWidth != null)
        {
            svgBuilder.append(" width=\"");
            String width = SVG.toString(this.getExplicitWidth());
            svgBuilder.append(width);
            svgBuilder.append("\"");
        }

        BigDecimal bdHeight = this.getExplicitHeight();
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
     * Get the number of items in this Drawing
     *
     * @return the number of items in this Drawing
     */
    public Integer length()
    {
        return contents.size();
    }

    /**
     * Set the explicit dimensions of this Drawing.
     * <p>
     * It is preferable to do it this way rather than setting width and height separately.
     *
     * @param explicitWidthOfDrawingFloat  The explicit width of the Drawing.
     * @param explicitHeightOfDrawingFloat The explicit height of the Drawing.
     */
    public void setExplicitDimensions(Float explicitWidthOfDrawingFloat, Float explicitHeightOfDrawingFloat)
    {

        this.setExplicitHeight(BigDecimal.valueOf(explicitHeightOfDrawingFloat));
        this.setExplicitWidth(BigDecimal.valueOf(explicitWidthOfDrawingFloat));

        if (this.length() > 0)
        {

            BigDecimal implicitHeightOfContents = this.getImplicitHeightOfContents();
            assert implicitHeightOfContents.compareTo(BigDecimal.ZERO) > 0 :
                    "Implicit height of contents cannot be zero or negative.";

            BigDecimal implicitWidthOfContents = this.getImplicitWidthOfContents();
            assert implicitWidthOfContents.compareTo(BigDecimal.ZERO) > 0 :
                    "Implicit width of contents cannot be zero or negative.";

            BigDecimal implicitAspectRatio = implicitWidthOfContents.divide(implicitHeightOfContents,
                    SisuBigDecimal.mcOperations);
            BigDecimal explicitAspectRatio = this.getExplicitWidth().divide(this.getExplicitHeight(),
                    SisuBigDecimal.mcOperations);

            if (implicitAspectRatio.compareTo(explicitAspectRatio) > 0)
            {
                // The implicit aspect ratio is greater than the explicit aspect ratio.
                // Therefore, we are constrained by width.
                BigDecimal adjustedHeight = this.getExplicitWidth().divide(explicitAspectRatio,
                        SisuBigDecimal.mcOperations);
                this.setExplicitHeight(adjustedHeight);
                this.setExplicitWidth(this.getExplicitWidth());

            }
            else
            {
                // The implicit aspect ratio is less than or equal to the explicit aspect ratio
                // Therefore, we are constrained by height
                BigDecimal adjustedWidth = this.getExplicitHeight().multiply(explicitAspectRatio);
                this.setExplicitWidth(adjustedWidth);
                this.setExplicitHeight(this.getExplicitHeight());
            }
        }
        else
        {
            // The drawing has no contents, so just set its dimensions
            this.setExplicitWidth(this.getExplicitWidth());
            this.setExplicitHeight(this.getExplicitHeight());
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
    private void setExplicitHeight(@NotNull BigDecimal drawingExplicitHeight)
    {
        // Note that this line, by changing this.explicitWidth, can change the explicit to implicit ratio
        this.explicitHeight = drawingExplicitHeight;
        if (this.getExplicitWidth() == null)
        {
            // If this Drawing does not have an explicit width, make the explicit width of the Drawing as wide as it
            // needs to be to accommodate the contents.
            this.explicitWidth = this.getImplicitWidthOfContents().multiply(this.getExplicitToImplicitRatio(),
                    SisuBigDecimal.mcOperations);
        }
        for (Shape shape : this.contents)
        {
            updateShape(shape);
        }
    }

    /**
     * Set the explicit height of this Drawing
     *
     * @param height the explicit height of the Drawing; it is a Float to match
     *               the precision allowed by the SVG spec.
     */
    public void setExplicitHeight(Float height)
    {
        this.setExplicitHeight(BigDecimal.valueOf(height));
    }

    /**
     * Set the explicit height of this Drawing.
     *
     * @param height The explicit height of the Drawing; it is an Integer to allow for common use cases.
     */
    public void setExplicitHeight(Integer height)
    {
        this.setExplicitHeight(BigDecimal.valueOf(height));
    }

    /**
     * Set the explicit width of this drawing.
     *
     * This method also calculates the explicit widths and x positions of all the drawing's contents.
     *
     * @param drawingExplicitWidth The explicit width of the drawing.
     */
    private void setExplicitWidth(@NotNull BigDecimal drawingExplicitWidth)
    {
        // Note that this line, by changing this.explicitWidth, can change the explicit to implicit ratio
        this.explicitWidth = drawingExplicitWidth;
        if (this.getExplicitHeight() == null)
        {
            // If this Drawing does not have an explicit height, make the explicit height of the Drawing as tall as
            // necessary to accommodate the contents.
            this.explicitHeight = this.getImplicitWidthOfContents().multiply(this.getExplicitToImplicitRatio(),
                    SisuBigDecimal.mcOperations);
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
        this.setExplicitWidth(BigDecimal.valueOf(width));

    }

    /**
     * Set the explicit width of this Drawing.
     *
     * @param width The explicit width of the Drawing; it is an Integer to allow for common use cases.
     */
    public void setExplicitWidth(Integer width)
    {
        this.setExplicitWidth(BigDecimal.valueOf(width));
    }

    /**
     * Update the width and x-coordinate, height and y-coordinate of a single shape
     *
     * Called by setExplicitWidth()
     *
     */
    private void updateShape(Shape shape)
    {
        this.updateExplicitHeightOfShape(shape);
        this.updateExplicitWidthOfShape(shape);
        this.updateExplicitYPositionOfShape(shape);
        this.updateExplicitXPositionOfShape(shape);
    }


    private void updateExplicitHeightOfShape(Shape shape)
    {
        // Update explicit height of shape
        BigDecimal implicitHeightOfShape = shape.getImplicitHeight();
        BigDecimal explicitHeightOfShape = implicitHeightOfShape.multiply(this.getExplicitToImplicitRatio(),
                SisuBigDecimal.mcOperations);
        shape.setExplicitHeight(explicitHeightOfShape);
    }

    private void updateExplicitWidthOfShape(Shape shape)
    {
        // Update explicit width of shape
        BigDecimal implicitWidthOfShape = shape.getImplicitWidth();
        BigDecimal explicitWidthOfShape = implicitWidthOfShape.multiply(this.getExplicitToImplicitRatio(),
                SisuBigDecimal.mcOperations);
        shape.setExplicitWidth(explicitWidthOfShape);
    }
    private void updateExplicitXPositionOfShape(Shape shape)
    {
        // Update the explicit x position of the Shape
        BigDecimal implicitXPositionOfShape = shape.getImplicitXPosition();
        BigDecimal fudgeFactorX = this.getImplicitXMinimum();
        BigDecimal fudgedImplicitXPositionOfShape = implicitXPositionOfShape.subtract(fudgeFactorX);
        shape.setExplicitXPosition(fudgedImplicitXPositionOfShape.multiply(this.getExplicitToImplicitRatio(),
                SisuBigDecimal.mcOperations));
    }

    private void updateExplicitYPositionOfShape(Shape shape)
    {
        // Update the explicit y position of the Shape
        BigDecimal implicitYPositionOfShape = shape.getImplicitYPosition();
        // The fudge factor is to shift the diagram down so that all y-coordinates are positive
        BigDecimal fudgeFactorY = this.getImplicitYMaximum();
        BigDecimal fudgedImplicitYPositionOfShape = implicitYPositionOfShape.subtract(fudgeFactorY);
        fudgedImplicitYPositionOfShape = fudgedImplicitYPositionOfShape.negate();
        BigDecimal explicitYPositionOfShape =
                fudgedImplicitYPositionOfShape.multiply(this.getExplicitToImplicitRatio(),
                        SisuBigDecimal.mcOperations);
        shape.setExplicitYPosition(explicitYPositionOfShape);
    }

    /**
     * Write SVG representing this drawing to a file.
     *
     * @param filename The name of the file to which to write.
     * @throws IOException If there is a problem writing to the file.
     */
    public void writeToFile(String filename) throws IOException
    {
        String str = this.getSVG();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename)))
        {
            writer.write(str);
        }
        catch (IOException e)
        {
            throw (e);
        }
    }
}
