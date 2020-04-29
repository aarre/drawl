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
import com.aarrelaakso.drawl.SisuBigDecimal;

import com.google.common.flogger.FluentLogger;
import org.assertj.core.api.BDDSoftAssertions;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the public API of Shapes.
 */
@DisplayName("Unit tests of Shape public API (abstract)")
public abstract class ShapeTestPublic
{

    private static final FluentLogger logger;

    static
    {
        logger = FluentLogger.forEnclosingClass();
    }

    Shape shape1;
    Shape shape2;
    Shape shape3;

    @Nested
    @DisplayName("Given three default shapes")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenThreeDefaultShapes {

        @Test
        @DisplayName("When you set three default Circles adjacent to one another, no exception is thrown")
        void whenYouSetThreeDefaultShapesAdjacentToOneAnotherThenNoExceptionIsThrown() {
            shape2.setRightOf(shape1);
            shape3.setRightOf(shape2);
        }
    }

    @Nested
    @DisplayName("Given two default Shapes")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenTwoDefaultShapes {

        @Test
        @DisplayName("When you set a Circle rightOf another circle, you can recall that information")
        void whenYouSetAShapeRightOfADefaultShapeThenYouCanRecallThatInformation() {
            shape1.setRightOf(shape2);
            assertEquals(shape1.getRightOf(), shape2);
        }

    }

    // Do not delete this even if empty; the subclass needs it
    @Nested
    @DisplayName("Given one default Shape SVG")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultShapeSVG {

        @Test
        @DisplayName("Then you cannot make it adjacent to itself")
        void thenYouCannotMakeItAdjacentToItself() {
            assertThrows(UnsupportedOperationException.class, () -> {
                shape1.setRightOf(shape1);
            });
        }
    }

    @Nested
    @DisplayName("x position")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class xPosition {



    }

}
