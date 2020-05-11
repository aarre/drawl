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

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Tests the protected API of Lines.
 */
@ExtendWith(SoftAssertionsExtension.class)
@DisplayName("Lines - Protected API")
public class LineTestProtected extends ShapeTestProtected {


    @BeforeEach
    void givenRectangles()
    {
        // These values override those in ShapeTestProtected
        this.shape1 = new Line();
        this.shape2 = new Line();
        this.shape3 = new Line();
    }


    @DisplayName("When a horizontal line is constructed, then the coordinates are correct")
    @Test
    void whenAHorizontalLineIsConstructedThenTheYCoordinatesAreTheSame(BDDSoftAssertions softly) {

        final Line line = new Line(Line.Orientation.HORIZONTAL);

        softly.then(line.getPoint1Implicit().getX()).isEqualTo(DrawlNumber.ZERO);
        softly.then(line.getPoint1Implicit().getY()).isEqualTo(DrawlNumber.HALF);
        softly.then(line.getPoint2Implicit().getX()).isEqualTo(DrawlNumber.ONE);
        softly.then(line.getPoint2Implicit().getY()).isEqualTo(DrawlNumber.HALF);
        softly.then(line.getImplicitHalfHeight()).isEqualTo(DrawlNumber.ZERO);
        softly.then(line.getImplicitHeight()).isEqualTo(DrawlNumber.ZERO);
        softly.then(line.getImplicitHalfWidth()).isEqualTo(DrawlNumber.HALF);
        softly.then(line.getImplicitWidth()).isEqualTo(DrawlNumber.ONE);

    }




}
