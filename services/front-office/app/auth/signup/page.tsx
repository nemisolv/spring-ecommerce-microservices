import * as React from 'react';
import { type Metadata } from 'next';

import { AuthContainer } from '@/components/auth/auth-container';
import { SignUpCard } from '@/components/auth/sign-up/sign-up-card';
import { createTitle } from '@/lib/utils';

export const metadata: Metadata = {
  title: createTitle('Sign up')
};

export default function SignUpPage(): React.JSX.Element {
  return (
    <AuthContainer maxWidth="md">
      <SignUpCard />
    </AuthContainer>
  );
}
