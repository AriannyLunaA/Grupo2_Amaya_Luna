package com.fixnow.msauditoria.Repository;

import com.fixnow.msauditoria.Model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Integer> {

    List<Auditoria> findByIdTicket(Long idTicket);

    List<Auditoria> findByIdPago(Integer idPago);

    List<Auditoria> findByIdPersona(Long idPersona);
}
