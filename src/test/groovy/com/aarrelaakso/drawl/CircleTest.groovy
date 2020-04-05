package com.aarrelaakso.drawl

import spock.lang.Specification

class CircleTest extends Specification {
    def "ToSVG"() {
        when:
            Circle circle = new Circle()

        then:
            println "ToSVG"
    }
}
