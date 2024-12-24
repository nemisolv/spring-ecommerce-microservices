import { Routes } from "@/constants/routes";
import { getBaseUrl } from "../urls/get-base-url";

export const buildOAuth2Url = (provider: string, redirectUri: string = `${getBaseUrl()}${Routes.OAuth2Redirect}`  ) => {
 return process.env.NEXT_PUBLIC_API_URL +`/oauth2/authorize/${provider}?redirect_uri=${redirectUri}`;
}