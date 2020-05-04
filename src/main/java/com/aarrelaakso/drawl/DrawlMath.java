package com.aarrelaakso.drawl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Class for mathematical operations with Double values that have a similar API to that of the BigDecimal class.
 * <p>
 * Makes it easier to swap out a Double implementation for a BigDecimal implementation (SisuBigDecimal) as needs
 * dictate.
 */
public class DrawlMath implements Comparable<DrawlMath>
{

    protected static final DrawlMath HALF = DrawlMath.valueOf(0.5);
    protected static final DrawlMath ONE = DrawlMath.valueOf(BigDecimal.ONE);
    protected static final DrawlMath TWO = DrawlMath.valueOf(2);
    protected static RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    protected static int SCALE_FOR_COMPARISONS = 32;
    protected static int SCALE_FOR_OPERATIONS = 64;
    /**
     * Zero.
     */
    protected static DrawlMath ZERO = new DrawlMath("0");

    /**
     * Use this MathContext for comparisons.
     */
    protected static MathContext mcComparisons = new MathContext(SCALE_FOR_COMPARISONS, ROUNDING_MODE);

    /**
     * Use this MathContext for other operations, such as multiplying and dividing.
     */
    protected static MathContext mcOperations = new MathContext(SCALE_FOR_OPERATIONS, ROUNDING_MODE);


    /**
     * Precise number representation.
     */
    private Double number;

    /**
     * Prevents construction from outside.
     */
    private DrawlMath()
    {
    }

    /**
     * Creates new instance.
     *
     * @param number number
     */
    protected DrawlMath(Double number)
    {
        this.number = number;
    }

    /**
     * Creates a new instance
     */
    @Contract(pure = true)
    protected DrawlMath(Integer number)
    {
        this.number = number.doubleValue();
    }

    /**
     * Creates new instance.
     *
     * @param s string representation
     */
    private DrawlMath(String s)
    {
        this.number = Double.valueOf(s);
    }

    /**
     * Creates new instance. This method is capable to parse any result of toFullString method.
     *
     * @param s source string
     * @return created number
     */
    @Contract("_ -> new")
    protected static @NotNull DrawlMath valueOf(String s)
    {
        return new DrawlMath(s);
    }

    /**
     * Creates new instance.
     *
     * @param number number
     * @return created number
     */
    @Contract(value = "_ -> new", pure = true)
    protected static @NotNull DrawlMath valueOf(double number)
    {
        return new DrawlMath(number);
    }

    /**
     * Creates new instance.
     *
     * @param number number
     * @return created number
     */
    @Contract("_ -> new")
    protected static @NotNull DrawlMath valueOf(BigDecimal number)
    {
        return new DrawlMath(number.doubleValue());
    }

    /**
     * Creates a new instance.
     *
     * @param number The value of the new DrawlMath instance.
     * @return The new DrawlMath instance.
     */
    @Contract("_ -> new")
    protected static @NotNull DrawlMath valueOf(Integer number)
    {
        return new DrawlMath(number);
    }
    /**
     * Test whether a BigDecimal is a mathematical integer.
     *
     * @param bd the BigDecimal value to test
     * @return True if bd is a mathematical integer,
     * false otherwise.
     */
    protected static boolean isIntegerValue(BigDecimal bd)
    {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }

    /**
     * Test whether a float is a mathematical integer.
     *
     * @param val
     * @return
     */
    protected static boolean isIntegerValue(float val)
    {
        // See: https://stackoverflow.com/questions/4727569/how-to-check-whether-a-float-has-an-integer-value
        return ((int) val) == val;
    }

    /**
     * Returns absolute value of this number.
     *
     * @return absolute value of this number
     */
    protected DrawlMath abs()
    {
        return DrawlMath.valueOf(Math.abs(number));
    }

    /**
     * Performs addition operation.
     *
     * @param x other number
     * @return addition operation result
     */
    protected DrawlMath add(DrawlMath x)
    {
        return new DrawlMath(number + x.number);
    }

    /**
     * Performs addition operation.
     *
     * @param x other number
     * @return addition operation result
     */
    protected DrawlMath add(double x)
    {
        return add(new DrawlMath(String.valueOf(x)));
    }

    /**
     * Returns a SisuBigDecimal whose value is (this + augend), with rounding according to the context settings. If
     * either number is zero and the precision setting is nonzero, then the other number, rounded if necessary, is used
     * as the result.
     *
     * @param augend value to be added to this SisuBigDecimal.
     * @param mc     the MathContext to use.
     * @return this + augend, rounded as necessary
     */
    protected DrawlMath add(@NotNull DrawlMath augend, MathContext mc)
    {
        return new DrawlMath(this.number + augend.number);
    }

