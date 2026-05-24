package com.fixnow.ms_equipos.Controller;

import jakarta.validation.Valid;
import com.fixnow.ms_equipos.Model.Equipo;
import com.fixnow.ms_equipos.Service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipos")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;


    @GetMapping("")
    public ResponseEntity<List<Equipo>> getAllEquipos() {
        List<Equipo> listado = equipoService.listarEquipos();
        if (listado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(listado, HttpStatus.OK);
        }
    }

    /
    @GetMapping("/{id}")
    public ResponseEntity<Equipo> getEquipoById(@PathVariable Long id) {
        Equipo buscado = equipoService.buscarPorId(id);
        if (buscado != null) {
            return new ResponseEntity<>(buscado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/serie/{numeroSerie}")
    public ResponseEntity<Equipo> getEquipoByNumeroSerie(@PathVariable String numeroSerie) {
        Equipo buscado = equipoService.buscarPorNumeroSerie(numeroSerie);
        if (buscado != null) {
            return new ResponseEntity<>(buscado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Equipo>> getEquiposByMarca(@PathVariable String marca) {
        List<Equipo> filtrados = equipoService.buscarPorMarca(marca);
        if (filtrados.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(filtrados, HttpStatus.OK);
        }
    }


    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Equipo>> getEquiposByEstado(@PathVariable String estado) {
        List<Equipo> filtrados = equipoService.buscarPorEstado(estado);
        if (filtrados.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(filtrados, HttpStatus.OK);
        }
    }


    @PostMapping("/")
    public ResponseEntity<Equipo> createEquipo(@RequestBody @Valid Equipo equipo) {
        Equipo nuevo = equipoService.agregarEquipo(equipo);
        if (nuevo != null) {
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipo(@PathVariable Long id) {
        boolean res = equipoService.borrarEquipo(id);
        if (res) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Equipo> updateEquipo(@PathVariable Long id, @RequestBody @Valid Equipo nuevo) {
        Equipo actualizado = equipoService.actualizarEquipo(id, nuevo);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}