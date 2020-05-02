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

import com.aarrelaakso.drawl.Circle;
import com.aarrelaakso.drawl.Drawing;
import com.aarrelaakso.drawl.Shape;
import com.google.common.flogger.FluentLogger;
import org.assertj.core.api.BDDSoftAssertions;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@DisplayName("Unit tests of Drawing with Circle shapes")
public class DrawingTestCirclePublic extends DrawingTestShapePublic
{

    private static final FluentLogger logger =  FluentLogger.forEnclosingClass();

    @BeforeEach
    @DisplayName("Given three default Circles")
    void givenTheeDefaultCircles()
    {
        shape1 = new Circle();
        shape2 = new Circle();
        shape3 = new Circle();
    }


    @Test
    @DisplayName("SVG: The SVG generated by a drawing with a Circle is exactly as expected")
    void svgGeneratedByADrawingWithACircleIsExactlyAsExpected()
    {
        drawing.add(shape1);
        String svg = drawing.getSVG(100, 100);

        then(svg).isEqualTo("<?xml version=\"1.0\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"100\" height=\"100\"><circle r=\"50\" cx=\"50\" cy=\"50\" /></svg>");
    }


    @Test
    @DisplayName("When a default Circle is the only content of a drawing, then it is as large as possible")
    void svgWhenADefaultCircleIsTheOnlyContentOfADrawingThenItIsAsLargeAsPossible()
    {
        drawing.add(shape1);
        String svg = drawing.getSVG(100, 100);

        assertTrue(svg.contains("circle r=\"50\""));
    }

    @Test
    @DisplayName("SVG: When a square (100) drawing has two adjacent Circles, then the SVG is correct")
    void svgWhenASquare100DrawingHasTwoAdjacentCirclesThenTheSVGIsCorrect(BDDSoftAssertions softly)
    {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        String svg = drawing.getSVG(100, 100);

        softly.then(svg).contains("<?xml version=\"1.0\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"100\" height=\"100\">")
                .contains("<circle r=\"25\" cx=\"25\" cy=\"50\" />")
                .contains("<circle r=\"25\" cx=\"75\" cy=\"50\" />")
                .contains("</svg>");
    }

    /**
     * Tests the SVG by Drawings with Circles
     */
    @Nested
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    @DisplayName("Unit tests of the SVG generated by Drawings with Circle shapes")
    class SVG
    {
        @Test
        @DisplayName("When a drawing has three horizontally adjacent default circles, then the generated SVG is correct")
        void whenADrawingHasThreeHorizontallyAdjacentDefaultCirclesThenTheGeneratedSVGIsCorrect(BDDSoftAssertions softly)
        {
            drawing.add(shape1);
            drawing.add(shape2);
            shape2.setRightOf(shape1);
            drawing.add(shape3);
            shape3.setRightOf(shape2);
            Integer width = 100;
            Integer height = 100;
            String svg = drawing.getSVG(width, height);

            softly.then(svg).contains("cx=\"16.666666\"");
            softly.then(svg).contains("cx=\"50\"");
            softly.then(svg).contains("cx=\"83.333336\"");
        }
        @Test
        @DisplayName("When a drawing has three vertically adjacent default circles, then the generated SVG is correct")
        void whenADrawingHasThreeVerticallyAdjacentDefaultCirclesThenTheGeneratedSVGIsCorrect(BDDSoftAssertions softly)
        {
            drawing.add(shape1);
            drawing.add(shape2);
            shape2.setAbove(shape1);
            drawing.add(shape3);
            shape3.setAbove(shape2);
            Integer width = 100;
            Integer height = 100;
            String svg = drawing.getSVG(width, height);
            logger.atFine().log(svg);

            softly.then(svg).contains("cy=\"16.666666\"");
            softly.then(svg).contains("cy=\"50\"");
            softly.then(svg).contains("cy=\"83.333336\"");
        }
    }
}
