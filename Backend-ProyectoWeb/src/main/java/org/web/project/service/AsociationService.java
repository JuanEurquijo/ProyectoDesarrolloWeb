package org.web.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.project.model.Asociation;
import org.web.project.model.Bus;
import org.web.project.model.Driver;
import org.web.project.model.Route;
import org.web.project.model.Schedule;
import org.web.project.repository.AsociationRepository;

@Service
public class AsociationService {
    
    @Autowired
    private AsociationRepository asociationRepository;


    public List<Asociation> getAllAsociations() {
        return asociationRepository.findAll();
    }

    public Asociation findAsociation(Long id) {
        return asociationRepository.findById(id).orElseThrow();
    }

    public Asociation saveAssignment(Asociation asociation) {
        return asociationRepository.save(asociation);
    }

    public List<Schedule> findSchedulesByRoute(Route route){
       return asociationRepository.findSchedulesByRoute(route);
    }

    public List<Route> findRouteByBus(Bus bus){
        return asociationRepository.findRoutesByBus(bus);
     }

     public List<Bus> findBusByDriver(Driver driver){
        return asociationRepository.findBusesByDriver(driver);
     }

     public  List<Bus> getAssignedBusesByRoute(Route route){
        return asociationRepository.getAssignedBusesByRoute(route);
     }

     public List<Driver> getAssignedDriversByBus(Bus bus){
        return asociationRepository.getAssignedDriversByBus(bus);
     }

     public List<Schedule> findSchedulesByBusAndRoute(Bus bus, Route route){
      return asociationRepository.findSchedulesByBusAndRoute(bus, route);
     }

      public List<Schedule> findSchedulesByDriver(Driver driver,Bus bus, Route route){
         return asociationRepository.findSchedulesByDriver(driver,bus,route);
     }
   

}
