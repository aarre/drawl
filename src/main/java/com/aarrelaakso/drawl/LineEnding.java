/*
 * Copyright (c) 2020. Aarre Laakso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/** \file
 * Defines the line ending class.
 */

package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;

import static java.lang.Math.sqrt;

/**
 * Represents heads or tails on the ends of Lines.
 *
 * One of the design goals in creating line endings for Drawl was to make the line ending shapes subsume the same
 * area at their default sizes. This makes the various line endings subsume approximately the same
 * visual weight. It means, however, that they do not all share a common dimension, either width or height.
 *
 * We take as default a square with sides of length 4, which sets the area at 16.
 */
public class LineEnding {

    private LineEnding.Type lineEndingType = Type.DEFAULT;

    /**
     * Drawl defines many types of line endings.
     */
    public enum Type {
        DEFAULT,
        /*!< The default line ending. Synonyms: NORMAL, TRIANGLE.

        For the default line ending, we set the constraint of having the legs have twice the length of the base.

                  /\
                 /  \
                /    \  2x
               /      \
               --------
                  x

        Half of this triangle is:

                |\
                | \
               h|  \  2x
                |   \
                ------
                 x/2

        The height of this triangle can be calculated as:

        \f[h^2 + (\frac{x}{2})^2 = (2x)^2\f]

        \f[h^2 + \frac{x^2}{4} = 4x^2\f]

        \f[h^2 = 4x^2 - \frac{x^2}{4}\f]

        \f[h^2 = \frac{16x^2}{4} - \frac{x^2}{4}\f]

        \f[h^2 = \frac{15x^2}{4}\f]

        \f[h = \sqrt{\frac{15x^2}{4}}\f]

        \f[h = \frac{x \sqrt{15}}{2}\f]

        \f[\frac{xh}{2}\f]

        Given that we want the area to be 16:

        \f[ \frac{xh}{2} = 16\f]

        \f[xh = 32\f]

        \f[x \frac{x \sqrt{15}}{2} = 32\f]

        \f[\frac{x^2 \sqrt{15}}{2} = 32\f]

        \f[ x^2 \sqrt{15} = 64\f]

        \f[15 x^4  = 4096\f]

        \f[x^4  = \frac{4096}{15}\f]

        \f[x = \sqrt[4]{\frac{4096}{15}}\f]

        \f[x \approx 4.07\f]

        Then, to calculate the height of the triangle h:

        \f[\frac{xh}{2} = 16\f]

        \f[xh = 32\f]

        \f[h = \frac{32}{x}\f]

        \f[h \approx 7.87\f]

        */
        NORMAL,
        /*!< The default line ending. Synonyms: DEFAULT, TRIANGLE. */

        TRIANGLE,
        /*!< The default line ending. Synonyms: DEFAULT, NORMAL    */

        BOX,
        /*!< A square (box) line ending. Synonyms: SQUARE

        The area of this line ending is 16, so its sides are of length 4.

        */

        SQUARE,
        /*!< A square (box) line ending. Synonyms: BOX             */

        DIAMOND,
        /*!< A diamond-shaped line ending. Synonyms: TURNED_SQUARE

        To get a diamond shape with an area of 16, we set the crossways dimensions at

        \f[4 sqrt(2) \approx 5.66\f]

        */
        TURNED_SQUARE,
        /*!< A diamond-shaped line ending. Synonyms: DIAMOND       */

        OPEN_DIAMOND,



        DOT,
        /*!< A dot-shaped line ending. Synonyms: DISK

        For the dot/disk line ending, we need

        \f[\pi r^2 = 16\f]

        so

        \f[r^2 = 16/\pi\f]

        \f[r = \sqrt{\frac{16}{\pi}}\f]

        \f[r = \frac{4}{\sqrt \pi}\f]

        \f[r \approx 2.26\f]

        */

        DISK,
        /*!< A disk-shaped line ending. Synonyms: DOT           */

        CIRCLE, ODOT,
        CROW,
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
     * Constructs a default line ending.
     *
     */
    public LineEnding()
    {

    }

    /**
     * Constructs an line ending of a particular type.
     *
     * @param type
     */
    public LineEnding(@NotNull LineEnding.Type type)
    {
        this.lineEndingType = type;
    }

    // TODO: Need a new constructor (or method/s) to allow for half arrows (right, left)

    // TODO: Need a new constructor (or method/s) to allow for multiple arrows

    // TODO: Need to figure out how to do inverted (outline) arrows

    // TODO: Reverse/inverse arrows

