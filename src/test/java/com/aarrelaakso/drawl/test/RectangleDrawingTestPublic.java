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
@DisplayName("Rectangle Drawings - Public API")
public class RectangleDrawingTestPublic extends ShapeDrawingTestPublic
{

    @BeforeEach
    @DisplayName("Given three default Rectangles")
    void givenTheeDefaultRectangles() {
        this.shape1 = new Rectangle();
        this.shape2 = new Rectangle();
        this.shape3 = new Rectangle();
    }

    @Nested
    @DisplayName("Symmetric (square) Rectangles")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenSymmetricRectangles
    {
        @Test
        @Tag("rectangle")
        @Tag("svg")
        @DisplayName("SVG: When a square (100) drawing has two adjacent Rectangles, then the SVG is correct")
        void svgWhenASquare100DrawingHasTwoAdjacentRectanglesThenTheSVGIsCorrect(@NotNull final BDDSoftAssertions softly)
        {
            RectangleDrawingTestPublic.this.drawing.add(RectangleDrawingTestPublic.this.shape1);
            RectangleDrawingTestPublic.this.drawing.add(RectangleDrawingTestPublic.this.shape2);
            RectangleDrawingTestPublic.this.shape2.setRightOf(RectangleDrawingTestPublic.this.shape1);
            @NotNull final String svg = RectangleDrawingTestPublic.this.drawing.getSVG(100, 100);

            softly.then(svg).contains("<?xml version='1.0' standalone='no'?><svg xmlns='http://www.w3.org/2000/svg' width='100' height='100'>")
                    .contains("<rect width='50' height='50' x='0' y='25' />")
                    .contains("<rect width='50' height='50' x='50' y='25' />")
                    .contains("</svg>");
        }
    }

    @Nested
    @DisplayName("Asymmetric (non-square) Rectangles")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenAsymmetricRectangles
    {
        @Test
        @Tag("rectangle")
        @Tag("svg")
        @DisplayName("SVG: When a square (100) drawing has two adjacent asymmetric Rectangles, then the SVG is correct")
        void svgWhenASquare100DrawingHasTwoAdjacentAsymmetricRectanglesThenTheSVGIsCorrect(@NotNull final BDDSoftAssertions softly) {
            RectangleDrawingTestPublic.this.shape1 = new Rectangle(0.5);
            RectangleDrawingTestPublic.this.shape2 = new Rectangle(2.0);
            RectangleDrawingTestPublic.this.drawing.add(RectangleDrawingTestPublic.this.shape1);
            RectangleDrawingTestPublic.this.drawing.add(RectangleDrawingTestPublic.this.shape2);
            RectangleDrawingTestPublic.this.shape2.setRightOf(RectangleDrawingTestPublic.this.shape1);
            @NotNull final String svg = RectangleDrawingTestPublic.this.drawing.getSVG(100, 100);

            softly.then(svg).contains("<?xml version='1.0' standalone='no'?><svg xmlns='http://www.w3.org/2000/svg' width='100' height='100'>")
                    .contains("<rect width='80' height='40' x='20' y='30' />")
                    .contains("<rect width='20' height='40' x='0' y='30' />")
                    .contains("</svg>");
        }

    }

    @Test
    @DisplayName("When a drawing has three horizontally adjacent default Rectangles, then the generated SVG is correct")
    void whenADrawingHasThreeHorizontallyAdjacentDefaultCirclesThenTheGeneratedSVGIsCorrect(@NotNull final BDDSoftAssertions softly)
    {
        this.drawing.add(this.shape1);
        this.drawing.add(this.shape2);
        this.shape2.setRightOf(this.shape1);
        this.drawing.add(this.shape3);
        this.shape3.setRightOf(this.shape2);
        @NotNull final Integer width = 100;
        @NotNull final Integer height = 100;
        @NotNull final String svg = this.drawing.getSVG(width, height);

        softly.then(svg).contains("x='0'");
        softly.then(svg).contains("x='33.333332'");
        softly.then(svg).contains("x='66.666664'");
    }
    @Test
    @DisplayName("When a drawing has three vertically adjacent default Rectangles, then the generated SVG is correct")
    void whenADrawingHasThreeVerticallyAdjacentDefaultCirclesThenTheGeneratedSVGIsCorrect(@NotNull final BDDSoftAssertions softly)
    {
        this.drawing.add(this.shape1);
        this.drawing.add(this.shape2);
        this.shape2.setAbove(this.shape1);
        this.drawing.add(this.shape3);
        this.shape3.setAbove(this.shape2);
        @NotNull final Integer width = 100;
        @NotNull final Integer height = 100;
        @NotNull final String svg = this.drawing.getSVG(width, height);

        softly.then(svg).contains("y='0'");
        softly.then(svg).contains("y='33.333332'");
        softly.then(svg).contains("y='66.666664'");
    }


}
