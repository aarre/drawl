package com.aarrelaakso.drawl;

public class Measurement {

    private String constraintType;
    private Integer fixedValue;
    private Double implicitValue;

    /**
     * Create a measurement with an implicit value
     */
    public Measurement() {
        this.fixedValue = null;
        this.implicitValue = new Double(1.0);
        this.constraintType = "implicit";
    }

    /**
     * Create a measurement with a fixed value
     *
     * @param value The fixed value to which the measurement will be set
     */
    public Measurement(Integer value) {
        this.fixedValue = new Integer(value);
        this.implicitValue = new Double(1.0);
        this.constraintType = "fixed";
    }

    /**
     * Create a measurement with an implicit value
     *
     * @param value The implicit value to which the measurement will be set
     */
    public Measurement(Double value) {
        this.fixedValue = null;
        this.implicitValue = value;
        this.constraintType = "implicit";
    }


    public String getConstraintType() {
        return this.constraintType;
    }

    public Integer getFixedValue() {
        if (this.fixedValue == null) {
            throw new java.lang.UnsupportedOperationException("Cannot get the value of a Measurement when its value has not been defined");
        }
        return this.fixedValue;
    }

    public Double getImplicitValue() {
        return this.implicitValue;
    }

    public void setFixedValue(Integer value) {
        this.fixedValue = value;
    }

    public void setImplicitValue(Double value) {
        this.implicitValue = value;
    }

}
