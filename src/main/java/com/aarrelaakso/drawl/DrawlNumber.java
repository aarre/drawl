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
import java.util.regex.Pattern;

/**
 * Class for mathematical operations with Double values that have a similar API to that of the BigDecimal class.
 * <p>
 * Makes it easier to swap out a Double implementation for a BigDecimal implementation (SisuBigDecimal) as needs
 * dictate.
 */
public class DrawlNumber implements Number {

    protected static final DrawlNumber HALF = DrawlNumber.valueOf(0.5);
    protected static final DrawlNumber ONE = DrawlNumber.valueOf(BigDecimal.ONE);
    protected static final DrawlNumber TWO = DrawlNumber.valueOf(2);
    private static final @NotNull FluentLogger logger;
    private static final Pattern COMPILE = Pattern.compile("^0+(?!$)");
    protected static @NotNull RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    protected static int SCALE_FOR_COMPARISONS = 32;
    protected static int SCALE_FOR_OPERATIONS = 64;

    /**
     * Zero.
     */
    protected static @NotNull Number ZERO = new DrawlNumber("0");

    /**
     * Use this MathContext for comparisons.
     */
    protected static @NotNull MathContext mcComparisons = new MathContext(SCALE_FOR_COMPARISONS,
            ROUNDING_MODE);

    /**
     * Use this MathContext for other operations, such as multiplying and dividing.
     */
    protected static @NotNull MathContext mcOperations = new MathContext(SCALE_FOR_OPERATIONS,
            ROUNDING_MODE);

    static {
        logger = FluentLogger.forEnclosingClass();

    }

    /**
     * Precise number representation.
     */
    private Double number;

    /**
     * Prevents construction from outside.
     */
    private DrawlNumber() {
    }

    /**
     * Creates new instance.
     *
     * @param number number
     */
    protected DrawlNumber(final Double number) {
        this.number = number;
    }

    /**
     * Creates a new instance
     */
    protected DrawlNumber(@NotNull final Integer number) {
        this.number = number.doubleValue();
    }

    /**
     * Creates new instance.
     *
     * @param s string representation
     */
    private DrawlNumber(@NotNull final String s) {
        this.number = Double.valueOf(s);
    }

    /**
     * Creates a new instance from a String. This method is capable of parsing any result of the toFullString method.
     *
     * @param s source string
     * @return created number
     */
    static @NotNull DrawlNumber valueOf(@NotNull final String s) {
        return new DrawlNumber(s);
    }

    /**
     * Creates new instance from a double.
     *
     * @param number number
     * @return created number
     */
    static DrawlNumber valueOf(final double number) {
        return new DrawlNumber(number);
    }


    /**
     * Creates new instance from a BigDecimal
     *
     * @param number number
     * @return created number
     */
    static @NotNull DrawlNumber valueOf(@NotNull final BigDecimal number) {
        return new DrawlNumber(number.doubleValue());
    }

    /**
     * Creates a new instance from an Integer.
     *
     * @param number The value of the new DrawlMath instance.
     * @return The new DrawlMath instance.
     */
    static @NotNull DrawlNumber valueOf(@NotNull final Integer number) {
        return new DrawlNumber(number);
    }


    /**
     * Creates a new instance from an int.
     *
     * @param number The value of the new Number instance.
     * @return The new DrawlMath instance.
     */
    static @NotNull DrawlNumber valueOf(@NotNull final int number) {
        return new DrawlNumber(number);
    }

    /**
     * Test whether a BigDecimal is a mathematical integer.
     *
     * @param bd the BigDecimal value to test
     * @return True if bd is a mathematical integer,
     * false otherwise.
     */
    public boolean isIntegerValue(@NotNull final BigDecimal bd) {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }

    /**
     * Test whether a float is a mathematical integer.
     *
     * @param val
     * @return
     */
    public boolean isIntegerValue(final float val) {
        // See: https://stackoverflow.com/questions/4727569/how-to-check-whether-a-float-has-an-integer-value
        return ((int) val) == val;
    }

    /**
     * Returns absolute value of this number.
     *
     * @return absolute value of this number
     */
    public @NotNull Number abs() {
        return DrawlNumber.valueOf(Math.abs(this.number));
    }

