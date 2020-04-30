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

import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Tests the protected API for Shapes on Drawings.
 */
@ExtendWith(SoftAssertionsExtension.class)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@DisplayName("Tests the protected API for Shapes on Drawings")
public abstract class DrawingTestShapeProtected
{
    Drawing drawing;
    Shape shape1;
    Shape shape2;
    Shape shape3;

    @BeforeEach
    void givenADrawing()
    {
        drawing = new Drawing();
    }

    @Test
    @Tag("explicit")
    @Tag("height")
    @DisplayName("HEIGHT - EXPLICIT When a square (100) drawing has one default shape, then setting its explicit height scales its explicit width to fit")
    void heightExplicitWhenASquare100DrawingHasOneDefaultShapeThenSettingItsExplicitHeightScalesItsExplicitWidthToFit(@NotNull BDDSoftAssertions softly)
    {
        drawing.add(shape1);
        drawing.setExplicitHeight(100);
        SisuBigDecimal explicitHeightOfDrawing = drawing.getExplicitHeight();
        SisuBigDecimal explicitWidthOfDrawing = drawing.getExplicitWidth();
        SisuBigDecimal explicitHeightOfShape = shape1.getExplicitHeight();
        SisuBigDecimal explicitWidthOfShape = shape1.getExplicitWidth();
        SisuBigDecimal EXPECTED = SisuBigDecimal.valueOf(100);

        softly.then(explicitHeightOfDrawing).isEqualByComparingTo(EXPECTED);
        softly.then(explicitWidthOfDrawing).isEqualByComparingTo(EXPECTED);
        softly.then(explicitHeightOfShape).isEqualByComparingTo(EXPECTED);
        softly.then(explicitWidthOfShape).isEqualByComparingTo(EXPECTED);
    }

    @Test
    @Tag("explicit")
    @Tag("height")
    @DisplayName("HEIGHT - EXPLICIT When a square (100) drawing has one default shape, then setting its explicit width scales its explicit height to fit")
    void heightExplicitWhenASquare100DrawingHasOneDefaultShapeThenSettingItsExplicitWidthScalesItsExplicitHeightToFit(@NotNull BDDSoftAssertions softly)
    {
        Integer size = 100;
        SisuBigDecimal EXPECTED = SisuBigDecimal.valueOf(size);
        drawing.add(shape1);
        drawing.setExplicitWidth(size);
        SisuBigDecimal explicitHeightOfDrawing = drawing.getExplicitHeight();
        SisuBigDecimal explicitWidthOfDrawing = drawing.getExplicitWidth();
        SisuBigDecimal explicitHeightOfShape = shape1.getExplicitHeight();
        SisuBigDecimal explicitWidthOfShape = shape1.getExplicitWidth();

        softly.then(explicitHeightOfDrawing)
                .as("Expecting explicit height of drawing to be %s but got %s",
                        EXPECTED.toPlainString(), explicitHeightOfDrawing.toPlainString())
                .isEqualByComparingTo(EXPECTED);
        softly.then(explicitWidthOfDrawing)
                .as("Expecting explicit width of drawing to be %s but got %s",
                        EXPECTED.toPlainString(), explicitWidthOfDrawing.toPlainString())
                .isEqualByComparingTo(EXPECTED);
        softly.then(explicitHeightOfShape)
                .as("Expecting explicit height of shape to be %s but got %s",
                        EXPECTED.toPlainString(), explicitHeightOfShape.toPlainString())
                .isEqualByComparingTo(EXPECTED);
        softly.then(explicitWidthOfShape)
                .as("Expecting explicit width of shape to be %s but got %s",
                        EXPECTED.toPlainString(), explicitWidthOfShape.toPlainString())
                .isEqualByComparingTo(EXPECTED);
    }

    /**
     * This version of the test sets height first, then width second.
     *
     * @param softly
     */
    @Test
    @Tag("explicit")
    @Tag("height")
    @DisplayName("HEIGHT - EXPLICIT When a square (100) drawing has two adjacent Shapes, then their explicit heights are correct #1")
    void heightExplicitWhenASquare100DrawingHasTwoAdjacentShapesThenTheirExplicitHeightsAreCorrect01(@NotNull BDDSoftAssertions softly)
    {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitHeight(100);
        drawing.setExplicitWidth(100);
        SisuBigDecimal explicitHeight1 = shape1.getExplicitHeight();
        SisuBigDecimal explicitHeight2 = shape2.getExplicitHeight();
        SisuBigDecimal EXPECTED = SisuBigDecimal.valueOf(50);

        softly.then(explicitHeight1).isEqualByComparingTo(EXPECTED);
        softly.then(explicitHeight2).isEqualByComparingTo(EXPECTED);
    }

    /**
     * This version of the test sets width first, then height second.
     *
     * @param softly
     */
    @Test
    @Tag("explicit")
    @Tag("height")
    @DisplayName("HEIGHT - EXPLICIT When a square (100) drawing has two adjacent Shapes, then their explicit heights are correct #2")
    void heightExplicitWhenASquare100DrawingHasTwoAdjacentShapesThenTheirExplicitHeightsAreCorrect02(@NotNull BDDSoftAssertions softly)
    {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitWidth(100);
        drawing.setExplicitHeight(100);
        SisuBigDecimal explicitHeight1 = shape1.getExplicitHeight();
        SisuBigDecimal explicitHeight2 = shape2.getExplicitHeight();
        SisuBigDecimal EXPECTED = SisuBigDecimal.valueOf(50);

        softly.then(explicitHeight1).isEqualByComparingTo(EXPECTED);
        softly.then(explicitHeight2).isEqualByComparingTo(EXPECTED);
    }

