import * as React from 'react';
import Link from 'next/link';
import { InfoIcon } from 'lucide-react';

import { Alert, AlertDescription } from '@/components/ui/alert';
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
  type CardProps
} from '@/components/ui/card';
import { Routes } from '@/constants/routes';

export type ForgotPasswordSuccessCardProps = CardProps & {
  email: string;
};

export function ForgotPasswordSuccessCard({
  email,
  ...other
}: ForgotPasswordSuccessCardProps): React.JSX.Element {
  return (
    <Card {...other}>
      <CardHeader>
        <CardTitle>Reset instructions sent</CardTitle>
        <CardDescription>
          An email with a link and reset instructions is on its way to{' '}
          <strong className="text-foreground">{email}</strong>.
        </CardDescription>
      </CardHeader>
      <CardContent>
        <Alert variant="info">
          <div className="flex flex-row items-start gap-2">
            <InfoIcon className="mt-0.5 size-[18px] shrink-0" />
            <AlertDescription>
              If you don't receive an email soon, check that the email address
              you entered is correct, check your spam folder or reach out to
              support if the issue persists.
            </AlertDescription>
          </div>
        </Alert>
      </CardContent>
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
