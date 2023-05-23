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


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((stations == null) ? 0 : stations.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Route other = (Route) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (stations == null) {
            if (other.stations != null)
                return false;
        } else if (!stations.equals(other.stations))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Route [code=" + code + ", stations=" + stations + "]";
    }


}
