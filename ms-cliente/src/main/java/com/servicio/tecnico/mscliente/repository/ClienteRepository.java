//use interfaz para aplicar abtracción (se hereda de JPARepo para utilizar toda la logica de persistencia de datos CRUD, que viene en el Sprin Data** las herramientas
package com.servicio.tecnico.mscliente.repository;

import com.servicio.tecnico.mscliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    //metodos

    //cargar lista de clientes *listar
    List<Cliente> findAll();

    //buscar por rut
    Optional<Cliente> findByRut(String rut);

    boolean existsByRut(String rut); //validar si es¿xiste  rut
    //buscar por nombre
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    //valiodar si existe correo
    boolean existsByEmail(String email);
}//Fin
