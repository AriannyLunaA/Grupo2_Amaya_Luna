package com.servicio.tecnico.mscliente.controller;

import com.servicio.tecnico.mscliente.model.Cliente;
import com.servicio.tecnico.mscliente.repository.ClienteRepository;
import com.servicio.tecnico.mscliente.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ClienteService clienteService;

    //1- Listar todos
    @GetMapping
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

    //2-Buscar por rut para evitar problias con los puntos del Rut
    @GetMapping("/buscar-rut")
    public ResponseEntity<?> obtenenerPorRut(@RequestParam String rut){
        try {
            Cliente cliente = clienteService.buscarPorRut(rut);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    //Buscar por name

    @GetMapping("/buscar")
    public List<Cliente> buscar(@RequestParam String nombrre){
        return clienteService.buscarPorNombre(nombrre);
    }
    //crear cliente
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Cliente cliente){
        try{
            Cliente nuevoCliente = clienteService.guardar(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //Eliminar cliente por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            clienteService.eliminar(id);
            return ResponseEntity.ok("Cliente eliminado");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}//fin
