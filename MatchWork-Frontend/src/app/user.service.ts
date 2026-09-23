import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserRequestDto, UserResponseDto } from './cadastro/user.model';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiUrl = 'http://localhost:8080/us'

  constructor(
    private http: HttpClient,
    private storage: LocalStorageService
  ) {}

  private getAuthHeader(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${this.storage.getItem('token') ?? ''}`
    });
  }

  private getEmail(): string | null {
    return this.storage.getItem('email');
  }

  findAll(): Observable<UserResponseDto[]> {
    return this.http.get<UserResponseDto[]>(`${this.apiUrl}/findAll`, {headers: this.getAuthHeader()});
  }

  findById(id: number): Observable<UserResponseDto> {
    return this.http.get<UserResponseDto>(`${this.apiUrl}/find/${id}`, {headers: this.getAuthHeader()});
  }

  findByEmail(): Observable<UserResponseDto> {
    return this.http.get<UserResponseDto>(`${this.apiUrl}/findEmail/${this.getEmail()}`, {headers: this.getAuthHeader()});
  }

  insert(user: UserRequestDto): Observable<UserResponseDto> {
    return this.http.post<UserResponseDto>(`${this.apiUrl}/insert`, user);
  }

  update(id: number, user: UserRequestDto): Observable<UserResponseDto> {
    return this.http.put<UserResponseDto>(`${this.apiUrl}/update/${id}`, user, {headers: this.getAuthHeader()});
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, {headers: this.getAuthHeader()});
  }
}