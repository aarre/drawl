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

import com.aarrelaakso.drawl.*;
import com.google.common.flogger.FluentLogger;
import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SoftAssertionsExtension.class)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@DisplayName("Shape Drawings - Public API")
public abstract class ShapeDrawingTestPublic {

    private static final @NotNull FluentLogger logger;

    static {
        logger = FluentLogger.forEnclosingClass();

    }

    Drawing drawing;
    Shape shape1;
    Shape shape2;
    Shape shape3;

    @BeforeEach
    void givenADrawing() {
        this.drawing = new Drawing();
    }

    @Test
    @DisplayName("LENGTH: Adding a circle to an empty drawing gives the drawing a length of 1")
    void lengthWhenADrawingHasOneShapeThenItsLengthIs1() {
        this.drawing.add(this.shape1);
        assertEquals(new Integer(1), this.drawing.getLength());
    }

    @Test
    @DisplayName("SVG: Calling getSVG without parameters does not throw an exception")
    void svgWhenYouCallSVGWithoutParametersItDoesNotThrowAnException() {
        assertDoesNotThrow(() -> {
            @NotNull final String svg = this.drawing.getSVG();
        });
    }

    /**
     * Tests applying fill to Shapes
     */
    @Nested
    @DisplayName("Fill")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class Fill {

        @Test
        @Tag("fill")
        @Tag("svg")
        @Tag("public")
        @DisplayName("When the fill is not set, then it does not appear in the SVG")
        void whenTheFillIsNotSetThenItDoesNotAppearInTheSVG(@NotNull final BDDSoftAssertions softly) {
            ShapeDrawingTestPublic.this.drawing.add(ShapeDrawingTestPublic.this.shape1);
            @NotNull final String svg = ShapeDrawingTestPublic.this.drawing.getSVG(100, 100);
            softly.then(svg).doesNotContain("fill");
            softly.then(svg).doesNotContain("darkslategray");
        }

        @Test
        @Tag("fill")
        @Tag("svg")
        @Tag("public")
        @DisplayName("When the fill is set, then it appears in the SVG")
        void whenTheFillIsSetThenItAppearsInTheSVG(@NotNull final BDDSoftAssertions softly) {
            if (ShapeDrawingTestPublic.this.shape1.getClass() == Text.class) {
                ((Text) ShapeDrawingTestPublic.this.shape1).setString("Drawl");
            }
            ShapeDrawingTestPublic.this.drawing.add(ShapeDrawingTestPublic.this.shape1);
            ShapeDrawingTestPublic.this.shape1.setFill("darkslategray");
            @NotNull final String svg = ShapeDrawingTestPublic.this.drawing.getSVG(100, 100);
            softly.then(svg).contains("fill");
            softly.then(svg).contains("darkslategray");
        }
    }

    /**
     * Tests applying stroke to Shapes
     */
    @Nested
    @DisplayName("Stroke")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class Stroke {


        @Test
        @Tag("stroke")
        @Tag("svg")
        @Tag("public")
        @DisplayName("When the stroke is not set, then it does not appear in the SVG")
        void whenTheStrokeIsNotSetThenItDoesNotAppearInTheSVG(@NotNull final BDDSoftAssertions softly) {
            ShapeDrawingTestPublic.this.drawing.add(ShapeDrawingTestPublic.this.shape1);
            @NotNull final String svg = ShapeDrawingTestPublic.this.drawing.getSVG(100, 100);
            softly.then(svg).doesNotContain("stroke");
            softly.then(svg).doesNotContain("darkslategray");
        }

        @Test
        @Tag("stroke")
        @Tag("svg")
        @Tag("public")
        @DisplayName("When the stroke is set, then it appears in the SVG")
        void whenTheStrokeIsSetThenItAppearsInTheSVG(@NotNull final BDDSoftAssertions softly) {
            ShapeDrawingTestPublic.this.drawing.add(ShapeDrawingTestPublic.this.shape1);
            if (ShapeDrawingTestPublic.this.shape1.getClass() == Text.class) {
                ((Text) ShapeDrawingTestPublic.this.shape1).setString("Drawl");
            }
            ShapeDrawingTestPublic.this.shape1.setStroke("darkslategray");
            @NotNull final String svg = ShapeDrawingTestPublic.this.drawing.getSVG(100, 100);
            softly.then(svg).contains("stroke");
            softly.then(svg).contains("darkslategray");
        }

    }

