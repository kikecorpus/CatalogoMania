package CatagoloEmprende.service;

import CatagoloEmprende.dto.CategoriaPDTO;

import java.util.List;

public interface CategoriaPService {

    CategoriaPDTO.Response crear(CategoriaPDTO.Request request);

    CategoriaPDTO.Response actualizar(Long id, CategoriaPDTO.Request request);

    void eliminar(Long id);

    CategoriaPDTO.Response buscarPorId(Long id);

    List<CategoriaPDTO.Response> listarTodas();
}
