import { useState } from 'react';
import type { FormEvent } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { TagCard } from '../components/ui/TagCard';
import { Button } from '../components/ui/Button';
import { Input } from '../components/ui/Input';

export function LoginPage() {
  const [correo, setCorreo] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [enviando, setEnviando] = useState(false);

  const { login } = useAuth();
  const navigate = useNavigate();

  async function manejarSubmit(evento: FormEvent) {
    evento.preventDefault();
    setError(null);
    setEnviando(true);

    try {
      await login(correo, password);
      navigate('/productos');
    } catch (err) {
      setError('Correo o contraseña incorrectos');
    } finally {
      setEnviando(false);
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center px-4">
      <div className="w-full max-w-sm">
        <div className="text-center mb-6">
          <span className="font-mono text-xs tracking-widest text-teal uppercase">Catalogo Mania</span>
          <h1 className="text-2xl mt-1">Iniciar sesión</h1>
        </div>

        <TagCard>
          <form onSubmit={manejarSubmit} className="grid gap-4">
            <Input
              id="correo"
              label="Correo"
              type="email"
              value={correo}
              onChange={(e) => setCorreo(e.target.value)}
              required
            />
            <Input
              id="password"
              label="Contraseña"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />

            {error && <p className="text-sm text-danger">{error}</p>}

            <Button type="submit" disabled={enviando}>
              {enviando ? 'Ingresando...' : 'Ingresar'}
            </Button>
          </form>
        </TagCard>

        <p className="text-center text-sm text-ink-soft mt-4">
          ¿No tienes cuenta?{' '}
          <Link to="/registro" className="text-teal font-medium">
            Regístrate aquí
          </Link>
        </p>
      </div>
    </div>
  );
}
