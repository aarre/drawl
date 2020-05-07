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
 * Tests the SisuNumber class
 *
 * @author radek.hecl
 * @link https://dzone.com/articles/arbitrary-precision-numbers
 */
@DisplayName("SisuBigDecimal")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class SisuNumberTestProtected
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
        assertEquals(SisuNumber.valueOf(0), SisuNumber.valueOf(0).abs());
        assertEquals(SisuNumber.valueOf(1), SisuNumber.valueOf(1).abs());
        assertEquals(SisuNumber.valueOf(1), SisuNumber.valueOf(-1).abs());
    }

    /**
     * Tests addition.
     */
    @Test
    public void testAdd()
    {
        assertEquals(SisuNumber.valueOf(2), SisuNumber.valueOf(1).add(SisuNumber.valueOf("1")));
        // double version
        assertEquals(SisuNumber.valueOf(2), SisuNumber.valueOf(1).add(1));
    }

    /**
     * Tests compare to method.
     */
    @Test
    public void testCompareTo()
    {
        assertEquals(1, SisuNumber.valueOf(1).compareTo(SisuNumber.valueOf(0)));
        assertEquals(0, SisuNumber.valueOf(0).compareTo(SisuNumber.valueOf(0)));
        assertEquals(-1, SisuNumber.valueOf(-1).compareTo(SisuNumber.valueOf(0)));
    }

    /**
     * Test compareToFuzzy method when precision is low
     */
    @Test
    @DisplayName("Test fuzzily comparing two SisuBigDecimal instances when precision is low")
    public void testCompareToFuzzy()
    {
        @NotNull final SisuNumber value1 = SisuNumber.valueOf(1.0/3.0);
        @NotNull final SisuNumber value2 = SisuNumber.valueOf(1.0/2.999999999999999999999999999999999999999999999999999);
        @NotNull final Integer precision = 10;
        final Integer ACTUAL =  value1.compareToFuzzy(value2, SisuNumber.mcComparisons);
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
        @NotNull final SisuNumber value1 = SisuNumber.valueOf("0.3333333333333333");
        @NotNull final SisuNumber value2 = SisuNumber.valueOf("0.3333333333333334");

        @NotNull final Integer precision = 100;
        final Integer ACTUAL =  value1.compareToFuzzy(value2, SisuNumber.mcOperations);
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
        @NotNull final SisuNumber value1 = SisuNumber.valueOf("0.3333333333333334");
        @NotNull final SisuNumber value2 = SisuNumber.valueOf("0.3333333333333333");

        @NotNull final Integer precision = 100;
        final Integer ACTUAL =  value1.compareToFuzzy(value2, SisuNumber.mcOperations);
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
        assertEquals(SisuNumber.valueOf(1), SisuNumber.valueOf("1"));
        assertEquals(SisuNumber.valueOf(-1), SisuNumber.valueOf("-1"));
        assertEquals(SisuNumber.valueOf(1), SisuNumber.valueOf("1.00"));
        assertEquals(SisuNumber.valueOf(1.5), SisuNumber.valueOf("1.5"));
        assertEquals(SisuNumber.valueOf(4.95), SisuNumber.valueOf("4.95"));
        assertEquals(SisuNumber.valueOf(new BigDecimal("1e-25")), SisuNumber.valueOf("0.0000000000000000000000001"));
        assertEquals(SisuNumber.valueOf(new BigDecimal("1e-25")), SisuNumber.valueOf("0.000000000000000000000000100000"));
        //
        assertEquals(SisuNumber.valueOf(1).hashCode(), SisuNumber.valueOf("1").hashCode());
        assertEquals(SisuNumber.valueOf(1).hashCode(), SisuNumber.valueOf("1.00").hashCode());
        assertEquals(SisuNumber.valueOf(1.5).hashCode(), SisuNumber.valueOf("1.5").hashCode());
        assertEquals(SisuNumber.valueOf(1000).hashCode(), SisuNumber.valueOf("1000").hashCode());
        assertEquals(SisuNumber.valueOf(1000).hashCode(), SisuNumber.valueOf("1.000E3").hashCode());
        assertEquals(SisuNumber.valueOf(new BigDecimal("1e-25")).hashCode(), SisuNumber.valueOf("0.0000000000000000000000001").hashCode());
        assertEquals(SisuNumber.valueOf(new BigDecimal("1e-25")).hashCode(), SisuNumber.valueOf("0.000000000000000000000000100000").hashCode());
        assertEquals(SisuNumber.valueOf(1000), SisuNumber.valueOf("1000"));
        assertEquals(SisuNumber.valueOf(1000), SisuNumber.valueOf("1.000E3"));
        assertFalse(SisuNumber.valueOf(1).hashCode() == SisuNumber.valueOf("1.5").hashCode());
    }

    /**
     * Tests division.
     */
    @Test
    public void testDiv()
    {
        assertEquals(SisuNumber.valueOf("1.5"), SisuNumber.valueOf(3).divide(SisuNumber.valueOf(2), 2));
        assertEquals(SisuNumber.valueOf("0.67"), SisuNumber.valueOf(2).divide(SisuNumber.valueOf(3), 2));
        assertEquals(SisuNumber.valueOf("6.7"), SisuNumber.valueOf(20).divide(SisuNumber.valueOf(3), 2));
        assertEquals(SisuNumber.valueOf("0.6666666667"), SisuNumber.valueOf(2).divide(SisuNumber.valueOf("3"), 10));
        // double version
        assertEquals(SisuNumber.valueOf("1.5"), SisuNumber.valueOf(3).divide(2, 2));
        assertEquals(SisuNumber.valueOf("0.67"), SisuNumber.valueOf(2).divide(3, 2));
        assertEquals(SisuNumber.valueOf("6.7"), SisuNumber.valueOf(20).divide(3, 2));
        assertEquals(SisuNumber.valueOf("0.6666666667"), SisuNumber.valueOf(2).divide(3, 10));
    }

    /**
     * Tests equal operator.
     */
    @Test
    public void testEq()
    {
        assertTrue(SisuNumber.valueOf(0).equals(SisuNumber.ZERO));
        assertFalse(SisuNumber.valueOf(1).equals(SisuNumber.ZERO));
        // double version
        assertTrue(SisuNumber.valueOf(0).equals(0));
        assertFalse(SisuNumber.valueOf(1).equals(0));
    }

    /**
     * Tests greater operator.
     */
    @Test
    public void testG()
    {
        assertTrue(SisuNumber.valueOf(1).isGreaterThan(SisuNumber.valueOf(0)));
        assertFalse(SisuNumber.valueOf(0).isGreaterThan(SisuNumber.valueOf(0)));
        assertFalse(SisuNumber.valueOf(-1).isGreaterThan(SisuNumber.valueOf(0)));
        // double version
        assertTrue(SisuNumber.valueOf(1).isGreaterThan(0));
        assertFalse(SisuNumber.valueOf(0).isGreaterThan(0));
        assertFalse(SisuNumber.valueOf(-1).isGreaterThan(0));
    }

    /**
     * Tests greater or equal operator.
     */
    @Test
    public void testGeq()
    {
        assertTrue(SisuNumber.valueOf(1).isGreaterThanOrEqualTo(SisuNumber.valueOf(0)));
        assertTrue(SisuNumber.valueOf(0).isGreaterThanOrEqualTo(SisuNumber.valueOf(0)));
        assertFalse(SisuNumber.valueOf(-1).isGreaterThanOrEqualTo(SisuNumber.valueOf(0)));
        // double version
        assertTrue(SisuNumber.valueOf(1).isGreaterThanOrEqualTo(0));
        assertTrue(SisuNumber.valueOf(0).isGreaterThanOrEqualTo(0));
        assertFalse(SisuNumber.valueOf(-1).isGreaterThanOrEqualTo(0));
    }

    /**
     * Tests less operator.
     */
    @Test
    public void testL()
    {
        assertFalse(SisuNumber.valueOf(1).isLessThan(SisuNumber.valueOf(0)));
        assertFalse(SisuNumber.valueOf(0).isLessThan(SisuNumber.valueOf(0)));
        assertTrue(SisuNumber.valueOf(-1).isLessThan(SisuNumber.valueOf(0)));
        // double version
        assertFalse(SisuNumber.valueOf(1).isLessThan(0));
        assertFalse(SisuNumber.valueOf(0).isLessThan(0));
        assertTrue(SisuNumber.valueOf(-1).isLessThan(0));
    }

    /**
     * Tests less or equal operator.
     */
    @Test
    public void testLeq()
    {
        assertFalse(SisuNumber.valueOf(1).isLessThanOrEqualTo(SisuNumber.valueOf(0)));
        assertTrue(SisuNumber.valueOf(0).isLessThanOrEqualTo(SisuNumber.valueOf(0)));
        assertTrue(SisuNumber.valueOf(-1).isLessThanOrEqualTo(SisuNumber.valueOf(0)));
        // double version
        assertFalse(SisuNumber.valueOf(1).isLessThanOrEqualTo(0));
        assertTrue(SisuNumber.valueOf(0).isLessThanOrEqualTo(0));
        assertTrue(SisuNumber.valueOf(-1).isLessThanOrEqualTo(0));
    }

    /**
     * Tests multiplication.
     */
    @Test
    public void testMul()
    {
        assertEquals(SisuNumber.valueOf(6), SisuNumber.valueOf(3).multiply(SisuNumber.valueOf(2)));
        // double version
        assertEquals(SisuNumber.valueOf(6), SisuNumber.valueOf(3).multiply(2));
    }

    /**
     * Tests negation.
     */
    @Test
    public void testNegate()
    {
        assertEquals(SisuNumber.valueOf(0), SisuNumber.valueOf(0).negate());
        assertEquals(SisuNumber.valueOf(-1), SisuNumber.valueOf(1).negate());
        assertEquals(SisuNumber.valueOf(1), SisuNumber.valueOf(-1).negate());
    }

    /**
     * Tests powering value.
     */
    @Test
    public void testPow()
    {
        assertEquals(SisuNumber.valueOf(0), SisuNumber.valueOf(0).pow(3, 2));
        assertEquals(SisuNumber.valueOf(1), SisuNumber.valueOf(1).pow(3, 2));
        assertEquals(SisuNumber.valueOf(8), SisuNumber.valueOf(2).pow(3, 2));
        assertEquals(SisuNumber.valueOf(15.63), SisuNumber.valueOf(2.50).pow(3, 4));
        assertEquals(SisuNumber.valueOf(-15.625), SisuNumber.valueOf(-2.50).pow(3, 10));
    }

    /**
     * Tests subtraction.
     */
    @Test
    public void testSub()
    {
        assertEquals(SisuNumber.valueOf(2), SisuNumber.valueOf(3).subtract(SisuNumber.valueOf(1)));
        // double version
        assertEquals(SisuNumber.valueOf(2), SisuNumber.valueOf(3).subtract(1));
    }

    /**
     * Tests conversion to the string with fixed decimal points.
     */
    @Test
    public void testToFixedDecimalString()
    {
        assertEquals("1", SisuNumber.valueOf("1").toFixedDecimalString(0));
        assertEquals("1", SisuNumber.valueOf("1.5").toFixedDecimalString(0));
        assertEquals("1.00", SisuNumber.valueOf("1").toFixedDecimalString(2));
        assertEquals("1.50", SisuNumber.valueOf("1.5").toFixedDecimalString(2));
        assertEquals("1.50", SisuNumber.valueOf("1.50").toFixedDecimalString(2));
        assertEquals("1.50", SisuNumber.valueOf("1.500").toFixedDecimalString(2));
        assertEquals("0.0000000000000000000000001", SisuNumber.valueOf("1E-25").toFixedDecimalString(25));
        assertEquals("0.00", SisuNumber.valueOf("1E-25").toFixedDecimalString(2));
        assertEquals("-1", SisuNumber.valueOf("-1").toFixedDecimalString(0));
        assertEquals("-1", SisuNumber.valueOf("-1.5").toFixedDecimalString(0));
        assertEquals("-1.00", SisuNumber.valueOf("-1").toFixedDecimalString(2));
        assertEquals("-1.50", SisuNumber.valueOf("-1.5").toFixedDecimalString(2));
        assertEquals("-1.50", SisuNumber.valueOf("-1.50").toFixedDecimalString(2));
        assertEquals("-1.50", SisuNumber.valueOf("-1.500").toFixedDecimalString(2));
        assertEquals("-0.0000000000000000000000001", SisuNumber.valueOf("-1E-25").toFixedDecimalString(25));
        assertEquals("-0.00", SisuNumber.valueOf("-1E-25").toFixedDecimalString(2));
    }

    /**
     * Tests conversion to full string.
     */
    @Test
    public void testToFullString()
    {
        assertEquals("1.5", SisuNumber.valueOf("1.5").toFullString());
        assertEquals("0.0000000000000000000000001", SisuNumber.valueOf("1E-25").toFullString());
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}