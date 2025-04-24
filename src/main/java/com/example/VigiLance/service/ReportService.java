package com.example.VigiLance.service;

import com.example.VigiLance.entity.Report;
import com.example.VigiLance.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final SmsService smsService;

    public ReportService(ReportRepository reportRepository, SmsService smsService) {
        this.reportRepository = reportRepository;
        this.smsService = smsService;
    }

    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    public Report saveReportFromSms(String smsContent, String phoneNumber) {
        Report report = new Report();
        report.setDescription(smsContent);
        report.setLocation("Received via SMS/WhatsApp from " + phoneNumber);

        // Envoyer une confirmation WhatsApp avec Content Template
        Map<String, String> contentVariables = new HashMap<>();
        contentVariables.put("1", report.getReportedAt().toString()); // Exemple: date du signalement
        contentVariables.put("2", "VigiLance"); // Exemple: nom de l'application
        smsService.sendWhatsAppMessage(
                phoneNumber,
                "HXb5b62575e6e4ff6129ad7c8efe1f983e", // ContentSid de la requête cURL
                contentVariables
        );

        return reportRepository.save(report);
    }

    public List<Report> findAllReports() {
        return reportRepository.findAll();
    }
}