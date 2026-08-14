import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CurriculumRequestDto, CurriculumResponseDto } from './curriculum/curriculum.model';

@Injectable({
  providedIn: 'root',
})
export class CurriculumService {

  private apiUrl = 'http://localhost:8080/cr';

  constructor(private http: HttpClient) {}

  private getAuthHeader(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  private getEmail() {
    const emailLoged = localStorage.getItem('email');
    return emailLoged?.toString;
  }

  insert(curriculum: CurriculumRequestDto): Observable<CurriculumResponseDto> {
    return this.http.post<CurriculumResponseDto>(`${this.apiUrl}/insert/${this.getEmail}`, this.getAuthHeader);
  }

}
