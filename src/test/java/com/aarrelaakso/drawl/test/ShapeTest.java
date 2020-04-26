package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.Shape;
import com.google.common.flogger.FluentLogger;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests of Shape (abstract)")
public abstract class ShapeTest {

    private static final FluentLogger logger;

    static
    {
        logger = FluentLogger.forEnclosingClass();

    }

    Shape shape1;
    Shape shape2;
    Shape shape3;

    @Nested
    @DisplayName("Given one default Shape SVG")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultShapeSVG {

        @Test
        @DisplayName("Then the SVG is not null")
        void thenSVGIsNotNull() {
            String svg = shape1.getSVG();
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
            BigDecimal y1 = shape1.getImplicitYPositionCenter();
            BigDecimal y2 = shape2.getImplicitYPositionCenter();
            assertEquals(0, BigDecimal.ONE.compareTo(y1));
            assertEquals(0, BigDecimal.ZERO.compareTo(y2));
        }

        @Test
        @DisplayName("When you set a default Shape below a default Shape, its implicit y-coordinate is -1")
        void whenYouSetADefaultShapeBelowADefaultShapeItsImplicitYCoordinateIsNeg1() {
            shape1.setBelow(shape2);
            BigDecimal y1 = shape1.getImplicitYPositionCenter();
            BigDecimal y2 = shape2.getImplicitYPositionCenter();
            assertEquals(0, y1.compareTo(BigDecimal.valueOf(-1)));
            assertEquals(0, y2.compareTo(BigDecimal.ZERO));
        }

        @Test
        @DisplayName("When you set a Shape leftOf a default Shape, its implicit x-coordinate is -1")
        void whenYouSetAShapeLeftOfADefaultShapeItsImplicitXCoordinateIsNeg1() {
            BigDecimal x10 = shape1.getImplicitXPositionCenter();
            BigDecimal x20 = shape2.getImplicitXPositionCenter();
            shape1.setLeftOf(shape2);
            BigDecimal x11 = shape1.getImplicitXPositionCenter();
            BigDecimal x21 = shape2.getImplicitXPositionCenter();
            assertEquals(0, x10.compareTo(BigDecimal.ZERO));
            assertEquals(0, x20.compareTo(BigDecimal.ZERO));
            assertEquals(0, x11.compareTo(BigDecimal.valueOf(-1)));
            assertEquals(0, x21.compareTo(BigDecimal.ZERO));
        }

        @Test
        @DisplayName("When you set a Shape rightOf another Shape, its implicit x-coordinate is 1")
        void whenYouSetAShapeRightOfADefaultShapeItsImplicitXCoordinateIs1() {
            BigDecimal x10 = shape1.getImplicitXPositionCenter();
            BigDecimal x20 = shape2.getImplicitXPositionCenter();
            shape1.setRightOf(shape2);
            BigDecimal x11 = shape1.getImplicitXPositionCenter();
            BigDecimal x21 = shape2.getImplicitXPositionCenter();
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
        @DisplayName("Then its explicit width is null")
        void thenExplicitWidthIsNull() {
            BigDecimal width = shape1.getExplicitWidth();
            assertEquals(null, width, "The explicit width of a default Shape should be null.");
        }

        @Test
        @DisplayName("Then its explicit x-coordinate is 0")
        void thenExplicitXCoordinateIs0() {
            BigDecimal x = shape1.getExplicitXPosition();
            assertAll("The result should be exactly 0",
                    () -> assertEquals(BigDecimal.ZERO, x),
                    () -> assertEquals("0", x.toString())
            );
        }

        @Test
        @DisplayName("Then its explicit y-coordinate is 0")
        void thenExplicitYCoordinateIs0() {
            BigDecimal y = shape1.getExplicitYPosition();
            assertAll("The y-coordinate of a default Circle should be exactly 0",
                    () -> assertEquals(BigDecimal.ZERO, y),
                    () -> assertEquals("0", y.toString())
            );
        }

        @Test
        @DisplayName("Then its implicit height is 1")
        void thenImplicitHeightIs1() {
            BigDecimal implicitHeight = shape1.getImplicitHeight();
            assertEquals(1, implicitHeight.compareTo(BigDecimal.ZERO));
        }

        @Test
        @DisplayName("Then its implicit width is 1")
        void thenImplicitWidthIs1() {
            BigDecimal width = shape1.getImplicitWidth();
            assertEquals(0, width.compareTo(BigDecimal.ONE));
        }

        @Test
        @DisplayName("Then its implicit x-coordinate is 0")
        void thenImplicitXPositionIs0() {
            BigDecimal x = shape1.getImplicitXPositionCenter();
            assertEquals(0, x.compareTo(BigDecimal.ZERO));
        }

        @Test
        @DisplayName("Then its implicit y-coordinate is 0")
        void thenImplicitYPositionIs0() {
            BigDecimal x = shape1.getImplicitYPositionCenter();
            assertEquals(0, x.compareTo(BigDecimal.ZERO));
        }

        @Test
        @DisplayName("Then setting the height and then getting the height give the same result")
        void thenSettingTheHeightAndGettingTheHeightGiveTheSameResult() {
            BigDecimal EXPECTED = BigDecimal.valueOf(100);
            shape1.setExplicitHeight(EXPECTED);
            BigDecimal ACTUAL = shape1.getExplicitHeight();

            then(EXPECTED).isEqualByComparingTo(ACTUAL);
        }

        @Test
        @DisplayName("Then setting the width and then getting the width give the same result")
        void thenSettingTheWidthAndGettingTheWidthGiveTheSameResult() {
            BigDecimal EXPECTED = BigDecimal.valueOf(100);
            shape1.setExplicitWidth(EXPECTED);
            BigDecimal ACTUAL = shape1.getExplicitWidth();

            then(EXPECTED).isEqualByComparingTo(ACTUAL);
        }

        @Test
        @DisplayName("The SVG generated by a Shape contains the x- and y-coordinates")
        void thenTheSVGGeneratedByACircleContainsXAndYCoordinates() {
            int x = 50;
            int y = 50;
            shape1.setExplicitXPosition(x);
            shape1.setExplicitYPosition(y);
            String svg = shape1.getSVG();
            assertTrue(svg.indexOf("cx=\"50\"") > -1);
            assertTrue(svg.indexOf("cy=\"50\"") > -1);
        }

        @Test
        @DisplayName("Then you can set its x-coordinate and get it back")
        void thenYouCanSetItsXCoordinateAndGetItBack() {
            shape1.setExplicitXPosition(100);
            BigDecimal x = shape1.getExplicitXPosition();
            assertEquals(0, x.compareTo(BigDecimal.valueOf(100)));
        }

        @Test
        @DisplayName("Then you cannot make it adjacent to itself")
        void thenYouCannotMakeItAdjacentToItself() {
            assertThrows(UnsupportedOperationException.class, () -> {
                shape1.setRightOf(shape1);
            });
        }
    }

    @Nested
    @DisplayName("x position")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class xPosition {

        @Test
        @DisplayName("When a Shape is created, then it has an implicit center x position")
        void whenAShapeIsCreatedThenItHasAnImplicitCenterXPosition() {
            BigDecimal implicitXPositionCenter = shape1.getImplicitXPositionCenter();
            then(implicitXPositionCenter).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("When a Shape is created, then it has an implicit left x position")
        void whenAShapeIsCreatedThenItHasAnImplicitLeftXPosition() {
            BigDecimal implicitXPositionLeft = shape1.getImplicitXPositionLeft();
            then(implicitXPositionLeft).isEqualByComparingTo(BigDecimal.valueOf(-0.5));
        }

    }

    @Nested
    @DisplayName("y position")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class yPosition {

        @Test
        @DisplayName("When a Shape is created, then it has an implicit center y position")
        void whenShapeIsCreatedThenItHasAnImplicitCenterYPosition() {
            BigDecimal implicitYPositionCenter = shape1.getImplicitYPositionCenter();
            then(implicitYPositionCenter).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("When a Shape is created, then it has an implicit top y position")
        void whenShapeIsCreatedThenItHasAnImplicitTopYPosition() {
            BigDecimal implicitYPositionTop = shape1.getImplicitYPositionTop();
            then(implicitYPositionTop).isEqualByComparingTo(BigDecimal.valueOf(-0.5));
        }

    }




}
