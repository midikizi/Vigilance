package com.example.VigiLance.repository;

import com.example.VigiLance.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.report.id = :reportId")
    List<Comment> findByReportId(@Param("reportId") Long reportId);
}