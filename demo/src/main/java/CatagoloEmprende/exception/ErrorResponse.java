package CatagoloEmprende.exception;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Formato estándar y consistente para TODOS los errores que devuelve la API.
 * Así el frontend siempre sabe qué estructura esperar, sin importar qué
 * salió mal.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String mensaje,
        List<String> detalles
) {
    public ErrorResponse(int status, String error, String mensaje) {
        this(LocalDateTime.now(), status, error, mensaje, null);
    }

    public ErrorResponse(int status, String error, String mensaje, List<String> detalles) {
        this(LocalDateTime.now(), status, error, mensaje, detalles);
    }
}
