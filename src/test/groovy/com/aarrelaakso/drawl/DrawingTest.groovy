package com.aarrelaakso.drawl

import java.util.concurrent.ThreadLocalRandom

import spock.lang.Specification
import org.apache.commons.lang3.StringUtils
import com.google.common.math.DoubleMath



class DrawingTestOverall extends Specification {

    Double epsilon

    void setup() {
        epsilon = 0.00000001 // Tolerance for comparing Double values
    }

    void cleanup() {
    }

    def "LENGTH: Adding a circle to an empty drawing gives the drawing a length of 1"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle = new Circle()
        drawing.add(circle)
        then:
        drawing.length() == new Integer(1)
    }

    def "When you create a Drawing, its length is 0"() {
        when:
        Drawing drawing = new Drawing()

        then:
        drawing.length() == 0
    }

}

class DrawingTestRadius extends Specification {

    def "RADIUS - EXPLICIT: When a square (100) drawing has two adjacent Circles, then their explicit radii are correct"() {
        when:
        Drawing drawing = new Drawing();
        Circle circle1 = new Circle();
        drawing.add(circle1);
        Circle circle2 = new Circle();
        drawing.add(circle2);
        circle2.setRightOf(circle1);
        drawing.setExplicitHeight(100)
        drawing.setExplicitWidth(100)
        Double explicitRadius1 = circle1.getExplicitRadius()
        Double explicitRadius2 = circle2.getExplicitRadius()

        then:
        explicitRadius1.equals(Double.valueOf(25))
        explicitRadius2.equals(Double.valueOf(25))

    }

    def "RADIUS - IMPLICIT: When a square (100) drawing has two adjacent Circles, then their implicit radii are correct"() {
        when:
        Drawing drawing = new Drawing();
        Circle circle1 = new Circle();
        drawing.add(circle1);
        Circle circle2 = new Circle();
        drawing.add(circle2);
        circle2.setRightOf(circle1);
        drawing.setExplicitHeight(100)
        drawing.setExplicitWidth(100)
        Double implicitRadius1 = circle1.getImplicitRadius()
        Double implicitRadius2 = circle2.getImplicitRadius()

        then:
        implicitRadius1.equals(Double.valueOf(0.5))
        implicitRadius2.equals(Double.valueOf(0.5))
    }

}

class DrawingTestSVG extends Specification {

    def "SVG: Calling getSVG without parameters does not throw an exception"() {
        when:
        Drawing drawing = new Drawing()
        def svg = drawing.getSVG()
        then:
        def exception = notThrown(java.lang.NullPointerException)
    }

    def "SVG: If a default Circle is the only content of a drawing, then it is as large as possible"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle = new Circle()
        drawing.add(circle)
        def svg = drawing.getSVG(100, 100)
        then:
        svg.contains("circle r=\"50\"")
    }

    def "SVG: The SVG generated by a drawing contains the string 'svg'"() {
        when:
        Drawing drawing = new Drawing()
        def svg = drawing.getSVG(100, 100)

        then:
        svg.indexOf("svg") > -1
    }

    def "SVG: The SVG generated by a drawing contains the width and height"() {
        when:
        Drawing drawing = new Drawing()
        def svg = drawing.getSVG(100, 100)

        then:
        svg.indexOf("width=\"100\"") > -1
        svg.indexOf("height=\"100\"") > -1
    }

    def "SVG: The SVG generated by a drawing with a Circle contains the string 'circle'"() {
        when:
        Drawing drawing = new Drawing()
        int radius = 4000
        Circle circle = new Circle(radius);
        drawing.add(circle)
        then:
        def svg = drawing.getSVG(100, 100)
        svg.indexOf("circle") > -1
    }

    def "SVG: The SVG generated by a drawing with a Circle is exactly as expected"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle = new Circle()
        drawing.add(circle)
        String svg = drawing.getSVG(100, 100)

        then:
        svg == "<?xml version=\"1.0\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"100\" height=\"100\"><circle r=\"50\" cx=\"50\" cy=\"50\" /></svg>"
    }

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
        Double implicitWidthOfContents = drawing.getImplicitWidthOfContents()

        then:
        implicitWidthOfContents == 1.0
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

    def "SVG: When a square (100) drawing has two adjacent Circles, then the SVG is correct"() {
        when:
        Drawing drawing = new Drawing();
        Circle circle1 = new Circle();
        drawing.add(circle1);
        Circle circle2 = new Circle();
        drawing.add(circle2);
        circle2.setRightOf(circle1);
        String svg = drawing.getSVG(100, 100);

        then:
        svg.contains("<?xml version=\"1.0\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"100\" height=\"100\">")
        svg.contains("<circle r=\"25\" cx=\"25\" cy=\"50\" />")
        svg.contains("<circle r=\"25\" cx=\"75\" cy=\"50\" />")
        svg.contains("</svg>")
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
        drawing.getImplicitWidth() == new Double(2.0)

        when:
        String svg = drawing.getSVG(100, 100)

        then:
        drawing.getImplicitWidth() == new Double(2.0)
    }

    def "SVG - WIDTH: When you add two default Circles to a drawing, the drawing is still the same width after you get the SVG"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        drawing.add(circle1)
        drawing.add(circle2)

        then:
        drawing.getImplicitWidth() == new Double(1.0)

        when:
        String svg = drawing.getSVG(100, 100)
        int count = StringUtils.countMatches(svg, "circle")

        then:
        drawing.getImplicitWidth() == new Double(1.0)
    }

}

