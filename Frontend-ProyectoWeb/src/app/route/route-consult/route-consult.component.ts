import { Component, OnInit } from '@angular/core';
import { Bus } from 'src/app/model/bus';
import { Route } from 'src/app/model/route';
import { RouteService } from 'src/app/services/route.service';


@Component({
  selector: 'app-route-consult',
  templateUrl: './route-consult.component.html',
  styleUrls: ['./route-consult.component.css']
})
export class RouteConsultComponent implements OnInit {
  routes: Route[];
  searchQuery = '';
  filteredRoutes: Route[]=[];


  constructor (private routeService: RouteService) {

  }

  ngOnInit(): void {
    this.routeService.findAll().subscribe(routes => {this.routes = routes
      this.search()
    });
    this.filteredRoutes = this.routes;

  }



  search() {
    this.filteredRoutes = this.routes.filter(route =>
      route.code!.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
  }





}
