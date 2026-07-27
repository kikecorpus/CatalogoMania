package CatagoloEmprende.mapper;

import CatagoloEmprende.model.CategoriaP;
import CatagoloEmprende.dto.CategoriaPDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoriaPMapper {

    public CategoriaP toEntity(CategoriaPDTO.Request dto) {
        return CategoriaP.builder()
                .nombreCategoria(dto.nombreCategoria())
                .descripcion(dto.descripcion())
                .build();
    }

    public CategoriaPDTO.Response toResponse(CategoriaP categoriaP) {
        return new CategoriaPDTO.Response(categoriaP.getId(), categoriaP.getNombreCategoria(), categoriaP.getDescripcion());
    }
}
