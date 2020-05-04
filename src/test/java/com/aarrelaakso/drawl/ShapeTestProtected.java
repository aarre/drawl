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

import org.junit.jupiter.api.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the protected API of Shapes.
 */
@DisplayName("Shapes - Private API")
public abstract class ShapeTestProtected
{

    Shape shape1;
    Shape shape2;
    Shape shape3;


    /**
     * Tests the x positions of Shapes
     */
    @Nested
    @DisplayName("XPosition")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class XPosition
    {
        /**
         * Tests the explicit x positions of Shapes
         */
        @Nested
        @DisplayName("Explicit")
        @TestMethodOrder(MethodOrderer.Alphanumeric.class)
        class Explicit
        {
            @Test
            @DisplayName("When a Shape is created, then it has an explicit center x position")
            void whenAShapeIsCreatedThenItHasAnExplicitCenterXPosition()
            {
                SisuBigDecimal explicitXPositionCenter = shape1.getExplicitXPositionCenter();
                then(explicitXPositionCenter).isEqualByComparingTo(SisuBigDecimal.ZERO);
            }

            @Test
            @DisplayName("When a Shape has explicit dimensions, then it has an explicit left x position")
            void whenAShapeIsCreatedThenItHasAnExplicitLeftXPosition()
            {
                shape1.setExplicitWidth(SisuBigDecimal.valueOf(100));
                SisuBigDecimal explicitXPositionLeft = shape1.getExplicitXPositionLeft();
                then(explicitXPositionLeft).isEqualByComparingTo(SisuBigDecimal.valueOf(-50));
            }
        }

        /**
         * Tests the implicit x positions of Shapes
         */
        @Nested
        @DisplayName("Implicit")
        @TestMethodOrder(MethodOrderer.Alphanumeric.class)
        class Implicit
        {
            @Test
            @DisplayName("When a Shape is created, then it has an implicit center x position")
            void whenAShapeIsCreatedThenItHasAnImplicitCenterXPosition()
            {
                SisuBigDecimal implicitXPositionCenter = shape1.getImplicitXPositionCenter();
                then(implicitXPositionCenter).isEqualByComparingTo(SisuBigDecimal.ZERO);
            }

            @Test
            @DisplayName("When a Shape is created, then it has an implicit left x position")
            void whenAShapeIsCreatedThenItHasAnImplicitLeftXPosition()
            {
                SisuBigDecimal implicitXPositionLeft = shape1.getImplicitXPositionLeft();
                then(implicitXPositionLeft).isEqualByComparingTo(SisuBigDecimal.valueOf(-0.5));
            }

            @Test
            @DisplayName("When you set a Shape leftOf a default Shape, its implicit x-coordinate is -1")
            void whenYouSetAShapeLeftOfADefaultShapeItsImplicitXCoordinateIsNeg1()
            {
                SisuBigDecimal x10 = shape1.getImplicitXPositionCenter();
                SisuBigDecimal x20 = shape2.getImplicitXPositionCenter();
                shape1.setLeftOf(shape2);
                SisuBigDecimal x11 = shape1.getImplicitXPositionCenter();
                SisuBigDecimal x21 = shape2.getImplicitXPositionCenter();
                assertEquals(x10, SisuBigDecimal.ZERO);
                assertEquals(x20, SisuBigDecimal.ZERO);
                assertEquals(x11, SisuBigDecimal.valueOf(-1));
                assertEquals(x21, SisuBigDecimal.ZERO);
            }

            @Test
            @DisplayName("When you set a Shape rightOf another Shape, its implicit x-coordinate is 1")
            void whenYouSetAShapeRightOfADefaultShapeItsImplicitXCoordinateIs1()
            {
                SisuBigDecimal x10 = shape1.getImplicitXPositionCenter();
                SisuBigDecimal x20 = shape2.getImplicitXPositionCenter();
                shape1.setRightOf(shape2);
                SisuBigDecimal x11 = shape1.getImplicitXPositionCenter();
                SisuBigDecimal x21 = shape2.getImplicitXPositionCenter();
                assertEquals(x10, SisuBigDecimal.ZERO);
                assertEquals(x20, SisuBigDecimal.ZERO);
                assertEquals(x11, SisuBigDecimal.ONE);
                assertEquals(x21, SisuBigDecimal.ZERO);
            }
        }
    }


