import axiosClient from './axiosClient';
import type { CatalogoRequest, CatalogoResponse } from '../types/catalogo.types';

export async function listarMisCatalogos(): Promise<CatalogoResponse[]> {
  const respuesta = await axiosClient.get<CatalogoResponse[]>('/catalogos');
  return respuesta.data;
}

export async function crearCatalogo(datos: CatalogoRequest): Promise<CatalogoResponse> {
  const respuesta = await axiosClient.post<CatalogoResponse>('/catalogos', datos);
  return respuesta.data;
}

export async function agregarProductoACatalogo(
  catalogoId: number,
  productoId: number
): Promise<CatalogoResponse> {
  const respuesta = await axiosClient.post<CatalogoResponse>(
    `/catalogos/${catalogoId}/productos/${productoId}`
  );
  return respuesta.data;
}

export async function quitarProductoDeCatalogo(
  catalogoId: number,
  productoId: number
): Promise<CatalogoResponse> {
  const respuesta = await axiosClient.delete<CatalogoResponse>(
    `/catalogos/${catalogoId}/productos/${productoId}`
  );
  return respuesta.data;
}

// El PDF es binario, no JSON -- por eso pedimos responseType: 'blob'.
// Sin esto, axios intentaría parsear los bytes del PDF como texto/JSON
// y el archivo saldría corrupto.
export async function descargarCatalogoPdf(catalogoId: number): Promise<Blob> {
  const respuesta = await axiosClient.get(`/catalogos/${catalogoId}/pdf`, {
    responseType: 'blob',
  });
  return respuesta.data;
}
