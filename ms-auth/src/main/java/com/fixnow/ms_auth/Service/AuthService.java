package com.fixnow.ms_auth.Service;

import com.fixnow.ms_auth.DTO.AuthRequestDTO;
import com.fixnow.ms_auth.DTO.AuthResponseDTO;
import com.fixnow.ms_auth.Model.UsuarioCredencial;
import com.fixnow.ms_auth.Repository.UsuarioCredencialRepository;
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

    public AuthResponseDTO loginManual(AuthRequestDTO request) {
        log.info("buscando credenciales para el usuario: {}", request.getUsername());
        Optional<UsuarioCredencial> usuarioOpt = repository.findByUsername(request.getUsername());

        if (usuarioOpt.isPresent()) {
            UsuarioCredencial usuario = usuarioOpt.get();

            if (usuario.getPassword().equals(request.getPassword())) {
                log.info("login exitoso para el usuario: {}", request.getUsername());
                return new AuthResponseDTO(
                        usuario.getId(),
                        usuario.getUsername(),
                        usuario.getRol(),
                        usuario.getPerfilId(),
                        "Login Exitoso"
                );
            } else {
                log.warn("contraseña incorrecta para el usuario: {}", request.getUsername());
            }
        } else {
            log.warn("usuario no encontrado: {}", request.getUsername());
        }


        return null;
    }


    public List<UsuarioCredencial> listarTodos() {
        log.info("obteniendo todos los usuarios registrados en la base de datos");
        return repository.findAll();
    }
}
