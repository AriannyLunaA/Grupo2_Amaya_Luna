package com.fixnow.msauditoria.Controller;

import com.fixnow.msauditoria.Model.Auditoria;
import com.fixnow.msauditoria.Service.AuditoriaService;
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
public class AuditoriaController {

    @Autowired
    private AuditoriaService auditoriaService;

    @GetMapping("")
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
    public ResponseEntity<Auditoria> buscarPorId(@PathVariable Integer id) {
        log.info("GET solicitado en /api/v1/auditoria/{}", id);
        Auditoria auditoria = auditoriaService.buscarPorId(id);
        if (auditoria != null) {
            return new ResponseEntity<>(auditoria, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ticket/{idTicket}")
    public ResponseEntity<List<Auditoria>> buscarPorTicket(@PathVariable Integer idTicket) {
        log.info("GET solicitado en /api/v1/auditoria/ticket/{}", idTicket);
        List<Auditoria> lista = auditoriaService.buscarPorTicket(idTicket);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> registrar(@RequestBody @Valid Auditoria auditoria) {
        log.info("POST solicitado en /api/v1/auditoria/");

        if (auditoriaService.registrarAuditoria(auditoria)) {
            return new ResponseEntity<>("Registro de auditoría guardado con éxito.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error: No se pudo guardar el registro de auditoría.", HttpStatus.BAD_REQUEST);
        }
    }
}
