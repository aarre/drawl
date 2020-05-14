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
    @DisplayName("whenALineHasBeenConstructedThenYouCanAddAnLineEndingToIt")
    void whenALineHasBeenConstructedThenYouCanAddAnLineEndingToIt()
    {
        Line line = new Line();
        LineEnding lineEnding = new LineEnding();
        line.addLineEnding(lineEnding);
    }

    @Test
    @DisplayName("whenALineHasAnLineEndingThenItShowsUpInTheSVG")
    void whenALineHasAnLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding();
        line.addLineEnding(lineEnding);
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
        softly.then(svg).contains("stroke='black'");
        softly.then(svg).contains("fill='black'");
        softly.then(svg).contains("/>");
        softly.then(svg).contains("</marker>");
        softly.then(svg).contains("</defs>");
    }


    @DisplayName("when An Arrowhead Is Constructed Then The Type Can Be Set")
    @ParameterizedTest
    @EnumSource(LineEnding.Type.class)
    void whenAnLineEndingIsConstructedThenTheTypeCanBeSet(LineEnding.Type type) {
        final LineEnding lineEnding = new LineEnding(type);
        then(lineEnding).isNotNull();
    }

    @Test
    @DisplayName("whenALineHasADefaultLineEndingThenItShowsUpInTheSVG")
    void whenALineHasADefaultLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.DEFAULT);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<marker id='TRIANGLE-");
    }

    @Test
    @DisplayName("whenALineHasANormalLineEndingThenItShowsUpInTheSVG")
    void whenALineHasANormalLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.NORMAL);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<marker id='TRIANGLE-");
    }

    @Test
    @DisplayName("when A Line Has A triangle LineEndingThenItShowsUpInTheSVG")
    void whenALineHasATriangleLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.TRIANGLE);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<marker id='TRIANGLE-");
    }

    @Test
    @DisplayName("When a line has a box LineEnding then it shows up in the SVG")
    void whenALineHasABoxLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.BOX);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>" + newLine)
                .contains("<marker id='BOX-")
                .contains("' orient='auto' viewBox='0 0 4 4' markerWidth='4' markerHeight='4' refX='2' refY='2'>" + newLine +
                "<path d='M0,0 L0,4 L4,4 L4,0 z' stroke='black' fill='black' />" + newLine +
                "</marker>" + newLine +
                "</defs>");
    }

    @Test
    @DisplayName("When a line has a square LineEnding then it shows up in the SVG")
    void whenALineHasASquareLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.SQUARE);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>" + newLine)
                .contains("<marker id='BOX-")
                .contains("' orient='auto' viewBox='0 0 4 4' markerWidth='4' markerHeight='4' refX='2' refY='2'>" + newLine +
                "<path d='M0,0 L0,4 L4,4 L4,0 z' stroke='black' fill='black' />" + newLine +
                "</marker>" + newLine +
                "</defs>");
    }

    @Test
    @DisplayName("When a line has a diamond LineEnding, then it shows up in the SVG")
    void whenALineHasADiamondLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.DIAMOND);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<marker id='DIAMOND-");
        softly.then(svg).contains("<defs>");
        softly.then(svg).contains("orient='auto' viewBox='0 0 7.4448388728167965 4.298279727294168' markerWidth='7.4448388728167965' markerHeight='4.298279727294168' refX='3.7224194364083982' refY='2.149139863647084'>" + newLine +
                        "<path d='M0,2.149139863647084 L3.7224194364083982,4.298279727294168 L7.4448388728167965,2.149139863647084L3.7224194364083982,0 z' stroke='black' fill='black' />" + newLine +
                        "</marker>" + newLine +
                        "</defs>");
    }

    @Test
    @DisplayName("When a line has a turned square LineEnding, then it shows up in the SVG")
    void whenALineHasATurnedSquareLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.TURNED_SQUARE);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>")
                        .contains("<marker id='TURNED_SQUARE-")
                        .contains("' orient='auto' viewBox='0 0 5.656854249492381 5.656854249492381' markerWidth='5.656854249492381' markerHeight='5.656854249492381' refX='2.8284271247461903' refY='2.8284271247461903'>" + newLine +
                "<path d='M2.8284271247461903,0 L5.656854249492381,2.8284271247461903 L2.8284271247461903,5.656854249492381 L0,2.8284271247461903 z' stroke='black' fill='black' />" + newLine +
                "</marker>" + newLine +
                "</defs>");
    }

    @Test
    @DisplayName("When a line has a disk LineEnding, then it shows up in the SVG")
    void whenALineHasADiskLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.DISK);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>")
                .contains("<marker id='DOT-")
                .contains("' orient='auto' viewBox='0 0 4.51351666838205 4.51351666838205' markerWidth='4.51351666838205' markerHeight='4.51351666838205' refX='2.256758334191025' refY='2.256758334191025'>" + newLine +
                "<circle cx='2.256758334191025' cy='2.256758334191025' r='2.256758334191025' stroke='black' fill='black' />" + newLine +
                "</marker>" + newLine +
                "</defs>");
    }

    @Test
    @DisplayName("When a line has a dot LineEnding, then it shows up in the SVG")
    void whenALineHasADotLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.DOT);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>")
                .contains("<marker id='DOT-")
                .contains("' orient='auto' viewBox='0 0 4.51351666838205 4.51351666838205' markerWidth='4.51351666838205' markerHeight='4.51351666838205' refX='2.256758334191025' refY='2.256758334191025'>" + newLine +
                "<circle cx='2.256758334191025' cy='2.256758334191025' r='2.256758334191025' stroke='black' fill='black' />" + newLine +
                "</marker>" + newLine +
                "</defs>");
    }

    @Test
    @DisplayName("When a line has a circle LineEnding, then it shows up in the SVG")
    void whenALineHasCircleLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.CIRCLE);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>")
                .contains("<marker id='CIRCLE-")
                .contains("' orient='auto' viewBox='0 0 4.51351666838205 4.51351666838205' markerWidth='4.51351666838205' markerHeight='4.51351666838205' refX='2.256758334191025' refY='2.256758334191025'>" + newLine +
                "<circle cx='2.256758334191025' cy='2.256758334191025' r='2.256758334191025' stroke='black' fill='white' />" + newLine +
                "</marker>" + newLine +
                "</defs>");
    }

    @Test
    @DisplayName("When a line has an OPEN_DOT LineEnding, then it shows up in the SVG")
    void whenALineHasAnOdotLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly)
    {
        final Line line = new Line();
        final LineEnding lineEnding = new LineEnding(LineEnding.Type.OPEN_DOT);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>")
                .contains("<marker id='CIRCLE-")
                .contains("' orient='auto' viewBox='0 0 4.51351666838205 4.51351666838205' markerWidth='4.51351666838205' markerHeight='4.51351666838205' refX='2.256758334191025' refY='2.256758334191025'>" + newLine +
                "<circle cx='2.256758334191025' cy='2.256758334191025' r='2.256758334191025' stroke='black' fill='white' />" + newLine +
                "</marker>" + newLine +
                "</defs>");
    }

    @Test
    @DisplayName("When a line has an INVERTED line ending, then it is the same as a REVERSE line ending")
    void whenALineHasAnInvertedLineEndingThenItIsTheSameAsAReverseLineEnding()
    {
        final Line lineInverted = new Line();
        final LineEnding lineEndingInverted = new LineEnding(LineEnding.Type.INVERTED);
        lineInverted.addLineEnding(lineEndingInverted);

        final Line lineReverse = new Line();
        final LineEnding lineEndingReverse = new LineEnding(LineEnding.Type.REVERSE);
        lineReverse.addLineEnding(lineEndingReverse);

        final Drawing drawingInverted = new Drawing();
        drawingInverted.add(lineInverted);
        final String svgInverted = drawingInverted.getSVG();
        final String svgInvertedClean = svgInverted.replaceAll("REVERSE-[0-9]*", "");

        final Drawing drawingReverse = new Drawing();
        drawingReverse.add(lineReverse);
        final String svgReverse = drawingReverse.getSVG();
        final String svgReverseClean = svgReverse.replaceAll("REVERSE-[0-9]*", "");

        then(svgInvertedClean).isEqualTo(svgReverseClean);

    }

    @Test
    @DisplayName("When a LineEnding has been created, the user can set the fill color")
    void whenALineEndingHasBeenCreatedThenTheUserCanSetTheFillColor()
    {
        final Line line = new Line();
        LineEnding lineEnding = new LineEnding();
        lineEnding.setFill("red");
        line.addLineEnding(lineEnding);

        final Drawing drawing = new Drawing();
        drawing.add(line);

        String svg = drawing.getSVG();

        then(svg).contains("fill='red'");
    }


    @DisplayName("When a filled LineEnding is created, then the fill color is black")
    @ParameterizedTest
    @EnumSource(LineEnding.Type.class)
    void whenAFilledLineEndingIsCreatedThenTheFillColorIsBlack(LineEnding.Type type) {
        final LineEnding lineEnding = new LineEnding(type);
        final Line line = new Line();
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        try {
        String svg = drawing.getSVG();
            if ((type == LineEnding.Type.CIRCLE) ||
                    (type == LineEnding.Type.OPEN_DIAMOND) ||
                    (type == LineEnding.Type.OPEN_DOT)) {
                then(svg).contains("fill='white'");
                then(svg).doesNotContain("fill='black'");

            } else {
                then(svg).contains("fill='black'");
                then(svg).doesNotContain("fill='white'");
            }
        } catch(UnsupportedOperationException e) {
            // These are LineEndings not yet implemented
        }
    }



}