class DrawingTestWidthExplicit extends Specification {

    def "WIDTH - EXPLICIT: When a drawing has one default circle, the Circle's explicit width is the explicit width of the drawing"() {
        when:
        Circle circle = new Circle()
        Drawing drawing = new Drawing()
        drawing.add(circle)
        drawing.setExplicitWidth(100)
        drawing.setExplicitHeight(100)
        Integer circleExplicitWidth = circle.getExplicitWidth()

        then:
        circleExplicitWidth.equals(Integer.valueOf(100))
    }

    def "WIDTH - EXPLICIT: When a drawing has one default circle, the explicit width per object is the explicit width of the drawing"() {
        when:
        Circle circle = new Circle()
        Drawing drawing = new Drawing()
        drawing.add(circle)
        drawing.setExplicitWidth(100)
        drawing.setExplicitHeight(100)
        Integer widthPerObject = drawing.getExplicitWidthPerObject()

        then:
        widthPerObject == 100
    }

    def "WIDTH - EXPLICIT: When a square (100) drawing has two adjacent Circles, then their explicit widths are correct"() {
        when:
        Drawing drawing = new Drawing();
        Circle circle1 = new Circle();
        drawing.add(circle1);
        Circle circle2 = new Circle();
        drawing.add(circle2);
        circle2.setRightOf(circle1);
        drawing.setExplicitHeight(100)
        drawing.setExplicitWidth(100)
        Integer explicitWidth1 = circle1.getExplicitWidth()
        Integer explicitWidth2 = circle2.getExplicitWidth()

        then:
        explicitWidth1.equals(Integer.valueOf(50))
        explicitWidth2.equals(Integer.valueOf(50))
    }

    def "WIDTH - EXPLICIT: You can set and get the explicit width of a Drawing"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle = new Circle()
        drawing.add(circle)
        drawing.setExplicitWidth(100)
        Integer explicitWidth = drawing.getExplicitWidth()

