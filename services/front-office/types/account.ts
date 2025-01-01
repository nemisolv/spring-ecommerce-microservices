import { RoleEnum } from "./auth";


export interface ChangePasswordRequest {
    currentPassword: string;
    newPassword: string;
    verifyPassword: string;
}

export interface UpdatePersonalDetailsRequest {
    name: string;
    phoneNumber: string;
    imgUrl: string
}


export interface AddNewEmpoyeeParams {
    name: string;
    email: string;
    phoneNumber: string;
    imgUrl: string | null;
    role: RoleEnum;
    password: string;
    
}
