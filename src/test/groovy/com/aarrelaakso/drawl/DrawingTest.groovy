package com.aarrelaakso.drawl.test

import com.aarrelaakso.drawl.Circle
import com.aarrelaakso.drawl.SisuBigDecimal
import spock.lang.Specification
import org.apache.commons.lang3.StringUtils

import com.aarrelaakso.drawl.Drawing

class DrawingTestSVG extends Specification {

    def "SVG: The SVG generated by a drawing without a circle does not contain the string 'circle'"() {
        when:
        Drawing drawing = new Drawing()
        then:
        def svg = drawing.getSVG(100, 100)
        svg.indexOf("circle") < 0
    }

    def "SVG: When a Circle is placed with an implicit position, the SVG contains a concrete position"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle = new Circle()
        drawing.add(circle)
        String svg = drawing.getSVG(100, 100)

        then:
        svg.contains("circle r=\"50\" cx=\"50\" cy=\"50\"")
    }

    def "SVG: When a drawing has three adjacent default circles added sequentially, then the generated SVG is correct"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle1 = new Circle()
        drawing.add(circle1)
        Circle circle2 = new Circle()
        drawing.add(circle2)
        circle2.setRightOf(circle1)
        Circle circle3 = new Circle()
        drawing.add(circle3)
        circle3.setRightOf(circle2)
        Integer width = 100
        Integer height = 100
        String svg = drawing.getSVG(width, height)

        then:
        svg.contains("cx=\"16.666666\"")
        svg.contains("cx=\"50\"")
        svg.contains("cx=\"83.333336\"")
    }

    def "SVG: When a drawing has two adjacent circles, they are placed accordingly in the SVG (other order)"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle1.setRightOf(circle2)
        Drawing drawing = new Drawing()
        drawing.add(circle1)
        drawing.add(circle2)
        String svg = drawing.getSVG(200, 100)

        then:
        svg.contains("circle r=\"50\" cx=\"50\" cy=\"50\"")
        svg.contains("circle r=\"50\" cx=\"150\" cy=\"50\"")
    }

    def "SVG: When a drawing has two default Circles, the implicit width of the contents is the same as when it has one"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        Drawing drawing = new Drawing()
        drawing.add(circle1)
        drawing.add(circle2)
        SisuBigDecimal implicitWidthOfContents = drawing.getImplicitWidthOfContents()

        then:
        implicitWidthOfContents == SisuBigDecimal.ONE
    }

    def "SVG: When a drawing has two default Circles, they are the same in the SVG"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        Drawing drawing = new Drawing()
        drawing.add(circle1)
        drawing.add(circle2)
        String svg = drawing.getSVG(200, 200)

        then:
        svg == "<?xml version=\"1.0\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"200\" height=\"200\"><circle r=\"100\" cx=\"100\" cy=\"100\" /><circle r=\"100\" cx=\"100\" cy=\"100\" /></svg>"
    }

    def "SVG: When a drawing has two default Circles, the SVG contains two circles"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        drawing.add(circle1)
        drawing.add(circle2)
        String svg = drawing.getSVG(100, 100)
        int count = StringUtils.countMatches(svg, "circle")

        then:
        count == 2
    }

    def "SVG: When a drawing has a wide canvas, a circle fills its height"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle = new Circle()
        drawing.add(circle)
        String svg = drawing.getSVG(200, 100)

        then:
        svg.contains("circle r=\"50\"")
    }

    def "SVG: When a drawing has a tall canvas, a circle fills its width"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle = new Circle()
        drawing.add(circle)
        String svg = drawing.getSVG(100, 200)

        then:
        svg.contains("circle r=\"50\"")
    }



    def "SVG: When a square (200) drawing has two adjacent Circles, then the SVG is correct"() {
        when:
        Drawing drawing = new Drawing();
        Circle circle1 = new Circle();
        drawing.add(circle1);
        Circle circle2 = new Circle();
        drawing.add(circle2);
        circle2.setRightOf(circle1);
        String svg = drawing.getSVG(200, 200);

        then:
        svg.contains("<circle r=\"50\" cx=\"50\" cy=\"100\" />")
        svg.contains("<circle r=\"50\" cx=\"150\" cy=\"100\" />")
    }

    def "SVG: When a wide drawing has two adjacent circles, they are placed accordingly in the SVG"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle2.setRightOf(circle1)
        Drawing drawing = new Drawing()
        drawing.add(circle1)
        drawing.add(circle2)
        String svg = drawing.getSVG(200, 100)

        then:
        svg.contains("circle r=\"50\" cx=\"50\" cy=\"50\"")
        svg.contains("circle r=\"50\" cx=\"150\" cy=\"50\"")
    }

    def "SVG: When you get SVG twice from a Drawing, it is the same both times"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        drawing.add(circle1)
        drawing.add(circle2)
        String svg1 = drawing.getSVG(100, 100)
        String svg2 = drawing.getSVG(100, 100)

        then:
        svg1 == svg2
    }

    def "SVG - WIDTH: When you add two adjacent Circles to a drawing, the drawing is still twice as wide after you get the SVG"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle2.setRightOf(circle1)
        drawing.add(circle1)
        drawing.add(circle2)

        then:
        drawing.getImplicitWidth() == SisuBigDecimal.TWO;

        when:
        String svg = drawing.getSVG(100, 100)

        then:
        drawing.getImplicitWidth() == SisuBigDecimal.TWO;
    }

    def "SVG - WIDTH: When you add two default Circles to a drawing, the drawing is still the same width after you get the SVG"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        drawing.add(circle1)
        drawing.add(circle2)

        then:
        drawing.getImplicitWidth() == SisuBigDecimal.ONE;

        when:
        String svg = drawing.getSVG(100, 100)
        int count = StringUtils.countMatches(svg, "circle")

        then:
        drawing.getImplicitWidth() == SisuBigDecimal.ONE;
    }

}

