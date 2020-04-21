package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.Circle;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests of Circle")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class CircleTest extends ShapeTest {

    Circle shape;
    Circle shape1;
    Circle shape2;
    Circle shape3;

    @BeforeEach
    void givenOneDefaultCircle() {
        shape = new Circle();
        svg = shape.getSVG();
        CircleTest.super.shape = shape;
        CircleTest.super.svg = svg;
    }

    @BeforeEach
    void givenTwoDefaultCircles() {
        shape1 = new Circle();
        shape2 = new Circle();
        CircleTest.super.shape1 = shape1;
        CircleTest.super.shape2 = shape2;
    }

    @BeforeEach
    void givenTheeDefaultCircles() {
        shape1 = new Circle();
        shape2 = new Circle();
        shape3 = new Circle();
        CircleTest.super.shape1 = shape1;
        CircleTest.super.shape2 = shape2;
        CircleTest.super.shape3 = shape3;
    }

    @Nested
    @DisplayName("Given one default Circle SVG")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultShapeSVGSubclass {

        @Test
        @DisplayName("Then the SVG contains 'circle'")
        void thenSVGContainsCircle() {
            assertTrue(svg.indexOf("circle") > -1);
        }

        @Test
        @DisplayName("Then the SVG contains the radius")
        void thenSVGContainsRadius() {
            assertTrue(svg.indexOf("r=\"0.5\"") > -1);
        }
    }

    @Nested
    @DisplayName("Given one default Circle")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultShapeSubclass {

        @Test
        @DisplayName("Then its implicit radius is 0.5")
        void thenImplicitRadiusIs05() {
            BigDecimal radius = shape.getImplicitRadius();
            assertEquals(BigDecimal.valueOf(0.5), radius);
        }

        @Test
        @DisplayName("Then you can set the radius of a circle to a fixed value")
        void thenYouCanSetTheRadiusOfACircleToAFixedValue() {
            int radius = 100;
            shape.setExplicitRadius(radius);
        }

        @Test
        @DisplayName("Then you can set the radius of a circle to be the same as the radius of another circle that has an implicit radius")
        void thenYouCanSetTheRadiusOfACircleToBeTheSameAsTheRadiusOfAnotherCircle() {
            Circle circle2 = new Circle(shape.getImplicitRadius());
            assertEquals(shape.getImplicitRadius(), circle2.getImplicitRadius());
        }

    }
}