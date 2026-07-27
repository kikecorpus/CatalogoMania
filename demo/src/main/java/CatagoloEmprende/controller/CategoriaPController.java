package CatagoloEmprende.controller;

import CatagoloEmprende.dto.CategoriaPDTO;
import CatagoloEmprende.service.CategoriaPService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias-plantilla")
@RequiredArgsConstructor
@Tag(name = "Categorías de plantilla", description = "Catálogo de categorías usadas para clasificar plantillas")
public class CategoriaPController {

    private final CategoriaPService categoriaPService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Crear una categoría de plantilla")
    public ResponseEntity<CategoriaPDTO.Response> crear(@Valid @RequestBody CategoriaPDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaPService.crear(request));
    }

    @GetMapping
    @Operation(summary = "Listar todas las categorías de plantilla")
    public ResponseEntity<List<CategoriaPDTO.Response>> listar() {
        return ResponseEntity.ok(categoriaPService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una categoría de plantilla por id")
    public ResponseEntity<CategoriaPDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaPService.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una categoría de plantilla")
    public ResponseEntity<CategoriaPDTO.Response> actualizar(
            @PathVariable Long id, @Valid @RequestBody CategoriaPDTO.Request request) {
        return ResponseEntity.ok(categoriaPService.actualizar(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una categoría de plantilla")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaPService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
