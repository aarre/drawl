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

import com.aarrelaakso.drawl.Drawing;
import com.aarrelaakso.drawl.Line;
import com.aarrelaakso.drawl.LineEnding;
import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Tests the public API of Arrowheads.
 */
@DisplayName("Arrowheads - Public API")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith(SoftAssertionsExtension.class)
public class LineEndingDrawingTestPublic {

    String newLine = System.getProperty("line.separator");

    @DisplayName("When creating a LineEnding the user can set the height and width")
    @ParameterizedTest
    @EnumSource(LineEnding.Type.class)
    void whenCreatingALineEndingTheUserCanSetTheHeightAndWidth(LineEnding.Type type, BDDSoftAssertions softly) {
        double height = ThreadLocalRandom.current().nextDouble(10);
        double width = ThreadLocalRandom.current().nextDouble(10);
        final LineEnding lineEnding = LineEnding.newInstance(type, width, height);
        softly.then(lineEnding).isNotNull();
        softly.then(lineEnding.getWidth()).isEqualTo(width);
        softly.then(lineEnding.getHeight()).isEqualTo(height);
    }

    @DisplayName("When a LineEnding has been created, then the user can set the height and width")
    @ParameterizedTest
    @EnumSource(LineEnding.Type.class)
    void whenALineEndingHasBeenCreatedThenTheUserCanSetTheHeightAndWidth(LineEnding.Type type, BDDSoftAssertions softly) {
        double height = ThreadLocalRandom.current().nextDouble(10);
        double width = ThreadLocalRandom.current().nextDouble(10);
        final LineEnding lineEnding = LineEnding.newInstance(type);
        softly.then(lineEnding).isNotNull();
        lineEnding.setWidth(width);
        lineEnding.setHeight(height);
        softly.then(lineEnding.getWidth()).isEqualTo(width);
        softly.then(lineEnding.getHeight()).isEqualTo(height);
    }

    @DisplayName("When a filled LineEnding is created, then the fill color is black")
    @ParameterizedTest
    @EnumSource(LineEnding.Type.class)
    void whenAFilledLineEndingIsCreatedThenTheFillColorIsBlack(LineEnding.Type type) {
        final LineEnding lineEnding = LineEnding.newInstance(type);
        final Line line = new Line();
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        try {
            String svg = drawing.getSVG();
            if ((type == LineEnding.Type.CIRCLE) ||
                    (type == LineEnding.Type.OPEN_DIAMOND) ||
                    (type == LineEnding.Type.OPEN_DOT) ||
                    (type == LineEnding.Type.BRACKET)) {
                then(svg).contains("fill='white'");
                then(svg).doesNotContain("fill='black'");

            } else {
                then(svg).contains("fill='black'");
                then(svg).doesNotContain("fill='white'");
            }
        } catch (UnsupportedOperationException e) {
            // These are LineEndings not yet implemented
        }
    }

    @DisplayName("When a LineEnding has been created, the user can set the fill color")
    @ParameterizedTest
    @EnumSource(LineEnding.Type.class)
    void whenALineEndingHasBeenCreatedThenTheUserCanSetTheFillColor(LineEnding.Type type) {
        final Line line = new Line();
        LineEnding lineEnding = LineEnding.newInstance(type);
        lineEnding.setFill("red");
        line.addLineEnding(lineEnding);

        final Drawing drawing = new Drawing();
        drawing.add(line);

        String svg = drawing.getSVG();

        then(svg).contains("fill='red'");
    }

    @DisplayName("When a LineEnding has been created, the user can set the stroke color")
    @ParameterizedTest
    @EnumSource(LineEnding.Type.class)
    void whenALineEndingHasBeenCreatedThenTheUserCanSetTheStrokeColor(LineEnding.Type type) {
        final Line line = new Line();
        LineEnding lineEnding = LineEnding.newInstance(type);
        lineEnding.setStroke("red");
        line.addLineEnding(lineEnding);

        final Drawing drawing = new Drawing();
        drawing.add(line);

        String svg = drawing.getSVG();

        then(svg).contains("stroke='red'");
    }

