package CatagoloEmprende.service;

import CatagoloEmprende.exception.RecursoNoEncontradoException;
import CatagoloEmprende.mapper.PlantillaMapper;
import CatagoloEmprende.model.CategoriaP;
import CatagoloEmprende.model.Plantilla;
import CatagoloEmprende.repository.CategoriaPRepository;
import CatagoloEmprende.repository.PlantillaRepository;
import CatagoloEmprende.dto.PlantillaDTO;
import CatagoloEmprende.service.PlantillaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantillaServiceImpl implements PlantillaService {

    private final PlantillaRepository plantillaRepository;
    private final CategoriaPRepository categoriaPRepository;
    private final PlantillaMapper plantillaMapper;

    @Override
    @Transactional
    public PlantillaDTO.Response crear(PlantillaDTO.Request request) {
        CategoriaP categoriaP = buscarCategoriaP(request.categoriaPId());

        Plantilla plantilla = plantillaMapper.toEntity(request);
        plantilla.setCategoriaP(categoriaP);

        return plantillaMapper.toResponse(plantillaRepository.save(plantilla));
    }

    @Override
    @Transactional
    public PlantillaDTO.Response actualizar(Long id, PlantillaDTO.Request request) {
        Plantilla plantilla = obtenerEntidad(id);
        CategoriaP categoriaP = buscarCategoriaP(request.categoriaPId());

        plantilla.setNombrePlantilla(request.nombrePlantilla());
        plantilla.setEstado(request.estado());
        plantilla.setGratis(request.gratis());
        plantilla.setCategoriaP(categoriaP);

        return plantillaMapper.toResponse(plantillaRepository.save(plantilla));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        plantillaRepository.delete(obtenerEntidad(id));
    }

    @Override
    public PlantillaDTO.Response buscarPorId(Long id) {
        return plantillaMapper.toResponse(obtenerEntidad(id));
    }

    @Override
    public List<PlantillaDTO.Response> listarActivas() {
        // Solo mostramos plantillas ACTIVO en el editor -- una plantilla
        // "descontinuada" no debería ofrecerse a nuevos catálogos.
        return plantillaRepository.findByEstado("ACTIVO").stream()
                .map(plantillaMapper::toResponse)
                .toList();
    }

    private Plantilla obtenerEntidad(Long id) {
        return plantillaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la plantilla con id: " + id));
    }

    private CategoriaP buscarCategoriaP(Long id) {
        return categoriaPRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la categoría de plantilla con id: " + id));
    }
}
