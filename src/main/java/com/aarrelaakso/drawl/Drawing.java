package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class Drawing {

    private HashSet<Circle> contents;
    private BigDecimal explicitHeight;
    private BigDecimal explicitWidth;
    private Logger logger;

    public Drawing() {
        contents = new HashSet<>();
        logger = Logger.getLogger(Drawing.class.getName());
        logger.addHandler(new ConsoleHandler());

    }

    /**
     * Add a circle to this drawing
     *
     * @param circle The circle to add
     */
    public void add(Circle circle) {
        contents.add(circle);
    }

    /**
     * Get the explicit height of this Drawing.
     *
     * @return The explicit height of this Drawing. Can be zero if the drawing has length zero or the explicit
     * dimensions have not been set.
     */
    public BigDecimal getExplicitHeight() {
        if (this.explicitHeight == null) {
            return BigDecimal.ZERO;
        } else {
            return this.explicitHeight;
        }
    }

    /**
     * Get the explicit width of this Drawing.
     *
     * @return The explicit width of this Drawing. Can be zero if the drawing has length zero or the explicit
     * dimensions have not been set.
     */
    public BigDecimal getExplicitWidth() {
        if (this.explicitWidth == null) {
            return BigDecimal.ZERO;
        } else {
            return this.explicitWidth;
        }
    }

    /**
     * Get the explicit width per object in this Drawing.
     *
     * @return the explicit width per object in this Drawing.
     */
    private BigDecimal getExplicitWidthPerObject() {
        BigDecimal implicitWidth = this.getImplicitWidth();
        if ((implicitWidth == null) || implicitWidth.equals(BigDecimal.ZERO)) {
            // The drawing has no contents
            return BigDecimal.ZERO;
        } else {
            return this.getExplicitWidth().divide(implicitWidth, BigDecimalMath.mathContext);
        }
    }

    /**
     * Get the implicit total height of all contents in the drawing
     *
     * @return the implicit total height of all contents in this drawing
     */
    private BigDecimal getImplicitHeightOfContents() {
        return this.getImplicitYMaximum().subtract(this.getImplicitYMinimum());
    }

    /**
     * Get the implicit width of this Drawing.
     * <p>
     * This means the total implicit width subtended by all items in the Drawing.
     *
     * @return the implicit width of this Drawing
     */
    private @NotNull BigDecimal getImplicitWidth() {
        BigDecimal xMaximum = this.getImplicitXMaximum();
        BigDecimal xMinimum = this.getImplicitXMinimum();
        return xMaximum.subtract(xMinimum);
    }

    /**
     * Get the implicit total width of all contents in the drawing
     *
     * @return the implicit total width of all contents in this drawing
     */
    private BigDecimal getImplicitWidthOfContents() {
        return this.getImplicitXMaximum().subtract(this.getImplicitXMinimum());
    }

    /**
     * Get the maximum x-coordinate of this drawing in implicit terms.
     * <p>
     * The maximum x-coordinate is the right edge of the drawing.
     */
    @NotNull
    private BigDecimal getImplicitXMaximum() {
        BigDecimal xMaximum = BigDecimal.valueOf(Double.MIN_VALUE);
        for (Circle content : this.contents) {
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
        BigDecimal xMinimum = BigDecimal.valueOf(Double.MAX_VALUE);
        for (Circle content : this.contents) {
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
        for (Circle content : this.contents) {
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
        for (Circle content : this.contents) {
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
     */
    private @NotNull String getSVG() {

        BigDecimal bdHeight = this.getExplicitHeight();
        if (bdHeight == null) {
            throw new NullPointerException("Explicit height cannot be null.");
        }
        String height = SVG.toString(this.getExplicitHeight());

        BigDecimal bdWidth = this.getExplicitWidth();
        if (bdWidth == null) {
            throw new NullPointerException("Explicit width cannot be null.");
        }
        String width = SVG.toString(this.getExplicitWidth());

        StringBuilder svgBuilder = new StringBuilder("<?xml version=\"1.0\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\"");
        svgBuilder.append(" width=\"");
        svgBuilder.append(width);
        svgBuilder.append("\"");
        svgBuilder.append(" height=\"");
        svgBuilder.append(height);
        svgBuilder.append("\"");
        svgBuilder.append(">");

        if (this.contents != null) {
            for (Circle content : this.contents) {
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
     *
     * It is preferable to do it this way rather than setting width and height separately.
     *
     * @param explicitWidthOfDrawingFloat The explicit width of the Drawing.
     * @param explicitHeightOfDrawingFloat The explicit height of the Drawing.
     */
    public void setExplicitDimensions(Float explicitWidthOfDrawingFloat, Float explicitHeightOfDrawingFloat) {

        BigDecimal explicitHeightOfDrawing = BigDecimal.valueOf(explicitHeightOfDrawingFloat);
        BigDecimal explicitWidthOfDrawing = BigDecimal.valueOf(explicitWidthOfDrawingFloat);

        if (this.length() > 0) {

            BigDecimal implicitHeightOfContents = this.getImplicitHeightOfContents();
            assert implicitHeightOfContents.compareTo(BigDecimal.ZERO) != 0 : "Implicit height of contents cannot be zero.";

            BigDecimal implicitWidthOfContents = this.getImplicitWidthOfContents();
            assert implicitWidthOfContents.compareTo(BigDecimal.ZERO) != 0 : "Implicit width of contents cannot be zero.";

            BigDecimal implicitAspectRatio = implicitWidthOfContents.divide(implicitHeightOfContents, BigDecimalMath.mathContext);
            BigDecimal explicitAspectRatio = explicitWidthOfDrawing.divide(explicitHeightOfDrawing, BigDecimalMath.mathContext);

            if (implicitAspectRatio.compareTo(explicitAspectRatio) > 0) {
                // The implicit aspect ratio is greater than the explicit aspect ratio
                // Therefore, we are constrained by width
                BigDecimal adjustedHeight = explicitWidthOfDrawing.divide(explicitAspectRatio, BigDecimalMath.mathContext);
                this.setExplicitHeight(adjustedHeight);
                this.setExplicitWidth(explicitWidthOfDrawing);

            } else {
                // The implicit aspect ratio is less than or equal to the explicit aspect ratio
                // Therefore, we are constrained by height
                BigDecimal adjustedWidth = explicitHeightOfDrawing.multiply(explicitAspectRatio);
                this.setExplicitWidth(adjustedWidth);
                this.setExplicitHeight(explicitHeightOfDrawing);
            }
        } else {
            // The drawing has no contents, so just set its dimensions
            this.setExplicitWidth(explicitWidthOfDrawing);
            this.setExplicitHeight(explicitHeightOfDrawing);
        }
    }

    /**
     * Set the explicit height of this drawing.
     *
     * @param drawingExplicitHeight The explict height of the drawing.
     */
    private void setExplicitHeight(BigDecimal drawingExplicitHeight) {
        this.explicitHeight = drawingExplicitHeight;
        if (this.length() > 0) {
            BigDecimal implicitHeightOfContents = this.getImplicitHeightOfContents();
            BigDecimal explicitHeightPerImplicitHeight;
            if (implicitHeightOfContents.compareTo(BigDecimal.ZERO) != 0) {
                explicitHeightPerImplicitHeight = this.explicitHeight.divide(implicitHeightOfContents, BigDecimalMath.mathContext);
            } else {
                explicitHeightPerImplicitHeight = BigDecimal.ZERO;
            }
            BigDecimal implicitHeightOfCircle;
            BigDecimal explicitHeightOfCircle;
            // Set the explicit y positions of all contents
            for (Circle circle : this.contents) {
                implicitHeightOfCircle = circle.getImplicitHeight();
                explicitHeightOfCircle = implicitHeightOfCircle.multiply(explicitHeightPerImplicitHeight, BigDecimalMath.mathContext);
                circle.setExplicitHeight(explicitHeightOfCircle);
                BigDecimal implicitYPositionOfCircle = circle.getImplicitYPosition();
                // The fudge factor is to shift the diagram down so that all y-coordinates are positive
                BigDecimal fudgeFactor = this.getImplicitYMaximum();
                BigDecimal fudgedImplicitYPositionOfCircle = implicitYPositionOfCircle.subtract(fudgeFactor);
                fudgedImplicitYPositionOfCircle = fudgedImplicitYPositionOfCircle.multiply(BigDecimal.valueOf(-1));
                assert fudgedImplicitYPositionOfCircle.compareTo(BigDecimal.ZERO) >= 0 : "All y-coordinates must be positive.";
                circle.setExplicitYPosition(fudgedImplicitYPositionOfCircle.multiply(explicitHeightPerImplicitHeight, BigDecimalMath.mathContext));
            }
        }
    }

    /**
     * Set the explicit height of this Drawing
     *
     * @param height the explicit width of the Drawing; it is a Float to match
     *               the precision allowed by the SVG spec.
     */
    public void setExplicitHeight(Float height) {
        this.setExplicitHeight(BigDecimal.valueOf(height));
    }

    private void setExplicitWidth(BigDecimal drawingExplicitWidth) {
        this.explicitWidth = drawingExplicitWidth;
        if (this.length() > 0) {
            BigDecimal implicitWidthOfContents = this.getImplicitWidthOfContents();
            BigDecimal explicitWidthPerImplicitWidth;
            if (implicitWidthOfContents.compareTo(BigDecimal.ZERO) != 0) {
                explicitWidthPerImplicitWidth = this.explicitWidth.divide(implicitWidthOfContents, BigDecimalMath.mathContext);
            } else {
                explicitWidthPerImplicitWidth = BigDecimal.ZERO;
            }
            BigDecimal implicitWidthOfCircle;
            BigDecimal explicitWidthOfCircle;
            // Set the explicit x positions of all contents
            for (Circle circle : this.contents) {
                implicitWidthOfCircle = circle.getImplicitWidth();
                explicitWidthOfCircle = implicitWidthOfCircle.multiply(explicitWidthPerImplicitWidth, BigDecimalMath.mathContext);
                circle.setExplicitWidth(explicitWidthOfCircle);
                BigDecimal implicitXPositionOfCircle = circle.getImplicitXPosition();
                // The fudge factor is to shift the diagram right so that all x-coordinates are positive
                BigDecimal fudgeFactor = BigDecimal.ZERO.subtract(this.getImplicitXMinimum());
                BigDecimal fudgedImplicitXPositionOfCircle = implicitXPositionOfCircle.add(fudgeFactor);
                circle.setExplicitXPosition(fudgedImplicitXPositionOfCircle.multiply(explicitWidthPerImplicitWidth, BigDecimalMath.mathContext));
            }
        }
    }

    /**
     * Set the explicit width of this Drawing.
     * <p>
     * This has the side effect of setting the explicit x positions of all the contents.
     *
     * @param width the explicit width of the Drawing; it is a Float
     *              to match the precision allowed by the SVG spec.
     */
    public void setExplicitWidth(Float width) {
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
