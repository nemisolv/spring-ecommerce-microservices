import * as React from 'react';

import { MINIMUM_PASSWORD_LENGTH } from '@/constants/limits';
import { passwordValidator } from '@/lib/auth/password';
import { cn } from '@/lib/utils';
import type { Maybe } from '@/types/maybe';

export type PasswordRequirementListProps = {
  password: Maybe<string>;
};

export function PasswordRequirementList({
  password
}: PasswordRequirementListProps): React.JSX.Element {
  const containsLowerAndUpperCase =
    passwordValidator.containsLowerAndUpperCase(password);
  const hasMinimumLength = passwordValidator.hasMinimumLength(password);
  const containsNumber = passwordValidator.containsNumber(password);
  return (
    <ul className="list-none space-y-1 py-2">
      <li
        className={cn(
          'flex flex-row items-center px-4',
          containsLowerAndUpperCase ? 'text-green-500' : 'text-muted-foreground'
        )}
      >
        {containsLowerAndUpperCase ? <CheckMarkIcon /> : <BulletPointIcon />}
        <p className="text-sm">Mix of uppercase & lowercase letters</p>
      </li>
      <li
        className={cn(
          'flex flex-row items-center px-4',
          hasMinimumLength ? 'text-green-500' : 'text-muted-foreground'
        )}
      >
        {hasMinimumLength ? <CheckMarkIcon /> : <BulletPointIcon />}
        <p className="text-sm">
          Minimum {MINIMUM_PASSWORD_LENGTH} characters long
        </p>
      </li>
      <li
        className={cn(
          'flex flex-row items-center px-4',
          containsNumber ? 'text-green-500' : 'text-muted-foreground'
        )}
      >
        {containsNumber ? <CheckMarkIcon /> : <BulletPointIcon />}
        <p className="text-sm">Contain at least 1 number</p>
      </li>
    </ul>
  );
}

function BulletPointIcon(): React.JSX.Element {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width="5"
      height="5"
      fill="currentColor"
      stroke="currentColor"
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      viewBox="0 0 24 24"
      className="mr-2 inline-block"
    >
      <circle
        cx="12"
        cy="12"
        r="10"
      />
    </svg>
  );
}

function CheckMarkIcon(): React.JSX.Element {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width="12"
      height="12"
      fill="none"
      viewBox="0 0 24 24"
      stroke="currentColor"
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="3"
      className="-ml-1.5 mr-1 inline-block"
    >
      <path d="M20 6L9 17 4 12" />
    </svg>
  );
}
