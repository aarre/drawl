/*
 * Copyright (c) 2020. Aarre Laakso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/**
 * \file
 * Defines the line ending class.
 */

package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Represents heads or tails on the ends of Lines.
 *
 * One of the design goals in creating line endings for Drawl was to make the line ending shapes subsume the same
 * area at their default sizes. This makes the various line endings subsume approximately the same
 * visual weight. It means, however, that they do not all share a common dimension, either width or height.
 *
 * We take as default a square with sides of length 4, which sets the area at 16.
 */
public class LineEnding implements LineEndingInterface {

    protected static final String newLine = System.getProperty("line.separator");
    private static final AtomicLong idCounter = new AtomicLong();
    /**
     * Enumerates the LineEnding types that have open figures.
     */
    public static EnumSet<Type> OpenType = EnumSet.of(Type.CIRCLE, Type.OPEN_DIAMOND, Type.OPEN_DOT);
    @NotNull
    private String fillColor = "black";
    private double height = 1.0;
    private double width = 1.0;
    private String uniqueId;
    private LineEnding.Type lineEndingType = Type.DEFAULT;


    /**
     * Constructs a default line ending.
     */
    public LineEnding() {

    }

    /**
     * Constructs a line ending of a particular type.
     *
     * @param type
     */
    public LineEnding(@NotNull LineEnding.Type type) {
        this.lineEndingType = type;
    }

    public static String createID() {
        return String.valueOf(idCounter.getAndIncrement());
    }

    /**
     * Returns a new instance of the default LineEnding subclass (a triangle).
     */
    public static LineEnding newInstance() {
        return newInstance(Type.TRIANGLE);
    }

    /**
     * Returns a new instance of a LineEnding subclass.
     *
     * This static factory method is the preferred way to create a LineEnding.
     */
    public static LineEnding newInstance(LineEnding.Type type) {
        switch (type) {
            case DEFAULT:
            case NORMAL:
            case TRIANGLE:
                return new LineEndingTriangle();
            case BAR:
            case TEE:
                return new LineEndingBar();
            case BOX:
            case SQUARE:
                return new LineEndingSquare();
            case TURNED_SQUARE:
                return new LineEndingTurnedSquare();
            case DIAMOND:
            case RHOMBUS:
                return new LineEndingDiamond();
            case OPEN_DIAMOND:
                return new LineEndingOpenDiamond();
            case DISK:
            case DOT:
                return new LineEndingDot();
            case CIRCLE:
            case OPEN_DOT:
                return new LineEndingCircle();
            case REVERSE:
            case INVERTED:
                return new LineEndingReverse();
            case RECTANGLE:
                return new LineEndingRectangle();
            case CROW:
            case STEALTH:
                return new LineEndingStealth();
            case KITE:
                return new LineEndingKite();
            default:
                throw new UnsupportedOperationException("Unknown type: " + type);
        }
    }

    protected double getHeight() {
        return this.height;
    }

    // TODO: Need a new constructor (or method/s) to allow for half arrows (right, left)

    // TODO: Need a new constructor (or method/s) to allow for multiple arrows

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
        } else if ((this.lineEndingType == Type.BAR) ||
                (this.lineEndingType == Type.TEE)) {
            return Type.TEE;
        } else {
            return this.lineEndingType;
        }
    }

    /**
     * It is up to subclasses to override this method.
     *
     * @return
     */
    protected String getSVG() {
        return "Override me!";
    }

    protected String getSVGDef(double lineWidth) {

        StringBuilder svg = new StringBuilder();

        svg.append(newLine + "<defs>" + newLine);
        svg.append("<marker id='");
        svg.append(this.getLineEndingType());
        svg.append("-");
        svg.append(this.getUniqueId());
        svg.append("' orient='auto'");
        svg.append(getSVG());
        if ((this.getLineEndingType() != Type.BAR) &&
        (this.getLineEndingType() != Type.STEALTH)){
            svg.append(" stroke='black'");
        }

        // Fill color
        svg.append(" fill='");
        if ((this.getLineEndingType() == Type.CIRCLE) ||
                (this.getLineEndingType() == Type.OPEN_DIAMOND)) {
            this.fillColor = "white";
        }
        svg.append(fillColor);

        // Closing tags
        svg.append("' />" + newLine);
        svg.append("</marker>" + newLine);
        svg.append("</defs>" + newLine);

        return svg.toString();
    }

    String getUniqueId() {
        if (this.uniqueId == null) {
            this.uniqueId = createID();
        }
        return this.uniqueId;
    }

    protected double getWidth() {
        return this.width;
    }

    public void setFill(String fillColor) {
        this.fillColor = fillColor;
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

    /**
     * Drawl defines many types of line endings.
     */
    public enum Type {
        //ARC_BARB,
        BAR,              /*!< A simple bar at the end of the line                                         */
        BOX,              /*!< A square (box) line ending. Synonyms: SQUARE */
        //BRACKET,          /*!< A line ending that looks like a square bracket at the end of the line.       */
        //BUTT_CAP,
        CIRCLE,           /*!< A circular line ending. Synonym: OPEN_DOT */
        //CLASSICAL_TIKZ_RIGHTARROW,
        //COMPUTER_MODERN_RIGHTARROW,
        CROW,              /*!< A synonym for STEALTH                                                       */
        //CURVE,
        DEFAULT,           /*!< The default line ending. Synonyms: NORMAL, TRIANGLE.                        */
        DIAMOND,           /*!< A diamond-shaped line ending. Synonyms: RHOMBUS                             */
        DISK,              /*!< A disk-shaped line ending. Synonyms: DOT                                    */
        DOT,               /*!< A dot-shaped line ending. Synonyms: DISK                                    */
        //ELLIPSE,
        //FAST_ROUND,
        //FAST_TRIANGLE,
        //HOOKS,
        INVERTED,           /*!< An inverted (reverse) version of the NORMAL line ending. Synonyms REVERSE  */
        //LATEX,
        KITE,
        NORMAL,             /*!< The default line ending. Synonyms: DEFAULT, TRIANGLE.                      */
        OPEN_DIAMOND,       /*!< As a DIAMOND, but with fill='white' */
        OPEN_DOT,           /*!< A synonym for CIRCLE */
        //OPEN_ELLIPSE,
        //PARENTHESIS,
        //RAYS,
        RECTANGLE,          /*!< A rectangle is a generalization of square that has different width and height */
        REVERSE,            /*!< A reverse (inverted) version of the NORMAL line ending. Synonyms: INVERTED */
        RHOMBUS,            /*!< A synonym for DIAMOND */
        //ROUNDED,
        //ROUND_CAP,
        SQUARE,             /*!< A synonym for BOX             */
        //STRAIGHT_BARB,
        STEALTH,            /*!< A synonym for CROW.                                                        */
        TEE,                /*!< A synonym for BAR.                                                         */
        //TEE_BARB,
        TRIANGLE,           /*!< The default line ending. Synonyms: DEFAULT, NORMAL                         */
        //TRIANGLE_CAP,
        TURNED_SQUARE,      /*!< A special case of the DIAMOND/RHOMBUS line ending in which the angles are all 90 degrees.*/
        //VEE
    }
}
