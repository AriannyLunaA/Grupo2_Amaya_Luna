package com.fixnow.msdiagnostico.Service;


import com.fixnow.msdiagnostico.Client.TicketClient;
import com.fixnow.msdiagnostico.DTO.DiagnosticoDTO;
import com.fixnow.msdiagnostico.DTO.TicketDTO;
import com.fixnow.msdiagnostico.Model.Diagnostico;
import com.fixnow.msdiagnostico.Repository.DiagnosticoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DiagnosticoService {

    @Autowired
    private DiagnosticoRepository repository;

    @Autowired
    private TicketClient ticketClient;

    public List<DiagnosticoDTO> listarTodos() {
        log.info("listando diagnósticos");
        List<Diagnostico> lista = repository.findAll();
        List<DiagnosticoDTO> listaDTO = new ArrayList<>();

        for (Diagnostico diag : lista) {
            listaDTO.add(convertirAEntityDTO(diag));
        }
        return listaDTO;
    }

    public DiagnosticoDTO buscarPorId(Long id) {
        log.info("buscando diagnóstico id: {}", id);
        Optional<Diagnostico> opt = repository.findById(id);

        if (opt.isPresent()) {
            return convertirAEntityDTO(opt.get());
        }
        return null;
    }

    public DiagnosticoDTO guardar(DiagnosticoDTO dto) {
        log.info("validando existencia del Ticket id: {}", dto.getIdTicket());

        // Escudo protector con OpenFeign
        try {
            TicketDTO ticket = ticketClient.obtenerPorId(dto.getIdTicket());
            if (ticket == null) {
                log.warn("el Ticket id {} no existe. Diagnóstico rechazado.", dto.getIdTicket());
                return null;
            }
        } catch (Exception e) {
            log.error("falla al contactar con ms-ticket. Motivo: {}", e.getMessage());
            return null;
        }

        log.info("ticket validado correctamente. Guardando diagnóstico");
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setIdTicket(dto.getIdTicket());
        diagnostico.setDetalleRevision(dto.getDetalleRevision());
        diagnostico.setNecesitaRepuesto(dto.getNecesitaRepuesto());
        diagnostico.setCostoEstimado(dto.getCostoEstimado());
        diagnostico.setTiempoEstimadoDias(dto.getTiempoEstimadoDias());

        Diagnostico guardado = repository.save(diagnostico);
        return convertirAEntityDTO(guardado);
    }

    private DiagnosticoDTO convertirAEntityDTO(Diagnostico diagnostico) {
        DiagnosticoDTO dto = new DiagnosticoDTO();
        dto.setIdDiagnostico(diagnostico.getIdDiagnostico());
        dto.setIdTicket(diagnostico.getIdTicket());
        dto.setDetalleRevision(diagnostico.getDetalleRevision());
        dto.setNecesitaRepuesto(diagnostico.getNecesitaRepuesto());
        dto.setCostoEstimado(diagnostico.getCostoEstimado());
        dto.setTiempoEstimadoDias(diagnostico.getTiempoEstimadoDias());
        return dto;
    }
}
