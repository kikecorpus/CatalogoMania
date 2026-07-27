package CatagoloEmprende.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "producto_medida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Especificación concreta de un producto para un tipo de medida dado (ej. Peso = 50 lbs)")
public class ProductoMedida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_espesifica")
    @Schema(description = "Identificador único de la especificación", example = "5")
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    @Schema(description = "Producto al que pertenece esta especificación")
    private Producto producto;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medida_id", nullable = false)
    @Schema(description = "Tipo de medida (ej. Peso, Dimensiones, Material)")
    private Medida medida;

    @NotBlank
    @Column(name = "valor", nullable = false, length = 150)
    @Schema(description = "Valor concreto de la especificación", example = "130cm x 200cm")
    private String valor;
}
