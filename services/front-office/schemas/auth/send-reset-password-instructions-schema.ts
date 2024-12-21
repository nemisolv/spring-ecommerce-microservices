import { z } from 'zod';

export const sendResetPasswordInstructionsSchema = z.object({
  email: z
    .string({
      required_error: 'Email is required.',
      invalid_type_error: 'Email must be a string.'
    })
    .trim()
    .min(1, 'Email is required.')
    .max(255, 'Maximum 255 characters allowed.')
    .email('Enter a valid email address.')
});

export type SendResetPasswordInstructionsSchema = z.infer<
  typeof sendResetPasswordInstructionsSchema
>;
