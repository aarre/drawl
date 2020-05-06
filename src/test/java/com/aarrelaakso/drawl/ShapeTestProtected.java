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
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the protected API of Shapes.
 */
@ExtendWith(SoftAssertionsExtension.class)
@DisplayName("Shapes - Protected API")
public abstract class ShapeTestProtected {

    Shape shape1;
    Shape shape2;
    Shape shape3;


    /**
     * Tests the x positions of Shapes
     */
    @Nested
    @DisplayName("XPosition")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class XPosition {
        /**
         * Tests the explicit x positions of Shapes
         */
        @Nested
        @DisplayName("Explicit")
        @TestMethodOrder(MethodOrderer.Alphanumeric.class)
        class Explicit {
            @Test
            @DisplayName("When a Shape is created, then it has an explicit center x position")
            void whenAShapeIsCreatedThenItHasAnExplicitCenterXPosition() {
                @NotNull DrawlNumber explicitXPositionCenter = shape1.getExplicitXPositionCenter();
                then(explicitXPositionCenter).isEqualByComparingTo(DrawlNumber.ZERO);
            }

            @Test
            @DisplayName("When a Shape has explicit dimensions, then it has an explicit left x position")
            void whenAShapeIsCreatedThenItHasAnExplicitLeftXPosition() {
                shape1.setExplicitWidth(DrawlNumber.valueOf(100));
                DrawlNumber explicitXPositionLeft = shape1.getExplicitXPositionLeft();
                then(explicitXPositionLeft).isEqualByComparingTo(DrawlNumber.valueOf(-50));
            }
        }

        /**
         * Tests the implicit x positions of Shapes
         */
        @Nested
        @DisplayName("Implicit")
        @TestMethodOrder(MethodOrderer.Alphanumeric.class)
        class Implicit {
            @Test
            @DisplayName("When a Shape is created, then it has an implicit center x position")
            void whenAShapeIsCreatedThenItHasAnImplicitCenterXPosition() {
                DrawlNumber implicitXPositionCenter = shape1.getImplicitXPositionCenter();
                then(implicitXPositionCenter).isEqualByComparingTo(DrawlNumber.ZERO);
            }

            @Test
            @DisplayName("When a Shape is created, then it has an implicit left x position")
            void whenAShapeIsCreatedThenItHasAnImplicitLeftXPosition() {
                DrawlNumber implicitXPositionLeft = shape1.getImplicitXPositionLeft();
                then(implicitXPositionLeft).isEqualByComparingTo(DrawlNumber.valueOf(-0.5));
            }

            @Test
            @DisplayName("When you set a Shape leftOf a default Shape, its implicit x-coordinate is -1")
            void whenYouSetAShapeLeftOfADefaultShapeItsImplicitXCoordinateIsNeg1() {
                DrawlNumber x10 = shape1.getImplicitXPositionCenter();
                DrawlNumber x20 = shape2.getImplicitXPositionCenter();
                shape1.setLeftOf(shape2);
                DrawlNumber x11 = shape1.getImplicitXPositionCenter();
                DrawlNumber x21 = shape2.getImplicitXPositionCenter();
                assertEquals(x10, DrawlNumber.ZERO);
                assertEquals(x20, DrawlNumber.ZERO);
                assertEquals(x11, DrawlNumber.valueOf(-1));
                assertEquals(x21, DrawlNumber.ZERO);
            }

            @Test
            @DisplayName("When you set a Shape rightOf another Shape, its implicit x-coordinate is 1")
            void whenYouSetAShapeRightOfADefaultShapeItsImplicitXCoordinateIs1() {
                DrawlNumber x10 = shape1.getImplicitXPositionCenter();
                DrawlNumber x20 = shape2.getImplicitXPositionCenter();
                shape1.setRightOf(shape2);
                DrawlNumber x11 = shape1.getImplicitXPositionCenter();
                DrawlNumber x21 = shape2.getImplicitXPositionCenter();
                assertEquals(x10, DrawlNumber.ZERO);
                assertEquals(x20, DrawlNumber.ZERO);
                assertEquals(x11, DrawlNumber.ONE);
                assertEquals(x21, DrawlNumber.ZERO);
            }
        }
    }


