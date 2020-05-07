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
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * Example of using Drawl to draw a series of shaded rectangles.
 */
public class ShadedRectangles
{
    public static void main(final String[] args) throws IOException
    {
        for (int k = 0; k < 20; k++)
        {
            @NotNull final Drawing drawing = new Drawing();
            @Nullable Rectangle currentRectangle = null;
            @Nullable Rectangle lastRectangle = null;
            final int MAXIMUM_RECTANGLES = 1000;

            for (int i = 0; i < MAXIMUM_RECTANGLES; i++)
            {
                currentRectangle = new Rectangle(0.01);
                final float lightness = i * 100 / MAXIMUM_RECTANGLES;
                currentRectangle.setFill("hsl(0, 100%, " + lightness + "%)");
                drawing.add(currentRectangle);
                if (lastRectangle != null)
                {
                    currentRectangle.setRightOf(lastRectangle);
                }
                lastRectangle = currentRectangle;
            }
            drawing.writeToFile("src/main/java/com/aarrelaakso/drawl/examples/ShadedRectangles.svg", 1000, 100);
        }
    }
}
