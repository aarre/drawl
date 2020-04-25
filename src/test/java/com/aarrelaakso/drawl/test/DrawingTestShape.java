package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.SisuBigDecimal;
import com.aarrelaakso.drawl.Drawing;
import com.aarrelaakso.drawl.Shape;
import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SoftAssertionsExtension.class)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@DisplayName("Unit tests of Drawing - Shape (abstract)")
public abstract class DrawingTestShape {

    Drawing drawing;
    Shape shape1;
    Shape shape2;
    Shape shape3;

    @BeforeEach
    void givenADrawing() {
        drawing = new Drawing();
    }

    @Test
    @Tag("explicit")
    @Tag("height")
    @DisplayName("HEIGHT - EXPLICIT When a square (100) drawing has one default shape, then setting its explicit height scales its explicit width to fit")
    void heightExplicitWhenASquare100DrawingHasOneDefaultShapeThenSettingItsExplicitHeightScalesItsExplicitWidthToFit(@NotNull BDDSoftAssertions softly) {
        drawing.add(shape1);
        drawing.setExplicitHeight(100);
        BigDecimal explicitHeightOfDrawing = drawing.getExplicitHeight();
        BigDecimal explicitWidthOfDrawing = drawing.getExplicitWidth();
        BigDecimal explicitHeightOfShape = shape1.getExplicitHeight();
        BigDecimal explicitWidthOfShape = shape1.getExplicitWidth();
        BigDecimal EXPECTED = BigDecimal.valueOf(100);

        softly.then(explicitHeightOfDrawing).isEqualByComparingTo(EXPECTED);
        softly.then(explicitWidthOfDrawing).isEqualByComparingTo(EXPECTED);
        softly.then(explicitHeightOfShape).isEqualByComparingTo(EXPECTED);
        softly.then(explicitWidthOfShape).isEqualByComparingTo(EXPECTED);
    }

