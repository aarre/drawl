package com.aarrelaakso.drawl.examples;

import com.aarrelaakso.drawl.Drawing;
import com.aarrelaakso.drawl.Rectangle;

import java.io.IOException;

/**
 * Demonstrates using Drawl to draw a rectangle.
 */
public class RectangleExample
{
    /**
     * Draws a rectangle.
     */
    public static void main(final String[] args) throws IOException {
        Drawing drawing = new Drawing();
        Rectangle rectangle = new Rectangle();
        drawing.add(rectangle);
        drawing.writeToFile("docs/adoc/images/Rectangle.svg", 100, 100);
    }
}
