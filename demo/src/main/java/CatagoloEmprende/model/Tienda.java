package CatagoloEmprende.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tienda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Representa una tienda registrada en la plataforma")
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tienda")
    @Schema(description = "Identificador único de la tienda", example = "1")
    private Long id;

    @NotBlank
    @Column(name = "nombre_tienda", nullable = false, length = 150)
    @Schema(description = "Nombre comercial de la tienda", example = "La Bodeguita")
    private String nombreTienda;

    @Column(name = "telefono", length = 30)
    @Schema(description = "Teléfono de contacto de la tienda", example = "+57 300 123 4567")
    private String telefono;

    @NotBlank
    @Email
    @Column(name = "correo", nullable = false, unique = true, length = 150)
    @Schema(description = "Correo electrónico usado para iniciar sesión", example = "tienda@correo.com")
    private String correo;

    @Column(name = "direccion", length = 255)
    @Schema(description = "Dirección física de la tienda (opcional)")
    private String direccion;

    @NotBlank
    @Column(name = "password", nullable = false, length = 255)
    @Schema(description = "Contraseña encriptada de la cuenta", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    @Builder.Default
    @Column(name = "admin", nullable = false)
    @Schema(description = "Indica si la cuenta tiene permisos de administrador de la plataforma", example = "false")
    private Boolean admin = false;

    @Column(name = "created_at", updatable = false)
    @Schema(description = "Fecha de creación del registro", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Schema(description = "Fecha de última actualización", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    // Relaciones

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @Schema(description = "Productos que pertenecen a esta tienda")
    private Set<Producto> productos = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @Schema(description = "Catálogos creados por esta tienda")
    private Set<Catalogo> catalogos = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
