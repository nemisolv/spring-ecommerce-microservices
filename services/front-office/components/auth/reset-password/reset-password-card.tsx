'use client';

import * as React from 'react';
import { formatDistanceToNow } from 'date-fns';
import { AlertCircleIcon, LockIcon } from 'lucide-react';
import { type SubmitHandler } from 'react-hook-form';

import { resetPassword } from '@/actions/auth/reset-password';
import { PasswordRequirementList } from '@/components/auth/password-requirement-list';
import { Alert, AlertDescription } from '@/components/ui/alert';
import { Button } from '@/components/ui/button';
import {
  Card,
  CardContent,
  CardDescription,
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
import { InputPassword } from '@/components/ui/input-password';
import { useZodForm } from '@/hooks/use-zod-form';
import {
  resetPasswordSchema,
  type ResetPasswordSchema
} from '@/schemas/auth/reset-password-schema';

export type ResetPasswordCardProps = CardProps & {
  requestId: string;
  expires: Date;
};

export function ResetPasswordCard({
  requestId,
  expires,
  ...other
}: ResetPasswordCardProps): React.JSX.Element {
  const [errorMessage, setErrorMessage] = React.useState<string>();
  const methods = useZodForm({
    schema: resetPasswordSchema,
    mode: 'onSubmit',
    defaultValues: {
      requestId,
      password: ''
    }
  });
  const password = methods.watch('password');
  const canSubmit = !methods.formState.isSubmitting;
  const onSubmit: SubmitHandler<ResetPasswordSchema> = async (values) => {
    if (!canSubmit) {
      return;
    }
    const result = await resetPassword(values);
    if (result?.serverError || result?.validationErrors) {
      setErrorMessage("Couldn't reset password.");
    }
  };
  return (
    <FormProvider {...methods}>
      <Card {...other}>
        <CardHeader>
          <CardTitle>Reset your password</CardTitle>
          <CardDescription suppressHydrationWarning>
            Use the form below to change your password. This request will expire
            in {expires ? formatDistanceToNow(expires) : ''}.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form
            onSubmit={methods.handleSubmit(onSubmit)}
            className="flex flex-col gap-4"
          >
            <input
              type="hidden"
              className="hidden"
              disabled={methods.formState.isSubmitting}
              {...methods.register('requestId')}
            />
            <FormField
              control={methods.control}
              name="password"
              render={({ field }) => (
                <FormItem className="flex flex-col">
                  <FormLabel>Password</FormLabel>
                  <FormControl>
                    <InputPassword
                      maxLength={72}
                      autoCapitalize="off"
                      startAdornment={<LockIcon className="size-4 shrink-0" />}
                      disabled={methods.formState.isSubmitting}
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <PasswordRequirementList password={password} />
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
              onClick={methods.handleSubmit(onSubmit)}
            >
              Change password
            </Button>
          </form>
        </CardContent>
      </Card>
    </FormProvider>
  );
}
