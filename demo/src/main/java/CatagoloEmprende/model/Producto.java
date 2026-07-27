package CatagoloEmprende.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Producto de una tienda, incluible en uno o varios catálogos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    @Schema(description = "Identificador único del producto", example = "10")
    private Long id;

    @NotBlank
    @Column(name = "nombre_producto", nullable = false, length = 150)
    @Schema(description = "Nombre del producto", example = "Mesa de comedor")
    private String nombreProducto;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    @Schema(description = "Descripción detallada del producto")
    private String descripcion;

    @NotNull
    @Positive
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    @Schema(description = "Precio de venta del producto", example = "150000.00")
    private BigDecimal precio;

    @Column(name = "img_url", length = 500)
    @Schema(description = "URL de la imagen del producto (Cloudinary)")
    private String imgUrl;

    @Column(name = "created_at", updatable = false)
    @Schema(description = "Fecha de creación del registro", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Schema(description = "Fecha de última actualización", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    // Relaciones

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tienda_id", nullable = false)
    @Schema(description = "Tienda dueña del producto")
    private Tienda tienda;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name = "producto_catalogo",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "catalogo_id")
    )
    @Builder.Default
    @Schema(description = "Catálogos en los que aparece este producto")
    private Set<Catalogo> catalogos = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name = "producto_categoria",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    @Builder.Default
    @Schema(description = "Categorías a las que pertenece el producto")
    private Set<Categoria> categorias = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @Schema(description = "Especificaciones del producto (dimensiones, peso, material, etc.)")
    private Set<ProductoMedida> especificaciones = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
