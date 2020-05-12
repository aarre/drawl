/*
 * Copyright (c) 2020. Aarre Laakso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.aarrelaakso.drawl.test;

import com.aarrelaakso.drawl.LineEnding;
import com.aarrelaakso.drawl.Drawing;
import com.aarrelaakso.drawl.Line;
import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Tests the public API of Arrowheads.
 */
@DisplayName("Arrowheads - Public API")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith(SoftAssertionsExtension.class)
public class LineEndingDrawingTestPublic {

    String newLine = System.getProperty("line.separator");

    @Test
    @DisplayName("whenALineHasBeenConstructedThenYouCanAddAnArrowheadToIt")
    void whenALineHasBeenConstructedThenYouCanAddAnArrowheadToIt()
    {
        Line line = new Line();
        LineEnding lineEnding = new LineEnding();
        line.addArrowhead(lineEnding);
    }

    @Test
    @DisplayName("whenALineHasAnArrowheadThenItShowsUpInTheSVG")
    void whenALineHasAnArrowheadThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding();
        line.addArrowhead(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>");
        softly.then(svg).contains("<marker id=");
        softly.then(svg).contains("orient='auto'");
        softly.then(svg).contains("markerWidth=");
        softly.then(svg).contains("markerHeight=");
        softly.then(svg).contains("refX=");
        softly.then(svg).contains("refY=");
        softly.then(svg).contains("<path d=");
        softly.then(svg).contains("fill='red'");
        softly.then(svg).contains("/>");
        softly.then(svg).contains("</marker>");
        softly.then(svg).contains("</defs>");
    }


    @DisplayName("when An Arrowhead Is Constructed Then The Type Can Be Set")
    @ParameterizedTest
    @EnumSource(LineEnding.Type.class)
    void whenAnArrowheadIsConstructedThenTheTypeCanBeSet(LineEnding.Type type) {
        final LineEnding lineEnding = new LineEnding(type);
        then(lineEnding).isNotNull();
    }

    @Test
    @DisplayName("whenALineHasADefaultArrowheadThenItShowsUpInTheSVG")
    void whenALineHasADefaultArrowheadThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.DEFAULT);
        line.addArrowhead(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<marker id='TRIANGLE'");
    }

    @Test
    @DisplayName("whenALineHasANormalArrowheadThenItShowsUpInTheSVG")
    void whenALineHasANormalArrowheadThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.NORMAL);
        line.addArrowhead(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<marker id='TRIANGLE'");
    }

    @Test
    @DisplayName("when A Line Has A triangle ArrowheadThenItShowsUpInTheSVG")
    void whenALineHasATriangleArrowheadThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.TRIANGLE);
        line.addArrowhead(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<marker id='TRIANGLE'");
    }

    @Test
    @DisplayName("When a line has a box arrowhead then it shows up in the SVG")
    void whenALineHasABoxArrowheadThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.BOX);
        line.addArrowhead(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>" + newLine +
                "<marker id='BOX' orient='auto' viewBox='0 0 4 4' markerWidth='4' markerHeight='4' refX='2' refY='2'>" + newLine +
                "<path d='M0,0 L0,4 L4,4 L4,0 z' fill='red' />" + newLine +
                "</marker>" + newLine +
                "</defs>");
    }

    @Test
    @DisplayName("When a line has a square arrowhead then it shows up in the SVG")
    void whenALineHasASquareArrowheadThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.SQUARE);
        line.addArrowhead(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>" + newLine +
                "<marker id='BOX' orient='auto' viewBox='0 0 4 4' markerWidth='4' markerHeight='4' refX='2' refY='2'>" + newLine +
                "<path d='M0,0 L0,4 L4,4 L4,0 z' fill='red' />" + newLine +
                "</marker>" + newLine +
                "</defs>");
    }

    @Test
    @DisplayName("When a line has a diamond arrowhead, then it shows up in the SVG")
    void whenALineHasADiamondArrowheadThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.DIAMOND);
        line.addArrowhead(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<marker id='DIAMOND'");
        softly.then(svg).contains("<defs>" + newLine +
                "<marker id='DIAMOND' orient='auto' viewBox='0 0 5.656854249492381 5.656854249492381' markerWidth='5.656854249492381' markerHeight='5.656854249492381' refX='2.8284271247461903' refY='2.8284271247461903'>" + newLine +
                "<path d='M2.8284271247461903,0 L5.656854249492381,2.8284271247461903 L2.8284271247461903,5.656854249492381 L0,2.8284271247461903 z' fill='red' />" + newLine +
                "</marker>" + newLine +
                "</defs>");
    }

    @Test
    @DisplayName("When a line has a turned square arrowhead, then it shows up in the SVG")
    void whenALineHasATurnedSquareArrowheadThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.TURNED_SQUARE);
        line.addArrowhead(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>" + newLine +
                "<marker id='DIAMOND' orient='auto' viewBox='0 0 5.656854249492381 5.656854249492381' markerWidth='5.656854249492381' markerHeight='5.656854249492381' refX='2.8284271247461903' refY='2.8284271247461903'>" + newLine +
                "<path d='M2.8284271247461903,0 L5.656854249492381,2.8284271247461903 L2.8284271247461903,5.656854249492381 L0,2.8284271247461903 z' fill='red' />" + newLine +
                "</marker>" + newLine +
                "</defs>");
    }

    @Test
    @DisplayName("When a line has a disk arrowhead, then it shows up in the SVG")
    void whenALineHasADiskArrowheadThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.DISK);
        line.addArrowhead(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>" + newLine +
                "<marker id='DOT' orient='auto' viewBox='0 0 4.51351666838205 4.51351666838205' markerWidth='4.51351666838205' markerHeight='4.51351666838205' refX='2.256758334191025' refY='2.256758334191025'>" + newLine +
                "<circle cx='2.256758334191025' cy='2.256758334191025' r='2.256758334191025' fill='red' />" + newLine +
                "</marker>" + newLine +
                "</defs>");
    }

    @Test
    @DisplayName("When a line has a dot arrowhead, then it shows up in the SVG")
    void whenALineHasADotArrowheadThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.DOT);
        line.addArrowhead(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>" + newLine +
                "<marker id='DOT' orient='auto' viewBox='0 0 4.51351666838205 4.51351666838205' markerWidth='4.51351666838205' markerHeight='4.51351666838205' refX='2.256758334191025' refY='2.256758334191025'>" + newLine +
                "<circle cx='2.256758334191025' cy='2.256758334191025' r='2.256758334191025' fill='red' />" + newLine +
                "</marker>" + newLine +
                "</defs>");
    }
}