    /**
     * Tests adding Text to Shapes
     */
    @Nested
    @DisplayName("Text")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class TextTests {

        @Test
        @DisplayName("When Text is added to a Shape, then it shows up in the SVG")
        void whenTextIsAddedToAShapeThenItShowsUpInTheSVG() {
            if (ShapeDrawingTestPublic.this.shape1.getClass() == Text.class) {
                // If the outer Shape is a Text object but does not have a String, then it will not be issued to
                // the SVG. Therefore, if the outer Shape is a Text object, we need to assign it a String.
                ((Text) ShapeDrawingTestPublic.this.shape1).setString("Lward");
            }
            @NotNull final Text text = new Text("Drawl");
            ShapeDrawingTestPublic.this.shape1.addText(text);
            ShapeDrawingTestPublic.this.drawing.add(ShapeDrawingTestPublic.this.shape1);
            ShapeDrawingTestPublic.this.drawing.setExplicitDimensions(100, 100);

            then(ShapeDrawingTestPublic.this.drawing.getSVG()).contains("Drawl");
        }

    }

    /**
     * Tests setting Shapes non-adjacent to other Shapes
     */
    @Nested
    @DisplayName("Non-adjacent Positioning")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class NonAdjacentTests {
        @Test
        @DisplayName("When a Shape is set nonadjacent right of another shape, then...")
        void whenAShapeIsSetNonAdjacentRightOfAnotherShapeThen() {
            ShapeDrawingTestPublic.this.drawing.add(ShapeDrawingTestPublic.this.shape1);
            ShapeDrawingTestPublic.this.drawing.add(ShapeDrawingTestPublic.this.shape2);
            ShapeDrawingTestPublic.this.shape2.setRightOf(ShapeDrawingTestPublic.this.shape1, ShapeDrawingTestPublic.this.shape2.getWidth());
            if (ShapeDrawingTestPublic.this.shape1.getClass() == Circle.class) {
                then(ShapeDrawingTestPublic.this.drawing.getSVG(30, 10)).contains("x=\"25\"");
            } else if (ShapeDrawingTestPublic.this.shape1.getClass() == Rectangle.class) {
                then(ShapeDrawingTestPublic.this.drawing.getSVG(30, 10)).contains("x=\"20\"");
            } else if (ShapeDrawingTestPublic.this.shape1.getClass() == Text.class) {
                ((Text) ShapeDrawingTestPublic.this.shape1).setString("Drawl");
                ((Text) ShapeDrawingTestPublic.this.shape2).setString("Lward");
                then(ShapeDrawingTestPublic.this.drawing.getSVG(30, 10)).contains("x=\"25\"");
            }
        }

        @Test
        @DisplayName("When a Shape is set nonadjacent left of another shape, then...")
        void whenAShapeIsSetNonAdjacentLeftOfAnotherShapeThen() {
            ShapeDrawingTestPublic.this.drawing.add(ShapeDrawingTestPublic.this.shape1);
            ShapeDrawingTestPublic.this.drawing.add(ShapeDrawingTestPublic.this.shape2);
            ShapeDrawingTestPublic.this.shape2.setLeftOf(ShapeDrawingTestPublic.this.shape1, ShapeDrawingTestPublic.this.shape2.getWidth());
            if (ShapeDrawingTestPublic.this.shape1.getClass() == Circle.class) {
                then(ShapeDrawingTestPublic.this.drawing.getSVG(30, 10)).contains("x=\"25\"");
            } else if (ShapeDrawingTestPublic.this.shape1.getClass() == Rectangle.class) {
                then(ShapeDrawingTestPublic.this.drawing.getSVG(30, 10)).contains("x=\"20\"");
            } else if (ShapeDrawingTestPublic.this.shape1.getClass() == Text.class) {
                ((Text) ShapeDrawingTestPublic.this.shape1).setString("Drawl");
                ((Text) ShapeDrawingTestPublic.this.shape2).setString("Lward");
                then(ShapeDrawingTestPublic.this.drawing.getSVG(30, 10)).contains("x=\"25\"");
            }
        }

        @Test
        @DisplayName("When a Shape is set nonadjacent above another shape, then...")
        void whenAShapeIsSetNonAdjacentAboveAnotherShapeThen() {
            ShapeDrawingTestPublic.this.drawing.add(ShapeDrawingTestPublic.this.shape1);
            ShapeDrawingTestPublic.this.drawing.add(ShapeDrawingTestPublic.this.shape2);
            ShapeDrawingTestPublic.this.shape2.setAbove(ShapeDrawingTestPublic.this.shape1, ShapeDrawingTestPublic.this.shape2.getWidth());
            if (ShapeDrawingTestPublic.this.shape1.getClass() == Circle.class) {
                then(ShapeDrawingTestPublic.this.drawing.getSVG(10, 30)).contains("y=\"25\"");
            } else if (ShapeDrawingTestPublic.this.shape1.getClass() == Rectangle.class) {
                then(ShapeDrawingTestPublic.this.drawing.getSVG(10, 30)).contains("y=\"20\"");
            } else if (ShapeDrawingTestPublic.this.shape1.getClass() == Text.class) {
                ((Text) ShapeDrawingTestPublic.this.shape1).setString("Drawl");
                ((Text) ShapeDrawingTestPublic.this.shape2).setString("Lward");
                then(ShapeDrawingTestPublic.this.drawing.getSVG(10, 30)).contains("y=\"25\"");
            }
        }

        @Test
        @DisplayName("When a Shape is set nonadjacent below another shape, then...")
        void whenAShapeIsSetNonAdjacentBelowAnotherShapeThen() {
            ShapeDrawingTestPublic.this.drawing.add(ShapeDrawingTestPublic.this.shape1);
            ShapeDrawingTestPublic.this.drawing.add(ShapeDrawingTestPublic.this.shape2);
            ShapeDrawingTestPublic.this.shape2.setBelow(ShapeDrawingTestPublic.this.shape1, ShapeDrawingTestPublic.this.shape2.getWidth());
            if (ShapeDrawingTestPublic.this.shape1.getClass() == Circle.class) {
                then(ShapeDrawingTestPublic.this.drawing.getSVG(10, 30)).contains("y=\"25\"");
            } else if (ShapeDrawingTestPublic.this.shape1.getClass() == Rectangle.class) {
                then(ShapeDrawingTestPublic.this.drawing.getSVG(10, 30)).contains("y=\"20\"");
            } else if (ShapeDrawingTestPublic.this.shape1.getClass() == Text.class) {
                ((Text) ShapeDrawingTestPublic.this.shape1).setString("Drawl");
                ((Text) ShapeDrawingTestPublic.this.shape2).setString("Lward");
                then(ShapeDrawingTestPublic.this.drawing.getSVG(10, 30)).contains("y=\"25\"");
            }
        }
    }

