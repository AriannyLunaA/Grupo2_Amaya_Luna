package com.fixnow.msnotificacion.Controller;

import com.fixnow.msnotificacion.Model.Notificacion;
import com.fixnow.msnotificacion.Service.NotificacionService;
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
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("")
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
    public ResponseEntity<Notificacion> buscarPorId(@PathVariable Integer id) {
        log.info("GET solicitado en /api/v1/notificaciones/{}", id);
        Notificacion buscada = notificacionService.buscarPorId(id);
        if (buscada != null) {
            return new ResponseEntity<>(buscada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ticket/{idTicket}")
    public ResponseEntity<List<Notificacion>> buscarPorTicket(@PathVariable Long idTicket) {
        log.info("GET solicitado en /api/v1/notificaciones/ticket/{}", idTicket);
        List<Notificacion> lista = notificacionService.buscarPorTicket(idTicket);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }


    @PostMapping("/")
    public ResponseEntity<String> enviar(@RequestBody @Valid Notificacion notificacion) {
        log.info("POST solicitado en /api/v1/notificaciones/");

        if (notificacionService.enviarNotificacion(notificacion)) {
            return new ResponseEntity<>("Notificación generada y enviada con éxito.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error: No se pudo enviar la notificación. Verifique la existencia del Ticket y la Persona.", HttpStatus.BAD_REQUEST);
        }
    }
}
