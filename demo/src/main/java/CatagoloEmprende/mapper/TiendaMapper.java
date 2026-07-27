package CatagoloEmprende.mapper;

import CatagoloEmprende.dto.TiendaDTO;
import CatagoloEmprende.model.Tienda;
import org.springframework.stereotype.Component;

@Component
public class TiendaMapper {

    // No incluye password aquí a propósito: el Service es quien decide
    // encriptarla antes de guardar, para mantener esa responsabilidad en un solo lugar.
    public Tienda toEntity(TiendaDTO.RegistroRequest dto) {
        return Tienda.builder()
                .nombreTienda(dto.nombreTienda())
                .telefono(dto.telefono())
                .correo(dto.correo())
                .direccion(dto.direccion())
                .password(dto.password()) // se sobreescribe encriptada en el Service
                .build();
    }

    public TiendaDTO.Response toResponse(Tienda tienda) {
        return new TiendaDTO.Response(
                tienda.getId(),
                tienda.getNombreTienda(),
                tienda.getTelefono(),
                tienda.getCorreo(),
                tienda.getDireccion(),
                tienda.getAdmin()
        );
    }
}
