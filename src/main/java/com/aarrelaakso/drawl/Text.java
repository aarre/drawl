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
import org.jetbrains.annotations.Nullable;

/**
 * Represents text on a drawing.
 */
public class Text extends Shape
{
    /**
     * The string associated with this Text object. Defaults to <code>null</code>, meaning that no string has been
     * associated with this Text object.
     */
    @Nullable private String string;

    /**
     * Constructs a default Text object.
     */
    public Text()
    {

    }

    /**
     * Constructs a Text object with some text.
     *
     * @param string the text to associate with the new Text object.
     */
    public Text(@NotNull final String string)
    {
        this.string = string;
    }

    /**
     * Gets the SVG associated with this Text object.
     *
     * @return a string of SVG that represents the text associated with this Text object.
     */
    @Override
    @NotNull
    public String getSVG() {
        if (this.getExplicitWidth() == null || this.getExplicitHeight() == null)
        {
            throw new UnsupportedOperationException("Cannot get SVG without setting explicit dimensions");
        }
        if (this.toString() != null)
        {
            @NotNull final StringBuilder svgBuilder = new StringBuilder();
            svgBuilder.append("<text");
            svgBuilder.append(" x='");
            svgBuilder.append(this.getExplicitXPositionCenter().toSVG());
            svgBuilder.append("'");
            svgBuilder.append(" y='");
            svgBuilder.append(this.getExplicitYPositionCenter().toSVG());
            svgBuilder.append("'");
            svgBuilder.append(" dominant-baseline='middle' text-anchor='middle'");
            if (this.getStroke() != null)
            {
                svgBuilder.append(" stroke='");
                svgBuilder.append(this.getStroke());
                svgBuilder.append("'");
            }
            if (this.getFill() != null)
            {
                svgBuilder.append(" fill='");
                svgBuilder.append(this.getFill());
                svgBuilder.append("'");
            }
            svgBuilder.append(">");
            svgBuilder.append(this);
            svgBuilder.append("</text>");
            if (this.hasText())
            {
                svgBuilder.append(this.getText().getSVG());
            }
            return svgBuilder.toString();
        }
        else
        {
            return "";
        }
    }

    /**
     * Sets the string associated with this Text object.
     *
     * @param string The string to associate with this Text object. Use <code>null</code> to indicate that no string is
     *               associated with this Text object.
     */
    public void setString(@Nullable final String string)
    {
        this.string = string;
    }

    /**
     * Gets the text string associated with this Text object.
     *
     * @return the text string associated with this Text object, or <code>null</code> if there is no text string
     * associated with this Text object.
     */
    @Nullable
    public String toString()
    {
        return this.string;
    }
}
