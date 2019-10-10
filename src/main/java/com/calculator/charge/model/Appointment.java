package com.calculator.charge.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;

public class Appointment {

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime beginTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime bedTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;

    private int chargeAmount;

    public Appointment(LocalTime beginTime, LocalTime bedTime, LocalTime endTime ) {
        this.beginTime = beginTime;
        this.bedTime = bedTime;
        this.endTime = endTime;
    }

    public Appointment() {
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalTime getBedTime() {
        return bedTime;
    }

    public void setBedTime(LocalTime bedTime) {
        this.bedTime = bedTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(int chargeAmount) {
        this.chargeAmount = chargeAmount;
    }
}
