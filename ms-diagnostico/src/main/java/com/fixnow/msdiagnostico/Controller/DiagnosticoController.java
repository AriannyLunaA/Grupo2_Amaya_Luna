package com.fixnow.msdiagnostico.Controller;

import com.fixnow.msdiagnostico.DTO.DiagnosticoDTO;
import com.fixnow.msdiagnostico.Service.DiagnosticoService;
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
@RequestMapping("/api/v1/diagnostico")
@Tag(name = "API Diagnósticos", description = "API para la gestión, registro y consulta de diagnósticos técnicos de los equipos")
public class DiagnosticoController {

    @Autowired
    private DiagnosticoService service;

    @GetMapping("")
    @Operation(summary = "Obtener todos los diagnósticos", description = "Retorna la lista completa de diagnósticos emitidos en el sistema")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa se entrega la lista de diagnósticos")
    @ApiResponse(responseCode = "204", description = "Consulta exitosa, pero no se encontraron diagnósticos registrados")
    public ResponseEntity<List<DiagnosticoDTO>> listar() {
        log.info("GET solicitado en /api/v1/diagnostico");
        List<DiagnosticoDTO> lista = service.listarTodos();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar diagnóstico por ID", description = "Retorna los detalles de un diagnóstico específico mediante su identificador único")
    @ApiResponse(responseCode = "200", description = "Diagnóstico encontrado exitosamente")
    @ApiResponse(responseCode = "404", description = "El ID proporcionado no corresponde a ningún diagnóstico registrado")
    public ResponseEntity<DiagnosticoDTO> obtenerPorId(@Parameter(description = "ID único del diagnóstico a consultar") @PathVariable Long id) {
        log.info("GET solicitado en /api/v1/diagnostico/{}", id);
        DiagnosticoDTO dto = service.buscarPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    @Operation(summary = "Crear un nuevo diagnóstico", description = "Registra un diagnóstico técnico en el sistema validando la integridad de los datos de entrada")
    @ApiResponse(responseCode = "201", description = "Diagnóstico creado correctamente en el sistema")
    @ApiResponse(responseCode = "400", description = "Error en la solicitud, datos inválidos o DTO mal estructurado")
    public ResponseEntity<DiagnosticoDTO> crear(@Valid @RequestBody DiagnosticoDTO dto) {
        log.info("POST solicitado en /api/v1/diagnostico/");
        DiagnosticoDTO guardado = service.guardar(dto);
        if (guardado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
}
