package com.aarrelaakso.drawl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Class for mathematical operations with BigDecimal values that are not provided by the BigDecimal class itself.
 * <p>
 * Like a BigDecimal, a SisuNumber consists of an arbitrary precision integer unscaled value and a 32-bit integer
 * scale. If zero or positive, the scale is the number of digits to the right of the decimal point. If negative, the
 * unscaled value of the number is multiplied by ten to the power of the negation of the scale. The value of the number
 * represented by the BigDecimal is therefore (unscaledValue × 10^scale).
 * <p>
 * In this class precision has same meaning as in the standard java BigDecimal class.
 * That means number of digits representing the number counted from to most left non zero one.
 *
 * @author radek.hecl
 * @url https://dzone.com/articles/arbitrary-precision-numbers
 */
public class SisuNumber implements Comparable<SisuNumber>
{

    protected static final SisuNumber HALF = SisuNumber.valueOf(0.5);
    protected static final SisuNumber ONE = SisuNumber.valueOf(BigDecimal.ONE);
    protected static final SisuNumber TWO = SisuNumber.valueOf(2);
    protected static @NotNull RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    protected static int SCALE_FOR_COMPARISONS = 32;
    protected static int SCALE_FOR_OPERATIONS = 64;
    /**
     * Zero.
     */
    protected static @NotNull SisuNumber ZERO = new SisuNumber("0");

    /**
     * Use this MathContext for comparisons.
     */
    protected static @NotNull MathContext mcComparisons = new MathContext(SCALE_FOR_COMPARISONS, ROUNDING_MODE);

    /**
     * Use this MathContext for other operations, such as multiplying and dividing.
     */
    protected static @NotNull MathContext mcOperations = new MathContext(SCALE_FOR_OPERATIONS, ROUNDING_MODE);


    /**
     * Precise number representation.
     */
    private BigDecimal number;

    /**
     * Prevents construction from outside.
     */
    private SisuNumber()
    {
    }

    /**
     * Creates new instance.
     *
     * @param number number
     */
    protected SisuNumber(@NotNull BigDecimal number)
    {
        this.number = number.stripTrailingZeros();
    }

    /**
     * Creates new instance.
     *
     * @param s string representation
     */
    private SisuNumber(@NotNull String s)
    {
        this.number = new BigDecimal(s).stripTrailingZeros();
    }

    /**
     * Creates new instance. This method is capable to parse any result of toFullString method.
     *
     * @param s source string
     * @return created number
     */
    protected static @NotNull SisuNumber valueOf(@NotNull String s)
    {
        return new SisuNumber(s);
    }

    /**
     * Creates new instance.
     *
     * @param number number
     * @return created number
     */
    protected static @NotNull SisuNumber valueOf(double number)
    {
        return new SisuNumber(BigDecimal.valueOf(number));
    }

    /**
     * Creates new instance.
     *
     * @param number number
     * @return created number
     */
    protected static @NotNull SisuNumber valueOf(@NotNull BigDecimal number)
    {
        return new SisuNumber(number);
    }

    protected static @NotNull SisuNumber valueOf(Integer number)
    {
        return new SisuNumber(BigDecimal.valueOf(number));
    }
    /**
     * Test whether a BigDecimal is a mathematical integer.
     *
     * @param bd the BigDecimal value to test
     * @return True if bd is a mathematical integer,
     * false otherwise.
     */
    protected static boolean isIntegerValue(@NotNull BigDecimal bd)
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
    protected @NotNull SisuNumber abs()
    {
        return SisuNumber.valueOf(number.abs());
    }

    /**
     * Performs addition operation.
     *
     * @param x other number
     * @return addition operation result
     */
    protected @NotNull SisuNumber add(@NotNull SisuNumber x)
    {
        return new SisuNumber(number.add(x.number));
    }

    /**
     * Performs addition operation.
     *
     * @param x other number
     * @return addition operation result
     */
    protected @NotNull SisuNumber add(double x)
    {
        return add(new SisuNumber(String.valueOf(x)));
    }

