package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.Circle;
import com.aarrelaakso.drawl.Drawing;
import com.aarrelaakso.drawl.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
    @DisplayName("LENGTH: Adding a circle to an empty drawing gives the drawing a length of 1")
    void whenADrawingHasOneShapeThenItsLengthIs1() {
        drawing.add(shape1);
        assertEquals(new Integer(1), drawing.length());
    }

    @Test
    @DisplayName("HEIGHT - EXPLICIT: When a square (100) drawing has two adjacent Shapes, then their explicit heights are correct")
    void whenASquare100DrawingHasTwoAdjacentCirclesThenTheirExplicitHeightsAreCorrect() {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        drawing.setExplicitHeight(100);
        drawing.setExplicitWidth(100);
        BigDecimal explicitHeight1 = shape1.getExplicitHeight();
        BigDecimal explicitHeight2 = shape2.getExplicitHeight();

        assertEquals(explicitHeight1, BigDecimal.valueOf(50));
        assertEquals(explicitHeight2, BigDecimal.valueOf(50));
    }

    @Test
    @DisplayName("SVG: Calling getSVG without parameters does not throw an exception")
    void svgWhenYouCallSVGWithoutParametersItDoesNotThrowAnException() {
        assertDoesNotThrow( () -> { String svg = drawing.getSVG(); } );
    }

}
