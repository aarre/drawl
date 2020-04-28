package com.aarrelaakso.drawl;

import com.aarrelaakso.drawl.Rectangle;
import com.aarrelaakso.drawl.Shape;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Unit tests of Rectangle")
public class RectangleTest extends ShapeTest {

    Rectangle shape1;
    Rectangle shape2;
    Rectangle shape3;

    @BeforeEach
    void givenRectangles() {
        shape1 = new Rectangle();
        shape2 = new Rectangle();
        shape3 = new Rectangle();
        RectangleTest.super.shape1 = shape1;
        RectangleTest.super.shape2 = shape2;
        RectangleTest.super.shape3 = shape3;
    }

    @Nested
    @DisplayName("Given one default Rectangle SVG")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultRectangleSVG {

    }





}