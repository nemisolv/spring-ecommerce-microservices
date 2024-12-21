import { getApiUrl } from '@/lib/urls/get-api-url';

export function getUserImageUrl(userId: string, hash: string): string {
  return `${getApiUrl()}/user-images/${userId}?v=${hash}`;
}
