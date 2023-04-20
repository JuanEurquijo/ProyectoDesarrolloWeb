package org.web.project.controller;

import java.util.*;

import org.web.project.model.Route;
import org.web.project.model.Station;
import org.web.project.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RouteService routeService;


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/list")
    public List<Route> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    public Route findRoute(@PathVariable Long id) {
        log.info("Retrieving route {}", id);
        return routeService.findRoute(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/update/{id}")
    public Route updateRoute(@PathVariable Long id, @RequestBody Route route) {
        log.info("Updating route {}", route.getCode());
        Route routeToUpdate = routeService.findRoute(id);
        if (routeToUpdate != null) {
            routeToUpdate.setCode(route.getCode());
            routeToUpdate.setStations(route.getStations());
            return routeService.saveRoute(routeToUpdate);

        } else {
            throw new NoSuchElementException("Route not found " + route.getCode());
        }

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("")
    public Route saveRoute(@RequestBody Route r) {
        log.info("Saved route {}", r.getCode());
        return routeService.saveRoute(r);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/delete/{id}")
    public void deleteRoute(@PathVariable Long id) {
        log.info("Deleting route with id {}", id);
        routeService.deleteRoute(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}/stations")
    public List<Station> getRouteStations(@PathVariable Long id) {
        Route route = findRoute(id);
        if (route == null) {
            throw new NoSuchElementException("Route not found " + id);
        }
        return route.getStations();
    }


}
