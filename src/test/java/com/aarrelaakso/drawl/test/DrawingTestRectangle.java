package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit tests of Drawing with Rectangle shapes")
public class DrawingTestRectangle extends DrawingTestShape {

    @BeforeEach
    @DisplayName("Given three default Circles")
    void givenTheeDefaultCircles() {
        shape1 = new Circle();
        shape2 = new Circle();
        shape3 = new Circle();
        DrawingTestRectangle.super.shape1 = shape1;
        DrawingTestRectangle.super.shape2 = shape2;
        DrawingTestRectangle.super.shape3 = shape3;
    }
}
