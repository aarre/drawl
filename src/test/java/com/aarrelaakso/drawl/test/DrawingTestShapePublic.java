/*
 * Copyright (c) 2020. Aarre Laakso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.Drawing;
import com.aarrelaakso.drawl.Shape;
import com.google.common.flogger.FluentLogger;
import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SoftAssertionsExtension.class)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@DisplayName("Unit tests of Drawing - Shape (abstract)")
public abstract class DrawingTestShapePublic
{

    private static final FluentLogger logger;

    static
    {
        logger = FluentLogger.forEnclosingClass();

    }

    Drawing drawing;
    Shape shape1;
    Shape shape2;
    Shape shape3;

    @BeforeEach
    void givenADrawing()
    {
        drawing = new Drawing();
    }

    @Test
    @DisplayName("LENGTH: Adding a circle to an empty drawing gives the drawing a length of 1")
    void lengthWhenADrawingHasOneShapeThenItsLengthIs1()
    {
        drawing.add(shape1);
        assertEquals(new Integer(1), drawing.length());
    }

    @Test
    @DisplayName("SVG: Calling getSVG without parameters does not throw an exception")
    void svgWhenYouCallSVGWithoutParametersItDoesNotThrowAnException()
    {
        assertDoesNotThrow(() -> {
            String svg = drawing.getSVG();
        });
    }

    /**
     * Tests applying fill to Shapes
     */
    @Nested
    @DisplayName("Fill")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class Fill
    {

        @Test
        @Tag("fill")
        @Tag("svg")
        @Tag("public")
        @DisplayName("When the fill is not set, then it does not appear in the SVG")
        void whenTheFillIsNotSetThenItDoesNotAppearInTheSVG(BDDSoftAssertions softly)
        {
            drawing.add(shape1);
            String svg = drawing.getSVG(100, 100);
            softly.then(svg).doesNotContain("fill");
            softly.then(svg).doesNotContain("darkslategray");
        }

        @Test
        @Tag("fill")
        @Tag("svg")
        @Tag("public")
        @DisplayName("When the fill is set, then it appears in the SVG")
        void whenTheFillIsSetThenItAppearsInTheSVG(BDDSoftAssertions softly)
        {
            drawing.add(shape1);
            shape1.setFill("darkslategray");
            String svg = drawing.getSVG(100, 100);
            softly.then(svg).contains("fill");
            softly.then(svg).contains("darkslategray");
        }
    }

    /**
     * Tests applying stroke to Shapes
     */
    @Nested
    @DisplayName("Stroke")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class Stroke
    {


        @Test
        @Tag("stroke")
        @Tag("svg")
        @Tag("public")
        @DisplayName("When the stroke is not set, then it does not appear in the SVG")
        void whenTheStrokeIsNotSetThenItDoesNotAppearInTheSVG(BDDSoftAssertions softly)
        {
            drawing.add(shape1);
            String svg = drawing.getSVG(100, 100);
            softly.then(svg).doesNotContain("stroke");
            softly.then(svg).doesNotContain("darkslategray");
        }

        @Test
        @Tag("stroke")
        @Tag("svg")
        @Tag("public")
        @DisplayName("When the stroke is set, then it appears in the SVG")
        void whenTheStrokeIsSetThenItAppearsInTheSVG(BDDSoftAssertions softly)
        {
            drawing.add(shape1);
            shape1.setStroke("darkslategray");
            String svg = drawing.getSVG(100, 100);
            softly.then(svg).contains("stroke");
            softly.then(svg).contains("darkslategray");
        }

    }

}
