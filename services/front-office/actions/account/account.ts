import { axiosInstance } from "@/lib/axios.config";
import { ChangePasswordRequest, UpdatePersonalDetailsRequest } from "@/types/account";


export const signUp = async(params: ChangePasswordRequest) => {
  await axiosInstance.post('/account/change-password', params);
}

export const updatePersonalDetails = async (params: UpdatePersonalDetailsRequest) => {
 return await axiosInstance.post('/account/update-personal-details',params)   
}