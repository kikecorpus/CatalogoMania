// Equivalente TypeScript de tus DTOs de Java (AuthDTO y TiendaDTO).
// La idea es la misma: separar lo que ENVÍAS (Request) de lo que RECIBES (Response).

export interface LoginRequest {
  correo: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  tipo: string; // siempre "Bearer"
}

export interface RegistroRequest {
  nombreTienda: string;
  telefono?: string;
  correo: string;
  direccion?: string;
  password: string;
}

export interface TiendaResponse {
  id: number;
  nombreTienda: string;
  telefono?: string;
  correo: string;
  direccion?: string;
  admin: boolean;
}
