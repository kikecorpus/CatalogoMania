package CatagoloEmprende.repository;

import CatagoloEmprende.model.Catalogo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {

    // Listar los catálogos de una tienda
    List<Catalogo> findByTiendaId(Long tiendaId);
}
