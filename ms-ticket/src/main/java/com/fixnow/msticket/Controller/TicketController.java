package com.fixnow.msticket.Controller;


import com.fixnow.msticket.DTO.TicketDTO;
import com.fixnow.msticket.Service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
@Tag(name = "API Tickets", description = "API para la gestión, creación y control de tickets de soporte técnico")
public class TicketController {

    @Autowired
    private TicketService service;

    @GetMapping
    @Operation(summary = "Obtener todos los tickets", description = "Retorna la lista completa de tickets registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta exitosa se entrega la lista de tickets")
    })
    public ResponseEntity<List<TicketDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ticket por ID", description = "Retorna los detalles de un ticket específico según su identificador único ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El ID proporcionado no corresponde a ningún ticket activo")
    })

    public ResponseEntity<TicketDTO> obtenerPorId(@Parameter(description = "ID único del ticket a consultar") @PathVariable Long id) {
        TicketDTO dto = service.buscarPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo ticket", description = "Registra un ticket en el sistema validando la integridad del DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket creado correctamente en el sistema"),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud")
    })
    public ResponseEntity<TicketDTO> crear(@Valid @RequestBody TicketDTO dto) {
        TicketDTO guardado = service.guardar(dto);
        if (guardado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
}
