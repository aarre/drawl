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
import com.aarrelaakso.drawl.Line;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Demonstrates using Drawl to draw a line between two shapes.
 */
public class LineExample {

    /**
     * Draws a line between two shapes.
     *
     * @param args no command line arguments.
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException
    {
        @NotNull final Drawing drawing = new Drawing();
        @NotNull final Circle circle1 = new Circle();
        @NotNull final Circle circle2 = new Circle();
        drawing.add(circle1);
        drawing.add(circle2);
        circle1.setFill("green");
        circle2.setFill("blue");

        circle2.setRightOf(circle1, circle2.getWidth());

        @NotNull final Line line = new Line(circle1.getRightPort(), circle2.getLeftPort());
        line.setStroke("red");
        drawing.add(line);

        drawing.writeToFile("src/main/java/com/aarrelaakso/drawl/examples/LineExample.svg", 100, 100);
    }
}
