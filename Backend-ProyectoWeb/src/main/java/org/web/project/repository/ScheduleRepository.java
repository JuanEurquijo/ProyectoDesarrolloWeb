package org.web.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.web.project.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

     
}
