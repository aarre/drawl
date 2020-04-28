package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.Circle;
import com.aarrelaakso.drawl.Drawing;
import com.aarrelaakso.drawl.SisuBigDecimal;

import org.assertj.core.api.BDDSoftAssertions;

import org.jetbrains.annotations.NotNull;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.assertj.core.api.BDDAssertions.then;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@DisplayName("Unit tests of Drawing with Circle shapes")
public class DrawingTestCircle extends DrawingTestShape
{

    Circle shape1;
    Circle shape2;
    Circle shape3;

    @BeforeEach
    @DisplayName("Given three default Circles")
    void givenTheeDefaultCircles()
    {
        shape1 = new Circle();
        shape2 = new Circle();
        shape3 = new Circle();
        DrawingTestCircle.super.shape1 = shape1;
        DrawingTestCircle.super.shape2 = shape2;
        DrawingTestCircle.super.shape3 = shape3;
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
    @DisplayName("SVG: The SVG generated by a drawing with a Circle is exactly as expected")
    void svgGeneratedByADrawingWithACircleIsExactlyAsExpected()
    {
        drawing.add(shape1);
        String svg = drawing.getSVG(100, 100);

        then(svg).isEqualTo("<?xml version=\"1.0\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"100\" height=\"100\"><circle r=\"50\" cx=\"50\" cy=\"50\" /></svg>");
    }




    @Test
    @DisplayName("RADIUS - EXPLICIT: When a square (100) drawing has two adjacent Circles, then their explicit radii are correct")
    void radiusExplicitWhenASquare100DrawingHasTwoAdjacentCirclesThenTheirExplicitRadiiAreCorrect()
    {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitHeight(100);
        drawing.setExplicitWidth(100);
        SisuBigDecimal explicitRadius1 = shape1.getExplicitRadius();
        SisuBigDecimal explicitRadius2 = shape2.getExplicitRadius();

        assertEquals(0, SisuBigDecimal.valueOf(25).compareTo(explicitRadius1));
        assertEquals(0, SisuBigDecimal.valueOf(25).compareTo(explicitRadius2));
    }

    @Test
    @DisplayName("RADIUS - IMPLICIT: When a square (100) drawing has two adjacent Circles, then their implicit radii are correct")
    void radiusImplicitWhenASquare100DrawingHasTwoAdjacentCirclesThenTheirImplicitRadiiAreCorrect()
    {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitHeight(100);
        drawing.setExplicitWidth(100);
        SisuBigDecimal actualImplicitRadius1 = shape1.getImplicitRadius();
        SisuBigDecimal actualImplicitRadius2 = shape2.getImplicitRadius();
        SisuBigDecimal expectedValue = SisuBigDecimal.HALF;

        assertEquals(0, expectedValue.compareTo(actualImplicitRadius1));
        assertEquals(0, expectedValue.compareTo(actualImplicitRadius2));
    }

    @Test
    @DisplayName("SVG: If a default Circle is the only content of a drawing, then it is as large as possible")
    void svgWhenADefaultCircleIsTheOnlyContentOfADrawingThenItIsAsLargeAsPossible()
    {
        drawing.add(shape1);
        String svg = drawing.getSVG(100, 100);

        assertTrue(svg.contains("circle r=\"50\""));
    }

    @Test
    @DisplayName("SVG: When a square (100) drawing has two adjacent Circles, then the SVG is correct")
    void svgWhenASquare100DrawingHasTwoAdjacentCirclesThenTheSVGIsCorrect(BDDSoftAssertions softly)
    {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        String svg = drawing.getSVG(100, 100);

        softly.then(svg).contains("<?xml version=\"1.0\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"100\" height=\"100\">")
                .contains("<circle r=\"25\" cx=\"25\" cy=\"50\" />")
                .contains("<circle r=\"25\" cx=\"75\" cy=\"50\" />")
                .contains("</svg>");
    }

    @Test
    @DisplayName("The SVG generated by a Shape contains the x- and y-coordinates")
    @Override
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
        softly.then(svg).contains("cx=\"50\"")
                .contains("cy=\"50\"");
    }

}
