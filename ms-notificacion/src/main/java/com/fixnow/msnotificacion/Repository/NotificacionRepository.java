package com.fixnow.msnotificacion.Repository;

import com.fixnow.msnotificacion.Model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByIdTicket(Long idTicket);
}
