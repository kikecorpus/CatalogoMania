package CatagoloEmprende.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "personalizacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Personalización visual (logo y colores) aplicada a un catálogo específico")
public class Personalizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_personalizacion")
    @Schema(description = "Identificador único de la personalización", example = "7")
    private Long id;

    @Column(name = "nombre_personalizacion", length = 100)
    @Schema(description = "Nombre interno de la personalización (opcional)")
    private String nombrePersonalizacion;

    @Column(name = "logotipo", length = 500)
    @Schema(description = "URL del logo de la tienda para este catálogo")
    private String logotipo;

    @Column(name = "color_principal", length = 20)
    @Schema(description = "Color principal en formato hexadecimal", example = "#FF5733")
    private String colorPrincipal;

    @Column(name = "color_secundario", length = 20)
    @Schema(description = "Color secundario en formato hexadecimal", example = "#1A1A1A")
    private String colorSecundario;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "personalizacion")
    @Schema(description = "Catálogo al que pertenece esta personalización", accessMode = Schema.AccessMode.READ_ONLY)
    private Catalogo catalogo;
}
