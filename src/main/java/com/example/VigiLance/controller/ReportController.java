package com.example.VigiLance.controller;


import com.example.VigiLance.entity.Report;
import com.example.VigiLance.service.ReportService;
import com.example.VigiLance.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;
    private final SmsService smsService;

    public ReportController(ReportService reportService, SmsService smsService) {
        this.reportService = reportService;
        this.smsService = smsService;
    }

    @Operation(summary = "Créer un nouveau signalement", description = "Permet de soumettre un signalement anonyme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Signalement créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report savedReport = reportService.saveReport(report);
        return ResponseEntity.ok(savedReport);
    }

    @Operation(summary = "Lister tous les signalements", description = "Réservé aux administrateurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des signalements"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/admin")
    public ResponseEntity<List<Report>> listReports() {
        List<Report> reports = reportService.findAllReports();
        return ResponseEntity.ok(reports);
    }

    @Operation(summary = "Créer un signalement à partir d'un SMS", description = "Permet de soumettre un signalement via SMS ou WhatsApp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Signalement créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping("/sms")
    public ResponseEntity<Report> createReportFromSms(@RequestParam String smsContent, @RequestParam String phoneNumber) {
        Report savedReport = reportService.saveReportFromSms(smsContent, phoneNumber);
        return ResponseEntity.ok(savedReport);
    }

    @Operation(summary = "Envoyer un message WhatsApp", description = "Envoie un message WhatsApp avec un modèle de contenu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message envoyé avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping("/testWhatsApp")
    public ResponseEntity<String> testwhasap() {
        smsService.testWhatsApp();
        return ResponseEntity.ok("Message envoyé avec succès");
    }
}