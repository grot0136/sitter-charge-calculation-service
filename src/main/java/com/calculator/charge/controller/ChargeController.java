package com.calculator.charge.controller;

import com.calculator.charge.model.Appointment;
import com.calculator.charge.service.ChargeService;
import com.calculator.charge.validator.AppointmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ChargeController {

    @Autowired
    AppointmentValidator validator;

    @Autowired
    ChargeService chargeService;

    public ChargeController(AppointmentValidator validator, ChargeService chargeService) {
        this.validator = validator;
        this.chargeService = chargeService;
    }

    @GetMapping("/chargeform")
    public String loadSitterForm(Model model) {
        //add begin time, bed time, and end time
        model.addAttribute("appointment", new Appointment());
        return "chargeform";
    }

    @PostMapping("/chargeform")
    //public String processSitterForm(@Valid Appointment appointment, BindingResult result) {
    public String processSitterForm(@Valid @ModelAttribute("appointment") Appointment appointment, BindingResult result) {
        validator.validate(appointment, result);

        if(result.hasErrors()) {
            return "chargeform";
        }
        appointment.setChargeAmount(chargeService.calculateCharge(appointment));
        return "chargeconfirm";
    }
}
