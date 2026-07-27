package CatagoloEmprende.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EspecificacionDTO {

    @Schema(name = "EspecificacionRequest", description = "Especificación a asignar a un producto")
    public record Request(
            @NotNull
            @Schema(description = "Id del tipo de medida (ej. Peso, Dimensiones)", example = "2")
            Long medidaId,
            @NotBlank
            @Schema(example = "50 lbs")
            String valor
    ) {}

    @Schema(name = "EspecificacionResponse", description = "Especificación de un producto ya guardada")
    public record Response(
            Long id,
            @Schema(description = "Nombre del tipo de medida", example = "Peso")
            String nombreMedida,
            String valor
    ) {}
}
