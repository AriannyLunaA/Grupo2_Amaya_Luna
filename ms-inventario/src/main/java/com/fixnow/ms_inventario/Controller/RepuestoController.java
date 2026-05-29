package com.fixnow.ms_inventario.Controller;

import com.fixnow.ms_inventario.Model.Repuesto;
import com.fixnow.ms_inventario.Service.RepuestoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/inventario")
public class RepuestoController {

    @Autowired
    private RepuestoService repuestoService;

    @GetMapping("")
    public ResponseEntity<List<Repuesto>> listar() {
        log.info("GET solicitado en /api/v1/inventario");
        List<Repuesto> lista = repuestoService.listarTodos();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repuesto> buscarPorId(@PathVariable Integer id) {
        log.info("GET solicitado en /api/v1/inventario/{}", id);
        Repuesto repuesto = repuestoService.buscarPorId(id);
        if (repuesto != null) {
            return new ResponseEntity<>(repuesto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<String> registrar(@RequestBody @Valid Repuesto repuesto) {
        log.info("POST solicitado en /api/v1/inventario/");
        if (repuestoService.registrarRepuesto(repuesto)) {
            return new ResponseEntity<>("repuesto registrado con éxito en el inventario.", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error: no se pudo registrar el repuesto.", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}/descontar")
    public ResponseEntity<String> descontar(@PathVariable Integer id, @RequestParam Integer cantidad) {
        log.info("PUT solicitado en /api/v1/inventario/{}/descontar con cantidad: {}", id, cantidad);
        if (repuestoService.descontarStock(id, cantidad)) {
            return new ResponseEntity<>("stock descontado con éxito.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Error: no se pudo descontar el stock. Verifique ID o disponibilidad.", HttpStatus.BAD_REQUEST);
    }
}
