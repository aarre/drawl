package com.aarrelaakso.drawl;

public class Measurement {

    private String constraintType;
    private Integer explicitValue;
    private Double implicitValue;

    /**
     * Create a measurement with an implicit value
     */
    public Measurement() {
        this.explicitValue = null;
        this.implicitValue = new Double(1.0);
        this.constraintType = "implicit";
    }

    /**
     * Create a measurement with a fixed value
     *
     * @param value The fixed value to which the measurement will be set
     */
    public Measurement(Integer value) {
        this.explicitValue = new Integer(value);
        this.implicitValue = new Double(1.0);
        this.constraintType = "fixed";
    }

    /**
     * Create a measurement with an implicit value
     *
     * @param value The implicit value to which the measurement will be set
     */
    public Measurement(Double value) {
        this.explicitValue = null;
        this.implicitValue = value;
        this.constraintType = "implicit";
    }


    public String getConstraintType() {
        return this.constraintType;
    }

    public Integer getExplicitValue() {
        return this.explicitValue;
    }

    public Double getImplicitValue() {
        return this.implicitValue;
    }

    public void setExplicitValue(Integer value) {
        this.explicitValue = value;
    }

    public void setImplicitValue(Double value) {
        this.implicitValue = value;
    }

}
