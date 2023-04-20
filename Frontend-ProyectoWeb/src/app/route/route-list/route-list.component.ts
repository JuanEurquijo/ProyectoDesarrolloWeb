import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Assignment } from 'src/app/model/assignment';
import { Bus } from 'src/app/model/bus';
import { Route } from 'src/app/model/route';
import { AsociationService } from 'src/app/services/asociation.service';
import { BusService } from 'src/app/services/bus.service';
import { RouteService } from 'src/app/services/route.service';

@Component({
  selector: 'app-route-list',
  templateUrl: './route-list.component.html',
  styleUrls: ['./route-list.component.css']
})
export class RouteListComponent implements OnInit {

  routes: Route[];
  buses: Bus[];
  searchQuery = '';
  filteredRoutes: Route[]=[]


  constructor (private busService : BusService,
    private routeService: RouteService,
    private router: Router,
    private assignmentService: AsociationService) {



  }

  ngOnInit(): void {
    this.routeService.findAll().subscribe(routes => {this.routes = routes 
      this.search()
    });
    this.busService.findAll().subscribe(buses => this.buses = buses);
    this.filteredRoutes = this.routes;
  }

  deleteRoute(route: Route): void {

    this.assignmentService.getAssignedBusesByRoute(route.id!).subscribe(buses => {
      if(buses.length > 0){
        alert('No se puede eliminar la ruta ya que esta asignada a un bus')
      } else{
        if (confirm(`¿Está seguro que desea eliminar la ruta?`)) {
          this.routeService.deleteRoute(route.id!).subscribe(() => {
            this.routes = this.routes.filter(r => r.id !== route.id);
          });
        }
        location.reload();
      }
    });
  }


  search() {
    this.filteredRoutes = this.routes.filter(route =>
      route.code!.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
  }



}
