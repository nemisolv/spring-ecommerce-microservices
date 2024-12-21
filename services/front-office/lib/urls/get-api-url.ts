import { getBaseUrl } from '@/lib/urls/get-base-url';

export function getApiUrl(): string {
  return `${getBaseUrl()}/api`;
}
