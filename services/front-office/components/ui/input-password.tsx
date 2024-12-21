'use client';

import * as React from 'react';
import { EyeIcon, EyeOffIcon } from 'lucide-react';

import { Button } from '@/components/ui/button';
import {
  InputWithAdornments,
  type InputWithAdornmentsElement,
  type InputWithAdornmentsProps
} from '@/components/ui/input-with-adornments';

export type InputPasswordElement = InputWithAdornmentsElement;
export type InputPasswordProps = Omit<InputWithAdornmentsProps, 'endAdornment'>;
const InputPassword = React.forwardRef<
  InputPasswordElement,
  InputPasswordProps
>((props, ref) => {
  const [showPassword, setShowPassword] = React.useState<boolean>(false);
  const handleClickShowPassword = (): void => {
    setShowPassword((prev) => !prev);
  };
  const handleMouseDownPassword = (event: React.SyntheticEvent): void => {
    event.preventDefault();
  };
  return (
    <InputWithAdornments
      ref={ref}
      type={showPassword ? 'text' : 'password'}
      endAdornment={
        <Button
          type="button"
          variant="ghost"
          size="icon"
          aria-label="Toggle password visibility"
          className="-mr-2.5 size-8"
          onClick={handleClickShowPassword}
          onMouseDown={handleMouseDownPassword}
          disabled={props.disabled}
        >
          {showPassword ? (
            <EyeOffIcon className="size-4 shrink-0" />
          ) : (
            <EyeIcon className="size-4 shrink-0" />
          )}
        </Button>
      }
      {...props}
    />
  );
});
InputPassword.displayName = 'InputPassword';

export { InputPassword };
