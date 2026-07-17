import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JobResponseAll } from './jobs/job';
import { Page } from './jobs/page';

@Injectable({
  providedIn: 'root',
})
export class JobService {

  private apiUrl = 'http://localhost:8080/job';

  constructor(private http: HttpClient) {}

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
      return this.http.get<Page<JobResponseAll>>(`${this.apiUrl}/findAll`, {params});
  }
}
