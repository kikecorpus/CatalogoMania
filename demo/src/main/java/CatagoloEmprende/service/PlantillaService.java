package CatagoloEmprende.service;

import CatagoloEmprende.dto.PlantillaDTO;

import java.util.List;

public interface PlantillaService {

    PlantillaDTO.Response crear(PlantillaDTO.Request request);

    PlantillaDTO.Response actualizar(Long id, PlantillaDTO.Request request);

    void eliminar(Long id);

    PlantillaDTO.Response buscarPorId(Long id);

    List<PlantillaDTO.Response> listarActivas();
}
