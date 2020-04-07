package com.aarrelaakso.drawl;

import com.aarrelaakso.drawl.Circle;

public class Drawl {
    public static void main(String[] args) {
        System.out.println("Hello World");
        Drawing drawing = new Drawing();
        Circle circle = new Circle();
        drawing.add(circle);
        String svg = drawing.getSVG(100, 100);
        System.out.println(svg);
    }
}
