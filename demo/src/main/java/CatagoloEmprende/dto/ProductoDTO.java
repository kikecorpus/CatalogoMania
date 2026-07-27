package CatagoloEmprende.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public class ProductoDTO {

    @Schema(name = "ProductoRequest", description = "Datos para crear o actualizar un producto")
    public record Request(
            @NotBlank
            @Schema(example = "Mesa de comedor")
            String nombreProducto,

            String descripcion,

            @NotNull
            @Positive
            @Schema(example = "150000.00")
            BigDecimal precio,

            String imgUrl,

            @Schema(description = "Lista de especificaciones (peso, dimensiones, etc.)")
            List<EspecificacionDTO.Request> especificaciones,

            @Schema(description = "Ids de las categorías a las que pertenece el producto", example = "[1, 3]")
            List<Long> categoriaIds
    ) {}

    @Schema(name = "ProductoResponse", description = "Datos públicos de un producto")
    public record Response(
            Long id,
            String nombreProducto,
            String descripcion,
            BigDecimal precio,
            String imgUrl,

            @Schema(description = "Id de la tienda dueña del producto")
            Long tiendaId,

            List<EspecificacionDTO.Response> especificaciones,

            @Schema(description = "Categorías a las que pertenece el producto")
            List<CategoriaDTO.Response> categorias
    ) {}
}
