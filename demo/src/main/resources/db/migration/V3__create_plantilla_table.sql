CREATE TABLE plantilla (
    id_plantilla BIGSERIAL PRIMARY KEY,
    nombre_plantilla VARCHAR(100) NOT NULL,
    estado VARCHAR(30) NOT NULL DEFAULT 'ACTIVO',
    gratis BOOLEAN NOT NULL DEFAULT TRUE,
    categoria_p_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT fk_plantilla_categoria_p
        FOREIGN KEY (categoria_p_id) REFERENCES categoria_p (id_categoria_p)
);

CREATE INDEX idx_plantilla_categoria_p ON plantilla (categoria_p_id);
