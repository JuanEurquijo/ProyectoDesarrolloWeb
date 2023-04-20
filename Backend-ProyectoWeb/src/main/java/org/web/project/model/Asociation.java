package org.web.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Asociation {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Driver driver;
    
    @ManyToOne
    @JoinColumn(name = "bus")
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "route")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "schedule")
    private Schedule schedule;



    public Asociation() {}



    public Asociation(Driver driver, Bus bus, Route route, Schedule schedule) {
        this.driver = driver;
        this.bus = bus;
        this.route = route;
        this.schedule = schedule;
    }



    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Driver getDriver() {
        return driver;
    }


    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    
    public Bus getBus() {
        return bus;
    }


    public void setBus(Bus bus) {
        this.bus = bus;
    }


    public Route getRoute() {
        return route;
    }


    public void setRoute(Route route) {
        this.route = route;
    }


    public Schedule getSchedule() {
        return schedule;
    }


    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    
}