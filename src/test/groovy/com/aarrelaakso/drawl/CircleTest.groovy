package com.aarrelaakso.drawl

import spock.lang.Specification

class CircleTest extends Specification {

    def "Circle throws an error if SVG requested without a radius"() {
        when:
        Circle circle = new Circle()
        def svg = circle.getSVG()

        then:
        def exception = thrown(java.lang.UnsupportedOperationException)
    }

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
        Measurement radiusMeasurement = circle.getRadius()
        Double radiusImplicitWidth = radiusMeasurement.getImplicitValue()

        then:
        radiusImplicitWidth == 0.5
    }

    def "The SVG generated by a Circle returns a string"() {
        when:
        int radius = 40
        Circle circle = new Circle(radius)
        def svg = circle.getSVG()

        then:
        svg instanceof String
    }

    def "The SVG generated by a Circle contains 'circle'"() {
        when:
        int radius = 200
        Circle circle = new Circle(radius)
        def svg = circle.getSVG()

        then:
        svg.indexOf("circle") > -1
    }

    def "The SVG generated by a Circle contains the radius"() {
        when:
        int radius = 100
        Circle circle = new Circle(radius)
        def svg = circle.getSVG()

        then:
        svg.indexOf("r=\"100\"") > -1
    }

    def "The SVG generated by a Circle contains the x- and y-coordinates"() {
        given:
        int radius = 50
        int x = 50
        int y = 50
        Circle circle = new Circle(radius);
        circle.setX(x)
        circle.setY(y)

        when:
        def svg = circle.getSVG()

        then:
        svg.indexOf("cx=\"50\"") > -1
        svg.indexOf("cy=\"50\"") > -1
    }

    def "When you set a Circle rightOf another circle, you can recall that information"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle1.setRightOf(circle2)

        then:
        circle1.getRightOf() == circle2
    }

    def "When you set a Circle rightOf another circle, the other Circle is leftOf the original"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle()
        circle1.setRightOf(circle2)

        then:
        circle2.getLeftOf() == circle1
    }

    def "You can construct a circle with a given radius"() {
        when:
        int radius = 100;

        then:
        Circle circle = new Circle(radius)
    }

    def "You can get the radius of a Circle with a fixed radius"() {
        when:
        int radius = 100
        Circle circle = new Circle(radius)
        Measurement radiusMeasurement = circle.getRadius()
        int radiusValue = radiusMeasurement.getFixedValue()

        then:
        radiusValue == 100
    }

    def "You can get the radius of a circle with an implicit radius"() {
        when:
        Circle circle = new Circle()
        Measurement radius = circle.getRadius()

        then:
        radius != null
    }

    def "You can get the default x-coordinate of a Circle"() {
        when:
        Circle circle = new Circle()
        int x = circle.getX()

        then:
        x == 0
        x.toString() == "0"
    }

    def "You can get the default y-coordinate of a Circle"() {
        when:
        Circle circle = new Circle()
        Integer y = circle.getY()

        then:
        y == 0
        y.toString() == "0"
    }

    def "You can set the x-coordinate of a Circle and get it back"() {
        when:
        Circle circle = new Circle()
        circle.setX(100)
        Integer x = circle.getX()

        then:
        x == 100
        x.toString() == "100"
    }

    def "You can set the radius of a circle to be the same as the radius of another circle"() {
        when:
        Circle circle1 = new Circle(100)
        Measurement radius = circle1.getRadius()
        Integer radiusValue = radius.getFixedValue()
        Circle circle2 = new Circle(radiusValue)
        Measurement radius2 = circle2.getRadius()
        int radiusValue2 = radius2.getFixedValue()

        then:
        radiusValue2 == radiusValue
    }

    def "You can set the radius of a circle to be the same as the radius of another circle that has an implicit radius"() {
        when:
        Circle circle1 = new Circle()
        Circle circle2 = new Circle(circle1.getRadius())

        then:
        circle1.getRadius() == circle2.getRadius()
    }

    def "You can set the radius of a circle to a fixed value"() {
        when:
        int radius = 100
        Circle circle = new Circle()

        then:
        circle.setRadiusFixed(radius)
    }

    def "You can construct a Circle with an implicit radius"() {
        when:
        Circle circle = new Circle()
        Measurement radius = circle.getRadius()

        then:
        radius != null
    }

}
