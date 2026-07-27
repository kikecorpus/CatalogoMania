-- Relación "contiene": qué productos aparecen en qué catálogos
CREATE TABLE producto_catalogo (
    producto_id BIGINT NOT NULL,
    catalogo_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY (producto_id, catalogo_id),
    CONSTRAINT fk_prodcat_producto
        FOREIGN KEY (producto_id) REFERENCES producto (id_producto),
    CONSTRAINT fk_prodcat_catalogo
        FOREIGN KEY (catalogo_id) REFERENCES catalogo (id_catalogo)
);

-- Relación "se clasifica en": un producto puede tener varias categorías
CREATE TABLE producto_categoria (
    producto_id BIGINT NOT NULL,
    categoria_id BIGINT NOT NULL,
    PRIMARY KEY (producto_id, categoria_id),
    CONSTRAINT fk_prodcateg_producto
        FOREIGN KEY (producto_id) REFERENCES producto (id_producto),
    CONSTRAINT fk_prodcateg_categoria
        FOREIGN KEY (categoria_id) REFERENCES categoria (id_categoria)
);

-- Relación "especifica": especificaciones del producto (dimensiones, peso, material, etc.)
-- Tiene id propio porque el mismo producto puede tener varias filas con la misma medida
-- (ej. dos especificaciones distintas de "Dimensiones" si aplicara) y porque lleva atributo propio (valor)
CREATE TABLE producto_medida (
    id_espesifica BIGSERIAL PRIMARY KEY,
    producto_id BIGINT NOT NULL,
    medida_id BIGINT NOT NULL,
    valor VARCHAR(150) NOT NULL,
    CONSTRAINT fk_prodmed_producto
        FOREIGN KEY (producto_id) REFERENCES producto (id_producto),
    CONSTRAINT fk_prodmed_medida
        FOREIGN KEY (medida_id) REFERENCES medida (id_medida)
);

CREATE INDEX idx_prodmed_producto ON producto_medida (producto_id);
CREATE INDEX idx_prodcateg_producto ON producto_categoria (producto_id);
CREATE INDEX idx_prodcat_catalogo ON producto_catalogo (catalogo_id);
