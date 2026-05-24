package com.fixnow.msnotificaciones.Repository;

import com.fixnow.msnotificaciones.Model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {

    // Recupera la trazabilidad de los avisos enviados en el contexto de un ticket
    List<Notificacion> findByIdTicket(Integer idTicket);
}
