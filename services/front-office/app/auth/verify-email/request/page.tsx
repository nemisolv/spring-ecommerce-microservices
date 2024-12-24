import * as React from 'react';
import { type Metadata } from 'next';
import { notFound, redirect } from 'next/navigation';
import { createSearchParamsCache, parseAsString } from 'nuqs/server';

import { verifyEmailWithToken } from '@/actions/auth/authenticatation';
import { Routes } from '@/constants/routes';
import { createTitle } from '@/lib/utils';
import type { NextPageProps } from '@/types/next-page-props';
import { ResultCode } from '@/constants/api-result-code';
import { toast } from 'sonner';

const paramsCache = createSearchParamsCache({
  token: parseAsString.withDefault('')
});

export const metadata: Metadata = {
  title: createTitle('Email Verification')
};

export default async function EmailVerificationPage({
  searchParams
}: NextPageProps): Promise<React.JSX.Element> {
  const { token } = await paramsCache.parse(searchParams);
  if (!token) {
    return notFound();
  }
  try {
  await verifyEmailWithToken({ token: token });

  }catch(e) {
    const {code} = e.data;
    if (code === ResultCode.EMAIL_ALREADY_VERIFIED) {
      return redirect(Routes.VerifyEmailSuccess);
    } else if(code === ResultCode.TOKEN_RESET_PASSWORD_USED) {
      return redirect(Routes.ResetPasswordSuccess);
    }else if(code === ResultCode.TOKEN_EXPIRED) {
      toast.error("Your verification link has expired. Please request a new one.");
    }else {
      toast.error("Something went wrong. Please try again.");
    }
  }


  
  // const verificationToken = await prisma.verificationToken.findFirst({
  //   where: { token },
  //   select: { identifier: true, expires: true }
  // });
  // if (!verificationToken) {
  //   return notFound();
  // }
  // const user = await prisma.user.findFirst({
  //   where: { email: verificationToken.identifier },
  //   select: { emailVerified: true }
  // });
  // if (!user) {
  //   return notFound();
  // }
  // if (user.emailVerified) {
  //   return redirect(Routes.VerifyEmailSuccess);
  // }

  // if (isAfter(new Date(), verificationToken.expires)) {
  //   return redirect(
  //     `${Routes.VerifyEmailExpired}?email=${verificationToken.identifier}`
  //   );
  // }


  return redirect(Routes.VerifyEmailSuccess);
}
