package com.fixnow.mspersona.Controller;

import com.fixnow.mspersona.Model.Persona;
import com.fixnow.mspersona.Service.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/personas")
@Tag(name = "API Personas", description = "gestión de las personas del sistema de Fixnow")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping("")
    @Operation(summary = "obtiene todas las personas")
    @ApiResponse(responseCode = "200", description = "lista entregada exitosamente")
    @ApiResponse(responseCode = "204", description = "no existen personas en la base de datos")
    public ResponseEntity<List<Persona>> listar() {
        log.info("GET en /api/v1/personas");
        List<Persona> lista = personaService.listarPersonas();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "buscar persona por ID")
    @ApiResponse(responseCode = "200", description = "persona encontrada")
    @ApiResponse(responseCode = "404", description = "persona no encontrada")
    public ResponseEntity<Persona> buscarPorId(
            @Parameter(description = "ID único de la persona") @PathVariable Long id) {
        log.info("GET en /api/v1/personas/{}", id);
        Persona persona = personaService.buscarPorId(id);
        if (persona == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(persona);
        }
    }

    @PostMapping("/")
    @Operation(summary = "registrar nueva persona")
    @ApiResponse(responseCode = "200", description = "persona agregada con éxito")
    public ResponseEntity<Persona> agregar(@RequestBody @Valid Persona persona) {
        log.info("POST en /api/v1/personas/");
        return ResponseEntity.ok(personaService.agregar(persona));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "eliminar persona por id")
    @ApiResponse(responseCode = "204", description = "persona eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "no se encontró el id de la persona a eliminar")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la persona a eliminar") @PathVariable Long id) {
        log.info("DELETE en /api/v1/personas/{}", id);
        boolean res = personaService.eliminar(id);
        if (res) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "actualiza datos de la persona")
    @ApiResponse(responseCode = "200", description = "datos actualizados exitosamente")
    @ApiResponse(responseCode = "404", description = "la persona no existe")
    public ResponseEntity<Persona> actualizar(
            @Parameter(description = "id de la persona a modificar") @PathVariable Long id, @RequestBody @Valid Persona persona) {
        log.info("PUT en /api/v1/personas/{}", id);
        Persona updated = personaService.updatePersona(id, persona);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updated);
        }
    }
}
