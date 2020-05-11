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
import com.aarrelaakso.drawl.Measure;
import com.aarrelaakso.drawl.Point;
import com.aarrelaakso.drawl.Shape;
import com.google.common.flogger.FluentLogger;
import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the public API of Shapes.
 */
@ExtendWith(SoftAssertionsExtension.class)
@DisplayName("Unit tests of Shape public API (abstract)")
public abstract class ShapeTestPublic {

    private static final @NotNull FluentLogger logger;

    static {
        logger = FluentLogger.forEnclosingClass();
    }

    Shape shape1;
    Shape shape2;
    Shape shape3;

    @Test
    @DisplayName("When two shapes have been constructed, then it is possible to connect them with a Line")
    void whenTwoShapesHaveBeenConstructedThenItIsPossibleToConnectThemWithALine() {
        this.shape1.setLeftOf(this.shape2, this.shape1.getWidth());
        @NotNull final Point pointA = this.shape1.getRightPort();
        @NotNull final Point pointB = this.shape2.getLeftPort();
        @NotNull final Line line = new Line(pointA, pointB);
    }

    @Nested
    @DisplayName("Above - Below - Left - Right")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class AboveBelowLeftRight {

        @Test
        @DisplayName("When Shape A is above Shape B, then Shape A is above Shape B")
        void whenShapeAIsAboveShapeBThenShapeAIsAboveShapeB() {
            ShapeTestPublic.this.shape1.setAbove(ShapeTestPublic.this.shape2);
            final Shape shapeAbove = ShapeTestPublic.this.shape1.getAbove();
            then(shapeAbove).isEqualTo(ShapeTestPublic.this.shape2);
        }

        @Test
        @DisplayName("When Shape A is below Shape B, then Shape A is below Shape B")
        void whenShapeAIsBelowShapeBThenShapeAIsBelowShapeB() {
            ShapeTestPublic.this.shape1.setBelow(ShapeTestPublic.this.shape2);
            final Shape shapeBelow = ShapeTestPublic.this.shape1.getBelow();
            then(shapeBelow).isEqualTo(ShapeTestPublic.this.shape2);
        }

        @Test
        @DisplayName("When Shape A is left of Shape B, then Shape A is left of Shape B")
        void whenShapeAIsLeftOfShapeBThenShapeAIsLeftOfShapeB() {
            ShapeTestPublic.this.shape1.setLeftOf(ShapeTestPublic.this.shape2);
            then(ShapeTestPublic.this.shape1.getLeftOf()).isEqualTo(ShapeTestPublic.this.shape2);
        }

        @Test
        @DisplayName("When Shape A is right of Shape B, then Shape A is right of Shape B")
        void whenShapeAIsRightOfShapeBThenShapeAIsRightOfShapeB() {
            ShapeTestPublic.this.shape1.setRightOf(ShapeTestPublic.this.shape2);
            then(ShapeTestPublic.this.shape1.getRightOf()).isEqualTo(ShapeTestPublic.this.shape2);
        }

    }

    @Nested
    @DisplayName("Given three default shapes")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenThreeDefaultShapes {

        @Test
        @DisplayName("When you set three default Circles adjacent to one another, no exception is thrown")
        void whenYouSetThreeDefaultShapesAdjacentToOneAnotherThenNoExceptionIsThrown() {
            ShapeTestPublic.this.shape2.setRightOf(ShapeTestPublic.this.shape1);
            ShapeTestPublic.this.shape3.setRightOf(ShapeTestPublic.this.shape2);
        }
    }

    @Nested
    @DisplayName("Given two default Shapes")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenTwoDefaultShapes {

        @Test
        @DisplayName("When you set a Circle rightOf another circle, you can recall that information")
        void whenYouSetAShapeRightOfADefaultShapeThenYouCanRecallThatInformation() {
            ShapeTestPublic.this.shape1.setRightOf(ShapeTestPublic.this.shape2);
            assertEquals(ShapeTestPublic.this.shape1.getRightOf(), ShapeTestPublic.this.shape2);
        }

    }

    // Do not delete this even if empty; the subclass needs it
    @Nested
    @DisplayName("Given one default Shape SVG")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultShapeSVG {

        @Test
        @DisplayName("Then you cannot make it adjacent to itself")
        void thenYouCannotMakeItAdjacentToItself() {
            assertThrows(UnsupportedOperationException.class, () -> {
                ShapeTestPublic.this.shape1.setRightOf(ShapeTestPublic.this.shape1);
            });
        }
    }

    @Nested
    @DisplayName("Ports")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class Ports {

        @Test
        @DisplayName("whenAShapeIsCreatedThenItHasALeftPort")
        void whenAShapeIsCreatedThenItHasALeftPort(@NotNull final BDDSoftAssertions softly) {
            @NotNull final Point leftPort = ShapeTestPublic.this.shape1.getLeftPort();
            softly.then(leftPort).isNotNull();
            softly.then(leftPort).isInstanceOf(Point.class);
        }

        @Test
        @DisplayName("whenAShapeIsCreatedThenItHasARightPort")
        void whenAShapeIsCreatedThenItHasARightPort(@NotNull final BDDSoftAssertions softly) {
            @NotNull final Point leftPort = ShapeTestPublic.this.shape1.getRightPort();
            softly.then(leftPort).isNotNull();
            softly.then(leftPort).isInstanceOf(Point.class);
        }

        @Test
        @DisplayName("whenAShapeIsCreatedThenItHasATopPort")
        void whenAShapeIsCreatedThenItHasATopPort(@NotNull final BDDSoftAssertions softly) {
            @NotNull final Point leftPort = ShapeTestPublic.this.shape1.getTopPort();
            softly.then(leftPort).isNotNull();
            softly.then(leftPort).isInstanceOf(Point.class);
        }

        @Test
        @DisplayName("whenAShapeIsCreatedThenItHasABottomPort")
        void whenAShapeIsCreatedThenItHasABottomPort(@NotNull final BDDSoftAssertions softly) {
            @NotNull final Point leftPort = ShapeTestPublic.this.shape1.getBottomPort();
            softly.then(leftPort).isNotNull();
            softly.then(leftPort).isInstanceOf(Point.class);
        }
    }

    /**
     * Tests getting the width of Shapes.
     */
    @Nested
    @DisplayName("Width")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class Width {

        @Test
        @DisplayName("When a shape is created, then you can get its width")
        void whenAShapeIsCreatedThenYouCanGetItsWidth(BDDSoftAssertions softly) {
            Measure width = shape1.getWidth();
            softly.then(width).isNotNull();
        }

        @Test
        @DisplayName("When a shape has been created, then you can set its width")
        void whenAShapeHasBeenCreatedThenYouCanSetItsWidth(BDDSoftAssertions softly) {
            Measure width = shape1.getWidth();
            shape2.setWidth(width);
            Measure width2 = shape2.getWidth();
            softly.then(width2).isEqualByComparingTo(width);
        }
    }
}
