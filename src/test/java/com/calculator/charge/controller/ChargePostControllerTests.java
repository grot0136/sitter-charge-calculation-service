package com.calculator.charge.controller;

import com.calculator.charge.model.Appointment;
import com.calculator.charge.service.ChargeService;
import com.calculator.charge.validator.AppointmentValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;

import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ChargeController.class)
public class ChargePostControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AppointmentValidator validator;

    @MockBean
    ChargeService chargeService;

    @Before
    public void setUp() {
        initMocks(this);
        ChargeController chargeController = new ChargeController(validator, chargeService);
        mockMvc = MockMvcBuilders.standaloneSetup(chargeController).build();
    }

    @Test
    public void chargeFormPostTest() throws Exception {

        Appointment appointment = new Appointment(
                LocalTime.parse("17:00:00"),
                LocalTime.parse("00:00:00"),
                LocalTime.parse("04:00:00")
                );


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/chargeform")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(appointment)))
                .andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}
