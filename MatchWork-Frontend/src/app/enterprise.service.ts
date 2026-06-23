import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EnterpriseRequestDto, EnterpriseResponseDto } from './cadastro/enterprise.model';

@Injectable({
  providedIn: 'root',
})
export class EnterpriseService {

  private apiUrl = 'http://localhost:8080/en';

  constructor(private http: HttpClient) {}

  findAll(): Observable<EnterpriseResponseDto[]> {
    return this.http.get<EnterpriseResponseDto[]>(`${this.apiUrl}/findAll`);
  }

  findById(id: number): Observable<EnterpriseResponseDto> {
    return this.http.get<EnterpriseResponseDto>(`${this.apiUrl}/find/${id}`);
  }

  insert(enterprise: EnterpriseRequestDto): Observable<EnterpriseResponseDto> {
    return this.http.post<EnterpriseResponseDto>(`${this.apiUrl}/insert`, enterprise);
  }
  
}
