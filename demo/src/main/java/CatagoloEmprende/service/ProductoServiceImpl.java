package CatagoloEmprende.service;

import CatagoloEmprende.dto.EspecificacionDTO;
import CatagoloEmprende.dto.ProductoDTO;
import CatagoloEmprende.exception.LimitePlanExcedidoException;
import CatagoloEmprende.exception.RecursoNoEncontradoException;
import CatagoloEmprende.mapper.ProductoMapper;
import CatagoloEmprende.model.Categoria;
import CatagoloEmprende.model.Medida;
import CatagoloEmprende.model.Producto;
import CatagoloEmprende.model.ProductoMedida;
import CatagoloEmprende.model.Tienda;
import CatagoloEmprende.repository.CategoriaRepository;
import CatagoloEmprende.repository.MedidaRepository;
import CatagoloEmprende.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private static final int LIMITE_PRODUCTOS_PLAN_GRATIS = 15;

    private final ProductoRepository productoRepository;
    private final MedidaRepository medidaRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;
    private final TiendaService tiendaService;

    @Override
    @Transactional
    public ProductoDTO.Response crear(Long tiendaId, ProductoDTO.Request request) {
        Tienda tienda = tiendaService.obtenerEntidadPorId(tiendaId);

        long cantidadActual = productoRepository.countByTiendaId(tiendaId);
        if (cantidadActual >= LIMITE_PRODUCTOS_PLAN_GRATIS) {
            throw new LimitePlanExcedidoException(
                    "Alcanzaste el límite de " + LIMITE_PRODUCTOS_PLAN_GRATIS +
                    " productos del plan gratuito. Actualiza tu plan para agregar más.");
        }

        Producto producto = productoMapper.toEntity(request);
        producto.setTienda(tienda);
        agregarEspecificaciones(producto, request.especificaciones());
        producto.setCategorias(resolverCategorias(request.categoriaIds()));

        Producto guardado = productoRepository.save(producto);
        return productoMapper.toResponse(guardado);
    }

    @Override
    @Transactional
    public ProductoDTO.Response actualizar(Long productoId, ProductoDTO.Request request) {
        Producto producto = obtenerEntidadPorId(productoId);

        producto.setNombreProducto(request.nombreProducto());
        producto.setDescripcion(request.descripcion());
        producto.setPrecio(request.precio());
        producto.setImgUrl(request.imgUrl());

        producto.getEspecificaciones().clear();
        agregarEspecificaciones(producto, request.especificaciones());

        producto.setCategorias(resolverCategorias(request.categoriaIds()));

        Producto actualizado = productoRepository.save(producto);
        return productoMapper.toResponse(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long productoId) {
        Producto producto = obtenerEntidadPorId(productoId);
        productoRepository.delete(producto);
    }

    @Override
    public ProductoDTO.Response buscarPorId(Long productoId) {
        return productoMapper.toResponse(obtenerEntidadPorId(productoId));
    }

    @Override
    public List<ProductoDTO.Response> listarPorTienda(Long tiendaId) {
        return productoRepository.findByTiendaId(tiendaId)
                .stream()
                .map(productoMapper::toResponse)
                .toList();
    }

    @Override
    public Producto obtenerEntidadPorId(Long productoId) {
        return productoRepository.findById(productoId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el producto con id: " + productoId));
    }

    private void agregarEspecificaciones(Producto producto, List<EspecificacionDTO.Request> especificaciones) {
        if (especificaciones == null) return;

        for (EspecificacionDTO.Request espDto : especificaciones) {
            Medida medida = medidaRepository.findById(espDto.medidaId())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No se encontró la medida con id: " + espDto.medidaId()));

            ProductoMedida especificacion = ProductoMedida.builder()
                    .producto(producto)
                    .medida(medida)
                    .valor(espDto.valor())
                    .build();

            producto.getEspecificaciones().add(especificacion);
        }
    }

    // Busca cada Categoria por id y valida que todas existan antes de
    // asignarlas -- evita guardar un producto "a medias" con categorías
    // inválidas silenciosamente ignoradas.
    private Set<Categoria> resolverCategorias(List<Long> categoriaIds) {
        if (categoriaIds == null || categoriaIds.isEmpty()) {
            return new HashSet<>();
        }

        Set<Categoria> categorias = new HashSet<>();
        for (Long categoriaId : categoriaIds) {
            Categoria categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No se encontró la categoría con id: " + categoriaId));
            categorias.add(categoria);
        }
        return categorias;
    }
}
