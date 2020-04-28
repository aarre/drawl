package com.aarrelaakso.drawl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Class for mathematical operations with BigDecimal values that are not provided by the BigDecimal class itself.
 * <p>
 * Like a BigDecimal, a SisuBigDecimal consists of an arbitrary precision integer unscaled value and a 32-bit integer
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
public class SisuBigDecimal implements Comparable<SisuBigDecimal>
{

    protected static final SisuBigDecimal HALF = SisuBigDecimal.valueOf(0.5);
    protected static final SisuBigDecimal ONE = SisuBigDecimal.valueOf(BigDecimal.ONE);
    protected static final SisuBigDecimal TWO = SisuBigDecimal.valueOf(2);
    protected static RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    protected static int SCALE_FOR_COMPARISONS = 32;
    protected static int SCALE_FOR_OPERATIONS = 64;
    /**
     * Zero.
     */
    protected static SisuBigDecimal ZERO = new SisuBigDecimal("0");

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
    protected SisuBigDecimal(BigDecimal number)
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
    protected static SisuBigDecimal valueOf(String s)
    {
        return new SisuBigDecimal(s);
    }

    /**
     * Creates new instance.
     *
     * @param number number
     * @return created number
     */
    protected static SisuBigDecimal valueOf(double number)
    {
        return new SisuBigDecimal(BigDecimal.valueOf(number));
    }

    /**
     * Creates new instance.
     *
     * @param number number
     * @return created number
     */
    protected static SisuBigDecimal valueOf(BigDecimal number)
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
    protected SisuBigDecimal abs()
    {
        return SisuBigDecimal.valueOf(number.abs());
    }

    /**
     * Performs addition operation.
     *
     * @param x other number
     * @return addition operation result
     */
    protected SisuBigDecimal add(SisuBigDecimal x)
    {
        return new SisuBigDecimal(number.add(x.number));
    }

    /**
     * Performs addition operation.
     *
     * @param x other number
     * @return addition operation result
     */
    protected SisuBigDecimal add(double x)
    {
        return add(new SisuBigDecimal(String.valueOf(x)));
    }

    /**
     * Returns a SisuBigDecimal whose value is (this + augend), with rounding according to the context settings. If
     * either number is zero and the precision setting is nonzero, then the other number, rounded if necessary, is used
     * as the result.
     *
     * @throws ArithmeticException if the result is inexact but the rounding mode is UNNECESSARY.
     * @param augend value to be added to this SisuBigDecimal.
     * @param mc the MathContext to use.
     * @return this + augend, rounded as necessary
     */
    protected SisuBigDecimal add(SisuBigDecimal augend, MathContext mc)
    {
        return new SisuBigDecimal(number.add(augend.number, mc));
    }

    /**
     * Converts this SisuBigDecimal to a BigDecimal.
     *
     * @return this SisuBigDecimal converted to a BigDecimal.
     */
    protected BigDecimal bigDecimalValue()
    {
        return this.number;
    }

    /**
     * Compares this BigDecimal with the specified BigDecimal. Two BigDecimal objects that are equal in value but have
     * a different scale (like 2.0 and 2.00) are considered equal by this method.
     *
     * This method overrides the compareTo method in the java.lang.Comparable interface.
     *
     * @param val BigDecimal to which this BigDecimal is to be compared.
     * @returns -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     */
    @Override
    public int compareTo(SisuBigDecimal val)
    {
        return number.compareTo(val.number);
    }

    /**
     * Compare this SisusBigInt to another using the default MathContext.
     *
     * @param val The other SisuBigDecimal to compare to this one.
     * @return -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     */
    protected int compareToFuzzy(SisuBigDecimal val)
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
    protected int compareToFuzzy(SisuBigDecimal val, MathContext mc)
    {
        SisuBigDecimal thisNumberRounded = SisuBigDecimal.valueOf(this.number.round(mc));
        SisuBigDecimal xNumberRounded = val.round(mc);
        return thisNumberRounded.compareTo(xNumberRounded);
    }

    protected SisuBigDecimal divide(SisuBigDecimal val, MathContext mathContext)
    {
        return new SisuBigDecimal(number.divide(val.number, mathContext));
    }

    /**
     * Performs division operation.
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    protected SisuBigDecimal divide(SisuBigDecimal x, int precision)
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
    protected SisuBigDecimal divide(double x, int precision)
    {
        return divide(new SisuBigDecimal(String.valueOf(x)), precision);
    }

    /**
     * Performs division operation and returns the result with remainder.
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    protected SisuBigDecimalRemainderPair divWithRemainder(SisuBigDecimal x, int precision)
    {
        BigDecimal div = number.divide(x.number, new MathContext(precision, RoundingMode.DOWN));
        BigDecimal rem = number.subtract(div.multiply(x.number));
        return SisuBigDecimalRemainderPair.valueOf(new SisuBigDecimal(div), new SisuBigDecimal(rem));
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
        return divWithRemainder(new SisuBigDecimal(String.valueOf(x)), precision);
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
    protected boolean equals(SisuBigDecimal x)
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
        return equals(new SisuBigDecimal(String.valueOf(x)));
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
    protected boolean g(SisuBigDecimal x)
    {
        return number.compareTo(x.number) == 1;
    }

    /**
     * Returns whether this number is greater than the other number.
     *
     * @param x tested number
     * @return true if this number is greater than the other one
     */
    protected boolean g(double x)
    {
        return g(new SisuBigDecimal(String.valueOf(x)));
    }

