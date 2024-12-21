import * as React from 'react';

import { cn } from '@/lib/utils';

export type InputWithAdornmentsElement = HTMLInputElement;
export type InputWithAdornmentsProps =
  React.InputHTMLAttributes<HTMLInputElement> & {
    startAdornment?: React.JSX.Element;
    endAdornment?: React.JSX.Element;
    containerClassName?: string;
  };
const InputWithAdornments = React.forwardRef<
  InputWithAdornmentsElement,
  InputWithAdornmentsProps
>(
  (
    { className, startAdornment, endAdornment, containerClassName, ...other },
    ref
  ) => (
    <div className={cn('relative inline-block h-9 w-full', containerClassName)}>
      {startAdornment && (
        <span className="absolute left-3 top-1/2 flex -translate-y-1/2 text-muted-foreground">
          {startAdornment}
        </span>
      )}
      <input
        ref={ref}
        className={cn(
          'flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50',
          startAdornment && endAdornment
            ? 'px-10'
            : startAdornment
              ? 'pl-10 pr-4'
              : endAdornment
                ? 'pl-4 pr-10'
                : '',
          className
        )}
        {...other}
      />
      {endAdornment && (
        <span className="absolute left-auto right-3 top-1/2 flex -translate-y-1/2 text-muted-foreground">
          {endAdornment}
        </span>
      )}
    </div>
  )
);
InputWithAdornments.displayName = 'InputWithAdornments';

export { InputWithAdornments };
