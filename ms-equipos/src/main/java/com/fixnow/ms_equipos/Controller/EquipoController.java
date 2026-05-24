package com.fixnow.ms_equipos.Controller;

import com.fixnow.ms_equipos.DTO.EquipoDTO;
import com.fixnow.ms_equipos.Service.EquipoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipo")
public class EquipoController {

    @Autowired
    private EquipoService service;

    @GetMapping
    public ResponseEntity<List<EquipoDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoDTO> obtenerPorId(@PathVariable Long id) {
        EquipoDTO dto = service.buscarPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/persona/{idPersona}")
    public ResponseEntity<List<EquipoDTO>> listarPorPersona(@PathVariable Long idPersona) {
        return ResponseEntity.ok(service.listarPorPersona(idPersona));
    }

    @PostMapping
    public ResponseEntity<EquipoDTO> crear(@Valid @RequestBody EquipoDTO dto) {
        EquipoDTO equipoGuardado = service.guardar(dto);
        if (equipoGuardado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(equipoGuardado);
    }
}