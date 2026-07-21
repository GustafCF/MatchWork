export interface JobResponseAll {
    id: number;
    name: string;
    description: string;
    responsibility: string;
    requirements: string;
    additionalInfo: string;
    ProcessSteps: any;
    jobModel: any;
    typeContract: any;
    date: string;
    enterprise: any;
    candidates: any;
}

export interface JobResponse {
    id: number;
    name: string;
    description: string;
    responsibility: string;
    requirements: string;
    additionalInfo: string;
    ProcessSteps: any;
    jobModel: any;
    typeContract: any;
    enterprise: any;
    date: string;
}