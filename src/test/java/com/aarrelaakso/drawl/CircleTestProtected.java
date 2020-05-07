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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the protected API of the Circle class.
 */
@DisplayName("Circles - Protected API")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class CircleTestProtected extends ShapeTestProtected
{

    Circle shape1;
    Circle shape2;
    Circle shape3;

    @BeforeEach
    void givenCircles()
    {
        this.shape1 = new Circle();
        this.shape2 = new Circle();
        this.shape3 = new Circle();
        com.aarrelaakso.drawl.CircleTestProtected.super.shape1 = this.shape1;
        com.aarrelaakso.drawl.CircleTestProtected.super.shape2 = this.shape2;
        com.aarrelaakso.drawl.CircleTestProtected.super.shape3 = this.shape3;
    }


    @Nested
    @DisplayName("Given one default Circle")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultCircle extends ShapeTestProtected.GivenOneDefaultShape {

        @Test
        @DisplayName("Then its implicit radius is 0.5")
        void thenImplicitRadiusIs05() {
            final Number radius = CircleTestProtected.this.shape1.getImplicitRadius();
            assertEquals(DrawlNumber.valueOf(0.5), radius);
        }

        @Tag("circle")
        @Tag("explicit")
        @Tag("radius")
        @Test
        @DisplayName("Then you can set the explicit radius of a circle to a fixed value")
        void thenYouCanSetTheExplicitRadiusOfACircleToAFixedValue() {
            @NotNull final Integer radius = 100;
            @NotNull final Number EXPECTED = DrawlNumber.valueOf(radius);
            CircleTestProtected.this.shape1.setExplicitRadius(radius);
            @Nullable final Number ACTUAL = CircleTestProtected.this.shape1.getExplicitRadius();

            then(ACTUAL).isEqualByComparingTo(EXPECTED);
        }

        @Test
        @DisplayName("Then you can set the radius of a circle to be the same as the radius of another circle that has an implicit radius")
        void thenYouCanSetTheRadiusOfACircleToBeTheSameAsTheRadiusOfAnotherCircle() {
            @NotNull final Circle circle2 = new Circle(CircleTestProtected.this.shape1.getImplicitRadius());
            assertEquals(CircleTestProtected.this.shape1.getImplicitRadius(), circle2.getImplicitRadius());
        }
    }
}
