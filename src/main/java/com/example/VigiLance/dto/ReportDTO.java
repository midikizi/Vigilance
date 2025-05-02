package com.example.VigiLance.dto;

import java.time.LocalDateTime;

public class ReportDTO {
    private String description;
    private String location;
    private LocalDateTime reportedAt;

    // Getters et setters

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getReportedAt() {
        return reportedAt;
    }

    public void setReportedAt(LocalDateTime reportedAt) {
        this.reportedAt = reportedAt;
    }

}