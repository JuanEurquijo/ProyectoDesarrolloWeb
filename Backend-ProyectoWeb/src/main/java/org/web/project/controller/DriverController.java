package org.web.project.controller;

import java.util.*;

import org.web.project.model.Driver;
import org.web.project.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DriverService driverService;

    @GetMapping("/list")
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/{id}")
    public Driver findDriver(@PathVariable Long id) {
        log.info("Retrieving driver {}", id);
        return driverService.findDriver(id);
    }

    @PutMapping("/update/{id}")
    public Driver updateDriver(@PathVariable Long id, @RequestBody Driver driver) {
        log.info("Updating driver {}", driver.getName());
        Driver driverToUpdate = driverService.findDriver(id);
        if (driverToUpdate != null) {
            driverToUpdate.setName(driver.getName());
            driverToUpdate.setLastName(driver.getLastName());
            driverToUpdate.setIdentifier(driver.getIdentifier());
            driverToUpdate.setPhone(driver.getPhone());
            driverToUpdate.setAddress(driver.getAddress());

            return driverService.saveDriver(driverToUpdate);

        } else {
            throw new NoSuchElementException("Driver not found " + driver.getName());
        }

    }

    @PostMapping("")
    public Driver saveDriver(@RequestBody Driver driver) {
        log.info("Saved driver {}", driver.getName());
        return driverService.saveDriver(driver);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDriver(@PathVariable Long id) {
        log.info("Deleting driver with id {}", id);
        driverService.deleteDriver(id);
    }

}
