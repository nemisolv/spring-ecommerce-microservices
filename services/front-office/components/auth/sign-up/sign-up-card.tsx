/* eslint-disable @typescript-eslint/no-unused-vars */
"use client";

import * as React from "react";
import Link from "next/link";
import GoogleLogo from "public/google-logo.svg";
import MicrosoftLogo from "public/microsoft-logo.svg";
import { type SubmitHandler } from "react-hook-form";
import {
  continueWithGoogle,
  continueWithMicrosoft,
  signUp,
} from "@/actions/auth/authenticatation";

import { OrContinueWith } from "@/components/auth/or-continue-with";
import { PasswordRequirementList } from "@/components/auth/password-requirement-list";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
  type CardProps,
} from "@/components/ui/card";
import {
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  FormProvider,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { InputPassword } from "@/components/ui/input-password";
import { Routes } from "@/constants/routes";
import { useZodForm } from "@/hooks/use-zod-form";
import { signUpSchema, type SignUpSchema } from "@/schemas/auth/sign-up-schema";
import { toast } from "sonner";
import { ResultCode, ResultCodeMessages } from "@/constants/api-result-code";
import { useRouter } from "next/navigation";

export function SignUpCard(props: CardProps): React.JSX.Element {
  const router = useRouter();
  const methods = useZodForm({
    schema: signUpSchema,
    mode: "onSubmit",
    defaultValues: {
      name: "",
      email: "",
      password: "",
    },
  });
  const password = methods.watch("password");
  const onSubmit: SubmitHandler<SignUpSchema> = async (values) => {
    try {
      await signUp(values);
      // redirect to verify email
      router.push(`${Routes.VerifyEmail}?email=${encodeURIComponent(values.email)}`)

    } catch (e) {
      console.log("🚀 ~ constonSubmit:SubmitHandler<SignUpSchema>= ~ e::::", e)
      const {code} = e.data;
      if (code === ResultCode.USER_ALREADY_EXISTS) {
        toast.error(ResultCodeMessages[ResultCode.USER_ALREADY_EXISTS]);
      } else {
        toast.error("Couldn't sign up");
      }
    }
  };

  const handleSignInWithGoogle = async (): Promise<void> => {
    try {
      const result = await continueWithGoogle();
    } catch (e) {
      toast.error("Couldn't continue with Google");
    }
  };
  const handleSignInWithMicrosoft = async (): Promise<void> => {
    try {
      const result = await continueWithMicrosoft();
    } catch (e) {
      toast.error("Couldn't continue with Microsoft");
    }
  };
  return (
    <Card {...props}>
      <CardHeader>
        <CardTitle>Sign up</CardTitle>
        <CardDescription>
          Already have an account?{" "}
          <Link href={Routes.Login} className="text-foreground underline">
            Log in
          </Link>
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
              name="name"
              render={({ field }) => (
                <FormItem className="flex w-full flex-col">
                  <FormLabel>Name</FormLabel>
                  <FormControl>
                    <Input
                      type="text"
                      maxLength={64}
                      autoComplete="name"
                      disabled={methods.formState.isSubmitting}
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={methods.control}
              name="email"
              render={({ field }) => (
                <FormItem className="flex w-full flex-col">
                  <FormLabel>Email</FormLabel>
                  <FormControl>
                    <Input
                      type="email"
                      maxLength={255}
                      autoComplete="username"
                      disabled={methods.formState.isSubmitting}
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <div className="flex flex-col">
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
                        autoComplete="current-password"
                        disabled={methods.formState.isSubmitting}
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <PasswordRequirementList password={password} />
            </div>

            <Button
              type="submit"
              className="w-full"
              disabled={methods.formState.isSubmitting}
              loading={methods.formState.isSubmitting}
            >
              Create account
            </Button>
          </form>
        </FormProvider>
        <OrContinueWith />
        <div className="flex flex-row gap-4">
          <Button
            type="button"
            variant="outline"
            className="flex w-full flex-row items-center gap-2"
            disabled={methods.formState.isSubmitting}
            onClick={handleSignInWithGoogle}
          >
            <GoogleLogo width="20" height="20" />
            Google
          </Button>
          <Button
            type="button"
            variant="outline"
            className="flex w-full flex-row items-center gap-2"
            disabled={methods.formState.isSubmitting}
            onClick={handleSignInWithMicrosoft}
          >
            <MicrosoftLogo width="20" height="20" />
            Microsoft
          </Button>
        </div>
      </CardContent>
      <CardFooter className="inline-block rounded-b-xl border-t bg-muted pt-6 text-xs text-muted-foreground">
        By signing up, you agree to our{" "}
        <Link href={Routes.TermsOfUse} className="text-foreground underline">
          Terms of Use
        </Link>{" "}
        and{" "}
        <Link href={Routes.PrivacyPolicy} className="text-foreground underline">
          Privacy Policy
        </Link>
        . Need help?{" "}
        <Link href={Routes.Contact} className="text-foreground underline">
          Get in touch
        </Link>
        .
      </CardFooter>
    </Card>
  );
}
