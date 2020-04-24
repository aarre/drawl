package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.Circle;
import com.aarrelaakso.drawl.Drawing;
import com.aarrelaakso.drawl.Shape;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests of Circle")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class CircleTest extends ShapeTest {

    Circle shape1;
    Circle shape2;
    Circle shape3;

    @BeforeEach
    void givenCircles() {
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
    class GivenOneDefaultShapeSVGSubclass extends ShapeTest.GivenOneDefaultShapeSVG {

        @Test
        @DisplayName("Then the SVG contains 'circle'")
        void thenSVGContainsCircle() {
            String svg = shape1.getSVG();
            then(svg).contains("circle");
        }

        @Test
        @DisplayName("Then the SVG contains the radius")
        void thenSVGContainsRadius() {
            String svg = shape1.getSVG();
            then(svg).contains("r=\"0.5\"");
        }
    }

    @Nested
    @DisplayName("Given one default Circle")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultCircle extends ShapeTest.GivenOneDefaultShape {

         @Test
        @DisplayName("Then its implicit radius is 0.5")
        void thenImplicitRadiusIs05() {
            BigDecimal radius = shape1.getImplicitRadius();
            assertEquals(BigDecimal.valueOf(0.5), radius);
        }

        @Tag("circle")
        @Tag("explicit")
        @Tag("radius")
        @Test
        @DisplayName("Then you can set the explicit radius of a circle to a fixed value")
        void thenYouCanSetTheExplicitRadiusOfACircleToAFixedValue() {
            Integer radius = 100;
            BigDecimal EXPECTED = BigDecimal.valueOf(radius);
            shape1.setExplicitRadius(radius);
            BigDecimal ACTUAL = shape1.getExplicitRadius();

            then(ACTUAL).isEqualByComparingTo(EXPECTED);
        }

        @Test
        @DisplayName("Then you can set the radius of a circle to be the same as the radius of another circle that has an implicit radius")
        void thenYouCanSetTheRadiusOfACircleToBeTheSameAsTheRadiusOfAnotherCircle() {
            Circle circle2 = new Circle(shape1.getImplicitRadius());
            assertEquals(shape1.getImplicitRadius(), circle2.getImplicitRadius());
        }
    }

    @Nested
    @DisplayName("Given three default Circles")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenThreeDefaultCircles extends ShapeTest.GivenThreeDefaultShapes {

    }

    @Nested
    @DisplayName("Given two default Circles")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenTwoDefaultCircles extends ShapeTest.GivenTwoDefaultShapes{


    }
}