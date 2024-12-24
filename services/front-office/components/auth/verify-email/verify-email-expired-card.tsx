"use client";

import * as React from "react";
import { toast } from "sonner";

import { resendEmailConfirmation } from "@/actions/auth/authenticatation";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
  type CardProps,
} from "@/components/ui/card";

export type VerifyEmailExpiredCardProps = CardProps & {
  email: string;
};

export function VerifyEmailExpiredCard({
  email,
  ...other
}: VerifyEmailExpiredCardProps): React.JSX.Element {
  const [isResendingEmailVerification, setIsResendingEmailVerification] =
    React.useState<boolean>(false);
  const handleResendEmailVerification = async (): Promise<void> => {
    try {
      setIsResendingEmailVerification(true);
      await resendEmailConfirmation({ email });
      toast.success("Email verification resent");
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    } catch (e) {
      toast.error("Couldn't resend verification");
    }finally {
      setIsResendingEmailVerification(false);

    }
  };
  return (
    <Card {...other}>
      <CardHeader>
        <CardTitle>Email verification is expired</CardTitle>
        <CardDescription>
          Sorry, your email verification is already expired! You need to request
          a verification again.
        </CardDescription>
      </CardHeader>
      <CardFooter className="flex justify-center  gap-1 text-sm text-muted-foreground">
        Didn&apos;t receive an email?
        <Button
          type="button"
          variant="link"
          className="h-fit px-0.5 py-0 text-foreground underline"
          disabled={isResendingEmailVerification}
          onClick={handleResendEmailVerification}
        >
          Resend email verification
        </Button>
      </CardFooter>
    </Card>
  );
}
