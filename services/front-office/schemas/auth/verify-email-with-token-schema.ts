import { z } from 'zod';

export const verifyEmailWithTokenSchema = z.object({
  token: z
    .string({
      required_error: 'Token is required.',
      invalid_type_error: 'Token must be a string.'
    })
    .trim()
    .min(1, 'Token is required.')
    .max(64, 'Maximum 64 characters allowed.')
});

export type VerifyEmailWithTokenSchema = z.infer<
  typeof verifyEmailWithTokenSchema
>;
