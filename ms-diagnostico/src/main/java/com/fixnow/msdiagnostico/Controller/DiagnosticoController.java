package com.fixnow.msdiagnostico.Controller;


import com.fixnow.msdiagnostico.DTO.DiagnosticoDTO;
import com.fixnow.msdiagnostico.Service.DiagnosticoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/diagnostico")
public class DiagnosticoController {

    @Autowired
    private DiagnosticoService service;

    @GetMapping
    public ResponseEntity<List<DiagnosticoDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticoDTO> obtenerPorId(@PathVariable Long id) {
        DiagnosticoDTO dto = service.buscarPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DiagnosticoDTO> crear(@Valid @RequestBody DiagnosticoDTO dto) {
        DiagnosticoDTO guardado = service.guardar(dto);
        if (guardado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
}