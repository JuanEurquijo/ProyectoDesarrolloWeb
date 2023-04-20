import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { DriversService } from './services/drivers.service';

import { AppComponent } from './app.component';
import {InputTextModule} from 'primeng/inputtext';
import {ButtonModule} from 'primeng/button';
import {CardModule} from 'primeng/card';
import {DropdownModule} from 'primeng/dropdown';
import {ListboxModule} from 'primeng/listbox';
import { MultiSelectModule } from 'primeng/multiselect';
import { FormsModule } from '@angular/forms';
import {CalendarModule} from 'primeng/calendar';
import { TableModule } from 'primeng/table';


import { HeaderComponent } from './header/header.component';
import { DriverListComponent } from './driver/driver-list/driver-list.component';
import { FooterComponent } from './footer/footer.component';
import { AppRoutingModule } from './app-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FormDriverComponent } from './driver/form-driver/form-driver.component';
import { HomeComponent } from './home/home.component';
import { BusListComponent } from './bus/bus-list/bus-list.component';
import { FormBusComponent } from './bus/form-bus/form-bus.component';
import { BusAssignedViewComponent } from './bus/bus-assigned-view/bus-assigned-view.component';
import { RouteAssignedViewComponent } from './route/route-assigned-view/route-assigned-view.component';
import { RouteListComponent } from './route/route-list/route-list.component';
import { FormRouteComponent } from './route/form-route/form-route.component';
import { AssignmentComponent } from './assignment/assignment.component';

import { HttpClientModule } from '@angular/common/http';
import { RouteStationsViewComponent } from './route/route-stations-view/route-stations-view.component';
import { RouteSchedulesViewComponent } from './route/route-schedules-view/route-schedules-view.component';
import { RouteScheduleEditComponent } from './route/route-schedule-edit/route-schedule-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    DriverListComponent,
    FooterComponent,
    FormDriverComponent,
    HomeComponent,
    BusListComponent,
    FormBusComponent,
    BusAssignedViewComponent,
    RouteAssignedViewComponent,
    RouteListComponent,
    FormRouteComponent,
    RouteStationsViewComponent,
    RouteSchedulesViewComponent,
    RouteScheduleEditComponent,
    AssignmentComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ReactiveFormsModule,
    InputTextModule,
    ButtonModule,
    CardModule,
    DropdownModule,
    FormsModule,
    MultiSelectModule,
    ListboxModule,
    CalendarModule,
    TableModule,
    HttpClientModule
  ],
  providers: [DriversService],
  bootstrap: [AppComponent]
})
export class AppModule { }
