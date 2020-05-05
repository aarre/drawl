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
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SoftAssertionsExtension.class)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@DisplayName("Shape Drawings - Public API")
public abstract class ShapeDrawingTestPublic {

    private static final FluentLogger logger;

    static {
        logger = FluentLogger.forEnclosingClass();

    }

    Drawing drawing;
    Shape shape1;
    Shape shape2;
    Shape shape3;

    @BeforeEach
    void givenADrawing() {
        drawing = new Drawing();
    }

    @Test
    @DisplayName("LENGTH: Adding a circle to an empty drawing gives the drawing a length of 1")
    void lengthWhenADrawingHasOneShapeThenItsLengthIs1() {
        drawing.add(shape1);
        assertEquals(new Integer(1), drawing.getLength());
    }

    @Test
    @DisplayName("SVG: Calling getSVG without parameters does not throw an exception")
    void svgWhenYouCallSVGWithoutParametersItDoesNotThrowAnException() {
        assertDoesNotThrow(() -> {
            String svg = drawing.getSVG();
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
        void whenTheFillIsNotSetThenItDoesNotAppearInTheSVG(BDDSoftAssertions softly) {
            drawing.add(shape1);
            String svg = drawing.getSVG(100, 100);
            softly.then(svg).doesNotContain("fill");
            softly.then(svg).doesNotContain("darkslategray");
        }

        @Test
        @Tag("fill")
        @Tag("svg")
        @Tag("public")
        @DisplayName("When the fill is set, then it appears in the SVG")
        void whenTheFillIsSetThenItAppearsInTheSVG(BDDSoftAssertions softly) {
            if (shape1.getClass() == Text.class) {
                ((Text) shape1).setString("Drawl");
            }
            drawing.add(shape1);
            shape1.setFill("darkslategray");
            String svg = drawing.getSVG(100, 100);
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
        void whenTheStrokeIsNotSetThenItDoesNotAppearInTheSVG(BDDSoftAssertions softly) {
            drawing.add(shape1);
            String svg = drawing.getSVG(100, 100);
            softly.then(svg).doesNotContain("stroke");
            softly.then(svg).doesNotContain("darkslategray");
        }

        @Test
        @Tag("stroke")
        @Tag("svg")
        @Tag("public")
        @DisplayName("When the stroke is set, then it appears in the SVG")
        void whenTheStrokeIsSetThenItAppearsInTheSVG(BDDSoftAssertions softly) {
            drawing.add(shape1);
            if (shape1.getClass() == Text.class) {
                ((Text) shape1).setString("Drawl");
            }
            shape1.setStroke("darkslategray");
            String svg = drawing.getSVG(100, 100);
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
            if (shape1.getClass() == Text.class) {
                // If the outer Shape is a Text object but does not have a String, then it will not be issued to
                // the SVG. Therefore, if the outer Shape is a Text object, we need to assign it a String.
                ((Text) shape1).setString("Lward");
            }
            Text text = new Text("Drawl");
            shape1.addText(text);
            drawing.add(shape1);
            drawing.setExplicitDimensions(100, 100);

            then(drawing.getSVG()).contains("Drawl");
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
            drawing.add(shape1);
            drawing.add(shape2);
            shape2.setRightOf(shape1, shape2.getWidth());
            if (shape1.getClass() == Circle.class) {
                then(drawing.getSVG(30, 10)).contains("x=\"25\"");
            } else if (shape1.getClass() == Rectangle.class) {
                then(drawing.getSVG(30, 10)).contains("x=\"20\"");
            } else if (shape1.getClass() == Text.class) {
                ((Text)shape1).setString("Drawl");
                ((Text)shape2).setString("Lward");
                then(drawing.getSVG(30, 10)).contains("x=\"25\"");
            }
        }

        @Test
        @DisplayName("When a Shape is set nonadjacent left of another shape, then...")
        void whenAShapeIsSetNonAdjacentLeftOfAnotherShapeThen() {
            drawing.add(shape1);
            drawing.add(shape2);
            shape2.setLeftOf(shape1, shape2.getWidth());
            if (shape1.getClass() == Circle.class) {
                then(drawing.getSVG(30, 10)).contains("x=\"25\"");
            } else if (shape1.getClass() == Rectangle.class) {
                then(drawing.getSVG(30, 10)).contains("x=\"20\"");
            } else if (shape1.getClass() == Text.class) {
                ((Text)shape1).setString("Drawl");
                ((Text)shape2).setString("Lward");
                then(drawing.getSVG(30, 10)).contains("x=\"25\"");
            }
        }

        @Test
        @DisplayName("When a Shape is set nonadjacent above another shape, then...")
        void whenAShapeIsSetNonAdjacentAboveAnotherShapeThen() {
            drawing.add(shape1);
            drawing.add(shape2);
            shape2.setAbove(shape1, shape2.getWidth());
            if (shape1.getClass() == Circle.class) {
                then(drawing.getSVG(10, 30)).contains("y=\"25\"");
            } else if (shape1.getClass() == Rectangle.class) {
                then(drawing.getSVG(10, 30)).contains("y=\"20\"");
            } else if (shape1.getClass() == Text.class) {
                ((Text)shape1).setString("Drawl");
                ((Text)shape2).setString("Lward");
                then(drawing.getSVG(10, 30)).contains("y=\"25\"");
            }
        }

        @Test
        @DisplayName("When a Shape is set nonadjacent below another shape, then...")
        void whenAShapeIsSetNonAdjacentBelowAnotherShapeThen() {
            drawing.add(shape1);
            drawing.add(shape2);
            shape2.setBelow(shape1, shape2.getWidth());
            if (shape1.getClass() == Circle.class) {
                then(drawing.getSVG(10, 30)).contains("y=\"25\"");
            } else if (shape1.getClass() == Rectangle.class) {
                then(drawing.getSVG(10, 30)).contains("y=\"20\"");
            } else if (shape1.getClass() == Text.class) {
                ((Text)shape1).setString("Drawl");
                ((Text)shape2).setString("Lward");
                then(drawing.getSVG(10, 30)).contains("y=\"25\"");
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
            Measure measure = shape1.getWidth();
            then(measure).isInstanceOf(Measure.class);
            then(measure).isNotEqualTo(0);
            then(measure).isEqualToComparingFieldByField(new Measure(1));
        }

        @Test
        @DisplayName("whenAUserGetsTheHeightOfADefaultShapeThenItIs1")
        void whenAUserGetsTheHeightOfADefaultShapeThenItIs1() {
            Measure measure = shape1.getHeight();
            then(measure).isInstanceOf(Measure.class);
            then(measure).isNotEqualTo(0);
            then(measure).isEqualToComparingFieldByField(new Measure(1));
        }
    }
}
