import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BusAssignedViewComponent } from './bus/bus-assigned-view/bus-assigned-view.component';
import { BusListComponent } from './bus/bus-list/bus-list.component';
import { FormBusComponent } from './bus/form-bus/form-bus.component';
import { DriverListComponent } from './driver/driver-list/driver-list.component';
import { FormDriverComponent } from './driver/form-driver/form-driver.component';
import { HomeComponent } from './home/home.component';
import { RouteAssignedViewComponent } from './route/route-assigned-view/route-assigned-view.component';
import { RouteListComponent } from './route/route-list/route-list.component';
import { FormRouteComponent } from './route/form-route/form-route.component';
import { RouteStationsViewComponent } from './route/route-stations-view/route-stations-view.component';
import { RouteSchedulesViewComponent } from './route/route-schedules-view/route-schedules-view.component';
import { RouteScheduleEditComponent } from './route/route-schedule-edit/route-schedule-edit.component';
import { AssignmentComponent } from './assignment/assignment.component';
import { AuthGuard } from './guard/auth.guard';



const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'driver/list',component:DriverListComponent,canActivate: [AuthGuard]},
  {path: 'driver/create',component:FormDriverComponent},
  {path: 'driver/edit/:id', component: FormDriverComponent},
  {path: 'driver/:id/assignedBuses', component: BusAssignedViewComponent},
  {path: 'bus/list',component:BusListComponent},
  {path: 'bus/create',component:FormBusComponent},
  {path: 'bus/edit/:id', component: FormBusComponent},
  {path: 'bus/:id/assignedRoutes', component: RouteAssignedViewComponent},
  {path: 'route/list',component:RouteListComponent,canActivate: [AuthGuard]},
  {path: 'route/create',component:FormRouteComponent},
  {path: 'route/edit/:id', component: FormRouteComponent},
  {path: 'route/:id/stations', component: RouteStationsViewComponent},
  {path: 'route/:id/schedules', component: RouteSchedulesViewComponent},
  {path: 'route/:id/schedules/edit/:scheduleId', component: RouteScheduleEditComponent},
  {path: 'assignment', component: AssignmentComponent},
  {path: '', pathMatch: 'full', redirectTo: '/home'}
]


@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
