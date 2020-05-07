/*
 * Copyright (c) 2020. Aarre Laakso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.aarrelaakso.drawl;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests the SisuBigDecimal class
 *
 * @author radek.hecl
 * @link https://dzone.com/articles/arbitrary-precision-numbers
 */
@DisplayName("SisuBigDecimal")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class DrawlNumberTestProtected
{
    /**
     * Creates new instance.
     */
    //public SisuBigDecimalTest()
    //{
    //}

    /**
     * Tests absolute value.
     */
    @Test
    public void testAbs()
    {
        assertEquals(DrawlNumber.valueOf(0), DrawlNumber.valueOf(0).abs());
        assertEquals(DrawlNumber.valueOf(1), DrawlNumber.valueOf(1).abs());
        assertEquals(DrawlNumber.valueOf(1), DrawlNumber.valueOf(-1).abs());
    }

    /**
     * Tests addition.
     */
    @Test
    public void testAdd()
    {
        assertEquals(DrawlNumber.valueOf(2), DrawlNumber.valueOf(1).add(DrawlNumber.valueOf("1")));
        // double version
        assertEquals(DrawlNumber.valueOf(2), DrawlNumber.valueOf(1).add(1));
    }

    /**
     * Tests compare to method.
     */
    @Test
    public void testCompareTo()
    {
        assertEquals(1, DrawlNumber.valueOf(1).compareTo(DrawlNumber.valueOf(0)));
        assertEquals(0, DrawlNumber.valueOf(0).compareTo(DrawlNumber.valueOf(0)));
        assertEquals(-1, DrawlNumber.valueOf(-1).compareTo(DrawlNumber.valueOf(0)));
    }

    /**
     * Test compareToFuzzy method when precision is low
     */
    @Test
    @DisplayName("Test fuzzily comparing two SisuBigDecimal instances when precision is low")
    public void testCompareToFuzzy()
    {
        @NotNull final DrawlNumber value1 = DrawlNumber.valueOf(1.0 / 3.0);
        @NotNull final DrawlNumber value2 = DrawlNumber.valueOf(1.0 / 2.999999999999999999999999999999999999999999999999999);
        @NotNull final Integer precision = 10;
        final Integer ACTUAL = value1.compareToFuzzy(value2, DrawlNumber.mcComparisons);
        @NotNull final Integer EXPECTED = 0;

        then(ACTUAL).as("Fuzzily comparing " + value1 + " to " + value2 +
                " should result in a value of " + EXPECTED + " but got " + ACTUAL)
                .isEqualTo(EXPECTED);
    }

    /**
     * Test compareToFuzzy method when precision is high (big number comes second)
     */
    @Test
    @DisplayName("Test fuzzily comparing two SisuBigDecimal instances when precision is high #1")
    public void testCompareToFuzzyWhenPrecisionIsHigh01()
    {
        @NotNull final DrawlNumber value1 = DrawlNumber.valueOf("0.3333333333333333");
        @NotNull final DrawlNumber value2 = DrawlNumber.valueOf("0.3333333333333334");

        @NotNull final Integer precision = 100;
        final Integer ACTUAL = value1.compareToFuzzy(value2, DrawlNumber.mcOperations);
        @NotNull final Integer EXPECTED = -1;

        then(ACTUAL).as("Fuzzily comparing " + value1 + " to " + value2 +
                " should result in a value of " + EXPECTED + " but got " + ACTUAL)
                .isEqualTo(EXPECTED);
    }

    /**
     * Test compareToFuzzy method when precision is high (big number comes first)
     */
    @Test
    @DisplayName("Test fuzzily comparing two SisuBigDecimal instances when precision is high #2")
    public void testCompareToFuzzyWhenPrecisionIsHigh02()
    {
        @NotNull final DrawlNumber value1 = DrawlNumber.valueOf("0.3333333333333334");
        @NotNull final DrawlNumber value2 = DrawlNumber.valueOf("0.3333333333333333");

        final Integer ACTUAL = value1.compareToFuzzy(value2, DrawlNumber.mcOperations);
        @NotNull final Integer EXPECTED = 1;

        then(ACTUAL).as("Fuzzily comparing " + value1 + " to " + value2 +
                " should result in a value of " + EXPECTED + " but got " + ACTUAL)
                .isEqualTo(EXPECTED);
    }


    /**
     * Tests creation.
     */
    @Test
    public void testCreate()
    {
        assertEquals(DrawlNumber.valueOf(1), DrawlNumber.valueOf("1"));
        assertEquals(DrawlNumber.valueOf(-1), DrawlNumber.valueOf("-1"));
        assertEquals(DrawlNumber.valueOf(1), DrawlNumber.valueOf("1.00"));
        assertEquals(DrawlNumber.valueOf(1.5), DrawlNumber.valueOf("1.5"));
        assertEquals(DrawlNumber.valueOf(4.95), DrawlNumber.valueOf("4.95"));
        assertEquals(DrawlNumber.valueOf(new BigDecimal("1e-25")), DrawlNumber.valueOf("0.0000000000000000000000001"));
        assertEquals(DrawlNumber.valueOf(new BigDecimal("1e-25")), DrawlNumber.valueOf("0.000000000000000000000000100000"));
        //
        assertEquals(DrawlNumber.valueOf(1).hashCode(), DrawlNumber.valueOf("1").hashCode());
        assertEquals(DrawlNumber.valueOf(1).hashCode(), DrawlNumber.valueOf("1.00").hashCode());
        assertEquals(DrawlNumber.valueOf(1.5).hashCode(), DrawlNumber.valueOf("1.5").hashCode());
        assertEquals(DrawlNumber.valueOf(1000).hashCode(), DrawlNumber.valueOf("1000").hashCode());
        assertEquals(DrawlNumber.valueOf(1000).hashCode(), DrawlNumber.valueOf("1.000E3").hashCode());
        assertEquals(DrawlNumber.valueOf(new BigDecimal("1e-25")).hashCode(), DrawlNumber.valueOf("0.0000000000000000000000001").hashCode());
        assertEquals(DrawlNumber.valueOf(new BigDecimal("1e-25")).hashCode(), DrawlNumber.valueOf("0.000000000000000000000000100000").hashCode());
        assertEquals(DrawlNumber.valueOf(1000), DrawlNumber.valueOf("1000"));
        assertEquals(DrawlNumber.valueOf(1000), DrawlNumber.valueOf("1.000E3"));
        assertFalse(DrawlNumber.valueOf(1).hashCode() == DrawlNumber.valueOf("1.5").hashCode());
    }

    /**
     * Tests dividing a DrawlNumber by a double
     */
    @Test
    @DisplayName("Test division by double (3/2 precision 2)")
    public void testDivisionByDouble32Precision2()
    {
        // Dividing by Double number
        assertEquals(DrawlNumber.valueOf("1.5"), DrawlNumber.valueOf(3).divide(2, 2));
    }

    @Test
    @DisplayName("Test division by double (20/3 precision 2)")
    public void testDivisionByDouble203Precision2()
    {
        assertEquals(DrawlNumber.valueOf("6.7"), DrawlNumber.valueOf(20).divide(3, 2));
    }

    @Test
    @DisplayName("Test division by double (2/3 precision 2)")
    public void testDivisionByDouble23Precision2()
    {
        assertEquals(DrawlNumber.valueOf("0.67"), DrawlNumber.valueOf(2).divide(3, 2));
    }

    @Test
    @DisplayName("Test division by double (2/3 precision 10)")
    public void testDivisionByDouble23Precision10()
    {
        assertEquals(DrawlNumber.valueOf("0.6666666667"), DrawlNumber.valueOf(2).divide(3, 10));
    }

    /**
     * Tests dividing a DrawlNumber by a DrawlNumber
     */
    @Test
    @DisplayName("Test division by DrawlNumber (3/2 precision 2)")
    public void testDivisionByDrawlNumber()
    {
        // Note Drawl number ignores precision
        // Dividing by Drawl number
        then(DrawlNumber.valueOf(3).divide(DrawlNumber.valueOf(2), 2)).isEqualTo(DrawlNumber.valueOf("1.5"));
    }

    @Test
    @DisplayName("Test division by DrawlNumber (20/3 precision 2)")
    void testDivisionByDrawlNumber203Precision2()
    {
        assertEquals(DrawlNumber.valueOf("6.7"), DrawlNumber.valueOf(20).divide(DrawlNumber.valueOf(3), 2));
    }

    @Test
    @DisplayName("Test division by DrawlNumber (2/3 precision 2)")
    void testDivisionByDrawlNumber23Precision2()
    {
        assertEquals(DrawlNumber.valueOf("0.67"), DrawlNumber.valueOf(2).divide(DrawlNumber.valueOf(3), 2));
    }

    @Test
    @DisplayName("Test division by DrawlNumber (2/3 precision 10")
    void testDivisionByDrawlNumber23Precision10()
    {
        assertEquals(DrawlNumber.valueOf("0.6666666667"), DrawlNumber.valueOf(2).divide(DrawlNumber.valueOf("3"), 10));
    }

    /**
     * Tests equal operator.
     */
    @Test
    public void testEq()
    {
        assertTrue(DrawlNumber.valueOf(0).equals(DrawlNumber.ZERO));
        assertFalse(DrawlNumber.valueOf(1).equals(DrawlNumber.ZERO));
        // double version
        assertTrue(DrawlNumber.valueOf(0).equals(0));
        assertFalse(DrawlNumber.valueOf(1).equals(0));
    }

    /**
     * Tests greater operator.
     */
    @Test
    public void testG()
    {
        assertTrue(DrawlNumber.valueOf(1).isGreaterThan(DrawlNumber.valueOf(0)));
        assertFalse(DrawlNumber.valueOf(0).isGreaterThan(DrawlNumber.valueOf(0)));
        assertFalse(DrawlNumber.valueOf(-1).isGreaterThan(DrawlNumber.valueOf(0)));
        // double version
        assertTrue(DrawlNumber.valueOf(1).isGreaterThan(0));
        assertFalse(DrawlNumber.valueOf(0).isGreaterThan(0));
        assertFalse(DrawlNumber.valueOf(-1).isGreaterThan(0));
    }

    /**
     * Tests greater or equal operator.
     */
    @Test
    public void testGeq()
    {
        assertTrue(DrawlNumber.valueOf(1).isGreaterThanOrEqualTo(DrawlNumber.valueOf(0)));
        assertTrue(DrawlNumber.valueOf(0).isGreaterThanOrEqualTo(DrawlNumber.valueOf(0)));
        assertFalse(DrawlNumber.valueOf(-1).isGreaterThanOrEqualTo(DrawlNumber.valueOf(0)));
        // double version
        assertTrue(DrawlNumber.valueOf(1).isGreaterThanOrEqualTo(0));
        assertTrue(DrawlNumber.valueOf(0).isGreaterThanOrEqualTo(0));
        assertFalse(DrawlNumber.valueOf(-1).isGreaterThanOrEqualTo(0));
    }

    /**
     * Tests less operator.
     */
    @Test
    public void testL()
    {
        assertFalse(DrawlNumber.valueOf(1).isLessThan(DrawlNumber.valueOf(0)));
        assertFalse(DrawlNumber.valueOf(0).isLessThan(DrawlNumber.valueOf(0)));
        assertTrue(DrawlNumber.valueOf(-1).isLessThan(DrawlNumber.valueOf(0)));
        // double version
        assertFalse(DrawlNumber.valueOf(1).isLessThan(0));
        assertFalse(DrawlNumber.valueOf(0).isLessThan(0));
        assertTrue(DrawlNumber.valueOf(-1).isLessThan(0));
    }

    /**
     * Tests less or equal operator.
     */
    @Test
    public void testLeq()
    {
        assertFalse(DrawlNumber.valueOf(1).isLessThanOrEqualTo(DrawlNumber.valueOf(0)));
        assertTrue(DrawlNumber.valueOf(0).isLessThanOrEqualTo(DrawlNumber.valueOf(0)));
        assertTrue(DrawlNumber.valueOf(-1).isLessThanOrEqualTo(DrawlNumber.valueOf(0)));
        // double version
        assertFalse(DrawlNumber.valueOf(1).isLessThanOrEqualTo(0));
        assertTrue(DrawlNumber.valueOf(0).isLessThanOrEqualTo(0));
        assertTrue(DrawlNumber.valueOf(-1).isLessThanOrEqualTo(0));
    }

    /**
     * Tests multiplication.
     */
    @Test
    public void testMul()
    {
        assertEquals(DrawlNumber.valueOf(6), DrawlNumber.valueOf(3).multiply(DrawlNumber.valueOf(2)));
        // double version
        assertEquals(DrawlNumber.valueOf(6), DrawlNumber.valueOf(3).multiply(2));
    }

    /**
     * Tests negation.
     */
    @Test
    public void testNegate()
    {
        assertTrue(DrawlNumber.valueOf(0).compareToFuzzy(DrawlNumber.valueOf(0).negate()) == 0);
        assertEquals(DrawlNumber.valueOf(-1), DrawlNumber.valueOf(1).negate());
        assertEquals(DrawlNumber.valueOf(1), DrawlNumber.valueOf(-1).negate());
    }

    /**
     * Tests powering value.
     */
    @Test
    public void testPow()
    {
        // Note DrawlNumber ignores precision
        assertEquals(DrawlNumber.valueOf(0), DrawlNumber.valueOf(0).pow(3, 2));
        assertEquals(DrawlNumber.valueOf(1), DrawlNumber.valueOf(1).pow(3, 2));
        assertEquals(DrawlNumber.valueOf(8), DrawlNumber.valueOf(2).pow(3, 2));
        assertEquals(DrawlNumber.valueOf(15.625), DrawlNumber.valueOf(2.50).pow(3, 4));
        assertEquals(DrawlNumber.valueOf(-15.625), DrawlNumber.valueOf(-2.50).pow(3, 10));
    }

    /**
     * Tests subtraction.
     */
    @Test
    public void testSub()
    {
        assertEquals(DrawlNumber.valueOf(2), DrawlNumber.valueOf(3).subtract(DrawlNumber.valueOf(1)));
        // double version
        assertEquals(DrawlNumber.valueOf(2), DrawlNumber.valueOf(3).subtract(1));
    }

    /**
     * Tests conversion to the string with fixed decimal points.
     */
    @Test
    public void testToFixedDecimalString()
    {
        assertEquals("1", DrawlNumber.valueOf("1").toFixedDecimalString(0));
        //assertEquals("1", DrawlNumber.valueOf("1.5").toFixedDecimalString(0));
        assertEquals("1.00", DrawlNumber.valueOf("1").toFixedDecimalString(2));
        assertEquals("1.50", DrawlNumber.valueOf("1.5").toFixedDecimalString(2));
        assertEquals("1.50", DrawlNumber.valueOf("1.50").toFixedDecimalString(2));
        assertEquals("1.50", DrawlNumber.valueOf("1.500").toFixedDecimalString(2));
        assertEquals("0.0000000000000000000000001", DrawlNumber.valueOf("1E-25").toFixedDecimalString(25));
        assertEquals("0.00", DrawlNumber.valueOf("1E-25").toFixedDecimalString(2));
        assertEquals("-1", DrawlNumber.valueOf("-1").toFixedDecimalString(0));
        //assertEquals("-1", DrawlNumber.valueOf("-1.5").toFixedDecimalString(0));
        assertEquals("-1.00", DrawlNumber.valueOf("-1").toFixedDecimalString(2));
        assertEquals("-1.50", DrawlNumber.valueOf("-1.5").toFixedDecimalString(2));
        assertEquals("-1.50", DrawlNumber.valueOf("-1.50").toFixedDecimalString(2));
        assertEquals("-1.50", DrawlNumber.valueOf("-1.500").toFixedDecimalString(2));
        assertEquals("-0.0000000000000000000000001", DrawlNumber.valueOf("-1E-25").toFixedDecimalString(25));
        assertEquals("-0.00", DrawlNumber.valueOf("-1E-25").toFixedDecimalString(2));
    }

    /**
     * Tests conversion to full string.
     */
    @Test
    public void testToFullString()
    {
        assertEquals("1.5", DrawlNumber.valueOf("1.5").toFullString());
        assertEquals("0.0000000000000000000000001", DrawlNumber.valueOf("1E-25").toFullString());
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}