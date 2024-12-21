export enum AuthErrorCode {
    USER_PERMISSION_ERROR = 20005,
    USER_PASSWORD_ERROR = 20010,
    USER_AUTH_ERROR = 20009,
    USER_NOT_FOUND = 20002,
    USER_ALREADY_EXISTS = 20003,
    AUTH_TOKEN_INVALID = 20007,
    USER_EMAIL_NOT_VERIFIED = 20011,
}

export const authErrorMessages: Record<AuthErrorCode, string> = {
    [AuthErrorCode.USER_PERMISSION_ERROR]: 'User does not have permission to perform this action',
    [AuthErrorCode.USER_PASSWORD_ERROR]: 'Password is incorrect',
    [AuthErrorCode.USER_AUTH_ERROR]: 'User authentication failed',
    [AuthErrorCode.USER_NOT_FOUND]: 'User not found',
    [AuthErrorCode.USER_ALREADY_EXISTS]: 'User already exists',
    [AuthErrorCode.AUTH_TOKEN_INVALID]: 'Invalid token',
    [AuthErrorCode.USER_EMAIL_NOT_VERIFIED]: 'Email is not verified',
};

export const mapAuthErrorCode = (code: AuthErrorCode): string => {
    return authErrorMessages[code];
}
