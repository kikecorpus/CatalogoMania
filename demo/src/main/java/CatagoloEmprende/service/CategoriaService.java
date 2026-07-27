package CatagoloEmprende.service;

import CatagoloEmprende.dto.CategoriaDTO;

import java.util.List;

public interface CategoriaService {

    CategoriaDTO.Response crear(CategoriaDTO.Request request);

    CategoriaDTO.Response actualizar(Long id, CategoriaDTO.Request request);

    void eliminar(Long id);

    CategoriaDTO.Response buscarPorId(Long id);

    List<CategoriaDTO.Response> listarTodas();
}
