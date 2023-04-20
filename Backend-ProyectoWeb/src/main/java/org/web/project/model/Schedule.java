package org.web.project.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assignedDay;
    private Date startTime;
    private Date endTime;


    @JsonIgnore
    @OneToMany(mappedBy = "schedule")
    private List<Asociation> asociations = new ArrayList<>();



    public Schedule() {
    }

    public Schedule(String assignedDay, Date startTime, Date endTime) {
        this.assignedDay = assignedDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssignedDay() {
        return assignedDay;
    }

    public void setAssignedDay(String assignedDay) {
        this.assignedDay = assignedDay;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Schedule [id=" + id + ", assignedDay=" + assignedDay + ", startTime=" + startTime + ", endTime="
                + endTime + "]";
    }


    

   
    

    
    
}
