package com.servicio.tecnico.mscliente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "clientes")
@Data

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Es long para evitar errores de datos con int, long recubre int y en ms-ticket igual es long

    @NotBlank(message = "El RUT es obligatorio")
    @Column(unique = true,nullable = false)
    private String rut;

    @NotBlank(message = "El Nombre es obligatorio")
    private String nombre;
//No es necesario el segundo nombre ni apellido.
    @NotBlank(message = "El Apellido paterno es obligatorio")
    private String apellidoPaterno;

    //Email
    @Email(message = "El formato del Email es invalido")
    @NotBlank(message = "El Email es obligatorio")
    private String email;

    @NotBlank(message = "El Telefono es obligartorio")
    private String telefono;

    private String direccion;

}//fin
