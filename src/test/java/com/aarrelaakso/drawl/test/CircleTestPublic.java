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

import com.aarrelaakso.drawl.Circle;
import com.aarrelaakso.drawl.ShapeTestProtected;
import com.aarrelaakso.drawl.SisuBigDecimal;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the public API of Circles.
 */
@DisplayName("Circles - Public API")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class CircleTestPublic extends ShapeTestPublic
{

    Circle shape1;
    Circle shape2;
    Circle shape3;

    @BeforeEach
    void givenCircles() {
        shape1 = new Circle();
        shape2 = new Circle();
        shape3 = new Circle();
        CircleTestPublic.super.shape1 = shape1;
        CircleTestPublic.super.shape2 = shape2;
        CircleTestPublic.super.shape3 = shape3;
    }


    @Nested
    @DisplayName("Given one default Circle SVG")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultShapeSVGSubclass extends ShapeTestPublic.GivenOneDefaultShapeSVG {

        @Test
        @DisplayName("Then the SVG contains 'circle'")
        void thenSVGContainsCircle() {
            String svg = shape1.getSVG();
            then(svg).contains("circle");
        }

        @Test
        @DisplayName("Then the SVG contains the radius")
        void thenSVGContainsRadius() {
            String svg = shape1.getSVG();
            then(svg).contains("r=\"0.5\"");
        }
    }

    @Nested
    @DisplayName("Given three default Circles")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenThreeDefaultCircles extends ShapeTestPublic.GivenThreeDefaultShapes {

    }

    @Nested
    @DisplayName("Given two default Circles")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenTwoDefaultCircles extends ShapeTestPublic.GivenTwoDefaultShapes{


    }
}