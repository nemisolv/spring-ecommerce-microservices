import * as React from 'react';
import { cva, type VariantProps } from 'class-variance-authority';

import { Logo } from '@/components/ui/logo';
import Link from 'next/link';
import { Routes } from '@/constants/routes';

const containerVariants = cva('mx-auto w-full min-w-[306px] space-y-6', {
  variants: {
    maxWidth: {
      sm: 'max-w-sm',
      md: 'max-w-md',
      lg: 'max-w-lg'
    }
  },
  defaultVariants: {
    maxWidth: 'sm'
  }
});

export type AuthContainerProps = React.PropsWithChildren &
  VariantProps<typeof containerVariants>;

export function AuthContainer({
  maxWidth,
  children
}: AuthContainerProps): React.JSX.Element {
  return (
    <div className={containerVariants({ maxWidth })}>
       <Link href={Routes.Root}>
      <Logo className="justify-center" />
      </Link>
      {children}
    </div>
  );
}
