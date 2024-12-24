import { z } from "zod";

import { passwordValidator } from "@/lib/auth/password";

export const resetPasswordSchema = z.object({
  token: z
    .string({
      required_error: "Token is required.",
      invalid_type_error: "Token must be a string.",
    })
    .trim()
    .min(1, "Request id is required."),
  password: z
    .string({
      required_error: "Password is required.",
      invalid_type_error: "Password must be a string.",
    })
    .min(1, "Password is required.")
    .max(72, "Maximum 72 characters allowed.")
    .refine((arg) => passwordValidator.validate(arg).success, {
      message: "Password does not meet requirements.",
    }),
});

export type ResetPasswordSchema = z.infer<typeof resetPasswordSchema>;
