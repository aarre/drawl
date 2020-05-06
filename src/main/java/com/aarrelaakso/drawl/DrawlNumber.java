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
public class DrawlNumber implements Comparable<DrawlNumber> {

    protected static final DrawlNumber HALF = valueOf(0.5);
    protected static final DrawlNumber ONE = valueOf(BigDecimal.ONE);
    protected static final DrawlNumber TWO = valueOf(2);
    private static final @NotNull FluentLogger logger;
    private static final Pattern COMPILE = Pattern.compile("^0+(?!$)");
    protected static @NotNull RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    protected static int SCALE_FOR_COMPARISONS = 32;
    protected static int SCALE_FOR_OPERATIONS = 64;
    /**
     * Zero.
     */
    protected static @NotNull DrawlNumber ZERO = new DrawlNumber("0");
    /**
     * Use this MathContext for comparisons.
     */
    protected static @NotNull MathContext mcComparisons = new MathContext(DrawlNumber.SCALE_FOR_COMPARISONS, DrawlNumber.ROUNDING_MODE);
    /**
     * Use this MathContext for other operations, such as multiplying and dividing.
     */
    protected static @NotNull MathContext mcOperations = new MathContext(DrawlNumber.SCALE_FOR_OPERATIONS, DrawlNumber.ROUNDING_MODE);

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
    @Contract(pure = true)
    protected DrawlNumber(@NotNull final Integer number) {
        this.number = number.doubleValue();
    }

    /**
     * Creates new instance.
     *
     * @param s string representation
     */
    private DrawlNumber(@NotNull final String s) {
        number = Double.valueOf(s);
    }

    /**
     * Creates new instance. This method is capable to parse any result of toFullString method.
     *
     * @param s source string
     * @return created number
     */
    @Contract("_ -> new")
    protected static @NotNull DrawlNumber valueOf(@NotNull final String s) {
        return new DrawlNumber(s);
    }

    /**
     * Creates new instance.
     *
     * @param number number
     * @return created number
     */
    @Contract(value = "_ -> new", pure = true)
    protected static @NotNull DrawlNumber valueOf(final double number) {
        return new DrawlNumber(number);
    }

    /**
     * Creates new instance.
     *
     * @param number number
     * @return created number
     */
    @Contract("_ -> new")
    protected static @NotNull DrawlNumber valueOf(@NotNull final BigDecimal number) {
        return new DrawlNumber(number.doubleValue());
    }

    /**
     * Creates a new instance.
     *
     * @param number The value of the new DrawlMath instance.
     * @return The new DrawlMath instance.
     */
    @Contract("_ -> new")
    protected static @NotNull DrawlNumber valueOf(@NotNull final Integer number) {
        return new DrawlNumber(number);
    }

    /**
     * Test whether a BigDecimal is a mathematical integer.
     *
     * @param bd the BigDecimal value to test
     * @return True if bd is a mathematical integer,
     * false otherwise.
     */
    protected static boolean isIntegerValue(@NotNull final BigDecimal bd) {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }

    /**
     * Test whether a float is a mathematical integer.
     *
     * @param val
     * @return
     */
    protected static boolean isIntegerValue(final float val) {
        // See: https://stackoverflow.com/questions/4727569/how-to-check-whether-a-float-has-an-integer-value
        return ((int) val) == val;
    }

    /**
     * Returns absolute value of this number.
     *
     * @return absolute value of this number
     */
    protected @NotNull DrawlNumber abs() {
        return valueOf(Math.abs(this.number));
    }

    /**
     * Performs addition operation.
     *
     * @param x other number
     * @return addition operation result
     */
    protected @NotNull DrawlNumber add(@NotNull final DrawlNumber x) {
        return new DrawlNumber(this.number + x.number);
    }

