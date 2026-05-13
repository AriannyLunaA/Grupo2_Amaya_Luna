/package com.servicio.tecnico.mscliente.service;

import com.servicio.tecnico.mscliente.model.Cliente;
import com.servicio.tecnico.mscliente.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service


public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    // lista completa de clients
    public List<Cliente> listarTodos(){
     return clienteRepository.findAll();
    }
    //Guardar cliente
    public Cliente guardar (Cliente cliente){
        if (clienteRepository.existsByRut(cliente.getRut())){
            throw new RuntimeException("Ya existe un cliente con ese rut");
        } // si no existe lo guarda
        return clienteRepository.save(cliente);
    }
    //Buscar por rut
    public Cliente buscarPorRut(String rut){
        return clienteRepository.findByRut(rut)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }
    //buscar por nombre ignora Mayuscula y minuscula
    public List<Cliente>buscarPorNombre(String nombre){
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }
    //Eliminar por id cliente
    public void eliminar(Long id){
        if (!clienteRepository.existsById(id)){ //valida que existe
            throw new RuntimeException("El cliente no existe");

        } //si esxiste lo borra
        clienteRepository.deleteById(id);
    }




}//Fin
