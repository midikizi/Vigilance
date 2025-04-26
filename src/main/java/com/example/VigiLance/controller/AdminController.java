package com.example.VigiLance.controller;

import com.example.VigiLance.entity.Report;
import com.example.VigiLance.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = "Lister tous les signalements", description = "Réservé aux administrateurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des signalements"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/list-reports")
    public ResponseEntity<List<Report>> listReports() {
        List<Report> reports = reportService.findAllReports();
        return ResponseEntity.ok(reports);
    }
}