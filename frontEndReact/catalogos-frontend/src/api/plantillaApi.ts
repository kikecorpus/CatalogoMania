import axiosClient from './axiosClient';
import type { PlantillaResponse } from '../types/catalogo.types';

export async function listarPlantillas(): Promise<PlantillaResponse[]> {
  const respuesta = await axiosClient.get<PlantillaResponse[]>('/plantillas');
  return respuesta.data;
}
