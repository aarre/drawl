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

    private Number xCoordinate;
    private Number yCoordinate;

    /**
     * Constructs a new Point object.
     *
     * @param xCoordinate
     * @param yCoordinate
     */
    protected Point(final Number xCoordinate, final Number yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    protected Point(@NotNull final Integer xCoordinate, @NotNull final Integer yCoordinate) {
        this.xCoordinate = new DrawlNumber(xCoordinate);
        this.yCoordinate = new DrawlNumber(yCoordinate);
    }

    /**
     * Gets the x coordinate of this Point.
     *
     * @return
     * @author Aarre Laakso
     * @version 1, 05/08/2020
     * @since 05/08/2020
     */
    protected Number getX() {
        return this.xCoordinate;
    }

    /**
     * Sets the x coordinate of this Point.
     *
     * @param x the new x coordinate of this Point.
     * @author Aarre Laakso
     * @version 1, 05/08/2020
     * @since 05/08/2020
     */
    protected void setX(final Number x) {
        this.xCoordinate = x;
    }

    /**
     * Gets the y coordinate of this Point.
     *
     * @return
     * @author Aarre Laakso
     * @version 1, 05/08/2020
     * @since 05/08/2020
     */
    protected Number getY() {
        return this.yCoordinate;
    }

    /**
     * Sets the y coordinate of this Point.
     *
     * @param y the new y coordinate of this Point.
     * @author Aarre Laakso
     * @version 1, 05/08/2020
     * @since 05/08/2020
     */
    void setY(final Number y) {
        this.yCoordinate = y;
    }
}
