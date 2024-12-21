import { getApiUrl } from '@/lib/urls/get-api-url';

export function getContactImageUrl(contactId: string, hash: string): string {
  return `${getApiUrl()}/contact-images/${contactId}?v=${hash}`;
}
