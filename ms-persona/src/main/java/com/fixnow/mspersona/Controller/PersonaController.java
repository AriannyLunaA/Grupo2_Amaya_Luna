package com.fixnow.mspersona.Controller;

import com.fixnow.mspersona.Model.Persona;
import com.fixnow.mspersona.Service.PersonaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping("")
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
    public ResponseEntity<Persona> buscarPorId(@PathVariable Long id) {
        log.info("GET en /api/v1/personas/{}", id);
        Persona persona = personaService.buscarPorId(id);
        if (persona == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(persona);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Persona> agregar(@RequestBody @Valid Persona persona) {
        log.info("POST en /api/v1/personas/");
        return ResponseEntity.ok(personaService.agregar(persona));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE en /api/v1/personas/{}", id);
        boolean res = personaService.eliminar(id);
        if (res) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizar(@PathVariable Long id, @RequestBody @Valid Persona persona) {
        log.info("PUT en /api/v1/personas/{}", id);
        Persona updated = personaService.updatePersona(id, persona);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updated);
        }
    }
}
