package com.fixnow.ms_equipos.Repository;

import com.fixnow.ms_equipos.Model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    // al heredar de JpaRepository, ya tenemos: save(), findAll(), findById(), deleteById()

    //Custom Queries

    //1.Buscar por número de serie (recibe un String y devuelve 1 solo equipo)
    Equipo findByNumeroSerie(String numeroSerie);

    //2.Buscar todos los equipos de una marca (devuelve una lista de equipos)
    List<Equipo> findByMarca(String marca);

    //3.Buscar todos los equipos que esten en un estado específico (ej: "Disponible")
    List<Equipo> findByEstado(String estado);

    //4.Buscar por marca y modelo al mismo tiempo
    List<Equipo> findByMarcaAndModelo(String marca, String modelo);
}
