import axios from 'axios';

// Reemplaza estos dos valores con los tuyos (Cloud Name y el nombre del
// upload preset "unsigned" que creaste en el Dashboard de Cloudinary).
const CLOUD_NAME = 'ejsyl83n';
const UPLOAD_PRESET = 'catalogomania';

const CLOUDINARY_URL = `https://api.cloudinary.com/v1_1/${CLOUD_NAME}/image/upload`;

/**
 * Sube una imagen directamente a Cloudinary (sin pasar por tu backend de
 * Spring Boot) y devuelve la URL pública final.
 *
 * Nota: esta petición NO usa axiosClient (el que armamos para tu API),
 * porque Cloudinary no espera ni necesita tu token JWT -- usa un cliente
 * de axios "limpio", sin el interceptor que le agrega el Authorization.
 */
export async function subirImagen(archivo: File): Promise<string> {
  const formData = new FormData();
  formData.append('file', archivo);
  formData.append('upload_preset', UPLOAD_PRESET);

  const respuesta = await axios.post(CLOUDINARY_URL, formData);
  return respuesta.data.secure_url;
}