    /**
     * Performs addition operation.
     *
     * @param x other number
     * @return addition operation result
     */
    protected @NotNull DrawlNumber add(final double x) {
        return this.add(new DrawlNumber(String.valueOf(x)));
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
    protected @NotNull DrawlNumber add(@NotNull final DrawlNumber augend, final MathContext mc) {
        return new DrawlNumber(number + augend.number);
    }

    /**
     * Converts this SisuBigDecimal to a BigDecimal.
     *
     * @return this SisuBigDecimal converted to a BigDecimal.
     */
    protected @NotNull BigDecimal bigDecimalValue() {
        return BigDecimal.valueOf(number);
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
    public int compareTo(@NotNull final DrawlNumber val) {
        return this.number.compareTo(val.number);
    }

    /**
     * Compare this DrawlNumber to another using the default MathContext.
     *
     * @param val The other DrawlNumber to compare to this one.
     * @return -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     */
    protected int compareToFuzzy(@NotNull final DrawlNumber val) {
        return compareToFuzzy(val, DrawlNumber.mcComparisons);
    }

    /**
     * Compare this DrawlNumber to another fuzzily.
     *
     * @param val The other DrawlNumber to compare to this one.
     * @param mc  A MathContext object. The precision field (total number of significant digits) is used as the scale
     *            (number of digits to the right of the decimal place) for this operation.
     * @return -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     */
    protected int compareToFuzzy(@NotNull final DrawlNumber val, final MathContext mc) {
        //Integer precision = mc.getPrecision();
        //DrawlNumber thisNumberRounded = DrawlNumber.valueOf(Precision.round(this.number, precision));
        //DrawlNumber xNumberRounded = DrawlNumber.valueOf(Precision.round(val.doubleValue(), precision));
        //return thisNumberRounded.compareTo(xNumberRounded);

        // Use floating point comparison. The second parameter to compareTo is the units in the last place,
        // i.e., the number of floating point digits by which the numbers may differ.
        return Precision.compareTo(number, val.number, 1);
    }

    /**
     * Performs division operation and returns the result with remainder.
     *
     * @param x         divisor number
     * @param precision This parameter is ignored. It is preserved for interface compatibility with SisuBigDecimal.
     * @return division operation result
     */
    protected @NotNull DrawlNumberRemainderPair divWithRemainder(@NotNull final DrawlNumber x, final int precision) {
        @NotNull final Double div = number / x.number;
        @NotNull final Double rem = number - (div * x.number);
        return DrawlNumberRemainderPair.valueOf(valueOf(div), valueOf(rem));
    }

    /**
     * Performs division operation and returns the result with remainder.
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    protected @NotNull DrawlNumberRemainderPair divWithRemainder(final double x, final int precision) {
        return this.divWithRemainder(new DrawlNumber(String.valueOf(x)), precision);
    }

    /**
     * Divides with DrawlNumber by another
     *
     * @param divisor     The divisor.
     * @param mathContext Ignored. Retained for compatibility with SisuNumber interface.
     * @return This divided by val.
     */
    protected @NotNull DrawlNumber divide(@NotNull final DrawlNumber divisor, final MathContext mathContext) {
        return new DrawlNumber(number / divisor.number);
    }

    /**
     * Divides this DrawlNumber by another
     *
     * @param divisor         divisor number
     * @param precision The precision (number of significant digits) of the result.
     * @return division operation result
     */
    protected final @NotNull DrawlNumber divide(@NotNull final DrawlNumber divisor, final int precision) {
        int actualPrecision = precision;
        final double quotient = this.number / divisor.doubleValue();
        DrawlNumber.logger.atFine().log("Quotient is " + quotient);
        final String quotientAsString = String.valueOf(quotient);
        // Remove leading zeros
        @NotNull final String noZerosQuotient = COMPILE.matcher(quotientAsString).replaceFirst("");
        DrawlNumber.logger.atFine().log("noZerosQuotient is " + noZerosQuotient);
        if ((quotient > -1) && (quotient < 1)) {
            actualPrecision++;
        }
        int digitsBeforeDecimal = (int) Math.log10(quotient) + 1;
        DrawlNumber.logger.atFine().log("Digits before decimal is " + digitsBeforeDecimal);
        int requiredScale = actualPrecision - digitsBeforeDecimal;
        DrawlNumber.logger.atFine().log("Required scale (digits after decimal) is " + requiredScale);
        Double roundedQuotient = Precision.round(quotient, requiredScale);
        DrawlNumber.logger.atFine().log("Rounded quotient is " + roundedQuotient);
        return new DrawlNumber(roundedQuotient);
    }

    /**
     * Divides this DrawlNumber by a Double
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    protected @NotNull DrawlNumber divide(final double x, final int precision) {
        return this.divide(new DrawlNumber(String.valueOf(x)), precision);
    }

    /**
     * Returns double representation of this number.
     * Conversion might ended up by loosing the precision.
     *
     * @return double representation of this value
     */
    protected double doubleValue() {
        return this.number.doubleValue();
    }

    /**
     * Returns whether this number is equal with the other number.
     *
     * @param x tested number
     * @return true if this number is equal to the other number
     */
    protected boolean equals(@NotNull final DrawlNumber x) {
        return this.number.compareTo(x.number) == 0;
    }

    /**
     * Returns whether this number is equal with the other number.
     *
     * @param x tested number
     * @return true if this number is equal to the other number
     */
    protected boolean equals(final double x) {
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
    protected @NotNull Float floatValue() {
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
    protected Integer intValue() {
        return number.intValue();
    }

    /**
     * Returns whether this number is equal with another number.
     *
     * @param val tested number
     * @return true if this number is equal to the other number
     * @author Aarre Laakso
     * @version 1.0, 04/28/2020
     * @since 04/28/2020
     */
    protected boolean isEqualTo(@NotNull final DrawlNumber val) {
        return this.number.compareTo(val.number) == 0;
    }

    /**
     * Returns whether this number is greater than the other number.
     *
     * @param val tested number
     * @return true if this number is greater than the other one
     */
    protected boolean isGreaterThan(@NotNull final DrawlNumber val) {
        return this.number.compareTo(val.number) == 1;
    }

    /**
     * Returns whether this number is greater than the other number.
     *
     * @param val tested number
     * @return true if this number is greater than the other one
     */
    protected boolean isGreaterThan(final double val) {
        return this.isGreaterThan(new DrawlNumber(String.valueOf(val)));
    }

    /**
     * Returns whether this number is greater or equal to the other number.
     *
     * @param val tested number
     * @return true if this number is greater or equal to the other one
     */
    protected boolean isGreaterThanOrEqualTo(@NotNull final DrawlNumber val) {
        return this.number.compareTo(val.number) >= 0;
    }

    /**
     * Returns whether this number is greater or equal to the other number.
     *
     * @param val tested number
     * @return true if this number is greater or equal to the other one
     */
    protected boolean isGreaterThanOrEqualTo(final double val) {
        return this.isGreaterThanOrEqualTo(valueOf(String.valueOf(val)));
    }

    /**
     * Determines whether this DrawlNumber is an integer
     *
     * @return <code>TRUE</code> if this DrawlNumber is an integer, <code>FALSE</code> otherwise.
     */
    @NotNull
    private boolean isIntegerValue() {
        return (double)number.intValue() == number;
    }

    /**
     * Returns whether this number is less than the other number.
     *
     * @param val tested number
     * @return true if this number is less than the other one
     */
    protected boolean isLessThan(@NotNull final DrawlNumber val) {
        return this.number.compareTo(val.number) < 0;
    }

    /**
     * Returns whether this number is less than the other number.
     *
     * @param val tested number
     * @return true if this number is less than the other one
     */
    protected boolean isLessThan(final double val) {
        return this.isLessThan(valueOf(String.valueOf(val)));
    }

    /**
     * Returns whether this number is less or equal to the other number.
     *
     * @param val tested number
     * @return true if this number is less than the other one
     */
    protected boolean isLessThanOrEqualTo(@NotNull final DrawlNumber val) {
        return this.number.compareTo(val.number) <= 0;
    }

    /**
     * Returns whether this number is less or equal to the other number.
     *
     * @param val tested number
     * @return true if this number is less than the other one
     */
    protected boolean isLessThanOrEqualTo(final double val) {
        return this.isLessThanOrEqualTo(valueOf(String.valueOf(val)));
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
    protected @NotNull Boolean isNotEqualTo(@NotNull final DrawlNumber val) {
        return !equals(val);
    }

    /**
     * Performs multiplication operation.
     *
     * @param multiplicand other number
     * @return multiplication result
     */
    protected @NotNull DrawlNumber multiply(@NotNull final DrawlNumber multiplicand) {
        return new DrawlNumber(number * multiplicand.number);
    }

    /**
     * Returns a BigDecimal whose value is (this × multiplicand), with rounding according to the context settings.
     *
     * @param multiplicand value to be multiplied by this BigDecimal.
     * @param mc           Ignored. Preserved for interface compatibility with SisuBigDecimal.
     * @return this * multiplicand, rounded as necessary.
     * @throws ArithmeticException if the result is inexact but the rounding mode is UNNECESSARY.
     */
    protected @NotNull DrawlNumber multiply(@NotNull final DrawlNumber multiplicand, final MathContext mc) {
        return new DrawlNumber(number * multiplicand.number);
    }

    /**
     * Performs multiplication operation.
     *
     * @param multiplicand other number
     * @return addition multiplication result
     */
    protected @NotNull DrawlNumber multiply(final double multiplicand) {
        return this.multiply(new DrawlNumber(String.valueOf(multiplicand)));
    }

    /**
     * Negates this number.
     *
     * @return negative of this number
     */
    protected @NotNull DrawlNumber negate() {
        return valueOf(-number);
    }

    /**
     * Returns this number powered by n.
     *
     * @param n         power
     * @param precision Ignored. Provided for compatibility with SisuBigDecimal interface.
     * @return power operation result
     */
    protected @NotNull DrawlNumber pow(final int n, final int precision) {
        return valueOf(Math.pow(number, n));
    }

    /**
     * Create a new instance that has same value as this one rounded.
     *
     * @param mc Ignored. Provided for interface compatibility with SisuBigDecimal.
     * @return A new instance of SisuBigDecimal that has the same value as this one rounded.
     */
    protected @NotNull DrawlNumber round(final MathContext mc) {
        return valueOf(Math.round(number));
    }

    /**
     * Create a new instance that has the same value as this one rounded.
     *
     * @param places How many decimal places to preserve in the new instance
     * @return
     */
    protected @NotNull DrawlNumber round(final int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(number));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return valueOf(bd.doubleValue());
    }

    /**
     * Performs subtraction operation.
     *
     * @param subtrahend value to be subtracted from this DrawlNumber.
     * @return addition subtraction result
     */
    protected @NotNull DrawlNumber subtract(@NotNull final DrawlNumber subtrahend) {
        return new DrawlNumber(this.number - subtrahend.number);
    }

    /**
     * Returns a BigDecimal whose value is (this - subtrahend), with rounding according to the context settings. If
     * subtrahend is zero, then this, rounded if necessary, is used as the result. If this is zero then the result is
     * subtrahend.negate(mc).
     *
     * @param subtrahend value to be subtracted from this DrawlNumber.
     * @param mc         Ignored. Preserved for compatibility with the SisuBigDecimal interface.
     * @return this - subtrahend, rounded as necessary
     * @throws ArithmeticException if the result is inexact but the rounding mode is UNNECESSARY.
     */
    protected @NotNull DrawlNumber subtract(@NotNull final DrawlNumber subtrahend, final MathContext mc) {
        return new DrawlNumber(number - subtrahend.number);
    }

    /**
     * Performs subtraction operation.
     *
     * @param subtrahend value to be subtracted from this DrawlNumber.
     * @return addition subtraction result
     */
    protected @NotNull DrawlNumber subtract(final double subtrahend) {
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
    protected String toFixedDecimalString(final int numDecimals) {
        assert (numDecimals >= 0) : "numDecimals must be >= 0";
        final String formato = String.format("%." + numDecimals + "f", number);
        return formato;
    }

    /**
     * Converts this number to the full, containing everything that can be restored.
     *
     * @return number in it's string representation
     */
    protected @NotNull String toFullString() {
        //Option2, use decimalFormat.
        @NotNull final DecimalFormat df = new DecimalFormat("0");
        df.setMaximumFractionDigits(32);
        return df.format(number);
    }

    /**
     * @returns a string representation of this SisuBigDecimal without an exponent field.
     */
    protected String toPlainString() {
        if (Boolean.TRUE.equals(isIntegerValue())) {
            return String.valueOf(number.intValue());
        } else {
            return number.toString();
        }
    }

    @Override
    public @NotNull String toString() {
        return "DrawlNumber[" + toFullString() + "]";
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
