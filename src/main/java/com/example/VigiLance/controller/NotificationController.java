package com.example.VigiLance.controller;


import com.example.VigiLance.dto.NotificationDTO;
import com.example.VigiLance.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Operation(summary = "Obtenir les notifications non lues", description = "Récupère la liste des notifications non lues")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des notifications non lues"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/unread")
    public List<NotificationDTO> getUnreadNotifications() {
        return notificationService.getUnreadNotifications();
    }

    @Operation(summary = "Marquer une notification comme lue", description = "Marque une notification spécifique comme lue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification marquée comme lue"),
            @ApiResponse(responseCode = "404", description = "Notification non trouvée"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
    }
}