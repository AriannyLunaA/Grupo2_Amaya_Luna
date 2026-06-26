package com.fixnow.msauth.Service;

import com.fixnow.msauth.Client.PersonaClient;
import com.fixnow.msauth.DTO.AuthRequestDTO;
import com.fixnow.msauth.DTO.AuthResponseDTO;
import com.fixnow.msauth.DTO.PersonaDTO;
import com.fixnow.msauth.Model.UsuarioCredencial;
import com.fixnow.msauth.Repository.UsuarioCredencialRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private UsuarioCredencialRepository repository;

    @Autowired
    private PersonaClient personaClient;

    public AuthResponseDTO loginManual(AuthRequestDTO request) {
        log.info("buscando credenciales para el usuario: {}", request.getUsername());
        Optional<UsuarioCredencial> usuarioOpt = repository.findByUsername(request.getUsername());

        if (usuarioOpt.isPresent()) {
            UsuarioCredencial usuario = usuarioOpt.get();

            if (usuario.getPassword().equals(request.getPassword())) {
                log.info("login exitoso para el usuario: {}", request.getUsername());

                AuthResponseDTO response = new AuthResponseDTO();
                response.setId(usuario.getId());
                response.setUsername(usuario.getUsername());
                response.setRol(usuario.getRol());
                response.setPerfilId(usuario.getPerfilId());
                response.setMensaje("Login Exitoso");


                try {
                    log.info("consultando a ms-persona para el PerfilId: {}", usuario.getPerfilId());
                    PersonaDTO persona = personaClient.buscarPorId(usuario.getPerfilId());

                    if (persona != null) {
                        response.setRut(persona.getRut());
                        response.setNombres(persona.getNombres());
                        response.setApellidos(persona.getApellidos());
                        response.setCorreo(persona.getCorreo());
                        log.info("datos de persona agregados al response exitosamente");
                    }
                } catch (Exception e) {
                    log.error("error al comunicarse con ms-persona {}", e.getMessage());
                    response.setMensaje("login exitoso: sin datos del perfil por problemas en la conexion");
                }
                return response;
            } else {
                log.warn("contraseña incorrecta para el usuario: {}", request.getUsername());
            }
        } else {
            log.warn("usuario no encontrado {}", request.getUsername());
        }
        return null;
    }

    public List<UsuarioCredencial> listarTodos() {
        log.info("obteniendo todos los usuarios registrados en la bdd");
        return repository.findAll();
    }

    /*ignore esto profesor, es lo que hicimos en la prueba el lunes
    public List<UsuarioCredencial> findByRol(String rol) {
        log.info("obteniendo los usuarios por rol registrados en la bdd");
        return repository.findByRol(rol);
    }
    */

}