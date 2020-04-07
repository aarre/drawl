package com.aarrelaakso.drawl;

import com.aarrelaakso.drawl.Circle;

public class Drawl {
    public static void main(String[] args) {
        System.out.println("Hello World");
        Drawing drawing = new Drawing();
        Circle circle = new Circle();
        drawing.add(circle);
        Circle circle2 = new Circle();
        drawing.add(circle2);
        String svg = drawing.getSVG(200, 200);
        System.out.println(svg);
    }
}
