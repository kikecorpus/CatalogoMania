package CatagoloEmprende.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "plantilla")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Diseño predefinido y reutilizable que un catálogo puede usar")
public class Plantilla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plantilla")
    @Schema(description = "Identificador único de la plantilla", example = "4")
    private Long id;

    @NotBlank
    @Column(name = "nombre_plantilla", nullable = false, length = 100)
    @Schema(description = "Nombre de la plantilla", example = "Minimal")
    private String nombrePlantilla;

    @Builder.Default
    @Column(name = "estado", nullable = false, length = 30)
    @Schema(description = "Estado de la plantilla", example = "ACTIVO")
    private String estado = "ACTIVO";

    @Builder.Default
    @Column(name = "gratis", nullable = false)
    @Schema(description = "Indica si la plantilla está disponible en el plan gratuito", example = "true")
    private Boolean gratis = true;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_p_id", nullable = false)
    @Schema(description = "Categoría a la que pertenece esta plantilla")
    private CategoriaP categoriaP;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "plantilla")
    @Builder.Default
    @Schema(description = "Catálogos que están usando esta plantilla")
    private Set<Catalogo> catalogos = new HashSet<>();
}
