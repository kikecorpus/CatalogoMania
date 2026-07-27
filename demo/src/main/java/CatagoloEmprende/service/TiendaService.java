package CatagoloEmprende.service;

import CatagoloEmprende.dto.TiendaDTO;
import CatagoloEmprende.model.Tienda;

public interface TiendaService {

    TiendaDTO.Response registrar(TiendaDTO.RegistroRequest request);

    TiendaDTO.Response buscarPorId(Long id);

    // Uso interno: para que otros Services obtengan la entidad real
    // (ej. ProductoService necesita el objeto Tienda para asignarlo a un Producto)
    Tienda obtenerEntidadPorId(Long id);
}
