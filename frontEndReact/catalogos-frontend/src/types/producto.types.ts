export interface CategoriaResponse {
  id: number;
  nombreCategoria: string;
  descripcion?: string;
}

export interface CategoriaRequest {
  nombreCategoria: string;
  descripcion?: string;
}

export interface MedidaResponse {
  id: number;
  nombreMedida: string;
  descripcion?: string;
}

export interface MedidaRequest {
  nombreMedida: string;
  descripcion?: string;
}

export interface EspecificacionRequest {
  medidaId: number;
  valor: string;
}

export interface EspecificacionResponse {
  id: number;
  nombreMedida: string;
  valor: string;
}

export interface ProductoRequest {
  nombreProducto: string;
  descripcion?: string;
  precio: number;
  imgUrl?: string;
  especificaciones: EspecificacionRequest[];
  categoriaIds: number[];
}

export interface ProductoResponse {
  id: number;
  nombreProducto: string;
  descripcion?: string;
  precio: number;
  imgUrl?: string;
  tiendaId: number;
  especificaciones: EspecificacionResponse[];
  categorias: CategoriaResponse[];
}
