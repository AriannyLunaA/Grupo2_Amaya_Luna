CREATE TABLE persona (
                          id_persona BIGINT AUTO_INCREMENT PRIMARY KEY,
                          rut VARCHAR(20) NOT NULL UNIQUE,
                          nombres VARCHAR(100) NOT NULL,
                          apellidos VARCHAR(100) NOT NULL,
                          correo VARCHAR(100) NOT NULL,
                          fecha_nacimiento DATE NOT NULL
);

INSERT INTO persona (rut, nombres, apellidos, correo, fecha_nacimiento)
VALUES ('26.774.777-4', 'Jesus', 'Amaya', 'jesus.amaya@fixnow.cl', '1999-06-02');

INSERT INTO persona (rut, nombres, apellidos, correo, fecha_nacimiento)
VALUES ('14.546.243-2', 'Valeria', 'Pastran', 'valeria.pastran@fixnow.cl', '2000-01-15');