package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.Rectangle;
import com.aarrelaakso.drawl.Shape;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Unit tests of Rectangle")
public class RectangleTest extends ShapeTest {

    Rectangle shape;
    Rectangle shape1;
    Rectangle shape2;

    @BeforeEach
    void givenOneDefaultRectangle() {
        shape = new Rectangle();
        svg = shape.getSVG();
        RectangleTest.super.shape = shape;
        RectangleTest.super.svg = svg;
    }

    @BeforeEach
    void givenTwoDefaultRectangles() {
        shape1 = new Rectangle();
        shape2 = new Rectangle();
        RectangleTest.super.shape1 = shape1;
        RectangleTest.super.shape2 = shape2;
    }

    @BeforeEach
    void givenThreeDefaultRectangles() {
        shape1 = new Rectangle();
        shape2 = new Rectangle();
        shape3 = new Rectangle();
        RectangleTest.super.shape1 = shape1;
        RectangleTest.super.shape2 = shape2;
        RectangleTest.super.shape3 = shape3;
    }

    @Nested
    @DisplayName("Given one default Rectangle")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultRectangle {

        Rectangle shape;

        @BeforeEach
        @DisplayName("Given one default Rectangle")
        void givenOneDefaultShape() {
            this.shape = new Rectangle();
            RectangleTest.super.shape = this.shape;
        }
    }

    @Nested
    @DisplayName("Given one default Rectangle SVG")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultRectangleSVG {

        Shape shape;
        String svg;

        @BeforeEach
        @DisplayName("Given one default Rectangle SVG")
        void givenOneDefaultShapeSVG() {
            shape = new Rectangle();
            svg = shape.getSVG();
            RectangleTest.super.shape = shape;
            RectangleTest.super.svg = svg;
        }
    }





}