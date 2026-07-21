import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JobResponse, JobResponseAll } from './jobs/job';
import { Page } from './jobs/page';

@Injectable({
  providedIn: 'root',
})
export class JobService {

  private apiUrl = 'http://localhost:8080/job';

  constructor(private http: HttpClient) {}

  private getAuthHeader(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}` 
    });
  }

  findAll(
    page: number = 0,
    size: number = 10,
    sortBy: string = 'id',
    direction: string = 'asc'
  ): Observable<Page<JobResponseAll>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', sortBy)
      .set('direction', direction);
      return this.http.get<Page<JobResponseAll>>(`${this.apiUrl}/findAll`, { params });
  }

  findByid(id: number): Observable<JobResponse> {
    return this.http.get<JobResponse>(`${this.apiUrl}/find/${id}`, {headers: this.getAuthHeader()});
  }

  searchByName(name: string, page: number = 0, size: number = 10): Observable<Page<JobResponseAll>> {
    const params = new HttpParams()
      .set('name', name)
      .set('page', page.toString())
      .set('size', size.toString());
      return this.http.get<Page<JobResponseAll>>(`${this.apiUrl}/search`, { params });
  }


}
