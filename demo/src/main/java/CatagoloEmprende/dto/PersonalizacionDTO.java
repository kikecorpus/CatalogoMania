package CatagoloEmprende.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class PersonalizacionDTO {

    @Schema(name = "PersonalizacionRequest", description = "Personalización visual del catálogo (logo y colores)")
    public record Request(
            String logotipo,
            @Schema(example = "#FF5733") String colorPrincipal,
            @Schema(example = "#1A1A1A") String colorSecundario
    ) {}

    @Schema(name = "PersonalizacionResponse")
    public record Response(
            Long id,
            String logotipo,
            String colorPrincipal,
            String colorSecundario
    ) {}
}
