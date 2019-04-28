package com.example.licenta;

import java.io.Serializable;

public class Light implements Serializable {

    private long id;
    private double fullSpectrum;
    private double infraredSpectrum;
    private double visibleSpectrum;
    private long timeStamp;
    private String measureUnit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getFullSpectrum() {
        return fullSpectrum;
    }

    public void setFullSpectrum(double fullSpectrum) {
        this.fullSpectrum = fullSpectrum;
    }

    public double getInfraredSpectrum() {
        return infraredSpectrum;
    }

    public void setInfraredSpectrum(double infraredSpectrum) {
        this.infraredSpectrum = infraredSpectrum;
    }

    public double getVisibleSpectrum() {
        return visibleSpectrum;
    }

    public void setVisibleSpectrum(double visibleSpectrum) {
        this.visibleSpectrum = visibleSpectrum;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }
}
