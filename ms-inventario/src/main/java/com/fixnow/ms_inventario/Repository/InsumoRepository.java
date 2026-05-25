package com.fixnow.ms_inventario.Repository;

import com.fixnow.ms_inventario.Model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {
}
