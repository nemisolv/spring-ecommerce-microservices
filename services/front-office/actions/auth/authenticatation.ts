
import { Routes } from "@/constants/routes";
import { axiosInstance } from "@/lib/axios.config";
import { getBaseUrl } from "@/lib/urls/get-base-url";
import { VerifyEmailWithOtpSchema } from "@/schemas/auth/verify-email-with-otp-schema";
import { VerifyEmailWithTokenSchema } from "@/schemas/auth/verify-email-with-token-schema";
import { ApiResponse, ForgotPasswordParams, LoginParams, LoginSuccessResponse, ResendEmailConfirmationParams, ResetPasswordParams, SignUpParams } from "@/types/auth";
import {  saveTokens } from "@/util/authUtil";

export const authenticate = async (params: LoginParams) => {
    const response = await axiosInstance.post('/identity/auth/login', params) as ApiResponse<LoginSuccessResponse>;
    saveTokens(response.data);
    return response;
}

export const signUp = async(params: SignUpParams) => {
 return await axiosInstance.post('/identity/auth/register', params);
    // backend should send email verification
    
}

export const continueWithGoogle = async () => {
    const url = process.env.NEXT_PUBLIC_API_URL +'/identity/oauth2/authorize/google?redirect_uri=' + `${getBaseUrl()}${Routes.OAuth2Redirect}`;
    // call to backend
    await axiosInstance.get(url);
}

export const continueWithFacebook = async () => {
    return null;
}

export const getAuthResponse = async (): Promise<ApiResponse<LoginSuccessResponse>> => {
    return await axiosInstance.get('/identity/internal-auth/auth-response')
}

export const continueWithMicrosoft = async () => {
    const url = process.env.NEXT_PUBLIC_API_URL +'/identity/oauth2/authorize/azure?redirect_uri=' + `${getBaseUrl()}${Routes.OAuth2Redirect}`;
    
    // call to backend
    await axiosInstance.get(url);
}

export const resendEmailConfirmation = async(params: ResendEmailConfirmationParams) => {
  await axiosInstance.post('/identity/auth/resend-email-confirmation', params);
}

export const verifyEmailWithOtp = async(params: VerifyEmailWithOtpSchema) => {
    await axiosInstance.post('/identity/auth/verify-email/with-otp', params);
}

export const verifyEmailWithToken = async(params: VerifyEmailWithTokenSchema) => {
    await axiosInstance.post('/identity/auth/verify-email/with-token', params);
}

export const sendResetPasswordInstructions = async(params: ForgotPasswordParams) =>  {
    await axiosInstance.post('/identity/auth/forgot-password', params);
    // backend will send email with token
}

export const resetPassword = async (params: ResetPasswordParams) => {
    await axiosInstance.post('/identity/auth/reset-password', params);
}



