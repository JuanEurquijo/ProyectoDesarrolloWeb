import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs';
import { Schedule } from '../../model/schedule';
import { ScheduleService } from '../../services/schedule.service';

@Component({
  selector: 'app-route-schedule-edit',
  templateUrl: './route-schedule-edit.component.html',
  styleUrls: ['./route-schedule-edit.component.css']
})
export class RouteScheduleEditComponent {

  schedule: Schedule;
  routeId: Number;


  editScheduleForm = this.formBuilder.group({
    assignedDay: this.formBuilder.control<string | null>('', [Validators.required]),
    startTime: this.formBuilder.control<Date | null>(null, [Validators.required]),
    endTime: this.formBuilder.control<Date | null>(null, [Validators.required])
  });

  constructor(
    private formBuilder: FormBuilder,
    private actRoute: ActivatedRoute,
    private router: Router,
    private scheduleService: ScheduleService,
  ) { }

  ngOnInit(): void {

    this.actRoute.paramMap.pipe(switchMap(params =>{
      this.routeId = +params.get('id')!;
        return this.scheduleService.findById(+params.get('scheduleId')!)
    })).subscribe(schedule => {

        this.schedule = schedule

        this.editScheduleForm.patchValue({
          assignedDay: this.schedule.assignedDay,
          startTime: new Date(this.schedule.startTime!),
          endTime: new Date(this.schedule.endTime!)
        })

    });

  }

  onSubmitEdit() {
    const assignedDay = this.editScheduleForm.value.assignedDay;
    const startTime = this.editScheduleForm.value.startTime;
    const endTime = this.editScheduleForm.value.endTime;

    const newSchedule = new Schedule(assignedDay!, startTime!, endTime!);

     this.scheduleService.updateSchedule(this.schedule.id!,newSchedule).subscribe();
     this.router.navigate([`/route/${this.routeId}/schedules`]);
  }

  cancelEdit() {
    this.router.navigate([`/route/${this.routeId}/schedules`]);
  }
}
