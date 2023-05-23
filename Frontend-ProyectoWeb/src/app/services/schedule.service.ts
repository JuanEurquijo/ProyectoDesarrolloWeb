import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Schedule } from '../model/schedule';

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  private httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };

  constructor(private http: HttpClient) { }



  findAll(): Observable<Schedule[]> {
    return this.http.get<Schedule[]>(`http://localhost:8080/api/schedule/list`)
  }

  findById(id: number): Observable<Schedule> {
    return this.http.get<Schedule>(`http://localhost:8080/api/schedule/${id}`);
  }

  saveSchedule(schedule: Schedule): Observable<Schedule> {
    return this.http.post<Schedule>(`http://localhost:8080/api/schedule`, schedule, this.httpOptions);
  }

  updateSchedule(id:Number, schedule:Schedule): Observable<Schedule> {
    return this.http.put<Schedule>(`http://localhost:8080/api/schedule/update/${id}`, schedule, this.httpOptions);
  }

  deleteSchedule(id: Number){
    return this.http.delete<Schedule>(`http://localhost:8080/api/schedule/delete/${id}`)
  }

}
