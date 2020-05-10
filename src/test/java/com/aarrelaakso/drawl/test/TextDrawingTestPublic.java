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

import com.aarrelaakso.drawl.Text;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Tests the public API of Text placed on Drawings
 */
@ExtendWith(SoftAssertionsExtension.class)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@DisplayName("Text Drawings - Public API")
public class TextDrawingTestPublic extends ShapeDrawingTestPublic
{

    @BeforeEach
    @DisplayName("Given three default Text objects")
    void givenTheeDefaultTextObjects()
    {
        this.shape1 = new Text();
        this.shape2 = new Text();
        this.shape3 = new Text();
    }

    @Test
    @DisplayName("When a user creates a Drawing with Text, then its SVG contains a text element opening tag")
    void whenAUserCreatesADrawingWithTextThenItsSVGContainsATextElementOpeningTag()
    {
        @NotNull final Text text = new Text("Drawl");
        this.drawing.add(text);
        this.drawing.setExplicitDimensions(100, 100);

        then(this.drawing.getSVG()).contains("<text");
    }

    @Test
    @DisplayName("When a user creates a Drawing with Text, then its SVG contains a text element opening tag")
    void whenAUserCreatesADrawingWithEmptyTextThenItsSVGDoesNotContainATextElementOpeningTag()
    {
        @NotNull final Text text = new Text();
        this.drawing.add(text);
        this.drawing.setExplicitDimensions(100, 100);

        then(this.drawing.getSVG()).doesNotContain("<text");
    }

    @Test
    @DisplayName("When a user creates a Drawing with Text, then the Text is placed at midline by default")
    void whenAUserCreatesADrawingWithTextThenTheTextIsPlacedAtMidlineByDefault()
    {
        @NotNull final Text text = new Text("Drawl");
        this.drawing.add(text);
        this.drawing.setExplicitDimensions(100, 100);
        then(this.drawing.getSVG()).contains("y='50'");
    }

} // end class