package CatagoloEmprende.service;

import CatagoloEmprende.dto.MedidaDTO;

import java.util.List;

public interface MedidaService {

    MedidaDTO.Response crear(MedidaDTO.Request request);

    MedidaDTO.Response actualizar(Long id, MedidaDTO.Request request);

    void eliminar(Long id);

    MedidaDTO.Response buscarPorId(Long id);

    List<MedidaDTO.Response> listarTodas();
}
