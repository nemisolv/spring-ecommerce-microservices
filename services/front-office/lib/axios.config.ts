
import { Routes } from '@/constants/routes';
import { TokenResponse } from '@/types/auth';
import { getToken, logOut, saveTokens } from '@/util/authUtil';
import axios from 'axios';


const publicApiEndpoint = [
    "/identity"+Routes.Login,
    "/identity"+Routes.SignUp
]



export const axiosInstance = axios.create({

  baseURL: `${process.env.NEXT_PUBLIC_API_URL}${process.env.NEXT_PUBLIC_BACKEND_API_VERSION}`,
    
    headers: {
        'Content-Type': 'application/json',
    },
});

axiosInstance.interceptors.request.use(
    async (config) => {
        const { accessToken } = getToken();
        if (accessToken) {
            config.headers.Authorization = `Bearer ${accessToken}`;
        }

        return config;
    },
    (error) => {
        return Promise.reject(error);
      },
)

axiosInstance.interceptors.response.use(
    res => res.data,
    async (error) => {
        const originalRequest = error.config;
        console.log("🚀 ~ originalRequest::", originalRequest)
        if(publicApiEndpoint.includes(originalRequest.url)) {
            return Promise.reject(error.response);
        }

        if (error.response && error.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;
            const { refreshToken } = getToken();
            console.log('Refreshing token');
            if (refreshToken) {
             const data =  await  axiosInstance.post('/auth/refresh', { refreshToken }) as TokenResponse
             if(!data) {
                logOut();
             }
            saveTokens(data)
            // Retry original request
            return axiosInstance(originalRequest);

            } else {
                // Redirect to login page
                console.error('No refresh token found');
                logOut();
            }
        }
        return Promise.reject(error.response);
    }
)

