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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@DisplayName("Line - Protected API")
@ExtendWith(SoftAssertionsExtension.class)
public class LineDrawingTestProtected extends ShapeDrawingTestProtected {

    @BeforeEach
    @DisplayName("Given three default Lines")
    void givenTheeDefaultLines()
    {
        // These values override those in the superclass.
        shape1 = new Line();
        shape2 = new Line();
        shape3 = new Line();
    }

    @Test
    @DisplayName("When a drawing has a Line, then the Line has the correct coordinates")
    void whenADrawingHasALineThenItShowsUpInSVG(BDDSoftAssertions softly) {
        drawing.add(shape1);
        shape1.setStroke("blue");
        drawing.add(shape2);
        shape2.setStroke("green");
        shape2.setRightOf(shape1, shape2.getWidth());
        Line line = new Line(shape1.getRightPort(), shape2.getLeftPort());
        line.setStroke("red");
        drawing.add(line);
        drawing.setExplicitDimensions(100,100);

        Point point1 = line.getPoint1Explicit();
        Point point2 = line.getPoint2Explicit();

        softly.then(point1).isNotNull();
        softly.then(point2).isNotNull();

        softly.then(point1.getX()).isEqualTo(DrawlNumber.valueOf(100.0/3.0));
        softly.then(point1.getY()).isEqualTo(DrawlNumber.valueOf(50));

        softly.then(point1.getX()).isEqualTo(DrawlNumber.valueOf(200.0/3.0));
        softly.then(point1.getY()).isEqualTo(DrawlNumber.valueOf(50));
    }

}
