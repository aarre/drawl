package com.aarrelaakso.drawl;

import com.google.common.flogger.FluentLogger;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.math3.util.Precision;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Class for mathematical operations with Double values that have a similar API to that of the BigDecimal class.
 * <p>
 * Makes it easier to swap out a Double implementation for a BigDecimal implementation (SisuBigDecimal) as needs
 * dictate.
 */
public class DrawlNumber implements Comparable<DrawlNumber>
{

    protected static final DrawlNumber HALF = DrawlNumber.valueOf(0.5);
    protected static final DrawlNumber ONE = DrawlNumber.valueOf(BigDecimal.ONE);
    protected static final DrawlNumber TWO = DrawlNumber.valueOf(2);
    private static final FluentLogger logger;
    protected static RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    protected static int SCALE_FOR_COMPARISONS = 32;
    protected static int SCALE_FOR_OPERATIONS = 64;
    /**
     * Zero.
     */
    protected static DrawlNumber ZERO = new DrawlNumber("0");
    /**
     * Use this MathContext for comparisons.
     */
    protected static MathContext mcComparisons = new MathContext(SCALE_FOR_COMPARISONS, ROUNDING_MODE);
    /**
     * Use this MathContext for other operations, such as multiplying and dividing.
     */
    protected static MathContext mcOperations = new MathContext(SCALE_FOR_OPERATIONS, ROUNDING_MODE);

    static
    {
        logger = FluentLogger.forEnclosingClass();

    }

    /**
     * Precise number representation.
     */
    private Double number;

    /**
     * Prevents construction from outside.
     */
    private DrawlNumber()
    {
    }

    /**
     * Creates new instance.
     *
     * @param number number
     */
    protected DrawlNumber(Double number)
    {
        this.number = number;
    }

    /**
     * Creates a new instance
     */
    @Contract(pure = true)
    protected DrawlNumber(Integer number)
    {
        this.number = number.doubleValue();
    }

    /**
     * Creates new instance.
     *
     * @param s string representation
     */
    private DrawlNumber(String s)
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
    protected static @NotNull DrawlNumber valueOf(String s)
    {
        return new DrawlNumber(s);
    }

    /**
     * Creates new instance.
     *
     * @param number number
     * @return created number
     */
    @Contract(value = "_ -> new", pure = true)
    protected static @NotNull DrawlNumber valueOf(double number)
    {
        return new DrawlNumber(number);
    }

    /**
     * Creates new instance.
     *
     * @param number number
     * @return created number
     */
    @Contract("_ -> new")
    protected static @NotNull DrawlNumber valueOf(BigDecimal number)
    {
        return new DrawlNumber(number.doubleValue());
    }

