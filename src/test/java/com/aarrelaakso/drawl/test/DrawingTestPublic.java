package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.Drawing;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@DisplayName("Unit tests of Drawing without any shapes")
public class DrawingTestPublic
{

    Drawing drawing;

    @BeforeEach
    void givenADrawing() {
        this.drawing = new Drawing();
    }

    @Test
    @DisplayName("When a Drawing is empty, then its length is 0")
    void whenADrawingIsEmptyThenItsLengthIs1 () {
        @NotNull final Drawing drawing = new Drawing();
        assertEquals(0, drawing.getLength());
    }

    @Test
    @DisplayName("SVG: The SVG generated by a drawing contains the string 'svg'")
    void whenSVGIsGeneratedFromADrawingThenItContainsTheStringSvg() {
        @NotNull final String svg = this.drawing.getSVG(100, 100);

        assertTrue(svg.indexOf("svg") > -1);
    }

    @Test
    @DisplayName("SVG: The SVG generated by a drawing contains the width and height")
    void whenSVGIsGeneratedFromADrawingThenItContainsWidthAndHeight() {
        @NotNull final String svg = this.drawing.getSVG(100, 100);

        assertTrue(svg.indexOf("width='100'") > -1);
        assertTrue(svg.indexOf("height='100'") > -1);
    }

}