    /**
     * Converts this SisuBigDecimal to a BigDecimal.
     *
     * @return this SisuBigDecimal converted to a BigDecimal.
     */
    protected BigDecimal bigDecimalValue()
    {
        return BigDecimal.valueOf(this.number);
    }

    /**
     * Compares this BigDecimal with the specified BigDecimal. Two BigDecimal objects that are equal in value but have
     * a different scale (like 2.0 and 2.00) are considered equal by this method.
     * <p>
     * This method overrides the compareTo method in the java.lang.Comparable interface.
     *
     * @param val BigDecimal to which this BigDecimal is to be compared.
     * @returns -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     */
    @Override
    public int compareTo(@NotNull DrawlMath val)
    {
        return number.compareTo(val.number);
    }

    /**
     * Compare this SisusBigInt to another using the default MathContext.
     *
     * @param val The other SisuBigDecimal to compare to this one.
     * @return -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     */
    protected int compareToFuzzy(DrawlMath val)
    {
        return this.compareToFuzzy(val, mcComparisons);
    }

    /**
     * Compare this SisuBigDecimal to another fuzzily.
     *
     * @param val The other SisuBigDecimal to compare to this one.
     * @param mc  The MathContext to use for the comparison.
     * @return -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     */
    protected int compareToFuzzy(DrawlMath val, MathContext mc)
    {
        DrawlMath thisNumberRounded = DrawlMath.valueOf(Math.round(this.number));
        DrawlMath xNumberRounded = val.round(mc);
        return thisNumberRounded.compareTo(xNumberRounded);
    }

    /**
     * Performs division operation and returns the result with remainder.
     *
     * @param x         divisor number
     * @param precision This parameter is ignored. It is preserved for interface compatibility with SisuBigDecimal.
     * @return division operation result
     */
    protected SisuBigDecimalRemainderPair divWithRemainder(DrawlMath x, int precision)
    {
        Double div = this.number / x.number;
        Double rem = this.number - (div * x.number);
        return SisuBigDecimalRemainderPair.valueOf(SisuBigDecimal.valueOf(div), SisuBigDecimal.valueOf(rem));
    }

    /**
     * Performs division operation and returns the result with remainder.
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    protected SisuBigDecimalRemainderPair divWithRemainder(double x, int precision)
    {
        return divWithRemainder(new DrawlMath(String.valueOf(x)), precision);
    }

    protected DrawlMath divide(DrawlMath val, MathContext mathContext)
    {
        return new DrawlMath(this.number / val.number);
    }

    /**
     * Performs division operation.
     *
     * @param x         divisor number
     * @param precision This parameter is ignored. It is preserved for API compatibility with SisuBigDecimal.
     * @return division operation result
     */
    protected DrawlMath divide(DrawlMath x, int precision)
    {
        return new DrawlMath(this.number / x.number);
    }

    /**
     * Performs division operation.
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    protected DrawlMath divide(double x, int precision)
    {
        return divide(new DrawlMath(String.valueOf(x)), precision);
    }

    /**
     * Returns double representation of this number.
     * Conversion might ended up by loosing the precision.
     *
     * @return double representation of this value
     */
    protected double doubleValue()
    {
        return number.doubleValue();
    }

    /**
     * Returns whether this number is equal with the other number.
     *
     * @param x tested number
     * @return true if this number is equal to the other number
     */
    protected boolean equals(DrawlMath x)
    {
        return number.compareTo(x.number) == 0;
    }

    /**
     * Returns whether this number is equal with the other number.
     *
     * @param x tested number
     * @return true if this number is equal to the other number
     */
    protected boolean equals(double x)
    {
        return equals(new DrawlMath(String.valueOf(x)));
    }

    /**
     * Compares this SisuBigDecimal with the specified Object for equality. Unlike compareTo, this method considers two
     * SisuBigDecimal objects equal only if they are equal in value and scale (thus 2.0 is not equal to 2.00 when
     * compared by this method).
     *
     * @param obj Object to which this BigDecimal is to be compared.
     * @returns true if and only if the specified Object is a SisuBigDecimal whose value and scale are equal to this
     * SisuBigDecimal's.
     */
    @Override
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * Converts this BigDecimal to a Float. This conversion is similar to the narrowing primitive conversion from
     * double to float as defined in section 5.1.3 of The Java™ Language Specification: if this BigDecimal has too great
     * a magnitude to represent as a Float, it will be converted to Float.NEGATIVE_INFINITY or Float.POSITIVE_INFINITY
     * as appropriate. Note that even when the return value is finite, this conversion can lose information about the
     * precision of the BigDecimal value.
     *
     * @return this SisuBigDecimal converted to a Float.
     */
    protected Float floatValue()
    {
        return Float.valueOf(number.floatValue());
    }

