package com.fixnow.ms_auth.Controller;

import com.fixnow.ms_auth.DTO.AuthRequestDTO;
import com.fixnow.ms_auth.DTO.AuthResponseDTO;
import com.fixnow.ms_auth.Model.UsuarioCredencial;
import com.fixnow.ms_auth.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "API Auth", description = "Control de acceso y credenciales de usuarios")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "iniciar sesión", description = "Valida las credenciales de un usuario y devuelve login exitoso")
    @ApiResponse(responseCode = "200", description = "Login exitoso")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas (No autorizado)")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequestDTO request) {
        log.info("POST recibido en /api/v1/auth/login");

        AuthResponseDTO response = authService.loginManual(request);

        if (response == null) {
            log.error("401: fallo en la autenticación");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            log.info("200: autenticación exitosa");
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("")
    @Operation(summary = "listar usuarios", description = "obtiene todos los usuarios con credenciales registradas")
    @ApiResponse(responseCode = "200", description = "lista enviada correctamente")
    @ApiResponse(responseCode = "204", description = "no hay usuarios en la base de datos")
    public ResponseEntity<List<UsuarioCredencial>> listar() {
        log.info("GET recibido en /api/v1/auth");
        List<UsuarioCredencial> usuarios = authService.listarTodos();

        if (usuarios.isEmpty()) {
            log.warn("204: no hay usuarios en la bdd");
            return ResponseEntity.noContent().build();
        } else {
            log.info("200: lista de usuarios enviada");
            return ResponseEntity.ok(usuarios);
        }
    }
}
