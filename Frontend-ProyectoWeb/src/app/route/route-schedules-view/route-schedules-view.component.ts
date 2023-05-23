import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Assignment } from 'src/app/model/assignment';
import { Schedule } from 'src/app/model/schedule';
import { AsociationService } from 'src/app/services/asociation.service';
import { SecurityService } from 'src/app/services/security.service';


@Component({
  selector: 'app-route-schedules-view',
  templateUrl: './route-schedules-view.component.html',
  styleUrls: ['./route-schedules-view.component.css']
})
export class RouteSchedulesViewComponent implements OnInit {
  schedules: Schedule[]=[];
  schedule: Schedule;
  routeId: number;
  assignments: Assignment[] = [];
  isLoggedIn$: Promise<boolean>;

  constructor(
    private securityService: SecurityService,
    private route: ActivatedRoute,
    private router: Router,
    private assignmentService: AsociationService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.routeId = params['id'] || null;
    })
    this.isLoggedIn$ = this.securityService.isLogged();
    this.show()
  }

  show(){
    this.assignmentService.findSchedulesByRoute(this.routeId).subscribe(schedules => this.schedules = schedules)
  }



  goBack() {
    window.history.back();
    this.router.navigateByUrl(this.router.url);
  }
}