class DrawingTestWidthExplicit extends Specification {

    def "WIDTH - EXPLICIT: You can set and get the explicit width of a Drawing"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle = new Circle()
        drawing.add(circle)
        drawing.setExplicitWidth(100)
        SisuBigDecimal explicitWidth = drawing.getExplicitWidth()

        then:
        explicitWidth == SisuBigDecimal.valueOf(100)
    }

    def "WIDTH - EXPLICIT: You can get the explicit width per implicit width for a drawing with one Circle"() {
        when:
        Circle circle = new Circle()
        Drawing drawing = new Drawing()
        drawing.add(circle)
        drawing.setExplicitWidth(100)
        drawing.setExplicitHeight(100)
        SisuBigDecimal explicitWidthPerObject = drawing.getExplicitWidthPerImplicitWidth();

        then:
        explicitWidthPerObject == SisuBigDecimal.valueOf(100)
    }

    def "WIDTH - EXPLICIT: You can get the explicit width per implicit width for a drawing with two adjacent Circles"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle2.setRightOf(circle1)
        Drawing drawing = new Drawing()
        drawing.add(circle1)
        drawing.add(circle2)
        drawing.setExplicitWidth(100)
        drawing.setExplicitHeight(100)
        SisuBigDecimal explicitWidthPerObject = drawing.getExplicitWidthPerImplicitWidth();

        then:
        explicitWidthPerObject == SisuBigDecimal.valueOf(50)
    }

    def "WIDTH - EXPLICIT: You can get the explicit width per implicit width for a drawing with two adjacent Circles (other order)"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle1.setRightOf(circle2)
        Drawing drawing = new Drawing()
        drawing.add(circle1)
        drawing.add(circle2)
        drawing.setExplicitWidth(100)
        drawing.setExplicitHeight(100)
        SisuBigDecimal explicitWidthPerObject = drawing.getExplicitWidthPerImplicitWidth();

        then:
        explicitWidthPerObject == SisuBigDecimal.valueOf(50)
    }
}

