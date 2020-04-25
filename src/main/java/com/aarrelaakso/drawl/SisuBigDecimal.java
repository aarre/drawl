package com.aarrelaakso.drawl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Class for mathematical operations with BigDecimal values that are not
 * provided by the BigDecimal class itself.
 * <p>
 * In this class precision has same meaning as in the standard java BigDecimal class.
 * That means number of digits representing the number counted from to most left non zero one.
 *
 * @author radek.hecl
 * @url https://dzone.com/articles/arbitrary-precision-numbers
 */
public class SisuBigDecimal implements Comparable<SisuBigDecimal>
{

    public static final BigDecimal HALF = BigDecimal.valueOf(0.5);
    public static final BigDecimal TWO = BigDecimal.valueOf(2);
    public static RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    public static int SCALE = 32;
    /**
     * Zero.
     */
    public static SisuBigDecimal ZERO = new SisuBigDecimal("0");
    public static MathContext mathContext = new MathContext(32, RoundingMode.HALF_UP);
    /**
     * Precise number representation.
     */
    private BigDecimal number;

    /**
     * Prevents construction from outside.
     */
    private SisuBigDecimal()
    {
    }

    /**
     * Creates new instance.
     *
     * @param number number
     */
    private SisuBigDecimal(BigDecimal number)
    {
        this.number = number.stripTrailingZeros();
    }

    /**
     * Creates new instance.
     *
     * @param s string representation
     */
    private SisuBigDecimal(String s)
    {
        this.number = new BigDecimal(s).stripTrailingZeros();
    }

    /**
     * Test whether a BigDecimal is a mathematical integer.
     *
     * @param bd the BigDecimal value to test
     * @return True if bd is a mathematical integer,
     * false otherwise.
     */
    static boolean isIntegerValue(BigDecimal bd)
    {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }

    /**
     * Creates new instance. This method is capable to parse any result of toFullString method.
     *
     * @param s source string
     * @return created number
     */
    public static SisuBigDecimal create(String s)
    {
        return new SisuBigDecimal(s);
    }

    /**
     * Creates new instance.
     *
     * @param number number
     * @return created number
     */
    public static SisuBigDecimal create(double number)
    {
        return new SisuBigDecimal(new BigDecimal(String.valueOf(number)));
    }

    /**
     * Creates new instance.
     *
     * @param number number
     * @return created number
     */
    public static SisuBigDecimal create(BigDecimal number)
    {
        return new SisuBigDecimal(number);
    }

    /**
     * Returns absolute value of this number.
     *
     * @return absolute value of this number
     */
    public SisuBigDecimal abs()
    {
        return SisuBigDecimal.create(number.abs());
    }

    /**
     * Performs addition operation.
     *
     * @param x other number
     * @return addition operation result
     */
    public SisuBigDecimal add(SisuBigDecimal x)
    {
        return new SisuBigDecimal(number.add(x.number));
    }

    /**
     * Performs addition operation.
     *
     * @param x other number
     * @return addition operation result
     */
    public SisuBigDecimal add(double x)
    {
        return add(new SisuBigDecimal(String.valueOf(x)));
    }

    @Override
    public int compareTo(SisuBigDecimal x)
    {
        return number.compareTo(x.number);
    }

    /**
     * Performs division operation.
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    public SisuBigDecimal div(SisuBigDecimal x, int precision)
    {
        return new SisuBigDecimal(number.divide(x.number, new MathContext(precision, RoundingMode.DOWN)));
    }

    /**
     * Performs division operation.
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    public SisuBigDecimal div(double x, int precision)
    {
        return div(new SisuBigDecimal(String.valueOf(x)), precision);
    }

    /**
     * Performs division operation and returns the result with remainder.
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    public SisuBigDecimalRemainderPair divWithRemainder(SisuBigDecimal x, int precision)
    {
        BigDecimal div = number.divide(x.number, new MathContext(precision, RoundingMode.DOWN));
        BigDecimal rem = number.subtract(div.multiply(x.number));
        return SisuBigDecimalRemainderPair.create(new SisuBigDecimal(div), new SisuBigDecimal(rem));
    }

    /**
     * Performs division operation and returns the result with remainder.
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    public SisuBigDecimalRemainderPair divWithRemainder(double x, int precision)
    {
        return divWithRemainder(new SisuBigDecimal(String.valueOf(x)), precision);
    }

    /**
     * Returns double representation of this number.
     * Conversion might ended up by loosing the precision.
     *
     * @return double representation of this value
     */
    public double doubleValue()
    {
        return number.doubleValue();
    }

    /**
     * Returns whether this number is equal with the other number.
     *
     * @param x tested number
     * @return true if this number is equal to the other number
     */
    public boolean eq(SisuBigDecimal x)
    {
        return number.compareTo(x.number) == 0;
    }

    /**
     * Returns whether this number is equal with the other number.
     *
     * @param x tested number
     * @return true if this number is equal to the other number
     */
    public boolean eq(double x)
    {
        return eq(new SisuBigDecimal(String.valueOf(x)));
    }

