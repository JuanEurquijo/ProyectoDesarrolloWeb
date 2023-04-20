import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs';
import { Station } from 'src/app/model/station';
import { RouteService } from 'src/app/services/route.service';


@Component({
  selector: 'app-route-stations-view',
  templateUrl: './route-stations-view.component.html',
  styleUrls: ['./route-stations-view.component.css']
})
export class RouteStationsViewComponent implements OnInit {

  stations: Station[];

  constructor(
    private routeService: RouteService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.paramMap.pipe(
      switchMap(params => this.routeService.getRouteStations(+params.get('id')!))
    ).subscribe(stations => {
      this.stations = stations;
    });
  }

  goBack(){
    this.router.navigate([`/route/list`]);
  }
}
