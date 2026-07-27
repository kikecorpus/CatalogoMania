import { useEffect, useState } from 'react';
import type { FormEvent } from 'react';
import { listarMisProductos, crearProducto, eliminarProducto } from '../api/productoApi';
import type { ProductoResponse } from '../types/producto.types';
import { SubidaImagen } from '../components/SubidaImagen';
import { NavBar } from '../components/ui/NavBar';
import { TagCard } from '../components/ui/TagCard';
import { Button } from '../components/ui/Button';
import { Input } from '../components/ui/Input';

export function ProductosPage() {
  const [productos, setProductos] = useState<ProductoResponse[]>([]);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const [nombreProducto, setNombreProducto] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [precio, setPrecio] = useState('');
  const [imgUrl, setImgUrl] = useState('');
  const [guardando, setGuardando] = useState(false);

  useEffect(() => {
    cargarProductos();
  }, []);

  async function cargarProductos() {
    setCargando(true);
    setError(null);
    try {
      const datos = await listarMisProductos();
      setProductos(datos);
    } catch (err) {
      setError('No se pudieron cargar los productos');
    } finally {
      setCargando(false);
    }
  }

  async function manejarCrear(evento: FormEvent) {
    evento.preventDefault();
    setGuardando(true);
    setError(null);

    try {
      await crearProducto({
        nombreProducto,
        descripcion,
        precio: Number(precio),
        imgUrl,
        especificaciones: [],
        categoriaIds: [],
      });

      setNombreProducto('');
      setDescripcion('');
      setPrecio('');
      setImgUrl('');
      await cargarProductos();
    } catch (err) {
      setError('No se pudo crear el producto. Revisa los datos e intenta de nuevo.');
    } finally {
      setGuardando(false);
    }
  }

  async function manejarEliminar(id: number) {
    if (!confirm('¿Eliminar este producto?')) return;
    try {
      await eliminarProducto(id);
      setProductos((actuales) => actuales.filter((p) => p.id !== id));
    } catch (err) {
      setError('No se pudo eliminar el producto');
    }
  }

  return (
    <div className="min-h-screen">
      <NavBar />

      <div className="max-w-5xl mx-auto px-4 py-8">
        <h1 className="text-2xl mb-6">Mis productos</h1>

        <TagCard className="mb-8">
          <h2 className="text-base font-display font-semibold mb-4">Agregar producto</h2>
          <form onSubmit={manejarCrear} className="grid gap-4 sm:grid-cols-2">
            <Input
              id="nombreProducto"
              label="Nombre del producto"
              value={nombreProducto}
              onChange={(e) => setNombreProducto(e.target.value)}
              required
            />
            <Input
              id="precio"
              label="Precio"
              type="number"
              step="0.01"
              value={precio}
              onChange={(e) => setPrecio(e.target.value)}
              required
            />
            <div className="sm:col-span-2">
              <Input
                id="descripcion"
                label="Descripción"
                value={descripcion}
                onChange={(e) => setDescripcion(e.target.value)}
              />
            </div>
            <div className="sm:col-span-2">
              <SubidaImagen valor={imgUrl} onCambiar={setImgUrl} etiqueta="Foto del producto" />
            </div>

            {error && <p className="text-sm text-danger sm:col-span-2">{error}</p>}

            <div className="sm:col-span-2">
              <Button type="submit" disabled={guardando}>
                {guardando ? 'Guardando...' : 'Agregar producto'}
              </Button>
            </div>
          </form>
        </TagCard>

        {cargando ? (
          <p className="text-ink-soft">Cargando productos...</p>
        ) : productos.length === 0 ? (
          <p className="text-ink-soft">Todavía no tienes productos. Agrega el primero arriba.</p>
        ) : (
          <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-4">
            {productos.map((producto) => (
              <TagCard key={producto.id}>
                {producto.imgUrl && (
                  <img
                    src={producto.imgUrl}
                    alt={producto.nombreProducto}
                    className="w-full h-32 object-cover rounded-md mb-3"
                  />
                )}
                <h3 className="font-display font-semibold text-sm">{producto.nombreProducto}</h3>
                <p className="precio text-amber-dark font-semibold mt-1">
                  ${producto.precio.toLocaleString('es-CO')}
                </p>
                {producto.descripcion && (
                  <p className="text-xs text-ink-soft mt-1">{producto.descripcion}</p>
                )}
                <Button variant="danger" className="mt-3 w-full" onClick={() => manejarEliminar(producto.id)}>
                  Eliminar
                </Button>
              </TagCard>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
