package com.calculator.charge.service;

import com.calculator.charge.model.Appointment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BabySitterChargeServiceTests {

    private Appointment appointment;

    @Autowired
    BabySitterChargeService chargeService;

    @Before
    public void setUp(){
        appointment = new Appointment();
    }

    @Test
    public void emptyAppointmentShouldReturn0Test() {
        appointment = null;
        Assert.assertEquals(0, chargeService.calculateCharge(appointment));
    }

    @Test
    public void beginTime5PMBedTime4AMEndTime4AMshouldReturn132Test() {
        appointment.setBeginTime(LocalTime.parse("17:00:00"));
        appointment.setBedTime(LocalTime.parse("04:00:00"));
        appointment.setEndTime(LocalTime.parse("04:00:00"));

        Assert.assertEquals(132, chargeService.calculateCharge(appointment));
    }

    @Test
    public void beginTime5PMBedTime5PMEndTime5PMshouldReturn0Test() {
        appointment.setBeginTime(LocalTime.parse("17:00:00"));
        appointment.setBedTime(LocalTime.parse("17:00:00"));
        appointment.setEndTime(LocalTime.parse("17:00:00"));

        Assert.assertEquals(0, chargeService.calculateCharge(appointment));
    }

    @Test
    public void startTime7PMbedTime10PMEndTime4AMshouldReturn116Test() {
        appointment.setBeginTime(LocalTime.parse("19:00:00"));
        appointment.setBedTime(LocalTime.parse("22:00:00"));
        appointment.setEndTime(LocalTime.parse("04:00:00"));

        Assert.assertEquals(116, chargeService.calculateCharge(appointment));
    }

    @Test
    public void startTime5PMbedTime2AMEndTime4AMshouldReturn116Test() {
        appointment.setBeginTime(LocalTime.parse("19:00:00"));
        appointment.setBedTime(LocalTime.parse("02:00:00"));
        appointment.setEndTime(LocalTime.parse("04:00:00"));

        Assert.assertEquals(116, chargeService.calculateCharge(appointment));
    }


}
