package com.fixnow.mspersona.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPersona;

    @NotNull(message = "el rut es obligatorio")
    @Size(max = 20)
    private String rut;

    @NotNull(message = "los nombres son obligatorios")
    @Size(max = 100)
    private String nombres;

    @NotNull(message = "los apellidos son obligatorios")
    @Size(max = 100)
    private String apellidos;

    @NotNull(message = "el correo es obligatorio")
    @Email(message = "correo invalido")
    @Size(max = 100)
    private String correo;

    @NotNull(message = "la fecha de nacimiento es obligatoria. Formato: YYYY-MM-DD ")
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
}
