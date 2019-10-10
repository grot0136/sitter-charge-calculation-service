package com.calculator.charge.service;

import com.calculator.charge.model.Appointment;

public interface ChargeService {
    public abstract int calculateCharge(Appointment appointment);
}