    /**
     * Returns whether this number is greater than the other number.
     *
     * @param x tested number
     * @return true if this number is greater than the other one
     */
    protected boolean isGreaterThan(DrawlMath x)
    {
        return number.compareTo(x.number) == 1;
    }

    /**
     * Returns whether this number is greater than the other number.
     *
     * @param x tested number
     * @return true if this number is greater than the other one
     */
    protected boolean isGreaterThan(double x)
    {
        return isGreaterThan(new DrawlMath(String.valueOf(x)));
    }

    /**
     * Returns the hash code for this SisuBigDecimal. Note that two SisuBigDecimal objects that are numerically equal
     * but differ in scale (like 2.0 and 2.00) will generally not have the same hash code.
     *
     * @return hash code for this BigDecimal.
     */
    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Converts this BigDecimal to an int. This conversion is analogous to the narrowing primitive conversion from
     * double to short as defined in section 5.1.3 of The Java™ Language Specification: any fractional part of this
     * BigDecimal will be discarded, and if the resulting "BigInteger" is too big to fit in an int, only the low-order
     * 32 bits are returned. Note that this conversion can lose information about the overall magnitude and precision
     * of this BigDecimal value as well as return a result with the opposite sign.
     *
     * @return this BigDecimal converted to an int.
     */
    protected Integer intValue()
    {
        return this.number.intValue();
    }

    /**
     * Returns whether this number is equal with another number.
     *
     * @param x tested number
     * @return true if this number is equal to the other number
     * @author Aarre Laakso
     * @version 1.0, 04/28/2020
     * @since 04/28/2020
     */
    protected boolean isEqualTo(DrawlMath x)
    {
        return number.compareTo(x.number) == 0;
    }

    /**
     * Returns whether this number is greater or equal to the other number.
     *
     * @param x tested number
     * @return true if this number is greater or equal to the other one
     */
    protected boolean isGreaterThanOrEqualTo(DrawlMath x)
    {
        return number.compareTo(x.number) >= 0;
    }

    /**
     * Returns whether this number is greater or equal to the other number.
     *
     * @param x tested number
     * @return true if this number is greater or equal to the other one
     */
    protected boolean isGreaterThanOrEqualTo(double x)
    {
        return isGreaterThanOrEqualTo(DrawlMath.valueOf(String.valueOf(x)));
    }

    /**
     * Returns whether this number is less or equal to the other number.
     *
     * @param x tested number
     * @return true if this number is less than the other one
     */
    protected boolean isLessThanOrEqualTo(DrawlMath x)
    {
        return number.compareTo(x.number) <= 0;
    }

    /**
     * Returns whether this number is less or equal to the other number.
     *
     * @param x tested number
     * @return true if this number is less than the other one
     */
    protected boolean isLessThanOrEqualTo(double x)
    {
        return isLessThanOrEqualTo(DrawlMath.valueOf(String.valueOf(x)));
    }

    /**
     * Indicates whether another value is not equal to this SisuBigDecimal.
     *
     * @param val
     * @return True if this SisuBigDecimal are not equal, False if they are.
     * @author Aarre Laakso
     * @version 1.0, 04/28/2020
     * @since 1.0, 04/28/2020
     */
    protected Boolean isNotEqualTo(DrawlMath val)
    {
        return !this.equals(val);
    }

    /**
     * Returns whether this number is less than the other number.
     *
     * @param x tested number
     * @return true if this number is less than the other one
     */
    protected boolean isLessThan(DrawlMath x)
    {
        return number.compareTo(x.number) < 0;
    }

    /**
     * Returns whether this number is less than the other number.
     *
     * @param x tested number
     * @return true if this number is less than the other one
     */
    protected boolean isLessThan(double x)
    {
        return isLessThan(DrawlMath.valueOf(String.valueOf(x)));
    }

    /**
     * Performs multiplication operation.
     *
     * @param x other number
     * @return addition multiplication result
     */
    protected DrawlMath multiply(DrawlMath x)
    {
        return new DrawlMath(this.number * x.number);
    }

