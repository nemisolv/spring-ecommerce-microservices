

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