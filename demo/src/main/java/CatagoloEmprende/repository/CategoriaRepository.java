package CatagoloEmprende.repository;

import CatagoloEmprende.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Con findAll() alcanza por ahora: la lista de categorías es un catálogo
    // fijo que se muestra completo en el editor (ej. un <select>)
}
