import { useRef, useState } from 'react';
import type { ChangeEvent } from 'react';
import { subirImagen } from '../api/cloudinaryApi';
import { Button } from './ui/Button';

interface Props {
  valor: string;
  onCambiar: (url: string) => void;
  etiqueta?: string;
}

// Componente "controlado": el valor real (la URL) vive en el componente
// padre, este solo se encarga de la mecánica de subir el archivo y avisar
// cuando hay una URL nueva.
export function SubidaImagen({ valor, onCambiar, etiqueta = 'Imagen' }: Props) {
  const [subiendo, setSubiendo] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const inputRef = useRef<HTMLInputElement>(null);

  async function manejarSeleccion(evento: ChangeEvent<HTMLInputElement>) {
    const archivo = evento.target.files?.[0];
    if (!archivo) return;

    setSubiendo(true);
    setError(null);

    try {
      const url = await subirImagen(archivo);
      onCambiar(url);
    } catch (err) {
      setError('No se pudo subir la imagen. Intenta de nuevo.');
    } finally {
      setSubiendo(false);
      // Permite volver a seleccionar el mismo archivo si el usuario
      // quiere subirlo de nuevo (el input nativo no dispara onChange
      // dos veces seguidas con el mismo archivo sin este reset).
      if (inputRef.current) inputRef.current.value = '';
    }
  }

  return (
    <div className="grid gap-2">
      <label className="text-xs font-medium text-ink-soft uppercase tracking-wide">{etiqueta}</label>

      {/* El <input type="file"> real queda oculto -- lo disparamos
          programáticamente desde nuestro propio botón estilizado. */}
      <input
        ref={inputRef}
        type="file"
        accept="image/*"
        onChange={manejarSeleccion}
        disabled={subiendo}
        className="hidden"
        id="input-archivo-oculto"
      />

      <div className="flex items-center gap-4">
        {valor ? (
          <div className="relative group">
            <img
              src={valor}
              alt="preview"
              className="w-20 h-20 object-cover rounded-md border-2 border-dashed border-line"
            />
            <button
              type="button"
              onClick={() => inputRef.current?.click()}
              disabled={subiendo}
              className="absolute inset-0 bg-ink/60 rounded-md opacity-0 group-hover:opacity-100 transition-opacity flex items-center justify-center text-white text-xs font-medium"
            >
              Cambiar
            </button>
          </div>
        ) : (
          <button
            type="button"
            onClick={() => inputRef.current?.click()}
            disabled={subiendo}
            className="w-20 h-20 rounded-md border-2 border-dashed border-line flex items-center justify-center text-ink-soft hover:border-amber hover:text-amber-dark transition-colors disabled:opacity-50"
          >
            <span className="text-2xl leading-none">+</span>
          </button>
        )}

        <Button
          type="button"
          variant="ghost"
          onClick={() => inputRef.current?.click()}
          disabled={subiendo}
        >
          {subiendo ? 'Subiendo...' : valor ? 'Reemplazar imagen' : 'Seleccionar imagen'}
        </Button>
      </div>

      {error && <p className="text-xs text-danger">{error}</p>}
    </div>
  );
}
