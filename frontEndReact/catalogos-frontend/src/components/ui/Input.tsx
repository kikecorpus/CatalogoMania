import type { InputHTMLAttributes } from 'react';

interface Props extends InputHTMLAttributes<HTMLInputElement> {
  label: string;
}

export function Input({ label, id, className = '', ...props }: Props) {
  return (
    <div className="grid gap-1">
      <label htmlFor={id} className="text-xs font-medium text-ink-soft uppercase tracking-wide">
        {label}
      </label>
      <input
        id={id}
        className={`border border-line rounded-md px-3 py-2 text-sm bg-paper-raised text-ink focus:outline-none focus:ring-2 focus:ring-amber/50 focus:border-amber ${className}`}
        {...props}
      />
    </div>
  );
}
