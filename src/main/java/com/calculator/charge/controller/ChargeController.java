package com.calculator.charge.controller;

import com.calculator.charge.model.Appointment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ChargeController {

    @GetMapping("/chargeform")
    public String loadSitterForm(Model model) {
        //add begin time, bed time, and end time
        model.addAttribute("appointment", new Appointment());
        return "chargeform";
    }

    @PostMapping("/chargeform")
    public String processSitterForm(@Valid Appointment appointment, BindingResult result) {
        if(result.hasErrors()) {
            return "chargeform";
        }
        return "chargeconfirm";
    }
}
