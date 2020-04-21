package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.Circle;
import com.aarrelaakso.drawl.Drawing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Unit tests of Drawing with Circle shapes")
public class DrawingTestCircle extends DrawingTestShape {

    Circle shape1;
    Circle shape2;
    Circle shape3;

    @BeforeEach
    @DisplayName("Given three default Circles")
    void givenTheeDefaultCircles() {
        shape1 = new Circle();
        shape2 = new Circle();
        shape3 = new Circle();
        DrawingTestCircle.super.shape1 = shape1;
        DrawingTestCircle.super.shape2 = shape2;
        DrawingTestCircle.super.shape3 = shape3;
    }

    @Test
    @DisplayName("RADIUS - EXPLICIT: When a square (100) drawing has two adjacent Circles, then their explicit radii are correct")
    void radiusExplicitWhenASquare100DrawingHasTwoAdjacentCirclesThenTheirExplicitRadiiAreCorrect() {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitHeight(100);
        drawing.setExplicitWidth(100);
        BigDecimal explicitRadius1 = shape1.getExplicitRadius();
        BigDecimal explicitRadius2 = shape2.getExplicitRadius();

        assertEquals(0, BigDecimal.valueOf(25).compareTo(explicitRadius1));
        assertEquals(0, BigDecimal.valueOf(25).compareTo(explicitRadius2));
    }

    @Test
    @DisplayName("RADIUS - IMPLICIT: When a square (100) drawing has two adjacent Circles, then their implicit radii are correct")
    void radiusImplicitWhenASquare100DrawingHasTwoAdjacentCirclesThenTheirImplicitRadiiAreCorrect() {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitHeight(100);
        drawing.setExplicitWidth(100);
        BigDecimal actualImplicitRadius1 = shape1.getImplicitRadius();
        BigDecimal actualImplicitRadius2 = shape2.getImplicitRadius();
        BigDecimal expectedValue = BigDecimal.valueOf(0.5);

        assertEquals(0, expectedValue.compareTo(actualImplicitRadius1));
        assertEquals(0, expectedValue.compareTo(actualImplicitRadius2));
    }

    @Test
    @DisplayName("SVG: If a default Circle is the only content of a drawing, then it is as large as possible")
    void svgWhenADefaultCircleIsTheOnlyContentOfADrawingThenItIsAsLargeAsPossible() {
        drawing.add(shape1);
        String svg = drawing.getSVG(100, 100);

        assertTrue(svg.contains("circle r=\"50\""));
    }

}
