package com.aarrelaakso.drawl.examples;

import com.aarrelaakso.drawl.*;

import java.io.IOException;

/**
 * Demonstrates drawing line endings at different sizes.
 */
public class LineEndingSizeExample {

    /**
     * Draws line endings at different sizes.
     *
     * @param args no command line arguments.
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {

        final Drawing drawing = new Drawing();

        final Text text1 = new Text("Default size");
        Measure textWidth = text1.getWidth();
        final Line line1 = new Line(Line.Orientation.HORIZONTAL);
        line1.setWidth(textWidth);
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.DEFAULT);
        line1.addLineEnding(lineEnding);
        line1.setBelow(text1);
        drawing.add(text1);
        drawing.add(line1);

        final Text text2 = new Text("Size 2");
        text2.setBelow(line1);
        final Line line2 = new Line(Line.Orientation.HORIZONTAL);
        line2.setWidth(textWidth);
        final LineEnding lineEnding2 = LineEnding.newInstance(LineEnding.Type.DEFAULT);
        lineEnding2.setSize(2.0);
        line2.addLineEnding(lineEnding2);
        line2.setBelow(text2);
        drawing.add(text2);
        drawing.add(line2);

        final Text text3 = new Text("Size 0.5");
        text3.setBelow(line2);
        final Line line3 = new Line(Line.Orientation.HORIZONTAL);
        line3.setWidth(textWidth);
        final LineEnding lineEnding3 = LineEnding.newInstance(LineEnding.Type.DEFAULT);
        lineEnding3.setSize(0.5);
        line3.addLineEnding(lineEnding3);
        line3.setBelow(text3);
        drawing.add(text3);
        drawing.add(line3);

        final Text text4 = new Text("Width = 2, Height = 0.5");
        text4.setBelow(line3);
        final Line line4 = new Line(Line.Orientation.HORIZONTAL);
        line4.setWidth(textWidth);
        final LineEnding lineEnding4 = LineEnding.newInstance(LineEnding.Type.DEFAULT);
        lineEnding4.setWidth(2.0);
        lineEnding4.setHeight(0.5);
        line4.addLineEnding(lineEnding4);
        line4.setBelow(text4);
        drawing.add(text4);
        drawing.add(line4);

        final Text text5 = new Text("Width = 0.5, Height = 2");
        text5.setBelow(line4);
        final Line line5 = new Line(Line.Orientation.HORIZONTAL);
        line5.setWidth(textWidth);
        final LineEnding lineEnding5 = LineEnding.newInstance(LineEnding.Type.DEFAULT);
        lineEnding5.setWidth(0.5);
        lineEnding5.setHeight(2.0);
        line5.addLineEnding(lineEnding5);
        line5.setBelow(text5);
        drawing.add(text5);
        drawing.add(line5);

        final Text bottomSpacer = new Text("");
        bottomSpacer.setBelow(line5);
        drawing.add(bottomSpacer);

        drawing.writeToFile("src/main/java/com/aarrelaakso/drawl/examples/LineEndingSizeExample.svg", 500, 500);
    }
}
