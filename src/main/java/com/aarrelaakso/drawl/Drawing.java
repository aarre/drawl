package com.aarrelaakso.drawl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Drawing {

    private Set<Circle> contents;

    public Drawing() {
        contents = new HashSet<Circle>();
    }

    public void add(Circle circle) {
        contents.add(circle);
    }

    public Integer size() {
        final Integer integer = new Integer(1);
        return integer;
    }

    /**
     * Get the SVG for this Drawing.
     *
     * @param x The desired width of the output (in pixels).
     * @param y The desired height of the output (in pixels).
     * @return A string of valid SVG that depicts the drawing within the bounds of x and y.
     */
    public String getSVG(int x, int y) {
        Circle next;
        String svg = new String("<svg");
        svg += " width=\"";
        svg += x;
        svg += "\"";
        svg += " height=\"";
        svg += y;
        svg += "\"";
        svg += ">";
        for (Circle content : this.contents) {
            content.setRadius(x);
            svg += content.getSVG();
        }
        svg += "</svg>";
        return(svg);
    }
}
