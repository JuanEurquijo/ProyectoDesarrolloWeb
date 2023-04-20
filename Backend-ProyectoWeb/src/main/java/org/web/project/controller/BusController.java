package org.web.project.controller;

import java.util.*;

import org.web.project.model.Bus;
import org.web.project.service.BusService;
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
@RequestMapping("/api/bus")
public class BusController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private BusService busService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/list")
    public List<Bus> getAllBuses() {
        return busService.getAllBuses();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    public Bus findBus(@PathVariable Long id) {
        log.info("Retrieving bus {}", id);
        return busService.findBus(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/update/{id}")
    public Bus updateBus(@PathVariable Long id, @RequestBody Bus bus) {
        log.info("Updating bus {}", bus.getPlate());
        Bus busToUpdate = busService.findBus(id);
        if (busToUpdate != null) {
            busToUpdate.setPlate(bus.getPlate());
            busToUpdate.setModel(bus.getModel());
    
            return busService.saveBus(busToUpdate);

        } else {
            throw new NoSuchElementException("Bus not found " + bus.getPlate());
        }

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("")
    public Bus saveBus(@RequestBody Bus bus) {
        log.info("Saved bus {}", bus.getPlate());
        return busService.saveBus(bus);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/delete/{id}")
    public void deleteBus(@PathVariable Long id) {
        log.info("Deleting bus with id {}", id);
        busService.deleteBus(id);
    }

 

}
