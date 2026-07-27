import axios from 'axios';

// Cliente axios base. Todas las funciones de api/*.ts lo usan en vez de
// axios directo, para no repetir la URL base y el manejo del token en
// cada archivo.
const axiosClient = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// INTERCEPTOR DE REQUEST: se ejecuta ANTES de que cada petición salga.
// Agrega automáticamente el header "Authorization: Bearer <token>" si hay
// un token guardado -- así no tienes que repetir esta lógica en cada
// llamada a la API, como haría @AuthenticationPrincipal del lado del backend.
axiosClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// INTERCEPTOR DE RESPONSE: si el backend devuelve 401 (token inválido o
// expirado), limpiamos el token guardado y mandamos al usuario al login.
// Esto evita que la app quede en un estado raro con un token viejo.
axiosClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default axiosClient;
