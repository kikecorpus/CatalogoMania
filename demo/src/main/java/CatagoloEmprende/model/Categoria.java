package CatagoloEmprende.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Categoría usada para clasificar productos (ej. Muebles, Ofertas)")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    @Schema(description = "Identificador único de la categoría", example = "3")
    private Long id;

    @NotBlank
    @Column(name = "nombre_categoria", nullable = false, length = 100)
    @Schema(description = "Nombre de la categoría", example = "Muebles")
    private String nombreCategoria;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    @Schema(description = "Descripción opcional de la categoría")
    private String descripcion;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "categorias")
    @Builder.Default
    @Schema(description = "Productos que pertenecen a esta categoría")
    private Set<Producto> productos = new HashSet<>();
}
