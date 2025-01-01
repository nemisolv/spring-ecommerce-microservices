import { axiosInstance } from "@/lib/axios.config";
import { AddNewEmpoyeeParams, ChangePasswordRequest, UpdatePersonalDetailsRequest } from "@/types/account";
import { GetPageParams } from "@/types/common";


export const changePassword = async(params: ChangePasswordRequest) => {
  await axiosInstance.post('/account/change-password', params);
}

export const updatePersonalDetails = async (params: UpdatePersonalDetailsRequest) => {
 return await axiosInstance.post('/account/update-personal-details',params)   
}


export const addNewEmployee = async (params: AddNewEmpoyeeParams) =>  {
  return await axiosInstance.post('/account/add-new-employee', params)
}

export const getEmployees = async (params: GetPageParams) => {
  return await axiosInstance.get('/account/get-employees', {params})
}