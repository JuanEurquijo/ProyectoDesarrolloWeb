import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Station } from '../model/station';

@Injectable({
  providedIn: 'root'
})
export class StationService {

  constructor(private http: HttpClient) { }



  findAll(): Observable<Station[]> {
    return this.http.get<Station[]>(`http://localhost:8080/api/station/list`)
  }

  findById(id: number): Observable<Station> {
    return this.http.get<Station>(`http://localhost:8080/api/station/${id}`);
  }

}