    @Test
    @Tag("explicit")
    @Tag("height")
    @DisplayName("HEIGHT - EXPLICIT When a square (100) drawing has two adjacent Shapes, then their explicit heights are correct #3")
    void heightExplicitWhenASquare100DrawingHasTwoAdjacentShapesThenTheirExplicitHeightsAreCorrect03(BDDSoftAssertions softly)
    {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitDimensions(100, 100);
        SisuBigDecimal explicitHeight1 = shape1.getExplicitHeight();
        SisuBigDecimal explicitHeight2 = shape2.getExplicitHeight();
        SisuBigDecimal expectedHeight = SisuBigDecimal.valueOf(50);

        softly.then(explicitHeight1)
                .as("The explicit height of shape1 should be " + expectedHeight.toPlainString() + " but it was " + explicitHeight1.toString())
                .isEqualTo(expectedHeight);
        softly.then(explicitHeight2)
                .as("The explicit height of shape2 should be " + expectedHeight.toPlainString() + " but it was " + explicitHeight2.toString())
                .isEqualTo(expectedHeight);
    }

    @Test
    @DisplayName("SVG: The SVG generated by a drawing with a Circle contains the string 'circle'")
    void svgGeneratedByADrawingWithACircleContainsTheStringCircle()
    {
        int radius = 4000;
        Circle circle = new Circle(SisuBigDecimal.valueOf(radius));
        drawing.add(circle);
        String svg = drawing.getSVG(100, 100);
        then(svg).contains("circle");
    }

    @Test
    @DisplayName("The SVG generated by a Shape contains the x- and y-coordinates")
    void thenTheSVGGeneratedByAShapeContainsXAndYCoordinates(@NotNull BDDSoftAssertions softly)
    {
        int x = 50;
        int y = 50;
        drawing.add(shape1);
        drawing.add(shape2);
        drawing.setExplicitDimensions(100, 100);
        shape1.setExplicitXPositionCenter(x);
        shape1.setExplicitYPosition(y);
        String svg = drawing.getSVG();
        softly.then(svg).contains("x=\"50\"")
                .contains("y=\"50\"");
    }


    @Test
    @DisplayName("WIDTH - EXPLICIT: When a drawing has one default Shape, the explicit width per implicit width is the explicit width of the drawing")
    void widthExplicitWhenADrawingHasOneDefaultShapeThenTheExplicitWidthPerObjectIsTheExplicitWidthOfTheDrawing()
    {
        Integer size = 100;
        SisuBigDecimal EXPECTED = SisuBigDecimal.valueOf(size);
        drawing.add(shape1);
        drawing.setExplicitWidth(size);
        drawing.setExplicitHeight(size);
        SisuBigDecimal widthPerObject = drawing.getExplicitWidthPerImplicitWidth();

        then(widthPerObject).isEqualByComparingTo(EXPECTED);
    }

    @Test
    @Tag("explicit")
    @Tag("width")
    @DisplayName("WIDTH - EXPLICIT: When a drawing has one default Shape, the Shape's explicit width is the explicit width of the drawing")
    void widthExplicitWhenASquare100DrawingHasOneDefaultShapeThenItsExplicitWidthIsTheExplicitWidthOfTheDrawing()
    {
        drawing.add(shape1);
        drawing.setExplicitWidth(100);
        drawing.setExplicitHeight(100);
        SisuBigDecimal shapeExplicitWidth = shape1.getExplicitWidth();

        then(shapeExplicitWidth).isEqualByComparingTo(SisuBigDecimal.valueOf(100));
    }

    @Test
    @Tag("explicit")
    @Tag("width")
    @DisplayName("WIDTH - EXPLICIT: When a square (100) drawing has two adjacent Shapes, then their explicit widths are correct")
    void widthExplicitWhenASquare100DrawingHasTwoAdjacentShapesThenTheirExplicitWidthsAreCorrect(BDDSoftAssertions softly)
    {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitHeight(100);
        drawing.setExplicitWidth(100);
        SisuBigDecimal explicitWidth1 = shape1.getExplicitWidth();
        SisuBigDecimal explicitWidth2 = shape2.getExplicitWidth();

        softly.then(explicitWidth1).isEqualByComparingTo(SisuBigDecimal.valueOf(50));
        softly.then(explicitWidth2).isEqualByComparingTo(SisuBigDecimal.valueOf(50));
    }

    @Test
    @Tag("explicit")
    @Tag("y-position")
    @DisplayName("Y-POSITION - EXPLICIT: When a square (100) drawing has a default Shape, then its explicit y position is correct")
    void yPositionExplicitWhenASquare100DrawingHasOneDefaultShapeThenItsExplicitYPositionIsCorrect()
    {
        Integer size = 100;
        drawing.add(shape1);
        drawing.setExplicitDimensions(size, size);
        SisuBigDecimal yPosition = shape1.getExplicitYPositionCenter();
        SisuBigDecimal EXPECTED = SisuBigDecimal.valueOf(50);

        then(yPosition).isEqualByComparingTo(EXPECTED);
    }

    /**
     * This variant tests the effect of this order:
     * 1. Adding the shapes to the drawing.
     * 2. Setting the shapes adjacent to one another.
     * 3. Setting the size of the drawing (height first).
     *
     * @param softly
     */
    @Test
    @Tag("explicit")
    @Tag("y-position")
    @DisplayName("Y-POSITION - EXPLICIT: When a square (100) drawing has two adjacent Shapes, then their explicit y positions are correct #1a")
    void yPositionExplicitWhenASquare100DrawingHasTwoAdjacentShapesThenTheirExplicitYPositionsAreCorrect01a(@NotNull BDDSoftAssertions softly)
    {

        Integer SIZE = 100;
        SisuBigDecimal EXPECTED_Y_POS = SisuBigDecimal.valueOf(SIZE).divide(SisuBigDecimal.TWO, SisuBigDecimal.mcOperations);

        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);

