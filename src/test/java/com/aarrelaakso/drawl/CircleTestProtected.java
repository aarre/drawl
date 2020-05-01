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

import com.aarrelaakso.drawl.test.ShapeTestPublic;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the protected API of Circles.
 */
@DisplayName("Unit tests of Circle protected API")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class CircleTestProtected extends ShapeTestProtected
{

    Circle shape1;
    Circle shape2;
    Circle shape3;

    @BeforeEach
    void givenCircles()
    {
        shape1 = new Circle();
        shape2 = new Circle();
        shape3 = new Circle();
        com.aarrelaakso.drawl.CircleTestProtected.super.shape1 = shape1;
        com.aarrelaakso.drawl.CircleTestProtected.super.shape2 = shape2;
        com.aarrelaakso.drawl.CircleTestProtected.super.shape3 = shape3;
    }


    @Nested
    @DisplayName("Given one default Circle")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultCircle extends ShapeTestProtected.GivenOneDefaultShape {

        @Test
        @DisplayName("Then its implicit radius is 0.5")
        void thenImplicitRadiusIs05() {
            SisuBigDecimal radius = shape1.getImplicitRadius();
            assertEquals(SisuBigDecimal.valueOf(0.5), radius);
        }

        @Tag("circle")
        @Tag("explicit")
        @Tag("radius")
        @Test
        @DisplayName("Then you can set the explicit radius of a circle to a fixed value")
        void thenYouCanSetTheExplicitRadiusOfACircleToAFixedValue() {
            Integer radius = 100;
            SisuBigDecimal EXPECTED = SisuBigDecimal.valueOf(radius);
            shape1.setExplicitRadius(radius);
            SisuBigDecimal ACTUAL = shape1.getExplicitRadius();

            then(ACTUAL).isEqualByComparingTo(EXPECTED);
        }

        @Test
        @DisplayName("Then you can set the radius of a circle to be the same as the radius of another circle that has an implicit radius")
        void thenYouCanSetTheRadiusOfACircleToBeTheSameAsTheRadiusOfAnotherCircle() {
            Circle circle2 = new Circle(shape1.getImplicitRadius());
            assertEquals(shape1.getImplicitRadius(), circle2.getImplicitRadius());
        }
    }
}