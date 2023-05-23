import { Injectable } from '@angular/core';
import { Assignment } from '../model/assignment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Schedule } from '../model/schedule';
import { Route } from '../model/route';
import { Bus } from '../model/bus';
import { Driver } from '../model/driver';

@Injectable({
  providedIn: 'root'
})
export class AsociationService {

  private httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };

  constructor(private http: HttpClient) { }

  findAll(): Observable<Assignment[]> {
    return this.http.get<Assignment[]>(`http://localhost:8080/api/assignment/list`)
  }

  update(id:number, asociation: Assignment): Observable<Assignment> {
    return this.http.put<Assignment>(`http://localhost:8080/api/assignment/update/${id}`, asociation, this.httpOptions);
  }

  save(asociation: Assignment): Observable<Assignment>{
   return this.http.post<Assignment>(`http://localhost:8080/api/assignment`, asociation, this.httpOptions);
  }

  findSchedulesByRoute(idRoute:number): Observable<Schedule[]> {
    return this.http.get<Schedule[]>(`http://localhost:8080/api/assignment/${idRoute}/schedules`)
  }

  findRoutesByBus(idBus:number): Observable<Route[]> {
    return this.http.get<Route[]>(`http://localhost:8080/api/assignment/${idBus}/routes`)
  }

  findBusesByDriver(idDriver:number): Observable<Bus[]> {
    return this.http.get<Bus[]>(`http://localhost:8080/api/assignment/${idDriver}/buses`)
  }

  getAssignedBusesByRoute(idRoute:number): Observable<Bus[]> {
    return this.http.get<Bus[]>(`http://localhost:8080/api/assignment/${idRoute}/assignedBuses`)
  }

  getAssignedDriversByBus(idBus:number): Observable<Driver[]> {
    return this.http.get<Driver[]>(`http://localhost:8080/api/assignment/${idBus}/assignedDrivers`)
  }

  findSchedulesByBusAndRoute(idBus: number,idRoute:number): Observable<Schedule[]> {
    return this.http.get<Schedule[]>(`http://localhost:8080/api/assignment/bus/${idBus}/route/${idRoute}`)
  }

  findSchedulesByDriver(idDriver:number,idBus:number,idRoute:number): Observable<Schedule[]> {
    return this.http.get<Schedule[]>(`http://localhost:8080/api/assignment/driver/${idDriver}/bus/${idBus}/route/${idRoute}`)
  }
}