    /**
     * Tests the y positions of Shapes
     */
    @Nested
    @DisplayName("Y Position")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class YPosition
    {
        /**
         * Tests the explicit y positions of Shapes
         */
        @Nested
        @DisplayName("Explicit")
        @TestMethodOrder(MethodOrderer.Alphanumeric.class)
        class Explicit
        {
            @Test
            @DisplayName("When a Shape has explicit dimensions, then it has an explicit top y position")
            void whenAShapeIsCreatedThenItHasAnExplicitTopXPosition()
            {
                Drawing drawing = new Drawing();
                drawing.add(shape1);
                drawing.setExplicitDimensions(100, 100);
                SisuBigDecimal explicitXPositionTop = shape1.getExplicitYPositionTop();
                then(explicitXPositionTop).isEqualByComparingTo(SisuBigDecimal.valueOf(0));
            }
        }

        /**
         * Tests the implicit y positions of Shapes
         */
        @Nested
        @DisplayName("Implicit")
        @TestMethodOrder(MethodOrderer.Alphanumeric.class)
        class Implicit
        {
            @Test
            @DisplayName("When a Shape is created, then it has an implicit center y position")
            void whenShapeIsCreatedThenItHasAnImplicitCenterYPosition()
            {
                SisuBigDecimal implicitYPositionCenter = shape1.getImplicitYPositionCenter();
                then(implicitYPositionCenter).isEqualByComparingTo(SisuBigDecimal.ZERO);
            }

            @Test
            @DisplayName("When a Shape is created, then it has an implicit top y position")
            void whenShapeIsCreatedThenItHasAnImplicitTopYPosition()
            {
                SisuBigDecimal implicitYPositionTop = shape1.getImplicitYPositionTop();
                then(implicitYPositionTop).isEqualByComparingTo(SisuBigDecimal.valueOf(0.5));
            }

            @Test
            @DisplayName("When you set a default Shape above a default Shape, its implicit y-coordinate is 1")
            void whenYouSetADefaultShapeAboveADefaultShapeItsImplicitYCoordinateIs1()
            {
                shape1.setAbove(shape2);
                SisuBigDecimal y1 = shape1.getImplicitYPositionCenter();
                SisuBigDecimal y2 = shape2.getImplicitYPositionCenter();
                assertEquals(SisuBigDecimal.ONE, y1);
                assertEquals(SisuBigDecimal.ZERO, y2);
            }

            @Test
            @DisplayName("When you set a default Shape below a default Shape, its implicit y-coordinate is -1")
            void whenYouSetADefaultShapeBelowADefaultShapeItsImplicitYCoordinateIsNeg1()
            {
                shape1.setBelow(shape2);
                SisuBigDecimal y1 = shape1.getImplicitYPositionCenter();
                SisuBigDecimal y2 = shape2.getImplicitYPositionCenter();
                assertEquals(y1, SisuBigDecimal.valueOf(-1));
                assertEquals(y2, SisuBigDecimal.ZERO);
            }
        }
    }

    @Nested
    @DisplayName("Given one default Shape")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultShape
    {

        @Test
        @DisplayName("Then its explicit width is null")
        void thenExplicitWidthIsNull()
        {
            SisuBigDecimal width = shape1.getExplicitWidth();
            assertEquals(null, width, "The explicit width of a default Shape should be null.");
        }

        @Test
        @DisplayName("Then its explicit x-coordinate is 0")
        void thenExplicitXCoordinateIs0()
        {
            SisuBigDecimal x = shape1.getExplicitXPositionCenter();
            then(x).isEqualByComparingTo(SisuBigDecimal.ZERO);
            then("0").isEqualTo(x.toPlainString());
        }

        @Test
        @DisplayName("Then its explicit y-coordinate is 0")
        void thenExplicitYCoordinateIs0()
        {
            SisuBigDecimal y = shape1.getExplicitYPositionCenter();
            assertAll("The y-coordinate of a default Circle should be exactly 0",
                    () -> assertEquals(SisuBigDecimal.ZERO, y),
                    () -> assertEquals("0", y.toPlainString())
            );
        }

        @Test
        @DisplayName("Then its implicit height is 1")
        void thenImplicitHeightIs1()
        {
            SisuBigDecimal implicitHeight = shape1.getImplicitHeight();
            assertEquals(implicitHeight, SisuBigDecimal.ONE);
        }

        @Test
        @DisplayName("Then its implicit width is 1")
        void thenImplicitWidthIs1()
        {
            SisuBigDecimal width = shape1.getImplicitWidth();
            assertEquals(width, SisuBigDecimal.ONE);
        }

        @Test
        @DisplayName("Then its implicit x-coordinate is 0")
        void thenImplicitXPositionIs0()
        {
            SisuBigDecimal x = shape1.getImplicitXPositionCenter();
            assertEquals(x, SisuBigDecimal.ZERO);
        }

        @Test
        @DisplayName("Then its implicit y-coordinate is 0")
        void thenImplicitYPositionIs0()
        {
            SisuBigDecimal x = shape1.getImplicitYPositionCenter();
            assertEquals(x, SisuBigDecimal.ZERO);
        }

        @Test
        @DisplayName("Then setting the height and then getting the height give the same result")
        void thenSettingTheHeightAndGettingTheHeightGiveTheSameResult()
        {
            SisuBigDecimal EXPECTED = SisuBigDecimal.valueOf(100);
            shape1.setExplicitHeight(EXPECTED);
            SisuBigDecimal ACTUAL = shape1.getExplicitHeight();

            then(EXPECTED).isEqualByComparingTo(ACTUAL);
        }

        @Test
        @DisplayName("Then setting the width and then getting the width give the same result")
        void thenSettingTheWidthAndGettingTheWidthGiveTheSameResult()
        {
            SisuBigDecimal EXPECTED = SisuBigDecimal.valueOf(100);
            shape1.setExplicitWidth(EXPECTED);
            SisuBigDecimal ACTUAL = shape1.getExplicitWidth();

            then(EXPECTED).isEqualByComparingTo(ACTUAL);
        }


        @Test
        @DisplayName("Then you can set its x-coordinate and get it back")
        void thenYouCanSetItsXCoordinateAndGetItBack()
        {
            shape1.setExplicitXPositionCenter(100);
            SisuBigDecimal x = shape1.getExplicitXPositionCenter();
            assertEquals(x, SisuBigDecimal.valueOf(100));
        }

    }
}