    /**
     * Returns a SisuNumber whose value is (this + augend), with rounding according to the context settings. If
     * either number is zero and the precision setting is nonzero, then the other number, rounded if necessary, is used
     * as the result.
     *
     * @param augend value to be added to this SisuNumber.
     * @param mc     the MathContext to use.
     * @return this + augend, rounded as necessary
     * @throws ArithmeticException if the result is inexact but the rounding mode is UNNECESSARY.
     */
    protected @NotNull SisuNumber add(@NotNull SisuNumber augend, MathContext mc)
    {
        return new SisuNumber(number.add(augend.number, mc));
    }

    /**
     * Converts this SisuNumber to a BigDecimal.
     *
     * @return this SisuNumber converted to a BigDecimal.
     */
    protected BigDecimal bigDecimalValue()
    {
        return this.number;
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
    public int compareTo(@NotNull SisuNumber val)
    {
        return number.compareTo(val.number);
    }

    /**
     * Compare this SisusBigInt to another using the default MathContext.
     *
     * @param val The other SisuNumber to compare to this one.
     * @return -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     */
    protected int compareToFuzzy(@NotNull SisuNumber val)
    {
        return this.compareToFuzzy(val, mcComparisons);
    }

    /**
     * Compare this SisuNumber to another fuzzily.
     *
     * @param val The other SisuNumber to compare to this one.
     * @param mc  The MathContext to use for the comparison.
     * @return -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     */
    protected int compareToFuzzy(@NotNull SisuNumber val, MathContext mc)
    {
        @NotNull SisuNumber thisNumberRounded = SisuNumber.valueOf(this.number.round(mc));
        @NotNull SisuNumber xNumberRounded = val.round(mc);
        return thisNumberRounded.compareTo(xNumberRounded);
    }


    protected @NotNull SisuNumber divide(@NotNull SisuNumber val, MathContext mathContext)
    {
        return new SisuNumber(number.divide(val.number, mathContext));
    }

    /**
     * Performs division operation.
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    protected @NotNull SisuNumber divide(@NotNull SisuNumber x, int precision)
    {
        return new SisuNumber(number.divide(x.number, new MathContext(precision, ROUNDING_MODE)));
    }

    /**
     * Performs division operation.
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    protected @NotNull SisuNumber divide(double x, int precision)
    {
        return divide(new SisuNumber(String.valueOf(x)), precision);
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
    protected boolean equals(@NotNull SisuNumber x)
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
        return equals(new SisuNumber(String.valueOf(x)));
    }

    /**
     * Compares this SisuNumber with the specified Object for equality. Unlike compareTo, this method considers two
     * SisuNumber objects equal only if they are equal in value and scale (thus 2.0 is not equal to 2.00 when
     * compared by this method).
     *
     * @param obj Object to which this BigDecimal is to be compared.
     * @returns true if and only if the specified Object is a SisuNumber whose value and scale are equal to this
     * SisuNumber's.
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
     * @return this SisuNumber converted to a Float.
     */
    protected @NotNull Float floatValue()
    {
        return Float.valueOf(number.floatValue());
    }

    /**
     * Returns whether this number is greater than the other number.
     *
     * @param x tested number
     * @return true if this number is greater than the other one
     */
    protected boolean isGreaterThan(@NotNull SisuNumber x)
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
        return isGreaterThan(new SisuNumber(String.valueOf(x)));
    }

