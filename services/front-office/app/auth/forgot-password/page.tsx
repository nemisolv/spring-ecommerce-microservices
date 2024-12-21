import * as React from 'react';
import { type Metadata } from 'next';

import { AuthContainer } from '@/components/auth/auth-container';
import { ForgotPasswordCard } from '@/components/auth/forgot-password/forgot-password-card';
import { createTitle } from '@/lib/utils';

export const metadata: Metadata = {
  title: createTitle('Forgot password')
};

export default function ForgotPasswordPage(): React.JSX.Element {
  return (
    <AuthContainer maxWidth="sm">
      <ForgotPasswordCard />
    </AuthContainer>
  );
}
