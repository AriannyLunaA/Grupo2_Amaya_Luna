package com.fixnow.mspagos.Repository;

import com.fixnow.mspagos.Model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {

    // Método personalizado para obtener el historial de transacciones de un ticket
    List<Pago> findByIdTicket(Integer idTicket);
}
