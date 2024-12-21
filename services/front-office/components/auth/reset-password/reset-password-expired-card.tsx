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

export function ResetPasswordExpiredCard(props: CardProps): React.JSX.Element {
  return (
    <Card {...props}>
      <CardHeader>
        <CardTitle>Reset request is expired</CardTitle>
        <CardDescription>
          Go back and enter the email associated with your account and we will
          send you another link with instructions to reset your password.
        </CardDescription>
      </CardHeader>
      <CardFooter className="flex justify-center text-sm">
        <Link
          href={Routes.ForgotPassword}
          className="text-foreground underline"
        >
          Want to try again?
        </Link>
      </CardFooter>
    </Card>
  );
}
