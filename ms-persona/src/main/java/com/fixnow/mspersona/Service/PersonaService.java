package com.fixnow.mspersona.Service;

import com.fixnow.mspersona.Model.Persona;
import com.fixnow.mspersona.Repository.PersonaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    public List<Persona> listarPersonas() {
        log.info("listando todas las personas");
        return personaRepository.findAll();
    }

    public Persona buscarPorId(Long idPersona) {
        log.info("buscando persona con id: {}", idPersona);
        return personaRepository.findById(idPersona).orElse(null);
    }

    public Persona agregar(Persona nueva) {
        log.info("guardando nueva persona con rut: {}", nueva.getRut());
        return personaRepository.save(nueva);
    }

    public boolean eliminar(Long idPersona) {
        Persona persona = buscarPorId(idPersona);
        if (persona != null) {
            log.info("eliminando persona id: {}", idPersona);
            personaRepository.delete(persona);
            return true;
        } else {
            log.warn("no se encontro la persona id: {} para eliminar", idPersona);
            return false;
        }
    }

    public Persona updatePersona(Long idPersona, Persona persona) {
        Persona buscado = buscarPorId(idPersona);
        if (buscado != null) {
            log.info("modificando datos de persona id: {}", idPersona);
            buscado.setRut(persona.getRut());
            buscado.setNombres(persona.getNombres());
            buscado.setApellidos(persona.getApellidos());
            buscado.setCorreo(persona.getCorreo());
            buscado.setFechaNacimiento(persona.getFechaNacimiento());
            return personaRepository.save(buscado);
        } else {
            log.warn("no se pudo actualizar, id: {} no existe", idPersona);
            return null;
        }
    }
}