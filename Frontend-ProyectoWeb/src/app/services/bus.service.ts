import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Bus } from '../model/bus';


@Injectable({
  providedIn: 'root'
})
export class BusService {

  private httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };

  constructor(private http: HttpClient) { }



  findAll(): Observable<Bus[]> {
    return this.http.get<Bus[]>(`http://localhost:8080/api/bus/list`)
  }

  findById(id: number): Observable<Bus> {
    return this.http.get<Bus>(`http://localhost:8080/api/bus/${id}`);
  }

  saveBus(bus: Bus): Observable<Bus> {
    return this.http.post<Bus>(`http://localhost:8080/api/bus`, bus, this.httpOptions);
  }

  updateBus(id:Number, bus:Bus): Observable<Bus> {
    return this.http.put<Bus>(`http://localhost:8080/api/bus/update/${id}`, bus, this.httpOptions);
  }

  deleteBus(id: Number){
    return this.http.delete<Bus>(`http://localhost:8080/api/bus/delete/${id}`)
  }


}
