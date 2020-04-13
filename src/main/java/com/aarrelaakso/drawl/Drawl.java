package com.aarrelaakso.drawl;

import com.aarrelaakso.drawl.Circle;

public class Drawl {
    public static void main(String[] args) {
        System.out.println("Hello World");
        Drawing drawing = new Drawing();
        Circle circle1 = new Circle();
        drawing.add(circle1);
        Circle circle2 = new Circle();
        drawing.add(circle2);
        circle2.setRightOf(circle1);
        String svg = drawing.getSVG(200, 200);
        System.out.println(svg);
    }
}
