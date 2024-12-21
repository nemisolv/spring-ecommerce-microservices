import * as React from 'react';
import { type Metadata } from 'next';

import { AuthContainer } from '@/components/auth/auth-container';
import { VerifyEmailSuccessCard } from '@/components/auth/verify-email/verify-email-success-card';
import { createTitle } from '@/lib/utils';

export const metadata: Metadata = {
  title: createTitle('Email Verification Success')
};

export default async function EmailVerificationSuccessPage(): Promise<React.JSX.Element> {
  return (
    <AuthContainer maxWidth="sm">
      <VerifyEmailSuccessCard />
    </AuthContainer>
  );
}
