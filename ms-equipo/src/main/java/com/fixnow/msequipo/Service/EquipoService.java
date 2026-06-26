package com.fixnow.msequipo.Service;

import com.fixnow.msequipo.Client.PersonaClient;
import com.fixnow.msequipo.DTO.EquipoDTO;
import com.fixnow.msequipo.DTO.PersonaDTO;
import com.fixnow.msequipo.Model.Equipo;
import com.fixnow.msequipo.Repository.EquipoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EquipoService {

    @Autowired
    private EquipoRepository repository;

    @Autowired
    private PersonaClient personaClient;

    public List<EquipoDTO> listarTodos() {
        log.info("Servicio: Buscando todos los equipos");
        List<Equipo> equipos = repository.findAll();
        List<EquipoDTO> resultadoDto = new ArrayList<>();

        for (Equipo equipo : equipos) {
            EquipoDTO dto = convertirAEntityDTO(equipo);
            resultadoDto.add(dto);
        }

        return resultadoDto;
    }

    public EquipoDTO buscarPorId(Long id) {
        log.info("buscando equipo id: {}", id);
        Optional<Equipo> equipoOpt = repository.findById(id);

        if (equipoOpt.isPresent()) {
            Equipo equipo = equipoOpt.get();
            return convertirAEntityDTO(equipo);
        } else {
            return null;
        }
    }

    public List<EquipoDTO> listarPorPersona(Long idPersona) {
        log.info("buscando equipos para la persona con id: {}", idPersona);
        List<Equipo> equipos = repository.findByIdPersona(idPersona);
        List<EquipoDTO> resultadoDto = new ArrayList<>();

        for (Equipo equipo : equipos) {
            EquipoDTO dto = convertirAEntityDTO(equipo);
            resultadoDto.add(dto);
        }
        return resultadoDto;
    }

    public EquipoDTO guardar(EquipoDTO dto) {
        log.info("validando existencia del cliente id: {}", dto.getIdPersona());

        try {
            PersonaDTO persona = personaClient.buscarPorId(dto.getIdPersona());

            if (persona == null) {
                log.warn("el cliente con ID {} no existe en la bdd", dto.getIdPersona());
                return null; // Retorna null si la persona no existe
            }
            log.info("cliente confirmado. guardando equipo");
        } catch (Exception e) {
            log.error("no se pudo contactar con ms-persona. Error técnico: {}", e.getMessage());
            return null;
        }



        Equipo equipo = new Equipo();
        equipo.setIdPersona(dto.getIdPersona());
        equipo.setTipo(dto.getTipo());
        equipo.setMarca(dto.getMarca());
        equipo.setModelo(dto.getModelo());
        equipo.setProcesador(dto.getProcesador());
        equipo.setMemoriaRam(dto.getMemoriaRam());
        equipo.setAlmacenamiento(dto.getAlmacenamiento());
        equipo.setTarjetaGrafica(dto.getTarjetaGrafica());
        equipo.setNumeroSerie(dto.getNumeroSerie());
        equipo.setObservacionesFisicas(dto.getObservacionesFisicas());

        Equipo equipoGuardado = repository.save(equipo);
        return convertirAEntityDTO(equipoGuardado);
    }

    private EquipoDTO convertirAEntityDTO(Equipo equipo) {
        EquipoDTO dto = new EquipoDTO();
        dto.setIdEquipo(equipo.getIdEquipo());
        dto.setIdPersona(equipo.getIdPersona());
        dto.setTipo(equipo.getTipo());
        dto.setMarca(equipo.getMarca());
        dto.setModelo(equipo.getModelo());
        dto.setProcesador(equipo.getProcesador());
        dto.setMemoriaRam(equipo.getMemoriaRam());
        dto.setAlmacenamiento(equipo.getAlmacenamiento());
        dto.setTarjetaGrafica(equipo.getTarjetaGrafica());
        dto.setNumeroSerie(equipo.getNumeroSerie());
        dto.setObservacionesFisicas(equipo.getObservacionesFisicas());
        return dto;
    }
}
