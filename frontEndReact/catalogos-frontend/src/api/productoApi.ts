import axiosClient from './axiosClient';
import type { ProductoRequest, ProductoResponse } from '../types/producto.types';

export async function listarMisProductos(): Promise<ProductoResponse[]> {
  const respuesta = await axiosClient.get<ProductoResponse[]>('/productos');
  return respuesta.data;
}

export async function crearProducto(datos: ProductoRequest): Promise<ProductoResponse> {
  const respuesta = await axiosClient.post<ProductoResponse>('/productos', datos);
  return respuesta.data;
}

export async function actualizarProducto(id: number, datos: ProductoRequest): Promise<ProductoResponse> {
  const respuesta = await axiosClient.put<ProductoResponse>(`/productos/${id}`, datos);
  return respuesta.data;
}

export async function eliminarProducto(id: number): Promise<void> {
  await axiosClient.delete(`/productos/${id}`);
}
