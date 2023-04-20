package org.web.project.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.project.model.Bus;
import org.web.project.repository.BusRepository;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;

    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    public Bus findBus(Long id) {
        return busRepository.findById(id).orElseThrow();
    }

    public Bus saveBus(Bus Bus) {
        return busRepository.save(Bus);
    }

    public void deleteBus(Long id){
        busRepository.deleteById(id);
    }


}
