package CatagoloEmprende.controller;

import CatagoloEmprende.dto.AuthDTO;
import CatagoloEmprende.dto.CatalogoDTO;
import CatagoloEmprende.pdf.CatalogoPdfService;
import CatagoloEmprende.security.CustomUserDetails;
import CatagoloEmprende.security.JwtService;
import CatagoloEmprende.service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Login y generación de tokens JWT")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener un token JWT")
    public ResponseEntity<AuthDTO.LoginResponse> login(@Valid @RequestBody AuthDTO.LoginRequest request) {
        // AuthenticationManager valida correo+password contra CustomUserDetailsService
        // y el PasswordEncoder automáticamente. Si falla, lanza una excepción
        // (que Spring Security convierte en un 401 sin que tengamos que hacer nada).
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.correo(), request.password())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtService.generarToken(userDetails.getTiendaId(), userDetails.getUsername());

        return ResponseEntity.ok(new AuthDTO.LoginResponse(token));
    }

    @RestController
    @RequestMapping("/api/catalogos")
    @RequiredArgsConstructor
    @Tag(name = "Catálogos", description = "Creación y gestión de catálogos de la tienda autenticada")
    public static class CatalogoController {

        private final CatalogoService catalogoService;
        private final CatalogoPdfService catalogoPdfService;

        @PostMapping
        @Operation(summary = "Crear un catálogo para la tienda autenticada")
        public ResponseEntity<CatalogoDTO.Response> crear(
                @AuthenticationPrincipal CustomUserDetails usuario,
                @Valid @RequestBody CatalogoDTO.Request request) {
            CatalogoDTO.Response respuesta = catalogoService.crear(usuario.getTiendaId(), request);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }

        @GetMapping
        @Operation(summary = "Listar los catálogos de la tienda autenticada")
        public ResponseEntity<List<CatalogoDTO.Response>> listarMisCatalogos(
                @AuthenticationPrincipal CustomUserDetails usuario) {
            return ResponseEntity.ok(catalogoService.listarPorTienda(usuario.getTiendaId()));
        }

        @GetMapping("/{id}")
        @Operation(summary = "Obtener un catálogo por id")
        public ResponseEntity<CatalogoDTO.Response> buscarPorId(@PathVariable Long id) {
            return ResponseEntity.ok(catalogoService.buscarPorId(id));
        }

        @PostMapping("/{catalogoId}/productos/{productoId}")
        @Operation(summary = "Agregar un producto existente al catálogo")
        public ResponseEntity<CatalogoDTO.Response> agregarProducto(
                @PathVariable Long catalogoId, @PathVariable Long productoId) {
            return ResponseEntity.ok(catalogoService.agregarProducto(catalogoId, productoId));
        }

        @DeleteMapping("/{catalogoId}/productos/{productoId}")
        @Operation(summary = "Quitar un producto del catálogo")
        public ResponseEntity<CatalogoDTO.Response> quitarProducto(
                @PathVariable Long catalogoId, @PathVariable Long productoId) {
            return ResponseEntity.ok(catalogoService.quitarProducto(catalogoId, productoId));
        }

        @GetMapping("/{id}/pdf")
        @Operation(summary = "Descargar el catálogo en formato PDF")
        public ResponseEntity<byte[]> descargarPdf(@PathVariable Long id) {
            byte[] pdf = catalogoPdfService.generarPdf(id);

            ContentDisposition contentDisposition = ContentDisposition.attachment()
                    .filename("catalogo-" + id + ".pdf")
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(contentDisposition);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        }
    }
}
