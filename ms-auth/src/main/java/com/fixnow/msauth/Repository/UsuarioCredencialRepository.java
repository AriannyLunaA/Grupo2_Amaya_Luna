package com.fixnow.msauth.Repository;

import com.fixnow.msauth.Model.UsuarioCredencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioCredencialRepository extends JpaRepository<UsuarioCredencial, Long> {
    Optional<UsuarioCredencial> findByUsername(String username);

    // Lunes 25-05: Defensa Técnica Evaluación 2 - Requerimiento del docente (shirokamidev): Buscar y retornar la lista de usuarios filtrados por su rol.
    List<UsuarioCredencial> findByRol(String rol);
}
