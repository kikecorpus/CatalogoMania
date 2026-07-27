package CatagoloEmprende.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Le indica a Swagger UI que esta API usa tokens JWT tipo Bearer, lo cual
 * hace aparecer el botón "Authorize" 🔒 arriba a la derecha en la interfaz.
 * Sin esta anotación, Swagger no tiene forma de saber cómo se autentican
 * las peticiones, aunque Spring Security ya lo esté exigiendo.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Catálogos API",
                version = "1.0",
                description = "API para gestión de catálogos de productos",
                contact = @io.swagger.v3.oas.annotations.info.Contact(name = "Equipo Catálogo", email = "soporte@catalogomania.local")
        ),
        security = @SecurityRequirement(name = "bearerAuth"),
        tags = {
                @io.swagger.v3.oas.annotations.tags.Tag(name = "Autenticación", description = "Login y generación de tokens JWT"),
                @io.swagger.v3.oas.annotations.tags.Tag(name = "Catálogos", description = "Creación y gestión de catálogos de la tienda autenticada"),
                @io.swagger.v3.oas.annotations.tags.Tag(name = "Tiendas", description = "Registro y consulta de tiendas"),
                @io.swagger.v3.oas.annotations.tags.Tag(name = "Productos", description = "CRUD de productos de la tienda autenticada"),
                @io.swagger.v3.oas.annotations.tags.Tag(name = "Categorías", description = "Catálogo de categorías de producto"),
                @io.swagger.v3.oas.annotations.tags.Tag(name = "Categorías de plantilla", description = "Catálogo de categorías usadas para clasificar plantillas"),
                @io.swagger.v3.oas.annotations.tags.Tag(name = "Plantillas", description = "Diseños disponibles para crear catálogos"),
                @io.swagger.v3.oas.annotations.tags.Tag(name = "Medidas", description = "Catálogo de tipos de medida/especificación (Peso, Dimensiones, etc.)")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
}
