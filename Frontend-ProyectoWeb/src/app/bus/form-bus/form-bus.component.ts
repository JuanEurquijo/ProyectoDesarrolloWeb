import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs';
import { Assignment } from 'src/app/model/assignment';
import { Bus } from 'src/app/model/bus';
import { AsociationService } from 'src/app/services/asociation.service';
import { BusService } from 'src/app/services/bus.service';

@Component({
  selector: 'app-form-bus',
  templateUrl: './form-bus.component.html',
  styleUrls: ['./form-bus.component.css']
})
export class FormBusComponent implements OnInit {

  public title = '';
  bus: Bus;
  alreadyExist: boolean = true
  buses: Bus[]

  busForm = this.fb.group(
    {
      plate: this.fb.control('', [Validators.required, Validators.pattern(/^[A-Z]{3}-\d{3}$/)]),
      model: this.fb.control('', [Validators.required]),
    }
  );

  constructor(private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private busService: BusService) { }

  ngOnInit(): void {

    this.busService.findAll().subscribe(buses => this.buses = buses)
    const currentRoute = this.route.snapshot.routeConfig?.path;
    this.title = currentRoute === 'bus/create' ? 'Crear Nuevo Bus' : 'Editar Bus';

    if (currentRoute === 'bus/edit/:id') {
      this.route.paramMap.pipe(switchMap(params =>
        this.busService.findById(+params.get('id')!)

      )).subscribe(bus => {
        if (bus) {
          this.bus = bus
          this.busForm.setValue({
            plate: bus.plate!,
            model: bus.model!
          });

        } else {
          this.router.navigate(['bus/create']);
        }
      });
    } else {
      this.bus = new Bus('', '')
    }


  }

  onSubmit() {
    const plate = this.busForm.value.plate;
    const model = this.busForm.value.model;

    const newBus = new Bus(plate!, model!);

    for (const bus of this.buses) {
      if (bus.id !== this.bus.id && newBus.plate === bus.plate) {
        alert(`Ya existe el bus con placa ${bus.plate}`)
        this.alreadyExist = true
        break
      } else {
        this.alreadyExist = false
      }
    }

    if (!this.alreadyExist) {
      if (this.bus.id) {
        this.busService.updateBus(this.bus.id, newBus).subscribe();
      } else {
        this.busService.saveBus(newBus).subscribe();
      }
      this.busService.findAll().subscribe()
      this.redirect();
    }

  }

  redirect() {
    this.router.navigate(['/bus/list']);
  }
}
