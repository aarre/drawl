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


import com.aarrelaakso.drawl.Arrowhead;
import com.aarrelaakso.drawl.Drawing;
import com.aarrelaakso.drawl.Line;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Tests the public API of Lines.
 */
@ExtendWith(SoftAssertionsExtension.class)
@DisplayName("Lines - Public API")
public class LineTestPublic extends ShapeTestPublic {

    @BeforeEach
    void setUp() {
        // These values override those in ShapeTestPublic
        this.shape1 = new Line();
        this.shape2 = new Line();
        this.shape3 = new Line();
    }


    @DisplayName("When a line is being constructed, then the user can specify the orientation")
    @ParameterizedTest
    @EnumSource(Line.Orientation.class)
    void whenALineIsBeingConstructedThenTheUserCanSpecifyTheOrientation(Line.Orientation orientation) {
        Line line = new Line(orientation);
        then(line).isNotNull();
    }

    @DisplayName("When a horizontal line is constructed, then the coordinates are correct")
    @Test
    void whenAHorizontalLineIsConstructedThenTheCoordinatesAreCorrect(BDDSoftAssertions softly) {
        final Line line = new Line(Line.Orientation.HORIZONTAL);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG(100,100);
        
        softly.then(svg).contains("x1='0'");
        softly.then(svg).contains("y1='50'");
        softly.then(svg).contains("x2='100'");
        softly.then(svg).contains("y2='50'");
    }

}
