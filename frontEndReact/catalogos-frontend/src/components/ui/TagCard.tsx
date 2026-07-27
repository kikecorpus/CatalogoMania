import type { ReactNode } from 'react';

interface Props {
  children: ReactNode;
  className?: string;
}

/**
 * Tarjeta con estética de "etiqueta de precio colgante": borde punteado
 * y un hueco de cordón en la esquina superior izquierda. Es el elemento
 * visual de identidad de la app -- una referencia directa al objeto físico
 * que la herramienta reemplaza (la etiqueta de precio de una tienda).
 *
 * Se usa para tarjetas de producto y de catálogo en toda la app.
 */
export function TagCard({ children, className = '' }: Props) {
  return (
    <div
      className={`relative bg-paper-raised border-2 border-dashed border-line rounded-lg p-4 pl-6 ${className}`}
    >
      {/* El "hueco" del cordón de la etiqueta */}
      <div className="absolute -left-[7px] top-4 w-3.5 h-3.5 rounded-full bg-paper border-2 border-dashed border-line" />
      {children}
    </div>
  );
}
