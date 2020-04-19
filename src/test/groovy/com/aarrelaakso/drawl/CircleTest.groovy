package com.aarrelaakso.drawl

import spock.lang.Specification
import sun.security.x509.OtherName

class CircleTest extends Specification {

    def "The implicit height of a Circle is 1.0 by default"() {
        when:
        Circle circle = new Circle()
        Double implicitHeight = circle.getImplicitHeight()

        then:
        implicitHeight.equals(Double.valueOf(1.0))
    }

    def "The implicit width of a Circle is 1.0 by default"() {
        when:
        Circle circle = new Circle()
        Double implicitWidth = circle.getImplicitWidth()

        then:
        implicitWidth == new Double(1.0)

    }

    def "The implicit width of a Circle's radius is 0.5 by default"() {
        when:
        Circle circle = new Circle()
        Double radiusImplicitWidth = circle.getImplicitRadius()

        then:
        radiusImplicitWidth == 0.5
    }

    def "The SVG generated by a Circle returns a string"() {
        when:
        Circle circle = new Circle()
        def svg = circle.getSVG()

        then:
        svg instanceof String
    }

    def "The SVG generated by a Circle contains 'circle'"() {
        when:
        int radius = 200
        Circle circle = new Circle()
        def svg = circle.getSVG()

        then:
        svg.indexOf("circle") > -1
    }

    def "The SVG generated by a Circle contains the radius"() {
        when:
        Circle circle = new Circle()
        def svg = circle.getSVG()

        then:
        svg.indexOf("r=\"0.5\"") > -1
    }

    def "The SVG generated by a Circle contains the x- and y-coordinates"() {
        given:
        int x = 50
        int y = 50
        Circle circle = new Circle();
        circle.setExplicitXPosition(x)
        circle.setExplicitYPosition(y)

        when:
        def svg = circle.getSVG()

        then:
        svg.indexOf("cx=\"50\"") > -1
        svg.indexOf("cy=\"50\"") > -1
    }

    def "When you create a default Circle, its explicit x-coordinate is 0"() {
        when:
        Circle circle = new Circle()
        int x = circle.getExplicitXPosition()

        then:
        x == 0
        x.toString() == "0"
    }

    def "When you create a default Circle, its explicit width is 1"() {
        when:
        Circle circle = new Circle()
        Double width = circle.getExplicitWidth()

        then:
        width == 1
    }

    def "When you create a default Circle, its explicit y-coordinate is 0"() {
        when:
        Circle circle = new Circle()
        Integer y = circle.getExplicitYPosition()

        then:
        y == 0
        y.toString() == "0"
    }

    def "When you create a default Circle, then its implicit radius is 0.5"() {
        when:
        Circle circle = new Circle()
        Double radius = circle.getImplicitRadius()

        then:
        radius == Double.valueOf(0.5)
    }

    def "When you create a default Circle, its implicit width is 1.0"() {
        when:
        Circle circle = new Circle()
        Double width = circle.getImplicitWidth()

        then:
        width == Double.valueOf(1.0)
    }

    def "When you create a default Circle, its implicit x-coordinate is 0.0"() {
        when:
        Circle circle = new Circle()
        Double x = circle.getImplicitXPosition()

        then:
        x.equals(Double.valueOf(0.0))
    }

    def "When you set a Circle above a default Circle, its implicit y-coordinate is 1"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle1.setAbove(circle2);
        Double y1 = circle1.getImplicitYPosition()
        Double y2 = circle2.getImplicitYPosition()

