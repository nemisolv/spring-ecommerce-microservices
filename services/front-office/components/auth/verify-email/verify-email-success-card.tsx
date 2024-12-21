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

export function VerifyEmailSuccessCard(props: CardProps): React.JSX.Element {
  return (
    <Card {...props}>
      <CardHeader>
        <CardTitle>Email verified</CardTitle>
        <CardDescription>
          Your email has been successfully verified. You can log in with your
          account now.
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
