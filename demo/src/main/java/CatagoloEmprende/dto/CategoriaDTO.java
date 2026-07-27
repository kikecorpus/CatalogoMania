package CatagoloEmprende.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class CategoriaDTO {

    @Schema(name = "CategoriaRequest", description = "Datos para crear o actualizar una categoría de producto")
    public record Request(
            @NotBlank
            @Schema(example = "Muebles")
            String nombreCategoria,
            String descripcion
    ) {}

    @Schema(name = "CategoriaResponse")
    public record Response(
            Long id,
            String nombreCategoria,
            String descripcion
    ) {}
}
