'use client';

import * as React from 'react';
import Link from 'next/link';
import {
  AlertCircleIcon,
  ArrowRightIcon,
  LockIcon,
  MailIcon
} from 'lucide-react';
import GoogleLogo from 'public/google-logo.svg';
import MicrosoftLogo from 'public/microsoft-logo.svg';

import { OrContinueWith } from '@/components/auth/or-continue-with';
import { Alert, AlertDescription } from '@/components/ui/alert';
import { Button, buttonVariants } from '@/components/ui/button';
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
import { InputPassword } from '@/components/ui/input-password';
import { InputWithAdornments } from '@/components/ui/input-with-adornments';
import { Routes } from '@/constants/routes';
import { useZodForm } from '@/hooks/use-zod-form';
import { cn } from '@/lib/utils';
import {
  logInSchema,
  type LoginSchema}
  from '@/schemas/auth/login-schema';
import {   authenticate } from '@/actions/auth/authenticatation';
import ErrorResponse from '@/schemas/ErrorResponse';
import { ResultCode, ResultCodeMessages } from '@/constants/api-result-code';
import { useRouter } from 'next/navigation';
import { buildOAuth2Url } from '@/lib/auth/oauth2';
import { getProfile } from '@/actions/profile/get-profile';
import {  saveAuthority, saveUser } from '@/util/authUtil';
import { getMyAuthority } from '@/actions/permission/get-authority';



export function LoginCard(props: CardProps): React.JSX.Element {
  const [isLoading, setIsLoading] = React.useState<boolean>(false);
  const [errorMessage, setErrorMessage] = React.useState<string>();
  const router = useRouter();
  // currently, for automatic verify purpose but not impl
  const [unverifiedEmail, setUnverifiedEmail] = React.useState<
  string | undefined
  >();


  const methods = useZodForm({
    schema: logInSchema,
    mode: 'onSubmit',
    defaultValues: {
      email: '',
      password: ''
    }
  });
  const canSubmit = !isLoading && !methods.formState.isSubmitting;
  const onSubmit = async (values: LoginSchema): Promise<void> => {
    if (!canSubmit) {
      return;
    }
    try {
      setIsLoading(true);
      await authenticate(values) 
      const profile = await getProfile();
      saveUser(profile?.data);
      const myAuthority = await getMyAuthority();
      saveAuthority(myAuthority?.data); 
      router.push(Routes.Home)
     

    }catch(e) {
      const errorData = e?.data as ErrorResponse;
      console.log("🚀 ~ onSubmit ~ errorData::", errorData)
      if(!errorData) {
        setUnverifiedEmail(undefined);
        setErrorMessage(ResultCodeMessages[ResultCode.UNKNOWN_ERROR]);
        return;
      }
      const {code} = errorData;
      if(!code) return 

      if(code === ResultCode.AUTHENTICATION_FAILED) {
        setErrorMessage(ResultCodeMessages[code]);
        return;
      }


        // if not verified => auto verify
        setUnverifiedEmail(code === ResultCode.EMAIL_NOT_VERIFIED ? values.email : undefined);
        setErrorMessage(ResultCodeMessages[code in ResultCodeMessages ? code as ResultCode : ResultCode.UNKNOWN_ERROR]);
    }finally {
      setIsLoading(false)
    }

    
  };

  // explain why we use link directly instead of function: The authentication flow must happen in a visible browsing context, not with a fetch request
  // refer: https://stackoverflow.com/questions/72382892/access-to-fetch-at-https-accounts-google-com-o-oauth2-v2-auth-has-been-blocked
  
  // const handleSignInWithGoogle = async (): Promise<void> => {
  //   if (!canSubmit) {
  //     return;
  //   }
  //   setIsLoading(true);
  //   try {
  //     await continueWithGoogle();
  //   // eslint-disable-next-line @typescript-eslint/no-unused-vars
  //   }catch(e) {
  //     toast.error("Couldn't continue with Google");
  //   }
    
  //   setIsLoading(false);
  // };
  // const handleSignInWithMicrosoft = async (): Promise<void> => {
  //   if (!canSubmit) {
  //     return;
  //   }
  //   setIsLoading(true);
  //   try {
  //    await continueWithMicrosoft();
  //   // eslint-disable-next-line @typescript-eslint/no-unused-vars
  //   }catch(e) {
  //     toast.error("Couldn't continue with Microsoft");
  //   }

   
  //   setIsLoading(false);
  // };
  return (
    <Card {...props}>
      <CardHeader>
        <CardTitle>Log in</CardTitle>
        <CardDescription>
          Enter your details below to sign into your account.
        </CardDescription>
      </CardHeader>
      <CardContent className="flex flex-col gap-4">
        <FormProvider {...methods}>
          <form
            className="flex flex-col gap-4"
            onSubmit={methods.handleSubmit(onSubmit)}
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
            <FormField
              control={methods.control}
              name="password"
              render={({ field }) => (
                <FormItem className="flex flex-col">
                  <div className="flex flex-row items-center justify-between">
                    <FormLabel>Password</FormLabel>
                    <Link
                      href={Routes.ForgotPassword}
                      className="ml-auto inline-block text-sm underline"
                    >
                      Forgot your password?
                    </Link>
                  </div>
                  <FormControl>
                    <InputPassword
                      {...field}
                      maxLength={72}
                      autoCapitalize="off"
                      autoComplete="current-password"
                      startAdornment={<LockIcon className="size-4 shrink-0" />}
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
                  <AlertDescription>
                    {errorMessage}
                    {unverifiedEmail && (
                      <Link
                        className={cn(
                          buttonVariants({ variant: 'link' }),
                          'ml-0.5 h-fit gap-0.5 px-0.5 py-0 text-foreground underline'
                        )}
                        // auto redirect to verify email page -> but not send OTP, user need to click 'resend' -> my logic business
                        href={`${Routes.VerifyEmail}?email=${encodeURIComponent(unverifiedEmail)}`}
                      >
                        Verify email
                        <ArrowRightIcon className="size-3 shrink-0" />
                      </Link>
                    )}
                  </AlertDescription>
                </div>
              </Alert>
            )}
            <Button
              type="submit"
              variant="default"
              className="w-full"
              disabled={!canSubmit}
              loading={methods.formState.isSubmitting}
              onClick={methods.handleSubmit(onSubmit)}
            >
              Log in
            </Button>
          </form>
        </FormProvider>
        <OrContinueWith />
        <div className="flex flex-row gap-4">
        
            <Link
            className='flex w-full flex-row items-center justify-center gap-2 border border-input rounded-md py-1'
            href={buildOAuth2Url('google')}
            >
            <GoogleLogo
              width="20"
              height="20"
            />
            Google
            </Link>
            <Link
            className='flex w-full flex-row items-center justify-center gap-2 border border-input rounded-md  py-1'
            href={buildOAuth2Url('azure')}
            >
            <MicrosoftLogo
              width="20"
              height="20"
            />
            Microsoft
            </Link>
        </div>
      </CardContent>
      <CardFooter className="flex justify-center gap-1 text-sm text-muted-foreground">
        <span>Don&apos;t have an account?</span>
        <Link
          href={Routes.SignUp}
          className="text-foreground underline"
        >
          Sign up
        </Link>
      </CardFooter>
    </Card>
  );
}
