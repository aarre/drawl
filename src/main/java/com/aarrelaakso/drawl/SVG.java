package com.aarrelaakso.drawl;

import com.google.common.math.DoubleMath;

import java.math.BigDecimal;

public class SVG {

    /**
     * Convert a number to a string for SVG.
     *
     * The number will be represented with as many decimal points as necessary for SVG and no more than SVG can
     * handle.
     *
     * @param number the number to convert.
     */
    public static String toString(BigDecimal number) {
        String result;
        if (BigDecimalMath.isIntegerValue(number)) {
            result = String.valueOf(number.intValue());
        } else {
            result = String.valueOf(number.floatValue());
        }
        return result;
    }
}
