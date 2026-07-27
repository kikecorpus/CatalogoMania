package CatagoloEmprende.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "catalogo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Catálogo publicado por una tienda, generado a partir de una plantilla y personalización")
public class Catalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_catalogo")
    @Schema(description = "Identificador único del catálogo", example = "8")
    private Long id;

    @NotBlank
    @Column(name = "nombre_catalogo", nullable = false, length = 150)
    @Schema(description = "Nombre del catálogo", example = "Catálogo Verano 2026")
    private String nombreCatalogo;

    @Builder.Default
    @Column(name = "cantidad_producto", nullable = false)
    @Schema(description = "Cantidad de productos incluidos en el catálogo", example = "12")
    private Integer cantidadProducto = 0;

    @Builder.Default
    @Column(name = "estado", nullable = false, length = 30)
    @Schema(description = "Estado del catálogo", example = "ACTIVO")
    private String estado = "ACTIVO";

    @Column(name = "created_at", updatable = false)
    @Schema(description = "Fecha de creación del catálogo", accessMode = Schema.AccessMode.READ_ONLY)
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
    @Schema(description = "Tienda dueña de este catálogo")
    private Tienda tienda;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plantilla_id", nullable = false)
    @Schema(description = "Plantilla de diseño usada por este catálogo")
    private Plantilla plantilla;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "personalizacion_id", nullable = false, unique = true)
    @Schema(description = "Personalización visual (logo, colores) de este catálogo")
    private Personalizacion personalizacion;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "catalogos")
    @Builder.Default
    @Schema(description = "Productos incluidos en este catálogo")
    private Set<Producto> productos = new HashSet<>();

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
