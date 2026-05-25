CREATE TABLE equipos (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         marca VARCHAR(50) NOT NULL,
                         modelo VARCHAR(50) NOT NULL,
                         numero_serie VARCHAR(100) UNIQUE NOT NULL,
                         estado VARCHAR(30) NOT NULL,
                         fecha_ingreso TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);