    /**
     * Tests the y positions of Shapes
     */
    @Nested
    @DisplayName("Y Position")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class YPosition {
        /**
         * Tests the explicit y positions of Shapes
         */
        @Nested
        @DisplayName("Explicit")
        @TestMethodOrder(MethodOrderer.Alphanumeric.class)
        class Explicit {
            @Test
            @DisplayName("When a Shape has explicit dimensions, then it has an explicit top y position")
            void whenAShapeIsCreatedThenItHasAnExplicitTopXPosition() {
                @NotNull Drawing drawing = new Drawing();
                drawing.add(shape1);
                drawing.setExplicitDimensions(100, 100);
                @NotNull DrawlNumber explicitXPositionTop = shape1.getExplicitYPositionTop();
                then(explicitXPositionTop).isEqualByComparingTo(DrawlNumber.valueOf(0));
            }
        }

        /**
         * Tests the implicit y positions of Shapes
         */
        @Nested
        @DisplayName("Implicit")
        @TestMethodOrder(MethodOrderer.Alphanumeric.class)
        class Implicit {
            @Test
            @DisplayName("When a Shape is created, then it has an implicit center y position")
            void whenShapeIsCreatedThenItHasAnImplicitCenterYPosition() {
                DrawlNumber implicitYPositionCenter = shape1.getImplicitYPositionCenter();
                then(implicitYPositionCenter).isEqualByComparingTo(DrawlNumber.ZERO);
            }

            @Test
            @DisplayName("When a Shape is created, then it has an implicit top y position")
            void whenShapeIsCreatedThenItHasAnImplicitTopYPosition() {
                DrawlNumber implicitYPositionTop = shape1.getImplicitYPositionTop();
                then(implicitYPositionTop).isEqualByComparingTo(DrawlNumber.valueOf(0.5));
            }

            @Test
            @DisplayName("When you set a default Shape above a default Shape, its implicit y-coordinate is 1")
            void whenYouSetADefaultShapeAboveADefaultShapeItsImplicitYCoordinateIs1() {
                shape1.setAbove(shape2);
                DrawlNumber y1 = shape1.getImplicitYPositionCenter();
                DrawlNumber y2 = shape2.getImplicitYPositionCenter();
                assertEquals(DrawlNumber.ONE, y1);
                assertEquals(DrawlNumber.ZERO, y2);
            }

            @Test
            @DisplayName("When you set a default Shape below a default Shape, its implicit y-coordinate is -1")
            void whenYouSetADefaultShapeBelowADefaultShapeItsImplicitYCoordinateIsNeg1() {
                shape1.setBelow(shape2);
                DrawlNumber y1 = shape1.getImplicitYPositionCenter();
                DrawlNumber y2 = shape2.getImplicitYPositionCenter();
                assertEquals(y1, DrawlNumber.valueOf(-1));
                assertEquals(y2, DrawlNumber.ZERO);
            }
        }
    }

