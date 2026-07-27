package CatagoloEmprende.repository;

import CatagoloEmprende.model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TiendaRepository extends JpaRepository<Tienda, Long> {

    // Necesario para el login: buscar la tienda por su correo
    Optional<Tienda> findByCorreo(String correo);

    // Útil para validar en el registro que el correo no esté ya usado
    boolean existsByCorreo(String correo);
}
