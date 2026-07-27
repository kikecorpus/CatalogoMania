package CatagoloEmprende.service;

import CatagoloEmprende.dto.CatalogoDTO;

import java.util.List;

public interface CatalogoService {

    CatalogoDTO.Response crear(Long tiendaId, CatalogoDTO.Request request);

    CatalogoDTO.Response agregarProducto(Long catalogoId, Long productoId);

    CatalogoDTO.Response quitarProducto(Long catalogoId, Long productoId);

    CatalogoDTO.Response buscarPorId(Long catalogoId);

    List<CatalogoDTO.Response> listarPorTienda(Long tiendaId);
}