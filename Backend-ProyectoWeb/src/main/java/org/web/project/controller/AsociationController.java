package org.web.project.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web.project.model.Asociation;
import org.web.project.model.Bus;
import org.web.project.model.Driver;
import org.web.project.model.Route;
import org.web.project.model.Schedule;
import org.web.project.service.AsociationService;
import org.web.project.service.BusService;
import org.web.project.service.DriverService;
import org.web.project.service.RouteService;

@RestController
@RequestMapping("/api/assignment")
public class AsociationController {

    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private AsociationService asociationService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private BusService busService;

    @Autowired
    private DriverService driverService;


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/list")
    public List<Asociation> getAllDrivers() {
        return asociationService.getAllAsociations();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("")
    public Asociation saveAssignment(@RequestBody Asociation asociation) {
        log.info("Saved assignment");
        return asociationService.saveAssignment(asociation);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/update/{id}")
    public Asociation updateDriver(@PathVariable Long id, @RequestBody Asociation asociation) {
        log.info("Updating assignment {}", asociation.getId());
        Asociation assignToUpdate = asociationService.findAsociation(id);
        if (assignToUpdate != null) {
            assignToUpdate.setDriver(asociation.getDriver());
            assignToUpdate.setBus(asociation.getBus());
            assignToUpdate.setRoute(asociation.getRoute());
            assignToUpdate.setSchedule(asociation.getSchedule());

            return asociationService.saveAssignment(assignToUpdate);

        } else {
            throw new NoSuchElementException("Assignment not found " + asociation.getId());
        }

    }

    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}/schedules")
    public List<Schedule> findSchedulesByRoute(@PathVariable Long id) {
        log.info("Getting schedules by route");
    
        Route route = routeService.findRoute(id);
        return asociationService.findSchedulesByRoute(route);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}/routes")
    public List<Route> findRoutesByBus(@PathVariable Long id) {
        log.info("Getting routes by bus");
    
        Bus bus = busService.findBus(id);
        return asociationService.findRouteByBus(bus);

    }


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}/buses")
    public List<Bus> findBusesByDriver(@PathVariable Long id) {
        log.info("Getting buses by driver");
    
        Driver driver = driverService.findDriver(id);
        return asociationService.findBusByDriver(driver);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}/assignedBuses")
    public List<Bus> getAssignedBusesByRoute(@PathVariable Long id) {
        log.info("Getting buses by route");
    
        Route route = routeService.findRoute(id);
        return asociationService.getAssignedBusesByRoute(route);

    }


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}/assignedDrivers")
    public List<Driver> getAssignedDriversByBus(@PathVariable Long id) {
        log.info("Getting drivers by bus");
    
        Bus bus = busService.findBus(id);
        return asociationService.getAssignedDriversByBus(bus);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("bus/{idBus}/route/{idRoute}")
    public List<Schedule> finSchedulesByBusAndRoute(@PathVariable Long idBus, @PathVariable Long idRoute) {
        log.info("Getting schedules by bus {} and route {}", idBus, idRoute);
    
        Bus bus = busService.findBus(idBus);
        Route route = routeService.findRoute(idRoute);
        return asociationService.findSchedulesByBusAndRoute(bus,route);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("driver/{idDriver}/bus/{idBus}/route/{idRoute}")
    public List<Schedule> finSchedulesByDriver(@PathVariable Long idDriver, @PathVariable Long idBus, @PathVariable Long idRoute) {
        log.info("Getting schedules by driver");
        Driver driver = driverService.findDriver(idDriver);
        Bus bus = busService.findBus(idBus);
        Route route = routeService.findRoute(idRoute);
        return asociationService.findSchedulesByDriver(driver,bus,route);
    }
    
}
