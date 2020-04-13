package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;

import java.lang.Math;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class Drawing {

    private Set<Circle> contents;
    private Double explicitHeight;
    private Double explicitWidth;
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
    public String getSVG(Double drawingWidth, Double drawingHeight) {
        this.setExplicitWidth(drawingWidth);
        this.setExplicitHeight(drawingHeight);
        return this.getSVG();
    }

    public String getSVG(Integer drawingWidth, Integer drawingHeight) {
        this.setExplicitWidth(new Double(drawingWidth));
        this.setExplicitHeight(new Double(drawingHeight));
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

        Double drawingCenterY = this.getExplicitHeight()/2;

        Double explicitWidthPerObject = this.getExplicitWidthPerObject();
        Double radius = Math.min(explicitWidthPerObject,this.getExplicitHeight())/2;

        int count = 0;
        Double xPosition;
        for (Circle content : this.contents) {
            content.setExplicitRadius(radius);
            if (count == 0) {
                xPosition = explicitWidthPerObject - radius;
                if (content.getLeftOf() != null) {
                    count++;
                }
                if (content.getRightOf() != null) {
                    count++;
                }
            } else {
                xPosition = (explicitWidthPerObject * count) - radius;
            }
            content.setExplicitXPosition(xPosition);
            content.setExplicitYPosition(drawingCenterY);
            svg += content.getSVG();
            count++;
        }
        svg += "</svg>";
        return(svg);
    }

    public Double getExplicitHeight() {
        return this.explicitHeight;
    }

    public Double getExplicitWidth(){
        return this.explicitWidth;
    }

    /**
     * Get the explicit width per object in this Drawing
     *
     * @return the explicit width per object in this Drawing, in pixels
     */
    @NotNull
    private Double getExplicitWidthPerObject() {
       return this.getExplicitWidth() / this.getImplicitWidth();
    }

    /**
     * Get the implicit width of this Drawing
     *
     * @return the implicit width of this Drawing
     */
    private Double getImplicitWidth() {
        Double width = new Double(0.0);
        int count = 0;
        for (Circle content : this.contents) {
            if (count == 0) {
                width += content.getImplicitWidth();
            }
            Circle objectToLeft = content.getRightOf();
            if (objectToLeft != null) {
                    width += objectToLeft.getImplicitWidth();
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
    private Double getImplicitWidthOfContents() {
        Double implicitWidthOfContents = new Double(0.0);
        int count = 0;
        for (Circle content : this.contents) {
            if (count == 0) {
                implicitWidthOfContents += content.getImplicitWidth();
            } else {
                if ((content.getRightOf() != null)  || (content.getLeftOf() != null)) {    // TODO: What if we are counting twice left and right?
                    implicitWidthOfContents += content.getImplicitWidth();
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
    public void setExplicitHeight(Double height) {
        this.explicitHeight = height;
    }

    /**
     * Set the explicit width of this Drawing
     *
     * @param width the explicit width of the Drawing, in pixels
     */
    public void setExplicitWidth(Double width) {
        this.explicitWidth = width;
        Double implicitWidthOfContents = this.getImplicitWidthOfContents();
        Double explicitWidthPerImplicitWidth = this.explicitWidth / implicitWidthOfContents;
        Double implicitWidthOfThisCircle = null;
        Double explicitWidthOfThisCircle = null;
        for (Circle content : this.contents) {
                implicitWidthOfThisCircle = content.getImplicitWidth();
                explicitWidthOfThisCircle = implicitWidthOfThisCircle * explicitWidthPerImplicitWidth;
                content.setExplicitWidth(explicitWidthOfThisCircle);
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
