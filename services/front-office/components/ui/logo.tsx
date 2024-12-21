import * as React from 'react';

import { AppInfo } from '@/constants/app-info';
import { cn } from '@/lib/utils';

// The logo size below is 28px x 28px but in a 36px x 36px container.
// Because of the 8px difference the components <Sidebar /> and <Mobilesheet /> have a pl-0.5 (4px left padding) class applied.
// When you update the logo make sure to eventually adjust the pl-0.5 class in those two components.

export type LogoProps = React.HTMLAttributes<HTMLDivElement> & {
  hideSymbol?: boolean;
  hideWordmark?: boolean;
};

export function Logo({
  hideSymbol,
  hideWordmark,
  className,
  ...other
}: LogoProps): React.JSX.Element {
  return (
    <div
      className={cn('flex items-center space-x-2', className)}
      {...other}
    >
      {!hideSymbol && (
        <div className="flex size-9 items-center justify-center p-1">
          <div className="flex size-7 items-center justify-center rounded-md border text-primary">
            <svg
              width="16"
              height="16"
              viewBox="0 0 24 24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <g>
                <path
                  d="M7.81815 8.36373L12 0L24 24H15.2809L7.81815 8.36373Z"
                  fill="currentColor"
                />
                <path
                  d="M4.32142 15.3572L8.44635 24H-1.14809e-06L4.32142 15.3572Z"
                  fill="currentColor"
                />
              </g>
            </svg>
          </div>
        </div>
      )}
      {!hideWordmark && <span className="font-bold">{AppInfo.APP_NAME}</span>}
    </div>
  );
}
