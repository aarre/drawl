package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.Rectangle;
import org.assertj.core.api.BDDSoftAssertions;
import org.junit.jupiter.api.*;

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@DisplayName("Unit tests of Drawing with Rectangle shapes")
public class DrawingTestRectangle extends DrawingTestShape {

    @BeforeEach
    @DisplayName("Given three default Rectangles")
    void givenTheeDefaultRectangles() {
        shape1 = new Rectangle();
        shape2 = new Rectangle();
        shape3 = new Rectangle();
        DrawingTestRectangle.super.shape1 = shape1;
        DrawingTestRectangle.super.shape2 = shape2;
        DrawingTestRectangle.super.shape3 = shape3;
    }

    @Test
    @Tag("rectangle")
    @Tag("svg")
    @DisplayName("SVG: When a square (100) drawing has two adjacent Rectangles, then the SVG is correct")
    void svgWhenASquare100DrawingHasTwoAdjacentCirclesThenTheSVGIsCorrect(BDDSoftAssertions softly) {
        drawing.add(shape1);
        drawing.add(shape2);
        shape2.setRightOf(shape1);
        String svg = drawing.getSVG(100, 100);

        softly.then(svg).contains("<?xml version=\"1.0\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"100\" height=\"100\">")
                .contains("<rect height=\"50\" width=\"50\" cx=\"25\" cy=\"50\" />")
                .contains("<rect height=\"50\" width=\"50\" cx=\"75\" cy=\"50\" />")
                .contains("</svg>");
    }
}
