CREATE TABLE tienda (
    id_tienda BIGSERIAL PRIMARY KEY,
    nombre_tienda VARCHAR(150) NOT NULL,
    telefono VARCHAR(30),
    correo VARCHAR(150) NOT NULL UNIQUE,
    direccion VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    admin BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);
