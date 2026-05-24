CREATE TABLE notificaciones (
                                id_notificacion INT AUTO_INCREMENT PRIMARY KEY,
                                id_ticket INT NOT NULL,
                                correo_destino VARCHAR(100) NOT NULL,
                                mensaje VARCHAR(255) NOT NULL,
                                tipo_notificacion VARCHAR(50) NOT NULL, -- Ej: 'PAGO_RECIBIDO', 'EQUIPO_REPARADO'
                                fecha_envio DATETIME NOT NULL
);

-- Insertamos un dato de prueba
INSERT INTO notificaciones (id_ticket, correo_destino, mensaje, tipo_notificacion, fecha_envio)
VALUES (1, 'cliente@prueba.com', 'Su equipo ha sido ingresado a diagnóstico.', 'INGRESO', NOW());