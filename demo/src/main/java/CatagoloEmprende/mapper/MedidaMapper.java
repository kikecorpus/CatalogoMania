package CatagoloEmprende.mapper;

import CatagoloEmprende.dto.MedidaDTO;
import CatagoloEmprende.model.Medida;
import org.springframework.stereotype.Component;

@Component
public class MedidaMapper {

    public Medida toEntity(MedidaDTO.Request dto) {
        return Medida.builder()
                .nombreMedida(dto.nombreMedida())
                .descripcion(dto.descripcion())
                .build();
    }

    public MedidaDTO.Response toResponse(Medida medida) {
        return new MedidaDTO.Response(medida.getId(), medida.getNombreMedida(), medida.getDescripcion());
    }
}
