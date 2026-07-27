package CatagoloEmprende.repository;

import CatagoloEmprende.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Listar todos los productos de una tienda (para el editor)
    List<Producto> findByTiendaId(Long tiendaId);

    // Contar cuántos productos tiene una tienda (para validar el límite del plan gratuito)
    long countByTiendaId(Long tiendaId);

    // Listar productos que pertenecen a un catálogo específico
    List<Producto> findByCatalogosId(Long catalogoId);
}
