package com.aarrelaakso.drawl;

import com.google.common.math.DoubleMath;

public class SVG {

    /**
     * Convert a number to a string with as few decimal points as necessary.
     *
     * @param number the number to convert.
     */
    public static String toString(Double number) {
        String result = null;
        // TODO Handle more than integers
        if (DoubleMath.isMathematicalInteger(number)) {
            result = String.valueOf(number.intValue());
        } else {
            result = String.valueOf(number);
        }
        return result;
    }
}
