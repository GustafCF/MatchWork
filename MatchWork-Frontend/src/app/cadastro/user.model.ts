export interface UserRequestDto {
    name: string;
    age: number;
    addres: string;
    cpf: string;
    email: string;
    password: string;
}

export interface Curriculum {
    education: string[];
    experience: string[];
    skills: string[];
}

export interface UserResponseDto {
    name: string;
    age: number;
    addres: string;
    cpf: string;
    email: string;
    curriculum: Curriculum;
}