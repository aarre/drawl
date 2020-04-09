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

    def "Measurement.getFixedValue() throws an error if its value is undefined"() {
        when:
        Measurement measurement = new Measurement()
        int value = measurement.getFixedValue()

        then:
        def exception = thrown(java.lang.UnsupportedOperationException)
    }


    def "You can create a Measurement with a fixed value"() {
        when:
        Measurement measurement = new Measurement(100)

        then:
        measurement.getFixedValue() == 100
        measurement.getImplicitValue() == 1.0
    }

    def "You can create a Measurement with an implicit value"() {
        when:
        Measurement measurement = new Measurement()

        then:
        measurement != null
    }

    def "You can set a Measurement to a fixed value"() {
        when:
        Measurement measurement = new Measurement()
        measurement.setFixedValue(100)

        then:
        measurement.getFixedValue() == 100
        noExceptionThrown()
    }

    def "You can set a Measurement to an implicit value"() {
        when:
        Measurement measurement = new Measurement()
        measurement.setImplicitValue(3.14)

        then:
        measurement.getImplicitValue() == 3.14
        noExceptionThrown()
    }

    def "The implicit value of a new Measurement is 1.0"() {
        when:
        Measurement measurement = new Measurement()

        then:
        measurement.getImplicitValue() == new Double(1.0)
    }

}
