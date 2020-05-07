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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Tests the protected API of Points.
 */
@ExtendWith(SoftAssertionsExtension.class)
@DisplayName("Points - Protected API")
public class PointTestProtected
{
    @Test
    @DisplayName("When you create a point then you can get X")
    void whenYouCreateAPointThenYouCanGetX(@NotNull final BDDSoftAssertions softly)
    {
        @NotNull final Point point = new Point(3, 19);
        softly.then(point.getX()).isNotNull();
        softly.then(point.getX()).isEqualTo(DrawlNumber.valueOf(3));
    }

    @Test
    @DisplayName("When you create a point then you can get Y")
    void whenYouCreateAPointThenYouCanGetY(@NotNull final BDDSoftAssertions softly)
    {
        @NotNull final Point point = new Point(19, 17);
        softly.then(point.getY()).isNotNull();
        softly.then(point.getY()).isEqualTo(DrawlNumber.valueOf(17));
    }

}