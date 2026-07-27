package CatagoloEmprende.mapper;

import CatagoloEmprende.dto.CategoriaDTO;
import CatagoloEmprende.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaDTO.Request dto) {
        return Categoria.builder()
                .nombreCategoria(dto.nombreCategoria())
                .descripcion(dto.descripcion())
                .build();
    }

    public CategoriaDTO.Response toResponse(Categoria categoria) {
        return new CategoriaDTO.Response(categoria.getId(), categoria.getNombreCategoria(), categoria.getDescripcion());
    }
}
