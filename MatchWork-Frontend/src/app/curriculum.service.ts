import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CurriculumRequestDto, CurriculumResponseDto } from './curriculum/curriculum.model';
import { LocalStorageService } from './local-storage.service';
import { Curriculum } from './cadastro/user.model';

@Injectable({ providedIn: 'root' })
export class CurriculumService {
  private apiUrl = 'http://localhost:8080/cr';

  constructor(
    private http: HttpClient,
    private storage: LocalStorageService
  ) {}

  private getAuthHeader(): HttpHeaders {
    const token = this.storage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token ?? ''}`
    });
  }

  private getEmail(): string | null {
    return this.storage.getItem('email');  
  }

  insert(curriculum: CurriculumRequestDto): Observable<Curriculum> {
    return this.http.post<Curriculum>(
      `${this.apiUrl}/insert/${this.getEmail()}`,     
      curriculum,                                      
      { headers: this.getAuthHeader() }                
    );
  }
}