    /**
     * Returns the hash code for this SisuNumber. Note that two SisuNumber objects that are numerically equal
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
    protected boolean isEqualTo(@NotNull SisuNumber x)
    {
        return number.compareTo(x.number) == 0;
    }

    /**
     * Returns whether this number is greater or equal to the other number.
     *
     * @param x tested number
     * @return true if this number is greater or equal to the other one
     */
    protected boolean isGreaterThanOrEqualTo(@NotNull SisuNumber x)
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
        return isGreaterThanOrEqualTo(SisuNumber.valueOf(String.valueOf(x)));
    }

    /**
     * Returns whether this number is less or equal to the other number.
     *
     * @param x tested number
     * @return true if this number is less than the other one
     */
    protected boolean isLessThanOrEqualTo(@NotNull SisuNumber x)
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
        return isLessThanOrEqualTo(SisuNumber.valueOf(String.valueOf(x)));
    }

    /**
     * Indicates whether another value is not equal to this SisuNumber.
     *
     * @param val
     * @return True if this SisuNumber are not equal, False if they are.
     * @author Aarre Laakso
     * @version 1.0, 04/28/2020
     * @since 1.0, 04/28/2020
     */
    protected @NotNull Boolean isNotEqualTo(@NotNull SisuNumber val)
    {
        return !this.equals(val);
    }

    /**
     * Returns whether this number is less than the other number.
     *
     * @param x tested number
     * @return true if this number is less than the other one
     */
    protected boolean isLessThan(@NotNull SisuNumber x)
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
        return isLessThan(SisuNumber.valueOf(String.valueOf(x)));
    }

    /**
     * Performs multiplication operation.
     *
     * @param x other number
     * @return addition multiplication result
     */
    protected @NotNull SisuNumber multiply(@NotNull SisuNumber x)
    {
        return new SisuNumber(number.multiply(x.number));
    }

    /**
     * Returns a BigDecimal whose value is (this × multiplicand), with rounding according to the context settings.
     *
     * @param multiplicand value to be multiplied by this BigDecimal.
     * @param mc           the MathContext to use.
     * @return this * multiplicand, rounded as necessary.
     * @throws ArithmeticException if the result is inexact but the rounding mode is UNNECESSARY.
     */
    protected @NotNull SisuNumber multiply(@NotNull SisuNumber multiplicand, MathContext mc)
    {
        return new SisuNumber(this.number.multiply(multiplicand.number, mc));
    }

    /**
     * Performs multiplication operation.
     *
     * @param x other number
     * @return addition multiplication result
     */
    protected @NotNull SisuNumber multiply(double x)
    {
        return multiply(new SisuNumber(String.valueOf(x)));
    }

    /**
     * Negates this number.
     *
     * @return negative of this number
     */
    protected @NotNull SisuNumber negate()
    {
        return SisuNumber.valueOf(number.negate());
    }

    /**
     * Returns this number powered by n.
     *
     * @param n         power
     * @param precision precision of the result (see the class level comment for details)
     * @return power operation result
     */
    protected @NotNull SisuNumber pow(int n, int precision)
    {
        return SisuNumber.valueOf(number.pow(n, new MathContext(precision, ROUNDING_MODE)));
    }

    /**
     * Create a new instance that has same value as this one rounded.
     *
     * @param mc The MathContext to use for the rounding operation.
     * @return A new instance of SisuNumber that has the same value as this one rounded.
     */
    protected @NotNull SisuNumber round(MathContext mc)
    {
        return SisuNumber.valueOf(this.number.round(mc));
    }

    /**
     * Create a new instance with a set scale
     */
    protected @NotNull SisuNumber setScale(Integer scale)
    {
        BigDecimal scaledValue = this.number.setScale(scale, ROUNDING_MODE);
        return SisuNumber.valueOf(scaledValue);
    }

    /**
     * Performs subtraction operation.
     *
     * @param x other number
     * @return addition subtraction result
     */
    protected @NotNull SisuNumber subtract(@NotNull SisuNumber x)
    {
        return new SisuNumber(number.subtract(x.number));
    }

    /**
     * Returns a BigDecimal whose value is (this - subtrahend), with rounding according to the context settings. If
     * subtrahend is zero, then this, rounded if necessary, is used as the result. If this is zero then the result is
     * subtrahend.negate(mc).
     *
     * @param subtrahend value to be subtracted from this SisuNumber.
     * @param mc         the MathContext to use.
     * @return this - subtrahend, rounded as necessary
     * @throws ArithmeticException if the result is inexact but the rounding mode is UNNECESSARY.
     */
    protected @NotNull SisuNumber subtract(@NotNull SisuNumber subtrahend, MathContext mc)
    {
        return new SisuNumber(number.subtract(subtrahend.number, mc));
    }

    /**
     * Performs subtraction operation.
     *
     * @param x other number
     * @return addition subtraction result
     */
    protected @NotNull SisuNumber subtract(double x)
    {
        return subtract(new SisuNumber(String.valueOf(x)));
    }

    /**
     * Returns the string representation of this SisuNumber, using scientific notation if an exponent is needed. A
     * standard canonical string form of the SisuNumber is created as though by the following steps: first, the
     * absolute value of the unscaled value of the BigDecimal is converted to a string in base ten using the characters
     * '0' through '9' with no leading zeros (except if its value is zero, in which case a single '0' character is
     * used). Next, an adjusted exponent is calculated; this is the negated scale, plus the number of characters in the
     * converted unscaled value, less one. That is, -scale+(ulength-1), where ulength is the length of the absolute
     * value of the unscaled value in decimal digits (its precision).
     * <p>
     * If the scale is greater than or equal to zero and the adjusted exponent is greater than or equal to -6, the
     * number will be converted to a character form without using exponential notation. In this case, if the scale is
     * zero then no decimal point is added and if the scale is positive a decimal point will be inserted with the scale
     * specifying the number of characters to the right of the decimal point. '0' characters are added to the left of
     * the converted unscaled value as necessary. If no character precedes the decimal point after this insertion then
     * a conventional '0' character is prefixed.
     * <p>
     * Otherwise (that is, if the scale is negative, or the adjusted exponent is less than -6), the number will be
     * converted to a character form using exponential notation. In this case, if the converted BigInteger has more than
     * one digit a decimal point is inserted after the first digit. An exponent in character form is then suffixed to
     * the converted unscaled value (perhaps with inserted decimal point); this comprises the letter 'E' followed
     * immediately by the adjusted exponent converted to a character form. The latter is in base ten, using the
     * characters '0' through '9' with no leading zeros, and is always prefixed by a sign character '-' ('\u002D') if
     * the adjusted exponent is negative, '+' ('\u002B') otherwise).
     * <p>
     * Finally, the entire string is prefixed by a minus sign character '-' ('\u002D') if the unscaled value is less
     * than zero. No sign character is prefixed if the unscaled value is zero or positive.
     * <p>
     * Examples:
     * <p>
     * For each representation [unscaled value, scale] on the left, the resulting string is shown on the right.
     * <p>
     * [123,0]      "123"
     * [-123,0]     "-123"
     * [123,-1]     "1.23E+3"
     * [123,-3]     "1.23E+5"
     * [123,1]      "12.3"
     * [123,5]      "0.00123"
     * [123,10]     "1.23E-8"
     * [-123,12]    "-1.23E-10"
     * <p>
     * Notes:
     * <p>
     * There is a one-to-one mapping between the distinguishable BigDecimal values and the result of this conversion.
     * That is, every distinguishable BigDecimal value (unscaled value and scale) has a unique string representation
     * as a result of using toString. If that string representation is converted back to a BigDecimal using the
     * BigDecimal(String) constructor, then the original value will be recovered.
     * <p>
     * The string produced for a given number is always the same; it is not affected by locale. This means that it can
     * be used as a canonical string representation for exchanging decimal data, or as a key for a Hashtable, etc.
     * Locale-sensitive number formatting and parsing is handled by the NumberFormat class and its subclasses.
     * <p>
     * The toEngineeringString() method may be used for presenting numbers with exponents in engineering notation, and
     * the setScale method may be used for rounding a BigDecimal so it has a known number of digits after the decimal
     * point.
     * <p>
     * The digit-to-character mapping provided by Character.forDigit is used.
     * <p>
     * Overrides:
     * toString in class Object
     *
     * @return string representation of this BigDecimal.
     */
    private int temp()
    {
        return 1;
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
     *
     * @return number in it's string representation
     */
    protected String toFullString()
    {
        return number.toPlainString();
    }

    /**
     * @returns a string representation of this SisuNumber without an exponent field.
     */
    protected String toPlainString()
    {
        return this.number.toPlainString();
    }

    @Override
    public @NotNull String toString()
    {
        return "SisuNumber[" + this.toFullString() + "]";
    }

    /**
     * Truncates all the fractional part of this number.
     * This is similar to narrowing type from double to long.
     *
     * @return number without part right from the decimal point
     */
    protected @NotNull SisuNumber truncDecimals()
    {
        return new SisuNumber(new BigDecimal(number.toBigInteger()));
    }
}
