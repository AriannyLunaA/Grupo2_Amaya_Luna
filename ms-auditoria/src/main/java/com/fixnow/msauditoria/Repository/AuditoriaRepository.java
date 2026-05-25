package com.fixnow.msauditoria.Repository;

import com.fixnow.msauditoria.Model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Integer> {

    // Recupera la trazabilidad de las acciones en el contexto de un ticket específico
    List<Auditoria> findByIdTicket(Integer idTicket);

    // Recupera la trazabilidad de las acciones asociadas a un pago específico
    List<Auditoria> findByIdPago(Integer idPago);

    // Recupera la trazabilidad de las acciones asociadas a una persona específica
    List<Auditoria> findByIdPersona(Integer idPersona);
}