        drawing.setExplicitHeight(SIZE);
        softly.then(drawing.getExplicitToImplicitRatio())
                .as("After setExplicitHeight, the explicit to implicit ratio should be %d", SIZE)
                .isEqualByComparingTo(SisuBigDecimal.valueOf(SIZE));

        drawing.setExplicitWidth(SIZE);
        softly.then(drawing.getExplicitToImplicitRatio())
                .as("After setExplicitWidth, the explicit to implicit ratio should be %d", SIZE / 2.0)
                .isEqualByComparingTo(SisuBigDecimal.valueOf(SIZE / 2.0));

        SisuBigDecimal explicitYPosition1 = shape1.getExplicitYPositionCenter();
        softly.then(explicitYPosition1)
                .as("The y position of shape1 should be %s", EXPECTED_Y_POS.toPlainString())
                .isEqualByComparingTo(EXPECTED_Y_POS);

        SisuBigDecimal explicitYPosition2 = shape2.getExplicitYPositionCenter();
        softly.then(explicitYPosition2)
                .as("The y position of shape2 should be %s", EXPECTED_Y_POS.toPlainString())
                .isEqualByComparingTo(EXPECTED_Y_POS);
    }

    /**
     * This variant tests the effect of this order:
     * 1. Adding the shapes to the drawing.
     * 2. Setting the shapes adjacent to one another.
     * 3. Setting the size of the drawing (width first).
     *
     * @param softly
     */
    @Test
    @Tag("explicit")
    @Tag("y-position")
    @DisplayName("Y-POSITION - EXPLICIT: When a square (100) drawing has two adjacent Shapes, then their explicit y positions are correct #1b")
    void yPositionExplicitWhenASquare100DrawingHasTwoAdjacentShapesThenTheirExplicitYPositionsAreCorrect01b(@NotNull BDDSoftAssertions softly)
    {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitWidth(100);
        drawing.setExplicitHeight(100);
        SisuBigDecimal explicitYPosition1 = shape1.getExplicitYPositionCenter();
        SisuBigDecimal explicitYPosition2 = shape2.getExplicitYPositionCenter();
        SisuBigDecimal EXPECTED = SisuBigDecimal.valueOf(50);

        softly.then(explicitYPosition1).isEqualByComparingTo(EXPECTED);
        softly.then(explicitYPosition2).isEqualByComparingTo(EXPECTED);
    }

    @Test
    @Tag("explicit")
    @Tag("y-position")
    @DisplayName("Y-POSITION - EXPLICIT: When a square (100) drawing has two adjacent Shapes, then their explicit y positions are correct (with setExplicitDimensions)")
    void yPositionExplicitWhenASquare100DrawingHasTwoAdjacentShapesThenTheirExplicitYPositionsAreCorrectWithSetExplicitDimensions(@NotNull BDDSoftAssertions softly)
    {
        Integer sizeOfDrawing = 100;
        SisuBigDecimal EXPECTED = SisuBigDecimal.valueOf(sizeOfDrawing).divide(SisuBigDecimal.TWO, SisuBigDecimal.mcOperations);

        drawing.add(shape1);
        SisuBigDecimal implicitYPosition1 = shape1.getImplicitYPositionCenter();
        softly.then(implicitYPosition1)
                .as("The actual implicit y position of Shape 1 (" + implicitYPosition1 + ") should match the"
                        + " expected explicit y position: (" + 0 + ")")
                .isEqualByComparingTo(SisuBigDecimal.ZERO);

        drawing.add(shape2);
        SisuBigDecimal implicitYPosition2 = shape2.getImplicitYPositionCenter();
        softly.then(implicitYPosition2)
                .as("The actual implicit y position of Shape 2 (" + implicitYPosition2 + ") should match the"
                        + " expected explicit y position: (" + 0 + ")")
                .isEqualByComparingTo(SisuBigDecimal.ZERO);

        shape2.setRightOf(shape1);
        implicitYPosition1 = shape1.getImplicitYPositionCenter();
        softly.then(implicitYPosition1)
                .as("The actual implicit y position of Shape 1 (" + implicitYPosition1 + ") should match the"
                        + " expected explicit y position: (" + 0 + ")")
                .isEqualByComparingTo(SisuBigDecimal.ZERO);
        implicitYPosition2 = shape2.getImplicitYPositionCenter();
        softly.then(implicitYPosition2)
                .as("The actual implicit y position of Shape 2 (" + implicitYPosition2 + ") should match the"
                        + " expected explicit y position: (" + 0 + ")")
                .isEqualByComparingTo(SisuBigDecimal.ZERO);

        drawing.setExplicitDimensions(sizeOfDrawing, sizeOfDrawing);
        implicitYPosition1 = shape1.getImplicitYPositionCenter();
        softly.then(implicitYPosition1)
                .as("The actual implicit y position of Shape 1 (" + implicitYPosition1 + ") should match the"
                        + " expected explicit y position: (" + 0 + ")")
                .isEqualByComparingTo(SisuBigDecimal.ZERO);
        implicitYPosition2 = shape1.getImplicitYPositionCenter();
        softly.then(implicitYPosition2)
                .as("The actual implicit y position of Shape 2 (" + implicitYPosition2 + ") should match the"
                        + " expected explicit y position: (" + 0 + ")")
                .isEqualByComparingTo(SisuBigDecimal.ZERO);

        SisuBigDecimal explicitYPosition1 = shape1.getExplicitYPositionCenter();
        softly.then(explicitYPosition1)
                .as("The actual explicit y position of Shape 1 (" + explicitYPosition1 + ") should match the"
                        + " expected explicit y position: (" + EXPECTED + ")")
                .isEqualByComparingTo(EXPECTED);

        SisuBigDecimal explicitYPosition2 = shape2.getExplicitYPositionCenter();
        softly.then(explicitYPosition2)
                .as("The actual explicit y position of Shape 2 (" + explicitYPosition2 + ") should match the"
                        + " expected explicit y position: (" + EXPECTED + ")")
                .isEqualByComparingTo(EXPECTED);
    }

    @Nested
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    @DisplayName("X Position")
    class XPosition
    {

        @Nested
        @TestMethodOrder(MethodOrderer.Alphanumeric.class)
        @DisplayName("Explicit")
        class Explicit
        {

            @Tag("x-position")
            @Tag("explicit")
            @DisplayName("When a drawing has three adjacent default Shapes, then their explicit x-positions are correct (max size)")
                    void whenADrawingHasThreeAdjacentDefaultShapesThenTheirExplicitXPositionsAreCorrectMaxSize(BDDSoftAssertions softly)
            {
                drawing.add(shape1);
                drawing.add(shape2);
                drawing.add(shape3);
                shape2.setRightOf(shape1);
                shape3.setRightOf(shape2);
                Float width = Float.MAX_VALUE;
                 Float height = Float.MAX_VALUE;
                  drawing.setExplicitWidth(width);
                drawing.setExplicitHeight(height);
                SisuBigDecimal bigX1 = SisuBigDecimal.valueOf(width).divide(SisuBigDecimal.valueOf(6), SisuBigDecimal.mcOperations);
                SisuBigDecimal bigX2 = SisuBigDecimal.valueOf(width).divide(SisuBigDecimal.valueOf(2), SisuBigDecimal.mcOperations);
                SisuBigDecimal fraction = SisuBigDecimal.valueOf(5).divide(SisuBigDecimal.valueOf(6), SisuBigDecimal.mcOperations);
                SisuBigDecimal bigX3 = SisuBigDecimal.valueOf(width).multiply(fraction, SisuBigDecimal.mcOperations);
                float bigX1f = bigX1.floatValue();
                float bigX2f = bigX2.floatValue();
                float bigX3f = bigX3.floatValue();

                softly.then(Float.MAX_VALUE).isGreaterThanOrEqualTo(bigX1f);
                softly.then(Float.MAX_VALUE).isGreaterThanOrEqualTo(bigX2f);
                softly.then(Float.MAX_VALUE).isGreaterThanOrEqualTo(bigX3f);

                softly.then(bigX1f).isEqualTo(shape1.getExplicitXPositionCenter().floatValue());
                softly.then(bigX2f).isEqualTo(shape2.getExplicitXPositionCenter().floatValue());
                softly.then(bigX3f).isEqualTo(shape3.getExplicitXPositionCenter().floatValue());

            }

            @Tag("x-position")
            @Tag("explicit")
            @DisplayName("When a square (100) drawing has two adjacent Circles, then their explicit x positions are correct")
                    void whenASquare100DrawingHasTwoAdjacentCirclesThenTheirExplicitXPositionsAreCorrect(BDDSoftAssertions softly)
            {

                drawing.add(shape1);
                 drawing.add(shape2);
                shape2.setRightOf(shape1);
                drawing.setExplicitHeight(100);
                drawing.setExplicitWidth(100);
                SisuBigDecimal explicitXPosition1 = shape1.getExplicitXPositionCenter();
                SisuBigDecimal explicitXPosition2 = shape2.getExplicitXPositionCenter();

                then:
                softly.then(explicitXPosition1).isEqualTo(SisuBigDecimal.valueOf(25));
                softly.then(explicitXPosition2).equals(SisuBigDecimal.valueOf(75));
            }

            /**
             * Test that, when a Drawing has three adjacent Shapes, then their x positions are correct.
             * <p>
             * This convenience method allows passing the arguments as Integers.
             *
             * @param widthInteger  The width of the Drawing to be tested.
             * @param heightInteger The height of the Drawing to be tested.
             * @param softly        AssertJ behavior-driven development soft assertions.
             */
            void test3Shapes(Integer widthInteger, Integer heightInteger, @NotNull BDDSoftAssertions softly)
            {
                test3Shapes(widthInteger.floatValue(), heightInteger.floatValue(), softly);
            }

            /**
             * Test that, when a Drawing has three adjacent Shapes, then their x positions are correct.
             * <p>
             * This convenience method allows passing the arguments as Strings.
             *
             * @param widthString  The width of the Drawing to be tested.
             * @param heightString The height of the Drawing to be tested.
             * @param softly       AssertJ behavior-driven development soft assertions.
             */
            void test3Shapes(String widthString, String heightString, @NotNull BDDSoftAssertions softly)
            {
                test3Shapes(Float.parseFloat(widthString), Float.parseFloat(heightString), softly);
            }

            /**
             * Test that, when a Drawing has three adjacent Shapes, then their x positions are correct.
             * <p>
             * This method centralizes the test code.
             *
             * @param widthFloat  The width of the drawing to be tested
             * @param heightFloat The height of the drawing to be tested
             * @param softly      AssertJ behavior-driven development soft assertions
             */
            void test3Shapes(Float widthFloat, Float heightFloat, @NotNull BDDSoftAssertions softly)
            {
                drawing.add(shape1);
                drawing.add(shape2);
                drawing.add(shape3);
                shape2.setRightOf(shape1);
                shape3.setRightOf(shape2);
                drawing.setExplicitWidth(widthFloat);
                softly.then(drawing.getExplicitWidth().equals(SisuBigDecimal.valueOf(widthFloat)));
                drawing.setExplicitHeight(heightFloat);
                softly.then(drawing.getExplicitHeight().equals(SisuBigDecimal.valueOf(heightFloat)));

                // At this aspect ratio, the sizes of the shapes may be constrained by the height
                SisuBigDecimal heightSisuBigDecimal = SisuBigDecimal.valueOf(heightFloat);
                SisuBigDecimal widthSisuBigDecimal = SisuBigDecimal.valueOf(widthFloat);

                SisuBigDecimal aspectRatio = SisuBigDecimal.valueOf(widthFloat / heightFloat);

                SisuBigDecimal widthOfShapes;
                if (aspectRatio.isGreaterThan(3))
                {
                    widthOfShapes = heightSisuBigDecimal.multiply(SisuBigDecimal.valueOf(3));
                }
                else
                {
                    widthOfShapes = widthSisuBigDecimal;
                }

                SisuBigDecimal extraWidth = widthSisuBigDecimal.subtract(widthOfShapes);
                SisuBigDecimal extraWidthLeft = extraWidth.divide(SisuBigDecimal.TWO, SisuBigDecimal.mcOperations);

                SisuBigDecimal shape1ExpectedXPosition = widthOfShapes.divide(SisuBigDecimal.valueOf(6), SisuBigDecimal.mcOperations);
                if (aspectRatio.isGreaterThan(3))
                {
                    shape1ExpectedXPosition = shape1ExpectedXPosition.add(extraWidthLeft);
                }

                SisuBigDecimal shape1ExplicitXPosition = shape1.getExplicitXPositionCenter();
                softly.then(shape1ExplicitXPosition.compareToFuzzy(shape1ExpectedXPosition))
                        .as("Expecting the explicit x position of shape 1 to be " +
                                shape1ExpectedXPosition + " but it was " + shape1ExplicitXPosition).isEqualTo(0);

                SisuBigDecimal shape2ExpectedXPosition = widthOfShapes.divide(SisuBigDecimal.TWO, SisuBigDecimal.mcOperations);
                if (aspectRatio.isGreaterThan(3))
                {
                    shape2ExpectedXPosition = shape2ExpectedXPosition.add(extraWidthLeft);
                }

                SisuBigDecimal shape2ExplicitXPosition = shape2.getExplicitXPositionCenter();
                softly.then(shape2ExplicitXPosition.compareToFuzzy(shape2ExpectedXPosition))
                        .as("Expecting the explicit x position of shape 2 to be " +
                                shape2ExpectedXPosition + " but it was " + shape2ExplicitXPosition).isEqualTo(0);

                SisuBigDecimal fraction = SisuBigDecimal.valueOf(5).divide(SisuBigDecimal.valueOf(6), SisuBigDecimal.mcOperations);
                SisuBigDecimal shape3ExpectedXPosition = widthOfShapes.multiply(fraction, SisuBigDecimal.mcOperations);
                if (aspectRatio.isGreaterThan(3))
                {
                    shape3ExpectedXPosition = shape3ExpectedXPosition.add(extraWidthLeft);
                }
                SisuBigDecimal shape3ExplicitXPosition = shape3.getExplicitXPositionCenter();
                softly.then(shape3ExplicitXPosition.compareToFuzzy(shape3ExpectedXPosition))
                        .as("Expecting the explicit x position of shape 3 to be " +
                                shape3ExpectedXPosition + " but it was " + shape3ExplicitXPosition).isEqualTo(0);
            }

            /**
             * Test that, when a Drawing has one adjacent Shape, then its x position is correct.
             * <p>
             * This version uses Float.MAX_VALUE for the width and height of the Shape.
             *
             * @param softly Allows using JAssert softly.then assertions.
             */
            @Test
            @DisplayName("When a drawing has one default Shape, then its x-position is correct (Float.MAX_VALUE)")
            void whenADrawingHasOneShapeThenItsXPositionIsCorrectFloatMax(@NotNull BDDSoftAssertions softly)
            {
                // TODO [Issue No 16]
                drawing.add(shape1);
                Float widthFloat = Float.MAX_VALUE;
                Float heightFloat = Float.MAX_VALUE;
                drawing.setExplicitWidth(widthFloat);
                softly.then(drawing.getExplicitWidth().equals(SisuBigDecimal.valueOf(widthFloat)));
                drawing.setExplicitHeight(heightFloat);
                softly.then(drawing.getExplicitHeight().equals(SisuBigDecimal.valueOf(heightFloat)));

                SisuBigDecimal widthBigDecimal = SisuBigDecimal.valueOf(widthFloat);

                SisuBigDecimal shape1ExpectedXPosition = widthBigDecimal.divide(SisuBigDecimal.valueOf(2), SisuBigDecimal.mcOperations);
                SisuBigDecimal shape1ExplicitXPosition = shape1.getExplicitXPositionCenter();
                softly.then(shape1ExplicitXPosition.compareToFuzzy(shape1ExpectedXPosition))
                        .as("Expecting the explicit position of " + shape1.toString() + "to be " +
                                shape1ExpectedXPosition + " but it was " + shape1ExplicitXPosition).isEqualTo(0);

            }

            /**
             * Test that, when a Drawing has one adjacent Shape, then its x position is correct.
             * <p>
             * This version uses Float.MAX_VALUE - 1 for the width and height of the Shape.
             *
             * @param softly Allows using JAssert softly.then assertions.
             */
            @Test
            @DisplayName("When a drawing has one default Shape, then its x-position is correct (Float.MAX_VALUE - 1)")
            void whenADrawingHasOneShapeThenItsXPositionIsCorrectFloatMaxMinus1(@NotNull BDDSoftAssertions softly)
            {
                // TODO [Issue No 16]
                drawing.add(shape1);
                Float widthFloat = Float.MAX_VALUE - 1;
                Float heightFloat = Float.MAX_VALUE - 1;
                drawing.setExplicitWidth(widthFloat);
                softly.then(drawing.getExplicitWidth().equals(SisuBigDecimal.valueOf(widthFloat)));
                drawing.setExplicitHeight(heightFloat);
                softly.then(drawing.getExplicitHeight().equals(SisuBigDecimal.valueOf(heightFloat)));

                SisuBigDecimal widthBigDecimal = SisuBigDecimal.valueOf(widthFloat);

                SisuBigDecimal shape1ExpectedXPosition = widthBigDecimal.divide(SisuBigDecimal.valueOf(2), SisuBigDecimal.mcOperations);
                SisuBigDecimal shape1ExplicitXPosition = shape1.getExplicitXPositionCenter();
                softly.then(shape1ExplicitXPosition.compareToFuzzy(shape1ExpectedXPosition))
                        .as("Expecting the explicit position of " + shape1.toString() + "to be " +
                                shape1ExpectedXPosition + " but it was " + shape1ExplicitXPosition).isEqualTo(0);

            }

            /**
             * Test that, when a Drawing has one default Shape, then its x position is correct.
             * <p>
             * This version uses a couple of specific numbers that were chosen randomly but fail reliably for three
             * shapes. Do they also fail for one shape (which is easier to debug)?
             *
             * @param softly Allows using JAssert softly.then assertions.
             */
            @Test
            @DisplayName("When a drawing has one default Shape, then its x-position is correct (random failed)")
            void whenADrawingHasOneShapeThenItsXPositionIsCorrectRandomFailed(@NotNull BDDSoftAssertions softly)
            {
                // TODO [Issue No 16]
                drawing.add(shape1);
                Float widthFloat = Float.valueOf("2.149144E38");
                Float heightFloat = Float.valueOf("5.567761E37");
                drawing.setExplicitWidth(widthFloat);
                softly.then(drawing.getExplicitWidth().equals(SisuBigDecimal.valueOf(widthFloat)));
                drawing.setExplicitHeight(heightFloat);
                softly.then(drawing.getExplicitHeight().equals(SisuBigDecimal.valueOf(heightFloat)));

                SisuBigDecimal widthBigDecimal = SisuBigDecimal.valueOf(widthFloat);

                SisuBigDecimal shape1ExpectedXPosition = widthBigDecimal.divide(SisuBigDecimal.valueOf(2), SisuBigDecimal.mcOperations);
                SisuBigDecimal shape1ExplicitXPosition = shape1.getExplicitXPositionCenter();
                softly.then(shape1ExplicitXPosition.compareToFuzzy(shape1ExpectedXPosition))
                        .as("Expecting the explicit position of " + shape1.toString() + "to be " +
                                shape1ExpectedXPosition + " but it was " + shape1ExplicitXPosition).isEqualTo(0);

            }

            /**
             * Test that, when a Drawing has one default Shape, then its x position is correct.
             * <p>
             * This version uses low-value analogs of a couple of specific numbers that were chosen randomly but
             * fail reliably for three shapes. They also fail for one shape (which is easier to debug).
             *
             * @param softly Allows using JAssert softly.then assertions.
             */
            @Test
            @DisplayName("When a drawing has one default Shape, then its x-position is correct (random failed low value)")
            void whenADrawingHasOneShapeThenItsXPositionIsCorrectRandomFailedLowValue(@NotNull BDDSoftAssertions softly)
            {
                // TODO [Issue No 16]
                drawing.add(shape1);
                Float widthFloat = Float.valueOf("214");
                Float heightFloat = Float.valueOf("56");
                drawing.setExplicitWidth(widthFloat);
                softly.then(drawing.getExplicitWidth().equals(SisuBigDecimal.valueOf(widthFloat)));
                drawing.setExplicitHeight(heightFloat);
                softly.then(drawing.getExplicitHeight().equals(SisuBigDecimal.valueOf(heightFloat)));

                SisuBigDecimal widthBigDecimal = SisuBigDecimal.valueOf(widthFloat);

                SisuBigDecimal shape1ExpectedXPosition = widthBigDecimal.divide(SisuBigDecimal.valueOf(2), SisuBigDecimal.mcOperations);
                SisuBigDecimal shape1ExplicitXPosition = shape1.getExplicitXPositionCenter();
                softly.then(shape1ExplicitXPosition.compareToFuzzy(shape1ExpectedXPosition))
                        .as("Expecting the explicit position of " + shape1.toString() + "to be " +
                                shape1ExpectedXPosition + " but it was " + shape1ExplicitXPosition).isEqualTo(0);

            }

            /**
             * Test that, when a Drawing has one default Shape, then its x position is correct.
             * <p>
             * This version uses a couple of specific numbers that were chosen randomly but fail reliably for three
             * shapes. Do they also fail for one shape (which is easier to debug)?
             * <p>
             * This version also uses setExplicitDimensions() instead of setExplicitWidth() and setExplicitHeight().
             *
             * @param softly Allows using JAssert softly.then assertions.
             */
            @Test
            @DisplayName("When a drawing has one default Shape, then its x-position is correct (random failed) using setExplicitDimensions()")
            void whenADrawingHasOneShapeThenItsXPositionIsCorrectRandomFailedSetExplicitDimensions(@NotNull BDDSoftAssertions softly)
            {
                // TODO [Issue No 16]
                drawing.add(shape1);
                Float widthFloat = Float.valueOf("2.149144E38");
                Float heightFloat = Float.valueOf("5.567761E37");
                drawing.setExplicitDimensions(widthFloat, heightFloat);
                softly.then(drawing.getExplicitWidth().equals(SisuBigDecimal.valueOf(widthFloat)));
                softly.then(drawing.getExplicitHeight().equals(SisuBigDecimal.valueOf(heightFloat)));

                SisuBigDecimal widthBigDecimal = SisuBigDecimal.valueOf(widthFloat);

                SisuBigDecimal shape1ExpectedXPosition = widthBigDecimal.divide(SisuBigDecimal.valueOf(2), SisuBigDecimal.mcOperations);
                SisuBigDecimal shape1ExplicitXPosition = shape1.getExplicitXPositionCenter();
                softly.then(shape1ExplicitXPosition.compareToFuzzy(shape1ExpectedXPosition))
                        .as("Expecting the explicit position of " + shape1.toString() + "to be " +
                                shape1ExpectedXPosition + " but it was " + shape1ExplicitXPosition).isEqualTo(0);

            }

            @Tag("x-position")
            @Tag("explicit")
            @DisplayName("When a drawing has three adjacent default circles added sequentially, then their explicit x-positions are correct (fixed)")
            void whenADrawingHasThreeAdjacentDefaultCirclesAddedSequentiallyThenTheirExplicitXPositionsAreCorrectFixed(BDDSoftAssertions softly)
            {
                drawing.add(shape1);
                drawing.add(shape2);
                shape2.setRightOf(shape1);
                drawing.add(shape3);
                shape3.setRightOf(shape2);
                Integer width = 100;
                Integer height = 100;
                drawing.setExplicitWidth(width);
                drawing.setExplicitHeight(height);
                SisuBigDecimal bigWidth = SisuBigDecimal.valueOf(width);
                SisuBigDecimal bigX1 = bigWidth.divide(SisuBigDecimal.valueOf(6), SisuBigDecimal.mcOperations);
                SisuBigDecimal bigX2 = bigWidth.divide(SisuBigDecimal.valueOf(2), SisuBigDecimal.mcOperations);
                SisuBigDecimal fraction = SisuBigDecimal.valueOf(5).divide(SisuBigDecimal.valueOf(6), SisuBigDecimal.mcOperations);
                SisuBigDecimal bigX3 = bigWidth.multiply(fraction, SisuBigDecimal.mcOperations);

                SisuBigDecimal actualExplicitXPositionCenter1 = shape1.getExplicitXPositionCenter();
                SisuBigDecimal actualExplicitXPositionCenter2 = shape2.getExplicitXPositionCenter();
                SisuBigDecimal actualExplicitXPositionCenter3 = shape3.getExplicitXPositionCenter();

                then:
                softly.then(bigX1).isEqualTo(actualExplicitXPositionCenter1);
                softly.then(bigX2).isEqualTo(actualExplicitXPositionCenter2);
                softly.then(bigX3).isEqualTo(actualExplicitXPositionCenter3);
            }

            @Tag("x-position")
            @Tag("explicit")
            @DisplayName("When a drawing has three adjacent default Circles, then their explicit x-positions are correct (fixed)")
            void whenADrawingHasThreeAdjacentDefaultCirclesThenTheirExplicitXPositionsAreCorrectFixed(BDDSoftAssertions softly)
            {
                drawing.add(shape1);
                drawing.add(shape2);
                drawing.add(shape3);
                shape2.setRightOf(shape1);
                shape3.setRightOf(shape2);
                Integer width = 100;
                Integer height = 100;
                drawing.setExplicitWidth(width);
                drawing.setExplicitHeight(height);
                SisuBigDecimal bigWidth = SisuBigDecimal.valueOf(width);
                SisuBigDecimal expectedExplicitXPositionCenter1 = bigWidth.divide(SisuBigDecimal.valueOf(6), SisuBigDecimal.mcOperations);
                SisuBigDecimal expectedExplicitXPositionCenter2 = bigWidth.divide(SisuBigDecimal.valueOf(2), SisuBigDecimal.mcOperations);
                SisuBigDecimal fraction = SisuBigDecimal.valueOf(5).divide(SisuBigDecimal.valueOf(6), SisuBigDecimal.mcOperations);
                SisuBigDecimal expectedExplicitXPositionCenter3 = bigWidth.multiply(fraction, SisuBigDecimal.mcOperations);

                SisuBigDecimal actualExplicitXPositionCenter1 = shape1.getExplicitXPositionCenter();
                SisuBigDecimal actualExplicitXPositionCenter2 = shape2.getExplicitXPositionCenter();
                SisuBigDecimal actualExplicitXPositionCenter3 = shape3.getExplicitXPositionCenter();

                softly.then(actualExplicitXPositionCenter1).isEqualTo(expectedExplicitXPositionCenter1);
                softly.then(actualExplicitXPositionCenter2).isEqualTo(expectedExplicitXPositionCenter2);
                softly.then(actualExplicitXPositionCenter3).isEqualTo(expectedExplicitXPositionCenter3);
            }

            @Test
            @DisplayName("When a drawing has three adjacent default Shapes, then their x-positions are correct (random)")
            void whenADrawingHasThreeAdjacentShapesThenTheirXPositionsAreCorrectRandom(@NotNull BDDSoftAssertions softly)
            {
                // TODO [Issue No 16]
                // TODO This method needs code from the others because it may randomly generate code in which the
                // height is less than 1/3 of the width.

                Double widthDouble = ThreadLocalRandom.current().nextDouble(0, Float.MAX_VALUE);
                Float widthFloat = widthDouble.floatValue();
                Double heightDouble = ThreadLocalRandom.current().nextDouble(0, Float.MAX_VALUE);
                Float heightFloat = heightDouble.floatValue();
                test3Shapes(widthFloat, heightFloat, softly);
            }

            /**
             * Test that, when a Drawing has three adjacent Shapes, then their x positions are correct.
             * <p>
             * This version uses a couple of specific numbers that were chosen randomly but fail reliably.
             *
             * @param softly Allows using JAssert softly.then assertions.
             */
            @Test
            @DisplayName("When a drawing has three adjacent default Shapes, then their x-positions are correct (random failed)")
            void whenADrawingHasThreeAdjacentShapesThenTheirXPositionsAreCorrectRandomFailed(@NotNull BDDSoftAssertions softly)
            {
                test3Shapes("2.149144E38", "5.567761E37", softly);

                // TODO [Issue No 16]
            }

            /**
             * Test that, when a Drawing has three adjacent Shapes, then their x positions are correct.
             * <p>
             * This version uses a couple of specific numbers that were chosen randomly but fail reliably.
             *
             * @param softly Allows using JAssert softly.then assertions.
             */
            @Test
            @DisplayName("When a drawing has three adjacent default Shapes, then their x-positions are correct (random failed no 2)")
            void whenADrawingHasThreeAdjacentShapesThenTheirXPositionsAreCorrectRandomFailed02(@NotNull BDDSoftAssertions softly)
            {
                // TODO [Issue No 16]
                test3Shapes("2.1890229E38", "2.6594734E38", softly);
            }

            /**
             * Test that, when a Drawing has three adjacent Shapes, then their x positions are correct.
             * <p>
             * This version uses a couple of specific numbers that were chosen randomly but fail reliably.
             *
             * @param softly Allows using JAssert softly.then assertions.
             */
            @Test
            @DisplayName("When a drawing has three adjacent default Shapes, then their x-positions are correct (random failed no 2 low value)")
            void whenADrawingHasThreeAdjacentShapesThenTheirXPositionsAreCorrectRandomFailed02LowValue(@NotNull BDDSoftAssertions softly)
            {
                test3Shapes(219, 266, softly);
                // TODO [Issue No 16]
            }

            /**
             * Test that, when a Drawing has three adjacent Shapes, then their x positions are correct.
             * <p>
             * This version uses low-value analogs of a couple of specific numbers that were chosen randomly but fail reliably.
             *
             * @param softly Allows using JAssert softly.then assertions.
             */
            @Test
            @DisplayName("When a drawing has three adjacent default Shapes, then their x-positions are correct (random failed - low value)")
            void whenADrawingHasThreeAdjacentShapesThenTheirXPositionsAreCorrectRandomFailedLowValue(@NotNull BDDSoftAssertions softly)
            {
                test3Shapes("214.9144", "55.67761", softly);
            }

            /**
             * Test that, when a Drawing has three adjacent Shapes, then their x positions are correct.
             * <p>
             * This version uses low-value analogs of a couple of specific numbers that were chosen randomly but fail reliably,
             * specifically choosing numbers that are easy to work with.
             *
             * @param softly Allows using JAssert softly.then assertions.
             */
            @Test
            @DisplayName("When a drawing has three adjacent default Shapes, then their x-positions are correct (low value - simple)")
            void whenADrawingHasThreeAdjacentShapesThenTheirXPositionsAreCorrectRandomFailedLowValueSimple(@NotNull BDDSoftAssertions softly)
            {
                test3Shapes("60", "19", softly);
            }

            @Tag("x-position")
            @Tag("implicit")
            @DisplayName("When a drawing has two adjacent default Circles, then their implicit x-positions are correct (fixed)")
            void whenADrawingHasTwoAdjacentDefaultCirclesThenTheirImplicitXPositionsAreCorrectFixed(BDDSoftAssertions softly)
            {
                drawing.add(shape1);
                drawing.add(shape2);
                shape2.setRightOf(shape1);

                softly.then(SisuBigDecimal.ZERO).isEqualTo(shape1.getImplicitXPositionCenter());
                softly.then(SisuBigDecimal.ONE).isEqualTo(shape2.getImplicitXPositionCenter());
            }

            @Test
            @DisplayName("X-POSITION - IMPLICIT: When a drawing has two adjacent default Circles, then their explicit x-positions are correct (fixed)")
            void whenADrawingHasTwoAdjacentDefaultShapesThenTheirExplicitXPositionsAreCorrectFixed(BDDSoftAssertions softly)
            {
                drawing.add(shape1);
                drawing.add(shape2);
                shape2.setRightOf(shape1);
                int width = 100;
                int height = 100;
                drawing.setExplicitWidth(width);
                drawing.setExplicitHeight(height);

                SisuBigDecimal EXPECTED_1 = SisuBigDecimal.valueOf(width / 4);
                SisuBigDecimal EXPECTED_2 = SisuBigDecimal.valueOf((3 * width) / 4);

                softly.then(EXPECTED_1).isEqualTo(shape1.getExplicitXPositionCenter());
                softly.then(EXPECTED_2).isEqualTo(shape2.getExplicitXPositionCenter());
            }
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    @DisplayName("Y Position")
    class YPosition
    {
        @Nested
        @TestMethodOrder(MethodOrderer.Alphanumeric.class)
        @DisplayName("Implicit")
        class Implicit
        {
            @Test
            @Tag("implicit")
            @Tag("y-position")
            @Tag("protected")
            @DisplayName("When a drawing has two adjacent default Circles, then their implicit y-positions are correct (fixed)")
            void whenADrawingHasTwoAdjacentDefaultCirclesThenTheirImplicitYPositionsAreCorrect(BDDSoftAssertions softly)
            {
                drawing.add(shape1);
                drawing.add(shape2);
                shape2.setAbove(shape1);

                softly.then(SisuBigDecimal.ZERO).isEqualTo(shape1.getImplicitYPositionCenter());
                softly.then(SisuBigDecimal.ONE).isEqualTo(shape2.getImplicitYPositionCenter());
            }
        }
    }

}
