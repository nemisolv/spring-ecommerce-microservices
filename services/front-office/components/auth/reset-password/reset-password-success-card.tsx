import * as React from 'react';
import Link from 'next/link';

import {
  Card,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
  type CardProps
} from '@/components/ui/card';
import { Routes } from '@/constants/routes';

export function ResetPasswordSuccessCard(props: CardProps): React.JSX.Element {
  return (
    <Card {...props}>
      <CardHeader>
        <CardTitle>Password updated</CardTitle>
        <CardDescription>
          Your password has been successfully changed. Use your new password to
          log in.
        </CardDescription>
      </CardHeader>
      <CardFooter className="flex justify-center text-sm">
        <Link
          href={Routes.Login}
          className="text-foreground underline"
        >
          Back to log in
        </Link>
      </CardFooter>
    </Card>
  );
}
