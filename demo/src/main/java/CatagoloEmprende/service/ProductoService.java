package CatagoloEmprende.service;

import CatagoloEmprende.dto.ProductoDTO;
import CatagoloEmprende.model.Producto;

import java.util.List;

public interface ProductoService {

    ProductoDTO.Response crear(Long tiendaId, ProductoDTO.Request request);

    ProductoDTO.Response actualizar(Long productoId, ProductoDTO.Request request);

    void eliminar(Long productoId);

    ProductoDTO.Response buscarPorId(Long productoId);

    List<ProductoDTO.Response> listarPorTienda(Long tiendaId);

    // Uso interno: para que CatalogoService pueda asociar el Producto real
    Producto obtenerEntidadPorId(Long productoId);
}
