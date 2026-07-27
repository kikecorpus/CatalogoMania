package CatagoloEmprende.controller;

import CatagoloEmprende.dto.TiendaDTO;
import CatagoloEmprende.service.TiendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tiendas")
@RequiredArgsConstructor
@Tag(name = "Tiendas", description = "Registro y consulta de tiendas")
public class TiendaController {

    private final TiendaService tiendaService;

    @PostMapping("/registro")
    @Operation(summary = "Registrar una nueva tienda")
    public ResponseEntity<TiendaDTO.Response> registrar(@Valid @RequestBody TiendaDTO.RegistroRequest request) {
        TiendaDTO.Response respuesta = tiendaService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una tienda por id")
    public ResponseEntity<TiendaDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tiendaService.buscarPorId(id));
    }
}