    @Test
    @Tag("explicit")
    @Tag("height")
    @DisplayName("HEIGHT - EXPLICIT When a square (100) drawing has one default shape, then setting its explicit width scales its explicit height to fit")
    void heightExplicitWhenASquare100DrawingHasOneDefaultShapeThenSettingItsExplicitWidthScalesItsExplicitHeightToFit(@NotNull BDDSoftAssertions softly) {
        Integer size = 100;
        BigDecimal EXPECTED = BigDecimal.valueOf(size);
        drawing.add(shape1);
        drawing.setExplicitWidth(size);
        BigDecimal explicitHeightOfDrawing = drawing.getExplicitHeight();
        BigDecimal explicitWidthOfDrawing = drawing.getExplicitWidth();
        BigDecimal explicitHeightOfShape = shape1.getExplicitHeight();
        BigDecimal explicitWidthOfShape = shape1.getExplicitWidth();

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
    void heightExplicitWhenASquare100DrawingHasTwoAdjacentShapesThenTheirExplicitHeightsAreCorrect01(@NotNull BDDSoftAssertions softly) {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitHeight(100);
        drawing.setExplicitWidth(100);
        BigDecimal explicitHeight1 = shape1.getExplicitHeight();
        BigDecimal explicitHeight2 = shape2.getExplicitHeight();
        BigDecimal EXPECTED = BigDecimal.valueOf(50);

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
    void heightExplicitWhenASquare100DrawingHasTwoAdjacentShapesThenTheirExplicitHeightsAreCorrect02(@NotNull BDDSoftAssertions softly) {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitWidth(100);
        drawing.setExplicitHeight(100);
        BigDecimal explicitHeight1 = shape1.getExplicitHeight();
        BigDecimal explicitHeight2 = shape2.getExplicitHeight();
        BigDecimal EXPECTED = BigDecimal.valueOf(50);

        softly.then(explicitHeight1).isEqualByComparingTo(EXPECTED);
        softly.then(explicitHeight2).isEqualByComparingTo(EXPECTED);
    }

    @Test
    @Tag("explicit")
    @Tag("height")
    @DisplayName("HEIGHT - EXPLICIT When a square (100) drawing has two adjacent Shapes, then their explicit heights are correct #3")
    void heightExplicitWhenASquare100DrawingHasTwoAdjacentShapesThenTheirExplicitHeightsAreCorrect03(BDDSoftAssertions softly) {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitDimensions(100, 100);
        BigDecimal explicitHeight1 = shape1.getExplicitHeight();
        BigDecimal explicitHeight2 = shape2.getExplicitHeight();
        BigDecimal expectedHeight = BigDecimal.valueOf(50);

        assertAll("The explicit heights of both Shapes are correct",
                () -> assertEquals(0, explicitHeight1.compareTo(expectedHeight),
                        "The explicit height of shape1 should be " + expectedHeight.toPlainString() + " but it was " + explicitHeight1.toString()),
                () -> assertEquals(0, explicitHeight2.compareTo(expectedHeight),
                        "The explicit height of shape2 should be " + expectedHeight.toPlainString() + " but it was " + explicitHeight2.toString())
        );
    }

    @Test
    @DisplayName("LENGTH: Adding a circle to an empty drawing gives the drawing a length of 1")
    void lengthWhenADrawingHasOneShapeThenItsLengthIs1() {
        drawing.add(shape1);
        assertEquals(new Integer(1), drawing.length());
    }

    @Test
    @DisplayName("SVG: Calling getSVG without parameters does not throw an exception")
    void svgWhenYouCallSVGWithoutParametersItDoesNotThrowAnException() {
        assertDoesNotThrow(() -> {
            String svg = drawing.getSVG();
        });
    }

    @Test
    @DisplayName("WIDTH - EXPLICIT: When a drawing has one default Shape, the explicit width per implicit width is the explicit width of the drawing")
    void widthExplicitWhenADrawingHasOneDefaultShapeThenTheExplicitWidthPerObjectIsTheExplicitWidthOfTheDrawing() {
        Integer size = 100;
        BigDecimal EXPECTED = BigDecimal.valueOf(size);
        drawing.add(shape1);
        drawing.setExplicitWidth(size);
        drawing.setExplicitHeight(size);
        BigDecimal widthPerObject = drawing.getExplicitWidthPerImplicitWidth();

        then(widthPerObject).isEqualByComparingTo(EXPECTED);
    }

    @Test
    @Tag("explicit")
    @Tag("width")
    @DisplayName("WIDTH - EXPLICIT: When a drawing has one default Shape, the Shape's explicit width is the explicit width of the drawing")
    void widthExplicitWhenASquare100DrawingHasOneDefaultShapeThenItsExplicitWidthIsTheExplicitWidthOfTheDrawing() {
        drawing.add(shape1);
        drawing.setExplicitWidth(100);
        drawing.setExplicitHeight(100);
        BigDecimal shapeExplicitWidth = shape1.getExplicitWidth();

        then(shapeExplicitWidth).isEqualByComparingTo(BigDecimal.valueOf(100));
    }

    @Test
    @Tag("explicit")
    @Tag("width")
    @DisplayName("WIDTH - EXPLICIT: When a square (100) drawing has two adjacent Shapes, then their explicit widths are correct")
    void widthExplicitWhenASquare100DrawingHasTwoAdjacentShapesThenTheirExplicitWidthsAreCorrect(BDDSoftAssertions softly) {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitHeight(100);
        drawing.setExplicitWidth(100);
        BigDecimal explicitWidth1 = shape1.getExplicitWidth();
        BigDecimal explicitWidth2 = shape2.getExplicitWidth();

        softly.then(explicitWidth1).isEqualByComparingTo(BigDecimal.valueOf(50));
        softly.then(explicitWidth2).isEqualByComparingTo(BigDecimal.valueOf(50));
    }

    @Test
    @Tag("explicit")
    @Tag("y-position")
    @DisplayName("Y-POSITION - EXPLICIT: When a square (100) drawing has a default Shape, then its explicit y position is correct")
    void yPositionExplicitWhenASquare100DrawingHasOneDefaultShapeThenItsExplicitYPositionIsCorrect() {
        Integer size = 100;
        drawing.add(shape1);
        drawing.setExplicitDimensions(size, size);
        BigDecimal yPosition = shape1.getExplicitYPosition();
        BigDecimal EXPECTED = BigDecimal.valueOf(50);

        then(yPosition).isEqualByComparingTo(EXPECTED);
    }

    /**
     * This variant tests the effect of this order:
     *     1. Adding the shapes to the drawing.
     *     2. Setting the shapes adjacent to one another.
     *     3. Setting the size of the drawing (height first).
     *
     * @param softly
     */
    @Test
    @Tag("explicit")
    @Tag("y-position")
    @DisplayName("Y-POSITION - EXPLICIT: When a square (100) drawing has two adjacent Shapes, then their explicit y positions are correct #1a")
    void yPositionExplicitWhenASquare100DrawingHasTwoAdjacentShapesThenTheirExplicitYPositionsAreCorrect01a(@NotNull BDDSoftAssertions softly) {

        Integer SIZE = 100;
        BigDecimal EXPECTED_Y_POS = BigDecimal.valueOf(SIZE).divide(SisuBigDecimal.TWO, SisuBigDecimal.mcOperations);

        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);

        drawing.setExplicitHeight(SIZE);
        softly.then(drawing.getExplicitToImplicitRatio())
                .as("After setExplicitHeight, the explicit to implicit ratio should be %d", SIZE)
                .isEqualByComparingTo(BigDecimal.valueOf(SIZE));

        drawing.setExplicitWidth(SIZE);
        softly.then(drawing.getExplicitToImplicitRatio())
                .as("After setExplicitWidth, the explicit to implicit ratio should be %d", SIZE/2.0)
                .isEqualByComparingTo(BigDecimal.valueOf(SIZE/2.0));

        BigDecimal explicitYPosition1 = shape1.getExplicitYPosition();
        softly.then(explicitYPosition1)
                .as("The y position of shape1 should be %s", EXPECTED_Y_POS.toPlainString())
                .isEqualByComparingTo(EXPECTED_Y_POS);

        BigDecimal explicitYPosition2 = shape2.getExplicitYPosition();
        softly.then(explicitYPosition2)
                .as("The y position of shape2 should be %s", EXPECTED_Y_POS.toPlainString())
                .isEqualByComparingTo(EXPECTED_Y_POS);
    }

    /**
     * This variant tests the effect of this order:
     *     1. Adding the shapes to the drawing.
     *     2. Setting the shapes adjacent to one another.
     *     3. Setting the size of the drawing (width first).
     *
     * @param softly
     */
    @Test
    @Tag("explicit")
    @Tag("y-position")
    @DisplayName("Y-POSITION - EXPLICIT: When a square (100) drawing has two adjacent Shapes, then their explicit y positions are correct #1b")
    void yPositionExplicitWhenASquare100DrawingHasTwoAdjacentShapesThenTheirExplicitYPositionsAreCorrect01b(@NotNull BDDSoftAssertions softly) {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitWidth(100);
        drawing.setExplicitHeight(100);
        BigDecimal explicitYPosition1 = shape1.getExplicitYPosition();
        BigDecimal explicitYPosition2 = shape2.getExplicitYPosition();
        BigDecimal EXPECTED = BigDecimal.valueOf(50);

        softly.then(explicitYPosition1).isEqualByComparingTo(EXPECTED);
        softly.then(explicitYPosition2).isEqualByComparingTo(EXPECTED);
    }

    /**
     * This variant tests the effect of this order:
     *     1. Setting the size of the drawing.
     *     2. Adding the shapes to the drawing.
     *     3. Setting the shapes adjacent to each other.
     *
     * @param softly
     */
    @Test
    @Tag("explicit")
    @Tag("y-position")
    @DisplayName("Y-POSITION - EXPLICIT: When a square (100) drawing has two adjacent Shapes, then their explicit y positions are correct #2")
    void yPositionExplicitWhenASquare100DrawingHasTwoAdjacentShapesThenTheirExplicitYPositionsAreCorrect02(@NotNull BDDSoftAssertions softly) {
        drawing.setExplicitHeight(100);
        drawing.setExplicitWidth(100);
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        BigDecimal explicitYPosition1 = shape1.getExplicitYPosition();
        BigDecimal explicitYPosition2 = shape2.getExplicitYPosition();
        BigDecimal EXPECTED = BigDecimal.valueOf(50);

        softly.then(explicitYPosition1).isEqualByComparingTo(EXPECTED);
        softly.then(explicitYPosition2).isEqualByComparingTo(EXPECTED);
    }

    /**
     * This variant tests the effect of this order:
     *     1. Setting the size of the drawing.
     *     2. Setting the shapes adjacent to each other.
     *     3. Adding the shapes to the drawing.
     *
     * @param softly
     */
    @Test
    @Tag("explicit")
    @Tag("y-position")
    @DisplayName("Y-POSITION - EXPLICIT: When a square (100) drawing has two adjacent Shapes, then their explicit y positions are correct #3")
    void yPositionExplicitWhenASquare100DrawingHasTwoAdjacentShapesThenTheirExplicitYPositionsAreCorrect03(@NotNull BDDSoftAssertions softly) {
        drawing.setExplicitHeight(100);
        drawing.setExplicitWidth(100);

        shape2.setRightOf(shape1);

        drawing.add(shape1);
        drawing.add(shape2);

        BigDecimal explicitYPosition1 = shape1.getExplicitYPosition();
        BigDecimal explicitYPosition2 = shape2.getExplicitYPosition();
        BigDecimal EXPECTED = BigDecimal.valueOf(50);

        softly.then(explicitYPosition1).isEqualByComparingTo(EXPECTED);
        softly.then(explicitYPosition2).isEqualByComparingTo(EXPECTED);
    }

    @Test
    @Tag("explicit")
    @Tag("y-position")
    @DisplayName("Y-POSITION - EXPLICIT: When a square (100) drawing has two adjacent Shapes, then their explicit y positions are correct (with setExplicitDimensions)")
    void yPositionExplicitWhenASquare100DrawingHasTwoAdjacentShapesThenTheirExplicitYPositionsAreCorrectWithSetExplicitDimensions(@NotNull BDDSoftAssertions softly) {
        Integer sizeOfDrawing = 100;
        BigDecimal EXPECTED = BigDecimal.valueOf(sizeOfDrawing).divide(SisuBigDecimal.TWO, SisuBigDecimal.mcOperations);
        drawing.add(shape1);
        drawing.add(shape1);
        shape2.setRightOf(shape1);
        drawing.setExplicitDimensions(sizeOfDrawing, sizeOfDrawing);
        BigDecimal explicitYPosition1 = shape1.getExplicitYPosition();
        BigDecimal explicitYPosition2 = shape2.getExplicitYPosition();

        softly.then(explicitYPosition1).isEqualByComparingTo(EXPECTED);
        softly.then(explicitYPosition2).isEqualByComparingTo(EXPECTED);
    }

}
