
export interface RegisterParams {
    email: string;
    password: string;
    name: string;
}

export interface LoginParams {
    email: string;
    password: string;
}

export interface SignUpParams {
    email: string;
    password: string;
    name: string;
}


export interface ResendEmailConfirmationParams {
    email: string;
}

export interface ForgotPasswordParams {
    email: string;
}

export interface ResetPasswordParams {
    password: string;
    token: string;
}

export enum Role {
    ADMIN = 'ADMIN',
    MANAGER = 'MANAGER',
    STAFF = 'STAFF',
    CUSTOMER = 'CUSTOMER',
}

// export interface RoleResponse {
//     id: number;                  // id là một số (Long -> number trong TypeScript)
//     name: Role               // name là chuỗi
//     permissions: Set<string>;    // permissions là một Set chứa các chuỗi
// }




export interface ApiResponse<T> {
    code: number;
    message?: string;
    countRecord?: number;
    data: T;
}



export interface TokenResponse extends ApiResponse<TokenResponse> {
    accessToken: string;
    refreshToken: string;
    accessTokenExpiry: number;
    refreshTokenExpiry: number;
}



export interface LoginSuccessResponse extends ApiResponse<LoginSuccessResponse> {
    userData: FullInfoUser;
    accessToken: string;
    refreshToken: string;
    accessTokenExpiry: number;
    refreshTokenExpiry: number;
}


export interface FullInfoUser {
    id: number;
    username: string;
    email: string;
    name: string;
    emailVerified: boolean;
    imgUrl: string;
    phoneNumber: string;
    role: RoleResponse;
    authProvider: string;
}

export interface RoleResponse {
    id: number;
    name: RoleEnum;
    permissions: Set<string>;
}

export enum RoleEnum {
    ADMIN = 'ADMIN',
    MANAGER = 'MANAGER',
    STAFF = 'STAFF',
    CUSTOMER = 'CUSTOMER',
}