import { FullInfoUser, TokenResponse } from '@/types/auth';
import Cookies from 'js-cookie';


const ACCESS_TOKEN = 'techshop_access_token';
const REFRESH_TOKEN= 'techshop_refresh_token';

const USER_DATA = 'user';


export const saveTokens = 
(tokenResponse: TokenResponse) => {
        const  { accessToken, refreshToken, accessTokenExpiry, refreshTokenExpiry } = tokenResponse;
  if (accessToken && refreshToken)  {
    Cookies.set(ACCESS_TOKEN, accessToken, {expires: accessTokenExpiry, secure: process.env.NODE_ENV === 'production'}); // secure: true in production
    Cookies.set(REFRESH_TOKEN, refreshToken, {expires: refreshTokenExpiry, secure: process.env.NODE_ENV === 'production'}); // secure: true in production
    
  }else {
    Cookies.remove(ACCESS_TOKEN);
    Cookies.remove(REFRESH_TOKEN);
  }
};

export const saveOnlyToken = (accessToken: string) => {
  console.log("🚀 ~ saveOnlyToken ~ accessToken::", accessToken)
  Cookies.set(ACCESS_TOKEN, accessToken, {expires: 1200, secure: process.env.NODE_ENV === 'production'}); // secure: true in production
}

export const getToken = () => {
  return {
    accessToken: Cookies.get(ACCESS_TOKEN),
    refreshToken: Cookies.get(REFRESH_TOKEN),
  };
};

export const getAuth = () => {
    const userCookie = Cookies.get(USER_DATA);
    try {
      return {
        user: userCookie ? JSON.parse(userCookie) : undefined,
      };
    } catch (error) {
      console.error('Error parsing user cookie:', error);
      return { user: undefined };
    }
  };


export const saveAuth = (userData:FullInfoUser )=> {
  Cookies.set(USER_DATA, JSON.stringify(userData), {secure: process.env.NODE_ENV === 'production'}); // secure: true in production
}

export const logOut = () => {
 
  // clear user cookie, token and refresh token
  Cookies.remove(USER_DATA);
  Cookies.remove(ACCESS_TOKEN);
  Cookies.remove(REFRESH_TOKEN);

};