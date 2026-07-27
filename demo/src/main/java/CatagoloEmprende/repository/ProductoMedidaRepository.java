package CatagoloEmprende.repository;

import CatagoloEmprende.model.ProductoMedida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoMedidaRepository extends JpaRepository<ProductoMedida, Long> {

    // Traer todas las especificaciones (peso, dimensiones, etc.) de un producto
    List<ProductoMedida> findByProductoId(Long productoId);
}
