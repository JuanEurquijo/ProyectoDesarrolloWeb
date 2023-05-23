package org.web.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.project.model.Station;
import org.web.project.repository.StationRepository;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    public Station findStation(Long id) {
        return stationRepository.findById(id).orElseThrow();
    }

    public Station saveStation(Station station) {
        return stationRepository.save(station);
    }

    public void deleteStation(Long id){
        stationRepository.deleteById(id);
    }


}
