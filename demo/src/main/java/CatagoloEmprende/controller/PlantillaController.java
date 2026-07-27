package CatagoloEmprende.controller;

import CatagoloEmprende.dto.PlantillaDTO;
import CatagoloEmprende.service.PlantillaService;
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
@RequestMapping("/api/plantillas")
@RequiredArgsConstructor
@Tag(name = "Plantillas", description = "Diseños disponibles para crear catálogos")
public class PlantillaController {

    private final PlantillaService plantillaService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Crear una plantilla")
    public ResponseEntity<PlantillaDTO.Response> crear(@Valid @RequestBody PlantillaDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(plantillaService.crear(request));
    }

    @GetMapping
    @Operation(summary = "Listar las plantillas activas (para mostrar en el editor)")
    public ResponseEntity<List<PlantillaDTO.Response>> listarActivas() {
        return ResponseEntity.ok(plantillaService.listarActivas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una plantilla por id")
    public ResponseEntity<PlantillaDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(plantillaService.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una plantilla")
    public ResponseEntity<PlantillaDTO.Response> actualizar(
            @PathVariable Long id, @Valid @RequestBody PlantillaDTO.Request request) {
        return ResponseEntity.ok(plantillaService.actualizar(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una plantilla")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        plantillaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
