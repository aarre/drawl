package com.aarrelaakso.drawl;

import static java.lang.Math.pow;


/**
        For the default line ending, we set the constraint of having the legs have twice the length of the base.

        * \image html triangle.svg width=300

        The width of this triangle can be calculated as:

        \f[w^2 + (\frac{h}{2})^2 = (2h)^2\f]

        \f[w^2 + \frac{h^2}{4} = 4h^2\f]

        \f[w^2 = 4h^2 - \frac{h^2}{4}\f]

        \f[w^2 = \frac{16h^2}{4} - \frac{h^2}{4}\f]

        \f[w^2 = \frac{15h^2}{4}\f]

        \f[w = \sqrt{\frac{15h^2}{4}}\f]

        \f[w = \frac{h \sqrt{15}}{2}\f]

        To calculate the area of the triangle:

        \f[Area = \frac{hw}{2}\f]

        Given that we want the area to be 16:

        \f[ \frac{hw}{2} = 16\f]

        \f[hw = 32\f]

        Substituting in our formula for w

        \f[h \frac{h \sqrt{15}}{2} = 32\f]

        \f[\frac{h^2 \sqrt{15}}{2} = 32\f]

        \f[h^2 \sqrt{15} = 64\f]

        \f[15 h^4  = 4096\f]

        \f[h^4  = \frac{4096}{15}\f]

        \f[h = \sqrt[4]{\frac{4096}{15}}\f]

        \f[h \approx 4.07\f]

        Then, to calculate the width of the triangle:

        \f[\frac{wh}{2} = 16\f]

        \f[wh = 32\f]

        \f[w = \frac{32}{h}\f]

        \f[w \approx 7.87\f]
*/
public class LineEndingTriangle extends LineEnding {

    @Override
    protected Type getLineEndingType() {
        return Type.TRIANGLE;
    }

    @Override
    protected String getSVG() {
        StringBuilder svg = new StringBuilder();
        // See the API documentation for the rationale for these calculations
        double quotient = 4096.0 / 15.0;
        // Take the 4th root of of the quotient
        double height = pow(quotient, 1.0 / 4.0);                      // approx. 4.07
        double width = 32.0 / height;                                    // approx. 7.87
        width = width * this.getWidth();
        height = height * this.getHeight();
        svg.append(" viewBox='0 0 " + (width + 3) + " " + (height + 2) + "'");
        svg.append(" markerWidth='" + (width + 3) + "' markerHeight='" + (height + 2) + "'");
        svg.append(" refX='" + (width / 2.0 + 1) + "' refY='" + (height / 2.0 + 1) + "'>" + newLine);
        svg.append("<path d='M1,1 L1," + (height + 1) + " L" + (width + 1) + "," + (height / 2.0 + 1) + " z'");
        svg.append(" stroke='black'");
        svg.append(" fill='");
        svg.append(this.getFill());
        svg.append("'");
        return svg.toString();
    }

}
