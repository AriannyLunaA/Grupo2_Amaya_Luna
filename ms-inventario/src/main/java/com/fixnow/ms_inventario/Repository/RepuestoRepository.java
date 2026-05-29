package com.fixnow.ms_inventario.Repository;

import com.fixnow.ms_inventario.Model.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepuestoRepository extends JpaRepository<Repuesto, Integer> {
}