    /**
     * Returns the canonical representation of this line ending type.
     *
     * For example, this function returns Type.TRIANGLE if the line ending type is Type.DEFAULT, Type.NORMAL, or
     * Type.TRIANGLE.
     *
     * @return the canonical representation of this line ending type.
     */
    protected Type getLineEndingType() {
        if ((this.lineEndingType == Type.DEFAULT) ||
                (this.lineEndingType == Type.NORMAL) ||
                (this.lineEndingType == Type.TRIANGLE)) {
            return Type.TRIANGLE;
        } else if ((this.lineEndingType == Type.BOX) ||
                (this.lineEndingType == Type.SQUARE)) {
            return Type.BOX;
        } else if ((this.lineEndingType == Type.DIAMOND) ||
                (this.lineEndingType == Type.TURNED_SQUARE)) {
            return Type.DIAMOND;
        } else if ((this.lineEndingType == Type.DISK) ||
                (this.lineEndingType == Type.DOT)) {
            return Type.DOT;
        } else if ((this.lineEndingType == Type.CIRCLE) ||
                (this.lineEndingType == Type.ODOT)) {
            return Type.CIRCLE;
        } else {
            throw new UnsupportedOperationException("Unknown line ending type: " + this.lineEndingType);
        }
    }

    protected String getSVGDef() {
        String newLine = System.getProperty("line.separator");
        StringBuilder svg = new StringBuilder();

        svg.append("<defs>" + newLine);
        svg.append("<marker id='");
        svg.append(this.getLineEndingType());
        svg.append("' orient='auto'");
        if (this.getLineEndingType() == Type.TRIANGLE) {
            // See the User's Guide for the rationale for these calculations
            double quotient = 4096.0 / 15.0;
            // Take the 4th root of of the quotient
            double width = Math.pow(quotient, 1.0/4.0);                      // approx. 4.07
            double height = 32.0 / width;                                    // approx. 7.87
            svg.append(" viewBox='0 0 " + height + " " + width + "'");
            svg.append(" markerWidth='" + height + "' markerHeight='" + width + "'");
            svg.append(" refX='" + height/2.0 + "' refY='" + width/2.0 + "'>" + newLine);
            svg.append("<path d='M0,0 L0," + width + " L" + height + "," + width/2.0 + " z' stroke='black' fill='black' />" + newLine);
        } else if (this.getLineEndingType() == Type.BOX) {
            svg.append(" viewBox='0 0 4 4' markerWidth='4' markerHeight='4' refX='2' refY='2'>" + newLine);
            svg.append("<path d='M0,0 L0,4 L4,4 L4,0 z' stroke='black' fill='black' />" + newLine);
        } else if (this.getLineEndingType() == Type.DIAMOND) {
            double diagonal = 4 * sqrt(2);
            double half_diag = diagonal / 2;
            svg.append(" viewBox='0 0 " + diagonal + " " + diagonal + "'");
            svg.append(" markerWidth='" + diagonal + "' markerHeight='" + diagonal + "'");
            svg.append(" refX='" + diagonal/2 + "' refY='" + diagonal/2 + "'>" + newLine);
            svg.append("<path d='M" + half_diag + ",0 L" + diagonal + "," + half_diag + " L" + half_diag + "," + diagonal + " L0," + half_diag + " z' stroke='black' fill='black' />" + newLine);
        } else if (this.getLineEndingType() == Type.DOT) {
            double radius = 4 / sqrt( Math.PI );
            double diameter = 2 * radius;
            svg.append(" viewBox='0 0 " + diameter + " " + diameter +"'");
            svg.append(" markerWidth='" + diameter + "' markerHeight='" + diameter + "'");
            svg.append(" refX='" + radius + "' refY='" + radius + "'>" + newLine);
            svg.append("<circle cx='" + radius + "' cy='" + radius +"' r='" + radius + "' stroke='black' fill='black' />" + newLine);
        } else if (this.getLineEndingType() == Type.CIRCLE) {
            double radius = 4 / sqrt( Math.PI );
            double diameter = 2 * radius;
            svg.append(" viewBox='0 0 " + diameter + " " + diameter +"'");
            svg.append(" markerWidth='" + diameter + "' markerHeight='" + diameter + "'");
            svg.append(" refX='" + radius + "' refY='" + radius + "'>" + newLine);
            svg.append("<circle cx='" + radius + "' cy='" + radius +"' r='" + radius + "' stroke='black' fill='white' />" + newLine);
        } else {
            throw new UnsupportedOperationException("Unknown line ending type: " + this.getLineEndingType());
        }
        svg.append("</marker>" + newLine);
        svg.append("</defs>" + newLine);

        return svg.toString();
    }
}
