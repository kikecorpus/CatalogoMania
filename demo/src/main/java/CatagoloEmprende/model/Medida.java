package CatagoloEmprende.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "medida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Tipo de especificación reutilizable (ej. Dimensiones, Peso, Material)")
public class Medida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medida")
    @Schema(description = "Identificador único del tipo de medida", example = "2")
    private Long id;

    @NotBlank
    @Column(name = "nombre_medida", nullable = false, length = 100)
    @Schema(description = "Nombre del tipo de medida", example = "Peso")
    private String nombreMedida;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    @Schema(description = "Descripción opcional del tipo de medida")
    private String descripcion;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "medida")
    @Builder.Default
    @Schema(description = "Especificaciones de productos que usan este tipo de medida")
    private Set<ProductoMedida> especificaciones = new HashSet<>();
}
