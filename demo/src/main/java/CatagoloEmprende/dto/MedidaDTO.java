package CatagoloEmprende.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class MedidaDTO {

    @Schema(name = "MedidaRequest", description = "Datos para crear o actualizar un tipo de medida")
    public record Request(
            @NotBlank
            @Schema(example = "Peso")
            String nombreMedida,
            String descripcion
    ) {}

    @Schema(name = "MedidaResponse")
    public record Response(
            Long id,
            String nombreMedida,
            String descripcion
    ) {}
}
