import * as React from 'react';
import { type Metadata } from 'next';
import { notFound } from 'next/navigation';
import { createSearchParamsCache, parseAsString } from 'nuqs/server';

import { AuthContainer } from '@/components/auth/auth-container';
import { ResetPasswordCard } from '@/components/auth/reset-password/reset-password-card';
import { createTitle } from '@/lib/utils';
import type { NextPageProps } from '@/types/next-page-props';

const paramsCache = createSearchParamsCache({
  token: parseAsString.withDefault('')
});

export const metadata: Metadata = {
  title: createTitle('Reset password')
};

export default async function ResetPasswordPage({
  searchParams
}: NextPageProps): Promise<React.JSX.Element> {
  const { token } = await paramsCache.parse(searchParams);
  if (!token) {
    return notFound();
  }



  // const resetPasswordRequest = await prisma.resetPasswordRequest.findUnique({
  //   where: { id: requestId },
  //   select: {
  //     id: true,
  //     expires: true
  //   }
  // });

  // if (!resetPasswordRequest) {
  //   return notFound();
  // }

  // if (isAfter(new Date(), resetPasswordRequest.expires)) {
  //   return redirect(Routes.ResetPasswordExpired);
  // }

  return (
    <AuthContainer maxWidth="sm">
      <ResetPasswordCard
        token={token}
      />
    </AuthContainer>
  );
}
