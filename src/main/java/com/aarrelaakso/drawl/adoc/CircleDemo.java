package com.aarrelaakso.drawl.adoc;

import com.aarrelaakso.drawl.Circle;
import com.aarrelaakso.drawl.Drawing;

import java.io.IOException;

/**
 * Demonstrates using Drawl to draw a circle
 */
public class CircleDemo
{
    public static void main(final String[] args) throws IOException {
        Drawing drawing = new Drawing();
        Circle circle = new Circle();
        drawing.add(circle);
        drawing.writeToFile("docs/adoc/images/Circle.svg", 100, 100);
    }
}
