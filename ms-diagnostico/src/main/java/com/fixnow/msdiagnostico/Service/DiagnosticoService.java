package com.fixnow.msdiagnostico.Service;

import com.fixnow.msdiagnostico.Client.InventarioClient;
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

@Slf4j
@Service
public class DiagnosticoService {

    @Autowired
    private DiagnosticoRepository repository;

    @Autowired
    private TicketClient ticketClient;

    @Autowired
    private InventarioClient inventarioClient;

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

        Diagnostico diagnostico = repository.findById(id).orElse(null);

        if (diagnostico != null) {
            return convertirAEntityDTO(diagnostico);
        }
        return null;
    }

    public DiagnosticoDTO guardar(DiagnosticoDTO dto) {
        log.info("validando existencia del Ticket id: {}", dto.getIdTicket());

        try {
            TicketDTO ticket = ticketClient.obtenerPorId(dto.getIdTicket());
            if (ticket == null) {
                log.warn("el ticket id {} no existe. Diagnóstico rechazado.", dto.getIdTicket());
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
        diagnostico.setIdRepuesto(dto.getIdRepuesto());
        diagnostico.setCantidadRepuesto(dto.getCantidadRepuesto());
        diagnostico.setCostoEstimado(dto.getCostoEstimado());
        diagnostico.setTiempoEstimadoDias(dto.getTiempoEstimadoDias());

        Diagnostico guardado = repository.save(diagnostico);

        if (dto.getIdRepuesto() != null && dto.getCantidadRepuesto() != null && dto.getCantidadRepuesto() > 0) {
            try {
                log.info("el diagnóstico requiere repuestos. Contactando a ms-inventario");
                inventarioClient.descontar(dto.getIdRepuesto(), dto.getCantidadRepuesto());
                log.info("stock descontado exitosamente en ms-inventario");
            } catch (Exception e) {
                log.error("el diagnóstico se guardó, pero falló el descuento en ms-inventario: {}", e.getMessage());
            }
        }

        return convertirAEntityDTO(guardado);
    }

    private DiagnosticoDTO convertirAEntityDTO(Diagnostico diagnostico) {
        DiagnosticoDTO dto = new DiagnosticoDTO();
        dto.setIdDiagnostico(diagnostico.getIdDiagnostico());
        dto.setIdTicket(diagnostico.getIdTicket());
        dto.setDetalleRevision(diagnostico.getDetalleRevision());
        dto.setNecesitaRepuesto(diagnostico.getNecesitaRepuesto());
        dto.setIdRepuesto(diagnostico.getIdRepuesto());
        dto.setCantidadRepuesto(diagnostico.getCantidadRepuesto());
        dto.setCostoEstimado(diagnostico.getCostoEstimado());
        dto.setTiempoEstimadoDias(diagnostico.getTiempoEstimadoDias());
        return dto;
    }
}