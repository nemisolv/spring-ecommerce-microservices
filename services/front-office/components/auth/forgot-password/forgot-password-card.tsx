'use client';

import * as React from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { AlertCircleIcon, MailIcon } from 'lucide-react';
import { type SubmitHandler } from 'react-hook-form';

import { sendResetPasswordInstructions } from '@/actions/auth/send-reset-password-instructions';
import { Alert, AlertDescription } from '@/components/ui/alert';
import { Button } from '@/components/ui/button';
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
  type CardProps
} from '@/components/ui/card';
import {
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  FormProvider
} from '@/components/ui/form';
import { InputWithAdornments } from '@/components/ui/input-with-adornments';
import { Routes } from '@/constants/routes';
import { useZodForm } from '@/hooks/use-zod-form';
import {
  sendResetPasswordInstructionsSchema,
  type SendResetPasswordInstructionsSchema
} from '@/schemas/auth/send-reset-password-instructions-schema';

export function ForgotPasswordCard(props: CardProps): React.JSX.Element {
  const router = useRouter();
  const [errorMessage, setErrorMessage] = React.useState<string>();
  const methods = useZodForm({
    schema: sendResetPasswordInstructionsSchema,
    mode: 'onSubmit',
    defaultValues: {
      email: ''
    }
  });
  const canSubmit = !methods.formState.isSubmitting;
  const onSubmit: SubmitHandler<SendResetPasswordInstructionsSchema> = async (
    values
  ) => {
    if (!canSubmit) {
      return;
    }
    const result = await sendResetPasswordInstructions(values);
    if (!result?.serverError && !result?.validationErrors) {
      setErrorMessage(undefined);
      router.replace(`${Routes.ForgotPasswordSuccess}?email=${values.email}`);
    } else {
      setErrorMessage("Couldn't request password change");
    }
  };
  return (
    <Card {...props}>
      <CardHeader>
        <CardTitle>Forgot your password?</CardTitle>
        <CardDescription>
          No worries! We'll send you a link with instructions on how to reset
          your password.
        </CardDescription>
      </CardHeader>
      <CardContent>
        <FormProvider {...methods}>
          <form
            onSubmit={methods.handleSubmit(onSubmit)}
            className="flex flex-col gap-4"
          >
            <FormField
              control={methods.control}
              name="email"
              render={({ field }) => (
                <FormItem className="flex flex-col">
                  <FormLabel>Email</FormLabel>
                  <FormControl>
                    <InputWithAdornments
                      {...field}
                      type="email"
                      maxLength={255}
                      autoCapitalize="off"
                      autoComplete="username"
                      startAdornment={<MailIcon className="size-4 shrink-0" />}
                      disabled={methods.formState.isSubmitting}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            {errorMessage && (
              <Alert variant="destructive">
                <div className="flex flex-row items-center gap-2">
                  <AlertCircleIcon className="size-[18px] shrink-0" />
                  <AlertDescription>{errorMessage}</AlertDescription>
                </div>
              </Alert>
            )}
            <Button
              type="submit"
              variant="default"
              className="w-full"
              disabled={!canSubmit}
              loading={methods.formState.isSubmitting}
            >
              Send instructions
            </Button>
          </form>
        </FormProvider>
      </CardContent>
      <CardFooter className="flex justify-center gap-1 text-sm text-muted-foreground">
        <span>Remembered your password?</span>
        <Link
          href={Routes.Login}
          className="text-foreground underline"
        >
          Log in
        </Link>
      </CardFooter>
    </Card>
  );
}
