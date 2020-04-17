package com.aarrelaakso.drawl;

import java.io.IOException;

/** \mainpage Drawl Documentation
 *
 * \section intro_sec Introduction
 *
 * Drawl is a drawing language. That means that it allows you to represent drawings
 * as programs, then render the programs into images.
 *
 * \section examples_sec Examples
 *
 * As an example, the following code:
 *
 * ~~~{.java}
 * public class Drawl {
 *     public static void main(String[] args) {
 *         System.out.println("Hello World");
 *         Drawing drawing = new Drawing();
 *         Circle circle1 = new Circle();
 *         drawing.add(circle1);
 *         Circle circle2 = new Circle();
 *         drawing.add(circle2);
 *         circle2.setRightOf(circle1);
 *         String svg = drawing.getSVG(200, 100);
 *         System.out.println(svg);
 *     }
 * }
 * ~~~
 *
 * Renders a drawing showing two adjacent circles:
 *
 * <IMG SRC="../example1.svg" ALIGN="left" WIDTH="200" HEIGHT="100" />
 *
 * <BR CLEAR="left" />
 *
 * <P><P>This simple example illustrates the basic idea, but it can be extended to create many beautiful and useful drawings.
 *
 *
 *
 *
 *
 *
 */

public class Drawl {

    /**
     * Main method for integration tests.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Drawing drawing = new Drawing();
        Circle circle1 = new Circle();
        drawing.add(circle1);
        Circle circle2 = new Circle();
        drawing.add(circle2);
        circle2.setRightOf(circle1);
        Circle circle3 = new Circle();
        drawing.add(circle3);
        circle3.setRightOf(circle2);
        String svg = drawing.getSVG(100, 100);
        System.out.println(svg);
        drawing.writeToFile("test.svg");
    }

}
