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
 * Defines the Arrowhead class.
 */

package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;

import static java.lang.Math.sqrt;

/**
 * Represents heads or tails on the ends of Lines.
 */
public class Arrowhead {

    private Arrowhead.Type arrowheadType = Type.DEFAULT;

    /**
     * Defines the different types of arrowheads that are available.
     */
    public enum Type {
        DEFAULT,                           /*!< The default arrowhead. Synonyms: NORMAL, TRIANGLE.  */
        NORMAL,                            /*!< The default arrowhead. Synonyms: DEFAULT, TRIANGLE. */
        TRIANGLE,                          /*!< The default arrowhead. Synonyms: DEFAULT, NORMAL    */
        BOX,                               /*!< A square (box) arrowhead. Synonyms: SQUARE          */
        SQUARE,                            /*!< A square (box) arrowhead. Synonyms: BOX             */
        DIAMOND,                           /*!< A diamond-shaped arrowhead. Synonyms: TURNED_SQUARE */
        TURNED_SQUARE,                     /*!< A diamond-shaped arrowhead. Synonyms: DIAMOND       */
        OPEN_DIAMOND,
        CROW,
        DOT,                               /*!< A dot-shaped arrowhead. Synonyms: DISK             */
        DISK,                              /*!< A disk-shaped line ending. Synonyms: DOT           */
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

    // TODO: Need to figure out how to do inverted (outline) arrows

    /**
     * Returns the canonical representation of this Arrowhead type.
     *
     * For example, this function returns Type.TRIANGLE if the Arrowhead type is Type.DEFAULT, Type.NORMAL, or
     * Type.TRIANGLE.
     *
     * @return the canonical representation of this Arrowhead type.
     */
    protected Type getArrowheadType() {
        if ((this.arrowheadType == Type.DEFAULT) ||
                (this.arrowheadType == Type.NORMAL) ||
                (this.arrowheadType == Type.TRIANGLE)) {
            return Type.TRIANGLE;
        } else if ((this.arrowheadType == Type.BOX) ||
                (this.arrowheadType == Type.SQUARE)) {
            return Type.BOX;
        } else if ((this.arrowheadType == Type.DIAMOND) ||
                (this.arrowheadType == Type.TURNED_SQUARE)) {
            return Type.DIAMOND;
        } else if ((this.arrowheadType == Type.DISK) ||
                (this.arrowheadType == Type.DOT)) {
            return Type.DOT;
        } else {
            throw new UnsupportedOperationException("Unknown arrowhead type: " + this.arrowheadType);
        }
    }

    protected String getSVGDef() {
        String newLine = System.getProperty("line.separator");
        StringBuilder svg = new StringBuilder();

        svg.append("<defs>" + newLine);
        svg.append("<marker id='");
        svg.append(this.getArrowheadType());
        svg.append("' orient='auto'");
        if (this.getArrowheadType() == Type.TRIANGLE) {
            // See the User's Guide for the rationale for these calculations
            double quotient = 4096.0 / 15.0;
            // Take the 4th root of of the quotient
            double width = Math.pow(quotient, 1.0/4.0);                      // approx. 4.07
            double height = 32.0 / width;                                    // approx. 7.87
            svg.append(" viewBox='0 0 " + height + " " + width + "'");
            svg.append(" markerWidth='" + height + "' markerHeight='" + width + "'");
            svg.append(" refX='" + height/2.0 + "' refY='" + width/2.0 + "'>" + newLine);
            svg.append("<path d='M0,0 L0," + width + " L" + height + "," + width/2.0 + " z' fill='red' />" + newLine);
        } else if (this.getArrowheadType() == Type.BOX) {
            svg.append(" viewBox='0 0 4 4' markerWidth='4' markerHeight='4' refX='2' refY='2'>" + newLine);
            svg.append("<path d='M0,0 L0,4 L4,4 L4,0 z' fill='red' />" + newLine);
        } else if (this.getArrowheadType() == Type.DIAMOND) {
            double diagonal = 4 * sqrt(2);
            double half_diag = diagonal / 2;
            svg.append(" viewBox='0 0 " + diagonal + " " + diagonal + "'");
            svg.append(" markerWidth='" + diagonal + "' markerHeight='" + diagonal + "'");
            svg.append(" refX='" + diagonal/2 + "' refY='" + diagonal/2 + "'>" + newLine);
            svg.append("<path d='M" + half_diag + ",0 L" + diagonal + "," + half_diag + " L" + half_diag + "," + diagonal + " L0," + half_diag + " z' fill='red' />" + newLine);
        } else if (this.getArrowheadType() == Type.DOT) {
            double radius = 4 / sqrt( Math.PI );
            double diameter = 2 * radius;
            svg.append(" viewBox='0 0 " + diameter + " " + diameter +"'");
            svg.append(" markerWidth='" + diameter + "' markerHeight='" + diameter + "'");
            svg.append(" refX='" + radius + "' refY='" + radius + "'>" + newLine);
            svg.append("<circle cx='" + radius + "' cy='" + radius +"' r='" + radius + "' fill='red' />" + newLine);
        } else {
            throw new UnsupportedOperationException("Unknown arrowhead type: " + this.getArrowheadType());
        }
        svg.append("</marker>" + newLine);
        svg.append("</defs>" + newLine);

        return svg.toString();
    }
}
