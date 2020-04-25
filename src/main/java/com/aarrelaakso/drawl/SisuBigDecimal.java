package com.aarrelaakso.drawl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Class for mathematical operations with BigDecimal values that are not provided by the BigDecimal class itself.
 *
 * Like a BigDecimal, a SisuBigDecimal consists of an arbitrary precision integer unscaled value and a 32-bit integer
 * scale. If zero or positive, the scale is the number of digits to the right of the decimal point. If negative, the
 * unscaled value of the number is multiplied by ten to the power of the negation of the scale. The value of the number
 * represented by the BigDecimal is therefore (unscaledValue × 10^scale).
 *
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
    public static int SCALE_FOR_OPERATIONS = 64;
    public static int SCALE_FOR_COMPARISONS = 32;
    /**
     * Zero.
     */
    public static SisuBigDecimal ZERO = new SisuBigDecimal("0");

    /**
     * Use this MathContext for comparisons.
     */
    public static MathContext mcComparisons = new MathContext(SCALE_FOR_COMPARISONS, ROUNDING_MODE);

    /**
     * Use this MathContext for other operations, such as multiplying and dividing.
     */
    public static MathContext mcOperations = new MathContext(SCALE_FOR_OPERATIONS, ROUNDING_MODE);


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
        return new SisuBigDecimal(BigDecimal.valueOf(number));
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
     * Test whether a BigDecimal is a mathematical integer.
     *
     * @param bd the BigDecimal value to test
     * @return True if bd is a mathematical integer,
     * false otherwise.
     */
    public static boolean isIntegerValue(BigDecimal bd)
    {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
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
     * Compare this SisusBigInt to another using the default MathContext.
     *
     * @param val The other SisuBigDecimal to compare to this one.
     * @return -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     */
    public int compareToFuzzy(SisuBigDecimal val)
    {
        return this.compareToFuzzy(val, mcComparisons);
    }

    /**
     * Compare this SisuBigDecimal to another fuzzily.
     *
     * @param val The other SisuBigDecimal to compare to this one.
     * @param mc The MathContext to use for the comparison.
     * @return -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     */
    public int compareToFuzzy(SisuBigDecimal val, MathContext mc)
    {
        SisuBigDecimal thisNumberRounded = SisuBigDecimal.create(this.number.round(mc));
        SisuBigDecimal xNumberRounded = val.round(mc);
        return thisNumberRounded.compareTo(xNumberRounded);
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
        return new SisuBigDecimal(number.divide(x.number, new MathContext(precision, ROUNDING_MODE)));
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
        BigDecimal div = number.divide(x.number, new MathContext(precision, ROUNDING_MODE));
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
    public SisuBigDecimal multiply(SisuBigDecimal x)
    {
        return new SisuBigDecimal(number.multiply(x.number));
    }

    /**
     * Performs multiplication operation.
     *
     * @param x other number
     * @return addition multiplication result
     */
    public SisuBigDecimal multiply(double x)
    {
        return multiply(new SisuBigDecimal(String.valueOf(x)));
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
        return SisuBigDecimal.create(number.pow(n, new MathContext(precision, ROUNDING_MODE)));
    }

    /**
     * Create a new instance that has same value as this one rounded.
     *
     * @param mc The MathContext to use for the rounding operation.
     * @return A new instance of SisuBigDecimal that has the same value as this one rounded.
     */
    public SisuBigDecimal round(MathContext mc)
    {
        return SisuBigDecimal.create(this.number.round(mc));
    }

    /**
     * Create a new instance with a set scale
     */
    public SisuBigDecimal setScale(Integer scale)
    {
        BigDecimal scaledValue = this.number.setScale(scale, ROUNDING_MODE);
        return SisuBigDecimal.create(scaledValue);
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
        //return "ANumber[" + number.toString() + "]";
        return "SisuBigDecimal[" + this.toFullString() + "]";
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
