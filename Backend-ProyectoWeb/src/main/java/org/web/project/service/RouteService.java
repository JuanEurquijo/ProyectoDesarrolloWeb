package org.web.project.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.project.model.Route;
import org.web.project.repository.RouteRepository;


@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Route findRoute(Long id) {
        return routeRepository.findById(id).orElseThrow();
    }

    public Route saveRoute(Route route) {
        return routeRepository.save(route);
    }

    public void deleteRoute(Long id){
        routeRepository.deleteById(id);
    }


}
