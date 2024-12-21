import { z } from 'zod';

import { passwordValidator } from '@/lib/auth/password';

export const signUpSchema = z.object({
  name: z
    .string({
      required_error: 'Name is required.',
      invalid_type_error: 'Name must be a string.'
    })
    .trim()
    .min(1, 'Name is required.')
    .max(64, 'Maximum 64 characters allowed.'),
  email: z
    .string({
      required_error: 'Email is required.',
      invalid_type_error: 'Email must be a string.'
    })
    .trim()
    .min(1, 'Email is required.')
    .max(255, 'Maximum 255 characters allowed.')
    .email('Enter a valid email address.'),
  password: z
    .string({
      required_error: 'Password is required.',
      invalid_type_error: 'Password must be a string.'
    })
    .min(1, 'Password is required.')
    .max(72, 'Maximum 72 characters allowed.')
    .refine((arg) => passwordValidator.validate(arg).success, {
      message: 'Password does not meet requirements.'
    })
});

export type SignUpSchema = z.infer<typeof signUpSchema>;
