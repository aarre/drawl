package com.aarrelaakso.drawl;

import java.lang.Math;
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
     * @param drawingWidth The desired width of the output (in pixels).
     * @param drawingHeight The desired height of the output (in pixels).
     * @return A string of valid SVG that depicts the drawing within the bounds of x and y.
     */
    public String getSVG(BigDecimal drawingWidth, BigDecimal drawingHeight) {
        this.setExplicitWidth(drawingWidth);
        this.setExplicitHeight(drawingHeight);
        return this.getSVG();
    }

    public String getSVG(Integer drawingWidth, Integer drawingHeight) {
        this.setExplicitWidth(new BigDecimal(drawingWidth));
        this.setExplicitHeight(new BigDecimal(drawingHeight));
        return this.getSVG();
    }

    /**
     * Get the SVG for this Drawing
     *
     * Assumes that the explicit width and height have been set
     *
     * @return A string of valid SVG that depicts the drawing within the bounds of the explicit width and height
     */
    private String getSVG() {
        String height = SVG.toString(this.getExplicitHeight());
        String width = SVG.toString(this.getExplicitWidth());

        String svg = new String("<svg");
        svg += " width=\"";
        svg += width;
        svg += "\"";
        svg += " height=\"";
        svg += height;
        svg += "\"";
        svg += ">";

        BigDecimal drawingCenterY = this.getExplicitHeight().divide(BigDecimal.valueOf(2), BigDecimalMath.SCALE, BigDecimalMath.ROUNDING_MODE);

        BigDecimal explicitWidthPerObject = this.getExplicitWidthPerObject();
        BigDecimal radius = explicitWidthPerObject.min(this.getExplicitHeight()).divide(BigDecimal.valueOf(2), BigDecimalMath.SCALE, BigDecimalMath.ROUNDING_MODE);

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
                    xPosition = explicitWidthPerObject.multiply(BigDecimal.valueOf(count)).subtract(radius);
                }
                content.setExplicitXPosition(xPosition);
                content.setExplicitYPosition(drawingCenterY);
                svg += content.getSVG();
                count++;
            }
        }
        svg += "</svg>";
        return(svg);
    }

    public BigDecimal getExplicitHeight() {
        return this.explicitHeight;
    }

    public BigDecimal getExplicitWidth(){
        return this.explicitWidth;
    }

    /**
     * Get the explicit width per object in this Drawing
     *
     * @return the explicit width per object in this Drawing, in pixels
     */
    private BigDecimal getExplicitWidthPerObject() {
        if (this.getImplicitWidth().equals(BigDecimal.ZERO)) {
            // The drawing has no contents
            return BigDecimal.ZERO;
        } else {
            return this.getExplicitWidth().divide(this.getImplicitWidth(), BigDecimalMath.SCALE, BigDecimalMath.ROUNDING_MODE);
        }
    }

    /**
     * Get the implicit width of this Drawing
     *
     * @return the implicit width of this Drawing
     */
    private BigDecimal getImplicitWidth() {
        BigDecimal width = new BigDecimal(0);
        int count = 0;
        for (Circle content : this.contents) {
            if (count == 0) {
                width = width.add(content.getImplicitWidth());
            }
            Circle objectToLeft = content.getRightOf();
            if (objectToLeft != null) {
                    width = width.add(objectToLeft.getImplicitWidth());
            }
            count++;
        }
        return width;
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
                if ((content.getRightOf() != null)  || (content.getLeftOf() != null)) {    // TODO: What if we are counting twice left and right?
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
     * @param height the explicit width of the Drawing
     */
    public void setExplicitHeight(BigDecimal height) {
        this.explicitHeight = height;
    }

    /**
     * Set the explicit width of this Drawing
     *
     * @param width the explicit width of the Drawing, in pixels
     */
    public void setExplicitWidth(BigDecimal width) {
        this.explicitWidth = width;
        BigDecimal implicitWidthOfContents = this.getImplicitWidthOfContents();
        if (!implicitWidthOfContents.equals(BigDecimal.ZERO)) {
            BigDecimal explicitWidthPerImplicitWidth = this.explicitWidth.divide(implicitWidthOfContents,
                    BigDecimalMath.SCALE, BigDecimalMath.ROUNDING_MODE);
            BigDecimal implicitWidthOfThisCircle = null;
            BigDecimal explicitWidthOfThisCircle = null;
            BigDecimal currentXPosition = BigDecimal.ZERO;
            for (Circle circle : this.contents) {
                implicitWidthOfThisCircle = circle.getImplicitWidth();
                explicitWidthOfThisCircle = implicitWidthOfThisCircle.multiply(explicitWidthPerImplicitWidth);
                circle.setExplicitWidth(explicitWidthOfThisCircle);
                BigDecimal implicitXPositionOfThisCircle = circle.getImplicitXPosition();
                implicitXPositionOfThisCircle = implicitXPositionOfThisCircle.add(BigDecimal.valueOf(0.5));
                circle.setExplicitXPosition(implicitXPositionOfThisCircle.multiply(explicitWidthPerImplicitWidth));
            }
        }
    }


    /**
     * Get the number of items in this Drawing
     *
     * @return the number of items in this Drawing
     */
    public Integer length() {
        final Integer integer = new Integer(1);
        return integer;
    }
}
