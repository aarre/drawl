/*
 * Copyright (c) 2020. Aarre Laakso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.aarrelaakso.drawl;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Pair of the arbitrary number and remainder.
 * This is the result after division operation.
 *
 * @author radek.hecl
 */
public final class DrawlNumberRemainderPair
{
    /**
     * Unit number.
     */
    private DrawlNumber number;
    /**
     * Remainder.
     */
    private DrawlNumber remainder;

    /**
     * Creates new instance.
     */
    private DrawlNumberRemainderPair()
    {
    }

    /**
     * Creates new instance.
     *
     * @param number    number
     * @param remainder remainder
     * @return number remainder pair
     */
    public static @NotNull DrawlNumberRemainderPair valueOf(final DrawlNumber number, final DrawlNumber remainder)
    {
        @NotNull final DrawlNumberRemainderPair res = new DrawlNumberRemainderPair();
        res.number = number;
        res.remainder = remainder;
        res.guardInvariants();
        return res;
    }

    @Override
    public boolean equals(final Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * Returns number.
     *
     * @return number
     */
    public DrawlNumber getNumber()
    {
        return this.number;
    }

    /**
     * Returns remainder.
     *
     * @return remainder
     */
    public DrawlNumber getRemainder()
    {
        return this.remainder;
    }

    /**
     * Guards this object to be consistent. Throws exception if this is not the case.
     */
    private void guardInvariants()
    {
        if (this.number == null)
        {
            throw new UnsupportedOperationException("number cannot be null");
        }
        if (this.remainder == null)
        {
            throw new UnsupportedOperationException("remainder cannot be null");
        }
    }

    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
