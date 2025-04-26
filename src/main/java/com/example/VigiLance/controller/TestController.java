package com.example.VigiLance.controller;

import com.example.VigiLance.service.SmsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private final SmsService smsService;

    public TestController(SmsService smsService) {
        this.smsService = smsService;
    }

    @GetMapping("/whatsapp")
    public String testWhatsApp() {
        smsService.testWhatsApp();
        return "Test WhatsApp envoyé";
    }
}