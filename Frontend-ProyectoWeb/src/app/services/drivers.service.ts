import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Driver } from '../model/driver';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Bus } from '../model/bus';


@Injectable({
  providedIn: 'root'
})
export class DriversService {

  private httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };

  constructor(private http: HttpClient) { }



  findAll(): Observable<Driver[]> {
    return this.http.get<Driver[]>(`http://localhost:8080/api/driver/list`)
  }

  findById(id: number): Observable<Driver> {
    return this.http.get<Driver>(`http://localhost:8080/api/driver/${id}`);
  }

  saveDriver(driver: Driver): Observable<Driver> {
    return this.http.post<Driver>(`http://localhost:8080/api/driver`, driver, this.httpOptions);
  }

  updateDriver(id:Number,driver: Driver): Observable<Driver> {
    return this.http.put<Driver>(`http://localhost:8080/api/driver/update/${id}`, driver, this.httpOptions);
  }

  deleteDriver(id: Number){
    return this.http.delete<Driver>(`http://localhost:8080/api/driver/delete/${id}`)
  }

  assignBus(idDriver: Number , bus: Bus){
    return this.http.post<Driver>(`http://localhost:8080/api/driver/${idDriver}/assign-bus`, bus, this.httpOptions)
  }

  assignedBuses(id: Number): Observable<Bus[]>{
    return this.http.get<Bus[]>(`http://localhost:8080/api/driver/${id}/assigned-buses`)
  }


}
