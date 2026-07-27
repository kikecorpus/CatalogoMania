CREATE TABLE producto (
    id_producto BIGSERIAL PRIMARY KEY,
    nombre_producto VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    img_url VARCHAR(500),
    tienda_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT fk_producto_tienda
        FOREIGN KEY (tienda_id) REFERENCES tienda (id_tienda)
);

CREATE INDEX idx_producto_tienda ON producto (tienda_id);
