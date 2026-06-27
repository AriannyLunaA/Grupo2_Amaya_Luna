package com.fixnow.ms_equipos.Controller;

import com.fixnow.ms_equipos.DTO.EquipoDTO;
import com.fixnow.ms_equipos.Service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipo")
@Tag(name = "API Equipos", description = "gestión de los equipos ingresados al servicio técnico")
public class EquipoController {

    @Autowired
    private EquipoService service;

    @GetMapping
    @Operation(summary = "lista todos los equipos", description = "Devuelve el inventario completo de los equipos registrados")
    @ApiResponse(responseCode = "200", description = "consulta exitosa")
    public ResponseEntity<List<EquipoDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "buscar equipo por ID", description = "obtiene los detalles de un equipo en específico")
    @ApiResponse(responseCode = "200", description = "equipo encontrado")
    @ApiResponse(responseCode = "404", description = "el id del equipo no existe")
    public ResponseEntity<EquipoDTO> obtenerPorId(
            @Parameter(description = "identificador único del equipo") @PathVariable Long id) {
        EquipoDTO dto = service.buscarPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/persona/{idPersona}")
    @Operation(summary = "buscar equipos por persona", description = "obtiene todos los equipos asociados al ID de una persona")
    @ApiResponse(responseCode = "200", description = "búsqueda exitosa")
    public ResponseEntity<List<EquipoDTO>> listarPorPersona(
            @Parameter(description = "ID de la persona dueña del equipo") @PathVariable Long idPersona) {
        return ResponseEntity.ok(service.listarPorPersona(idPersona));
    }

    @PostMapping
    @Operation(summary = "ingresar nuevo equipo", description = "registra un nuevo equipo en la base de datos")
    @ApiResponse(responseCode = "201", description = "equipo registrado exitosamente")
    @ApiResponse(responseCode = "400", description = "datos inválidos o incompletos")
    public ResponseEntity<EquipoDTO> crear(@Valid @RequestBody EquipoDTO dto) {
        EquipoDTO equipoGuardado = service.guardar(dto);
        if (equipoGuardado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(equipoGuardado);
    }
}