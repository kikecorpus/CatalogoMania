import type { ProductoResponse } from './producto.types';

export interface CategoriaPResponse {
  id: number;
  nombreCategoria: string;
  descripcion?: string;
}

export interface CategoriaPRequest {
  nombreCategoria: string;
  descripcion?: string;
}

export interface PlantillaResponse {
  id: number;
  nombrePlantilla: string;
  estado: string;
  gratis: boolean;
  categoriaP: CategoriaPResponse;
}

export interface PlantillaRequest {
  nombrePlantilla: string;
  estado?: string;
  gratis?: boolean;
  categoriaPId: number;
}

export interface PersonalizacionRequest {
  logotipo?: string;
  colorPrincipal?: string;
  colorSecundario?: string;
}

export interface PersonalizacionResponse {
  id: number;
  logotipo?: string;
  colorPrincipal?: string;
  colorSecundario?: string;
}

export interface CatalogoRequest {
  nombreCatalogo: string;
  plantillaId: number;
  personalizacion: PersonalizacionRequest;
}

export interface CatalogoResponse {
  id: number;
  nombreCatalogo: string;
  cantidadProducto: number;
  estado: string;
  tiendaId: number;
  plantilla: PlantillaResponse;
  personalizacion: PersonalizacionResponse;
  productos: ProductoResponse[];
}
