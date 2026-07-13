export interface EnterpriseRequestDto {
    name: string;
    description: string;
    location: string;
    cnpj: string;
    poBox: string;
    email: string;
    password: string;
}

export interface EnterpriseResponseDto {
    name: string;
    description: string;
    location: string;
    cnpj: string;
    poBox: string;
}