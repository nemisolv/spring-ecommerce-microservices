import * as React from 'react';

import { cn } from '@/lib/utils';

export function OrContinueWith({
  className,
  ...other
}: React.HTMLAttributes<HTMLDivElement>): React.JSX.Element {
  return (
    <p
      className={cn(
        'flex items-center gap-x-3 text-sm text-muted-foreground before:h-px before:flex-1 before:bg-border after:h-px after:flex-1 after:bg-border',
        className
      )}
      {...other}
    >
      Or continue with
    </p>
  );
}
