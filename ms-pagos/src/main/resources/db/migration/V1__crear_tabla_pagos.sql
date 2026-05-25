CREATE TABLE pagos (
                       id_pago INT AUTO_INCREMENT PRIMARY KEY,
                       id_ticket BIGINT NOT NULL,
                       monto_total DOUBLE NOT NULL,
                       metodo_pago VARCHAR(50) NOT NULL,
                       estado VARCHAR(50) NOT NULL,
                       fecha_pago DATETIME NOT NULL
);

INSERT INTO pagos (id_ticket, monto_total, metodo_pago, estado, fecha_pago)
VALUES (1, 45000.0, 'TRANSFERENCIA', 'PAGADO', NOW()),
       (2, 25000.0, 'EFECTIVO', 'PENDIENTE', NOW());