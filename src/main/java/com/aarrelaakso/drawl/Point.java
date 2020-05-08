/*
 * Copyright (c) 2020. Aarre Laakso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a point on the drawing canvas.
 */
public class Point {

    private final Number xCoordinate;
    private final Number yCoordinate;

    /**
     * Constructs a new Point object.
     *
     * @param xCoordinate
     * @param yCoordinate
     */
    protected Point(final Number xCoordinate, final Number yCoordinate)
    {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    protected Point(@NotNull final Integer xCoordinate, @NotNull final Integer yCoordinate)
    {
        this.xCoordinate = new DrawlNumber(xCoordinate);
        this.yCoordinate = new DrawlNumber(yCoordinate);
    }

    protected Number getX()
    {
        return this.xCoordinate;
    }


    protected Number getY()
    {
        return this.yCoordinate;
    }

}
