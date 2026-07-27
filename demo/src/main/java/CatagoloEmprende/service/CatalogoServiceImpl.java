package CatagoloEmprende.service;

import CatagoloEmprende.dto.CatalogoDTO;
import CatagoloEmprende.exception.RecursoNoEncontradoException;
import CatagoloEmprende.mapper.CatalogoMapper;
import CatagoloEmprende.model.Catalogo;
import CatagoloEmprende.model.Plantilla;
import CatagoloEmprende.model.Producto;
import CatagoloEmprende.model.Tienda;
import CatagoloEmprende.repository.CatalogoRepository;
import CatagoloEmprende.repository.PlantillaRepository;
import CatagoloEmprende.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogoServiceImpl implements CatalogoService {

    private final CatalogoRepository catalogoRepository;
    private final PlantillaRepository plantillaRepository;
    private final ProductoRepository productoRepository;
    private final CatalogoMapper catalogoMapper;
    private final TiendaService tiendaService;
    private final ProductoService productoService;

    @Override
    @Transactional
    public CatalogoDTO.Response crear(Long tiendaId, CatalogoDTO.Request request) {
        Tienda tienda = tiendaService.obtenerEntidadPorId(tiendaId);

        Plantilla plantilla = plantillaRepository.findById(request.plantillaId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró la plantilla con id: " + request.plantillaId()));

        Catalogo catalogo = catalogoMapper.toEntity(request);
        catalogo.setTienda(tienda);
        catalogo.setPlantilla(plantilla);

        Catalogo guardado = catalogoRepository.save(catalogo);
        return catalogoMapper.toResponse(guardado);
    }

    @Override
    @Transactional
    public CatalogoDTO.Response agregarProducto(Long catalogoId, Long productoId) {
        Catalogo catalogo = obtenerEntidad(catalogoId);
        Producto producto = productoService.obtenerEntidadPorId(productoId);

        // IMPORTANTE: Producto.catalogos es el lado DUEÑO de la relación
        // (tiene el @JoinTable). Hay que modificarlo a él para que JPA
        // realmente escriba en la tabla intermedia producto_catalogo.
        producto.getCatalogos().add(catalogo);
        productoRepository.save(producto);

        // Actualizamos también el lado inverso (catalogo.productos), solo
        // para que el objeto en memoria quede consistente antes de mapear
        // la respuesta -- esto NO es lo que persiste la relación.
        catalogo.getProductos().add(producto);
        catalogo.setCantidadProducto(catalogo.getProductos().size());
        Catalogo actualizado = catalogoRepository.save(catalogo);

        return catalogoMapper.toResponse(actualizado);
    }

    @Override
    @Transactional
    public CatalogoDTO.Response quitarProducto(Long catalogoId, Long productoId) {
        Catalogo catalogo = obtenerEntidad(catalogoId);
        Producto producto = productoService.obtenerEntidadPorId(productoId);

        // Mismo principio: se quita del lado DUEÑO para que se borre
        // realmente la fila de la tabla intermedia.
        producto.getCatalogos().remove(catalogo);
        productoRepository.save(producto);

        catalogo.getProductos().remove(producto);
        catalogo.setCantidadProducto(catalogo.getProductos().size());
        Catalogo actualizado = catalogoRepository.save(catalogo);

        return catalogoMapper.toResponse(actualizado);
    }

    @Override
    public CatalogoDTO.Response buscarPorId(Long catalogoId) {
        return catalogoMapper.toResponse(obtenerEntidad(catalogoId));
    }

    @Override
    public List<CatalogoDTO.Response> listarPorTienda(Long tiendaId) {
        return catalogoRepository.findByTiendaId(tiendaId)
                .stream()
                .map(catalogoMapper::toResponse)
                .toList();
    }

    private Catalogo obtenerEntidad(Long catalogoId) {
        return catalogoRepository.findById(catalogoId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el catálogo con id: " + catalogoId));
    }
}
