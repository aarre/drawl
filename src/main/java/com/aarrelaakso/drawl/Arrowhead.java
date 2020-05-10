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
 * Represents heads or tails on the ends of Line
 */
public class Arrowhead {

    private Arrowhead.Type arrowheadType = Type.DEFAULT;

    public enum Type {
        DEFAULT, NORMAL, /*!< The default arrowhead, also known as NORMAL */
        TRIANGLE,
        BOX, SQUARE,
        TURNED_SQUARE, DIAMOND, OPEN_DIAMOND,
        CROW,
        DOT, DISK,
        CIRCLE, ODOT,
        INVERTED,
        TEE,
        VEE,
        CURVE,
        PARENTHESIS,
        ARC_BARB,
        ROUNDED,
        RAYS,
        BRACKET,
        HOOKS,
        STRAIGHT_BARB,
        TEE_BARB,
        CLASSICAL_TIKZ_RIGHTARROW,
        COMPUTER_MODERN_RIGHTARROW,
        ELLIPSE, OPEN_ELLIPSE,
        KITE,
        LATEX,
        RECTANGLE,
        STEALTH,
        BUTT_CAP,
        FAST_ROUND,
        FAST_TRIANGLE,
        ROUND_CAP,
        TRIANGLE_CAP
    }

    /**
     * Constructs a default arrowhead.
     *
     */
    public Arrowhead()
    {

    }

    /**
     * Constructs an arrowhead of a particular type.
     *
     * @param type
     */
    public Arrowhead(@NotNull Arrowhead.Type type)
    {
        this.arrowheadType = type;
    }

    // TODO: Need a new constructor (or method/s) to allow for half arrows (right, left)

    // TODO: Need a new constructor (or method/s) to allow for multiple arrows


    protected String getSVGDef() {
        String newLine = System.getProperty("line.separator");
        StringBuilder svg = new StringBuilder();

        svg.append("<defs>" + newLine);
        svg.append("<marker id='");
        svg.append(this.arrowheadType);
        svg.append("' orient='auto' markerWidth='2' markerHeight='4' refX='0.1' refY='2'>" + newLine);
        svg.append("<path d='M0,0 V4 L2,2 Z' fill='red' />" + newLine);
        svg.append("</marker>" + newLine);
        svg.append("</defs>" + newLine);

        return svg.toString();
    }
}
