package com.calculator.charge.controller;

import com.calculator.charge.ChargeCalculatorApplication;
import com.calculator.charge.service.ChargeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                classes = {ChargeCalculatorApplication.class, ChargeController.class, ChargeService.class})
public class ChargeGetControllerTests {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    private ChargeController chargeController;

    @Test
    public void getExampleTest() {
        String body = this.testRestTemplate.getForObject("/chargeform", String.class);
        System.out.println("BODY INCOMING!!!");
        System.out.println(body);
        assert(body.contains("Nightly Charge Calculator"));
    }




}
