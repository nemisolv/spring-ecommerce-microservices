import type { ObjectValues } from '@/types/object-values';

import packageInfo from '../package.json';

export const AppInfo = {
  APP_NAME: process.env.NEXT_PUBLIC_APP_NAME ?? '',
  APP_DESCRIPTION: 'Tech shop with Next.js and Shadcn',
  PRODUCTION: process.env.NODE_ENV === 'production',
  VERSION: packageInfo.version
} as const;

export type AppInfo = ObjectValues<typeof AppInfo>;
