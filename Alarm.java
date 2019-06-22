package com.example.licenta;

import java.io.Serializable;

public class Alarm implements Serializable {

    private long id;
    private String alarmLabel;
    private String generatingEntity;
    private String generatingValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlarmLabel() {
        return alarmLabel;
    }

    public void setAlarmLabel(String alarmLabel) {
        this.alarmLabel = alarmLabel;
    }

    public String getGeneratingEntity() {
        return generatingEntity;
    }

    public void setGeneratingEntity(String generatingEntity) {
        this.generatingEntity = generatingEntity;
    }

    public String getGeneratingValue() {
        return generatingValue;
    }

    public void setGeneratingValue(String generatingValue) {
        this.generatingValue = generatingValue;
    }
}
