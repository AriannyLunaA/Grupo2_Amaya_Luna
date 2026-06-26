package com.fixnow.msinventario.Service;

import com.fixnow.msinventario.Model.Repuesto;
import com.fixnow.msinventario.Repository.RepuestoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RepuestoService {

    @Autowired
    private RepuestoRepository repuestoRepository;

    public List<Repuesto> listarTodos() {
        log.info("buscando todos los repuestos en el inventario.");
        return repuestoRepository.findAll();
    }

    public Repuesto buscarPorId(Integer id) {
        log.info("buscando repuesto con ID: {}", id);
        return repuestoRepository.findById(id).orElse(null);
    }

    public boolean registrarRepuesto(Repuesto repuesto) {
        try {
            log.info("guardando nuevo repuesto: {}", repuesto.getNombre());
            repuestoRepository.save(repuesto);
            return true;
        } catch (Exception e) {
            log.error("error al registrar repuesto: {}", e.getMessage());
            return false;
        }
    }

    public boolean descontarStock(Integer id, Integer cantidad) {
        log.info("intentando descontar {} unidades del repuesto ID: {}", cantidad, id);
        Repuesto repuesto = repuestoRepository.findById(id).orElse(null);

        if (repuesto == null) {
            log.warn("el repuesto con ID {} no existe.", id);
            return false;
        }

        if (repuesto.getStock() < cantidad) {
            log.warn("stock insuficiente para el repuesto {}. stock actual: {}, cantidad solicitada: {}",
                    repuesto.getNombre(), repuesto.getStock(), cantidad);
            return false;
        }

        repuesto.setStock(repuesto.getStock() - cantidad);
        repuestoRepository.save(repuesto);
        log.info("stock actualizado correctamente para {}. nuevo stock: {}", repuesto.getNombre(), repuesto.getStock());
        return true;
    }
}