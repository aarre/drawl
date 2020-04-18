package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class Drawing {

    private Set<Circle> contents;
    private BigDecimal explicitHeight;
    private BigDecimal explicitWidth;
    private Logger logger;

    public Drawing() {
        contents = new HashSet<Circle>();
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
        circle.setExplicitWidth(this.getExplicitWidth());    // TODO: What if the drawing has other contents?
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
        this.setExplicitWidth(drawingWidth);
        this.setExplicitHeight(drawingHeight);
        return this.getSVG();
    }

    /**
     * Get the SVG for this drawing.
     *
     * @param drawingWidth The desired width of the output; the type is Integer to allow for common use cases.
     * @param drawingHeight The desired height of the output; the type is Integer to allow for common use cases.
     * @return A string of valid SVG that depicts the drawing within the bounds of drawingWidth and drawingHeight.
     */
    public String getSVG(Integer drawingWidth, Integer drawingHeight) {
        this.setExplicitWidth(drawingWidth.floatValue());
        this.setExplicitHeight(drawingHeight.floatValue());
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
        String height = SVG.toString(this.getExplicitHeight());
        String width = SVG.toString(this.getExplicitWidth());

        String svg = new String("<?xml version=\"1.0\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\"");
        svg += " width=\"";
        svg += width;
        svg += "\"";
        svg += " height=\"";
        svg += height;
        svg += "\"";
        svg += ">";

        BigDecimal drawingCenterY = this.getExplicitHeight().divide(BigDecimal.valueOf(2), BigDecimalMath.mathContext);

        BigDecimal explicitWidthPerObject = this.getExplicitWidthPerObject();
        BigDecimal radius = explicitWidthPerObject.min(this.getExplicitHeight()).divide(BigDecimal.valueOf(2), BigDecimalMath.mathContext);

        int count = 0;
        BigDecimal xPosition;
        if (this.contents != null) {
            for (Circle content : this.contents) {
                content.setExplicitRadius(radius);
                if (count == 0) {
                    xPosition = explicitWidthPerObject.subtract(radius);
                    if (content.getLeftOf() != null) {
                        count++;
                    }
                    if (content.getRightOf() != null) {
                        count++;
                    }
                } else {
                    xPosition = explicitWidthPerObject.multiply(BigDecimal.valueOf(count), BigDecimalMath.mathContext).subtract(radius);
                }
                content.setExplicitXPosition(xPosition);
                content.setExplicitYPosition(drawingCenterY);
                svg += content.getSVG();
                count++;
            }
        }
        svg += "</svg>";
        return (svg);
    }

    public BigDecimal getExplicitHeight() {
        return this.explicitHeight;
    }

    public BigDecimal getExplicitWidth() {
        return this.explicitWidth;
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
     * Get the implicit width of this Drawing.
     *
     * This means the total implicit width subtended by all items in the Drawing.
     *
     * @return the implicit width of this Drawing
     */
    private BigDecimal getImplicitWidth() {
        BigDecimal xMaximum = null;
        BigDecimal xMinimum = null;
        BigDecimal xMaximumCurrent = null;
        BigDecimal xMinimumCurrent = null;
        for (Circle content : this.contents) {
           xMaximumCurrent = content.getImplicitXMaximum();
           xMinimumCurrent = content.getImplicitXMinimum();
            if (xMaximum == null) {
                xMaximum = xMaximumCurrent;
            } else if (xMaximumCurrent.compareTo(xMaximum) > 0) {
                xMaximum = xMaximumCurrent;
            }
            if (xMinimum == null) {
                xMinimum = xMinimumCurrent;
            } else if (xMinimumCurrent.compareTo(xMinimum) < 0) {
                xMinimum = xMinimumCurrent;
            }
        }
        if (xMaximum != null && xMinimum != null) {
            return xMaximum.subtract(xMinimum);
        } else {
            return null;
        }
    }

    /**
     * Get the implicit total width of all contents in the drawing
     *
     * @return the implicit total width of all contents in this drawing
     */
    private BigDecimal getImplicitWidthOfContents() {
        BigDecimal implicitWidthOfContents = new BigDecimal(0);
        int count = 0;
        for (Circle content : this.contents) {
            if (count == 0) {
                implicitWidthOfContents = implicitWidthOfContents.add(content.getImplicitWidth());
            } else {
                if ((content.getRightOf() != null) || (content.getLeftOf() != null)) {    // TODO: What if we are counting twice left and right?
                    implicitWidthOfContents = implicitWidthOfContents.add(content.getImplicitWidth());
                }
            }
            count++;
        }
        return implicitWidthOfContents;
    }


    /**
     * Set the explicit height of this Drawing
     *
     * @param height the explicit width of the Drawing; it is a Float to match
     *               the precision allowed by the SVG spec.
     */
    public void setExplicitHeight(Float height) {
        this.explicitHeight = BigDecimal.valueOf(height);
    }

    /**
     * Set the explicit width of this Drawing
     *
     * @param width the explicit width of the Drawing; it is a Float
     *              to match the precision allowed by the SVG spec.
     */
    public void setExplicitWidth(Float width) {
        this.explicitWidth = BigDecimal.valueOf(width);
        BigDecimal implicitWidthOfContents = this.getImplicitWidthOfContents();
        if (implicitWidthOfContents.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal explicitWidthPerImplicitWidth = this.explicitWidth.divide(implicitWidthOfContents, BigDecimalMath.mathContext);
            BigDecimal implicitWidthOfThisCircle = null;
            BigDecimal explicitWidthOfThisCircle = null;
            for (Circle circle : this.contents) {
                implicitWidthOfThisCircle = circle.getImplicitWidth();
                explicitWidthOfThisCircle = implicitWidthOfThisCircle.multiply(explicitWidthPerImplicitWidth, BigDecimalMath.mathContext);
                circle.setExplicitWidth(explicitWidthOfThisCircle);
                BigDecimal implicitXPositionOfThisCircle = circle.getImplicitXPosition();
                implicitXPositionOfThisCircle = implicitXPositionOfThisCircle.add(BigDecimal.valueOf(0.5));
                circle.setExplicitXPosition(implicitXPositionOfThisCircle.multiply(explicitWidthPerImplicitWidth, BigDecimalMath.mathContext));
            }
        }
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
     * Write SVG representing this drawing to a file.
     *
     * @param filename The name of the file to which to write.
     * @throws IOException
     */
    public void writeToFile(String filename) throws IOException {
        String str = this.getSVG();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(str);
        writer.close();
    }
}
