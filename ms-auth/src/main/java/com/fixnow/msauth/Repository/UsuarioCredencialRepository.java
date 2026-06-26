package com.fixnow.msauth.Repository;

import com.fixnow.msauth.Model.UsuarioCredencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioCredencialRepository extends JpaRepository<UsuarioCredencial, Long> {
    Optional<UsuarioCredencial> findByUsername(String username);

    /*ignore esto profesor, es lo que hicimos en la prueba el lunes
    List<UsuarioCredencial> findByRol(String rol);
    */
}
