package com.fixnow.msauditoria.Service;

import com.fixnow.msauditoria.Model.Auditoria;
import com.fixnow.msauditoria.Repository.AuditoriaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    public List<Auditoria> listarTodas() {
        return auditoriaRepository.findAll();
    }

    public Auditoria buscarPorId(Integer id) {
        return auditoriaRepository.findById(id).orElse(null);
    }

    public List<Auditoria> buscarPorTicket(long idTicket) {
        return auditoriaRepository.findByIdTicket(idTicket);
    }

    public boolean registrarAuditoria(Auditoria auditoria) {
        try {
            log.info("iniciando persistencia de log de auditoría para la acción: {}", auditoria.getAccion());

            // Asignamos la fecha exacta del servidor en el momento de guardar
            auditoria.setFecha(LocalDateTime.now());

            auditoriaRepository.save(auditoria);
            log.info("auditoría registrada correctamente con id: {}", auditoria.getIdAuditoria());
            return true;
        } catch (Exception e) {
            log.error("fallo crítico al intentar persistir en la tabla de auditoría: {}", e.getMessage());
            return false;
        }
    }
}
