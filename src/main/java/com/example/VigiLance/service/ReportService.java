package com.example.VigiLance.service;

import com.example.VigiLance.entity.Report;
import com.example.VigiLance.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final SmsService smsService;

    @Value("${admin.whatsapp.number}")
    private String adminWhatsAppNumber;

    // Remplacez ces valeurs par les nouveaux ContentSid de vos modèles
    private static final String USER_CONFIRMATION_SID = "HXacb3c56ab8bdea1bf6119a48d0faa545"; // À remplacer
    private static final String ADMIN_ALERT_SID = "HXda50615121bad13620324ffe0219ef4e"; // À remplacer

    public ReportService(ReportRepository reportRepository, SmsService smsService) {
        this.reportRepository = reportRepository;
        this.smsService = smsService;
    }

    public Report saveReport(Report report, String userPhoneNumber) {
        // Sauvegarder le signalement
        Report savedReport = reportRepository.save(report);

        // Envoyer une confirmation à l'utilisateur
        Map<String, String> userVariables = new HashMap<>();
        userVariables.put("1", savedReport.getReportedAt().toString());
        userVariables.put("2", "VigiLance"); // Note : Si le modèle n'a qu'une variable, ajustez ici
        smsService.sendWhatsAppMessage(userPhoneNumber, USER_CONFIRMATION_SID, userVariables);

        // Envoyer une alerte à l'admin
        Map<String, String> adminVariables = new HashMap<>();
        adminVariables.put("1", savedReport.getReportedAt().toString());
        adminVariables.put("2", savedReport.getDescription());
        smsService.sendWhatsAppMessage(adminWhatsAppNumber, ADMIN_ALERT_SID, adminVariables);

        return savedReport;
    }

    public Report saveReportFromSms(String smsContent, String phoneNumber) {
        Report report = new Report();
        report.setDescription(smsContent);
        report.setLocation("Received via SMS/WhatsApp from " + phoneNumber);
        return saveReport(report, phoneNumber);
    }

    public List<Report> findAllReports() {
        return reportRepository.findAll();
    }
}