package com.aarrelaakso.drawl

import spock.lang.Specification

class MeasurementTest extends Specification {
    def "A Measurement with a fixed value returns constraint type 'fixed'"()
    {
        when:
        Measurement measurement = new Measurement(100)

        then:
        measurement.getConstraintType() == "fixed"
    }

    def "A Measurement with an implicit value returns constraint type 'implicit'"()
    {
        when:
        Measurement measurement = new Measurement()
        String constraintType = measurement.getConstraintType()

        then:
        constraintType == "implicit"
    }

    def "Measurement.getValue() throws an error if its value is undefined"() {
        when:
        Measurement measurement = new Measurement()
        int value = measurement.getValue()

        then:
        def exception = thrown(java.lang.UnsupportedOperationException)
    }


    def "You can create a Measurement with a fixed value"() {
        when:
        Measurement measurement = new Measurement(100)

        then:
        measurement.getValue() == 100
    }

    def "You can create a Measurement with an implicit value"() {
        when:
        Measurement measurement = new Measurement()

        then:
        measurement != null
    }

}
