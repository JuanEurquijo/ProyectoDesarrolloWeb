package org.web.project.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web.project.model.Station;
import org.web.project.service.StationService;

@RestController
@RequestMapping("/api/station")
public class StationController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private StationService stationService;

    @GetMapping("/list")
    public List<Station> getAllStations() {
        return stationService.getAllStations();
    }

    @GetMapping("/{id}")
    public Station findStation(@PathVariable Long id) {
        log.info("Retrieving Station {}", id);
        return stationService.findStation(id);
    }


}
