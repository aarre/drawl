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

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Tests the public API of Shapes.
 */
@ExtendWith(SoftAssertionsExtension.class)
@DisplayName("Text Drawings - Protected API")
public class TextDrawingTestProtected extends ShapeDrawingTestProtected
{

    @BeforeEach
    @DisplayName("Given three default Rectangles")
    void givenTheeDefaultRectangles()
    {
        // These values override those in the superclass.
        shape1 = new Text();
        shape2 = new Text();
        shape3 = new Text();
    }

    @Test
    @DisplayName("When a user creates a Drawing with a Text object, then its explicit y position is correct")
    void whenAUserCreatesADrawingWithATextObjectThenItsExplicitYPositionIsCorrect()
    {
        Drawing drawing = new Drawing();
        Text text = new Text("Drawl");
        drawing.add(text);
        drawing.setExplicitDimensions(100,100);
        then(text.getExplicitYPositionCenter()).isEqualTo(DrawlNumber.valueOf(50));
    }

    @Test
    @DisplayName("When Text is added to a Shape, then the Text shows up in the SVG")
    void whenTextIsAddedToADrawingThenItInheritsAnXPosition()
    {
        if (shape1.getClass() == Text.class)
        {
            ((Text) shape1).setString("Drawl");
        }
        Text text = new Text("Drawl");
        shape1.addText(text);
        drawing.add(shape1);
        drawing.setExplicitDimensions(100, 100);

        then(drawing.getSVG()).contains("Drawl");
    }
}
