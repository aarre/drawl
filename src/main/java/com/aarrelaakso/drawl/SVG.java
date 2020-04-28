package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SVG
{

    /**
     * Convert a number to a string for SVG.
     * <p>
     * The number will be represented with as many decimal points as necessary for SVG and no more than SVG can
     * handle.
     *
     * @param number the number to convert.
     */
    public static @NotNull String toString(BigDecimal number)
    {
        String result = "";
        Float floatValue = number.floatValue();
        if (SisuBigDecimal.isIntegerValue(floatValue))
        {
                result = String.valueOf(number.intValue());
        }
        else
        {
            result = String.valueOf(floatValue);
        }
        return result;
    }

}
