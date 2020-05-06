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
        shape1 = new Rectangle();
        shape2 = new Rectangle();
        shape3 = new Rectangle();
    }

    @Test
    @DisplayName("The SVG generated by a Shape contains the x- and y-coordinates")
    void thenTheSVGGeneratedByAShapeContainsXAndYCoordinates(@NotNull BDDSoftAssertions softly) {
        int x = 50;
        int y = 50;
        drawing.add(shape1);
        drawing.add(shape2);
        drawing.setExplicitDimensions(100,100);
        shape1.setExplicitXPositionCenter(x);
        shape1.setExplicitYPositionCenter(y);
        @NotNull String svg = drawing.getSVG();
        softly.then(svg).contains("x=\"0\"")
                .contains("y=\"0\"");
    }

    @Nested
    @DisplayName("Asymmetric (non-square) Rectangles")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenAsymmetricRectangles
    {

        @Test
        @DisplayName("When an asymmetric rectangle is created, then it maintains its height and width over getting SVG")
        void whenAnAsymmetricRectangleIsCreatedThenItMaintainsItsImplicitHeightAndWidthOnDrawing(@NotNull BDDSoftAssertions softly)
        {
            Integer height = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
            Integer width = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
            @NotNull Double aspectRatio = Double.valueOf(Double.valueOf(width) / Double.valueOf(height));
            @NotNull Rectangle rectangle = new Rectangle(aspectRatio);
            @NotNull DrawlNumber EXPECTED_HEIGHT = DrawlNumber.ONE;
            @NotNull DrawlNumber EXPECTED_WIDTH = DrawlNumber.valueOf(aspectRatio);
            softly.then(EXPECTED_HEIGHT).isEqualTo(rectangle.getImplicitHeight());
            softly.then(EXPECTED_WIDTH).as("Expected implicit width to be %s but it was %s.",
                    EXPECTED_WIDTH.toPlainString(), rectangle.getImplicitWidth().toPlainString())
                    .isEqualTo(rectangle.getImplicitWidth());
            drawing.add(rectangle);
            softly.then(EXPECTED_HEIGHT).isEqualTo(rectangle.getImplicitHeight());
            softly.then(EXPECTED_WIDTH).isEqualTo(rectangle.getImplicitWidth());
            drawing.setExplicitDimensions(100,100);
            softly.then(EXPECTED_HEIGHT).isEqualTo(rectangle.getImplicitHeight());
            softly.then(EXPECTED_WIDTH).isEqualTo(rectangle.getImplicitWidth());
            @NotNull String svg = drawing.getSVG();
            softly.then(EXPECTED_HEIGHT).isEqualTo(rectangle.getImplicitHeight());
            softly.then(EXPECTED_WIDTH).isEqualTo(rectangle.getImplicitWidth());
        }

        @Test
        @DisplayName("When an asymmetric rectangle is created, then its explicit dimensions are correct")
        void whenAnAsymmetricRectangleIsCreatedThenItsExplicitDimensionsAreCorrect(@NotNull BDDSoftAssertions softly)
        {
            @NotNull Integer height = 5;
            @NotNull Integer width = 20;
            @NotNull Double aspectRatio = Double.valueOf(width) / Double.valueOf(height);
            @NotNull Rectangle rectangle = new Rectangle(aspectRatio);
            drawing.add(rectangle);
            drawing.setExplicitDimensions(100,100);
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