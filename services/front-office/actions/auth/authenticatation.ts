
import { axiosInstance } from "@/lib/axios.config";
import { getBaseUrl } from "@/lib/urls/get-base-url";
import { VerifyEmailWithOtpSchema } from "@/schemas/auth/verify-email-with-otp-schema";
import { VerifyEmailWithTokenSchema } from "@/schemas/auth/verify-email-with-token-schema";
import { LoginParams, ResendEmailConfirmationParams, SignUpParams } from "@/types/auth";

export const authenticate = async (params: LoginParams) => {
    const response = await axiosInstance.post('/auth/login', params);
    console.log(response);
    return response;
}

export const signUp = async(params: SignUpParams) => {
 return await axiosInstance.post('/auth/register', params);
    // backend should send email verification
    
}

export const continueWithGoogle = async () => {
    const url = process.env.NEXT_PUBLIC_API_URL +'/oauth2/authorize/google?redirect_uri=' + `${getBaseUrl()}`
    
    console.log("🚀 ~ continueWithGoogle ~ url::::", url)
    // call to backend
    await axiosInstance.get(url);
}

export const continueWithFacebook = async () => {
    return null;
}

export const continueWithMicrosoft = async () => {
    return null;
}

export const resendEmailConfirmation = async(params: ResendEmailConfirmationParams) => {
  await axiosInstance.post('/auth/resend-email-confirmation', params);
}

export const verifyEmailWithOtp = async(params: VerifyEmailWithOtpSchema) => {
    await axiosInstance.post('/auth/verify-email/with-otp', params);
}

export const verifyEmailWithToken = async(params: VerifyEmailWithTokenSchema) => {
    await axiosInstance.post('/auth/verify-email/with-token', params);
}



