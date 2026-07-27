import { Link, useLocation } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { Button } from './Button';

const ENLACES = [
  { ruta: '/productos', etiqueta: 'Productos' },
  { ruta: '/catalogos', etiqueta: 'Catálogos' },
];

export function NavBar() {
  const { correo, logout } = useAuth();
  const location = useLocation();

  return (
    <header className="border-b border-line bg-paper-raised">
      <div className="max-w-5xl mx-auto px-4 py-3 flex items-center justify-between">
        <div className="flex items-center gap-8">
          <span className="font-display font-semibold text-ink">Catalogo Mania</span>
          <nav className="flex gap-1">
            {ENLACES.map((enlace) => {
              const activo = location.pathname === enlace.ruta;
              return (
                <Link
                  key={enlace.ruta}
                  to={enlace.ruta}
                  className={`px-3 py-1.5 rounded-md text-sm font-medium transition-colors ${
                    activo ? 'bg-amber/20 text-ink' : 'text-ink-soft hover:bg-paper'
                  }`}
                >
                  {enlace.etiqueta}
                </Link>
              );
            })}
          </nav>
        </div>

        <div className="flex items-center gap-3">
          <span className="text-xs text-ink-soft hidden sm:inline">{correo}</span>
          <Button variant="ghost" onClick={logout}>
            Cerrar sesión
          </Button>
        </div>
      </div>
    </header>
  );
}
