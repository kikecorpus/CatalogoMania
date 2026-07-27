package CatagoloEmprende.mapper;

import CatagoloEmprende.dto.CategoriaDTO;
import CatagoloEmprende.dto.EspecificacionDTO;
import CatagoloEmprende.dto.ProductoDTO;
import CatagoloEmprende.model.Categoria;
import CatagoloEmprende.model.Producto;
import CatagoloEmprende.model.ProductoMedida;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductoMapper {

    private final CategoriaMapper categoriaMapper;

    // No asigna categorías ni especificaciones aquí: el Service las busca
    // por id y las asigna, porque el Mapper no debe depender de repositorios.
    public Producto toEntity(ProductoDTO.Request dto) {
        return Producto.builder()
                .nombreProducto(dto.nombreProducto())
                .descripcion(dto.descripcion())
                .precio(dto.precio())
                .imgUrl(dto.imgUrl())
                .build();
    }

    public ProductoDTO.Response toResponse(Producto producto) {
        List<EspecificacionDTO.Response> especificaciones = producto.getEspecificaciones()
                .stream()
                .map(this::toEspecificacionResponse)
                .toList();

        List<CategoriaDTO.Response> categorias = producto.getCategorias()
                .stream()
                .map(categoriaMapper::toResponse)
                .toList();

        return new ProductoDTO.Response(
                producto.getId(),
                producto.getNombreProducto(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getImgUrl(),
                producto.getTienda().getId(),
                especificaciones,
                categorias
        );
    }

    private EspecificacionDTO.Response toEspecificacionResponse(ProductoMedida especificacion) {
        return new EspecificacionDTO.Response(
                especificacion.getId(),
                especificacion.getMedida().getNombreMedida(),
                especificacion.getValor()
        );
    }
}
