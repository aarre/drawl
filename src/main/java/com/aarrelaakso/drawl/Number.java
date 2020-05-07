package com.aarrelaakso.drawl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.regex.Pattern;

/**
 * Class for mathematical operations with Double values that have a similar API to that of the BigDecimal class.
 * <p>
 * Makes it easier to swap out a Double implementation for a BigDecimal implementation (SisuBigDecimal) as needs
 * dictate.
 */
interface Number extends Comparable<Number> {

    //Number HALF = Number.valueOf(0.5);
    //Number ONE = Number.valueOf(BigDecimal.ONE);
    //Number TWO = Number.valueOf(2);
    //Number ZERO = Number.valueOf(BigDecimal.ZERO);
    Pattern COMPILE = Pattern.compile("^0+(?!$)");
    @NotNull RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    int SCALE_FOR_COMPARISONS = 32;
    int SCALE_FOR_OPERATIONS = 64;

    /**
     * Use this MathContext for comparisons.
     */
    @NotNull MathContext mcComparisons = new MathContext(Number.SCALE_FOR_COMPARISONS, Number.ROUNDING_MODE);

    /**
     * Use this MathContext for other operations, such as multiplying and dividing.
     */
    @NotNull MathContext mcOperations = new MathContext(Number.SCALE_FOR_OPERATIONS, Number.ROUNDING_MODE);


    /**
     * Test whether a BigDecimal is a mathematical integer.
     *
     * @param bd the BigDecimal value to test
     * @return True if bd is a mathematical integer,
     * false otherwise.
     */
    boolean isIntegerValue(@NotNull final BigDecimal bd);

    /**
     * Test whether a float is a mathematical integer.
     *
     * @param val
     * @return
     */
    boolean isIntegerValue(final float val);

    /**
     * Returns absolute value of this number.
     *
     * @return absolute value of this number
     */
    @NotNull Number abs();

    /**
     * Performs addition operation.
     *
     * @param x other number
     * @return addition operation result
     */
    @NotNull Number add(@NotNull final Number x);

    /**
     * Performs addition operation.
     *
     * @param x other number
     * @return addition operation result
     */
    @NotNull Number add(final double x);

    /**
     * Returns a SisuBigDecimal whose value is (this + augend), with rounding according to the context settings. If
     * either number is zero and the precision setting is nonzero, then the other number, rounded if necessary, is used
     * as the result.
     *
     * @param augend value to be added to this SisuBigDecimal.
     * @param mc     the MathContext to use.
     * @return this + augend, rounded as necessary
     */
    @NotNull Number add(@NotNull final Number augend, final MathContext mc);

    /**
     * Converts this SisuBigDecimal to a BigDecimal.
     *
     * @return this SisuBigDecimal converted to a BigDecimal.
     */
    @NotNull BigDecimal bigDecimalValue();

    /**
     * Compares this BigDecimal with the specified BigDecimal. Two BigDecimal objects that are equal in value but have
     * a different scale (like 2.0 and 2.00) are considered equal by this method.
     * <p>
     * This method overrides the compareTo method in the java.lang.Comparable interface.
     *
     * @param val BigDecimal to which this BigDecimal is to be compared.
     * @returns -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     */
    int compareTo(@NotNull final Number val);

    /**
     * Compare this DrawlNumber to another using the default MathContext.
     *
     * @param val The other DrawlNumber to compare to this one.
     * @return -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     */
    int compareToFuzzy(@NotNull final Number val);

    /**
     * Compare this DrawlNumber to another fuzzily.
     *
     * @param val The other DrawlNumber to compare to this one.
     * @param mc  A MathContext object. The precision field (total number of significant digits) is used as the scale
     *            (number of digits to the right of the decimal place) for this operation.
     * @return -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
     */
    int compareToFuzzy(@NotNull final Number val, final MathContext mc);

    /**
     * Performs division operation and returns the result with remainder.
     *
     * @param x         divisor number
     * @param precision This parameter is ignored. It is preserved for interface compatibility with SisuBigDecimal.
     * @return division operation result
     */
    @NotNull DrawlNumberRemainderPair divWithRemainder(@NotNull final Number x, final int precision);

