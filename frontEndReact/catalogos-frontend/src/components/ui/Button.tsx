import type { ButtonHTMLAttributes } from 'react';

interface Props extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'secondary' | 'ghost' | 'danger';
}

const ESTILOS: Record<string, string> = {
  primary: 'bg-amber text-ink hover:bg-amber-dark',
  secondary: 'bg-teal text-white hover:opacity-90',
  ghost: 'bg-transparent text-ink border border-line hover:bg-paper',
  danger: 'bg-transparent text-danger border border-danger/40 hover:bg-danger/5',
};

export function Button({ variant = 'primary', className = '', ...props }: Props) {
  return (
    <button
      className={`font-display font-semibold text-sm px-4 py-2 rounded-md transition-colors disabled:opacity-50 disabled:cursor-not-allowed ${ESTILOS[variant]} ${className}`}
      {...props}
    />
  );
}
