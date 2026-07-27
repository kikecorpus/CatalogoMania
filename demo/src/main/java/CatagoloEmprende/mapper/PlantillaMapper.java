package CatagoloEmprende.mapper;

import CatagoloEmprende.model.Plantilla;
import CatagoloEmprende.dto.PlantillaDTO;
import CatagoloEmprende.mapper.CategoriaPMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlantillaMapper {

    private final CategoriaPMapper categoriaPMapper;

    // No asigna categoriaP aquí: el Service la busca por id y la asigna,
    // porque el Mapper no debe depender de repositorios.
    public Plantilla toEntity(PlantillaDTO.Request dto) {
        return Plantilla.builder()
                .nombrePlantilla(dto.nombrePlantilla())
                .estado(dto.estado() != null ? dto.estado() : "ACTIVO")
                .gratis(dto.gratis() != null ? dto.gratis() : true)
                .build();
    }

    public PlantillaDTO.Response toResponse(Plantilla plantilla) {
        return new PlantillaDTO.Response(
                plantilla.getId(),
                plantilla.getNombrePlantilla(),
                plantilla.getEstado(),
                plantilla.getGratis(),
                categoriaPMapper.toResponse(plantilla.getCategoriaP())
        );
    }
}
