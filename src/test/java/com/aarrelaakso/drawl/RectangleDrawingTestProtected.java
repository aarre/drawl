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
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(SoftAssertionsExtension.class)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@DisplayName("Rectangle Drawings - Protected API")
public class RectangleDrawingTestProtected extends ShapeDrawingTestProtected
{

    @BeforeEach
    @DisplayName("Given three default Rectangles")
    void givenTheeDefaultRectangles()
    {
        // These values override those in the superclass.
        this.shape1 = new Rectangle();
        this.shape2 = new Rectangle();
        this.shape3 = new Rectangle();
    }

    @Test
    @DisplayName("The SVG generated by a Shape contains the x- and y-coordinates")
    void thenTheSVGGeneratedByAShapeContainsXAndYCoordinates(@NotNull final BDDSoftAssertions softly) {
        final int x = 50;
        final int y = 50;
        this.drawing.add(this.shape1);
        this.drawing.add(this.shape2);
        this.drawing.setExplicitDimensions(100,100);
        this.shape1.setExplicitXPositionCenter(x);
        this.shape1.setExplicitYPositionCenter(y);
        @NotNull final String svg = this.drawing.getSVG();
        softly.then(svg).contains("x='0'")
                .contains("y='0'");
    }

    @Nested
    @DisplayName("Asymmetric (non-square) Rectangles")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenAsymmetricRectangles
    {

        @Test
        @DisplayName("When an asymmetric rectangle is created, then it maintains its height and width over getting SVG")
        void whenAnAsymmetricRectangleIsCreatedThenItMaintainsItsImplicitHeightAndWidthOnDrawing(@NotNull final BDDSoftAssertions softly)
        {
            final Integer height = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
            final Integer width = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
            @NotNull final Double aspectRatio = Double.valueOf(Double.valueOf(width) / Double.valueOf(height));
            @NotNull final Rectangle rectangle = new Rectangle(aspectRatio);
            @NotNull final DrawlNumber EXPECTED_HEIGHT = DrawlNumber.ONE;
            @NotNull final DrawlNumber EXPECTED_WIDTH = DrawlNumber.valueOf(aspectRatio);
            softly.then(EXPECTED_HEIGHT).isEqualTo(rectangle.getImplicitHeight());
            softly.then(EXPECTED_WIDTH).as("Expected implicit width to be %s but it was %s.",
                    EXPECTED_WIDTH.toPlainString(), rectangle.getImplicitWidth().toPlainString())
                    .isEqualTo(rectangle.getImplicitWidth());
            RectangleDrawingTestProtected.this.drawing.add(rectangle);
            softly.then(EXPECTED_HEIGHT).isEqualTo(rectangle.getImplicitHeight());
            softly.then(EXPECTED_WIDTH).isEqualTo(rectangle.getImplicitWidth());
            RectangleDrawingTestProtected.this.drawing.setExplicitDimensions(100,100);
            softly.then(EXPECTED_HEIGHT).isEqualTo(rectangle.getImplicitHeight());
            softly.then(EXPECTED_WIDTH).isEqualTo(rectangle.getImplicitWidth());
            @NotNull final String svg = RectangleDrawingTestProtected.this.drawing.getSVG();
            softly.then(EXPECTED_HEIGHT).isEqualTo(rectangle.getImplicitHeight());
            softly.then(EXPECTED_WIDTH).isEqualTo(rectangle.getImplicitWidth());
        }

        @Test
        @DisplayName("When an asymmetric rectangle is created, then its explicit dimensions are correct")
        void whenAnAsymmetricRectangleIsCreatedThenItsExplicitDimensionsAreCorrect(@NotNull final BDDSoftAssertions softly)
        {
            @NotNull final Integer height = 5;
            @NotNull final Integer width = 20;
            @NotNull final Double aspectRatio = Double.valueOf(width) / Double.valueOf(height);
            @NotNull final Rectangle rectangle = new Rectangle(aspectRatio);
            RectangleDrawingTestProtected.this.drawing.add(rectangle);
            RectangleDrawingTestProtected.this.drawing.setExplicitDimensions(100,100);
            softly.then(DrawlNumber.valueOf(100))
                    .as("Expecting width to be %s but it was %s",
                            DrawlNumber.valueOf(100).toPlainString(),
                            rectangle.getExplicitWidth().toPlainString())
                    .isEqualTo(rectangle.getExplicitWidth());
            softly.then(DrawlNumber.valueOf(25))
                    .as("Expecting height to be %s but it was %s",
                            DrawlNumber.valueOf(25).toPlainString(),
                            rectangle.getExplicitHeight().toPlainString())
                    .isEqualTo(rectangle.getExplicitHeight());
        }



    }
}