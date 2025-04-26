package com.example.VigiLance.service;

import com.example.VigiLance.entity.Comment;
import com.example.VigiLance.entity.Report;
import com.example.VigiLance.repository.CommentRepository;
import com.example.VigiLance.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final CommentRepository commentRepository;

    public ReportService(ReportRepository reportRepository, CommentRepository commentRepository) {
        this.reportRepository = reportRepository;
        this.commentRepository = commentRepository;
    }

    // CRUD pour Report
    public Report saveReport(Report report) {
        report.setReportedAt(LocalDateTime.now());
        return reportRepository.save(report);
    }

    public List<Report> findAllReports() {
        return reportRepository.findAll();
    }

    public Report findReportById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Signalement non trouvé avec l'ID : " + id));
    }

    public Report updateReport(Long id, Report updatedReport) {
        Report report = findReportById(id);
        report.setDescription(updatedReport.getDescription());
        report.setLocation(updatedReport.getLocation());
        return reportRepository.save(report);
    }

    public void deleteReport(Long id) {
        Report report = findReportById(id);
        reportRepository.delete(report);
    }

    // CRUD pour Comment
    public Comment addComment(Long reportId, Comment comment) {
        Report report = findReportById(reportId);
        comment.setReport(report);
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public List<Comment> findCommentsByReportId(Long reportId) {
        return commentRepository.findByReportId(reportId);
    }
}