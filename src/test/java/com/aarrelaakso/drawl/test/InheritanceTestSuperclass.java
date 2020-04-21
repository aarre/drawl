package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.Shape;
import org.junit.Before;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Superclass to test inheritance of test cases in JUnit 5")
public abstract class InheritanceTestSuperclass {

    Shape shape;

    InheritanceTestSuperclass() {

    }
    //@BeforeEach
    //void givenOneDefaultCircle() {
    //    //shape = new Circle();
    //}

    @Test
    @DisplayName("Then its explicit width is 1")
    void thenExplicitWidthIs1() {
        BigDecimal width = shape.getExplicitWidth();
        assertEquals(0, BigDecimal.ONE.compareTo(width));
    }

    @Nested
    @DisplayName("Nested test class")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class GivenOneDefaultShape {

        @Test
        @DisplayName("Nested test method")
        void thenExplicitWidthIs1() {
            BigDecimal width = shape.getExplicitWidth();
            assertEquals(0, BigDecimal.ONE.compareTo(width));
        }

    }

}
