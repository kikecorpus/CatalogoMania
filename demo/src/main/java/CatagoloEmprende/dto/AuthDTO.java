package CatagoloEmprende.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class AuthDTO {

    @Schema(name = "AuthLoginRequest", description = "Credenciales para iniciar sesión")
    public record LoginRequest(
            @NotBlank String correo,
            @NotBlank String password
    ) {}

    @Schema(name = "AuthLoginResponse", description = "Token de acceso generado tras un login exitoso")
    public record LoginResponse(
            String token,
            @Schema(example = "Bearer") String tipo
    ) {
        public LoginResponse(String token) {
            this(token, "Bearer");
        }
    }
}
