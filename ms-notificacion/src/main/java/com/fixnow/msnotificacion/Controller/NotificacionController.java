package com.fixnow.msnotificacion.Controller;

import com.fixnow.msnotificacion.Model.Notificacion;
import com.fixnow.msnotificacion.Service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/notificaciones")
@Tag(name = "API Notificaciones", description = "API para el envío y gestión de notificaciones a clientes")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("")
    @Operation(summary = "Obtener todas las notificaciones", description = "Endpoint que permite consultar el historial completo de notificaciones enviadas")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa, se entrega la lista de notificaciones")
    @ApiResponse(responseCode = "204", description = "Consulta exitosa, pero no se encontraron notificaciones registradas")
    public ResponseEntity<List<Notificacion>> listar() {
        log.info("GET solicitado en /api/v1/notificaciones");
        List<Notificacion> lista = notificacionService.listarNotificaciones();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar notificación por ID", description = "Retorna un registro de notificación específico según su ID")
    @ApiResponse(responseCode = "200", description = "Notificación encontrada exitosamente")
    @ApiResponse(responseCode = "404", description = "No se encontró una notificación con el ID proporcionado")
    public ResponseEntity<Notificacion> buscarPorId(@Parameter(description = "ID de la notificación a consultar") @PathVariable Integer id) {
        log.info("GET solicitado en /api/v1/notificaciones/{}", id);
        Notificacion buscada = notificacionService.buscarPorId(id);
        if (buscada != null) {
            return new ResponseEntity<>(buscada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ticket/{idTicket}")
    @Operation(summary = "Buscar notificaciones por ID de Ticket", description = "Retorna todas las notificaciones enviadas correspondientes a un ticket específico")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa, se entrega la lista de notificaciones del ticket")
    @ApiResponse(responseCode = "204", description = "Consulta exitosa, pero el ticket no tiene notificaciones asociadas")
    public ResponseEntity<List<Notificacion>> buscarPorTicket(@Parameter(description = "ID del ticket para consultar sus notificaciones") @PathVariable Long idTicket) {
        log.info("GET solicitado en /api/v1/notificaciones/ticket/{}", idTicket);
        List<Notificacion> lista = notificacionService.buscarPorTicket(idTicket);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    @Operation(summary = "Enviar una nueva notificación", description = "Genera y envía una nueva notificación validando previamente los datos del ticket y la persona asociada")
    @ApiResponse(responseCode = "201", description = "Notificación generada y enviada con éxito")
    @ApiResponse(responseCode = "400", description = "Error: No se pudo enviar la notificación. Verifique la existencia del Ticket y la Persona")
    public ResponseEntity<String> enviar(@RequestBody @Valid Notificacion notificacion) {
        log.info("POST solicitado en /api/v1/notificaciones/");

        if (notificacionService.enviarNotificacion(notificacion)) {
            return new ResponseEntity<>("Notificación generada y enviada con éxito.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error: No se pudo enviar la notificación. Verifique la existencia del Ticket y la Persona.", HttpStatus.BAD_REQUEST);
        }
    }
}
