export interface LoginRequest {
    email: string;
    password: string;
}

export interface LoginResponse {
    email: string,
    accessToken: string;
    expiresIn: string;
}