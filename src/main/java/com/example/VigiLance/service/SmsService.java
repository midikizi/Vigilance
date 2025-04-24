package com.example.VigiLance.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmsService {
    // Configuration de Twilio
    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    // Envoi de SMS standard
    public void sendSms(String to, String message) {
        try {
            Twilio.init(accountSid, authToken);
            Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(fromPhoneNumber),
                    message
            ).create();
        } catch (Exception e) {
            throw new RuntimeException("Échec de l'envoi du SMS: " + e.getMessage(), e);
        }
    }

    // Envoi de message WhatsApp avec Content Template
    public void sendWhatsAppMessage(String to, String contentSid, Map<String, String> contentVariables) {
        try {
            Twilio.init(accountSid, authToken);
            Message.creator(
                            new PhoneNumber("whatsapp:" + to), // Format WhatsApp
                            new PhoneNumber(fromPhoneNumber),  // Doit être un numéro WhatsApp
                            (String) null                               // Pas de corps de message, car on utilise ContentSid
                    )
                    .setContentSid(contentSid)
                    .setContentVariables(convertVariablesToJson(contentVariables))
                    .create();
        } catch (Exception e) {
            throw new RuntimeException("Échec de l'envoi du message WhatsApp: " + e.getMessage(), e);
        }
    }

    // Convertir les variables en JSON pour Twilio
    private String convertVariablesToJson(Map<String, String> variables) {
        StringBuilder json = new StringBuilder("{");
        int i = 0;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            json.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"");
            if (i < variables.size() - 1) {
                json.append(",");
            }
            i++;
        }
        json.append("}");
        return json.toString();
    }

    public void testWhatsApp() {
        Map<String, String> variables = new HashMap<>();
        variables.put("1", "12/1");
        variables.put("2", "3pm");
        sendWhatsAppMessage("+22891934408", "HXb5b62575e6e4ff6129ad7c8efe1f983e", variables);
    }
}