    /**
     * Creates a new instance.
     *
     * @param number The value of the new DrawlMath instance.
     * @return The new DrawlMath instance.
     */
    @Contract("_ -> new")
    protected static @NotNull DrawlNumber valueOf(Integer number)
    {
        return new DrawlNumber(number);
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
    protected DrawlNumber abs()
    {
        return DrawlNumber.valueOf(Math.abs(number));
    }

    /**
     * Performs addition operation.
     *
     * @param x other number
     * @return addition operation result
     */
    protected DrawlNumber add(DrawlNumber x)
    {
        return new DrawlNumber(number + x.number);
    }

    /**
     * Performs addition operation.
     *
     * @param x other number
     * @return addition operation result
     */
    protected DrawlNumber add(double x)
    {
        return add(new DrawlNumber(String.valueOf(x)));
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
    protected DrawlNumber add(@NotNull DrawlNumber augend, MathContext mc)
    {
        return new DrawlNumber(this.number + augend.number);
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
    public int compareTo(@NotNull DrawlNumber val)
    {
        return number.compareTo(val.number);
    }

    /**
     * Compare this DrawlNumber to another using the default MathContext.
     *
     * @param val The other DrawlNumber to compare to this one.
     * @return -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     */
    protected int compareToFuzzy(DrawlNumber val)
    {
        return this.compareToFuzzy(val, mcComparisons);
    }

    /**
     * Compare this DrawlNumber to another fuzzily.
     *
     * @param val The other DrawlNumber to compare to this one.
     * @param mc  A MathContext object. The precision field (total number of significant digits) is used as the scale
     *            (number of digits to the right of the decimal place) for this operation.
     * @return -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     */
    protected int compareToFuzzy(DrawlNumber val, MathContext mc)
    {
        //Integer precision = mc.getPrecision();
        //DrawlNumber thisNumberRounded = DrawlNumber.valueOf(Precision.round(this.number, precision));
        //DrawlNumber xNumberRounded = DrawlNumber.valueOf(Precision.round(val.doubleValue(), precision));
        //return thisNumberRounded.compareTo(xNumberRounded);

        // Use floating point comparison. The second parameter to compareTo is the units in the last place,
        // i.e., the number of floating point digits by which the numbers may differ.
        return Precision.compareTo(this.number, val.number, 1);
    }

    /**
     * Performs division operation and returns the result with remainder.
     *
     * @param x         divisor number
     * @param precision This parameter is ignored. It is preserved for interface compatibility with SisuBigDecimal.
     * @return division operation result
     */
    protected DrawlNumberRemainderPair divWithRemainder(DrawlNumber x, int precision)
    {
        Double div = this.number / x.number;
        Double rem = this.number - (div * x.number);
        return DrawlNumberRemainderPair.valueOf(DrawlNumber.valueOf(div), DrawlNumber.valueOf(rem));
    }

    /**
     * Performs division operation and returns the result with remainder.
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    protected DrawlNumberRemainderPair divWithRemainder(double x, int precision)
    {
        return divWithRemainder(new DrawlNumber(String.valueOf(x)), precision);
    }

    /**
     * Divides with DrawlNumber by another
     *
     * @param divisor     The divisor.
     * @param mathContext Ignored. Retained for compatibility with SisuNumber interface.
     * @return This divided by val.
     */
    protected DrawlNumber divide(DrawlNumber divisor, MathContext mathContext)
    {
        return new DrawlNumber(this.number / divisor.number);
    }

    /**
     * Divides this DrawlNumber by another
     *
     * @param x         divisor number
     * @param precision The precision (number of significant digits) of the result.
     * @return division operation result
     */
    protected DrawlNumber divide(@NotNull DrawlNumber x, int precision)
    {
        Double quotient = this.number / x.number;
        logger.atFine().log("Quotient is " + quotient);
        String quotientAsString = String.valueOf(quotient);
        // Remove leading zeros
        String noZerosQuotient = quotientAsString.replaceFirst("^0+(?!$)", "");
        logger.atFine().log("noZerosQuotient is " + noZerosQuotient);
        //String truncatedQuotient = noZerosQuotient.substring(0, Math.min(noZerosQuotient.length(), precision + 1));
        //logger.atFine().log("truncatedQuotient is " + truncatedQuotient);
        if ((quotient > -1) && (quotient < 1))
        {
            precision = precision + 1;
        }
        Integer digitsBeforeDecimal = (int) Math.log10(quotient) + 1;
        //Integer digitsBeforeDecimal = (int)Math.log10(quotient);
        logger.atFine().log("Digits before decimal is " + digitsBeforeDecimal);
        Integer requiredScale = precision - digitsBeforeDecimal;
        logger.atFine().log("Required scale (digits after decimal) is " + requiredScale);
        Double roundedQuotient = Precision.round(quotient, requiredScale);
        //Double roundedQuotient = Precision.round(quotient, precision);
        logger.atFine().log("Rounded quotient is " + roundedQuotient);
        return new DrawlNumber(roundedQuotient);
    }

    /**
     * Divides this DrawlNumber by a Double
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    protected DrawlNumber divide(double x, int precision)
    {
        return divide(new DrawlNumber(String.valueOf(x)), precision);
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
    protected boolean equals(DrawlNumber x)
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
        return equals(new DrawlNumber(String.valueOf(x)));
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
    protected boolean isEqualTo(DrawlNumber x)
    {
        return number.compareTo(x.number) == 0;
    }

    /**
     * Returns whether this number is greater than the other number.
     *
     * @param x tested number
     * @return true if this number is greater than the other one
     */
    protected boolean isGreaterThan(DrawlNumber x)
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
        return isGreaterThan(new DrawlNumber(String.valueOf(x)));
    }

    /**
     * Returns whether this number is greater or equal to the other number.
     *
     * @param x tested number
     * @return true if this number is greater or equal to the other one
     */
    protected boolean isGreaterThanOrEqualTo(DrawlNumber x)
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
        return isGreaterThanOrEqualTo(DrawlNumber.valueOf(String.valueOf(x)));
    }

    /**
     * Determines whether this DrawlNumber is an integer
     */
    protected Boolean isIntegerValue()
    {
        return this.number.intValue() == this.number;
    }

    /**
     * Returns whether this number is less than the other number.
     *
     * @param x tested number
     * @return true if this number is less than the other one
     */
    protected boolean isLessThan(DrawlNumber x)
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
        return isLessThan(DrawlNumber.valueOf(String.valueOf(x)));
    }