    /**
     * Performs division operation and returns the result with remainder.
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    @NotNull DrawlNumberRemainderPair divWithRemainder(final double x, final int precision);

    /**
     * Divides with DrawlNumber by another
     *
     * @param divisor     The divisor.
     * @param mathContext Ignored. Retained for compatibility with SisuNumber interface.
     * @return This divided by val.
     */
    @NotNull Number divide(@NotNull final Number divisor, final MathContext mathContext);

    /**
     * Divides this DrawlNumber by another
     *
     * @param divisor   divisor number
     * @param precision The precision (number of significant digits) of the result.
     * @return division operation result
     */
    @NotNull Number divide(@NotNull final Number divisor, final int precision);

    /**
     * Divides this DrawlNumber by a Double
     *
     * @param x         divisor number
     * @param precision precision of the result (see the class level comment for details)
     * @return division operation result
     */
    @NotNull Number divide(final double x, final int precision);

    /**
     * Returns double representation of this number.
     * Conversion might ended up by loosing the precision.
     *
     * @return double representation of this value
     */
    double doubleValue();

    /**
     * Returns whether this number is equal with the other number.
     *
     * @param x tested number
     * @return true if this number is equal to the other number
     */
    boolean equals(@NotNull final Number x);

    /**
     * Returns whether this number is equal with the other number.
     *
     * @param x tested number
     * @return true if this number is equal to the other number
     */
    boolean equals(final double x);

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
    boolean equals(final Object obj);

    /**
     * Converts this BigDecimal to a Float. This conversion is similar to the narrowing primitive conversion from
     * double to float as defined in section 5.1.3 of The Java™ Language Specification: if this BigDecimal has too great
     * a magnitude to represent as a Float, it will be converted to Float.NEGATIVE_INFINITY or Float.POSITIVE_INFINITY
     * as appropriate. Note that even when the return value is finite, this conversion can lose information about the
     * precision of the BigDecimal value.
     *
     * @return this SisuBigDecimal converted to a Float.
     */
    @NotNull Float floatValue();

    /**
     * Returns the hash code for this SisuBigDecimal. Note that two SisuBigDecimal objects that are numerically equal
     * but differ in scale (like 2.0 and 2.00) will generally not have the same hash code.
     *
     * @return hash code for this BigDecimal.
     */
    @Override
    int hashCode();

    /**
     * Converts this BigDecimal to an Integer. This conversion is analogous to the narrowing primitive conversion from
     * double to short as defined in section 5.1.3 of The Java™ Language Specification: any fractional part of this
     * BigDecimal will be discarded, and if the resulting "BigInteger" is too big to fit in an int, only the low-order
     * 32 bits are returned. Note that this conversion can lose information about the overall magnitude and precision
     * of this BigDecimal value as well as return a result with the opposite sign.
     *
     * @return this BigDecimal converted to an Integer.
     */
    Integer intValue();

    /**
     * Returns whether this number is equal with another number.
     *
     * @param val tested number
     * @return true if this number is equal to the other number
     * @author Aarre Laakso
     * @version 1.0, 04/28/2020
     * @since 04/28/2020
     */
    boolean isEqualTo(@NotNull final Number val);

    /**
     * Returns whether this number is greater than the other number.
     *
     * @param val tested number
     * @return true if this number is greater than the other one
     */
    boolean isGreaterThan(@NotNull final Number val);

    /**
     * Returns whether this number is greater than the other number.
     *
     * @param val tested number
     * @return true if this number is greater than the other one
     */
    boolean isGreaterThan(final double val);

    /**
     * Returns whether this number is greater or equal to the other number.
     *
     * @param val tested number
     * @return true if this number is greater or equal to the other one
     */
    boolean isGreaterThanOrEqualTo(@NotNull final Number val);

    /**
     * Returns whether this number is greater or equal to the other number.
     *
     * @param val tested number
     * @return true if this number is greater or equal to the other one
     */
    boolean isGreaterThanOrEqualTo(final double val);

    /**
     * Determines whether this DrawlNumber is an integer
     *
     * @return <code>TRUE</code> if this DrawlNumber is an integer, <code>FALSE</code> otherwise.
     */
    @NotNull
    boolean isIntegerValue();