    @Override
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * Returns whether this number is greater than the other number.
     *
     * @param x tested number
     * @return true if this number is greater than the other one
     */
    public boolean g(SisuBigDecimal x)
    {
        return number.compareTo(x.number) == 1;
    }

    /**
     * Returns whether this number is greater than the other number.
     *
     * @param x tested number
     * @return true if this number is greater than the other one
     */
    public boolean g(double x)
    {
        return g(new SisuBigDecimal(String.valueOf(x)));
    }

    /**
     * Returns whether this number is greater or equal to the other number.
     *
     * @param x tested number
     * @return true if this number is greater or equal to the other one
     */
    public boolean geq(SisuBigDecimal x)
    {
        return number.compareTo(x.number) >= 0;
    }

    /**
     * Returns whether this number is greater or equal to the other number.
     *
     * @param x tested number
     * @return true if this number is greater or equal to the other one
     */
    public boolean geq(double x)
    {
        return geq(SisuBigDecimal.create(String.valueOf(x)));
    }

    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Returns whether this number is less than the other number.
     *
     * @param x tested number
     * @return true if this number is less than the other one
     */
    public boolean l(SisuBigDecimal x)
    {
        return number.compareTo(x.number) < 0;
    }

    /**
     * Returns whether this number is less than the other number.
     *
     * @param x tested number
     * @return true if this number is less than the other one
     */
    public boolean l(double x)
    {
        return l(SisuBigDecimal.create(String.valueOf(x)));
    }

    /**
     * Returns whether this number is less or equal to the other number.
     *
     * @param x tested number
     * @return true if this number is less than the other one
     */
    public boolean leq(SisuBigDecimal x)
    {
        return number.compareTo(x.number) <= 0;
    }

    /**
     * Returns whether this number is less or equal to the other number.
     *
     * @param x tested number
     * @return true if this number is less than the other one
     */
    public boolean leq(double x)
    {
        return leq(SisuBigDecimal.create(String.valueOf(x)));
    }

    /**
     * Performs multiplication operation.
     *
     * @param x other number
     * @return addition multiplication result
     */
    public SisuBigDecimal mul(SisuBigDecimal x)
    {
        return new SisuBigDecimal(number.multiply(x.number));
    }

    /**
     * Performs multiplication operation.
     *
     * @param x other number
     * @return addition multiplication result
     */
    public SisuBigDecimal mul(double x)
    {
        return mul(new SisuBigDecimal(String.valueOf(x)));
    }

    /**
     * Negates this number.
     *
     * @return negative of this number
     */
    public SisuBigDecimal negate()
    {
        return SisuBigDecimal.create(number.negate());
    }

    /**
     * Returns this number powered by n.
     *
     * @param n         power
     * @param precision precision of the result (see the class level comment for details)
     * @return power operation result
     */
    public SisuBigDecimal pow(int n, int precision)
    {
        return SisuBigDecimal.create(number.pow(n, new MathContext(precision, RoundingMode.DOWN)));
    }

    /**
     * Performs subtraction operation.
     *
     * @param x other number
     * @return addition subtraction result
     */
    public SisuBigDecimal sub(SisuBigDecimal x)
    {
        return new SisuBigDecimal(number.subtract(x.number));
    }

    /**
     * Performs subtraction operation.
     *
     * @param x other number
     * @return addition subtraction result
     */
    public SisuBigDecimal sub(double x)
    {
        return sub(new SisuBigDecimal(String.valueOf(x)));
    }

    /**
     * Converts number to the string with fixed decimal digits.
     * <p>
     * This method might truncate some numbers or add extra zeros at the end.
     *
     * @param numDecimals number of decimals, must be non negative
     * @return number as a plain string with specified number of decimals
     */
    public String toFixedDecimalString(int numDecimals)
    {
        assert (numDecimals >= 0) : "numDecimals must be >= 0";
        String str = number.toPlainString();
        String[] parts = str.split("\\.");
        if (parts.length == 1)
        {
            if (numDecimals == 0)
            {
                return parts[0];
            }
            else
            {
                return parts[0] + "." + StringUtils.repeat("0", numDecimals);
            }
        }
        else if (parts.length == 2)
        {
            if (numDecimals == 0)
            {
                return parts[0];
            }
            else if (parts[1].length() > numDecimals)
            {
                return parts[0] + "." + parts[1].substring(0, numDecimals);
            }
            else if (parts[1].length() < numDecimals)
            {
                return str + StringUtils.repeat("0", numDecimals - parts[1].length());
            }
            else
            {
                return str;
            }
        }
        else
        {
            throw new RuntimeException("unexpected number of '.': " + str);
        }
    }

    /**
     * Converts this number to the full, containing everything that can be restored.
     *
     * @return number in it's string representation
     */
    public String toFullString()
    {
        return number.toPlainString();
    }

    @Override
    public String toString()
    {
        return "ANumber[" + number.toString() + "]";
    }

    /**
     * Truncates all the fractional part of this number.
     * This is similar to narrowing type from double to long.
     *
     * @return number without part right from the decimal point
     */
    public SisuBigDecimal truncDecimals()
    {
        return new SisuBigDecimal(new BigDecimal(number.toBigInteger()));
    }
}
