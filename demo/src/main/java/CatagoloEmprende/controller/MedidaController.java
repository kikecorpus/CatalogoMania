package CatagoloEmprende.controller;

import CatagoloEmprende.dto.MedidaDTO;
import CatagoloEmprende.service.MedidaService;
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
@RequestMapping("/api/medidas")
@RequiredArgsConstructor
@Tag(name = "Medidas", description = "Catálogo de tipos de medida/especificación (Peso, Dimensiones, etc.)")
public class MedidaController {

    private final MedidaService medidaService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Crear un tipo de medida")
    public ResponseEntity<MedidaDTO.Response> crear(@Valid @RequestBody MedidaDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medidaService.crear(request));
    }

    @GetMapping
    @Operation(summary = "Listar todos los tipos de medida")
    public ResponseEntity<List<MedidaDTO.Response>> listar() {
        return ResponseEntity.ok(medidaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un tipo de medida por id")
    public ResponseEntity<MedidaDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medidaService.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un tipo de medida")
    public ResponseEntity<MedidaDTO.Response> actualizar(
            @PathVariable Long id, @Valid @RequestBody MedidaDTO.Request request) {
        return ResponseEntity.ok(medidaService.actualizar(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un tipo de medida")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        medidaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
