package com.servicio.tecnico.mstickets.model;

import jakarta.persistence.*; // Importa las anotaciones para mapear la clase a la base de datos
import jakarta.validation.constraints.*; // Importa las validaciones (Requerimiento de rúbrica)
import lombok.Data; // Importa Lombok para generar Getters y Setters automáticamente
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime; // Importa el manejo de fechas y horas

@Entity // Define que esta clase es una entidad de JPA (una tabla en la DB)
@Table(name = "tickets") // Especifica que la tabla en MySQL se llamará "tickets"
@Data // Genera automáticamente métodos toString, equals, hashCode, getters y setters
@EntityListeners(AuditingEntityListener.class)
public class Ticket {

    @Id // Define este atributo como la Llave Primaria (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el ID es autoincremental en MySQL
    private Long id;

    @NotBlank(message = "La descripción del problema es obligatoria") // Valida que no sea nulo ni vacío
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres") // Restringe el largo
    @Column(nullable = false, length = 500) // Configura la columna en la base de datos
    private String descripcion;

    @NotBlank(message = "El estado de ingreso es obligatorio") // Valida que se registre el estado inicial
    @Column(nullable = false) // Asegura que en la base de datos este campo nunca sea NULL
    private String estado; // Aquí registras si llega "Dañado", "Mantención", etc.

    @Column(name = "fecha_creacion", updatable = false) // El nombre en la DB y evita que se cambie al editar
    @CreatedDate //Nativo de spring para auditoria
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    @LastModifiedDate //Se actuasliza solo cuendo se edita
    private LocalDateTime fechaModificacion;

    @Column(name = "fechaCierre")
    private LocalDateTime fechaCierre; //se llena manualmente al cierre del ticket


    } //fin
