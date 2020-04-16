package com.aarrelaakso.drawl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;



/**
 * Class for mathematical operations with BigDecimal values that are not
 * provided by the BigDecimal class itself.
 */
public class BigDecimalMath {

    public static int SCALE = 32;

    public static RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    public static MathContext mathContext = new MathContext(32, RoundingMode.HALF_UP);


    /**
     * Test whether a BigDecimal is a mathematical integer.
     *
     * @param bd the BigDecimal value to test
     * @return True if bd is a mathematical integer,
     *         false otherwise.
     */
    static boolean isIntegerValue(BigDecimal bd) {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }
}
