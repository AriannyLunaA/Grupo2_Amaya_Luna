package com.fixnow.mspagos.Controller;

import com.fixnow.mspagos.Model.Pago;
import com.fixnow.mspagos.Service.PagoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el recurso de Pagos.
 * Delega toda la lógica de negocio al Service, cumpliendo con el principio
 * de Responsabilidad Única (SRP). Solo gestiona mapeos y códigos HTTP.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping("")
    public ResponseEntity<List<Pago>> listarPagos() {
        log.info("GET solicitado en /api/v1/pagos");
        List<Pago> listadoPagos = pagoService.listarPagos();
        if (listadoPagos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(listadoPagos, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscarPagoPorId(@PathVariable Integer id) {
        log.info("GET solicitado en /api/v1/pagos/{}", id);
        Pago pagoBuscado = pagoService.buscarPagoPorId(id);
        if (pagoBuscado != null) {
            return new ResponseEntity<>(pagoBuscado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ticket/{idTicket}")
    public ResponseEntity<List<Pago>> buscarPagosPorTicket(@PathVariable Integer idTicket) {
        log.info("GET solicitado en /api/v1/pagos/ticket/{}", idTicket);
        List<Pago> listadoPorTicket = pagoService.buscarPagosPorTicket(idTicket);
        if (listadoPorTicket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(listadoPorTicket, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> registrarPago(@RequestBody @Valid Pago pago) {
        log.info("POST solicitado en /api/v1/pagos/");

        if (pagoService.registrarPago(pago)) {
            return new ResponseEntity<>("Transacción de pago registrada con éxito.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error: No se pudo procesar el pago. Verifique el monto y la existencia del Ticket.", HttpStatus.BAD_REQUEST);
        }
    }
}
