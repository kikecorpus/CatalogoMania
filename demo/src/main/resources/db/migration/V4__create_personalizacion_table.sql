CREATE TABLE personalizacion (
    id_personalizacion BIGSERIAL PRIMARY KEY,
    nombre_personalizacion VARCHAR(100),
    logotipo VARCHAR(500),
    color_principal VARCHAR(20),
    color_secundario VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);