    /**
     * Returns whether this number is less or equal to the other number.
     *
     * @param x tested number
     * @return true if this number is less than the other one
     */
    protected boolean isLessThanOrEqualTo(DrawlNumber x)
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
        return isLessThanOrEqualTo(DrawlNumber.valueOf(String.valueOf(x)));
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
    protected Boolean isNotEqualTo(DrawlNumber val)
    {
        return !this.equals(val);
    }

    /**
     * Performs multiplication operation.
     *
     * @param x other number
     * @return addition multiplication result
     */
    protected DrawlNumber multiply(DrawlNumber x)
    {
        return new DrawlNumber(this.number * x.number);
    }

    /**
     * Returns a BigDecimal whose value is (this × multiplicand), with rounding according to the context settings.
     *
     * @param multiplicand value to be multiplied by this BigDecimal.
     * @param mc           Ignored. Preserved for interface compatibility with SisuBigDecimal.
     * @return this * multiplicand, rounded as necessary.
     * @throws ArithmeticException if the result is inexact but the rounding mode is UNNECESSARY.
     */
    protected DrawlNumber multiply(DrawlNumber multiplicand, MathContext mc)
    {
        return new DrawlNumber(this.number * multiplicand.number);
    }

    /**
     * Performs multiplication operation.
     *
     * @param x other number
     * @return addition multiplication result
     */
    protected DrawlNumber multiply(double x)
    {
        return multiply(new DrawlNumber(String.valueOf(x)));
    }

    /**
     * Negates this number.
     *
     * @return negative of this number
     */
    protected DrawlNumber negate()
    {
        return DrawlNumber.valueOf(-this.number);
    }

    /**
     * Returns this number powered by n.
     *
     * @param n         power
     * @param precision Ignored. Provided for compatibility with SisuBigDecimal interface.
     * @return power operation result
     */
    protected DrawlNumber pow(int n, int precision)
    {
        return DrawlNumber.valueOf(Math.pow(this.number, n));
    }

    /**
     * Create a new instance that has same value as this one rounded.
     *
     * @param mc Ignored. Provided for interface compatibility with SisuBigDecimal.
     * @return A new instance of SisuBigDecimal that has the same value as this one rounded.
     */
    protected DrawlNumber round(MathContext mc)
    {
        return DrawlNumber.valueOf(Math.round(this.number));
    }

    /**
     * Create a new instance that has the same value as this one rounded.
     *
     * @param places How many decimal places to preserve in the new instance
     * @return
     */
    protected DrawlNumber round(int places)
    {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(this.number));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return DrawlNumber.valueOf(bd.doubleValue());
    }

    /**
     * Performs subtraction operation.
     *
     * @param x other number
     * @return addition subtraction result
     */
    protected DrawlNumber subtract(DrawlNumber x)
    {
        return new DrawlNumber(number - x.number);
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
    protected DrawlNumber subtract(DrawlNumber subtrahend, MathContext mc)
    {
        return new DrawlNumber(this.number - subtrahend.number);
    }

    /**
     * Performs subtraction operation.
     *
     * @param x other number
     * @return addition subtraction result
     */
    protected DrawlNumber subtract(double x)
    {
        return subtract(new DrawlNumber(String.valueOf(x)));
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
        String formato = String.format("%." + numDecimals + "f", this.number);
        return formato;
    }

    /**
     * Converts this number to the full, containing everything that can be restored.
     *
     * @return number in it's string representation
     */
    protected String toFullString()
    {
        //Option2, use decimalFormat.
        DecimalFormat df = new DecimalFormat("0");
        df.setMaximumFractionDigits(32);
        return df.format(this.number);
    }

    /**
     * @returns a string representation of this SisuBigDecimal without an exponent field.
     */
    protected String toPlainString()
    {
        if (Boolean.TRUE.equals(this.isIntegerValue()))
        {
            return String.valueOf(this.number.intValue());
        }
        else
        {
            return this.number.toString();
        }
    }

    @Override
    public String toString()
    {
        return "DrawlNumber[" + this.toFullString() + "]";
    }

}
