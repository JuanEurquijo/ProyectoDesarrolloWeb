package org.web.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.web.project.model.Asociation;
import org.web.project.model.Bus;
import org.web.project.model.Driver;
import org.web.project.model.Route;
import org.web.project.model.Schedule;

public interface AsociationRepository extends JpaRepository<Asociation, Long> {

     @Query("SELECT a.schedule FROM Asociation a WHERE a.route = :route")
     List<Schedule> findSchedulesByRoute(@Param("route") Route route);

     @Query("SELECT a.route FROM Asociation a WHERE a.bus = :bus")
     List<Route> findRoutesByBus(@Param("bus") Bus bus);

     @Query("SELECT a.bus FROM Asociation a WHERE a.driver = :driver")
     List<Bus> findBusesByDriver(@Param("driver") Driver driver);

     @Query("SELECT a.bus FROM Asociation a WHERE a.route = :route")
     List<Bus> getAssignedBusesByRoute(@Param("route") Route route);

     @Query("SELECT a.driver FROM Asociation a WHERE a.bus = :bus")
     List<Driver> getAssignedDriversByBus(@Param("bus") Bus bus);

     @Query("SELECT a.schedule FROM Asociation a WHERE a.bus = :bus AND a.route = :route")
     List<Schedule> findSchedulesByBusAndRoute(@Param("bus") Bus bus, @Param("route") Route route);

     @Query("SELECT a.schedule FROM Asociation a WHERE a.driver = :driver AND a.bus = :bus AND a.route = :route")
     List<Schedule> findSchedulesByDriver(@Param("driver") Driver driver,@Param("bus") Bus bus,@Param("route") Route route);

    
}