        then:
        explicitWidth == 100
    }

    def "WIDTH - EXPLICIT: You can get the explicit width per object for a drawing with one Circle"() {
        when:
        Circle circle = new Circle()
        Drawing drawing = new Drawing()
        drawing.add(circle)
        drawing.setExplicitWidth(100)
        drawing.setExplicitHeight(100)
        Integer explicitWidthPerObject = drawing.getExplicitWidthPerObject();

        then:
        explicitWidthPerObject == 100
    }

    def "WIDTH - EXPLICIT: You can get the explicit width per object for a drawing with two adjacent Circles"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle2.setRightOf(circle1)
        Drawing drawing = new Drawing()
        drawing.add(circle1)
        drawing.add(circle2)
        drawing.setExplicitWidth(100)
        drawing.setExplicitHeight(100)
        Integer explicitWidthPerObject = drawing.getExplicitWidthPerObject();

        then:
        explicitWidthPerObject == 50
    }

    def "WIDTH - EXPLICIT: You can get the explicit width per object for a drawing with two adjacent Circles (other order)"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle1.setRightOf(circle2)
        Drawing drawing = new Drawing()
        drawing.add(circle1)
        drawing.add(circle2)
        drawing.setExplicitWidth(100)
        drawing.setExplicitHeight(100)
        Integer explicitWidthPerObject = drawing.getExplicitWidthPerObject();

        then:
        explicitWidthPerObject == 50
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
        drawing.getImplicitWidth() == new BigDecimal(2.0)
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
        drawing.getImplicitWidth() == new BigDecimal(2.0)
    }

    def "WIDTH - IMPLICIT: When you add two default Circles to a drawing, the drawing stays the same width"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        drawing.add(circle1)
        drawing.add(circle2)

        then:
        drawing.getImplicitWidth() == new Double(1.0)
    }

    def "WIDTH - IMPLICIT: When a drawing has one default Circle, the implicit width of the Circle is 1.0"() {
        when:
        Circle circle = new Circle()
        Drawing drawing = new Drawing()
        drawing.add(circle)
        Double implicitWidth = circle.getImplicitWidth()

        then:
        implicitWidth.equals(Double.valueOf(1.0))
    }

    def "WIDTH - IMPLICIT: When a drawing has one default Circle, the implicit width of the drawing is 1.0"() {
        when:
        Circle circle = new Circle()
        Drawing drawing = new Drawing()
        drawing.add(circle)
        Double implicitWidth = drawing.getImplicitWidth()

        then:
        implicitWidth == 1.0
    }

    def "WIDTH - IMPLICIT: When a drawing has one default Circle, the implicit width of the drawing's contents is 1.0"() {
        when:
        Circle circle = new Circle()
        Drawing drawing = new Drawing()
        drawing.add(circle)
        Double implicitWidthOfContents = drawing.getImplicitWidthOfContents()

        then:
        implicitWidthOfContents == 1.0
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
        Double implicitWidth1 = circle1.getImplicitWidth()
        Double implicitWidth2 = circle2.getImplicitWidth()

        then:
        implicitWidth1.equals(Double.valueOf(1.0))
        implicitWidth2.equals(Double.valueOf(1.0))
    }

    def "WIDTH - IMPLICIT: You can get the implicit width of a drawing with two adjacent Circles"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle2.setRightOf(circle1)
        Drawing drawing = new Drawing()
        drawing.add(circle1)
        drawing.add(circle2)
        Double implicitWidth = new Double(drawing.getImplicitWidth());

        then:
        implicitWidth == 2.0
    }

    def "WIDTH - IMPLICIT: You can get the implicit width of a drawing with two adjacent objects (other order)"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle1.setRightOf(circle2)
        Drawing drawing = new Drawing()
        drawing.add(circle1)
        drawing.add(circle2)
        Double implicitWidth = new Double(drawing.getImplicitWidth());

        then:
        implicitWidth == 2.0
    }
}

class DrawingTestXPositionExplicit extends Specification {

    def "X-POSITION - EXPLICIT: When a drawing has two adjacent default Circles, then their implicit x-positions are correct (fixed)"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        drawing.add(circle1)
        drawing.add(circle2)
        circle2.setRightOf(circle1)

