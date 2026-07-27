package CatagoloEmprende.repository;

import CatagoloEmprende.model.Personalizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalizacionRepository extends JpaRepository<Personalizacion, Long> {
    // Normalmente no la usarás directo: al guardar un Catalogo con
    // cascade = ALL, su Personalizacion se guarda automáticamente junto con él.
}
