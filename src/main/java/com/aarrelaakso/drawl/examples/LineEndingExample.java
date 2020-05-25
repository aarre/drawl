package com.aarrelaakso.drawl.examples;

import com.aarrelaakso.drawl.*;

import java.io.IOException;

public class ArrowheadExample {

    public static void main(final String[] args) throws IOException {

        final Drawing drawing = new Drawing();

        final Text text1 = new Text("DEFAULT, NORMAL, TRIANGLE");
        Measure textWidth = text1.getWidth();
        final Line line1 = new Line(Line.Orientation.HORIZONTAL);
        line1.setWidth(textWidth);
        line1.setThickness(5);
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.DEFAULT);
        line1.addLineEnding(lineEnding);
        line1.setBelow(text1);
        drawing.add(text1);
        drawing.add(line1);

        final Text text2 = new Text("BOX, SQUARE");
        text2.setBelow(line1);
        final Line line2 = new Line(Line.Orientation.HORIZONTAL);
        line2.setWidth(textWidth);
        line2.setThickness(5);
        final LineEnding lineEnding2 = LineEnding.newInstance(LineEnding.Type.BOX);
        line2.addLineEnding(lineEnding2);
        line2.setBelow(text2);
        drawing.add(text2);
        drawing.add(line2);

        final Text text3 = new Text("TURNED_SQUARE");
        text3.setBelow(line2);
        final Line line3 = new Line(Line.Orientation.HORIZONTAL);
        line3.setWidth(textWidth);
        line3.setThickness(5);
        final LineEnding lineEnding3 = LineEnding.newInstance(LineEnding.Type.TURNED_SQUARE);
        line3.addLineEnding(lineEnding3);
        line3.setBelow(text3);
        drawing.add(text3);
        drawing.add(line3);

        final Text text4 = new Text("DISK, DOT");
        text4.setBelow(line3);
        final Line line4 = new Line(Line.Orientation.HORIZONTAL);
        line4.setWidth(textWidth);
        line4.setThickness(5);
        final LineEnding lineEnding4 = LineEnding.newInstance(LineEnding.Type.DOT);
        line4.addLineEnding(lineEnding4);
        line4.setBelow(text4);
        drawing.add(text4);
        drawing.add(line4);

        final Text text5 = new Text("CIRCLE, OPEN_DOT");
        text5.setBelow(line4);
        final Line line5 = new Line(Line.Orientation.HORIZONTAL);
        line5.setWidth(textWidth);
        line5.setThickness(5);
        final LineEnding lineEnding5 = LineEnding.newInstance(LineEnding.Type.CIRCLE);
        line5.addLineEnding(lineEnding5);
        line5.setBelow(text5);
        drawing.add(text5);
        drawing.add(line5);

        final Text text6 = new Text("DIAMOND, RHOMBUS");
        text6.setBelow(line5);
        final Line line6 = new Line(Line.Orientation.HORIZONTAL);
        line6.setWidth(textWidth);
        line6.setThickness(5);
        final LineEnding lineEnding6 = LineEnding.newInstance(LineEnding.Type.DIAMOND);
        line6.addLineEnding(lineEnding6);
        line6.setBelow(text6);
        drawing.add(text6);
        drawing.add(line6);

        final Text text7 = new Text("OPEN_DIAMOND");
        text7.setBelow(line6);
        final Line line7 = new Line(Line.Orientation.HORIZONTAL);
        line7.setWidth(textWidth);
        line7.setThickness(5);
        final LineEnding lineEnding7 = LineEnding.newInstance(LineEnding.Type.OPEN_DIAMOND);
        line7.addLineEnding(lineEnding7);
        line7.setBelow(text7);
        drawing.add(text7);
        drawing.add(line7);

        final Text text8 = new Text("INVERSE, REVERSE");
        text8.setBelow(line7);
        final Line line8 = new Line(Line.Orientation.HORIZONTAL);
        line8.setWidth(textWidth);
        line8.setThickness(5);
        final LineEnding lineEnding8 = LineEnding.newInstance(LineEnding.Type.REVERSE);
        line8.addLineEnding(lineEnding8);
        line8.setBelow(text8);
        drawing.add(text8);
        drawing.add(line8);

        final Text text9 = new Text("RECTANGLE");
        text9.setBelow(line8);
        final Line line9 = new Line(Line.Orientation.HORIZONTAL);
        line9.setWidth(textWidth);
        line9.setThickness(5);
        final LineEnding lineEnding9 = LineEnding.newInstance(LineEnding.Type.RECTANGLE);
        line9.addLineEnding(lineEnding9);
        line9.setBelow(text9);
        drawing.add(text9);
        drawing.add(line9);

        final Text text10 = new Text("BAR");
        text10.setBelow(line9);
        final Line line10 = new Line(Line.Orientation.HORIZONTAL);
        line10.setWidth(textWidth);
        line10.setThickness(5);
        final LineEnding lineEnding10 = LineEnding.newInstance(LineEnding.Type.BAR);
        line10.addLineEnding(lineEnding10);
        line10.setBelow(text10);
        drawing.add(text10);
        drawing.add(line10);

        final Text text11 = new Text("STEALTH");
        text11.setBelow(line10);
        final Line line11 = new Line(Line.Orientation.HORIZONTAL);
        line11.setWidth(textWidth);
        line11.setThickness(5);
        final LineEnding lineEnding11 = LineEnding.newInstance(LineEnding.Type.STEALTH);
        line11.addLineEnding(lineEnding11);
        line11.setBelow(text11);
        drawing.add(text11);
        drawing.add(line11);

        final Text text12 = new Text("KITE");
        text12.setBelow(line11);
        final Line line12 = new Line(Line.Orientation.HORIZONTAL);
        line12.setWidth(textWidth);
        line12.setThickness(5);
        final LineEnding lineEnding12 = LineEnding.newInstance(LineEnding.Type.KITE);
        line12.addLineEnding(lineEnding12);
        line12.setBelow(text12);
        drawing.add(text12);
        drawing.add(line12);

        final Text text13 = new Text("BRACKET");
        text13.setBelow(line12);
        final Line line13 = new Line(Line.Orientation.HORIZONTAL);
        line13.setWidth(textWidth);
        line13.setThickness(5);
        final LineEnding lineEnding13 = LineEnding.newInstance(LineEnding.Type.BRACKET);
        line13.addLineEnding(lineEnding13);
        line13.setBelow(text13);
        drawing.add(text13);
        drawing.add(line13);

        final Text text14 = new Text("ELLIPSE");
        text14.setBelow(line13);
        final Line line14 = new Line(Line.Orientation.HORIZONTAL);
        line14.setWidth(textWidth);
        line14.setThickness(5);
        final LineEnding lineEnding14 = LineEnding.newInstance(LineEnding.Type.ELLIPSE);
        line14.addLineEnding(lineEnding14);
        line14.setBelow(text14);
        drawing.add(text14);
        drawing.add(line14);

        final Text bottomSpacer = new Text("");
        bottomSpacer.setBelow(line14);
        drawing.add(bottomSpacer);

        drawing.writeToFile("src/main/java/com/aarrelaakso/drawl/examples/ArrowheadExample.svg", 500, 1000);
    }
}
