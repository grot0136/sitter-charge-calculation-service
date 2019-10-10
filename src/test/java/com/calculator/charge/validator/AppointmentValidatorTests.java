package com.calculator.charge.validator;

import com.calculator.charge.model.Appointment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.xml.ws.RequestWrapper;
import java.time.LocalTime;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class AppointmentValidatorTests {

    @Autowired
    AppointmentValidator appointmentValidator;

    private Appointment appointment;

    private Errors errors;

    @Before
    public void setUp() {
        appointment = new Appointment();
        errors = new BeanPropertyBindingResult(appointment, "appointment");
    }

    @Test
    public void testAppointmentValidatorshouldBeSupported() {
        Assert.assertTrue(appointmentValidator.supports(Appointment.class));
    }

    @Test
    public void testBeginTime5PMBedTime4AMEndTime4AMshouldHaveNoErrors() {
        appointment.setBeginTime(LocalTime.parse("17:00:00"));
        appointment.setBedTime(LocalTime.parse("04:00:00"));
        appointment.setEndTime(LocalTime.parse("04:00:00"));

        appointmentValidator.validate(appointment, errors);

        Assert.assertFalse(errors.hasErrors());

    }

    @Test
    public void testNoBedTimeshouldHaveNoErrors() {
        appointment.setBeginTime(LocalTime.parse("17:00:00"));
        appointment.setEndTime(LocalTime.parse("22:00:00"));

        Assert.assertFalse(errors.hasFieldErrors());
    }

    @Test
    public void testAllArgsNullshouldHaveErrors() {
        appointmentValidator.validate(appointment, errors);
        Assert.assertTrue(errors.hasFieldErrors());
    }

    @Test
    public void testStartTimeOutOfRangeshouldHaveErrors() {
        appointment.setBeginTime(LocalTime.parse("09:00:00"));
        appointment.setEndTime(LocalTime.parse("03:00:00"));
        appointmentValidator.validate(appointment,errors);
    }

    @Test
    public void testBedTimeAfterEndTimeshouldHaveErrors() {
        appointment.setBeginTime(LocalTime.parse("17:00:00"));
        appointment.setEndTime(LocalTime.parse("22:00:00"));
        appointment.setBedTime(LocalTime.parse("00:00:00"));
        appointmentValidator.validate(appointment, errors);
        Assert.assertTrue(errors.hasFieldErrors());
    }

    @Test
    public void testEndAndStartEqualNotBedshouldHaveErrors() {
        appointment.setBeginTime(LocalTime.parse("17:00:00"));
        appointment.setEndTime(LocalTime.parse("17:00:00"));
        appointment.setBedTime(LocalTime.parse("00:00:00"));
        appointmentValidator.validate(appointment, errors);
        Assert.assertTrue(errors.hasFieldErrors());
    }

    @Test
    public void testEndAMStartPMBedNotInbetweenshouldHaveErrors() {
        appointment.setBeginTime(LocalTime.parse("17:00:00"));
        appointment.setEndTime(LocalTime.parse("00:00:00"));
        appointment.setBedTime(LocalTime.parse("06:00:00"));
        appointmentValidator.validate(appointment, errors);
        Assert.assertTrue(errors.hasFieldErrors());
    }

    @Test
    public void testEndTimeBeforeStartTimeshouldHaveErrors() {
        appointment.setBeginTime(LocalTime.parse("20:00:00"));
        appointment.setEndTime(LocalTime.parse("17:00:00"));
        appointmentValidator.validate(appointment, errors);
        Assert.assertTrue(errors.hasFieldErrors());
    }

    @Test
    public void testEndTimePMStartTimeAMshouldHaveErrors() {
        appointment.setBeginTime(LocalTime.parse("04:00:00"));
        appointment.setEndTime(LocalTime.parse("18:00:00"));
        appointmentValidator.validate(appointment, errors);
        Assert.assertTrue(errors.hasFieldErrors());
    }

    @Test
    public void testEndTimeAMBeforeStartTimeAMshouldHaveErrors() {
        appointment.setBeginTime(LocalTime.parse("04:00:00"));
        appointment.setEndTime(LocalTime.parse("01:00:00"));
        appointmentValidator.validate(appointment, errors);
        Assert.assertTrue(errors.hasFieldErrors());
    }



}
