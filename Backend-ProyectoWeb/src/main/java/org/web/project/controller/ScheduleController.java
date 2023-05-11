package org.web.project.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web.project.model.Schedule;
import org.web.project.service.ScheduleService;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/list")
    public List<Schedule> getAllRoutes() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/{id}")
    public Schedule findRoute(@PathVariable Long id) {
        log.info("Retrieving schedule {}", id);
        return scheduleService.findSchedule(id);
    }

    @PutMapping("/update/{id}")
    public Schedule updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {
        log.info("Updating schedule {}", schedule.getId());
        Schedule scheduleToUpdate = scheduleService.findSchedule(id);
        if (scheduleToUpdate != null) {
            scheduleToUpdate.setAssignedDay(schedule.getAssignedDay());
            scheduleToUpdate.setStartTime(schedule.getStartTime());
            scheduleToUpdate.setEndTime(schedule.getEndTime());
            return scheduleService.saveSchedule(scheduleToUpdate);

        } else {
            throw new NoSuchElementException("Schedule not found " + schedule.getId());
        }

    }

    @PostMapping("")
    public Schedule saveSchedule(@RequestBody Schedule schedule) {

        log.info("Saved Schedule {}", schedule.getAssignedDay());
        return scheduleService.saveSchedule(schedule);
    }



}