    /**
     * Performs addition operation.
     *
     * @param augend other number
     * @return addition operation result
     */
    public @NotNull Number add(@NotNull final Number augend) {
        return new DrawlNumber(this.doubleValue() + augend.doubleValue());
    }

    /**
     * Performs addition operation.
     *
     * @param x other number
     * @return addition operation result
     */
    public @NotNull Number add(final double x) {
        return this.add(new DrawlNumber(String.valueOf(x)));
    }

    /**
     * Returns a DrawlNumber whose value is (this + augend), with rounding according to the context settings. If
     * either number is zero and the precision setting is nonzero, then the other number, rounded if necessary, is used
     * as the result.
     *
     * @param augend value to be added to this SisuBigDecimal.
     * @param mc     the MathContext to use.
     * @return this + augend, rounded as necessary
     * @author Aarre Laakso
     * @version 1.0, 05/07/2020
     * @since 1.0, 05/07/2020
     */
    public @NotNull Number add(@NotNull final Number augend, final MathContext mc) {
        return new DrawlNumber(this.doubleValue() + augend.doubleValue());
    }

    /**
     * Converts this SisuBigDecimal to a BigDecimal.
     *
     * @return this SisuBigDecimal converted to a BigDecimal.
     */
    public @NotNull BigDecimal bigDecimalValue() {
        return BigDecimal.valueOf(this.number);
    }

    /**
     * Compares this DrawlNumber with the specified Number. Two Number objects that are equal in value but have
     * a different scale (like 2.0 and 2.00) are considered equal by this method.
     * <p>
     * This method overrides the compareTo method in the java.lang.Comparable interface.
     *
     * @param comparator Number to which this DrawlNumber is to be compared.
     * @returns -1, 0, or 1 as this DrawlNumber is numerically less than, equal to, or greater than comparator.
     * @author Aarre Laakso
     * @version 1.0, 05/07/2020
     * @since 1.0, 05/07/2020
     */
    @Override
    public int compareTo(@NotNull final Number comparator) {
        return this.number.compareTo(comparator.doubleValue());
    }


    /**
     * Compare this DrawlNumber to another using the default MathContext.
     *
     * @param comparator The other DrawlNumber to compare to this one.
     * @return -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     * @author Aarre Laakso
     * @version 1.0, 05/07/2020
     * @since 1.0, 05/07/2020
     */
    public int compareToFuzzy(@NotNull final Number comparator) {
        return this.compareToFuzzy(comparator, mcComparisons);
    }

    /**
     * Compares this DrawlNumber to another Number fuzzily.
     *
     * @param comparator The other Number to compare to this one.
     * @param mc  A MathContext object. The precision field (total number of significant digits) is used as the scale
     *            (number of digits to the right of the decimal place) for this operation.
     * @return -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     * @author Aarre Laakso
     * @version 1.0, 05/07/2020
     * @since 1.0, 05/07/2020
     */
    public int compareToFuzzy(@NotNull final Number comparator, final MathContext mc) {
        return Precision.compareTo(this.doubleValue(), comparator.doubleValue(), 1);
    }

    /**
     * Performs division operation and returns the result with remainder.
     *
     * @param divisor   divisor
     * @param precision This parameter is ignored. It is preserved for interface compatibility with SisuBigDecimal.
     * @return division operation result
     */
    public @NotNull DrawlNumberRemainderPair divWithRemainder(@NotNull final Number divisor, final int precision) {
        @NotNull final Double div = this.number / divisor.doubleValue();
        @NotNull final Double rem = this.number - (div * divisor.doubleValue());
        return DrawlNumberRemainderPair.valueOf(DrawlNumber.valueOf(div), DrawlNumber.valueOf(rem));
    }

    /**
     * Performs division operation and returns the result with remainder.
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    public @NotNull DrawlNumberRemainderPair divWithRemainder(final double x, final int precision) {
        return this.divWithRemainder(new DrawlNumber(String.valueOf(x)), precision);
    }

    /**
     * Divides with DrawlNumber by another
     *
     * @param divisor     The divisor.
     * @param mathContext Ignored. Retained for compatibility with SisuNumber interface.
     * @return This divided by val.
     */
    public @NotNull Number divide(@NotNull final Number divisor, final MathContext mathContext) {
        return new DrawlNumber(this.doubleValue() / divisor.doubleValue());
    }

