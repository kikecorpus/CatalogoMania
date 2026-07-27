import { useState } from 'react';
import type { FormEvent } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { registrarTienda } from '../api/authApi';
import axios from 'axios';
import { TagCard } from '../components/ui/TagCard';
import { Button } from '../components/ui/Button';
import { Input } from '../components/ui/Input';

export function RegistroPage() {
  const [nombreTienda, setNombreTienda] = useState('');
  const [correo, setCorreo] = useState('');
  const [telefono, setTelefono] = useState('');
  const [direccion, setDireccion] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [enviando, setEnviando] = useState(false);

  const navigate = useNavigate();

  async function manejarSubmit(evento: FormEvent) {
    evento.preventDefault();
    setError(null);
    setEnviando(true);

    try {
      await registrarTienda({ nombreTienda, correo, telefono, direccion, password });
      navigate('/login');
    } catch (err) {
      if (axios.isAxiosError(err) && err.response?.status === 409) {
        setError(err.response.data.mensaje);
      } else {
        setError('No se pudo completar el registro. Intenta de nuevo.');
      }
    } finally {
      setEnviando(false);
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center px-4 py-10">
      <div className="w-full max-w-sm">
        <div className="text-center mb-6">
          <span className="font-mono text-xs tracking-widest text-teal uppercase">Catalogo Mania</span>
          <h1 className="text-2xl mt-1">Crear cuenta</h1>
        </div>

        <TagCard>
          <form onSubmit={manejarSubmit} className="grid gap-4">
            <Input
              id="nombreTienda"
              label="Nombre de la tienda"
              value={nombreTienda}
              onChange={(e) => setNombreTienda(e.target.value)}
              required
            />
            <Input
              id="correo"
              label="Correo"
              type="email"
              value={correo}
              onChange={(e) => setCorreo(e.target.value)}
              required
            />
            <Input id="telefono" label="Teléfono" value={telefono} onChange={(e) => setTelefono(e.target.value)} />
            <Input id="direccion" label="Dirección" value={direccion} onChange={(e) => setDireccion(e.target.value)} />
            <Input
              id="password"
              label="Contraseña"
              type="password"
              minLength={8}
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />

            {error && <p className="text-sm text-danger">{error}</p>}

            <Button type="submit" disabled={enviando}>
              {enviando ? 'Creando cuenta...' : 'Registrarme'}
            </Button>
          </form>
        </TagCard>

        <p className="text-center text-sm text-ink-soft mt-4">
          ¿Ya tienes cuenta?{' '}
          <Link to="/login" className="text-teal font-medium">
            Inicia sesión
          </Link>
        </p>
      </div>
    </div>
  );
}
