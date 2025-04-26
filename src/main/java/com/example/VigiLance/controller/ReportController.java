package com.example.VigiLance.controller;

import com.example.VigiLance.entity.Comment;
import com.example.VigiLance.entity.Report;
import com.example.VigiLance.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reports", description = "API pour gérer les signalements anonymes")
@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
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

    @Operation(summary = "Obtenir tous les signalements", description = "Récupère la liste de tous les signalements")
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.findAllReports());
    }

    @Operation(summary = "Obtenir un signalement par ID", description = "Récupère les détails d'un signalement spécifique")
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.findReportById(id));
    }

    @Operation(summary = "Mettre à jour un signalement", description = "Met à jour les informations d'un signalement")
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report report) {
        return ResponseEntity.ok(reportService.updateReport(id, report));
    }

    @Operation(summary = "Supprimer un signalement", description = "Supprime un signalement spécifique")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Ajouter un commentaire à un signalement", description = "Ajoute un commentaire à un signalement spécifique")
    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long id, @RequestBody Comment comment) {
        return ResponseEntity.ok(reportService.addComment(id, comment));
    }

    @Operation(summary = "Obtenir les commentaires d'un signalement", description = "Récupère tous les commentaires d'un signalement")
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getCommentsByReportId(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.findCommentsByReportId(id));
    }
}