    /**
     * Returns a BigDecimal whose value is (this × multiplicand), with rounding according to the context settings.
     *
     * @param multiplicand value to be multiplied by this BigDecimal.
     * @param mc           Ignored. Preserved for interface compatibility with SisuBigDecimal.
     * @return this * multiplicand, rounded as necessary.
     * @throws ArithmeticException if the result is inexact but the rounding mode is UNNECESSARY.
     */
    protected DrawlMath multiply(DrawlMath multiplicand, MathContext mc)
    {
        return new DrawlMath(this.number * multiplicand.number);
    }

    /**
     * Performs multiplication operation.
     *
     * @param x other number
     * @return addition multiplication result
     */
    protected DrawlMath multiply(double x)
    {
        return multiply(new DrawlMath(String.valueOf(x)));
    }

    /**
     * Negates this number.
     *
     * @return negative of this number
     */
    protected DrawlMath negate()
    {
        return DrawlMath.valueOf(-this.number);
    }

    /**
     * Returns this number powered by n.
     *
     * @param n         power
     * @param precision Ignored. Provided for compatibility with SisuBigDecimal interface.
     * @return power operation result
     */
    protected DrawlMath pow(int n, int precision)
    {
        return DrawlMath.valueOf(Math.pow(this.number, n));
    }

    /**
     * Create a new instance that has same value as this one rounded.
     *
     * @param mc Ignored. Provided for interface compatibility with SisuBigDecimal.
     * @return A new instance of SisuBigDecimal that has the same value as this one rounded.
     */
    protected DrawlMath round(MathContext mc)
    {
        return DrawlMath.valueOf(Math.round(this.number));
    }

    /**
     * Create a new instance with a set scale
     */
    //protected DrawlMath setScale(Integer scale)
    //{
    //    BigDecimal scaledValue = this.number.setScale(scale, ROUNDING_MODE);
    //    return DrawlMath.valueOf(scaledValue);
    //}

    /**
     * Performs subtraction operation.
     *
     * @param x other number
     * @return addition subtraction result
     */
    protected DrawlMath subtract(DrawlMath x)
    {
        return new DrawlMath(number - x.number);
    }

    /**
     * Returns a BigDecimal whose value is (this - subtrahend), with rounding according to the context settings. If
     * subtrahend is zero, then this, rounded if necessary, is used as the result. If this is zero then the result is
     * subtrahend.negate(mc).
     *
     * @param subtrahend value to be subtracted from this SisuBigDecimal.
     * @param mc         Ignored. Preserved for compatibility with the SisuBigDecimal interface.
     * @return this - subtrahend, rounded as necessary
     * @throws ArithmeticException if the result is inexact but the rounding mode is UNNECESSARY.
     */
    protected DrawlMath subtract(DrawlMath subtrahend, MathContext mc)
    {
        return new DrawlMath(this.number - subtrahend.number);
    }

    /**
     * Performs subtraction operation.
     *
     * @param x other number
     * @return addition subtraction result
     */
    protected DrawlMath subtract(double x)
    {
        return subtract(new DrawlMath(String.valueOf(x)));
    }

    /**
     * Converts number to the string with fixed decimal digits.
     * <p>
     * This method might truncate some numbers or add extra zeros at the end.
     *
     * @param numDecimals number of decimals, must be non negative
     * @return number as a plain string with specified number of decimals
     */
    protected String toFixedDecimalString(int numDecimals)
    {
        assert (numDecimals >= 0) : "numDecimals must be >= 0";
        String str = number.toString();
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
    protected String toFullString()
    {
        return number.toString();
    }

    /**
     * Returns a string representation of this DrawlMath without an exponent field. For values with a positive
     * scale, the number of digits to the right of the decimal point is used to indicate scale. For values with a zero
     * or negative scale, the resulting string is generated as if the value were converted to a numerically equal value
     * with zero scale and as if all the trailing zeros of the zero scale value were present in the result. The entire
     * string is prefixed by a minus sign character '-' ('\u002D') if the unscaled value is less than zero. No sign
     * character is prefixed if the unscaled value is zero or positive. Note that if the result of this method is passed
     * to the string constructor, only the numerical value of this SisuBigDecimal will necessarily be recovered; the
     * representation of the new SisuBigDecimal may have a different scale. In particular, if this BigDecimal has a
     * negative scale, the string resulting from this method will have a scale of zero when processed by the string
     * constructor.
     *
     * @returns a string representation of this SisuBigDecimal without an exponent field.
     */
    protected String toPlainString()
    {
        return this.number.toString();
    }

    @Override
    public String toString()
    {
        return "SisuBigDecimal[" + this.toFullString() + "]";
    }

}
