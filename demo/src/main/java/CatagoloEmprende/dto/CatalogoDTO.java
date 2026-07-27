package CatagoloEmprende.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CatalogoDTO {

    @Schema(name = "CatalogoRequest", description = "Datos para crear un catálogo")
    public record Request(
            @NotBlank
            @Schema(example = "Catálogo Verano 2026")
            String nombreCatalogo,

            @NotNull
            @Schema(description = "Id de la plantilla elegida", example = "4")
            Long plantillaId,

            @NotNull
            PersonalizacionDTO.Request personalizacion
    ) {}

    @Schema(name = "CatalogoResponse", description = "Datos públicos de un catálogo")
    public record Response(
            Long id,
            String nombreCatalogo,
            Integer cantidadProducto,
            String estado,
            Long tiendaId,

            @Schema(description = "Plantilla usada por este catálogo")
            PlantillaDTO.Response plantilla,

            PersonalizacionDTO.Response personalizacion,
            List<ProductoDTO.Response> productos
    ) {}
}