    /**
     * Returns whether this number is greater or equal to the other number.
     *
     * @param x tested number
     * @return true if this number is greater or equal to the other one
     */
    protected boolean geq(SisuBigDecimal x)
    {
        return number.compareTo(x.number) >= 0;
    }

    /**
     * Returns whether this number is greater or equal to the other number.
     *
     * @param x tested number
     * @return true if this number is greater or equal to the other one
     */
    protected boolean geq(double x)
    {
        return geq(SisuBigDecimal.valueOf(String.valueOf(x)));
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
     * Returns whether this number is less than the other number.
     *
     * @param x tested number
     * @return true if this number is less than the other one
     */
    protected boolean l(SisuBigDecimal x)
    {
        return number.compareTo(x.number) < 0;
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
     * Returns whether this number is less than the other number.
     *
     * @param x tested number
     * @return true if this number is less than the other one
     */
    protected boolean l(double x)
    {
        return l(SisuBigDecimal.valueOf(String.valueOf(x)));
    }

    /**
     * Returns whether this number is less or equal to the other number.
     *
     * @param x tested number
     * @return true if this number is less than the other one
     */
    protected boolean leq(SisuBigDecimal x)
    {
        return number.compareTo(x.number) <= 0;
    }

    /**
     * Returns whether this number is less or equal to the other number.
     *
     * @param x tested number
     * @return true if this number is less than the other one
     */
    protected boolean leq(double x)
    {
        return leq(SisuBigDecimal.valueOf(String.valueOf(x)));
    }

    /**
     * Performs multiplication operation.
     *
     * @param x other number
     * @return addition multiplication result
     */
    protected SisuBigDecimal multiply(SisuBigDecimal x)
    {
        return new SisuBigDecimal(number.multiply(x.number));
    }

    /**
     * Returns a BigDecimal whose value is (this × multiplicand), with rounding according to the context settings.
     *
     * @throws ArithmeticException if the result is inexact but the rounding mode is UNNECESSARY.
     * @param multiplicand value to be multiplied by this BigDecimal.
     * @param mc the MathContext to use.
     * @return this * multiplicand, rounded as necessary.
     */
    protected SisuBigDecimal multiply(SisuBigDecimal multiplicand, MathContext mc)
    {
        return new SisuBigDecimal(this.number.multiply(multiplicand.number, mc));
    }

    /**
     * Performs multiplication operation.
     *
     * @param x other number
     * @return addition multiplication result
     */
    protected SisuBigDecimal multiply(double x)
    {
        return multiply(new SisuBigDecimal(String.valueOf(x)));
    }

    /**
     * Negates this number.
     *
     * @return negative of this number
     */
    protected SisuBigDecimal negate()
    {
        return SisuBigDecimal.valueOf(number.negate());
    }

    /**
     * Returns this number powered by n.
     *
     * @param n         power
     * @param precision precision of the result (see the class level comment for details)
     * @return power operation result
     */
    protected SisuBigDecimal pow(int n, int precision)
    {
        return SisuBigDecimal.valueOf(number.pow(n, new MathContext(precision, ROUNDING_MODE)));
    }

    /**
     * Create a new instance that has same value as this one rounded.
     *
     * @param mc The MathContext to use for the rounding operation.
     * @return A new instance of SisuBigDecimal that has the same value as this one rounded.
     */
    protected SisuBigDecimal round(MathContext mc)
    {
        return SisuBigDecimal.valueOf(this.number.round(mc));
    }

    /**
     * Create a new instance with a set scale
     */
    protected SisuBigDecimal setScale(Integer scale)
    {
        BigDecimal scaledValue = this.number.setScale(scale, ROUNDING_MODE);
        return SisuBigDecimal.valueOf(scaledValue);
    }

    /**
     * Performs subtraction operation.
     *
     * @param x other number
     * @return addition subtraction result
     */
    protected SisuBigDecimal subtract(SisuBigDecimal x)
    {
        return new SisuBigDecimal(number.subtract(x.number));
    }

    /**
     * Returns a BigDecimal whose value is (this - subtrahend), with rounding according to the context settings. If
     * subtrahend is zero, then this, rounded if necessary, is used as the result. If this is zero then the result is
     * subtrahend.negate(mc).
     *
     * @throws ArithmeticException if the result is inexact but the rounding mode is UNNECESSARY.
     * @param subtrahend value to be subtracted from this SisuBigDecimal.
     * @param mc the MathContext to use.
     * @return this - subtrahend, rounded as necessary
     */
    protected SisuBigDecimal subtract(SisuBigDecimal subtrahend, MathContext mc)
    {
        return new SisuBigDecimal(number.subtract(subtrahend.number, mc));
    }

    /**
     * Performs subtraction operation.
     *
     * @param x other number
     * @return addition subtraction result
     */
    protected SisuBigDecimal subtract(double x)
    {
        return subtract(new SisuBigDecimal(String.valueOf(x)));
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
     * Converts this number to the full, containing everything that can be restored.
     *
     * @return number in it's string representation
     */
    protected String toFullString()
    {
        return number.toPlainString();
    }

    /**
     * Returns the string representation of this SisuBigDecimal, using scientific notation if an exponent is needed. A
     * standard canonical string form of the SisuBigDecimal is created as though by the following steps: first, the
     * absolute value of the unscaled value of the BigDecimal is converted to a string in base ten using the characters
     * '0' through '9' with no leading zeros (except if its value is zero, in which case a single '0' character is
     * used). Next, an adjusted exponent is calculated; this is the negated scale, plus the number of characters in the
     * converted unscaled value, less one. That is, -scale+(ulength-1), where ulength is the length of the absolute
     * value of the unscaled value in decimal digits (its precision).
     *
     * If the scale is greater than or equal to zero and the adjusted exponent is greater than or equal to -6, the
     * number will be converted to a character form without using exponential notation. In this case, if the scale is
     * zero then no decimal point is added and if the scale is positive a decimal point will be inserted with the scale
     * specifying the number of characters to the right of the decimal point. '0' characters are added to the left of
     * the converted unscaled value as necessary. If no character precedes the decimal point after this insertion then
     * a conventional '0' character is prefixed.
     *
     * Otherwise (that is, if the scale is negative, or the adjusted exponent is less than -6), the number will be
     * converted to a character form using exponential notation. In this case, if the converted BigInteger has more than
     * one digit a decimal point is inserted after the first digit. An exponent in character form is then suffixed to
     * the converted unscaled value (perhaps with inserted decimal point); this comprises the letter 'E' followed
     * immediately by the adjusted exponent converted to a character form. The latter is in base ten, using the
     * characters '0' through '9' with no leading zeros, and is always prefixed by a sign character '-' ('\u002D') if
     * the adjusted exponent is negative, '+' ('\u002B') otherwise).
     *
     * Finally, the entire string is prefixed by a minus sign character '-' ('\u002D') if the unscaled value is less
     * than zero. No sign character is prefixed if the unscaled value is zero or positive.
     *
     * Examples:
     *
     * For each representation [unscaled value, scale] on the left, the resulting string is shown on the right.
     *
     *  [123,0]      "123"
     *  [-123,0]     "-123"
     *  [123,-1]     "1.23E+3"
     *  [123,-3]     "1.23E+5"
     *  [123,1]      "12.3"
     *  [123,5]      "0.00123"
     *  [123,10]     "1.23E-8"
     *  [-123,12]    "-1.23E-10"
     *
     * Notes:
     *
     * There is a one-to-one mapping between the distinguishable BigDecimal values and the result of this conversion.
     * That is, every distinguishable BigDecimal value (unscaled value and scale) has a unique string representation
     * as a result of using toString. If that string representation is converted back to a BigDecimal using the
     * BigDecimal(String) constructor, then the original value will be recovered.
     *
     * The string produced for a given number is always the same; it is not affected by locale. This means that it can
     * be used as a canonical string representation for exchanging decimal data, or as a key for a Hashtable, etc.
     * Locale-sensitive number formatting and parsing is handled by the NumberFormat class and its subclasses.
     *
     * The toEngineeringString() method may be used for presenting numbers with exponents in engineering notation, and
     * the setScale method may be used for rounding a BigDecimal so it has a known number of digits after the decimal
     * point.
     *
     * The digit-to-character mapping provided by Character.forDigit is used.
     *
     * Overrides:
     * toString in class Object
     *
     * @return string representation of this BigDecimal.
     */
    private int temp()
    {
        return 1;
    }

    @Override
    public String toString()
    {
        return "SisuBigDecimal[" + this.toFullString() + "]";
    }

    /**
     * Returns a string representation of this Sisu BigDecimal without an exponent field. For values with a positive
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
        return this.number.toPlainString();
    }

    /**
     * Truncates all the fractional part of this number.
     * This is similar to narrowing type from double to long.
     *
     * @return number without part right from the decimal point
     */
    protected SisuBigDecimal truncDecimals()
    {
        return new SisuBigDecimal(new BigDecimal(number.toBigInteger()));
    }
}
