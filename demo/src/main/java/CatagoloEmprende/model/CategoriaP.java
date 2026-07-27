package CatagoloEmprende.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categoria_p")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Categoría usada para clasificar plantillas (ej. Comida, Ropa, Minimalista)")
public class CategoriaP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria_p")
    @Schema(description = "Identificador único de la categoría de plantilla", example = "1")
    private Long id;

    @NotBlank
    @Column(name = "nombre_categoria", nullable = false, length = 100)
    @Schema(description = "Nombre de la categoría de plantilla", example = "Comida")
    private String nombreCategoria;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    @Schema(description = "Descripción opcional")
    private String descripcion;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "categoriaP")
    @Builder.Default
    @Schema(description = "Plantillas que pertenecen a esta categoría")
    private Set<Plantilla> plantillas = new HashSet<>();
}
