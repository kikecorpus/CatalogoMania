import { useEffect, useState } from 'react';
import type { FormEvent } from 'react';
import {
  listarMisCatalogos,
  crearCatalogo,
  agregarProductoACatalogo,
  quitarProductoDeCatalogo,
  descargarCatalogoPdf,
} from '../api/catalogoApi';
import { listarPlantillas } from '../api/plantillaApi';
import { listarMisProductos } from '../api/productoApi';
import type { CatalogoResponse, PlantillaResponse } from '../types/catalogo.types';
import type { ProductoResponse } from '../types/producto.types';
import { SubidaImagen } from '../components/SubidaImagen';
import { NavBar } from '../components/ui/NavBar';
import { TagCard } from '../components/ui/TagCard';
import { Button } from '../components/ui/Button';
import { Input } from '../components/ui/Input';

export function CatalogosPage() {
  const [catalogos, setCatalogos] = useState<CatalogoResponse[]>([]);
  const [plantillas, setPlantillas] = useState<PlantillaResponse[]>([]);
  const [productos, setProductos] = useState<ProductoResponse[]>([]);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const [nombreCatalogo, setNombreCatalogo] = useState('');
  const [plantillaId, setPlantillaId] = useState('');
  const [colorPrincipal, setColorPrincipal] = useState('#E8A33D');
  const [colorSecundario, setColorSecundario] = useState('#1B2A4A');
  const [logotipo, setLogotipo] = useState('');
  const [guardando, setGuardando] = useState(false);

  const [productoSeleccionado, setProductoSeleccionado] = useState<Record<number, string>>({});

  useEffect(() => {
    cargarDatos();
  }, []);

  async function cargarDatos() {
    setCargando(true);
    setError(null);
    try {
      const [catalogosData, plantillasData, productosData] = await Promise.all([
        listarMisCatalogos(),
        listarPlantillas(),
        listarMisProductos(),
      ]);
      setCatalogos(catalogosData);
      setPlantillas(plantillasData);
      setProductos(productosData);
    } catch (err) {
      setError('No se pudieron cargar los datos');
    } finally {
      setCargando(false);
    }
  }

  async function manejarCrear(evento: FormEvent) {
    evento.preventDefault();
    setGuardando(true);
    setError(null);

    try {
      await crearCatalogo({
        nombreCatalogo,
        plantillaId: Number(plantillaId),
        personalizacion: { logotipo, colorPrincipal, colorSecundario },
      });

      setNombreCatalogo('');
      setPlantillaId('');
      setLogotipo('');
      await cargarDatos();
    } catch (err) {
      setError('No se pudo crear el catálogo. Revisa los datos e intenta de nuevo.');
    } finally {
      setGuardando(false);
    }
  }

  async function manejarAgregarProducto(catalogoId: number) {
    const productoId = productoSeleccionado[catalogoId];
    if (!productoId) return;
    try {
      await agregarProductoACatalogo(catalogoId, Number(productoId));
      await cargarDatos();
    } catch (err) {
      setError('No se pudo agregar el producto al catálogo');
    }
  }

  async function manejarQuitarProducto(catalogoId: number, productoId: number) {
    try {
      await quitarProductoDeCatalogo(catalogoId, productoId);
      await cargarDatos();
    } catch (err) {
      setError('No se pudo quitar el producto del catálogo');
    }
  }

  async function manejarDescargarPdf(catalogoId: number, nombre: string) {
    try {
      const blob = await descargarCatalogoPdf(catalogoId);
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = `${nombre}.pdf`;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      window.URL.revokeObjectURL(url);
    } catch (err) {
      setError('No se pudo descargar el PDF');
    }
  }

  if (cargando) return <p className="text-ink-soft p-8">Cargando...</p>;

  return (
    <div className="min-h-screen">
      <NavBar />

      <div className="max-w-5xl mx-auto px-4 py-8">
        <h1 className="text-2xl mb-6">Mis catálogos</h1>

        {error && <p className="text-sm text-danger mb-4">{error}</p>}

        <TagCard className="mb-8">
          <h2 className="text-base font-display font-semibold mb-4">Crear catálogo</h2>
          <form onSubmit={manejarCrear} className="grid gap-4 sm:grid-cols-2">
            <Input
              id="nombreCatalogo"
              label="Nombre del catálogo"
              value={nombreCatalogo}
              onChange={(e) => setNombreCatalogo(e.target.value)}
              required
            />

            <div className="grid gap-1">
              <label className="text-xs font-medium text-ink-soft uppercase tracking-wide">Plantilla</label>
              <select
                value={plantillaId}
                onChange={(e) => setPlantillaId(e.target.value)}
                required
                className="border border-line rounded-md px-3 py-2 text-sm bg-paper-raised text-ink focus:outline-none focus:ring-2 focus:ring-amber/50"
              >
                <option value="">-- Selecciona una plantilla --</option>
                {plantillas.map((p) => (
                  <option key={p.id} value={p.id}>
                    {p.nombrePlantilla}
                  </option>
                ))}
              </select>
            </div>

            <div className="sm:col-span-2">
              <SubidaImagen valor={logotipo} onCambiar={setLogotipo} etiqueta="Logo de la tienda (opcional)" />
            </div>

            <div className="flex gap-6 sm:col-span-2">
              <label className="flex items-center gap-2 text-sm text-ink-soft">
                Color principal
                <input
                  type="color"
                  value={colorPrincipal}
                  onChange={(e) => setColorPrincipal(e.target.value)}
                  className="w-8 h-8 rounded border border-line"
                />
              </label>
              <label className="flex items-center gap-2 text-sm text-ink-soft">
                Color secundario
                <input
                  type="color"
                  value={colorSecundario}
                  onChange={(e) => setColorSecundario(e.target.value)}
                  className="w-8 h-8 rounded border border-line"
                />
              </label>
            </div>

            <div className="sm:col-span-2">
              <Button type="submit" disabled={guardando}>
                {guardando ? 'Creando...' : 'Crear catálogo'}
              </Button>
            </div>
          </form>
        </TagCard>

        {catalogos.length === 0 ? (
          <p className="text-ink-soft">Todavía no tienes catálogos. Crea el primero arriba.</p>
        ) : (
          <div className="grid gap-4">
            {catalogos.map((catalogo) => {
              const idsEnCatalogo = new Set(catalogo.productos.map((p) => p.id));
              const disponibles = productos.filter((p) => !idsEnCatalogo.has(p.id));

              return (
                <TagCard key={catalogo.id}>
                  <div className="flex items-start justify-between gap-4">
                    <div>
                      <h3
                        className="font-display font-semibold text-lg"
                        style={{ color: catalogo.personalizacion.colorPrincipal ?? undefined }}
                      >
                        {catalogo.nombreCatalogo}
                      </h3>
                      <p className="text-xs text-ink-soft mt-0.5">
                        Plantilla: {catalogo.plantilla.nombrePlantilla} · {catalogo.cantidadProducto} producto(s)
                      </p>
                    </div>
                    <Button variant="secondary" onClick={() => manejarDescargarPdf(catalogo.id, catalogo.nombreCatalogo)}>
                      Descargar PDF
                    </Button>
                  </div>

                  {catalogo.productos.length > 0 && (
                    <ul className="mt-4 grid gap-2">
                      {catalogo.productos.map((producto) => (
                        <li
                          key={producto.id}
                          className="flex items-center justify-between text-sm border-t border-line pt-2"
                        >
                          <span>{producto.nombreProducto}</span>
                          <div className="flex items-center gap-3">
                            <span className="precio text-amber-dark font-semibold">
                              ${producto.precio.toLocaleString('es-CO')}
                            </span>
                            <button
                              onClick={() => manejarQuitarProducto(catalogo.id, producto.id)}
                              className="text-xs text-danger hover:underline"
                            >
                              Quitar
                            </button>
                          </div>
                        </li>
                      ))}
                    </ul>
                  )}

                  {disponibles.length > 0 && (
                    <div className="mt-4 flex gap-2">
                      <select
                        value={productoSeleccionado[catalogo.id] ?? ''}
                        onChange={(e) =>
                          setProductoSeleccionado((actual) => ({ ...actual, [catalogo.id]: e.target.value }))
                        }
                        className="flex-1 border border-line rounded-md px-3 py-2 text-sm bg-paper-raised"
                      >
                        <option value="">-- Selecciona un producto --</option>
                        {disponibles.map((producto) => (
                          <option key={producto.id} value={producto.id}>
                            {producto.nombreProducto}
                          </option>
                        ))}
                      </select>
                      <Button variant="ghost" onClick={() => manejarAgregarProducto(catalogo.id)}>
                        Agregar
                      </Button>
                    </div>
                  )}
                </TagCard>
              );
            })}
          </div>
        )}
      </div>
    </div>
  );
}