    /**
     * Tests getting and setting Measures on Shapes
     */
    @Nested
    @DisplayName("Measures")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class MeasureTests {
        @Test
        @DisplayName("whenAUserGetsTheWidthOfADefaultShapeThenItIs1")
        void whenAUserGetsTheWidthOfADefaultShapeThenItIs1() {
            @NotNull final Measure measure = ShapeDrawingTestPublic.this.shape1.getWidth();
            then(measure).isInstanceOf(Measure.class);
            then(measure).isNotEqualTo(0);
            then(measure).isEqualToComparingFieldByField(new Measure(1));
        }

        @Test
        @DisplayName("whenAUserGetsTheHeightOfADefaultShapeThenItIs1")
        void whenAUserGetsTheHeightOfADefaultShapeThenItIs1() {
            @NotNull final Measure measure = ShapeDrawingTestPublic.this.shape1.getHeight();
            then(measure).isInstanceOf(Measure.class);
            then(measure).isNotEqualTo(0);
            then(measure).isEqualToComparingFieldByField(new Measure(1));
        }
    }

    @Test
    @DisplayName("SVG: When you get SVG twice from a Drawing, it is the same both times")
    void whenYouGetSVGTwiceFromADrawingThenItIsTheSameBothTimes()
    {
        this.drawing.add(this.shape1);
        this.drawing.add(this.shape2);
        final String svg1 = this.drawing.getSVG(100, 100);
        final String svg2 = this.drawing.getSVG(100, 100);

        then(svg1).isEqualTo(svg2);
    }




}
