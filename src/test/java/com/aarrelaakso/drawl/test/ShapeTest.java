package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.Drawing;
import com.aarrelaakso.drawl.Shape;
import com.aarrelaakso.drawl.SisuBigDecimal;

import com.google.common.flogger.FluentLogger;
import org.assertj.core.api.BDDSoftAssertions;
import org.junit.jupiter.api.*;

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
            SisuBigDecimal y1 = shape1.getImplicitYPositionCenter();
            SisuBigDecimal y2 = shape2.getImplicitYPositionCenter();
            assertEquals(0, SisuBigDecimal.ONE.compareTo(y1));
            assertEquals(0, SisuBigDecimal.ZERO.compareTo(y2));
        }

        @Test
        @DisplayName("When you set a default Shape below a default Shape, its implicit y-coordinate is -1")
        void whenYouSetADefaultShapeBelowADefaultShapeItsImplicitYCoordinateIsNeg1() {
            shape1.setBelow(shape2);
            SisuBigDecimal y1 = shape1.getImplicitYPositionCenter();
            SisuBigDecimal y2 = shape2.getImplicitYPositionCenter();
            assertEquals(0, y1.compareTo(SisuBigDecimal.valueOf(-1)));
            assertEquals(0, y2.compareTo(SisuBigDecimal.ZERO));
        }

        @Test
        @DisplayName("When you set a Shape leftOf a default Shape, its implicit x-coordinate is -1")
        void whenYouSetAShapeLeftOfADefaultShapeItsImplicitXCoordinateIsNeg1() {
            SisuBigDecimal x10 = shape1.getImplicitXPositionCenter();
            SisuBigDecimal x20 = shape2.getImplicitXPositionCenter();
            shape1.setLeftOf(shape2);
            SisuBigDecimal x11 = shape1.getImplicitXPositionCenter();
            SisuBigDecimal x21 = shape2.getImplicitXPositionCenter();
            assertEquals(0, x10.compareTo(SisuBigDecimal.ZERO));
            assertEquals(0, x20.compareTo(SisuBigDecimal.ZERO));
            assertEquals(0, x11.compareTo(SisuBigDecimal.valueOf(-1)));
            assertEquals(0, x21.compareTo(SisuBigDecimal.ZERO));
        }

        @Test
        @DisplayName("When you set a Shape rightOf another Shape, its implicit x-coordinate is 1")
        void whenYouSetAShapeRightOfADefaultShapeItsImplicitXCoordinateIs1() {
            SisuBigDecimal x10 = shape1.getImplicitXPositionCenter();
            SisuBigDecimal x20 = shape2.getImplicitXPositionCenter();
            shape1.setRightOf(shape2);
            SisuBigDecimal x11 = shape1.getImplicitXPositionCenter();
            SisuBigDecimal x21 = shape2.getImplicitXPositionCenter();
            assertEquals(0, x10.compareTo(SisuBigDecimal.ZERO));
            assertEquals(0, x20.compareTo(SisuBigDecimal.ZERO));
            assertEquals(0, x11.compareTo(SisuBigDecimal.ONE));
            assertEquals(0, x21.compareTo(SisuBigDecimal.ZERO));
        }

        @Test
        @DisplayName("When you set a Circle rightOf another circle, you can recall that information")
        void whenYouSetAShapeRightOfADefaultShapeThenYouCanRecallThatInformation() {
            shape1.setRightOf(shape2);
            assertEquals(shape1.getRightOf(), shape2);
        }

    }

    // Do not delete this even if empty; the subclass needs it
    @Nested
    @DisplayName("Given one default Shape SVG")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultShapeSVG {

    }

    @Nested
    @DisplayName("Given one default Shape")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultShape {

        @Test
        @DisplayName("Then its explicit width is null")
        void thenExplicitWidthIsNull() {
            SisuBigDecimal width = shape1.getExplicitWidth();
            assertEquals(null, width, "The explicit width of a default Shape should be null.");
        }

        @Test
        @DisplayName("Then its explicit x-coordinate is 0")
        void thenExplicitXCoordinateIs0() {
            SisuBigDecimal x = shape1.getExplicitXPositionCenter();
            then(x).isEqualByComparingTo(SisuBigDecimal.ZERO);
            then("0").isEqualTo(x.toPlainString());
         }

        @Test
        @DisplayName("Then its explicit y-coordinate is 0")
        void thenExplicitYCoordinateIs0() {
            SisuBigDecimal y = shape1.getExplicitYPositionCenter();
            assertAll("The y-coordinate of a default Circle should be exactly 0",
                    () -> assertEquals(SisuBigDecimal.ZERO, y),
                    () -> assertEquals("0", y.toPlainString())
            );
        }

        @Test
        @DisplayName("Then its implicit height is 1")
        void thenImplicitHeightIs1() {
            SisuBigDecimal implicitHeight = shape1.getImplicitHeight();
            assertEquals(1, implicitHeight.compareTo(SisuBigDecimal.ZERO));
        }

        @Test
        @DisplayName("Then its implicit width is 1")
        void thenImplicitWidthIs1() {
            SisuBigDecimal width = shape1.getImplicitWidth();
            assertEquals(0, width.compareTo(SisuBigDecimal.ONE));
        }

        @Test
        @DisplayName("Then its implicit x-coordinate is 0")
        void thenImplicitXPositionIs0() {
            SisuBigDecimal x = shape1.getImplicitXPositionCenter();
            assertEquals(0, x.compareTo(SisuBigDecimal.ZERO));
        }

        @Test
        @DisplayName("Then its implicit y-coordinate is 0")
        void thenImplicitYPositionIs0() {
            SisuBigDecimal x = shape1.getImplicitYPositionCenter();
            assertEquals(0, x.compareTo(SisuBigDecimal.ZERO));
        }

        @Test
        @DisplayName("Then setting the height and then getting the height give the same result")
        void thenSettingTheHeightAndGettingTheHeightGiveTheSameResult() {
            SisuBigDecimal EXPECTED = SisuBigDecimal.valueOf(100);
            shape1.setExplicitHeight(EXPECTED);
            SisuBigDecimal ACTUAL = shape1.getExplicitHeight();

            then(EXPECTED).isEqualByComparingTo(ACTUAL);
        }

        @Test
        @DisplayName("Then setting the width and then getting the width give the same result")
        void thenSettingTheWidthAndGettingTheWidthGiveTheSameResult() {
            SisuBigDecimal EXPECTED = SisuBigDecimal.valueOf(100);
            shape1.setExplicitWidth(EXPECTED);
            SisuBigDecimal ACTUAL = shape1.getExplicitWidth();

            then(EXPECTED).isEqualByComparingTo(ACTUAL);
        }



        @Test
        @DisplayName("Then you can set its x-coordinate and get it back")
        void thenYouCanSetItsXCoordinateAndGetItBack() {
            shape1.setExplicitXPositionCenter(100);
            SisuBigDecimal x = shape1.getExplicitXPositionCenter();
            assertEquals(0, x.compareTo(SisuBigDecimal.valueOf(100)));
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

        @Nested
        @DisplayName("Explicit")
        @TestMethodOrder(MethodOrderer.Alphanumeric.class)
        class explicit
        {
            @Test
            @DisplayName("When a Shape is created, then it has an explicit center x position")
            void whenAShapeIsCreatedThenItHasAnExplicitCenterXPosition()
            {
                SisuBigDecimal explicitXPositionCenter = shape1.getExplicitXPositionCenter();
                then(explicitXPositionCenter).isEqualByComparingTo(SisuBigDecimal.ZERO);
            }

            @Test
            @DisplayName("When a Shape has explicit dimensions, then it has an explicit left x position")
            void whenAShapeIsCreatedThenItHasAnExplicitLeftXPosition()
            {
                shape1.setExplicitWidth(SisuBigDecimal.valueOf(100));
                SisuBigDecimal explicitXPositionLeft = shape1.getExplicitXPositionLeft();
                then(explicitXPositionLeft).isEqualByComparingTo(SisuBigDecimal.valueOf(-50));
            }
        }

        @Nested
        @DisplayName("Implicit")
        @TestMethodOrder(MethodOrderer.Alphanumeric.class)
        class implicit
        {
            @Test
            @DisplayName("When a Shape is created, then it has an implicit center x position")
            void whenAShapeIsCreatedThenItHasAnImplicitCenterXPosition()
            {
                SisuBigDecimal implicitXPositionCenter = shape1.getImplicitXPositionCenter();
                then(implicitXPositionCenter).isEqualByComparingTo(SisuBigDecimal.ZERO);
            }

            @Test
            @DisplayName("When a Shape is created, then it has an implicit left x position")
            void whenAShapeIsCreatedThenItHasAnImplicitLeftXPosition()
            {
                SisuBigDecimal implicitXPositionLeft = shape1.getImplicitXPositionLeft();
                then(implicitXPositionLeft).isEqualByComparingTo(SisuBigDecimal.valueOf(-0.5));
            }
        }

    }

    @Nested
    @DisplayName("y position")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class yPosition {

        @Nested
        @DisplayName("Explicit")
        @TestMethodOrder(MethodOrderer.Alphanumeric.class)
        class explicit
        {
            @Test
            @DisplayName("When a Shape has explicit dimensions, then it has an explicit top y position")
            void whenAShapeIsCreatedThenItHasAnExplicitTopXPosition()
            {
                Drawing drawing = new Drawing();
                drawing.add(shape1);
                drawing.setExplicitDimensions(100,100);
                //shape1.setExplicitHeight(SisuBigDecimal.valueOf(100));
                SisuBigDecimal explicitXPositionTop = shape1.getExplicitYPositionTop();
                then(explicitXPositionTop).isEqualByComparingTo(SisuBigDecimal.valueOf(0));
            }
        }

        @Nested
        @DisplayName("Implicit")
        @TestMethodOrder(MethodOrderer.Alphanumeric.class)
        class implicit
        {

            @Test
            @DisplayName("When a Shape is created, then it has an implicit center y position")
            void whenShapeIsCreatedThenItHasAnImplicitCenterYPosition() {
                SisuBigDecimal implicitYPositionCenter = shape1.getImplicitYPositionCenter();
                then(implicitYPositionCenter).isEqualByComparingTo(SisuBigDecimal.ZERO);
            }

            @Test
            @DisplayName("When a Shape is created, then it has an implicit top y position")
            void whenShapeIsCreatedThenItHasAnImplicitTopYPosition() {
                SisuBigDecimal implicitYPositionTop = shape1.getImplicitYPositionTop();
                then(implicitYPositionTop).isEqualByComparingTo(SisuBigDecimal.valueOf(-0.5));
            }
        }

    }




}
