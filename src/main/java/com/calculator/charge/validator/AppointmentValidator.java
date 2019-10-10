package com.calculator.charge.validator;

import com.calculator.charge.model.Appointment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalTime;

@Component
public class AppointmentValidator implements Validator {

    //objects that can be validated by this validator
    @Override
    public boolean supports(Class clazz) {
        return Appointment.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Appointment appointment = (Appointment) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "beginTime", "error.beginTime");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endTime", "error.endTime");

        //make sure start time is after 5:00PM
        if(!errors.hasFieldErrors("beginTime") &&
                appointment.getBeginTime().isBefore(LocalTime.parse("17:00:00")) &&
                appointment.getBeginTime().isAfter(LocalTime.parse("04:00:00"))
        ) {
            errors.rejectValue("beginTime", "invalidRange");
        }
        boolean isEndTimeInvalid = false;
        if(!errors.hasErrors()) {
            if(appointment.getEndTime().isAfter(LocalTime.parse("04:00:00"))) {
                if(appointment.getBeginTime().isAfter(LocalTime.parse("04:00:00"))) {
                    //if start and end time fall between 5PM-midnight, fail if begin>=end
                    if(appointment.getEndTime().isBefore(appointment.getBeginTime())) {
                        isEndTimeInvalid = true;
                    }
                }

                //if end is in PM but start is in AM, fail
                else {
                    isEndTimeInvalid = true;
                }
            }
            else {
                //else if(appointment.getEndTime().isBefore(LocalTime.parse("04:00:00"))) {
                if (appointment.getBeginTime().isBefore(LocalTime.parse("17:00:00"))) {
                    //if start and end in AM, fail when begin>=end
                    if (appointment.getEndTime().isBefore(appointment.getBeginTime())) {
                        isEndTimeInvalid = true;
                    }
                }
            }
        }
        if(isEndTimeInvalid) {
            errors.rejectValue("endTime", "invalidRange");
        }
        boolean isBedTimeInvalid = false;
        if(!errors.hasErrors() && appointment.getBedTime()!=null) {
            if(appointment.getBeginTime().equals(appointment.getEndTime())) {
                //if start and end are same time, fail if bed time is not also the same
                if(!appointment.getBeginTime().equals(appointment.getBedTime())) {
                    isBedTimeInvalid = true;
                }
            }
            if(appointment.getBeginTime().isBefore(appointment.getEndTime())) {
                //if begin and end are in same time of day but bed time is not in between those times, fail
                if(appointment.getBedTime().isAfter(appointment.getEndTime()) ||
                        appointment.getBedTime().isBefore(appointment.getBeginTime())) {
                    isBedTimeInvalid = true;
                }
            }
            else if(appointment.getBedTime().isBefore(appointment.getBeginTime())) {
                //if begin time is in PM but end time is in AM, fail when bed time is not in between
                if(appointment.getBedTime().isAfter(appointment.getEndTime())) {
                    isBedTimeInvalid = true;
                }
            }
        }

        if(isBedTimeInvalid) {
            errors.rejectValue("bedTime", "invalidRange");
        }
    }
}
