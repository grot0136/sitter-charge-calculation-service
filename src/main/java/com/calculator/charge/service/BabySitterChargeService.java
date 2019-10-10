package com.calculator.charge.service;

import com.calculator.charge.model.Appointment;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class BabySitterChargeService implements ChargeService {
    private static final int START_TO_BED_TIME_RATE = 12;
    private static final int BED_TO_MIDNIGHT_RATE = 8;
    private static final int MIDNIGHT_TO_END_TIME_RATE = 16;

    private static final int NUMBER_OF_HOURS_IN_A_DAY = 24;

    private static final String MIDNIGHT = "00:00:00";

    @Override
    public int calculateCharge(Appointment appointment) {
        int total=0;
        if(appointment==null) {
            return total;
        }
        LocalTime bedtime = (appointment.getBedTime()==null) ? appointment.getEndTime() : appointment.getBedTime();
        //add charge for start time to bed time
        total += calculateHourMultiplier(appointment.getBeginTime(), bedtime) * START_TO_BED_TIME_RATE;
        //if bedtime is before midnight
        if(bedtime.getHour()>(NUMBER_OF_HOURS_IN_A_DAY/2)) {
            //and end time is after midnight
            if(appointment.getEndTime().getHour()<(NUMBER_OF_HOURS_IN_A_DAY/2)) {
                //rate extends normally
                total += calculateHourMultiplier(bedtime, LocalTime.parse(MIDNIGHT)) * BED_TO_MIDNIGHT_RATE
                + calculateHourMultiplier(LocalTime.parse(MIDNIGHT), appointment.getEndTime()) * MIDNIGHT_TO_END_TIME_RATE;
            }
            else {
                //otherwise rate ends at end time
                total += calculateHourMultiplier(bedtime, appointment.getEndTime()) * BED_TO_MIDNIGHT_RATE;
            }
        }
        //otherwise charge remaining hours using bedtime as a starting time
        else{
            total+= calculateHourMultiplier(bedtime, appointment.getEndTime()) * MIDNIGHT_TO_END_TIME_RATE;
        }
        return total;
    }

    private int calculateHourMultiplier(LocalTime begin, LocalTime end) {
        //does a partial hour need to be subtracted
        int partialHour = (end.getMinute() < begin.getMinute()) ? 1 : 0;
        //if end time rolled over to next day
        if(begin.getHour()>end.getHour()) {
            return NUMBER_OF_HOURS_IN_A_DAY - begin.getHour() + end.getHour() - partialHour;
        }
        //if end time did not roll over to next day, simply subtract hours
        else if(begin.getHour()<end.getHour()) {
            return end.getHour() - begin.getHour() - partialHour;
        }
        //start and end within same hour span zero full hours
        else {
            return 0;
        }
    }

}
