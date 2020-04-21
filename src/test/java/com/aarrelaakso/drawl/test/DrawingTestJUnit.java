package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.Circle;
import com.aarrelaakso.drawl.Drawing;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("JUnit 5 tests for Drawing")
public class DrawingTestJUnit {

    @Test
    @DisplayName("When you have a blank drawing, you can add a square")
    void firstTest() {

    }


    @Test
    @DisplayName("HEIGHT - EXPLICIT: When a square (100) drawing has two adjacent Circles, then their explicit heights are correct")
    void whenASquare100DrawingHasTwoAdjacentCirclesThenTheirExplicitHeightsAreCorrect() {
        Drawing drawing = new Drawing();
        Circle circle1 = new Circle();
        drawing.add(circle1);
        Circle circle2 = new Circle();
        drawing.add(circle2);
        circle2.setRightOf(circle1);
        drawing.setExplicitHeight(100);
        drawing.setExplicitWidth(100);
        BigDecimal explicitHeight1 = circle1.getExplicitHeight();
        BigDecimal explicitHeight2 = circle2.getExplicitHeight();

        assertEquals(explicitHeight1, BigDecimal.valueOf(50));
        assertEquals(explicitHeight2, BigDecimal.valueOf(50));
    }


}