class DrawingTestWidthImplicit extends Specification {



    def "WIDTH - IMPLICIT: When you add two horizontally adjacent Circles to a drawing, the drawing is twice as wide"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle1.setRightOf(circle2)
        drawing.add(circle1)
        drawing.add(circle2)

        then:
        drawing.getImplicitWidth() == SisuBigDecimal.valueOf(2)
    }

    def "WIDTH - IMPLICIT: When you add two horizontally adjacent Circles to a drawing (in the other order), the drawing is twice as wide"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle2.setRightOf(circle1)
        drawing.add(circle1)
        drawing.add(circle2)

        then:
        drawing.getImplicitWidth() == SisuBigDecimal.valueOf(2)
    }

    def "WIDTH - IMPLICIT: When you add two default Circles to a drawing, the drawing stays the same width"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        drawing.add(circle1)
        drawing.add(circle2)

        then:
        drawing.getImplicitWidth() == SisuBigDecimal.ONE
    }

    def "WIDTH - IMPLICIT: When a drawing has one default Circle, the implicit width of the Circle is 1.0"() {
        when:
        Circle circle = new Circle()
        Drawing drawing = new Drawing()
        drawing.add(circle)
        SisuBigDecimal implicitWidth = circle.getImplicitWidth()

        then:
        implicitWidth == SisuBigDecimal.ONE
    }

    def "WIDTH - IMPLICIT: When a drawing has one default Circle, the implicit width of the drawing is 1.0"() {
        when:
        Circle circle = new Circle()
        Drawing drawing = new Drawing()
        drawing.add(circle)
        SisuBigDecimal implicitWidth = drawing.getImplicitWidth()

        then:
        implicitWidth == SisuBigDecimal.ONE

    }

    def "WIDTH - IMPLICIT: When a drawing has one default Circle, the implicit width of the drawing's contents is 1.0"() {
        when:
        Circle circle = new Circle()
        Drawing drawing = new Drawing()
        drawing.add(circle)
        SisuBigDecimal implicitWidthOfContents = drawing.getImplicitWidthOfContents()

        then:
        implicitWidthOfContents == SisuBigDecimal.ONE
    }

    def "WIDTH - IMPLICIT: When a square (100) drawing has two adjacent Circles, then their implicit widths are correct"() {
        when:
        Drawing drawing = new Drawing();
        Circle circle1 = new Circle();
        drawing.add(circle1);
        Circle circle2 = new Circle();
        drawing.add(circle2);
        circle2.setRightOf(circle1);
        drawing.setExplicitHeight(100)
        drawing.setExplicitWidth(100)
        SisuBigDecimal implicitWidth1 = circle1.getImplicitWidth()
        SisuBigDecimal implicitWidth2 = circle2.getImplicitWidth()

        then:
        implicitWidth1 == SisuBigDecimal.ONE
        implicitWidth2 == SisuBigDecimal.ONE
    }

    def "WIDTH - IMPLICIT: You can get the implicit width of a drawing with two adjacent Circles"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle2.setRightOf(circle1)
        Drawing drawing = new Drawing()
        drawing.add(circle1)
        drawing.add(circle2)
        SisuBigDecimal implicitWidth = drawing.getImplicitWidth();

        then:
        implicitWidth == SisuBigDecimal.TWO
    }

    def "WIDTH - IMPLICIT: You can get the implicit width of a drawing with two adjacent objects (other order)"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle1.setRightOf(circle2)
        Drawing drawing = new Drawing()
        drawing.add(circle1)
        drawing.add(circle2)
        SisuBigDecimal implicitWidth = drawing.getImplicitWidth();

        then:
        implicitWidth == SisuBigDecimal.TWO
    }
}

class DrawingTestXPositionExplicit extends Specification {

}
