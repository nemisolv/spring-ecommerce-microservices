'use client';

import * as React from 'react';
import { useRouter } from 'next/navigation';

import { Button } from '@/components/ui/button';
import { Routes } from '@/constants/routes';

export default function NotFound(): React.JSX.Element {
  const router = useRouter();
  const handleGoBack = (): void => {
    router.back();
  };
  const handleBackToHome = (): void => {
    router.push(Routes.Home);
  };
  return (
    <div className="absolute left-1/2 top-1/2 mb-16 -translate-x-1/2 -translate-y-1/2 items-center justify-center text-center">
      <span className="bg-gradient-to-b from-foreground to-transparent bg-clip-text text-[10rem] font-extrabold leading-none text-transparent">
        404
      </span>
      <h2 className="font-heading my-2 text-2xl font-bold">
        Something&apos;s missing
      </h2>
      <p>
        Sorry, the page you are looking for doesn&apos;t exist or has been
        moved.
      </p>
      <div className="mt-8 flex justify-center gap-2">
        <Button
          type="button"
          variant="default"
          size="lg"
          onClick={handleGoBack}
        >
          Go back
        </Button>
        <Button
          type="button"
          variant="ghost"
          size="lg"
          onClick={handleBackToHome}
        >
          Back to Home
        </Button>
      </div>
    </div>
  );
}
