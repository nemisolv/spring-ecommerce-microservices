import * as React from 'react';

import { AuthContainer } from '@/components/auth/auth-container';
import { ResetPasswordSuccessCard } from '@/components/auth/reset-password/reset-password-success-card';

export default function ResetPasswordSuccessPage(): React.JSX.Element {
  return (
    <AuthContainer maxWidth="sm">
      <ResetPasswordSuccessCard />
    </AuthContainer>
  );
}
