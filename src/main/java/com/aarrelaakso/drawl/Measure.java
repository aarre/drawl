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
 * Defines a public Measure class to represent measures on the diagram without exposing internal numerical
 * measurements to the user.
 */
public class Measure
{
    private final Number length;

    /**
     * Creates a new Measure object from a DrawlNumber.
     *
     * In general, this should be the preferred way of constructing a Measure object, to keep all underlying math as
     * DrawlNumbers, allowing flexibility with the math implementation.
     *
     * @param length The length to associate with this Measure.
     */
    protected Measure(final Number length)
    {
        this.length = length;
    }

    /**
     * Creates a new Measure object from an Integer.
     *
     * Convenience method.
     *
     * @param length The length to associate with this Measure.
     */
    public Measure(@NotNull final Integer length)
    {
        this.length = new DrawlNumber(length);
    }

    /**
     * Converts this Measure to a DrawlNumber.
     *
     * @return A DrawlNumber representing the length of this Meausre.
     */
    protected Number toDrawlNumber()
    {
        return this.length;
    }
}