    @Nested
    @DisplayName("Given one default Shape")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultShape {

        @Test
        @DisplayName("Then its explicit width is null")
        void thenExplicitWidthIsNull() {
            @Nullable DrawlNumber width = shape1.getExplicitWidth();
            assertNull(width, "The explicit width of a default Shape should be null.");
        }

        @Test
        @DisplayName("Then its explicit x-coordinate is 0")
        void thenExplicitXCoordinateIs0() {
            @NotNull DrawlNumber x = shape1.getExplicitXPositionCenter();
            then(x).isEqualByComparingTo(DrawlNumber.ZERO);
            then("0").isEqualTo(x.toPlainString());
        }

        @Test
        @DisplayName("Then its explicit y-coordinate is 0")
        void thenExplicitYCoordinateIs0() {
            @NotNull DrawlNumber y = shape1.getExplicitYPositionCenter();
            assertAll("The y-coordinate of a default Circle should be exactly 0",
                    () -> assertEquals(DrawlNumber.ZERO, y),
                    () -> assertEquals("0", y.toPlainString())
            );
        }

        @Test
        @DisplayName("Then its implicit height is 1")
        void thenImplicitHeightIs1() {
            @Nullable DrawlNumber implicitHeight = shape1.getImplicitHeight();
            assertEquals(implicitHeight, DrawlNumber.ONE);
        }

        @Test
        @DisplayName("Then its implicit width is 1")
        void thenImplicitWidthIs1() {
            DrawlNumber width = shape1.getImplicitWidth();
            assertEquals(width, DrawlNumber.ONE);
        }

        @Test
        @DisplayName("Then its implicit x-coordinate is 0")
        void thenImplicitXPositionIs0() {
            DrawlNumber x = shape1.getImplicitXPositionCenter();
            assertEquals(x, DrawlNumber.ZERO);
        }

        @Test
        @DisplayName("Then its implicit y-coordinate is 0")
        void thenImplicitYPositionIs0() {
            DrawlNumber x = shape1.getImplicitYPositionCenter();
            assertEquals(x, DrawlNumber.ZERO);
        }

        @Test
        @DisplayName("Then setting the height and then getting the height give the same result")
        void thenSettingTheHeightAndGettingTheHeightGiveTheSameResult() {
            @NotNull DrawlNumber EXPECTED = DrawlNumber.valueOf(100);
            shape1.setExplicitHeight(EXPECTED);
            @Nullable DrawlNumber ACTUAL = shape1.getExplicitHeight();

            then(EXPECTED).isEqualByComparingTo(ACTUAL);
        }

        @Test
        @DisplayName("Then setting the width and then getting the width give the same result")
        void thenSettingTheWidthAndGettingTheWidthGiveTheSameResult() {
            @NotNull DrawlNumber EXPECTED = DrawlNumber.valueOf(100);
            shape1.setExplicitWidth(EXPECTED);
            @Nullable DrawlNumber ACTUAL = shape1.getExplicitWidth();

            then(EXPECTED).isEqualByComparingTo(ACTUAL);
        }


        @Test
        @DisplayName("Then you can set its x-coordinate and get it back")
        void thenYouCanSetItsXCoordinateAndGetItBack() {
            shape1.setExplicitXPositionCenter(100);
            @NotNull DrawlNumber x = shape1.getExplicitXPositionCenter();
            assertEquals(x, DrawlNumber.valueOf(100));
        }

    }

    @Nested
    @DisplayName("Ports")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class Ports {

        @Test
        @DisplayName("When a shape is created, then its left port is positioned correctly")
        void whenAShapeIsCreatedThenItsLeftPortIsPositionedCorrectly(@NotNull BDDSoftAssertions softly) {
            @NotNull Point leftPort = shape1.getLeftPort();
            softly.then(leftPort.getX()).isEqualTo(DrawlNumber.HALF.negate());
            softly.then(leftPort.getY()).isEqualTo(DrawlNumber.ZERO);
        }

        @Test
        @DisplayName("When a shape is created, then its right port is positioned correctly")
        void whenAShapeIsCreatedThenItsRightPortIsPositionedCorrectly(@NotNull BDDSoftAssertions softly) {
            @NotNull Point leftPort = shape1.getRightPort();
            softly.then(leftPort.getX()).isEqualTo(DrawlNumber.HALF);
            softly.then(leftPort.getY()).isEqualTo(DrawlNumber.ZERO);
        }

        @Test
        @DisplayName("When a shape is created, then its top port is positioned correctly")
        void whenAShapeIsCreatedThenItsTopPortIsPositionedCorrectly(@NotNull BDDSoftAssertions softly) {
            @NotNull Point leftPort = shape1.getTopPort();
            softly.then(leftPort.getX()).isEqualTo(DrawlNumber.ZERO);
            softly.then(leftPort.getY()).isEqualTo(DrawlNumber.HALF);
        }

        @Test
        @DisplayName("When a shape is created, then its bottom port is positioned correctly")
        void whenAShapeIsCreatedThenItsBottomPortIsPositionedCorrectly(@NotNull BDDSoftAssertions softly) {
            @NotNull Point leftPort = shape1.getBottomPort();
            softly.then(leftPort.getX()).isEqualTo(DrawlNumber.ZERO);
            softly.then(leftPort.getY()).isEqualTo(DrawlNumber.HALF.negate());
        }
    }
}
