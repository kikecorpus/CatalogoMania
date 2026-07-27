package CatagoloEmprende.service;

import CatagoloEmprende.dto.CategoriaPDTO;
import CatagoloEmprende.exception.RecursoNoEncontradoException;
import CatagoloEmprende.mapper.CategoriaPMapper;
import CatagoloEmprende.model.CategoriaP;
import CatagoloEmprende.repository.CategoriaPRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaPServiceImpl implements CategoriaPService {

    private final CategoriaPRepository categoriaPRepository;
    private final CategoriaPMapper categoriaPMapper;

    @Override
    @Transactional
    public CategoriaPDTO.Response crear(CategoriaPDTO.Request request) {
        CategoriaP categoriaP = categoriaPMapper.toEntity(request);
        return categoriaPMapper.toResponse(categoriaPRepository.save(categoriaP));
    }

    @Override
    @Transactional
    public CategoriaPDTO.Response actualizar(Long id, CategoriaPDTO.Request request) {
        CategoriaP categoriaP = obtenerEntidad(id);
        categoriaP.setNombreCategoria(request.nombreCategoria());
        categoriaP.setDescripcion(request.descripcion());
        return categoriaPMapper.toResponse(categoriaPRepository.save(categoriaP));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        categoriaPRepository.delete(obtenerEntidad(id));
    }

    @Override
    public CategoriaPDTO.Response buscarPorId(Long id) {
        return categoriaPMapper.toResponse(obtenerEntidad(id));
    }

    @Override
    public List<CategoriaPDTO.Response> listarTodas() {
        return categoriaPRepository.findAll().stream().map(categoriaPMapper::toResponse).toList();
    }

    private CategoriaP obtenerEntidad(Long id) {
        return categoriaPRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la categoría de plantilla con id: " + id));
    }
}
