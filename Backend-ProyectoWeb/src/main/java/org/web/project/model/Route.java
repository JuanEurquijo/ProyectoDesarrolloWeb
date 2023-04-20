package org.web.project.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Route {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;


    
    @JsonIgnore
    @OneToMany(mappedBy = "route")
    private List<Asociation> asociations = new ArrayList<>();


    @ManyToMany
    private List<Station> stations = new ArrayList<>();


    
    public Route() {
    }


    public Route(String code, List<Station> stations) {
        this.code = code;
        this.stations = stations;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public List<Station> getStations() {
        return stations;
    }


    public void setStations(List<Station> stations) {
        this.stations = stations;
    }


    public List<Asociation> getAsociations() {
        return asociations;
    }


    public void setAsociations(List<Asociation> asociations) {
        this.asociations = asociations;
    }


}
