import type { ReactNode } from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

// Envuelve cualquier página que requiera sesión activa. Es el equivalente
// en el frontend a lo que hace SecurityConfig + JwtAuthFilter en el
// backend: si no hay token válido, no dejamos ver la página.
export function RutaProtegida({ children }: { children: ReactNode }) {
  const { isAuthenticated, cargando } = useAuth();

  // Mientras se revisa si había un token guardado (al recargar la página),
  // no decidimos nada todavía -- evita un "flash" de redirección incorrecta.
  if (cargando) {
    return <p>Cargando...</p>;
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>;
}
