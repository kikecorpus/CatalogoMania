package CatagoloEmprende.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Genera un token para una tienda. Guarda el tiendaId como "claim"
     * dentro del propio token — esto es lo que nos permite, más adelante,
     * saber quién hace la petición SIN confiar en nada que mande el cliente.
     */
    public String generarToken(Long tiendaId, String correo) {
        return Jwts.builder()
                .subject(correo)
                .claim("tiendaId", tiendaId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extraerCorreo(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    public Long extraerTiendaId(String token) {
        Claims claims = extraerTodosLosClaims(token);
        return claims.get("tiendaId", Long.class);
    }

    public boolean esTokenValido(String token, String correoEsperado) {
        String correoDelToken = extraerCorreo(token);
        return correoDelToken.equals(correoEsperado) && !haExpirado(token);
    }

    private boolean haExpirado(String token) {
        return extraerClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extraerClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extraerTodosLosClaims(token);
        return resolver.apply(claims);
    }

    private Claims extraerTodosLosClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
