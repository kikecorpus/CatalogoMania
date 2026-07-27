import axiosClient from './axiosClient';
import type { LoginRequest, LoginResponse, RegistroRequest, TiendaResponse } from '../types/auth.types';

// Cada función corresponde 1 a 1 con un endpoint de tu AuthController /
// TiendaController -- mismo patrón que un Service de Java, solo que aquí
// no hay Spring que "adivine" la URL: la escribes explícita cada vez.

export async function login(datos: LoginRequest): Promise<LoginResponse> {
  const respuesta = await axiosClient.post<LoginResponse>('/auth/login', datos);
  return respuesta.data;
}

export async function registrarTienda(datos: RegistroRequest): Promise<TiendaResponse> {
  const respuesta = await axiosClient.post<TiendaResponse>('/tiendas/registro', datos);
  return respuesta.data;
}

export async function obtenerTienda(id: number): Promise<TiendaResponse> {
  const respuesta = await axiosClient.get<TiendaResponse>(`/tiendas/${id}`);
  return respuesta.data;
}
