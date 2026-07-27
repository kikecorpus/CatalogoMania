import { createContext, useContext, useState, useEffect } from 'react';
import type { ReactNode } from 'react';
import { login as loginApi } from '../api/authApi';

interface AuthState {
  token: string | null;
  tiendaId: number | null;
  correo: string | null;
  isAuthenticated: boolean;
  cargando: boolean;
  login: (correo: string, password: string) => Promise<void>;
  logout: () => void;
}

// Decodifica el payload de un JWT SIN verificar su firma.
// Esto es solo para leer datos (ej. tiendaId) del lado del cliente y
// mostrar la UI acorde -- la validación REAL de seguridad siempre ocurre
// en el backend (JwtAuthFilter), nunca confíes en esto para autorizar nada.
function decodificarToken(token: string): { tiendaId: number; sub: string } | null {
  try {
    const payloadBase64 = token.split('.')[1];
    const payloadJson = atob(payloadBase64.replace(/-/g, '+').replace(/_/g, '/'));
    return JSON.parse(payloadJson);
  } catch {
    return null;
  }
}

const AuthContext = createContext<AuthState | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [token, setToken] = useState<string | null>(null);
  const [tiendaId, setTiendaId] = useState<number | null>(null);
  const [correo, setCorreo] = useState<string | null>(null);
  const [cargando, setCargando] = useState(true);

  // Al cargar la app por primera vez, revisa si ya había un token guardado
  // (ej. el usuario recargó la página) y restaura la sesión sin pedir login de nuevo.
  useEffect(() => {
    const tokenGuardado = localStorage.getItem('token');
    if (tokenGuardado) {
      const datos = decodificarToken(tokenGuardado);
      if (datos) {
        setToken(tokenGuardado);
        setTiendaId(datos.tiendaId);
        setCorreo(datos.sub);
      }
    }
    setCargando(false);
  }, []);

  async function login(correoInput: string, password: string) {
    const respuesta = await loginApi({ correo: correoInput, password });
    const datos = decodificarToken(respuesta.token);

    localStorage.setItem('token', respuesta.token);
    setToken(respuesta.token);
    setTiendaId(datos?.tiendaId ?? null);
    setCorreo(datos?.sub ?? null);
  }

  function logout() {
    localStorage.removeItem('token');
    setToken(null);
    setTiendaId(null);
    setCorreo(null);
  }

  const value: AuthState = {
    token,
    tiendaId,
    correo,
    isAuthenticated: token !== null,
    cargando,
    login,
    logout,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

// Hook para consumir el contexto fácilmente desde cualquier componente:
// const { tiendaId, logout } = useAuth();
export function useAuth(): AuthState {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth debe usarse dentro de un <AuthProvider>');
  }
  return context;
}
