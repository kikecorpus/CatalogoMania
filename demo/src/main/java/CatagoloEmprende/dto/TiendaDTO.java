package CatagoloEmprende.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TiendaDTO {

    @Schema(name = "TiendaRegistroRequest", description = "Datos necesarios para registrar una nueva tienda")
    public record RegistroRequest(
            @NotBlank
            @Schema(example = "La Bodeguita")
            String nombreTienda,
            String telefono,
            @NotBlank @Email
            @Schema(example = "tienda@correo.com")
            String correo,
            String direccion,
            @NotBlank @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
            String password
    ) {}

    @Schema(name = "TiendaResponse", description = "Datos públicos de una tienda")
    public record Response(
            Long id,
            String nombreTienda,
            String telefono,
            String correo,
            String direccion,
            Boolean admin
    ) {}
}
