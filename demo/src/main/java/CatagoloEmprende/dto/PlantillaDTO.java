package CatagoloEmprende.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PlantillaDTO {

    @Schema(name = "PlantillaRequest", description = "Datos para crear o actualizar una plantilla")
    public record Request(
            @NotBlank
            @Schema(example = "Minimal")
            String nombrePlantilla,
            @Schema(example = "ACTIVO") String estado,
            @Schema(example = "true") Boolean gratis,
            @NotNull
            @Schema(description = "Id de la categoría de plantilla", example = "1")
            Long categoriaPId
    ) {}

    @Schema(name = "PlantillaResponse")
    public record Response(
            Long id,
            String nombrePlantilla,
            String estado,
            Boolean gratis,
            CategoriaPDTO.Response categoriaP
    ) {}
}
