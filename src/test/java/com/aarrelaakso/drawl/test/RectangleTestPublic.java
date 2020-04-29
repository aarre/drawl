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
import com.aarrelaakso.drawl.SisuBigDecimal;
import org.junit.jupiter.api.*;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Unit tests of Rectangle")
public class RectangleTestPublic extends ShapeTestPublic
{

    Rectangle shape1;
    Rectangle shape2;
    Rectangle shape3;

    @BeforeEach
    void givenRectangles() {
        shape1 = new Rectangle();
        shape2 = new Rectangle();
        shape3 = new Rectangle();
        RectangleTestPublic.super.shape1 = shape1;
        RectangleTestPublic.super.shape2 = shape2;
        RectangleTestPublic.super.shape3 = shape3;
    }

    @Nested
    @DisplayName("Given one default Rectangle SVG")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultRectangleSVG {

    }


    @Nested
    @DisplayName("Asymmetric (non-square) Rectangles")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenAsymmetricRectangles {

        @Test
        @DisplayName("When an asymmetric rectangle is created, then it is not null")
        void whenAnAsymmetricRectangleIsCreatedThenItIsNotNull()
        {
            Integer height = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
            Integer width = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
            Double aspectRatio = Double.valueOf(width/height);
            Rectangle rectangle = new Rectangle(aspectRatio);
            then(rectangle).isNotNull();
        }


    }


}