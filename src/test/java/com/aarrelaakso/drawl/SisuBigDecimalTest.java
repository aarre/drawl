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

import com.aarrelaakso.drawl.SisuBigDecimal;
import com.aarrelaakso.drawl.SisuBigDecimalRemainderPair;

import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.math.BigDecimal;



/**
 * Test cases for SisuBigDecimal
 *
 * @author radek.hecl
 * @link https://dzone.com/articles/arbitrary-precision-numbers
 */
@DisplayName("Unit tests of SisuBigDecimal")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class SisuBigDecimalTest
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
        assertEquals(SisuBigDecimal.valueOf(0), SisuBigDecimal.valueOf(0).abs());
        assertEquals(SisuBigDecimal.valueOf(1), SisuBigDecimal.valueOf(1).abs());
        assertEquals(SisuBigDecimal.valueOf(1), SisuBigDecimal.valueOf(-1).abs());
    }

    /**
     * Tests addition.
     */
    @Test
    public void testAdd()
    {
        assertEquals(SisuBigDecimal.valueOf(2), SisuBigDecimal.valueOf(1).add(SisuBigDecimal.valueOf("1")));
        // double version
        assertEquals(SisuBigDecimal.valueOf(2), SisuBigDecimal.valueOf(1).add(1));
    }

    /**
     * Tests compare to method.
     */
    @Test
    public void testCompareTo()
    {
        assertEquals(1, SisuBigDecimal.valueOf(1).compareTo(SisuBigDecimal.valueOf(0)));
        assertEquals(0, SisuBigDecimal.valueOf(0).compareTo(SisuBigDecimal.valueOf(0)));
        assertEquals(-1, SisuBigDecimal.valueOf(-1).compareTo(SisuBigDecimal.valueOf(0)));
    }

    /**
     * Test compareToFuzzy method when precision is low
     */
    @Test
    @DisplayName("Test fuzzily comparing two SisuBigDecimal instances when precision is low")
    public void testCompareToFuzzy()
    {
        SisuBigDecimal value1 = SisuBigDecimal.valueOf(1.0/3.0);
        SisuBigDecimal value2 = SisuBigDecimal.valueOf(1.0/2.999999999999999999999999999999999999999999999999999);
        Integer precision = 10;
        Integer ACTUAL =  value1.compareToFuzzy(value2, SisuBigDecimal.mcComparisons);
        Integer EXPECTED = 0;

        then(ACTUAL).as("Fuzzily comparing " + value1.toString() + " to " + value2.toString() +
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
        SisuBigDecimal value1 = SisuBigDecimal.valueOf("0.3333333333333333");
        SisuBigDecimal value2 = SisuBigDecimal.valueOf("0.3333333333333334");

        Integer precision = 100;
        Integer ACTUAL =  value1.compareToFuzzy(value2, SisuBigDecimal.mcOperations);
        Integer EXPECTED = -1;

        then(ACTUAL).as("Fuzzily comparing " + value1.toString() + " to " + value2.toString() +
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
        SisuBigDecimal value1 = SisuBigDecimal.valueOf("0.3333333333333334");
        SisuBigDecimal value2 = SisuBigDecimal.valueOf("0.3333333333333333");

        Integer precision = 100;
        Integer ACTUAL =  value1.compareToFuzzy(value2, SisuBigDecimal.mcOperations);
        Integer EXPECTED = 1;

        then(ACTUAL).as("Fuzzily comparing " + value1.toString() + " to " + value2.toString() +
                " should result in a value of " + EXPECTED + " but got " + ACTUAL)
                .isEqualTo(EXPECTED);
    }


    /**
     * Tests creation.
     */
    @Test
    public void testCreate()
    {
        assertEquals(SisuBigDecimal.valueOf(1), SisuBigDecimal.valueOf("1"));
        assertEquals(SisuBigDecimal.valueOf(-1), SisuBigDecimal.valueOf("-1"));
        assertEquals(SisuBigDecimal.valueOf(1), SisuBigDecimal.valueOf("1.00"));
        assertEquals(SisuBigDecimal.valueOf(1.5), SisuBigDecimal.valueOf("1.5"));
        assertEquals(SisuBigDecimal.valueOf(4.95), SisuBigDecimal.valueOf("4.95"));
        assertEquals(SisuBigDecimal.valueOf(new BigDecimal("1e-25")), SisuBigDecimal.valueOf("0.0000000000000000000000001"));
        assertEquals(SisuBigDecimal.valueOf(new BigDecimal("1e-25")), SisuBigDecimal.valueOf("0.000000000000000000000000100000"));
        //
        assertEquals(SisuBigDecimal.valueOf(1).hashCode(), SisuBigDecimal.valueOf("1").hashCode());
        assertEquals(SisuBigDecimal.valueOf(1).hashCode(), SisuBigDecimal.valueOf("1.00").hashCode());
        assertEquals(SisuBigDecimal.valueOf(1.5).hashCode(), SisuBigDecimal.valueOf("1.5").hashCode());
        assertEquals(SisuBigDecimal.valueOf(1000).hashCode(), SisuBigDecimal.valueOf("1000").hashCode());
        assertEquals(SisuBigDecimal.valueOf(1000).hashCode(), SisuBigDecimal.valueOf("1.000E3").hashCode());
        assertEquals(SisuBigDecimal.valueOf(new BigDecimal("1e-25")).hashCode(), SisuBigDecimal.valueOf("0.0000000000000000000000001").hashCode());
        assertEquals(SisuBigDecimal.valueOf(new BigDecimal("1e-25")).hashCode(), SisuBigDecimal.valueOf("0.000000000000000000000000100000").hashCode());
        assertEquals(SisuBigDecimal.valueOf(1000), SisuBigDecimal.valueOf("1000"));
        assertEquals(SisuBigDecimal.valueOf(1000), SisuBigDecimal.valueOf("1.000E3"));
        assertFalse(SisuBigDecimal.valueOf(1).hashCode() == SisuBigDecimal.valueOf("1.5").hashCode());
    }

    /**
     * Tests division.
     */
    @Test
    public void testDiv()
    {
        assertEquals(SisuBigDecimal.valueOf("1.5"), SisuBigDecimal.valueOf(3).divide(SisuBigDecimal.valueOf(2), 2));
        assertEquals(SisuBigDecimal.valueOf("0.67"), SisuBigDecimal.valueOf(2).divide(SisuBigDecimal.valueOf(3), 2));
        assertEquals(SisuBigDecimal.valueOf("6.7"), SisuBigDecimal.valueOf(20).divide(SisuBigDecimal.valueOf(3), 2));
        assertEquals(SisuBigDecimal.valueOf("0.6666666667"), SisuBigDecimal.valueOf(2).divide(SisuBigDecimal.valueOf("3"), 10));
        // double version
        assertEquals(SisuBigDecimal.valueOf("1.5"), SisuBigDecimal.valueOf(3).divide(2, 2));
        assertEquals(SisuBigDecimal.valueOf("0.67"), SisuBigDecimal.valueOf(2).divide(3, 2));
        assertEquals(SisuBigDecimal.valueOf("6.7"), SisuBigDecimal.valueOf(20).divide(3, 2));
        assertEquals(SisuBigDecimal.valueOf("0.6666666667"), SisuBigDecimal.valueOf(2).divide(3, 10));
    }

    /**
     * Tests division with remainder.
     */
    @Test
    public void testDivWithRemainder()
    {
        assertEquals(SisuBigDecimalRemainderPair.valueOf(SisuBigDecimal.valueOf("1.5"), SisuBigDecimal.valueOf("0")),
                SisuBigDecimal.valueOf(3).divWithRemainder(SisuBigDecimal.valueOf(2), 2));

        SisuBigDecimalRemainderPair EXPECTED = SisuBigDecimalRemainderPair.valueOf(
                SisuBigDecimal.valueOf("0.66"),
                SisuBigDecimal.valueOf("0.02"));

        SisuBigDecimal two = SisuBigDecimal.valueOf(2);
        SisuBigDecimal three = SisuBigDecimal.valueOf(3);

        SisuBigDecimalRemainderPair ACTUAL = two.divWithRemainder(three, 2);

        then(ACTUAL).as("Expected result of " + EXPECTED.getNumber().toFullString() +
                " remainder " + EXPECTED.getRemainder().toFullString() + " but instead got " +
                ACTUAL.getNumber().toFullString() + " remainder " + ACTUAL.getRemainder().toFullString())
                .isEqualTo(EXPECTED);


        assertEquals(SisuBigDecimalRemainderPair.valueOf(SisuBigDecimal.valueOf("6.6"), SisuBigDecimal.valueOf("0.2")),
                SisuBigDecimal.valueOf(20).divWithRemainder(SisuBigDecimal.valueOf(3), 2));
        assertEquals(SisuBigDecimalRemainderPair.valueOf(SisuBigDecimal.valueOf("0.6666666666"), SisuBigDecimal.valueOf("0.0000000002")),
                SisuBigDecimal.valueOf(2).divWithRemainder(SisuBigDecimal.valueOf(3), 10));
        // double version
        assertEquals(SisuBigDecimalRemainderPair.valueOf(SisuBigDecimal.valueOf("1.5"), SisuBigDecimal.valueOf("0")),
                SisuBigDecimal.valueOf(3).divWithRemainder(2, 2));
        assertEquals(SisuBigDecimalRemainderPair.valueOf(SisuBigDecimal.valueOf("0.66"), SisuBigDecimal.valueOf("0.02")),
                SisuBigDecimal.valueOf(2).divWithRemainder(3, 2));
        assertEquals(SisuBigDecimalRemainderPair.valueOf(SisuBigDecimal.valueOf("6.6"), SisuBigDecimal.valueOf("0.2")),
                SisuBigDecimal.valueOf(20).divWithRemainder(3, 2));
        assertEquals(SisuBigDecimalRemainderPair.valueOf(SisuBigDecimal.valueOf("0.6666666666"), SisuBigDecimal.valueOf("0.0000000002")),
                SisuBigDecimal.valueOf(2).divWithRemainder(3, 10));
    }

    /**
     * Tests equal operator.
     */
    @Test
    public void testEq()
    {
        assertTrue(SisuBigDecimal.valueOf(0).equals(SisuBigDecimal.ZERO));
        assertFalse(SisuBigDecimal.valueOf(1).equals(SisuBigDecimal.ZERO));
        // double version
        assertTrue(SisuBigDecimal.valueOf(0).equals(0));
        assertFalse(SisuBigDecimal.valueOf(1).equals(0));
    }

    /**
     * Tests greater operator.
     */
    @Test
    public void testG()
    {
        assertTrue(SisuBigDecimal.valueOf(1).g(SisuBigDecimal.valueOf(0)));
        assertFalse(SisuBigDecimal.valueOf(0).g(SisuBigDecimal.valueOf(0)));
        assertFalse(SisuBigDecimal.valueOf(-1).g(SisuBigDecimal.valueOf(0)));
        // double version
        assertTrue(SisuBigDecimal.valueOf(1).g(0));
        assertFalse(SisuBigDecimal.valueOf(0).g(0));
        assertFalse(SisuBigDecimal.valueOf(-1).g(0));
    }

    /**
     * Tests greater or equal operator.
     */
    @Test
    public void testGeq()
    {
        assertTrue(SisuBigDecimal.valueOf(1).geq(SisuBigDecimal.valueOf(0)));
        assertTrue(SisuBigDecimal.valueOf(0).geq(SisuBigDecimal.valueOf(0)));
        assertFalse(SisuBigDecimal.valueOf(-1).geq(SisuBigDecimal.valueOf(0)));
        // double version
        assertTrue(SisuBigDecimal.valueOf(1).geq(0));
        assertTrue(SisuBigDecimal.valueOf(0).geq(0));
        assertFalse(SisuBigDecimal.valueOf(-1).geq(0));
    }

    /**
     * Tests less operator.
     */
    @Test
    public void testL()
    {
        assertFalse(SisuBigDecimal.valueOf(1).l(SisuBigDecimal.valueOf(0)));
        assertFalse(SisuBigDecimal.valueOf(0).l(SisuBigDecimal.valueOf(0)));
        assertTrue(SisuBigDecimal.valueOf(-1).l(SisuBigDecimal.valueOf(0)));
        // double version
        assertFalse(SisuBigDecimal.valueOf(1).l(0));
        assertFalse(SisuBigDecimal.valueOf(0).l(0));
        assertTrue(SisuBigDecimal.valueOf(-1).l(0));
    }

    /**
     * Tests less or equal operator.
     */
    @Test
    public void testLeq()
    {
        assertFalse(SisuBigDecimal.valueOf(1).leq(SisuBigDecimal.valueOf(0)));
        assertTrue(SisuBigDecimal.valueOf(0).leq(SisuBigDecimal.valueOf(0)));
        assertTrue(SisuBigDecimal.valueOf(-1).leq(SisuBigDecimal.valueOf(0)));
        // double version
        assertFalse(SisuBigDecimal.valueOf(1).leq(0));
        assertTrue(SisuBigDecimal.valueOf(0).leq(0));
        assertTrue(SisuBigDecimal.valueOf(-1).leq(0));
    }

    /**
     * Tests multiplication.
     */
    @Test
    public void testMul()
    {
        assertEquals(SisuBigDecimal.valueOf(6), SisuBigDecimal.valueOf(3).multiply(SisuBigDecimal.valueOf(2)));
        // double version
        assertEquals(SisuBigDecimal.valueOf(6), SisuBigDecimal.valueOf(3).multiply(2));
    }

    /**
     * Tests negation.
     */
    @Test
    public void testNegate()
    {
        assertEquals(SisuBigDecimal.valueOf(0), SisuBigDecimal.valueOf(0).negate());
        assertEquals(SisuBigDecimal.valueOf(-1), SisuBigDecimal.valueOf(1).negate());
        assertEquals(SisuBigDecimal.valueOf(1), SisuBigDecimal.valueOf(-1).negate());
    }

    /**
     * Tests powering value.
     */
    @Test
    public void testPow()
    {
        assertEquals(SisuBigDecimal.valueOf(0), SisuBigDecimal.valueOf(0).pow(3, 2));
        assertEquals(SisuBigDecimal.valueOf(1), SisuBigDecimal.valueOf(1).pow(3, 2));
        assertEquals(SisuBigDecimal.valueOf(8), SisuBigDecimal.valueOf(2).pow(3, 2));
        assertEquals(SisuBigDecimal.valueOf(15.63), SisuBigDecimal.valueOf(2.50).pow(3, 4));
        assertEquals(SisuBigDecimal.valueOf(-15.625), SisuBigDecimal.valueOf(-2.50).pow(3, 10));
    }

    /**
     * Tests subtraction.
     */
    @Test
    public void testSub()
    {
        assertEquals(SisuBigDecimal.valueOf(2), SisuBigDecimal.valueOf(3).subtract(SisuBigDecimal.valueOf(1)));
        // double version
        assertEquals(SisuBigDecimal.valueOf(2), SisuBigDecimal.valueOf(3).subtract(1));
    }

    /**
     * Tests conversion to the string with fixed decimal points.
     */
    @Test
    public void testToFixedDecimalString()
    {
        assertEquals("1", SisuBigDecimal.valueOf("1").toFixedDecimalString(0));
        assertEquals("1", SisuBigDecimal.valueOf("1.5").toFixedDecimalString(0));
        assertEquals("1.00", SisuBigDecimal.valueOf("1").toFixedDecimalString(2));
        assertEquals("1.50", SisuBigDecimal.valueOf("1.5").toFixedDecimalString(2));
        assertEquals("1.50", SisuBigDecimal.valueOf("1.50").toFixedDecimalString(2));
        assertEquals("1.50", SisuBigDecimal.valueOf("1.500").toFixedDecimalString(2));
        assertEquals("0.0000000000000000000000001", SisuBigDecimal.valueOf("1E-25").toFixedDecimalString(25));
        assertEquals("0.00", SisuBigDecimal.valueOf("1E-25").toFixedDecimalString(2));
        assertEquals("-1", SisuBigDecimal.valueOf("-1").toFixedDecimalString(0));
        assertEquals("-1", SisuBigDecimal.valueOf("-1.5").toFixedDecimalString(0));
        assertEquals("-1.00", SisuBigDecimal.valueOf("-1").toFixedDecimalString(2));
        assertEquals("-1.50", SisuBigDecimal.valueOf("-1.5").toFixedDecimalString(2));
        assertEquals("-1.50", SisuBigDecimal.valueOf("-1.50").toFixedDecimalString(2));
        assertEquals("-1.50", SisuBigDecimal.valueOf("-1.500").toFixedDecimalString(2));
        assertEquals("-0.0000000000000000000000001", SisuBigDecimal.valueOf("-1E-25").toFixedDecimalString(25));
        assertEquals("-0.00", SisuBigDecimal.valueOf("-1E-25").toFixedDecimalString(2));
    }

    /**
     * Tests conversion to full string.
     */
    @Test
    public void testToFullString()
    {
        assertEquals("1.5", SisuBigDecimal.valueOf("1.5").toFullString());
        assertEquals("0.0000000000000000000000001", SisuBigDecimal.valueOf("1E-25").toFullString());
    }

    /**
     * Tests decimal truncation.
     */
    @Test
    public void testTruncDecimals()
    {
        assertEquals(SisuBigDecimal.valueOf("1"), SisuBigDecimal.valueOf("1").truncDecimals());
        assertEquals(SisuBigDecimal.valueOf("1"), SisuBigDecimal.valueOf("1.5").truncDecimals());
        assertEquals(SisuBigDecimal.valueOf("10"), SisuBigDecimal.valueOf("10").truncDecimals());
        assertEquals(SisuBigDecimal.valueOf("10"), SisuBigDecimal.valueOf("10.5").truncDecimals());
        assertEquals(SisuBigDecimal.valueOf("-1"), SisuBigDecimal.valueOf("-1").truncDecimals());
        assertEquals(SisuBigDecimal.valueOf("-1"), SisuBigDecimal.valueOf("-1.5").truncDecimals());
        assertEquals(SisuBigDecimal.valueOf("-10"), SisuBigDecimal.valueOf("-10").truncDecimals());
        assertEquals(SisuBigDecimal.valueOf("-10"), SisuBigDecimal.valueOf("-10.5").truncDecimals());
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}