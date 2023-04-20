import { Component } from '@angular/core';
import { Bus } from 'src/app/model/bus';
import { Driver } from 'src/app/model/driver';
import { DriversService } from '../../services/drivers.service';
import { BusService } from '../../services/bus.service';
import { Router } from '@angular/router';
import { Assignment } from 'src/app/model/assignment';
import { AsociationService } from 'src/app/services/asociation.service';

@Component({
  selector: 'app-bus-list',
  templateUrl: './bus-list.component.html',
  styleUrls: ['./bus-list.component.css']
})
export class BusListComponent {

  buses: Bus[];
  drivers: Driver[];

  constructor (
    private busService: BusService,
    private driverService : DriversService,
    private router: Router,
    private assignmentService: AsociationService) {

  }

  ngOnInit(): void {
    this.busService.findAll().subscribe(buses => this.buses = buses);
    this.driverService.findAll().subscribe(drivers =>this.drivers = drivers);

  }

  deleteBus(bus: Bus): void {
    
    this.assignmentService.getAssignedDriversByBus(bus.id!).subscribe(drivers => {
      if(drivers.length > 0){
        alert('No se puede eliminar el bus ya que esta asignado a un conductor')
      } else{
        if (confirm(`¿Está seguro que desea eliminar el bus?`)) {
          this.busService.deleteBus(bus.id!).subscribe(() => {
            this.buses = this.buses.filter(b => b.id !== bus.id);
          });
      }
        location.reload();
      }
    });
  }

}
