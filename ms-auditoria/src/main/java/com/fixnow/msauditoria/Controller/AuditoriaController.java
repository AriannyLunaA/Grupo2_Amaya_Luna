package com.fixnow.msauditoria.Controller;

import com.fixnow.msauditoria.Model.Auditoria;
import com.fixnow.msauditoria.Service.AuditoriaService;
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
@RequestMapping("/api/v1/auditoria")
@Tag(name = "API Auditoría", description = "API para el registro y consulta de logs de eventos y acciones del sistema")
public class AuditoriaController {

    @Autowired
    private AuditoriaService auditoriaService;

    @GetMapping("")
    @Operation(summary = "Obtener todos los registros", description = "Endpoint que permite consultar el historial completo de auditoría del sistema")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa, se entrega la lista de registros")
    @ApiResponse(responseCode = "204", description = "Consulta exitosa, pero no se encontraron registros de auditoría")
    public ResponseEntity<List<Auditoria>> listar() {
        log.info("GET solicitado en /api/v1/auditoria");
        List<Auditoria> lista = auditoriaService.listarTodas();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar registro por ID", description = "Retorna un evento de auditoría específico según su ID")
    @ApiResponse(responseCode = "200", description = "Registro encontrado exitosamente")
    @ApiResponse(responseCode = "404", description = "No se encontró un registro con el ID proporcionado")
    public ResponseEntity<Auditoria> buscarPorId(@Parameter(description = "ID del registro de auditoría a consultar") @PathVariable Integer id) {
        log.info("GET solicitado en /api/v1/auditoria/{}", id);
        Auditoria auditoria = auditoriaService.buscarPorId(id);
        if (auditoria != null) {
            return new ResponseEntity<>(auditoria, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ticket/{idTicket}")
    @Operation(summary = "Buscar auditoría por ID de Ticket", description = "Retorna todos los eventos y acciones asociadas a un ticket específico")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa, se entrega la lista de eventos del ticket")
    @ApiResponse(responseCode = "204", description = "Consulta exitosa, pero el ticket no tiene eventos registrados")
    public ResponseEntity<List<Auditoria>> buscarPorTicket(@Parameter(description = "ID del ticket para consultar su historial de eventos") @PathVariable Integer idTicket) {
        log.info("GET solicitado en /api/v1/auditoria/ticket/{}", idTicket);
        List<Auditoria> lista = auditoriaService.buscarPorTicket(idTicket);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    @Operation(summary = "Crear nuevo registro de auditoría", description = "Permite registrar un nuevo evento o acción realizada en el sistema")
    @ApiResponse(responseCode = "201", description = "Registro de auditoría guardado con éxito")
    @ApiResponse(responseCode = "400", description = "Error: No se pudo guardar el registro de auditoría")
    public ResponseEntity<String> registrar(@RequestBody @Valid Auditoria auditoria) {
        log.info("POST solicitado en /api/v1/auditoria/");

        if (auditoriaService.registrarAuditoria(auditoria)) {
            return new ResponseEntity<>("Registro de auditoría guardado con éxito.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error: No se pudo guardar el registro de auditoría.", HttpStatus.BAD_REQUEST);
        }
    }
}
