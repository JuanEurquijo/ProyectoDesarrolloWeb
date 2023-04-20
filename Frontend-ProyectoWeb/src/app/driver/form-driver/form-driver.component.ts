import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DriversService } from '../../services/drivers.service';
import { switchMap } from 'rxjs/operators';
import { Driver } from 'src/app/model/driver';


@Component({
  selector: 'app-form-driver',
  templateUrl: './form-driver.component.html',
  styleUrls: ['./form-driver.component.css']
})
export class FormDriverComponent implements OnInit {

  public title = '';
  driver: Driver;
  alreadyExist: boolean = true
  drivers: Driver[]

  driverForm = this.fb.group(
    {
      name: this.fb.control('', [Validators.required]),
      lastName: this.fb.control('', [Validators.required]),
      identifier: this.fb.control<Number | null>(null, [Validators.required, Validators.pattern(/^\d+$/), Validators.minLength(8)]),
      phone: this.fb.control<Number | null>(null, [Validators.required, Validators.pattern(/^\d+$/), Validators.minLength(10)]),
      address: this.fb.control('', [Validators.required]),
    }
  );

  constructor(private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private driverService: DriversService) { }

  ngOnInit(): void {

    this.driverService.findAll().subscribe(drivers => this.drivers = drivers)

    const currentRoute = this.route.snapshot.routeConfig?.path;
    this.title = currentRoute === 'driver/create' ? 'Crear Nuevo Conductor' : 'Editar Conductor';

    if (currentRoute === 'driver/edit/:id') {
      this.route.paramMap.pipe(switchMap(params =>
        this.driverService.findById(+params.get('id')!)
      )).subscribe(driver => {
        if (driver) {
          this.driver = driver
          this.driverForm.setValue({
            name: driver.name!,
            lastName: driver.lastName!,
            identifier: driver.identifier!,
            phone: driver.phone!,
            address: driver.address!
          });
        } else {
          this.router.navigate(['driver/create']);
        }
      });
    } else {
      this.driver = new Driver('', '', undefined, undefined, '')
    }

  }

  onSubmit() {
    const name = this.driverForm.value.name;
    const lastName = this.driverForm.value.lastName;
    const identifier = this.driverForm.value.identifier;
    const phone = this.driverForm.value.phone;
    const address = this.driverForm.value.address;

    const newDriver = new Driver(name!, lastName!, identifier!, phone!, address!);

    for (const driver of this.drivers) {
      if (driver.id !== this.driver.id && newDriver.identifier === driver.identifier) {
        alert(`Ya existe el conductor con cédula ${driver.identifier}`)
        this.alreadyExist = true
        break
      } else {
        this.alreadyExist = false
      }
    }


    if (!this.alreadyExist) {
      if (this.driver.id) {
        this.driverService.updateDriver(this.driver.id, newDriver).subscribe();
      } else {
        this.driverService.saveDriver(newDriver).subscribe();
      }
      this.driverService.findAll().subscribe()
      this.redirect();     
    }

  }


  redirect() {
    this.router.navigate(['/driver/list']);
  }


}
