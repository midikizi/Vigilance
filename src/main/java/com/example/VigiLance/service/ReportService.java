package com.example.VigiLance.service;


import com.example.VigiLance.dto.CommentDTO;
import com.example.VigiLance.dto.ReportDTO;
import com.example.VigiLance.entity.Comment;
import com.example.VigiLance.entity.Report;
import com.example.VigiLance.repository.CommentRepository;
import com.example.VigiLance.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final CommentRepository commentRepository;
    private final NotificationService notificationService;

    public ReportService(ReportRepository reportRepository, CommentRepository commentRepository, NotificationService notificationService) {
        this.reportRepository = reportRepository;
        this.commentRepository = commentRepository;
        this.notificationService = notificationService;
    }

    // Création d'un signalement sans userId ni token
    public ReportDTO saveReport(ReportDTO reportDTO) {
        Report report = new Report();
        report.setDescription(reportDTO.getDescription());
        report.setLocation(reportDTO.getLocation());
        // reportedAt est défini par @PrePersist
        report = reportRepository.save(report);

        // Créer une notification
        notificationService.createNotification("Nouveau signalement : " + report.getDescription());

        return mapToReportDTO(report);
    }


    public List<ReportDTO> findAllReports() {
        return reportRepository.findAll().stream()
                .map(this::mapToReportDTO)
                .collect(Collectors.toList());
    }

    public ReportDTO findReportById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Signalement non trouvé avec l'ID : " + id));
        return mapToReportDTO(report);
    }

    public ReportDTO updateReport(Long id, ReportDTO updatedReportDTO) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Signalement non trouvé avec l'ID : " + id));
        report.setDescription(updatedReportDTO.getDescription());
        report.setLocation(updatedReportDTO.getLocation());
        report = reportRepository.save(report);
        return mapToReportDTO(report);
    }

    public void deleteReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Signalement non trouvé avec l'ID : " + id));
        reportRepository.delete(report);
    }

    // CRUD pour Comment
    public CommentDTO addComment(Long reportId, CommentDTO commentDTO) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Signalement non trouvé avec l'ID : " + reportId));

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setAuthor(commentDTO.getAuthor());
        // createdAt est défini par @PrePersist
        comment.setReport(report);

        comment = commentRepository.save(comment);

        // Créer une notification
        notificationService.createNotification("Nouveau commentaire sur le signalement #" + reportId + " : " + comment.getContent());

        return mapToCommentDTO(comment);
    }

    public List<CommentDTO> findCommentsByReportId(Long reportId) {
        return commentRepository.findByReportId(reportId).stream()
                .map(this::mapToCommentDTO)
                .collect(Collectors.toList());
    }

    // Méthodes de mapping
    private ReportDTO mapToReportDTO(Report report) {
        ReportDTO dto = new ReportDTO();
        dto.setDescription(report.getDescription());
        dto.setLocation(report.getLocation());
        dto.setReportedAt(report.getReportedAt());
        return dto;
    }

    private CommentDTO mapToCommentDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setContent(comment.getContent());
        dto.setAuthor(comment.getAuthor());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setReportId(comment.getReport().getId());
        return dto;
    }
}