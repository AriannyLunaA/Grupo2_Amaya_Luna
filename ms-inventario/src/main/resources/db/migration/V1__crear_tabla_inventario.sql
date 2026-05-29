CREATE TABLE inventario (
                            id_repuesto INT AUTO_INCREMENT PRIMARY KEY,
                            nombre VARCHAR(100) NOT NULL,
                            descripcion VARCHAR(255) NULL,
                            stock INT NOT NULL,
                            precio_unitario INT NOT NULL -- <-- Cambiado a INT
);

INSERT INTO inventario (nombre, descripcion, stock, precio_unitario)
VALUES ('Pasta Térmica Arctic MX-4', 'pasta térmica para CPU y GPU.', 50, 8990);

INSERT INTO inventario (nombre, descripcion, stock, precio_unitario)
VALUES ('Memoria RAM DDR4 8GB Crucial', 'memoria RAM para notebook 3200MHz.', 15, 24990);

INSERT INTO inventario (nombre, descripcion, stock, precio_unitario)
VALUES ('Disco Estado Sólido SSD 500GB Kingston', 'almacenamiento NVMe PCIe M.2.', 10, 38990);