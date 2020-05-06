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
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("Text - Public API")
public class TextTestPublic extends ShapeTestPublic
{
    @BeforeEach
    void givenText()
    {
        // These values override those in ShapeTestPublic
        shape1 = new Text();
        shape2 = new Text();
        shape3 = new Text();
    }

    @Test
    @DisplayName("When a user creates a Text object, then he can initialize it with a String")
    void whenAUserCreatesATextObjectThenHeCanInitializeItWithAString()
    {
        @NotNull Text text = new Text("Lorem ipsum");
        then(text.toString()).isEqualTo("Lorem ipsum");
    }

    @Test
    @DisplayName("When a user creates a Text object, then he can initialize it without a String")
    void whenAUserCreatesATextObjectThenHeCanInitializeItWithoutAString()
    {
        @NotNull Text text = new Text();
        then(text.toString()).isEqualTo(null);
    }


    @Test
    @DisplayName("When a user creates a Text object without explicit dimensions, then getting SVG throws an exception")
    void whenAUserCreatesATextObjectWithoutExplicitDimensionsThenGettingSVGThrowsAnException()
    {

        // when
        @NotNull Text text = new Text();
        Throwable thrown = catchThrowable(() -> {
            text.getSVG();
        });

        // then
        assertThat(thrown).isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("Cannot get SVG without setting explicit dimensions");

    }


}
