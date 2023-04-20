import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Driver } from '../model/driver';
import { Route } from '../model/route';
import { Bus } from '../model/bus';
import { Schedule } from '../model/schedule';
import { DriversService } from '../services/drivers.service';
import { BusService } from '../services/bus.service';
import { RouteService } from '../services/route.service';
import { ScheduleService } from '../services/schedule.service';
import { Assignment } from '../model/assignment';
import { AsociationService } from '../services/asociation.service';

@Component({
  selector: 'app-assignment',
  templateUrl: './assignment.component.html',
  styleUrls: ['./assignment.component.css']
})

export class AssignmentComponent implements OnInit {

  drivers: Driver[];
  buses: Bus[];
  routes: Route[];
  schedule: Schedule;
  newAssignment: Assignment;
  assignments: Assignment[];
  scheduleDays = ['Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo']
  existSchedule:boolean = true

  constructor(private fb: FormBuilder,
    private router: Router,
    private driverService: DriversService,
    private busService: BusService,
    private routeService: RouteService,
    private scheduleService: ScheduleService,
    private assignmentService: AsociationService) { }

  assignmentForm = this.fb.group(
    {
      selectedDriver: this.fb.control<Driver | null>(null, [Validators.required]),
      selectedBus: this.fb.control<Bus | null>(null, [Validators.required]),
      selectedRoute: this.fb.control<Route | null>(null, [Validators.required]),
      selectedDay: this.fb.control<string | null>('', [Validators.required]),
      startTime: this.fb.control<Date | null>(null ,[Validators.required]),
      endTime: this.fb.control<Date | null>(null ,[Validators.required])
    }
  );


  ngOnInit(): void {
    this.driverService.findAll().subscribe(drivers => this.drivers = drivers);
    this.busService.findAll().subscribe(buses => this.buses = buses);
    this.routeService.findAll().subscribe(routes => this.routes = routes);
    this.assignmentService.findAll().subscribe(assignments=> this.assignments = assignments);
  }

  onSubmit() {
    const driver = this.assignmentForm.value.selectedDriver;
    const bus = this.assignmentForm.value.selectedBus;
    const route = this.assignmentForm.value.selectedRoute;
    const selectedDay = this.assignmentForm.value.selectedDay;
    const startTime = this.assignmentForm.value.startTime;
    const endTime = this.assignmentForm.value.endTime;

   for (const assignment of this.assignments) {
    if(assignment.schedule?.assignedDay == selectedDay && assignment.route?.code == route?.code){
          this.existSchedule = true
          alert(`Ya existe la ruta ${route?.code} en el horario del día ${selectedDay}`)
          break
     }
     else {
          this.existSchedule = false
     }
   }
   if(this.assignments.length == 0){
      this.existSchedule = false
   }
  if(!this.existSchedule){
   this.scheduleService.saveSchedule(new Schedule(selectedDay!,startTime!,endTime!)).subscribe(schedule =>
    {
      this.newAssignment = new Assignment(driver!,bus!,route!,schedule);
      console.log(this.assignments)
        this.assignmentService.save(this.newAssignment).subscribe();
        this.router.navigate(['/assignment']);
        this.assignmentService.findAll().subscribe();
        location.reload();

    });
  }

  }



}
