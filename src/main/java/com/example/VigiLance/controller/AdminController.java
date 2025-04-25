package com.example.VigiLance.controller;

import com.example.VigiLance.entity.Report;
import com.example.VigiLance.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final ReportService reportService;

    public AdminController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports")
    public ResponseEntity<List<Report>> getAllReports() {
        // Ajouter une méthode dans ReportService pour lister les signalements
        List<Report> reports = reportService.findAllReports();
        return ResponseEntity.ok(reports);
    }
}