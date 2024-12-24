import * as React from 'react';
import { type Metadata } from 'next';

import { AuthContainer } from '@/components/auth/auth-container';
import { LoginCard } from '@/components/auth/login/login-card';
import { createTitle } from '@/lib/utils';


export const metadata: Metadata = {
  title: createTitle('Log in')
};

export default async function LoginPage(): Promise<React.JSX.Element> {
  return (
    <AuthContainer maxWidth="sm">
      <LoginCard />
    </AuthContainer>
  );
}
