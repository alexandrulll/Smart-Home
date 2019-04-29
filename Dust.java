package com.example.licenta;

import java.io.Serializable;

public class Dust implements Serializable {

    private long id;
    private float dustDesnsity;
    private String densityUnit;
    private float voltage;
    private String voltageUnit;
    private long timeStamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getDustDesnsity() {
        return dustDesnsity;
    }

    public void setDustDesnsity(float dustDesnsity) {
        this.dustDesnsity = dustDesnsity;
    }

    public String getDensityUnit() {
        return densityUnit;
    }

    public void setDensityUnit(String densityUnit) {
        this.densityUnit = densityUnit;
    }

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public String getVoltageUnit() {
        return voltageUnit;
    }

    public void setVoltageUnit(String voltageUnit) {
        this.voltageUnit = voltageUnit;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
