import { Component } from '@angular/core';
import { ActivatedRoute} from '@angular/router';
import { forkJoin, switchMap } from 'rxjs';
import { Bus } from '../../model/bus';
import { BusService } from '../../services/bus.service';
import { DriversService } from '../../services/drivers.service';
import { Assignment } from '../../model/assignment';
import { Schedule } from '../../model/schedule';
import { AsociationService } from '../../services/asociation.service';
import { Route } from 'src/app/model/route';

@Component({
  selector: 'app-bus-assigned-view',
  templateUrl: './bus-assigned-view.component.html',
  styleUrls: ['./bus-assigned-view.component.css']
})
export class BusAssignedViewComponent {
  driverId: number;
  currentBus: Bus;
  assignedBuses: Bus[]=[];
  scheduleMap: {[Key: number]:Schedule[]} = {}

  constructor(
    private driverService: DriversService,
    private busService: BusService,
    private actRoute: ActivatedRoute,
    private assignmentService: AsociationService
  ) {}

  ngOnInit(): void {

    this.actRoute.params.subscribe(params => {
      this.driverId = params['id'] || null;
    })

    this.show()
  }

  show() {
    this.assignmentService.findBusesByDriver(this.driverId).subscribe(buses=> {
       this.assignedBuses = buses

       this.assignedBuses = this.assignedBuses.reduce((acum:Bus[], bus:Bus)=>{
        const existingRoute = acum.find(b => b.id === bus.id)
        if(!existingRoute){
          acum.push(bus)
        }
        return acum;
      },[])
      const scheduleMap: {[Key: number]:Schedule[]} = {}

      for (const bus of buses) {
        this.assignmentService.findRoutesByBus(bus.id!).subscribe(routes => {
        for (const route of routes) {
         this.assignmentService.findSchedulesByDriver(this.driverId,bus.id!,route.id!).subscribe(schedules =>
           {
              scheduleMap[bus.id!] = schedules
           })
          }
        })
      }
      this.scheduleMap = scheduleMap
     })
  }



}



