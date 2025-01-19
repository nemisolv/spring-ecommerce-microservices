import { axiosInstance } from "@/lib/axios.config";
import { ApiResponse, RoleResponse } from "@/types/auth";


export const getMyAuthority = async (): Promise<ApiResponse<RoleResponse>> => {
    return await axiosInstance.get('/identity/permissions/authority/me') as ApiResponse<RoleResponse>;
}