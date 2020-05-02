/*
 * Copyright (c) 2020. Aarre Laakso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.aarrelaakso.drawl.examples;

import com.aarrelaakso.drawl.Circle;
import com.aarrelaakso.drawl.Drawing;
import com.aarrelaakso.drawl.Shape;

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

public class ThreeCirclesHorizontal
{

    /**
     * Main method for integration tests.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Drawing drawing = new Drawing();
        Shape shape1 = new Circle();
        drawing.add(shape1);
        Shape shape2 = new Circle();
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        Shape shape3 = new Circle();
        drawing.add(shape3);
        shape3.setRightOf(shape2);
        drawing.writeToFile("src/main/java/com/aarrelaakso/drawl/examples/ThreeCirclesHorizontal.svg", 100, 100);
    }

}
