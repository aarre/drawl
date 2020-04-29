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

import com.aarrelaakso.drawl.Rectangle;
import org.assertj.core.api.BDDSoftAssertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@DisplayName("Unit tests of Drawing with Rectangle shapes")
public class DrawingTestRectanglePublic extends DrawingTestShapePublic
{

    @BeforeEach
    @DisplayName("Given three default Rectangles")
    void givenTheeDefaultRectangles() {
        shape1 = new Rectangle();
        shape2 = new Rectangle();
        shape3 = new Rectangle();
        DrawingTestRectanglePublic.super.shape1 = shape1;
        DrawingTestRectanglePublic.super.shape2 = shape2;
        DrawingTestRectanglePublic.super.shape3 = shape3;
    }

    @Test
    @Tag("rectangle")
    @Tag("svg")
    @DisplayName("SVG: When a square (100) drawing has two adjacent Rectangles, then the SVG is correct")
    void svgWhenASquare100DrawingHasTwoAdjacentRectanglesThenTheSVGIsCorrect(@NotNull BDDSoftAssertions softly) {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        String svg = drawing.getSVG(100, 100);

        softly.then(svg).contains("<?xml version=\"1.0\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"100\" height=\"100\">")
                .contains("<rect width=\"50\" height=\"50\" x=\"0\" y=\"25\" />")
                .contains("<rect width=\"50\" height=\"50\" x=\"50\" y=\"25\" />")
                .contains("</svg>");
    }

    @Test
    @Tag("rectangle")
    @Tag("svg")
    @DisplayName("SVG: When a square (100) drawing has two adjacent asymmetric Rectangles, then the SVG is correct")
    void svgWhenASquare100DrawingHasTwoAdjacentAsymmetricRectanglesThenTheSVGIsCorrect(@NotNull BDDSoftAssertions softly) {
        shape1 = new Rectangle(Double.valueOf(1/2));
        shape2 = new Rectangle(Double.valueOf(2));
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        String svg = drawing.getSVG(100, 100);

        softly.then(svg).contains("<?xml version=\"1.0\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"100\" height=\"100\">")
                .contains("<rect width=\"0.5\" height=\"1\" x=\"0\" y=\"25\" />")
                .contains("<rect width=\"2\" height=\"1\" x=\"50\" y=\"25\" />")
                .contains("</svg>");
    }



}
