package com.example.VigiLance.controller;


import com.example.VigiLance.dto.CommentDTO;
import com.example.VigiLance.dto.ReportDTO;
import com.example.VigiLance.entity.User;
import com.example.VigiLance.service.ReportService;
import com.example.VigiLance.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(summary = "Créer un signalement", description = "Crée un nouveau signalement, avec ou sans authentification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Signalement créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping
    public ReportDTO createReport(@RequestBody ReportDTO reportDTO) {
        return reportService.saveReport(reportDTO);
    }

    @Operation(summary = "Obtenir tous les signalements", description = "Récupère la liste de tous les signalements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des signalements"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public List<ReportDTO> getAllReports() {
        return reportService.findAllReports();
    }

    @Operation(summary = "Obtenir un signalement par ID", description = "Récupère les détails d'un signalement spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Détails du signalement"),
            @ApiResponse(responseCode = "404", description = "Signalement non trouvé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ReportDTO getReportById(@PathVariable Long id) {
        return reportService.findReportById(id);
    }

    @Operation(summary = "Mettre à jour un signalement", description = "Met à jour les détails d'un signalement spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Signalement mis à jour"),
            @ApiResponse(responseCode = "404", description = "Signalement non trouvé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ReportDTO updateReport(@PathVariable Long id, @RequestBody ReportDTO reportDTO) {
        return reportService.updateReport(id, reportDTO);
    }

    @Operation(summary = "Supprimer un signalement", description = "Supprime un signalement spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Signalement supprimé"),
            @ApiResponse(responseCode = "404", description = "Signalement non trouvé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
    }

    @Operation(summary = "Ajouter un commentaire à un signalement", description = "Ajoute un commentaire à un signalement spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commentaire ajouté avec succès"),
            @ApiResponse(responseCode = "404", description = "Signalement non trouvé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{reportId}/comments")
    public CommentDTO addComment(@PathVariable Long reportId, @RequestBody CommentDTO commentDTO) {
        return reportService.addComment(reportId, commentDTO);
    }

    @Operation(summary = "Obtenir les commentaires d'un signalement", description = "Récupère la liste des commentaires d'un signalement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des commentaires"),
            @ApiResponse(responseCode = "404", description = "Signalement non trouvé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{reportId}/comments")
    public List<CommentDTO> getComments(@PathVariable Long reportId) {
        return reportService.findCommentsByReportId(reportId);
    }
}