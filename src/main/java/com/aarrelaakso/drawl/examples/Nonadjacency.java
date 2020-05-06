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
import com.aarrelaakso.drawl.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Example of using Drawl to draw shapes separated by some distance.
 */
public class Nonadjacency {
    public static void main(String[] args) throws IOException
    {
        @NotNull Drawing drawing = new Drawing();
        @NotNull Circle circle1 = new Circle();
        @NotNull Circle circle2 = new Circle();
        drawing.add(circle1);
        drawing.add(circle2);

        circle2.setRightOf(circle1, circle2.getWidth());

        drawing.writeToFile("src/main/java/com/aarrelaakso/drawl/examples/Nonadjacency.svg", 100, 100);
    }
}
