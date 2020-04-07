package com.aarrelaakso.drawl;

public class Measurement {

    private String constraintType;
    private Integer fixedValue;

    /**
     * Create a measurement with an implicit value.
     */
    public Measurement() {
        this.fixedValue = null;
        this.constraintType = "implicit";
    }

    /**
     * Create a measurement with a fixed value.
     *
     * @param value
     */
    public Measurement(int value) {
        this.fixedValue = new Integer(value);
        this.constraintType = "fixed";
    }

    public String getConstraintType() {
        return this.constraintType;
    }

    public int getValue() {
        if (this.fixedValue == null) {
            throw new java.lang.UnsupportedOperationException("Cannot get the value of a Measurement when its value has not been defined");
        }
        return this.fixedValue.intValue();
    }
}
