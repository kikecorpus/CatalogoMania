package CatagoloEmprende.controller;

import CatagoloEmprende.dto.ProductoDTO;
import CatagoloEmprende.security.CustomUserDetails;
import CatagoloEmprende.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "CRUD de productos de la tienda autenticada")
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    @Operation(summary = "Crear un producto para la tienda autenticada")
    public ResponseEntity<ProductoDTO.Response> crear(
            @AuthenticationPrincipal CustomUserDetails usuario,
            @Valid @RequestBody ProductoDTO.Request request) {
        // El tiendaId NUNCA sale del cliente: sale del token ya validado.
        ProductoDTO.Response respuesta = productoService.crear(usuario.getTiendaId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping
    @Operation(summary = "Listar los productos de la tienda autenticada")
    public ResponseEntity<List<ProductoDTO.Response>> listarMisProductos(
            @AuthenticationPrincipal CustomUserDetails usuario) {
        return ResponseEntity.ok(productoService.listarPorTienda(usuario.getTiendaId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por id")
    public ResponseEntity<ProductoDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente")
    public ResponseEntity<ProductoDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDTO.Request request) {
        return ResponseEntity.ok(productoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
