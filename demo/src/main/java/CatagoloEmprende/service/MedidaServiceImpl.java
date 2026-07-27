package CatagoloEmprende.service;

import CatagoloEmprende.dto.MedidaDTO;
import CatagoloEmprende.exception.RecursoNoEncontradoException;
import CatagoloEmprende.mapper.MedidaMapper;
import CatagoloEmprende.model.Medida;
import CatagoloEmprende.repository.MedidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedidaServiceImpl implements MedidaService {

    private final MedidaRepository medidaRepository;
    private final MedidaMapper medidaMapper;

    @Override
    @Transactional
    public MedidaDTO.Response crear(MedidaDTO.Request request) {
        Medida medida = medidaMapper.toEntity(request);
        return medidaMapper.toResponse(medidaRepository.save(medida));
    }

    @Override
    @Transactional
    public MedidaDTO.Response actualizar(Long id, MedidaDTO.Request request) {
        Medida medida = obtenerEntidad(id);
        medida.setNombreMedida(request.nombreMedida());
        medida.setDescripcion(request.descripcion());
        return medidaMapper.toResponse(medidaRepository.save(medida));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        medidaRepository.delete(obtenerEntidad(id));
    }

    @Override
    public MedidaDTO.Response buscarPorId(Long id) {
        return medidaMapper.toResponse(obtenerEntidad(id));
    }

    @Override
    public List<MedidaDTO.Response> listarTodas() {
        return medidaRepository.findAll().stream().map(medidaMapper::toResponse).toList();
    }

    private Medida obtenerEntidad(Long id) {
        return medidaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la medida con id: " + id));
    }
}
