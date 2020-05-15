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

import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Math.pow;
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

    @NotNull
    private String fillColor = "black";

    private double height = 1.0;

    private double width = 1.0;

    private String uniqueId;

    private LineEnding.Type lineEndingType = Type.DEFAULT;

    private static AtomicLong idCounter = new AtomicLong();

    public static String createID()
    {
        return String.valueOf(idCounter.getAndIncrement());
    }


    /**
     * Drawl defines many types of line endings.
     */
    public enum Type {
        ARC_BARB,

        BOX,
        /*!< A square (box) line ending. Synonyms: SQUARE
        *
        * The area of this line ending is 16, so its sides are of length 4.
        *
        */

        BRACKET,
        BUTT_CAP,

        CIRCLE,
        /*!< A circular line ending. Synonym: OPEN_DOT */
        
        CLASSICAL_TIKZ_RIGHTARROW,
        COMPUTER_MODERN_RIGHTARROW,
        CROW,
        CURVE,
        DEFAULT,
        /*!< The default line ending. Synonyms: NORMAL, TRIANGLE.

        For the default line ending, we set the constraint of having the legs have twice the length of the base.

        * \image html triangle.svg width=300

        The width of this triangle can be calculated as:

        \f[w^2 + (\frac{h}{2})^2 = (2h)^2\f]

        \f[w^2 + \frac{h^2}{4} = 4h^2\f]

        \f[w^2 = 4h^2 - \frac{h^2}{4}\f]

        \f[w^2 = \frac{16h^2}{4} - \frac{h^2}{4}\f]

        \f[w^2 = \frac{15h^2}{4}\f]

        \f[w = \sqrt{\frac{15h^2}{4}}\f]

        \f[w = \frac{h \sqrt{15}}{2}\f]

        To calculate the area of the triangle:

        \f[Area = \frac{hw}{2}\f]

        Given that we want the area to be 16:

        \f[ \frac{hw}{2} = 16\f]

        \f[hw = 32\f]

        Substituting in our formula for w

        \f[h \frac{h \sqrt{15}}{2} = 32\f]

        \f[\frac{h^2 \sqrt{15}}{2} = 32\f]

        \f[h^2 \sqrt{15} = 64\f]

        \f[15 h^4  = 4096\f]

        \f[h^4  = \frac{4096}{15}\f]

        \f[h = \sqrt[4]{\frac{4096}{15}}\f]

        \f[h \approx 4.07\f]

        Then, to calculate the width of the triangle:

        \f[\frac{wh}{2} = 16\f]

        \f[wh = 32\f]

        \f[w = \frac{32}{h}\f]

        \f[w \approx 7.87\f]

        */
        DIAMOND,
        /*!< A diamond-shaped line ending. Synonyms: RHOMBUS
         *
         * \image html rhombus.svg width=300
         *
         * We set the height of the rhombus to be equal to its sides:
         *
         * \f[h = x\f]
         *
         * \f[Area = \frac{w \times h}{2}\f]
         *
         * Solving for width in terms of height:
         *
         * \f[h^2 = (\frac{h}{2})^2 + (\frac{w}{2})^2\f]
         *
         * \f[(\frac{w}{2})^2 = h^2 - (\frac{h}{2})^2\f]
         *
         * \f[\frac{w^2}{4} = h^2 - \frac{h^2}{4}\f]
         *
         * \f[w^2 = 4h^2 - h^2\f]
         *
         * \f[w^2 = 3h^2\f]
         *
         * \f[w = h \sqrt{3}\f]
         *
         * Substituting into the formula for area:
         *
         * \f[16 = \frac{h^2 \sqrt{3}}{2}\f]
         *
         * \f[32 = h^2 \sqrt{3}\f]
         *
         * \f[h^2 = \frac{32}{\sqrt{3}}\f]
         *
         *  \f[h = \frac{\sqrt{32}}{\sqrt[4]{3}}\f]
         *
         *  \f[h \approx 4.3\f]
         *
         * Now calculating w:
         *
         * \f[w = \sqrt{3} \frac{\sqrt{32}}{\sqrt[4]{3}}\f]
         *
         * \f[w \approx 7.44\f]
         */

        DISK,
        /*!< A disk-shaped line ending. Synonyms: DOT           */

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
        ELLIPSE,
        FAST_ROUND,
        FAST_TRIANGLE,
        HOOKS,
        INVERTED,
        /*!< An inverted (reverse) version of the NORMAL line ending. Synonyms REVERSE */
        LATEX,
        KITE,
        NORMAL,
        /*!< The default line ending. Synonyms: DEFAULT, TRIANGLE. */
        OPEN_DIAMOND,
        /*!< As a DIAMOND, but with fill='white' */
        OPEN_DOT,
        /*!< A synonym for CIRCLE */
        OPEN_ELLIPSE,
        PARENTHESIS,
        RAYS,
        RECTANGLE,
        REVERSE,
        /*!< A reverse (inverted) version of the NORMAL line ending. Synonyms: INVERTED */
        RHOMBUS,
        /*!< A synonym for DIAMOND */
        ROUNDED,
        ROUND_CAP,
        SQUARE,
        /*!< A synonym for BOX             */
        STRAIGHT_BARB,

        STEALTH,
        TEE,
        TEE_BARB,
        TRIANGLE,
        /*!< The default line ending. Synonyms: DEFAULT, NORMAL    */
        TRIANGLE_CAP,
        TURNED_SQUARE,
        /*!< A special case of the DIAMOND/RHOMBUS line ending in which the angles are all 90 degrees.

        To get a turned square shape with an area of 16, we set the crossways dimensions at

        \f[4 sqrt(2) \approx 5.66\f]

        */

        VEE

    }

    /**
     * Enumerates the LineEnding types that have open figures.
     */
    public static EnumSet<Type> OpenType = EnumSet.of(Type.CIRCLE, Type.OPEN_DIAMOND, Type.OPEN_DOT);

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
        } else if (this.lineEndingType == Type.TURNED_SQUARE) {
            return Type.TURNED_SQUARE;
        } else if ((this.lineEndingType == Type.DIAMOND) ||
                (this.lineEndingType == Type.RHOMBUS)) {
            return Type.DIAMOND;
        } else if ((this.lineEndingType == Type.DISK) ||
                (this.lineEndingType == Type.DOT)) {
            return Type.DOT;
        } else if ((this.lineEndingType == Type.CIRCLE) ||
                (this.lineEndingType == Type.OPEN_DOT)) {
            return Type.CIRCLE;
        } else if ((this.lineEndingType == Type.REVERSE) ||
                (this.lineEndingType == Type.INVERTED)) {
            return Type.REVERSE;
        } else {
            return this.lineEndingType;
        }
    }

    protected String getSVGDef(double lineWidth) {
        String newLine = System.getProperty("line.separator");
        StringBuilder svg = new StringBuilder();

        svg.append(newLine + "<defs>" + newLine);
        svg.append("<marker id='");
        svg.append(this.getLineEndingType());
        svg.append("-");
        svg.append(this.getUniqueId());
        svg.append("' orient='auto'");
        if (this.getLineEndingType() == Type.TRIANGLE) {
            // See the API documentation for the rationale for these calculations
            double quotient = 4096.0 / 15.0;
            // Take the 4th root of of the quotient
            double height = pow(quotient, 1.0/4.0);                      // approx. 4.07
            double width = 32.0 / height;                                    // approx. 7.87
            width = width / this.getWidth();
            height = height / this.getHeight();
            svg.append(" viewBox='0 0 " + (width + 3) + " " + (height + 2) + "'");
            svg.append(" markerWidth='" + (width + 3) + "' markerHeight='" + (height + 2) + "'");
            svg.append(" refX='" + (width/2.0 + 1) + "' refY='" + (height/2.0 + 1) + "'>" + newLine);
            svg.append("<path d='M1,1 L1," + (height + 1) + " L" + (width + 1) + "," + (height/2.0 + 1) + " z'");
        } else if (this.getLineEndingType() == Type.BOX) {
            svg.append(" viewBox='0 0 6 6' markerWidth='6' markerHeight='6' refX='3' refY='3'>" + newLine);
            svg.append("<path d='M1,1 L1,5 L5,5 L5,1 z'");
        } else if (this.getLineEndingType() == Type.TURNED_SQUARE) {
            double diagonal = 4 * sqrt(2);
            double half_diag = diagonal / 2;
            svg.append(" viewBox='0 0 " + (diagonal + 2) + " " + (diagonal + 2) + "'");
            svg.append(" markerWidth='" + (diagonal + 2) + "' markerHeight='" + (diagonal + 2) + "'");
            svg.append(" refX='" + (half_diag + 1) + "' refY='" + (half_diag + 1) + "'>" + newLine);
            svg.append("<path d='M" + (half_diag + 1) + ",1 L" + (diagonal + 1) + "," + (half_diag + 1) + " L" + (half_diag + 1) + "," + (diagonal + 1) + " L1," + (half_diag + 1) + " z'");
        } else if (this.getLineEndingType() == Type.DOT) {
            double radius = 4 / sqrt( Math.PI );
            double diameter = 2 * radius;
            svg.append(" viewBox='0 0 " + (diameter + 2) + " " + (diameter + 2) +"'");
            svg.append(" markerWidth='" + (diameter + 2) + "' markerHeight='" + (diameter + 2) + "'");
            svg.append(" refX='" + (radius + 1) + "' refY='" + (radius + 1) + "'>" + newLine);
            svg.append("<circle cx='" + (radius + 1) + "' cy='" + (radius + 1) +"' r='" + radius + "'");
        } else if (this.getLineEndingType() == Type.CIRCLE) {
            double radius = 4 / sqrt(Math.PI);
            double diameter = 2 * radius;
            svg.append(" viewBox='0 0 " + (diameter + 2) + " " + (diameter + 2) + "'");
            svg.append(" markerWidth='" + (diameter + 2) + "' markerHeight='" + (diameter + 2) + "'");
            svg.append(" refX='" + (radius + 1) + "' refY='" + (radius + 1) + "'>" + newLine);
            svg.append("<circle cx='" + (radius + 1) + "' cy='" + (radius + 1) + "' r='" + radius + "'");
        } else if (this.getLineEndingType() == Type.DIAMOND) {
            double height = sqrt(32.0) / pow(3.0, 1.0 / 4.0);                       // Approx. 4.3
            double width = sqrt(3.0) * height;                                      // Approx. 7.44
            width = width * this.getWidth();
            height = height * this.getHeight();
            svg.append(" viewBox='0 0 " + (width + 2) + " " + (height + 2) + "'");
            svg.append(" markerWidth='" + (width + 2) + "' markerHeight='" + (height + 2) + "'");
            svg.append(" refX='" + (width/2.0 + 1) + "' refY='" + (height/2.0 + 1) + "'>" + newLine);
            svg.append("<path d='M1," + (height/2.0 + 1) + " L" + (width/2.0 + 1) + "," + (height + 1) + " L" + (width + 1) + "," + (height/2.0 + 1) + " L" + (width/2.0 + 1) + ",1 z'");
        } else if (this.getLineEndingType() == Type.OPEN_DIAMOND) {
            double height = sqrt(32.0) / pow(3.0, 1.0 / 4.0);                       // Approx. 4.3
            double width = sqrt(3.0) * height;                                      // Approx. 7.44
            svg.append(" viewBox='0 0 " + (width + 2) + " " + (height + 2) + "'");
            svg.append(" markerWidth='" + (width + 2) + "' markerHeight='" + (height + 2) + "'");
            svg.append(" refX='" + (width / 2.0 + 1) + "' refY='" + (height / 2.0 + 1) + "'>" + newLine);
            svg.append("<path d='M1," + (height / 2.0 + 1) + " L" + (width / 2.0 + 1) + "," + (height + 1) + " L" + (width + 1) + "," + (height / 2.0 + 1) + "L" + (width / 2.0 + 1) + ",1 z'");
        } else if (this.getLineEndingType() == Type.REVERSE) {
            // See the API documentation for the rationale for these calculations
            double quotient = 4096.0 / 15.0;
            // Take the 4th root of of the quotient
            double height = pow(quotient, 1.0/4.0);                      // approx. 4.07
            double width = 32.0 / height;                                    // approx. 7.87
            width = width * this.getWidth();
            height = height * this.getHeight();
            svg.append(" viewBox='0 0 " + (width + 2) + " " + (height + 2) + "'");
            svg.append(" markerWidth='" + (width + 2) + "' markerHeight='" + (height + 2) + "'");
            svg.append(" refX='" + (width/2.0 + 1) + "' refY='" + (height/2.0 + 1) + "'>" + newLine);
            svg.append("<path d='M1," + (height/2.0 + 1) + "L" + (width+1) + "," + (height+1) + " L" + (width+1) + "," + 1 + " z'");
        }
        else {
            throw new UnsupportedOperationException("Unknown line ending type: " + this.getLineEndingType());
        }

        svg.append(" stroke='black'");

        // Fill color
        svg.append(" fill='");
        if ((this.getLineEndingType()==Type.CIRCLE) ||
                (this.getLineEndingType() == Type.OPEN_DIAMOND)){
            this.fillColor = "white";
        }
        svg.append(fillColor);

        // Closing tags
        svg.append("' />" + newLine);
        svg.append("</marker>" + newLine);
        svg.append("</defs>" + newLine);

        return svg.toString();
    }

    public void setFill(String fillColor) {
        this.fillColor = fillColor;
    }

    double getHeight() {
        return this.height;
    }

    String getUniqueId() {
        if (this.uniqueId == null) {
            this.uniqueId = createID();
        }
        return this.uniqueId;
    }

    double getWidth() {
        return this.width;
    }

    /**
     * Sets the size of this LineEnding as a proportion of the default size.
     *
     * @param size the proportion of the default size to which to set the size of this LineEnding
     */
    public void setSize(double size) {
        this.height = size;
        this.width = size;
    }
}