    /**
     * Returns whether this number is less than the other number.
     *
     * @param val tested number
     * @return true if this number is less than the other one
     */
    boolean isLessThan(@NotNull final Number val);

    /**
     * Returns whether this number is less than the other number.
     *
     * @param val tested number
     * @return true if this number is less than the other one
     */
    boolean isLessThan(final double val);

    /**
     * Returns whether this number is less or equal to the other number.
     *
     * @param val tested number
     * @return true if this number is less than the other one
     */
    boolean isLessThanOrEqualTo(@NotNull final Number val);

    /**
     * Returns whether this number is less or equal to the other number.
     *
     * @param val tested number
     * @return true if this number is less than the other one
     */
    boolean isLessThanOrEqualTo(final double val);

    /**
     * Indicates whether another value is not equal to this SisuBigDecimal.
     *
     * @param val
     * @return True if this SisuBigDecimal are not equal, False if they are.
     * @author Aarre Laakso
     * @version 1.0, 04/28/2020
     * @since 1.0, 04/28/2020
     */
    @NotNull Boolean isNotEqualTo(@NotNull final Number val);

    /**
     * Performs multiplication operation.
     *
     * @param multiplicand other number
     * @return multiplication result
     */
    @NotNull Number multiply(@NotNull final Number multiplicand);

    /**
     * Returns a BigDecimal whose value is (this × multiplicand), with rounding according to the context settings.
     *
     * @param multiplicand value to be multiplied by this BigDecimal.
     * @param mc           Ignored. Preserved for interface compatibility with SisuBigDecimal.
     * @return this * multiplicand, rounded as necessary.
     * @throws ArithmeticException if the result is inexact but the rounding mode is UNNECESSARY.
     */
    @NotNull Number multiply(@NotNull final Number multiplicand, final MathContext mc);

    /**
     * Performs multiplication operation.
     *
     * @param multiplicand other number
     * @return addition multiplication result
     */
    @NotNull Number multiply(final double multiplicand);

    /**
     * Negates this number.
     *
     * @return negative of this number
     */
    @NotNull Number negate();

    /**
     * Returns this number powered by n.
     *
     * @param n         power
     * @param precision Ignored. Provided for compatibility with SisuBigDecimal interface.
     * @return power operation result
     */
    @NotNull Number pow(final int n, final int precision);

    /**
     * Create a new instance that has same value as this one rounded.
     *
     * @param mc Ignored. Provided for interface compatibility with SisuBigDecimal.
     * @return A new instance of SisuBigDecimal that has the same value as this one rounded.
     */
    @NotNull Number round(final MathContext mc);

    /**
     * Create a new instance that has the same value as this one rounded.
     *
     * @param places How many decimal places to preserve in the new instance
     * @return
     */
    @NotNull Number round(final int places);

    /**
     * Performs subtraction operation.
     *
     * @param subtrahend value to be subtracted from this DrawlNumber.
     * @return addition subtraction result
     */
    @NotNull Number subtract(@NotNull final Number subtrahend);

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
    @NotNull Number subtract(@NotNull final Number subtrahend, final MathContext mc);

    /**
     * Performs subtraction operation.
     *
     * @param subtrahend value to be subtracted from this DrawlNumber.
     * @return addition subtraction result
     */
    @NotNull Number subtract(final double subtrahend);

    /**
     * Converts number to the string with fixed decimal digits.
     * <p>
     * This method might truncate some numbers or add extra zeros at the end.
     *
     * @param numDecimals number of decimals, must be non negative
     * @return number as a plain string with specified number of decimals
     */
    String toFixedDecimalString(final int numDecimals);

    /**
     * Converts this number to the full, containing everything that can be restored.
     *
     * @return number in it's string representation
     */
    @NotNull String toFullString();

    /**
     * @returns a string representation of this SisuBigDecimal without an exponent field.
     */
    String toPlainString();

    @Override
    @NotNull String toString();


    /**
     * Convert this DrawlNumber to a String for SVG.
     * <p>
     * The number will be represented with as many decimal points as necessary for SVG and no more than SVG can
     * handle.
     */
    @NotNull String toSVG();

}
