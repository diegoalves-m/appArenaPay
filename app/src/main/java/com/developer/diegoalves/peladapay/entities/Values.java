package com.developer.diegoalves.peladapay.entities;

/**
 * Created by Diego Alves on 25/11/2015.
 */
public class Values {

    public long id;
    public final Double all = 360.00;
    private Double current;
    private Double valueM;
    private Double valueP;

    public Values() {
    }

    public Values(long id, Double current, Double valueM, Double valueP) {
        this.id = id;
        this.current = current;
        this.valueM = valueM;
        this.valueP = valueP;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getAll() {
        return all;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    public Double getValueM() {
        return valueM;
    }

    public void setValueM(Double valueM) {
        this.valueM = valueM;
    }

    public Double getValueP() {
        return valueP;
    }

    public void setValueP(Double valueP) {
        this.valueP = valueP;
    }
}
