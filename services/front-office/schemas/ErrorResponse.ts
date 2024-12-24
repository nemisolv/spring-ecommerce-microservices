export interface ErrorType {
    path: string;
    timestamp: string;
    errors: string[];
    code: number;
}


class ErrorResponse extends Error implements ErrorType {
    path: string;
    timestamp: string;
    errors: string[];
    code: number;
    constructor(message: string, path: string, timestamp: string, errors: string[], code: number) {
        super(message);
        this.name = "ErrorResponse";
        this.path = path;
        this.timestamp = timestamp;
        this.errors = errors;
        this.code = code;
    }
}

export default ErrorResponse;