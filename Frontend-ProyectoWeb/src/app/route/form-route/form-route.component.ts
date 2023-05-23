import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs';
import { Route } from 'src/app/model/route';
import { Schedule } from 'src/app/model/schedule';
import { Station } from 'src/app/model/station';
import { RouteService } from 'src/app/services/route.service';
import { StationService } from 'src/app/services/station.service';

@Component({
  selector: 'app-form-route',
  templateUrl: './form-route.component.html',
  styleUrls: ['./form-route.component.css']
})
export class FormRouteComponent implements OnInit {

  public title = '';
  route: Route;
  stations: Station[];
  schedule: Schedule;
  routes: Route[] = []
  alreadyExist: boolean = true;


  routeForm = this.fb.group({
    code: this.fb.control('', [Validators.required, Validators.pattern(/^[A-Z]{1}-\d{2}$/)]),
    selectedStations: this.fb.control<Station[] | null>(null, [Validators.required]),
  });

  constructor(private fb: FormBuilder,
    private actRoute: ActivatedRoute,
    private router: Router,
    private routeService: RouteService,
    private stationService: StationService) { }

  ngOnInit(): void {

    this.stationService.findAll().subscribe(stations => {
      this.stations = stations

    });

    this.routeService.findAll().subscribe(routes => {
      this.routes = routes
    })

    const currentRoute = this.actRoute.snapshot.routeConfig?.path;
    this.title = currentRoute === 'route/create' ? 'Crear Nueva Ruta' : 'Editar Ruta';

    if (currentRoute === 'route/edit/:id') {
      this.actRoute.paramMap.pipe(switchMap(params =>
        this.routeService.findById(+params.get('id')!)

      )).subscribe(route => {
        if (route) {
          this.route = route

          this.routeForm.patchValue({
            code: route.code!,
            selectedStations: route.stations!,

          })

        }
        else {
          this.router.navigate(['route/create']);
        }
      });
    } else {
      this.route = new Route(undefined, undefined, undefined);
    }

  }

  onSubmit() {
    const code = this.routeForm.value.code;
    const stations = this.routeForm.value.selectedStations;

    const newRoute = new Route(code!, stations!, this.schedule);

    for (const route of this.routes) {
      if (route.id !== this.route.id && newRoute.code === route.code) {
        alert(`Ya existe la ruta con código ${route.code}`)
        this.alreadyExist = true
        break
      } else {
        this.alreadyExist = false
      }
    }


    if (!this.alreadyExist) {
      if (this.route.id) {
        this.routeService.updateRoute(this.route.id!, newRoute).subscribe(() => {
          this.redirect();
        })
      } else {
        this.routeService.saveRoute(newRoute).subscribe(() => {
          this.redirect();
        })
      }
    }

  }

  redirect() {
    this.router.navigate(['/route/list']);
  }

}