    @DisplayName("when A LineEnding Is Constructed Then The Type Can Be Set")
    @ParameterizedTest
    @EnumSource(LineEnding.Type.class)
    void whenALineEndingIsConstructedThenTheTypeCanBeSet(LineEnding.Type type) {
        final LineEnding lineEnding = LineEnding.newInstance(type);
        then(lineEnding).isNotNull();
    }

    @DisplayName("When a LineEnding is created, then the user can set the relative size")
    @ParameterizedTest
    @EnumSource(LineEnding.Type.class)
    void whenALineEndingIsCreatedThenTheUserCanSetTheSize(LineEnding.Type type, BDDSoftAssertions softly) {
        Line lineNormal = new Line();
        Line lineScaled = new Line();
        final LineEnding lineEndingNormal = LineEnding.newInstance(type);
        final LineEnding lineEndingScaled = LineEnding.newInstance(type);
        lineNormal.addLineEnding(lineEndingNormal);
        lineScaled.addLineEnding(lineEndingScaled);
        double size = ThreadLocalRandom.current().nextDouble(10);
        lineEndingScaled.setSize(size);
        Drawing drawingNormal = new Drawing();
        drawingNormal.add(lineNormal);
        Drawing drawingScaled = new Drawing();
        drawingScaled.add(lineScaled);
        String svgNormal = drawingNormal.getSVG();
        LineEndingSVG lineEndingSVGNormal = new LineEndingSVG(svgNormal);
        String svgScaled = drawingScaled.getSVG();
        LineEndingSVG lineEndingSVGScaled = new LineEndingSVG(svgScaled);
        softly.then(lineEndingSVGScaled.markerWidth).isNotEqualTo(lineEndingSVGNormal.markerWidth);
        softly.then(lineEndingSVGScaled.markerHeight).isNotEqualTo(lineEndingSVGNormal.markerHeight);

    }

