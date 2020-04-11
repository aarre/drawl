package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;

import java.lang.Math;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class Drawing {

    private Set<Circle> contents;
    private Integer explicitHeight;
    private Integer explicitWidth;
    private Logger logger;

    public Drawing() {
        contents = new HashSet<Circle>();
        logger = Logger.getLogger(Drawing.class.getName());
        logger.addHandler(new ConsoleHandler());

    }

    public void add(Circle circle) {
        contents.add(circle);
    }

    /**
     * Get the SVG for this Drawing.
     *
     * @param drawingWidth The desired width of the output (in pixels).
     * @param drawingHeight The desired height of the output (in pixels).
     * @return A string of valid SVG that depicts the drawing within the bounds of x and y.
     */
    public String getSVG(Integer drawingWidth, Integer drawingHeight) {
        this.setExplicitWidth(drawingWidth);
        this.setExplicitHeight(drawingHeight);
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
        String svg = new String("<svg");
        svg += " width=\"";
        svg += this.getExplicitWidth();
        svg += "\"";
        svg += " height=\"";
        svg += this.getExplicitHeight();
        svg += "\"";
        svg += ">";
        Integer radius = Math.min(this.getExplicitWidth(),this.getExplicitHeight())/2;
        int drawingCenterY = this.getExplicitHeight()/2;

        Integer explicitWidthPerObject = this.getExplicitWidthPerObject();
        int count = 0;
        int xPosition;
        for (Circle content : this.contents) {
            content.setRadiusFixed(radius);
            if (count == 0) {
                xPosition = (int) Math.round(explicitWidthPerObject - radius);
                if (content.getRightOf() != null) {
                    count++;
                }
            } else {
                xPosition = (int) Math.round((explicitWidthPerObject * count) - radius);
            }
            content.setX(xPosition);
            content.setY(drawingCenterY);
            svg += content.getSVG();
            count++;
        }
        svg += "</svg>";
        return(svg);
    }

    public Integer getExplicitHeight() {
        return this.explicitHeight;
    }

    public Integer getExplicitWidth(){
        return this.explicitWidth;
    }

    /**
     * Get the explicit width per object in this Drawing
     *
     * @return the explicit width per object in this Drawing, in pixels
     */
    @NotNull
    private Integer getExplicitWidthPerObject() {
       return (int)Math.round(this.getExplicitWidth().doubleValue() / this.getImplicitWidth());
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
                if (content.getRightOf() != null) {
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
     * @param height the explicit width of the Drawing, in pixels
     */
    public void setExplicitHeight(Integer height) {
        this.explicitHeight = height;
    }

    /**
     * Set the explicit width of this Drawing
     *
     * @param width the explicit width of the Drawing, in pixels
     */
    public void setExplicitWidth(Integer width) {
        this.explicitWidth = width;
    }

    /**
     * Get the number of items in this Drawing
     *
     * @return the number of items in this Drawing
     */
    public Integer size() {
        final Integer integer = new Integer(1);
        return integer;
    }
}
