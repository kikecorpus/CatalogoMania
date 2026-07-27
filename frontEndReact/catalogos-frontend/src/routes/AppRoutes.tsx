import { Routes, Route, Navigate } from 'react-router-dom';
import { LoginPage } from '../pages/LoginPage';
import { RegistroPage } from '../pages/RegistroPage';
import { ProductosPage } from '../pages/ProductosPage';
import { CatalogosPage } from '../pages/CatalogosPage';
import { RutaProtegida } from './RutaProtegida';

export function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/registro" element={<RegistroPage />} />

      <Route
        path="/productos"
        element={
          <RutaProtegida>
            <ProductosPage />
          </RutaProtegida>
        }
      />

      <Route
        path="/catalogos"
        element={
          <RutaProtegida>
            <CatalogosPage />
          </RutaProtegida>
        }
      />

      <Route path="/" element={<Navigate to="/productos" replace />} />
    </Routes>
  );
}
