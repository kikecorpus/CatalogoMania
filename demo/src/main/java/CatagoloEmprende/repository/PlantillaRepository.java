package CatagoloEmprende.repository;

import CatagoloEmprende.model.Plantilla;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantillaRepository extends JpaRepository<Plantilla, Long> {

    // Para mostrar en el editor solo las plantillas activas
    List<Plantilla> findByEstado(String estado);

    // Filtrar plantillas por categoría (ej. solo las de "Comida")
    List<Plantilla> findByCategoriaPId(Long categoriaPId);
}