    /**
     * Divides this DrawlNumber by another Number.
     *
     * @param divisor   the divisor
     * @param precision the precision (number of significant digits) of the result.
     * @return this / divisor
     * @author Aarre Laakso
     * @version 1.0, 05/07/2020
     * @since 1.0, 05/07/2020
     */
    public final @NotNull Number divide(@NotNull final Number divisor, final int precision) {
        int actualPrecision = precision;
        final double quotient = this.number / divisor.doubleValue();
        final String quotientAsString = String.valueOf(quotient);
        // Remove leading zeros
        @NotNull final String noZerosQuotient = DrawlNumber.COMPILE.matcher(quotientAsString).replaceFirst("");
        if ((quotient > -1) && (quotient < 1)) {
            actualPrecision++;
        }
        final int digitsBeforeDecimal = (int) Math.log10(quotient) + 1;
        final int requiredScale = actualPrecision - digitsBeforeDecimal;
        final Double roundedQuotient = Precision.round(quotient, requiredScale);
        return new DrawlNumber(roundedQuotient);
    }

    /**
     * Divides this DrawlNumber by a Double
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    public @NotNull Number divide(final double x, final int precision) {
        return this.divide(new DrawlNumber(String.valueOf(x)), precision);
    }

    /**
     * Returns double representation of this number.
     * Conversion might ended up by loosing the precision.
     *
     * @return double representation of this value
     */
    public double doubleValue() {
        return this.number.doubleValue();
    }

    /**
     * Returns whether this number is equal with the other number.
     *
     * @param x tested number
     * @return true if this number is equal to the other number
     * @author Aarre Laakso
     * @version 1.0, 05/07/2020
     * @since 1.0, 05/07/2020
     */
    public boolean equals(@NotNull final Number x) {
        return this.number.compareTo(x.doubleValue()) == 0;
    }

