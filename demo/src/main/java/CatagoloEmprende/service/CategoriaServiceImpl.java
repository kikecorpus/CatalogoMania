package CatagoloEmprende.service;

import CatagoloEmprende.dto.CategoriaDTO;
import CatagoloEmprende.exception.RecursoNoEncontradoException;
import CatagoloEmprende.mapper.CategoriaMapper;
import CatagoloEmprende.model.Categoria;
import CatagoloEmprende.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    @Transactional
    public CategoriaDTO.Response crear(CategoriaDTO.Request request) {
        Categoria categoria = categoriaMapper.toEntity(request);
        return categoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

    @Override
    @Transactional
    public CategoriaDTO.Response actualizar(Long id, CategoriaDTO.Request request) {
        Categoria categoria = obtenerEntidad(id);
        categoria.setNombreCategoria(request.nombreCategoria());
        categoria.setDescripcion(request.descripcion());
        return categoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        categoriaRepository.delete(obtenerEntidad(id));
    }

    @Override
    public CategoriaDTO.Response buscarPorId(Long id) {
        return categoriaMapper.toResponse(obtenerEntidad(id));
    }

    @Override
    public List<CategoriaDTO.Response> listarTodas() {
        return categoriaRepository.findAll().stream().map(categoriaMapper::toResponse).toList();
    }

    private Categoria obtenerEntidad(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la categoría con id: " + id));
    }
}
