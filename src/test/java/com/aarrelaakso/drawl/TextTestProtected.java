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

import com.google.common.flogger.FluentLogger;

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;

import static org.assertj.core.api.BDDAssertions.then;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Tests the protected API of Text objects.
 *
 * @author Aarre Laakso
 * @link https://github.com/aarre/drawl
 */
@DisplayName("Text - Protected API")
@ExtendWith(SoftAssertionsExtension.class)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class TextTestProtected
{
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    @Test
    @DisplayName("When a user creates a new Text object, then its implicit y position is correct")
    void whenAUserCreatesANewTextObjectThenItsImplicitYPositionIsCorrect()
    {
        @NotNull final Text text = new Text("Drawl");

        then(text.getImplicitYPositionCenter()).isEqualTo(DrawlNumber.ZERO);
    }
}