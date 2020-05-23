package com.aarrelaakso.drawl;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * !< A diamond-shaped line ending. Synonyms: RHOMBUS
 * <p>
 * \image html rhombus.svg width=300
 * <p>
 * We set the height of the rhombus to be equal to its sides:
 * <p>
 * \f[h = x\f]
 * <p>
 * \f[Area = \frac{w \times h}{2}\f]
 * <p>
 * Solving for width in terms of height:
 * <p>
 * \f[h^2 = (\frac{h}{2})^2 + (\frac{w}{2})^2\f]
 * <p>
 * \f[(\frac{w}{2})^2 = h^2 - (\frac{h}{2})^2\f]
 * <p>
 * \f[\frac{w^2}{4} = h^2 - \frac{h^2}{4}\f]
 * <p>
 * \f[w^2 = 4h^2 - h^2\f]
 * <p>
 * \f[w^2 = 3h^2\f]
 * <p>
 * \f[w = h \sqrt{3}\f]
 * <p>
 * Substituting into the formula for area:
 * <p>
 * \f[16 = \frac{h^2 \sqrt{3}}{2}\f]
 * <p>
 * \f[32 = h^2 \sqrt{3}\f]
 * <p>
 * \f[h^2 = \frac{32}{\sqrt{3}}\f]
 * <p>
 * \f[h = \frac{\sqrt{32}}{\sqrt[4]{3}}\f]
 * <p>
 * \f[h \approx 4.3\f]
 * <p>
 * Now calculating w:
 * <p>
 * \f[w = \sqrt{3} \frac{\sqrt{32}}{\sqrt[4]{3}}\f]
 * <p>
 * \f[w \approx 7.44\f]
 */
public class LineEndingDiamond extends LineEnding {

    @Override
    protected Type getLineEndingType() {
        return Type.DIAMOND;
    }

    @Override
    protected String getSVG() {
        StringBuilder svg = new StringBuilder();
        double height = sqrt(32.0) / pow(3.0, 1.0 / 4.0);                       // Approx. 4.3
        double width = sqrt(3.0) * height;                                      // Approx. 7.44
        width = width * this.getWidth();
        height = height * this.getHeight();
        svg.append(" viewBox='0 0 " + (width + 2) + " " + (height + 2) + "'");
        svg.append(" markerWidth='" + (width + 2) + "' markerHeight='" + (height + 2) + "'");
        svg.append(" refX='" + (width / 2.0 + 1) + "' refY='" + (height / 2.0 + 1) + "'>" + newLine);
        svg.append("<path d='M1," + (height / 2.0 + 1) + " L" + (width / 2.0 + 1) + "," + (height + 1) + " L" + (width + 1) + "," + (height / 2.0 + 1) + " L" + (width / 2.0 + 1) + ",1 z'");
        svg.append(" stroke='black'");
        svg.append(" fill='");
        svg.append(this.getFill());
        svg.append("'");
        return svg.toString();
    }
}
