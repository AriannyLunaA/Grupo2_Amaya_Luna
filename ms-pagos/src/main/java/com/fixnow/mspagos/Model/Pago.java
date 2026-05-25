package com.fixnow.mspagos.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;

    @NotNull(message = "el id del ticket es obligatorio")
    @Positive(message = "el id del ticket debe ser un número positivo")
    @Column(name = "id_ticket", nullable = false)
    private Long idTicket;

    @NotNull(message = "el monto total no puede ser nulo")
    @Positive(message = "el monto a pagar debe ser mayor a cero")
    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @NotBlank(message = "el método de pago es obligatorio")
    @Column(name = "metodo_pago", nullable = false, length = 50)
    private String metodoPago;

    @NotBlank(message = "el estado del pago es obligatorio")
    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;
}