        then:
        circle1.getImplicitXPosition() == 0.0
        circle2.getImplicitXPosition() == 1.0
    }

    def "X-POSITION - EXPLICIT: When a drawing has three adjacent default circles added sequentially, then their explicit x-positions are correct (fixed)"() {
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
        drawing.setExplicitWidth(width)
        drawing.setExplicitHeight(height)
        BigDecimal bigWidth = BigDecimal.valueOf(width)
        BigDecimal bigX1 = bigWidth.divide(6 as BigDecimal, BigDecimalMath.mathContext)
        BigDecimal bigX2 = bigWidth.divide(2 as BigDecimal, BigDecimalMath.mathContext)
        BigDecimal fraction = BigDecimal.valueOf(5).divide(BigDecimal.valueOf(6), BigDecimalMath.mathContext)
        BigDecimal bigX3 = bigWidth.multiply(fraction, BigDecimalMath.mathContext)

        then:
        circle1.getExplicitXPosition().compareTo(bigX1) == 0
        circle2.getExplicitXPosition().compareTo(bigX2) == 0
        circle3.getExplicitXPosition().compareTo(bigX3) == 0
    }

    def "X-POSITION - EXPLICIT: When a drawing has three adjacent default Circles, then their explicit x-positions are correct (fixed)"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        Circle circle3 = new Circle()
        drawing.add(circle1)
        drawing.add(circle2)
        drawing.add(circle3)
        circle2.setRightOf(circle1)
        circle3.setRightOf(circle2)
        Integer width = 100
        Integer height = 100
        drawing.setExplicitWidth(width)
        drawing.setExplicitHeight(height)
        BigDecimal bigWidth = BigDecimal.valueOf(width)
        BigDecimal bigX1 = bigWidth.divide(6 as BigDecimal, BigDecimalMath.mathContext)
        BigDecimal bigX2 = bigWidth.divide(2 as BigDecimal, BigDecimalMath.mathContext)
        BigDecimal fraction = BigDecimal.valueOf(5).divide(BigDecimal.valueOf(6), BigDecimalMath.mathContext)
        BigDecimal bigX3 = bigWidth.multiply(fraction, BigDecimalMath.mathContext)

        then:
        circle1.getExplicitXPosition().compareTo(bigX1) == 0
        circle2.getExplicitXPosition().compareTo(bigX2) == 0
        circle3.getExplicitXPosition().compareTo(bigX3) == 0
    }

    def "X-POSITION - EXPLICIT: When a drawing has three adjacent default Circles, then their explicit x-positions are correct (max size)"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        Circle circle3 = new Circle()
        drawing.add(circle1)
        drawing.add(circle2)
        drawing.add(circle3)
        circle2.setRightOf(circle1)
        circle3.setRightOf(circle2)
        Float width = Float.MAX_VALUE
        System.out.println("width: " + width)
        Float height = Float.MAX_VALUE
        System.out.println("height: " + height)
        drawing.setExplicitWidth(width)
        drawing.setExplicitHeight(height)
        BigDecimal bigX1 = BigDecimal.valueOf(width).divide(6 as BigDecimal, BigDecimalMath.mathContext)
        BigDecimal bigX2 = BigDecimal.valueOf(width).divide(2 as BigDecimal, BigDecimalMath.mathContext)
        BigDecimal fraction = BigDecimal.valueOf(5).divide(BigDecimal.valueOf(6), BigDecimalMath.mathContext)
        BigDecimal bigX3 = BigDecimal.valueOf(width).multiply(fraction, BigDecimalMath.mathContext)
        float bigX1f = bigX1.floatValue()
        float bigX2f = bigX2.floatValue()
        float bigX3f = bigX3.floatValue()

        then:
        bigX1f <= Float.MAX_VALUE
        bigX2f <= Float.MAX_VALUE
        bigX3f <= Float.MAX_VALUE
        circle1.getExplicitXPosition().floatValue() == bigX1f
        circle2.getExplicitXPosition().floatValue() == bigX2f
        circle3.getExplicitXPosition().floatValue() == bigX3f

    }

    def "X-POSITION - EXPLICIT: When a drawing has three adjacent default Circles, then their x-positions are correct (random)"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        Circle circle3 = new Circle()
        drawing.add(circle1)
        drawing.add(circle2)
        drawing.add(circle3)
        circle2.setRightOf(circle1)
        circle3.setRightOf(circle2)
        Float width = ThreadLocalRandom.current().nextDouble(0, Float.MAX_VALUE)
        System.out.println("width: " + width)
        Float height = ThreadLocalRandom.current().nextDouble(0, Float.MAX_VALUE);
        System.out.println("height: " + height)
        drawing.setExplicitWidth(width)
        drawing.setExplicitHeight(height)
        BigDecimal bigWidth = BigDecimal.valueOf(width)
        BigDecimal bigX1 = bigWidth.divide(6 as BigDecimal, BigDecimalMath.mathContext)
        BigDecimal bigX2 = bigWidth.divide(2 as BigDecimal, BigDecimalMath.mathContext)
        BigDecimal fraction = BigDecimal.valueOf(5).divide(BigDecimal.valueOf(6), BigDecimalMath.mathContext)
        BigDecimal bigX3 = bigWidth.multiply(fraction, BigDecimalMath.mathContext)
        float bigX1f = bigX1.floatValue()
        float bigX2f = bigX2.floatValue()
        float bigX3f = bigX3.floatValue()

        then:
        circle1.getExplicitXPosition().floatValue() == bigX1f
        circle2.getExplicitXPosition().floatValue() == bigX2f
        circle3.getExplicitXPosition().floatValue() == bigX3f

    }

    def "X-POSITION - EXPLICIT: When a square (100) drawing has two adjacent Circles, then their explicit x positions are correct"() {
        when:
        Drawing drawing = new Drawing();
        Circle circle1 = new Circle();
        drawing.add(circle1);
        Circle circle2 = new Circle();
        drawing.add(circle2);
        circle2.setRightOf(circle1);
        drawing.setExplicitHeight(100)
        drawing.setExplicitWidth(100)
        Integer explicitXPosition1 = circle1.getExplicitXPosition()
        Integer explicitXPosition2 = circle2.getExplicitXPosition()

        then:
        explicitXPosition1.equals(Integer.valueOf(25))
        explicitXPosition2.equals(Integer.valueOf(75))
    }

}

