/*
 * Copyright (c) 2020. Aarre Laakso 
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.SisuBigDecimal;
import com.aarrelaakso.drawl.SisuBigDecimalRemainderPair;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for SisuBigDecimal
 *
 * @author radek.hecl
 *
 * @link https://dzone.com/articles/arbitrary-precision-numbers
 */
public class SisuBigDecimalTest {
    /**
     * Creates new instance.
     */
    public SisuBigDecimalTest() {
    }
    /**
     * Tests creation.
     */
    @Test
    public void testCreate() {
        assertEquals(SisuBigDecimal.create(1), SisuBigDecimal.create("1"));
        assertEquals(SisuBigDecimal.create(-1), SisuBigDecimal.create("-1"));
        assertEquals(SisuBigDecimal.create(1), SisuBigDecimal.create("1.00"));
        assertEquals(SisuBigDecimal.create(1.5), SisuBigDecimal.create("1.5"));
        assertEquals(SisuBigDecimal.create(4.95), SisuBigDecimal.create("4.95"));
        assertEquals(SisuBigDecimal.create(new BigDecimal("1e-25")), SisuBigDecimal.create("0.0000000000000000000000001"));
        assertEquals(SisuBigDecimal.create(new BigDecimal("1e-25")), SisuBigDecimal.create("0.000000000000000000000000100000"));
        //
        assertEquals(SisuBigDecimal.create(1).hashCode(), SisuBigDecimal.create("1").hashCode());
        assertEquals(SisuBigDecimal.create(1).hashCode(), SisuBigDecimal.create("1.00").hashCode());
        assertEquals(SisuBigDecimal.create(1.5).hashCode(), SisuBigDecimal.create("1.5").hashCode());
        assertEquals(SisuBigDecimal.create(1000).hashCode(), SisuBigDecimal.create("1000").hashCode());
        assertEquals(SisuBigDecimal.create(1000).hashCode(), SisuBigDecimal.create("1.000E3").hashCode());
        assertEquals(SisuBigDecimal.create(new BigDecimal("1e-25")).hashCode(), SisuBigDecimal.create("0.0000000000000000000000001").hashCode());
        assertEquals(SisuBigDecimal.create(new BigDecimal("1e-25")).hashCode(), SisuBigDecimal.create("0.000000000000000000000000100000").hashCode());
        assertEquals(SisuBigDecimal.create(1000), SisuBigDecimal.create("1000"));
        assertEquals(SisuBigDecimal.create(1000), SisuBigDecimal.create("1.000E3"));
        assertFalse(SisuBigDecimal.create(1).hashCode() == SisuBigDecimal.create("1.5").hashCode());
    }
    /**
     * Tests addition.
     */
    @Test
    public void testAdd() {
        assertEquals(SisuBigDecimal.create(2), SisuBigDecimal.create(1).add(SisuBigDecimal.create("1")));
        // double version
        assertEquals(SisuBigDecimal.create(2), SisuBigDecimal.create(1).add(1));
    }
    /**
     * Tests subtraction.
     */
    @Test
    public void testSub() {
        assertEquals(SisuBigDecimal.create(2), SisuBigDecimal.create(3).sub(SisuBigDecimal.create(1)));
        // double version
        assertEquals(SisuBigDecimal.create(2), SisuBigDecimal.create(3).sub(1));
    }
    /**
     * Tests multiplication.
     */
    @Test
    public void testMul() {
        assertEquals(SisuBigDecimal.create(6), SisuBigDecimal.create(3).mul(SisuBigDecimal.create(2)));
        // double version
        assertEquals(SisuBigDecimal.create(6), SisuBigDecimal.create(3).mul(2));
    }
    /**
     * Tests division.
     */
    @Test
    public void testDiv() {
        assertEquals(SisuBigDecimal.create("1.5"), SisuBigDecimal.create(3).div(SisuBigDecimal.create(2), 2));
        assertEquals(SisuBigDecimal.create("0.66"), SisuBigDecimal.create(2).div(SisuBigDecimal.create(3), 2));
        assertEquals(SisuBigDecimal.create("6.6"), SisuBigDecimal.create(20).div(SisuBigDecimal.create(3), 2));
        assertEquals(SisuBigDecimal.create("0.6666666666"), SisuBigDecimal.create(2).div(SisuBigDecimal.create("3"), 10));
        // double version
        assertEquals(SisuBigDecimal.create("1.5"), SisuBigDecimal.create(3).div(2, 2));
        assertEquals(SisuBigDecimal.create("0.66"), SisuBigDecimal.create(2).div(3, 2));
        assertEquals(SisuBigDecimal.create("6.6"), SisuBigDecimal.create(20).div(3, 2));
        assertEquals(SisuBigDecimal.create("0.6666666666"), SisuBigDecimal.create(2).div(3, 10));
    }
    /**
     * Tests division with remainder.
     */
    @Test
    public void testDivWithRemainder() {
        assertEquals(SisuBigDecimalRemainderPair.create(SisuBigDecimal.create("1.5"), SisuBigDecimal.create("0")),
                SisuBigDecimal.create(3).divWithRemainder(SisuBigDecimal.create(2), 2));
        assertEquals(SisuBigDecimalRemainderPair.create(SisuBigDecimal.create("0.66"), SisuBigDecimal.create("0.02")),
                SisuBigDecimal.create(2).divWithRemainder(SisuBigDecimal.create(3), 2));
        assertEquals(SisuBigDecimalRemainderPair.create(SisuBigDecimal.create("6.6"), SisuBigDecimal.create("0.2")),
                SisuBigDecimal.create(20).divWithRemainder(SisuBigDecimal.create(3), 2));
        assertEquals(SisuBigDecimalRemainderPair.create(SisuBigDecimal.create("0.6666666666"), SisuBigDecimal.create("0.0000000002")),
                SisuBigDecimal.create(2).divWithRemainder(SisuBigDecimal.create(3), 10));
        // double version
        assertEquals(SisuBigDecimalRemainderPair.create(SisuBigDecimal.create("1.5"), SisuBigDecimal.create("0")),
                SisuBigDecimal.create(3).divWithRemainder(2, 2));
        assertEquals(SisuBigDecimalRemainderPair.create(SisuBigDecimal.create("0.66"), SisuBigDecimal.create("0.02")),
                SisuBigDecimal.create(2).divWithRemainder(3, 2));
        assertEquals(SisuBigDecimalRemainderPair.create(SisuBigDecimal.create("6.6"), SisuBigDecimal.create("0.2")),
                SisuBigDecimal.create(20).divWithRemainder(3, 2));
        assertEquals(SisuBigDecimalRemainderPair.create(SisuBigDecimal.create("0.6666666666"), SisuBigDecimal.create("0.0000000002")),
                SisuBigDecimal.create(2).divWithRemainder(3, 10));
    }
    /**
     * Tests negation.
     */
    @Test
    public void testNegate() {
        assertEquals(SisuBigDecimal.create(0), SisuBigDecimal.create(0).negate());
        assertEquals(SisuBigDecimal.create(-1), SisuBigDecimal.create(1).negate());
        assertEquals(SisuBigDecimal.create(1), SisuBigDecimal.create(-1).negate());
    }
    /**
     * Tests absolute value.
     */
    @Test
    public void testAbs() {
        assertEquals(SisuBigDecimal.create(0), SisuBigDecimal.create(0).abs());
        assertEquals(SisuBigDecimal.create(1), SisuBigDecimal.create(1).abs());
        assertEquals(SisuBigDecimal.create(1), SisuBigDecimal.create(-1).abs());
    }
    /**
     * Tests powering value.
     */
    @Test
    public void testPow() {
        assertEquals(SisuBigDecimal.create(0), SisuBigDecimal.create(0).pow(3, 2));
        assertEquals(SisuBigDecimal.create(1), SisuBigDecimal.create(1).pow(3, 2));
        assertEquals(SisuBigDecimal.create(8), SisuBigDecimal.create(2).pow(3, 2));
        assertEquals(SisuBigDecimal.create(15.62), SisuBigDecimal.create(2.50).pow(3, 4));
        assertEquals(SisuBigDecimal.create(-15.625), SisuBigDecimal.create(-2.50).pow(3, 10));
    }
    /**
     * Tests decimal truncation.
     */
    @Test
    public void testTruncDecimals() {
        assertEquals(SisuBigDecimal.create("1"), SisuBigDecimal.create("1").truncDecimals());
        assertEquals(SisuBigDecimal.create("1"), SisuBigDecimal.create("1.5").truncDecimals());
        assertEquals(SisuBigDecimal.create("10"), SisuBigDecimal.create("10").truncDecimals());
        assertEquals(SisuBigDecimal.create("10"), SisuBigDecimal.create("10.5").truncDecimals());
        assertEquals(SisuBigDecimal.create("-1"), SisuBigDecimal.create("-1").truncDecimals());
        assertEquals(SisuBigDecimal.create("-1"), SisuBigDecimal.create("-1.5").truncDecimals());
        assertEquals(SisuBigDecimal.create("-10"), SisuBigDecimal.create("-10").truncDecimals());
        assertEquals(SisuBigDecimal.create("-10"), SisuBigDecimal.create("-10.5").truncDecimals());
    }
    /**
     * Tests equal operator.
     */
    @Test
    public void testEq() {
        assertTrue(SisuBigDecimal.create(0).eq(SisuBigDecimal.ZERO));
        assertFalse(SisuBigDecimal.create(1).eq(SisuBigDecimal.ZERO));
        // double version
        assertTrue(SisuBigDecimal.create(0).eq(0));
        assertFalse(SisuBigDecimal.create(1).eq(0));
    }
    /**
     * Tests greater operator.
     */
    @Test
    public void testG() {
        assertTrue(SisuBigDecimal.create(1).g(SisuBigDecimal.create(0)));
        assertFalse(SisuBigDecimal.create(0).g(SisuBigDecimal.create(0)));
        assertFalse(SisuBigDecimal.create(-1).g(SisuBigDecimal.create(0)));
        // double version
        assertTrue(SisuBigDecimal.create(1).g(0));
        assertFalse(SisuBigDecimal.create(0).g(0));
        assertFalse(SisuBigDecimal.create(-1).g(0));
    }
    /**
     * Tests greater or equal operator.
     */
    @Test
    public void testGeq() {
        assertTrue(SisuBigDecimal.create(1).geq(SisuBigDecimal.create(0)));
        assertTrue(SisuBigDecimal.create(0).geq(SisuBigDecimal.create(0)));
        assertFalse(SisuBigDecimal.create(-1).geq(SisuBigDecimal.create(0)));
        // double version
        assertTrue(SisuBigDecimal.create(1).geq(0));
        assertTrue(SisuBigDecimal.create(0).geq(0));
        assertFalse(SisuBigDecimal.create(-1).geq(0));
    }
    /**
     * Tests less operator.
     */
    @Test
    public void testL() {
        assertFalse(SisuBigDecimal.create(1).l(SisuBigDecimal.create(0)));
        assertFalse(SisuBigDecimal.create(0).l(SisuBigDecimal.create(0)));
        assertTrue(SisuBigDecimal.create(-1).l(SisuBigDecimal.create(0)));
        // double version
        assertFalse(SisuBigDecimal.create(1).l(0));
        assertFalse(SisuBigDecimal.create(0).l(0));
        assertTrue(SisuBigDecimal.create(-1).l(0));
    }
    /**
     * Tests less or equal operator.
     */
    @Test
    public void testLeq() {
        assertFalse(SisuBigDecimal.create(1).leq(SisuBigDecimal.create(0)));
        assertTrue(SisuBigDecimal.create(0).leq(SisuBigDecimal.create(0)));
        assertTrue(SisuBigDecimal.create(-1).leq(SisuBigDecimal.create(0)));
        // double version
        assertFalse(SisuBigDecimal.create(1).leq(0));
        assertTrue(SisuBigDecimal.create(0).leq(0));
        assertTrue(SisuBigDecimal.create(-1).leq(0));
    }
    /**
     * Tests conversion to full string.
     */
    @Test
    public void testToFullString() {
        assertEquals("1.5", SisuBigDecimal.create("1.5").toFullString());
        assertEquals("0.0000000000000000000000001", SisuBigDecimal.create("1E-25").toFullString());
    }
    /**
     * Tests conversion to the string with fixed decimal points.
     */
    @Test
    public void testToFixedDecimalString() {
        assertEquals("1", SisuBigDecimal.create("1").toFixedDecimalString(0));
        assertEquals("1", SisuBigDecimal.create("1.5").toFixedDecimalString(0));
        assertEquals("1.00", SisuBigDecimal.create("1").toFixedDecimalString(2));
        assertEquals("1.50", SisuBigDecimal.create("1.5").toFixedDecimalString(2));
        assertEquals("1.50", SisuBigDecimal.create("1.50").toFixedDecimalString(2));
        assertEquals("1.50", SisuBigDecimal.create("1.500").toFixedDecimalString(2));
        assertEquals("0.0000000000000000000000001", SisuBigDecimal.create("1E-25").toFixedDecimalString(25));
        assertEquals("0.00", SisuBigDecimal.create("1E-25").toFixedDecimalString(2));
        assertEquals("-1", SisuBigDecimal.create("-1").toFixedDecimalString(0));
        assertEquals("-1", SisuBigDecimal.create("-1.5").toFixedDecimalString(0));
        assertEquals("-1.00", SisuBigDecimal.create("-1").toFixedDecimalString(2));
        assertEquals("-1.50", SisuBigDecimal.create("-1.5").toFixedDecimalString(2));
        assertEquals("-1.50", SisuBigDecimal.create("-1.50").toFixedDecimalString(2));
        assertEquals("-1.50", SisuBigDecimal.create("-1.500").toFixedDecimalString(2));
        assertEquals("-0.0000000000000000000000001", SisuBigDecimal.create("-1E-25").toFixedDecimalString(25));
        assertEquals("-0.00", SisuBigDecimal.create("-1E-25").toFixedDecimalString(2));
    }
    /**
     * Tests compare to method.
     */
    @Test
    public void testCompareTo() {
        assertEquals(1, SisuBigDecimal.create(1).compareTo(SisuBigDecimal.create(0)));
        assertEquals(0, SisuBigDecimal.create(0).compareTo(SisuBigDecimal.create(0)));
        assertEquals(-1, SisuBigDecimal.create(-1).compareTo(SisuBigDecimal.create(0)));
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}