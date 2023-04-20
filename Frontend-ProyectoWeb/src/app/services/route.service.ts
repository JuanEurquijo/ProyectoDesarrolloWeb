import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Route } from '../model/route';
import { Schedule } from '../model/schedule';
import { Station } from '../model/station';

@Injectable({
  providedIn: 'root'
})
export class RouteService {

  private httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };

  constructor(private http: HttpClient) { }



  findAll(): Observable<Route[]> {
    return this.http.get<Route[]>(`http://localhost:8080/api/route/list`)
  }

  findById(id: number): Observable<Route> {
    return this.http.get<Route>(`http://localhost:8080/api/route/${id}`);
  }

  saveRoute(route: Route): Observable<Route> {
    return this.http.post<Route>(`http://localhost:8080/api/route`, route, this.httpOptions);
  }

  updateRoute(id:Number, route:Route): Observable<Route> {
    return this.http.put<Route>(`http://localhost:8080/api/route/update/${id}`, route, this.httpOptions);
  }

  deleteRoute(id: Number){
    return this.http.delete<Route>(`http://localhost:8080/api/route/delete/${id}`)
  }

  getRouteStations(id: Number): Observable<Station[]> {
    return this.http.get<Station[]>(`http://localhost:8080/api/route/${id}/stations`)
  }

  getRouteSchedules(id: Number): Observable<Schedule[]> {
    return this.http.get<Schedule[]>(`http://localhost:8080/api/route/${id}/schedules`)
  }
}
