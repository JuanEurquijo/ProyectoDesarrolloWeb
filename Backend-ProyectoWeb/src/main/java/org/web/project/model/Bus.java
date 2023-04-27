package org.web.project.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plate;
    private String model;
    
    
    @JsonIgnore
    @OneToMany(mappedBy = "bus")
    private List<Asociation> asociations = new ArrayList<>();
  

    public Bus(){
        
    }


    public Bus(String plate, String model) {
        this.plate = plate;
        this.model = model;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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
        result = prime * result + ((plate == null) ? 0 : plate.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
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
        Bus other = (Bus) obj;
        if (plate == null) {
            if (other.plate != null)
                return false;
        } else if (!plate.equals(other.plate))
            return false;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Bus [plate=" + plate + ", model=" + model + "]";
    }



}
