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

import com.google.common.flogger.FluentLogger;
import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Test the protected API for Rectangles.
 */
@ExtendWith(SoftAssertionsExtension.class)
@DisplayName("Rectangles - Protected API")
public class RectangleTestProtected extends ShapeTestProtected
{
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    @BeforeEach
    void givenRectangles()
    {
        // These values override those in ShapeTestProtected
        shape1 = new Rectangle();
        shape2 = new Rectangle();
        shape3 = new Rectangle();
    }

    @Nested
    @DisplayName("Asymmetric (non-square) Rectangles")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenAsymmetricRectangles
    {

        @Test
        @DisplayName("When an asymmetric rectangle is created with an aspect ratio, then its implicit dimensions are correct")
        void whenAnAsymmetricRectangleIsCreatedThenItsImplicitDimensionsAreCorrect(@NotNull BDDSoftAssertions softly)
        {
            Integer height = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
            Integer width = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
            @NotNull Double aspectRatio = Double.valueOf(width) / Double.valueOf(height);
            @NotNull Rectangle rectangle = new Rectangle(aspectRatio);
            logger.atFine().log("Rectangle created");
            logger.atFine().log("Implicit width is %s", rectangle.getImplicitWidth().toPlainString());
            @NotNull DrawlNumber EXPECTED_HEIGHT = DrawlNumber.ONE;
            @NotNull DrawlNumber EXPECTED_WIDTH = DrawlNumber.valueOf(aspectRatio);
            logger.atFine().log("Implicit width is %s", rectangle.getImplicitWidth().toPlainString());
            @Nullable DrawlNumber ACTUAL_HEIGHT = rectangle.getImplicitHeight();
            logger.atFine().log("Implicit width is %s", rectangle.getImplicitWidth().toPlainString());
            DrawlNumber ACTUAL_WIDTH = rectangle.getImplicitWidth();
            logger.atFine().log("Implicit width is %s", ACTUAL_WIDTH.toPlainString());
            softly.then(EXPECTED_HEIGHT)
                    .as("Expected implicit height to be %s but it was %s.",
                            EXPECTED_HEIGHT.toPlainString(),
                            ACTUAL_HEIGHT.toPlainString())
                    .isEqualTo(ACTUAL_HEIGHT);
            softly.then(EXPECTED_WIDTH)
                    .as("Expected implicit width to be %s but it was %s.",
                            EXPECTED_WIDTH.toPlainString(),
                            ACTUAL_WIDTH.toPlainString())
                    .isEqualTo(ACTUAL_WIDTH);
        }

        @Test
        @DisplayName("When an asymmetric rectangle is created with SisuBigDecimal dimensions, then its implicit dimensions are correct")
        void whenAnAsymmetricRectangleIsCreatedWithSisuBigDecimalDimensionsThenItsImplicitDimensionsAreCorrect(@NotNull BDDSoftAssertions softly)
        {
            @NotNull DrawlNumber height = DrawlNumber.valueOf(ThreadLocalRandom.current().nextDouble(Double.MAX_VALUE));
            @NotNull DrawlNumber width = DrawlNumber.valueOf(ThreadLocalRandom.current().nextDouble(Double.MAX_VALUE));
            @NotNull Rectangle rectangle = new Rectangle(width, height);
            then(rectangle).isNotNull();
            logger.atFine().log("Rectangle created");
            logger.atFine().log("Implicit width is %s", rectangle.getImplicitWidth().toPlainString());
            @NotNull DrawlNumber EXPECTED_HEIGHT = height;
            @NotNull DrawlNumber EXPECTED_WIDTH = width;
            logger.atFine().log("Implicit width is %s", rectangle.getImplicitWidth().toPlainString());
            @Nullable DrawlNumber ACTUAL_HEIGHT = rectangle.getImplicitHeight();
            logger.atFine().log("Implicit width is %s", rectangle.getImplicitWidth().toPlainString());
            DrawlNumber ACTUAL_WIDTH = rectangle.getImplicitWidth();
            logger.atFine().log("Implicit width is %s", ACTUAL_WIDTH.toPlainString());
            softly.then(EXPECTED_HEIGHT)
                    .as("Expected implicit height to be %s but it was %s.",
                            EXPECTED_HEIGHT.toPlainString(),
                            ACTUAL_HEIGHT.toPlainString())
                    .isEqualTo(ACTUAL_HEIGHT);
            softly.then(EXPECTED_WIDTH)
                    .as("Expected implicit width to be %s but it was %s.",
                            EXPECTED_WIDTH.toPlainString(),
                            ACTUAL_WIDTH.toPlainString())
                    .isEqualTo(ACTUAL_WIDTH);
        }


    }


}