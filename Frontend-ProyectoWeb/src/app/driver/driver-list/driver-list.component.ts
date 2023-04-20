import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Assignment } from 'src/app/model/assignment';
import { Driver } from 'src/app/model/driver';
import { AsociationService } from 'src/app/services/asociation.service';
import { DriversService } from 'src/app/services/drivers.service';


@Component({
  selector: 'app-driver-list',
  templateUrl: './driver-list.component.html',
  styleUrls: ['./driver-list.component.css']
})
export class DriverListComponent implements OnInit{

  public drivers: Driver[];
  public assignments: Assignment[];
  haveAssignment: boolean;

  constructor (private route : ActivatedRoute, private router: Router,private driverService: DriversService, private assignmentService: AsociationService) {
  }

  ngOnInit(): void {
    this.driverService.findAll().subscribe(drivers => this.drivers = drivers);
    this.assignmentService.findAll().subscribe(assignments => this.assignments = assignments)
  }

  deleteDriver(driver: Driver): void {


    if(this.haveAssignations(driver.id!)){
      alert('Este conductor esta presente en alguna asignación, si elimina el conductor se eliminará las asignaciones hechas a este conductor')
    }

    if (confirm(`¿Está seguro que desea eliminar el conductor?`)

    ) {
        this.driverService.deleteDriver(driver.id!).subscribe(() => {
          this.drivers = this.drivers.filter(d => d.id !== driver.id)
        });
      }

    }


  haveAssignations(driverId: number): boolean{
    for (const assignment of this.assignments) {
           if(driverId == assignment.driver?.id){
                return true
           }
    }
    return false
  }

}
