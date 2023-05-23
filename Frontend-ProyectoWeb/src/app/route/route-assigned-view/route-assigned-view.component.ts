import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { forkJoin, switchMap } from 'rxjs';
import { Bus } from '../../model/bus';
import { Driver } from '../../model/driver';
import { Route } from '../../model/route';
import { Station } from '../../model/station';
import { BusService } from '../../services/bus.service';
import { RouteService } from '../../services/route.service';
import { Assignment } from '../../model/assignment';
import { AsociationService } from '../../services/asociation.service';
import { Schedule } from '../../model/schedule';

@Component({
  selector: 'app-route-assigned-view',
  templateUrl: './route-assigned-view.component.html',
  styleUrls: ['./route-assigned-view.component.css']
})
export class RouteAssignedViewComponent {
  busId: number;
  assignedRoutes: Route[] = [];
  currentRoute: Route;
  schedules: Schedule[] = [];
  scheduleMap: { [Key: number]: Schedule[] } = {}
  rutes: Route[]=[]


  constructor(
    private busService: BusService,
    private routeService: RouteService,
    private route: ActivatedRoute,
    private assignmentService: AsociationService
  ) { }

  ngOnInit(): void {


    this.route.params.subscribe(params => {
      this.busId = params['id'] || null;
    })

    this.show()

  }

  show() {
    this.assignmentService.findRoutesByBus(this.busId).subscribe(routes => {
      this.assignedRoutes = routes
      this.assignedRoutes = this.assignedRoutes.reduce((acum:Route[], route:Route)=>{
        const existingRoute = acum.find(r => r.id === route.id)
        if(!existingRoute){
          acum.push(route)
        }
        return acum;
      },[])
      const scheduleMap: { [Key: number]: Schedule[] } = {}
      for (const route of this.assignedRoutes) {
        this.assignmentService.findSchedulesByBusAndRoute(this.busId, route.id!).subscribe(schedules => {
          for (const schedule of schedules) {
            if (!scheduleMap[route.id!]) {
              scheduleMap[route.id!] = [schedule]
            } else if (!scheduleMap[route.id!].find(s => s.id === schedule.id)) {
              scheduleMap[route.id!].push(schedule)
            }
          }
        })
      }
      this.scheduleMap = scheduleMap
    })
  }

}
