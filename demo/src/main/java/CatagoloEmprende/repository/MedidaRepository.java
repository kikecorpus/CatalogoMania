package CatagoloEmprende.repository;

import CatagoloEmprende.model.Medida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedidaRepository extends JpaRepository<Medida, Long> {
    // Igual que Categoria: catálogo fijo, findAll() es suficiente por ahora
}