class DrawingTestXPositionImplicit extends Specification {

    def "X-POSITION - IMPLICIT: When a drawing has two adjacent default Circles, then their explicit x-positions are correct (fixed)"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        drawing.add(circle1)
        drawing.add(circle2)
        circle2.setRightOf(circle1)
        int width = 100
        int height = 100
        drawing.setExplicitWidth(width)
        drawing.setExplicitHeight(height)

        then:
        circle1.getExplicitXPosition().compareTo(BigDecimal.valueOf(width / 4)) == 0
        circle2.getExplicitXPosition().compareTo(BigDecimal.valueOf((3 * width) / 4)) == 0
    }

}

class DrawingTestYPositionExplicit extends Specification {

    def "Y-POSITION - EXPLICIT: When a square (100) drawing has a default Circle, then its explicit y position is correct"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle = new Circle()
        drawing.add(circle)
        drawing.setExplicitDimensions(100,100)
        BigDecimal yPosition = circle.getExplicitYPosition()

        then:
        yPosition == BigDecimal.valueOf(50)
    }

    def "Y-POSITION - EXPLICIT: When a square (100) drawing has two adjacent Circles, then their explicit y positions are correct"() {
        when:
        Drawing drawing = new Drawing();
        Circle circle1 = new Circle();
        drawing.add(circle1);
        Circle circle2 = new Circle();
        drawing.add(circle2);
        circle2.setRightOf(circle1);
        drawing.setExplicitHeight(100)
        drawing.setExplicitWidth(100)
        Integer explicitYPosition1 = circle1.getExplicitYPosition()

        then:
        explicitYPosition1 == Integer.valueOf(50)

        when:
        Integer explicitYPosition2 = circle2.getExplicitYPosition()

        then:
        explicitYPosition2 == Integer.valueOf(50)
    }

    def "Y-POSITION - EXPLICIT: When a square (100) drawing has two adjacent Circles, then their explicit y positions are correct (with setExplicitDimensions)"() {
        when:
        Drawing drawing = new Drawing()
        Circle circle1 = new Circle()
        drawing.add(circle1)
        Circle circle2 = new Circle()
        drawing.add(circle2)
        circle2.setRightOf(circle1)
        drawing.setExplicitDimensions(100, 100)
        Integer explicitYPosition1 = circle1.getExplicitYPosition()

        then:
        explicitYPosition1 == Integer.valueOf(50)

        when:
        Integer explicitYPosition2 = circle2.getExplicitYPosition()

        then:
        explicitYPosition2 == Integer.valueOf(50)
    }

}
