CREATE TABLE inventario (
                            id_insumo BIGINT AUTO_INCREMENT PRIMARY KEY,
                            sku VARCHAR(50) NOT NULL UNIQUE,
                            nombre VARCHAR(150) NOT NULL,
                            stock_actual INT NOT NULL CHECK (stock_actual >= 0),
                            unidad_medida VARCHAR(20) NOT NULL,
                            precio_unitario DECIMAL(10,2) NOT NULL
);