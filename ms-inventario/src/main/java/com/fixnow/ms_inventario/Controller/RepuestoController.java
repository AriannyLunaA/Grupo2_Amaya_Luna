package com.fixnow.ms_inventario.Controller;

import com.fixnow.ms_inventario.Model.Repuesto;
import com.fixnow.ms_inventario.Service.RepuestoService;
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
@RequestMapping("/api/v1/inventario")
@Tag(name = "API Inventario",description = "API para la gestion del inventario")
public class RepuestoController {

    @Autowired
    private RepuestoService repuestoService;

    @GetMapping("")
    @Operation(summary = "obtener los repuestos", description = "endpoint permite consultar todos los repuestos")
    @ApiResponse(responseCode="200",description = "consulta exitosa se entrega la lista de repuestos en inventario")
    @ApiResponse(responseCode="204",description = "no encontrado")
    public ResponseEntity<List<Repuesto>> listar() {
        log.info("GET solicitado en /api/v1/inventario");
        List<Repuesto> lista = repuestoService.listarTodos();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "buscar repuesto por ID", description = "retorna un repuesto específico del inventario según su ID único")
    @ApiResponse(responseCode="200", description = "repuesto encontrado exitosamente")
    @ApiResponse(responseCode="404", description = "repuesto no encontrado en el sistema")
    public ResponseEntity<Repuesto> buscarPorId(
            @Parameter(description = "ID único del repuesto a consultar", example = "1") @PathVariable Integer id) {
        log.info("GET solicitado en /api/v1/inventario/{}", id);
        Repuesto repuesto = repuestoService.buscarPorId(id);
        if (repuesto != null) {
            return new ResponseEntity<>(repuesto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    @Operation(summary = "registrar un nuevo repuesto", description = "crea un nuevo repuesto en el sistema de inventario validando los datos requeridos")
    @ApiResponse(responseCode="201", description = "repuesto registrado con éxito en el inventario")
    @ApiResponse(responseCode="400", description = "error en la petición, datos inválidos o duplicados")
    public ResponseEntity<String> registrar(@RequestBody @Valid Repuesto repuesto) {
        log.info("POST solicitado en /api/v1/inventario/");
        if (repuestoService.registrarRepuesto(repuesto)) {
            return new ResponseEntity<>("repuesto registrado con éxito en el inventario.", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("error: no se pudo registrar el repuesto.", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}/descontar")
    @Operation(summary = "descontar cantidad del stock", description = "disminuye el stock disponible de un repuesto según la cantidad indicada")
    @ApiResponse(responseCode="200", description = "stock descontado con éxito")
    @ApiResponse(responseCode="400", description = "error al descontar stock por ID inválido o insuficiencia de unidades")
    public ResponseEntity<String> descontar(@Parameter(description = "ID del repuesto que modificará su stock")@PathVariable Integer id,@Parameter(description = "Cantidad de unidades a restar del inventario") @RequestParam Integer cantidad) {
        log.info("PUT solicitado en /api/v1/inventario/{}/descontar con cantidad: {}", id, cantidad);
        if (repuestoService.descontarStock(id, cantidad)) {
            return new ResponseEntity<>("stock descontado con éxito.", HttpStatus.OK);
        }
        return new ResponseEntity<>("error: no se pudo descontar el stock. Verifique ID o disponibilidad.", HttpStatus.BAD_REQUEST);
    }
}
