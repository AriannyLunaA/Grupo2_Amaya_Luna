package com.fixnow.ms_inventario.Controller;

import com.fixnow.ms_inventario.Model.Repuesto;
import com.fixnow.ms_inventario.Service.RepuestoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
//swagger
@Tag(name = "API Inventario",description = "Api para la gestion del inventario")
public class RepuestoController {

    @Autowired
    private RepuestoService repuestoService;

    @GetMapping("")
   //swagger
    @Operation(summary = "Obtener los Repuestos", description = "Endpoint permite consultar todos los Repuestos")
    @ApiResponses(value={
            @ApiResponse(responseCode="200",description = "Consulta exitosa se entrega la lista de Repuestos en inventario"),
            @ApiResponse(responseCode="204",description = "No encontrado")
    })
    public ResponseEntity<List<Repuesto>> listar() {
        log.info("GET solicitado en /api/v1/inventario");
        List<Repuesto> lista = repuestoService.listarTodos();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar repuesto por ID", description = "Retorna un repuesto específico del inventario según su ID único")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description = "Repuesto encontrado exitosamente"),
            @ApiResponse(responseCode="404", description = "Repuesto no encontrado en el sistema")
    })
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
    @Operation(summary = "Registrar un nuevo repuesto", description = "Crea un nuevo repuesto en el sistema de inventario validando los datos requeridos")
    @ApiResponses(value={
            @ApiResponse(responseCode="201", description = "Repuesto registrado con éxito en el inventario"),
            @ApiResponse(responseCode="400", description = "Error en la petición, datos inválidos o duplicados")
    })
    public ResponseEntity<String> registrar(@RequestBody @Valid Repuesto repuesto) {
        log.info("POST solicitado en /api/v1/inventario/");
        if (repuestoService.registrarRepuesto(repuesto)) {
            return new ResponseEntity<>("repuesto registrado con éxito en el inventario.", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error: no se pudo registrar el repuesto.", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}/descontar")
    @Operation(summary = "Descontar cantidad del stock", description = "Disminuye el stock disponible de un repuesto según la cantidad indicada")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description = "Stock descontado con éxito"),
            @ApiResponse(responseCode="400", description = "Error al descontar stock por ID inválido o insuficiencia de unidades")
    })
    public ResponseEntity<String> descontar(@Parameter(description = "ID del repuesto que modificará su stock")@PathVariable Integer id,@Parameter(description = "Cantidad de unidades a restar del inventario") @RequestParam Integer cantidad) {
        log.info("PUT solicitado en /api/v1/inventario/{}/descontar con cantidad: {}", id, cantidad);
        if (repuestoService.descontarStock(id, cantidad)) {
            return new ResponseEntity<>("stock descontado con éxito.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Error: no se pudo descontar el stock. Verifique ID o disponibilidad.", HttpStatus.BAD_REQUEST);
    }
}
