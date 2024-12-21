import * as React from 'react';
import { type Metadata } from 'next';
import { createSearchParamsCache, parseAsString } from 'nuqs/server';

import { AuthContainer } from '@/components/auth/auth-container';
import { VerifyEmailExpiredCard } from '@/components/auth/verify-email/verify-email-expired-card';
import { createTitle } from '@/lib/utils';
import type { NextPageProps } from '@/types/next-page-props';

const searchParamsCache = createSearchParamsCache({
  email: parseAsString.withDefault('')
});

export const metadata: Metadata = {
  title: createTitle('Email Verification Expired')
};

export default async function VerifyEmailExpiredPage({
  searchParams
}: NextPageProps): Promise<React.JSX.Element> {
  const { email } = await searchParamsCache.parse(searchParams);
  return (
    <AuthContainer maxWidth="sm">
      <VerifyEmailExpiredCard email={email} />
    </AuthContainer>
  );
}
