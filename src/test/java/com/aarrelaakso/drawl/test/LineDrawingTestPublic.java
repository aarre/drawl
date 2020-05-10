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

import com.aarrelaakso.drawl.Line;
import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(SoftAssertionsExtension.class)
@DisplayName("Line Drawing - Public API")
public class LineDrawingTestPublic extends ShapeDrawingTestPublic {


    @BeforeEach
    @DisplayName("Given three default Lines")
    void givenTheeDefaultRectangles() {
        this.shape1 = new Line();
        this.shape2 = new Line();
        this.shape3 = new Line();
    }



    @Test
    @DisplayName("When a drawing has a line, then it is represented correctly in the SVG")
    void whenADrawingHasALineThenItIsRepresentedCorrectlyInTheSVG(@NotNull final BDDSoftAssertions softly) {
        this.drawing.add(this.shape1);
        this.shape1.setStroke("blue");
        this.drawing.add(this.shape2);
        this.shape2.setStroke("green");
        this.shape2.setRightOf(this.shape1, this.shape2.getWidth());
        @NotNull final Line line = new Line(this.shape1.getRightPort(), this.shape2.getLeftPort());
        line.setStroke("red");
        this.drawing.add(line);
        @NotNull final String svg = this.drawing.getSVG(100,100);

        softly.then(svg).contains("<line");
        softly.then(svg).contains("y1='50'");
        softly.then(svg).contains("y2='50'");
        softly.then(svg).contains("x1='33.333332");
        softly.then(svg).contains("x2='66.666664");
    }


}
