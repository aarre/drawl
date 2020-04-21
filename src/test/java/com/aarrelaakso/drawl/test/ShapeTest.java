package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.Circle;
import com.aarrelaakso.drawl.Shape;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests of Shape (abstract)")
public abstract class ShapeTest {

    Shape shape;
    Shape shape1;
    Shape shape2;
    Shape shape3;
    String svg;

    @Nested
    @DisplayName("Given one default Shape SVG")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultShapeSVG {

        @Test
        @DisplayName("Then the SVG is not null")
        void thenSVGIsNotNull() {
            assertNotNull(svg);
        }
    }

    @Nested
    @DisplayName("Given three default shapes")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenThreeDefaultShapes {

        @Test
        @DisplayName("When you set three default Circles adjacent to one another, no exception is thrown")
        void whenYouSetThreeDefaultShapesAdjacentToOneAnotherThenNoExceptionIsThrown() {
            shape2.setRightOf(shape1);
            shape3.setRightOf(shape2);
        }
    }

    @Nested
    @DisplayName("Given two default Shapes")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenTwoDefaultShapes {

        @Test
        @DisplayName("When you set a default Shape above a default Shape, its implicit y-coordinate is 1")
        void whenYouSetADefaultShapeAboveADefaultShapeItsImplicitYCoordinateIs1() {
            shape1.setAbove(shape2);
            BigDecimal y1 = shape1.getImplicitYPosition();
            BigDecimal y2 = shape2.getImplicitYPosition();
            assertEquals(0, BigDecimal.ONE.compareTo(y1));
            assertEquals(0, BigDecimal.ZERO.compareTo(y2));
        }

        @Test
        @DisplayName("When you set a default Shape below a default Shape, its implicit y-coordinate is -1")
        void whenYouSetADefaultShapeBelowADefaultShapeItsImplicitYCoordinateIsNeg1() {
            shape1.setBelow(shape2);
            BigDecimal y1 = shape1.getImplicitYPosition();
            BigDecimal y2 = shape2.getImplicitYPosition();
            assertEquals(0, y1.compareTo(BigDecimal.valueOf(-1)));
            assertEquals(0, y2.compareTo(BigDecimal.ZERO));
        }

        @Test
        @DisplayName("When you set a Shape leftOf a default Shape, its implicit x-coordinate is -1")
        void whenYouSetAShapeLeftOfADefaultShapeItsImplicitXCoordinateIsNeg1() {
            BigDecimal x10 = shape1.getImplicitXPosition();
            BigDecimal x20 = shape2.getImplicitXPosition();
            shape1.setLeftOf(shape2);
            BigDecimal x11 = shape1.getImplicitXPosition();
            BigDecimal x21 = shape2.getImplicitXPosition();
            assertEquals(0, x10.compareTo(BigDecimal.ZERO));
            assertEquals(0, x20.compareTo(BigDecimal.ZERO));
            assertEquals(0, x11.compareTo(BigDecimal.valueOf(-1)));
            assertEquals(0, x21.compareTo(BigDecimal.ZERO));
        }

        @Test
        @DisplayName("When you set a Shape rightOf another Shape, its implicit x-coordinate is 1")
        void whenYouSetAShapeRightOfADefaultShapeItsImplicitXCoordinateIs1() {
            BigDecimal x10 = shape1.getImplicitXPosition();
            BigDecimal x20 = shape2.getImplicitXPosition();
            shape1.setRightOf(shape2);
            BigDecimal x11 = shape1.getImplicitXPosition();
            BigDecimal x21 = shape2.getImplicitXPosition();
            assertEquals(0, x10.compareTo(BigDecimal.ZERO));
            assertEquals(0, x20.compareTo(BigDecimal.ZERO));
            assertEquals(0, x11.compareTo(BigDecimal.ONE));
            assertEquals(0, x21.compareTo(BigDecimal.ZERO));
        }

        @Test
        @DisplayName("When you set a Circle rightOf another circle, you can recall that information")
        void whenYouSetAShapeRightOfADefaultShapeThenYouCanRecallThatInformation() {
            shape1.setRightOf(shape2);
            assertEquals(shape1.getRightOf(), shape2);
        }

    }

    @Nested
    @DisplayName("Given one default Shape")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultShape {

        @Test
        @DisplayName("Then its explicit width is 1")
        void thenExplicitWidthIs1() {
            BigDecimal width = shape.getExplicitWidth();
            assertEquals(0, BigDecimal.ONE.compareTo(width));
        }

        @Test
        @DisplayName("Then its explicit x-coordinate is 0")
        void thenExplicitXCoordinateIs0() {
            BigDecimal x = shape.getExplicitXPosition();
            assertAll("The result should be exactly 0",
                    () -> assertEquals(BigDecimal.ZERO, x),
                    () -> assertEquals("0", x.toString())
            );
        }

        @Test
        @DisplayName("Then its explicit y-coordinate is 0")
        void thenExplicitYCoordinateIs0() {
            BigDecimal y = shape.getExplicitYPosition();
            assertAll("The y-coordinate of a default Circle should be exactly 0",
                    () -> assertEquals(BigDecimal.ZERO, y),
                    () -> assertEquals("0", y.toString())
            );
        }

        @Test
        @DisplayName("Then its implicit height is 1")
        void thenImplicitHeightIs1() {
            BigDecimal implicitHeight = shape.getImplicitHeight();
            assertEquals(1, implicitHeight.compareTo(BigDecimal.ZERO));
        }

        @Test
        @DisplayName("Then its implicit width is 1")
        void thenImplicitWidthIs1() {
            BigDecimal width = shape.getImplicitWidth();
            assertEquals(0, width.compareTo(BigDecimal.ONE));
        }

        @Test
        @DisplayName("Then its implicit x-coordinate is 0")
        void thenImplicitXPositionIs0() {
            BigDecimal x = shape.getImplicitXPosition();
            assertEquals(0, x.compareTo(BigDecimal.ZERO));
        }

        @Test
        @DisplayName("Then its implicit y-coordinate is 0")
        void thenImplicitYPositionIs0() {
            BigDecimal x = shape.getImplicitYPosition();
            assertEquals(0, x.compareTo(BigDecimal.ZERO));
        }

        @Test
        @DisplayName("The SVG generated by a Shape contains the x- and y-coordinates")
        void thenTheSVGGeneratedByACircleContainsXAndYCoordinates() {
            int x = 50;
            int y = 50;
            shape.setExplicitXPosition(x);
            shape.setExplicitYPosition(y);
            String svg = shape.getSVG();
            assertTrue(svg.indexOf("cx=\"50\"") > -1);
            assertTrue(svg.indexOf("cy=\"50\"") > -1);
        }

        @Test
        @DisplayName("Then you can set its x-coordinate and get it back")
        void thenYouCanSetItsXCoordinateAndGetItBack() {
            shape.setExplicitXPosition(100);
            BigDecimal x = shape.getExplicitXPosition();
            assertEquals(0, x.compareTo(BigDecimal.valueOf(100)));
        }

        @Test
        @DisplayName("Then you cannot make it adjacent to itself")
        void thenYouCannotMakeItAdjacentToItself() {
            assertThrows(UnsupportedOperationException.class, () -> {
                shape.setRightOf(shape);
            });
        }
    }


}
