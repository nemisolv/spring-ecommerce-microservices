import { axiosInstance } from "@/lib/axios.config";
import { ApiResponse } from "@/types/auth";
import { MyProfileResponse } from "@/types/profile";


export const getProfile = async (): Promise<ApiResponse<MyProfileResponse>> => {

    const response = await axiosInstance.get('/profile/users/my-profile') as ApiResponse<MyProfileResponse>;
    return response;

}