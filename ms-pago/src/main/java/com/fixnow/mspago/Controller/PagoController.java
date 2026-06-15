package com.fixnow.mspago.Controller;

import com.fixnow.mspago.Model.Pago;
import com.fixnow.mspago.Service.PagoService;
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
@RequestMapping("/api/v1/pagos")
@Tag(name = "API Pagos", description = "API para la gestión de transacciones y pagos de tickets")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping("")
    @Operation(summary = "Obtener todos los pagos", description = "Endpoint que permite consultar el historial completo de pagos registrados")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa, se entrega la lista de pagos")
    @ApiResponse(responseCode = "204", description = "Consulta exitosa, pero no se encontraron pagos registrados")
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
    @Operation(summary = "Buscar pago por ID", description = "Retorna un registro de pago específico según su ID")
    @ApiResponse(responseCode = "200", description = "Pago encontrado exitosamente")
    @ApiResponse(responseCode = "404", description = "No se encontró un pago con el ID proporcionado")
    public ResponseEntity<Pago> buscarPagoPorId(@Parameter(description = "ID del pago a consultar") @PathVariable Integer id) {
        log.info("GET solicitado en /api/v1/pagos/{}", id);
        Pago pagoBuscado = pagoService.buscarPagoPorId(id);
        if (pagoBuscado != null) {
            return new ResponseEntity<>(pagoBuscado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ticket/{idTicket}")
    @Operation(summary = "Buscar pagos por ID de Ticket", description = "Retorna todos los pagos asociados a un ticket de soporte específico")
    @ApiResponse(responseCode = "200", description = "Consulta exitosa, se entrega la lista de pagos del ticket")
    @ApiResponse(responseCode = "204", description = "Consulta exitosa, pero el ticket no tiene pagos registrados")
    public ResponseEntity<List<Pago>> buscarPagosPorTicket(@Parameter(description = "ID del ticket para consultar sus pagos") @PathVariable Long idTicket) {
        log.info("GET solicitado en /api/v1/pagos/ticket/{}", idTicket);
        List<Pago> listadoPorTicket = pagoService.buscarPagosPorTicket(idTicket);
        if (listadoPorTicket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(listadoPorTicket, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    @Operation(summary = "Registrar un nuevo pago", description = "Permite registrar una nueva transacción de pago validando la existencia del ticket")
    @ApiResponse(responseCode = "201", description = "Transacción de pago registrada con éxito")
    @ApiResponse(responseCode = "400", description = "Error: No se pudo procesar el pago. Verifique el monto y la existencia del Ticket")
    public ResponseEntity<String> registrarPago(@RequestBody @Valid Pago pago) {
        log.info("POST solicitado en /api/v1/pagos/");

        if (pagoService.registrarPago(pago)) {
            return new ResponseEntity<>("Transacción de pago registrada con éxito.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error: No se pudo procesar el pago. Verifique el monto y la existencia del Ticket.", HttpStatus.BAD_REQUEST);
        }
    }
}
