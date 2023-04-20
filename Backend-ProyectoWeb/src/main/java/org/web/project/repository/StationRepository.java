package org.web.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.web.project.model.Station;

public interface StationRepository extends JpaRepository<Station, Long> {

     
}