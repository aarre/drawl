package com.aarrelaakso.drawl.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineEndingSVG {

    String newLine = System.getProperty("line.separator");

    public String type;
    public int id;
    public double viewBoxX1;
    public double viewBoxY1;
    public double viewBoxX2;
    public double viewBoxY2;
    public double markerWidth;
    public double markerHeight;
    public double refX;
    public double refY;
    public String path;
    public String rest;


    public LineEndingSVG(String svg) {
        parse(svg);
    }

    /**
     * Parses the <defs>...</defs> portion of some SVG.
     *
     * @param svg the SVG to parse.
     */
    void parse(String svg) {
        System.out.println("SVG");
        System.out.println(svg);
        StringBuilder regexBldr = new StringBuilder();
        regexBldr.append("<\\?xml version='1.0' standalone='no'\\?><svg xmlns='http://www.w3.org/2000/svg'>");
        regexBldr.append(newLine);
        regexBldr.append("<defs>");
        regexBldr.append(newLine);
        regexBldr.append("<marker id='([A-Z_]*)-([0-9]*)' orient='auto' viewBox='([0-9.]*) ([0-9.]*) ([0-9.]*) ([0-9.]*)' ");
        regexBldr.append("markerWidth='([0-9.]*)' markerHeight='([0-9.]*)' ");
        regexBldr.append("refX='([0-9.]*)' refY='([0-9.]*)'>");
        regexBldr.append(newLine);
        regexBldr.append("(<[^>]*>)");
        regexBldr.append(newLine);
        regexBldr.append("</marker>");
        regexBldr.append(newLine);
        regexBldr.append("</defs>");
        regexBldr.append(newLine);
        regexBldr.append(newLine);
        regexBldr.append("(<[^>]*>)");
        regexBldr.append(newLine);
        regexBldr.append("</svg>");
        String regex = regexBldr.toString();
        System.out.println("REGEX");
        System.out.println(regex);
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);

        Matcher matcher = pattern.matcher(svg);
        matcher.find();
        assert matcher.groupCount() == 12;

        type = matcher.group(1);
        id = Integer.parseInt(matcher.group(2));
        viewBoxX1 = Double.parseDouble(matcher.group(3));
        viewBoxY1 = Double.parseDouble(matcher.group(4));
        viewBoxX2 = Double.parseDouble(matcher.group(5));
        viewBoxY2 = Double.parseDouble(matcher.group(6));
        markerWidth =  Double.parseDouble(matcher.group(7));
        markerHeight =  Double.parseDouble(matcher.group(8));
        refX =  Double.parseDouble(matcher.group(9));
        refY =  Double.parseDouble(matcher.group(10));
        path = matcher.group(11);
        rest = matcher.group(12);

    }
}