    /**
     * Returns whether this number is equal with the other number.
     *
     * @param x tested number
     * @return true if this number is equal to the other number
     */
    public boolean equals(final double x) {
        return this.equals(new DrawlNumber(String.valueOf(x)));
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
    public boolean equals(final Object obj) {
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
    public @NotNull Float floatValue() {
        return Float.valueOf(this.number.floatValue());
    }

    /**
     * Returns the hash code for this SisuBigDecimal. Note that two SisuBigDecimal objects that are numerically equal
     * but differ in scale (like 2.0 and 2.00) will generally not have the same hash code.
     *
     * @return hash code for this BigDecimal.
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Converts this BigDecimal to an Integer. This conversion is analogous to the narrowing primitive conversion from
     * double to short as defined in section 5.1.3 of The Java™ Language Specification: any fractional part of this
     * BigDecimal will be discarded, and if the resulting "BigInteger" is too big to fit in an int, only the low-order
     * 32 bits are returned. Note that this conversion can lose information about the overall magnitude and precision
     * of this BigDecimal value as well as return a result with the opposite sign.
     *
     * @return this BigDecimal converted to an Integer.
     */
    public Integer intValue() {
        return this.number.intValue();
    }

    /**
     * Tests whether this DrawlNumber is equal to another Number.
     *
     * @param val tested number
     * @return <code>TRUE</code>> if this number is equal to val, <code>FALSE</code> otherwise
     * @author Aarre Laakso
     * @version 2.0, 05/07/2020
     * @since 1.0 04/28/2020
     */
    public boolean isEqualTo(@NotNull final Number val) {
        return this.number.compareTo(val.doubleValue()) == 0;
    }

    /**
     * Returns whether this DrawlNumber is greater than another Number.
     *
     * @param val tested number
     * @return <code>TRUE</code> if this number is greater than the other one, <code>FALSE</code> otherwise
     * @author Aarre Laakso
     * @version 1.0, 05/07/2020
     * @since 1.0, 05/07/2020
     */
    public boolean isGreaterThan(@NotNull final Number val) {
        return this.number.compareTo(val.doubleValue()) == 1;
    }

    /**
     * Tests whether this DrawlNumber is greater than another Number.
     *
     * @param val tested number
     * @return <code>TRUE</code> if this number is greater than the other one, <code>FALSE</code> otherwise
     * @author Aarre Laakso
     * @version 1.0, 05/07/2020
     * @since 1.0, 05/07/2020
     */
    public boolean isGreaterThan(final double val) {
        return this.isGreaterThan(new DrawlNumber(String.valueOf(val)));
    }

    /**
     * Tests whether this DrawlNumber is greater or equal to another Number.
     *
     * @param val tested number
     * @return <code>TRUE</code> if this number is greater or equal to the other one, <code>FALSE</code> otherwise
     * @author Aarre Laakso
     * @version 1.0, 05/07/2020
     * @since 1.0, 05/07/2020
     */
    public boolean isGreaterThanOrEqualTo(@NotNull final Number val) {
        return this.number.compareTo(val.doubleValue()) >= 0;
    }

    /**
     * Returns whether this number is greater or equal to the other number.
     *
     * @param val tested number
     * @return true if this number is greater or equal to the other one
     */
    public boolean isGreaterThanOrEqualTo(final double val) {
        return this.isGreaterThanOrEqualTo(DrawlNumber.valueOf(String.valueOf(val)));
    }

    /**
     * Determines whether this DrawlNumber is an integer
     *
     * @return <code>TRUE</code> if this DrawlNumber is an integer, <code>FALSE</code> otherwise.
     */
    @NotNull
    public boolean isIntegerValue() {
        return (double) this.number.intValue() == this.number;
    }

    /**
     * Returns whether this DrawlNumber is less than another Number.
     *
     * @param val the number to compare with this DrawlNumber.
     * @return <code>TRUE</code> if this number is less than the other one, <code>FALSE</code> otherwise.
     * @author Aarre Laakso
     * @version 1.0, 05/07/2020
     * @since 1.0, 05/07/2020
     */
    public boolean isLessThan(@NotNull final Number val) {
        return this.number.compareTo(val.doubleValue()) < 0;
    }

    /**
     * Returns whether this number is less than the other number.
     *
     * @param val tested number
     * @return true if this number is less than the other one
     */
    public boolean isLessThan(final double val) {
        return this.isLessThan(DrawlNumber.valueOf(String.valueOf(val)));
    }

    /**
     * Returns whether this number is less or equal to the other number.
     *
     * @param val tested number
     * @return true if this number is less than the other one
     * @author Aarre Laakso
     * @version 1.0, 05/07/2020
     * @since 1.0, 05/07/2020
     */
    public boolean isLessThanOrEqualTo(@NotNull final Number val) {
        return this.number.compareTo(val.doubleValue()) <= 0;
    }

    /**
     * Returns whether this number is less or equal to the other number.
     *
     * @param val tested number
     * @return true if this number is less than the other one
     */
    public boolean isLessThanOrEqualTo(final double val) {
        return this.isLessThanOrEqualTo(DrawlNumber.valueOf(String.valueOf(val)));
    }

    /**
     * Indicates whether another value is not equal to this SisuBigDecimal.
     *
     * @param val
     * @return True if this SisuBigDecimal are not equal, False if they are.
     * @author Aarre Laakso
     * @version 2.0, 05/27/2020
     * @since 1.0, 04/28/2020
     */
    public @NotNull Boolean isNotEqualTo(@NotNull final Number val) {
        return !this.equals(val);
    }

    /**
     * Performs multiplication operation.
     *
     * @param multiplicand other number
     * @return multiplication result
     */
    public @NotNull Number multiply(@NotNull final Number multiplicand) {
        return new DrawlNumber(this.doubleValue() * multiplicand.doubleValue());
    }

    /**
     * Returns a BigDecimal whose value is (this × multiplicand), with rounding according to the context settings.
     *
     * @param multiplicand value to be multiplied by this BigDecimal.
     * @param mc           Ignored. Preserved for interface compatibility with SisuBigDecimal.
     * @return this * multiplicand, rounded as necessary.
     * @throws ArithmeticException if the result is inexact but the rounding mode is UNNECESSARY.
     */
    public @NotNull Number multiply(@NotNull final Number multiplicand, final MathContext mc) {
        return new DrawlNumber(this.doubleValue() * multiplicand.doubleValue());
    }

    /**
     * Performs multiplication operation.
     *
     * @param multiplicand other number
     * @return addition multiplication result
     */
    public @NotNull Number multiply(final double multiplicand) {
        return this.multiply(new DrawlNumber(String.valueOf(multiplicand)));
    }

    /**
     * Negates this number.
     *
     * @return negative of this number
     */
    public @NotNull Number negate() {
        return DrawlNumber.valueOf(-this.number);
    }

    /**
     * Returns this number powered by n.
     *
     * @param n         power
     * @param precision Ignored. Provided for compatibility with SisuBigDecimal interface.
     * @return power operation result
     */
    public @NotNull Number pow(final int n, final int precision) {
        return DrawlNumber.valueOf(Math.pow(this.number, n));
    }

    /**
     * Create a new instance that has same value as this one rounded.
     *
     * @param mc Ignored. Provided for interface compatibility with SisuBigDecimal.
     * @return A new instance of SisuBigDecimal that has the same value as this one rounded.
     */
    public @NotNull Number round(final MathContext mc) {
        return DrawlNumber.valueOf(Math.round(this.number));
    }

    /**
     * Create a new instance that has the same value as this one rounded.
     *
     * @param places How many decimal places to preserve in the new instance
     * @return
     */
    public @NotNull Number round(final int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(this.number));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return DrawlNumber.valueOf(bd.doubleValue());
    }

    /**
     * Performs subtraction operation.
     *
     * @param subtrahend value to be subtracted from this DrawlNumber.
     * @return addition subtraction result
     */
    public @NotNull Number subtract(@NotNull final Number subtrahend) {
        return new DrawlNumber(this.doubleValue() - subtrahend.doubleValue());
    }

    /**
     * Returns a DrawlNumber whose value is (this - subtrahend), with rounding according to the context settings. If
     * subtrahend is zero, then this, rounded if necessary, is used as the result. If this is zero then the result is
     * subtrahend.negate(mc).
     *
     * @param subtrahend value to be subtracted from this DrawlNumber.
     * @param mc         Ignored. Preserved for compatibility with the Number interface.
     * @return this - subtrahend, rounded as necessary
     * @throws ArithmeticException if the result is inexact but the rounding mode is UNNECESSARY.
     */
    public final @NotNull Number subtract(@NotNull final Number subtrahend, final MathContext mc) {
        return new DrawlNumber(this.number - subtrahend.doubleValue());
    }

    /**
     * Performs subtraction operation.
     *
     * @param subtrahend value to be subtracted from this DrawlNumber.
     * @return addition subtraction result
     */
    public @NotNull Number subtract(final double subtrahend) {
        return this.subtract(new DrawlNumber(String.valueOf(subtrahend)));
    }

    /**
     * Converts number to the string with fixed decimal digits.
     * <p>
     * This method might truncate some numbers or add extra zeros at the end.
     *
     * @param numDecimals number of decimals, must be non negative
     * @return number as a plain string with specified number of decimals
     */
    public String toFixedDecimalString(final int numDecimals) {
        assert (numDecimals >= 0) : "numDecimals must be >= 0";
        final String formato = String.format("%." + numDecimals + "f", this.number);
        return formato;
    }

    /**
     * Converts this number to the full, containing everything that can be restored.
     *
     * @return number in it's string representation
     */
    public @NotNull String toFullString() {
        //Option2, use decimalFormat.
        @NotNull final DecimalFormat df = new DecimalFormat("0");
        df.setMaximumFractionDigits(32);
        return df.format(this.number);
    }

    /**
     * @returns a string representation of this SisuBigDecimal without an exponent field.
     */
    public String toPlainString() {
        if (Boolean.TRUE.equals(this.isIntegerValue())) {
            return String.valueOf(this.number.intValue());
        } else {
            return this.number.toString();
        }
    }

    public @NotNull String toString() {
        return "DrawlNumber[" + this.toFullString() + "]";
    }


    /**
     * Convert this DrawlNumber to a String for SVG.
     * <p>
     * The number will be represented with as many decimal points as necessary for SVG and no more than SVG can
     * handle.
     */
    public @NotNull String toSVG() {
        String result = "";
        if (this.isIntegerValue()) {
            result = String.valueOf(this.intValue());
        } else {
            result = String.valueOf(this.floatValue());
        }
        return result;
    }
}
