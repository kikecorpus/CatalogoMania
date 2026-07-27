CREATE TABLE catalogo (
    id_catalogo BIGSERIAL PRIMARY KEY,
    nombre_catalogo VARCHAR(150) NOT NULL,
    cantidad_producto INT NOT NULL DEFAULT 0,
    estado VARCHAR(30) NOT NULL DEFAULT 'ACTIVO',
    tienda_id BIGINT NOT NULL,
    plantilla_id BIGINT NOT NULL,
    personalizacion_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT fk_catalogo_tienda
        FOREIGN KEY (tienda_id) REFERENCES tienda (id_tienda),
    CONSTRAINT fk_catalogo_plantilla
        FOREIGN KEY (plantilla_id) REFERENCES plantilla (id_plantilla),
    CONSTRAINT fk_catalogo_personalizacion
        FOREIGN KEY (personalizacion_id) REFERENCES personalizacion (id_personalizacion),
    CONSTRAINT uq_catalogo_personalizacion UNIQUE (personalizacion_id)
);

CREATE INDEX idx_catalogo_tienda ON catalogo (tienda_id);
CREATE INDEX idx_catalogo_plantilla ON catalogo (plantilla_id);
