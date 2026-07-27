package CatagoloEmprende.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class CategoriaPDTO {

    @Schema(name = "CategoriaPRequest", description = "Datos para crear o actualizar una categoría de plantilla")
    public record Request(
            @NotBlank
            @Schema(example = "Comida")
            String nombreCategoria,
            String descripcion
    ) {}

    @Schema(name = "CategoriaPResponse")
    public record Response(
            Long id,
            String nombreCategoria,
            String descripcion
    ) {}
}
