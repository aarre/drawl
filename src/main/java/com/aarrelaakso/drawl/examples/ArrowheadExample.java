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

import com.aarrelaakso.drawl.Arrowhead;
import com.aarrelaakso.drawl.Drawing;
import com.aarrelaakso.drawl.Line;
import com.aarrelaakso.drawl.Text;

import java.io.IOException;

public class ArrowheadExample {

    public static void main(final String[] args) throws IOException {

        final Text text = new Text("DEFAULT, NORMAL, TRIANGLE");
        final Line line = new Line(Line.Orientation.HORIZONTAL);
        final Arrowhead arrowhead = new Arrowhead(Arrowhead.Type.DEFAULT);
        line.addArrowhead(arrowhead);
        line.setRightOf(text);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        drawing.add(text);

        drawing.writeToFile("src/main/java/com/aarrelaakso/drawl/examples/ArrowheadExample.svg", 1000, 500);
    }
}
