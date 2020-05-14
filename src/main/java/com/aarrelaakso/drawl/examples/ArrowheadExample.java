/*
 * Copyright (c) 2020. Aarre Laakso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.DEFAULT);
        line1.addLineEnding(lineEnding);
        line1.setBelow(text1);
        drawing.add(text1);
        drawing.add(line1);

        final Text text2 = new Text("BOX, SQUARE");
        text2.setBelow(line1);
        final Line line2 = new Line(Line.Orientation.HORIZONTAL);
        line2.setWidth(textWidth);
        line2.setThickness(5);
        final LineEnding lineEnding2 = new LineEnding(LineEnding.Type.BOX);
        line2.addLineEnding(lineEnding2);
        line2.setBelow(text2);
        drawing.add(text2);
        drawing.add(line2);

        final Text text3 = new Text("TURNED_SQUARE");
        text3.setBelow(line2);
        final Line line3 = new Line(Line.Orientation.HORIZONTAL);
        line3.setWidth(textWidth);
        line3.setThickness(5);
        final LineEnding lineEnding3 = new LineEnding(LineEnding.Type.TURNED_SQUARE);
        line3.addLineEnding(lineEnding3);
        line3.setBelow(text3);
        drawing.add(text3);
        drawing.add(line3);

        final Text text4 = new Text("DISK, DOT");
        text4.setBelow(line3);
        final Line line4 = new Line(Line.Orientation.HORIZONTAL);
        line4.setWidth(textWidth);
        line4.setThickness(5);
        final LineEnding lineEnding4 = new LineEnding(LineEnding.Type.DOT);
        line4.addLineEnding(lineEnding4);
        line4.setBelow(text4);
        drawing.add(text4);
        drawing.add(line4);

        final Text text5 = new Text("CIRCLE, OPEN_DOT");
        text5.setBelow(line4);
        final Line line5 = new Line(Line.Orientation.HORIZONTAL);
        line5.setWidth(textWidth);
        line5.setThickness(5);
        final LineEnding lineEnding5 = new LineEnding(LineEnding.Type.CIRCLE);
        line5.addLineEnding(lineEnding5);
        line5.setBelow(text5);
        drawing.add(text5);
        drawing.add(line5);

        final Text text6 = new Text("DIAMOND, RHOMBUS");
        text6.setBelow(line5);
        final Line line6 = new Line(Line.Orientation.HORIZONTAL);
        line6.setWidth(textWidth);
        line6.setThickness(5);
        final LineEnding lineEnding6 = new LineEnding(LineEnding.Type.DIAMOND);
        line6.addLineEnding(lineEnding6);
        line6.setBelow(text6);
        drawing.add(text6);
        drawing.add(line6);

        final Text text7 = new Text("OPEN_DIAMOND");
        text7.setBelow(line6);
        final Line line7 = new Line(Line.Orientation.HORIZONTAL);
        line7.setWidth(textWidth);
        line7.setThickness(5);
        final LineEnding lineEnding7 = new LineEnding(LineEnding.Type.OPEN_DIAMOND);
        line7.addLineEnding(lineEnding7);
        line7.setBelow(text7);
        drawing.add(text7);
        drawing.add(line7);

        final Text text8 = new Text("INVERSE, REVERSE");
        text8.setBelow(line7);
        final Line line8 = new Line(Line.Orientation.HORIZONTAL);
        line8.setWidth(textWidth);
        line8.setThickness(5);
        final LineEnding lineEnding8 = new LineEnding(LineEnding.Type.REVERSE);
        line8.addLineEnding(lineEnding8);
        line8.setBelow(text8);
        drawing.add(text8);
        drawing.add(line8);

        final Text bottomSpacer = new Text("");
        bottomSpacer.setBelow(line8);
        drawing.add(bottomSpacer);

        drawing.writeToFile("src/main/java/com/aarrelaakso/drawl/examples/ArrowheadExample.svg", 500, 500);
    }
}
