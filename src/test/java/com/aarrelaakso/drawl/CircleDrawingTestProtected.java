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

import org.assertj.core.api.BDDSoftAssertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@DisplayName("Unit tests of Drawing with Circle shapes")
public class CircleDrawingTestProtected extends ShapeDrawingTestProtected
{

    Circle shape1;
    Circle shape2;
    Circle shape3;

    @BeforeEach
    @DisplayName("Given three default Circles")
    void givenTheeDefaultCircles()
    {
        shape1 = new Circle();
        shape2 = new Circle();
        shape3 = new Circle();
        CircleDrawingTestProtected.super.shape1 = shape1;
        CircleDrawingTestProtected.super.shape2 = shape2;
        CircleDrawingTestProtected.super.shape3 = shape3;
    }

    @Test
    @DisplayName("SVG: The SVG generated by a drawing with a Circle contains the string 'circle'")
    void svgGeneratedByADrawingWithACircleContainsTheStringCircle()
    {
        int radius = 4000;
        Circle circle = new Circle(DrawlNumber.valueOf(radius));
        drawing.add(circle);
        String svg = drawing.getSVG(100, 100);
        then(svg).contains("circle");
    }

    @Test
    @DisplayName("RADIUS - EXPLICIT: When a square (100) drawing has two adjacent Circles, then their explicit radii are correct")
    void radiusExplicitWhenASquare100DrawingHasTwoAdjacentCirclesThenTheirExplicitRadiiAreCorrect()
    {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitHeight(100);
        drawing.setExplicitWidth(100);
        DrawlNumber explicitRadius1 = shape1.getExplicitRadius();
        DrawlNumber explicitRadius2 = shape2.getExplicitRadius();

        assertEquals(DrawlNumber.valueOf(25), explicitRadius1);
        assertEquals(DrawlNumber.valueOf(25), explicitRadius2);
    }

    @Test
    @DisplayName("RADIUS - IMPLICIT: When a square (100) drawing has two adjacent Circles, then their implicit radii are correct")
    void radiusImplicitWhenASquare100DrawingHasTwoAdjacentCirclesThenTheirImplicitRadiiAreCorrect()
    {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitHeight(100);
        drawing.setExplicitWidth(100);
        DrawlNumber actualImplicitRadius1 = shape1.getImplicitRadius();
        DrawlNumber actualImplicitRadius2 = shape2.getImplicitRadius();
        DrawlNumber expectedValue = DrawlNumber.HALF;

        assertEquals(expectedValue, actualImplicitRadius1);
        assertEquals(expectedValue, actualImplicitRadius2);
    }

    @Test
    @DisplayName("The SVG generated by a Shape contains the x- and y-coordinates")
    void thenTheSVGGeneratedByAShapeContainsXAndYCoordinates(@NotNull BDDSoftAssertions softly)
    {
        int x = 50;
        int y = 50;
        drawing.add(shape1);
        drawing.add(shape2);
        drawing.setExplicitDimensions(100, 100);
        shape1.setExplicitXPositionCenter(x);
        shape1.setExplicitYPositionCenter(y);
        String svg = drawing.getSVG();
        softly.then(svg).contains("cx=\"50\"")
                .contains("cy=\"50\"");
    }


}