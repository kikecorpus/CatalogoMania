package CatagoloEmprende.security;

import CatagoloEmprende.model.Tienda;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * "Envuelve" la entidad Tienda para que Spring Security pueda trabajar con
 * ella como si fuera un usuario del sistema. El campo extra "tiendaId" es
 * la pieza clave: nos permite recuperarlo fácilmente en los Controllers
 * sin volver a consultar la base de datos.
 */
@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Tienda tienda;

    public Long getTiendaId() {
        return tienda.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String rol = Boolean.TRUE.equals(tienda.getAdmin()) ? "ROLE_ADMIN" : "ROLE_TIENDA";
        return List.of(new SimpleGrantedAuthority(rol));
    }

    @Override
    public String getPassword() {
        return tienda.getPassword();
    }

    @Override
    public String getUsername() {
        return tienda.getCorreo();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