    @Test
    @DisplayName("When a line has a BAR LineEnding, then it shows up in the SVG")
    void whenALineHasABarLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.BAR);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>" + newLine +
                "<marker id='BAR")
                .contains("' orient='auto' viewBox='0 0 3.0 8.0' markerWidth='3.0' markerHeight='8.0' refX='1.5' refY='4.0'>" + newLine +
                        "<path d='M1,1 L1,7.0 L2.0,7.0 L2.0,1 z' fill='black' />" + newLine +
                        "</marker>" + newLine +
                        "</defs>");
    }

    @Test
    @DisplayName("When a line has a BOX LineEnding then it shows up in the SVG")
    void whenALineHasABoxLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.BOX);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        LineEndingSVG lineEndingSVG = new LineEndingSVG(svg);
        softly.then(lineEndingSVG.type).isEqualTo("BOX");
        softly.then(lineEndingSVG.viewBoxX1).isEqualTo(0);
        softly.then(lineEndingSVG.viewBoxY1).isEqualTo(0);
        softly.then(lineEndingSVG.viewBoxX2).isEqualTo(6);
        softly.then(lineEndingSVG.viewBoxY2).isEqualTo(6);
        softly.then(lineEndingSVG.markerWidth).isEqualTo(6);
        softly.then(lineEndingSVG.markerHeight).isEqualTo(6);
        softly.then(lineEndingSVG.refX).isEqualTo(3);
        softly.then(lineEndingSVG.refY).isEqualTo(3);
        softly.then(lineEndingSVG.path).isEqualTo("<path d='M1,1 L1,5 L5,5 L5,1 z' stroke='black' fill='black' />");
    }

    @Test
    @DisplayName("When a line has a BRACKET LineEnding, then it shows up in the SVG")
    void whenALineHasABracketLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.BRACKET);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>" + newLine +
                "<marker id='BRACKET")
                .contains("' orient='auto' viewBox='0 0 5 8' markerWidth='5' markerHeight='8' refX='4' refY='4'>" + newLine +
                        "<path d='M1,1 L4,1 L4,7 L1,7' stroke='black' fill='white' fill-opacity='0.0' />" + newLine +
                        "</marker>" + newLine +
                        "</defs>");
    }

    @Test
    @DisplayName("When a line has a circle LineEnding, then it shows up in the SVG")
    void whenALineHasACircleLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.CIRCLE);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>")
                .contains("<marker id='CIRCLE-")
                .contains("' orient='auto' ")
                .contains("viewBox='0 0 6.513516668382")
                .contains("6.513516668382")
                .contains("markerWidth='6.513516668382")
                .contains("markerHeight='6.513516668382")
                .contains("refX='3.2567583341910")
                .contains("refY='3.2567583341910")
                .contains("<circle cx='3.2567583341910")
                .contains("cy='3.2567583341910")
                .contains("r='2.2567583341910")
                .contains("stroke='black' fill='white' />" + newLine)
                .contains("</marker>" + newLine + "</defs>");
    }

    @Test
    @DisplayName("When a line has a CROW LineEnding, then it shows up in the SVG")
    void whenALineHasACrowLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.STEALTH);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        LineEndingSVG lineEndingSVG = new LineEndingSVG(svg);
        softly.then(lineEndingSVG.type).isEqualTo("STEALTH");
        softly.then(lineEndingSVG.viewBoxX1).isEqualTo(0);
        softly.then(lineEndingSVG.viewBoxY1).isEqualTo(0);
        softly.then(lineEndingSVG.viewBoxX2).isEqualTo(8);
        softly.then(lineEndingSVG.viewBoxY2).isEqualTo(8);
        softly.then(lineEndingSVG.markerWidth).isEqualTo(8);
        softly.then(lineEndingSVG.markerHeight).isEqualTo(8);
        softly.then(lineEndingSVG.refX).isEqualTo(4);
        softly.then(lineEndingSVG.refY).isEqualTo(4);
        softly.then(lineEndingSVG.path).isEqualTo("<path d='M1,1 L4,4 L1,7 L7,4 z' fill='black' />");
    }

    @Test
    @DisplayName("whenALineHasADefaultLineEndingThenItShowsUpInTheSVG")
    void whenALineHasADefaultLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.DEFAULT);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<marker id='TRIANGLE-");
    }

    @Test
    @DisplayName("When a line has a DIAMOND LineEnding, then it shows up in the SVG")
    void whenALineHasADiamondLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.DIAMOND);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<marker id='DIAMOND-");
        softly.then(svg).contains("<defs>");
        softly.then(svg).contains("orient='auto' viewBox='0 0 ");
        softly.then(svg).contains("9.4448388728167");
        softly.then(svg).contains("6.2982797272941");
        softly.then(svg).contains("markerWidth='9.44483887281679");
        softly.then(svg).contains("markerHeight='6.29827972729416");
        softly.then(svg).contains("refX='4.722419436408");
        softly.then(svg).contains("refY='3.149139863647");
        softly.then(svg).contains(",5.298279727294168");
        softly.then(svg).contains("L8.444838872816796");
        softly.then(svg).contains(",3.149139863647084");
        softly.then(svg).contains("L4.722419436408398");
        softly.then(svg).contains(",1 z' stroke='black' fill='black' />" + newLine + "</marker>" + newLine + "</defs>");
    }

    @Test
    @DisplayName("When a line has a DISK LineEnding, then it shows up in the SVG")
    void whenALineHasADiskLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.DISK);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>")
                .contains("<marker id='DOT-")
                .contains("' orient='auto' ")
                .contains("viewBox='0 0 6.513516668382")
                .contains("6.513516668382")
                .contains("markerWidth='6.513516668382")
                .contains("markerHeight='6.513516668382")
                .contains("refX='3.2567583341910")
                .contains("refY='3.2567583341910")
                .contains("<circle cx='3.2567583341910")
                .contains("cy='3.2567583341910")
                .contains("r='2.2567583341910")
                .contains("stroke='black' fill='black' />" + newLine)
                .contains("</marker>" + newLine + "</defs>");
    }

    @Test
    @DisplayName("When a line has a DOT LineEnding, then it shows up in the SVG")
    void whenALineHasADotLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.DOT);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>")
                .contains("<marker id='DOT-")
                .contains("' orient='auto' ")
                .contains("viewBox='0 0 6.513516668382")
                .contains("6.513516668382")
                .contains("markerWidth='6.513516668382")
                .contains("markerHeight='6.513516668382")
                .contains("refX='3.2567583341910")
                .contains("refY='3.2567583341910")
                .contains("<circle cx='3.2567583341910")
                .contains("cy='3.2567583341910")
                .contains("r='2.2567583341910")
                .contains("stroke='black' fill='black' />" + newLine)
                .contains("</marker>" + newLine + "</defs>");
    }

    @Test
    @DisplayName("When a line has an ELLIPSE LineEnding then it shows up in the SVG")
    void whenALineHasAEllipseLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.ELLIPSE);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        LineEndingSVG lineEndingSVG = new LineEndingSVG(svg);
        softly.then(lineEndingSVG.type).isEqualTo("ELLIPSE");
        softly.then(lineEndingSVG.viewBoxX1).isEqualTo(0);
        softly.then(lineEndingSVG.viewBoxY1).isEqualTo(0);
        softly.then(lineEndingSVG.viewBoxX2).isEqualTo(8);
        softly.then(lineEndingSVG.viewBoxY2).isEqualTo(6);
        softly.then(lineEndingSVG.markerWidth).isEqualTo(8);
        softly.then(lineEndingSVG.markerHeight).isEqualTo(6);
        softly.then(lineEndingSVG.refX).isEqualTo(4);
        softly.then(lineEndingSVG.refY).isEqualTo(3);
        softly.then(lineEndingSVG.path).isEqualTo("<ellipse cx='4' cy='3' rx='3' ry='2' stroke='black' fill='black' />");
    }

    @Test
    @DisplayName("when a line Has a KITE line ending then it shows up in the SVG")
    void whenALineHasAKiteLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.KITE);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>" + newLine +
                "<marker id='KITE")
                .contains("' orient='auto' viewBox='0 0 8 8' markerWidth='8' markerHeight='8' refX='4' refY='4'>" + newLine +
                        "<path d='M1,4 L3,7 L7,4 L3,1 z' stroke='black' fill='black' />" + newLine +
                        "</marker>" + newLine +
                        "</defs>");
    }

    @Test
    @DisplayName("When a line has a line ending then it shows up in the SVG")
    void whenALineHasALineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance();
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

    @Test
    @DisplayName("whenALineHasANormalLineEndingThenItShowsUpInTheSVG")
    void whenALineHasANormalLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.NORMAL);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<marker id='TRIANGLE-");
    }

    @Test
    @DisplayName("When a line has a rectangle LineEnding then it shows up in the SVG")
    void whenALineHasARectangleLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        //final LineEnding lineEnding = new LineEnding(LineEnding.Type.RECTANGLE);
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.RECTANGLE);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>" + newLine)
                .contains("<marker id='RECTANGLE-")
                .contains("' orient='auto'")
                .contains("viewBox='0 0 7.656854249492381 4.82842712474619'")
                .contains("markerWidth='7.656854249492381'")
                .contains("markerHeight='4.82842712474619'")
                .contains("refX='3.8284271247461903'")
                .contains("refY='2.414213562373095'>")
                .contains("<path d='M1,1 L1,3.8284271247461903 L6.656854249492381,3.8284271247461903 L6.656854249492381,1 z'")
                .contains("stroke='black'")
                .contains("fill='black' />")
                .contains("</marker>")
                .contains("</defs>");
    }

    @Test
    @DisplayName("When a line has a square LineEnding then it shows up in the SVG")
    void whenALineHasASquareLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.SQUARE);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>" + newLine)
                .contains("<marker id='BOX-")
                .contains("' orient='auto'")
                .contains("viewBox='0 0 6 6'")
                .contains("markerWidth='6'")
                .contains("markerHeight='6'")
                .contains("refX='3'")
                .contains("refY='3'>")
                .contains("<path d='M1,1 L1,5 L5,5 L5,1 z'")
                .contains("stroke='black'")
                .contains("fill='black' />")
                .contains("</marker>")
                .contains("</defs>");
    }

    @Test
    @DisplayName("When a line has a STEALTH LineEnding, then it shows up in the SVG")
    void whenALineHasAStealthLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.STEALTH);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>" + newLine +
                "<marker id='STEALTH")
                .contains("' orient='auto' viewBox='0 0 8 8' markerWidth='8' markerHeight='8' refX='4' refY='4'>" + newLine +
                        "<path d='M1,1 L4,4 L1,7 L7,4 z' fill='black' />" + newLine +
                        "</marker>" + newLine +
                        "</defs>");
    }

    @Test
    @DisplayName("when A Line Has A triangle LineEndingThenItShowsUpInTheSVG")
    void whenALineHasATriangleLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.TRIANGLE);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<marker id='TRIANGLE-");
    }

    @Test
    @DisplayName("When a line has a turned square LineEnding, then it shows up in the SVG")
    void whenALineHasATurnedSquareLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.TURNED_SQUARE);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>")
                .contains("<marker id='TURNED_SQUARE-")
                .contains("' orient='auto'")
                .contains("viewBox='0 0 7.6568542494923")
                .contains("markerWidth='7.6568542494923")
                .contains("markerHeight='7.6568542494923")
                .contains("refX='3.8284271247461903'")
                .contains("refY='3.8284271247461903'>")
                .contains("<path d='M3.8284271247461903,1 L6.656854249492381,3.8284271247461903 L3.8284271247461903,6.656854249492381 L1,3.8284271247461903 z'")
                .contains("stroke='black'")
                .contains("fill='black'")
                .contains("/>")
                .contains("</marker>")
                .contains("</defs>");
    }

    @Test
    @DisplayName("When a line has an INVERTED line ending, then it is the same as a REVERSE line ending")
    void whenALineHasAnInvertedLineEndingThenItIsTheSameAsAReverseLineEnding() {
        final Line lineInverted = new Line();
        final LineEnding lineEndingInverted = LineEnding.newInstance(LineEnding.Type.INVERTED);
        lineInverted.addLineEnding(lineEndingInverted);

        final Line lineReverse = new Line();
        final LineEnding lineEndingReverse = LineEnding.newInstance(LineEnding.Type.REVERSE);
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
    @DisplayName("When a line has an OPEN_DOT LineEnding, then it shows up in the SVG")
    void whenALineHasAnOdotLineEndingThenItShowsUpInTheSVG(BDDSoftAssertions softly) {
        final Line line = new Line();
        final LineEnding lineEnding = LineEnding.newInstance(LineEnding.Type.OPEN_DOT);
        line.addLineEnding(lineEnding);
        final Drawing drawing = new Drawing();
        drawing.add(line);
        final String svg = drawing.getSVG();
        softly.then(svg).contains("<defs>")
                .contains("<marker id='CIRCLE-")
                .contains("' orient='auto' ")
                .contains("viewBox='0 0 6.513516668382")
                .contains("6.513516668382")
                .contains("markerWidth='6.513516668382")
                .contains("markerHeight='6.513516668382")
                .contains("refX='3.2567583341910")
                .contains("refY='3.2567583341910")
                .contains("<circle cx='3.2567583341910")
                .contains("cy='3.2567583341910")
                .contains("r='2.2567583341910")
                .contains("stroke='black' fill='white' />" + newLine)
                .contains("</marker>" + newLine + "</defs>");
    }

    @Test
    @DisplayName("whenALineHasBeenConstructedThenYouCanAddAnLineEndingToIt")
    void whenALineHasBeenConstructedThenYouCanAddAnLineEndingToIt() {
        Line line = new Line();
        LineEnding lineEnding = new LineEnding();
        line.addLineEnding(lineEnding);
    }

}
