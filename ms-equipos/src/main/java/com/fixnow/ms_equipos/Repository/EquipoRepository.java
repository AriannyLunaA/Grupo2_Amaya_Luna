package com.fixnow.ms_equipos.Repository;

import com.fixnow.ms_equipos.Model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    List<Equipo> findByIdPersona(Long idPersona);
}