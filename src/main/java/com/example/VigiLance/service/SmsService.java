package com.example.VigiLance.service;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmsService {
    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @Value("${twilio.whatsapp.content.sid.admin}")
    private String contentSidAdmin;

    @Value("${twilio.whatsapp.content.sid.confirmation}")
    private String contentSidConfirmation;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    // Méthode pour envoyer un message WhatsApp simple
    public void sendWhatsAppMessage(String toPhoneNumber, String messageBody) {
        validatePhoneNumber(toPhoneNumber);
        try {
            Message message = Message.creator(
                            new PhoneNumber(toPhoneNumber),
                            new PhoneNumber(twilioPhoneNumber),
                            messageBody)
                    .create();

            System.out.println("Message envoyé avec SID : " + message.getSid());
        } catch (ApiException e) {
            throw new RuntimeException("Échec de l'envoi du message WhatsApp: " + e.getMessage(), e);
        }
    }

    // Méthode pour envoyer un message WhatsApp avec un Content Template
    public void sendWhatsAppMessage(String to, String templateType, Map<String, String> contentVariables) {
        String toNumber = to.startsWith("whatsapp:") ? to : "whatsapp:" + to;
        validatePhoneNumber(toNumber);

        String contentSid = templateType.equals("admin") ? contentSidAdmin : contentSidConfirmation;

        try {
            Message message = Message.creator(
                            new PhoneNumber(toNumber),
                            new PhoneNumber(twilioPhoneNumber),
                            (String) null)
                    .setContentSid(contentSid)
                    .setContentVariables(convertVariablesToJson(contentVariables))
                    .create();

            System.out.println("Message envoyé avec SID : " + message.getSid());
        } catch (ApiException e) {
            throw new RuntimeException("Échec de l'envoi du message WhatsApp: " + e.getMessage(), e);
        }
    }

    // Validation du numéro de téléphone
    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("^whatsapp:\\+[1-9][0-9]{1,14}$")) {
            throw new IllegalArgumentException("Le numéro de téléphone n'est pas au format WhatsApp valide : " + phoneNumber);
        }
    }

    // Convertir les variables en JSON pour Twilio
    private String convertVariablesToJson(Map<String, String> variables) {
        if (variables == null || variables.isEmpty()) {
            return "{}";
        }
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

    // Méthode de test
    public void testWhatsApp() {
        Map<String, String> variables = new HashMap<>();
        variables.put("1", "12/1");
        variables.put("2", "3pm");
        sendWhatsAppMessage("+22891934408", "admin", variables);
    }
}