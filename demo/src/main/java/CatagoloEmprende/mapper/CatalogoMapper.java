package CatagoloEmprende.mapper;

import CatagoloEmprende.dto.CatalogoDTO;
import CatagoloEmprende.dto.PersonalizacionDTO;
import CatagoloEmprende.model.Catalogo;
import CatagoloEmprende.model.Personalizacion;
import CatagoloEmprende.mapper.PlantillaMapper;
import CatagoloEmprende.mapper.ProductoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CatalogoMapper {

    private final ProductoMapper productoMapper;
    private final PlantillaMapper plantillaMapper;

    public Catalogo toEntity(CatalogoDTO.Request dto) {
        Personalizacion personalizacion = Personalizacion.builder()
                .logotipo(dto.personalizacion().logotipo())
                .colorPrincipal(dto.personalizacion().colorPrincipal())
                .colorSecundario(dto.personalizacion().colorSecundario())
                .build();

        return Catalogo.builder()
                .nombreCatalogo(dto.nombreCatalogo())
                .personalizacion(personalizacion)
                .build();
    }

    public CatalogoDTO.Response toResponse(Catalogo catalogo) {
        return new CatalogoDTO.Response(
                catalogo.getId(),
                catalogo.getNombreCatalogo(),
                catalogo.getCantidadProducto(),
                catalogo.getEstado(),
                catalogo.getTienda().getId(),
                plantillaMapper.toResponse(catalogo.getPlantilla()),
                toPersonalizacionResponse(catalogo.getPersonalizacion()),
                catalogo.getProductos().stream().map(productoMapper::toResponse).toList()
        );
    }

    private PersonalizacionDTO.Response toPersonalizacionResponse(Personalizacion personalizacion) {
        return new PersonalizacionDTO.Response(
                personalizacion.getId(),
                personalizacion.getLogotipo(),
                personalizacion.getColorPrincipal(),
                personalizacion.getColorSecundario()
        );
    }
}
