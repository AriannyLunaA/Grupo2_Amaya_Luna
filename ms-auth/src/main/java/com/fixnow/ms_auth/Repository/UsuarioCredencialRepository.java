package com.fixnow.ms_auth.Repository;

import com.fixnow.ms_auth.Model.UsuarioCredencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioCredencialRepository extends JpaRepository<UsuarioCredencial, Long> {
    Optional<UsuarioCredencial> findByUsername(String username);
}