        then:
        y1 == Double.valueOf(1.0)
        y2 == Double.valueOf(0.0)
    }

    def "When you set a Circle below a default Circle, its implicit y-coordinate is -1"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle1.setBelow(circle2);
        Double y1 = circle1.getImplicitYPosition()
        Double y2 = circle2.getImplicitYPosition()

        then:
        y1 == Double.valueOf(-1.0)
        y2 == Double.valueOf(0.0)
    }

    def "When you set a Circle leftOf a default Circle, its implicit x-coordinate is -1"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        Double x10 = circle1.getImplicitXPosition()
        Double x20 = circle2.getImplicitXPosition()
        circle1.setLeftOf(circle2);
        Double x11 = circle1.getImplicitXPosition()
        Double x21 = circle2.getImplicitXPosition()

        then:
        x10.equals(Double.valueOf(0.0))
        x20.equals(Double.valueOf(0.0))
        x11.equals(Double.valueOf(-1.0))
        x21.equals(Double.valueOf(0.0))
    }

    def "When you set a Circle rightOf another Circle, its implicit x-coordinate is 1"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        Double x10 = circle1.getImplicitXPosition()
        Double x20 = circle2.getImplicitXPosition()
        circle1.setRightOf(circle2);
        Double x11 = circle1.getImplicitXPosition()
        Double x21 = circle2.getImplicitXPosition()

        then:
        x10.equals(Double.valueOf(0.0))
        x20.equals(Double.valueOf(0.0))
        x11.equals(Double.valueOf(1.0))
        x21.equals(Double.valueOf(0.0))
    }

    def "When you set a Circle rightOf another circle, you can recall that information"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle1.setRightOf(circle2)

        then:
        circle1.getRightOf() == circle2
    }

    def "When you set the explicit width of a Circle, you get the same value back"() {
        when:
        Circle circle = new Circle()
        Random random = new Random()
        Integer width = random.nextInt(Integer.MAX_VALUE.intValue())
        circle.setExplicitWidth(width)
        Integer newWidth = circle.getExplicitWidth()

        then:
        newWidth == width
    }

    def "When you set the explicit width of a Circle to 137930687, you get the same value back"() {
        when:
        Circle circle = new Circle()
        Random random = new Random()
        Integer width = Integer.valueOf(137930687)
        circle.setExplicitWidth(width)
        Integer newWidth = circle.getExplicitWidth()

        then:
        newWidth == width
        // newWidth has come back as 137930686 instead of 137930687
    }

    def "When you set the explicit width of a Circle to 175332073, you get the same value back"() {
        when:
        Circle circle = new Circle()
        Random random = new Random()
        Integer width = Integer.valueOf(175332073)
        circle.setExplicitWidth(width)
        Integer newWidth = circle.getExplicitWidth()

        then:
        newWidth == width
        // newWidth has come back as 175332072 instead of 175332073.
    }

    def "When you set the explicit width of a Circle to 64201805, you get the same value back"() {
        when:
        Circle circle = new Circle()
        Random random = new Random()
        Integer width = Integer.valueOf(64201805)
        circle.setExplicitWidth(width)
        Integer newWidth = circle.getExplicitWidth()

        then:
        newWidth == width
        // newWidth has come back as 64201804 instead of 64201805
    }

    def "When you set three default Circles adjacent to one another, no StackOverflowError is thrown"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        Circle circle3 = new Circle()
        circle2.setRightOf(circle1)
        circle3.setRightOf(circle2)

        then:
        notThrown(java.lang.StackOverflowError)
    }

    def "You can set the x-coordinate of a Circle and get it back"() {
        when:
        Circle circle = new Circle()
        circle.setExplicitXPosition(100)
        Integer x = circle.getExplicitXPosition()

        then:
        x == 100
        x.toString() == "100"
    }

    def "You can set the radius of a circle to be the same as the radius of another circle that has an implicit radius"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle(circle1.getImplicitRadius())

        then:
        circle1.getImplicitRadius() == circle2.getImplicitRadius()
    }

    def "You can set the radius of a circle to a fixed value"() {
        when:
        int radius = 100
        Circle circle = new Circle()

        then:
        circle.setExplicitRadius(radius)
    }

    def "You can construct a Circle with an implicit radius"() {
        when:
        Circle circle = new Circle()
        Double radius = circle.getImplicitRadius()

        then:
        radius == 0.5
    }

    def "You cannot create a circular adjacency"() {
        when:
        Circle circle = new Circle()
        circle.setRightOf(circle)

        then:
        thrown(UnsupportedOperationException)
    }
}
