package org.web.project.model;


import java.util.ArrayList;
import java.util.List;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity()
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Debes especificar el nombre")
    private String name;

    
    @NotEmpty(message = "Debes especificar los apellidos")
    private String lastName;

    
    @Pattern(regexp = "[0-9]*",message = "Debe ser un numero")
    @NotEmpty(message = "Debes especificar la cédula")
    private String identifier;

    @Pattern(regexp = "[0-9]*",message = "Debe ser un numero")
    @NotEmpty(message = "Debes especificar el teléfono")
    private String phone;

    @NotEmpty(message = "Debes especificar la dirección")
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "driver")
    private List<Asociation> asociations = new ArrayList<>();


    public Driver() {
    }

    public Driver(String name, String lastName, String identifier, String phone, String address) {
        this.name = name;
        this.lastName = lastName;
        this.identifier = identifier;
        this.phone = phone;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Asociation> getAsociations() {
        return asociations;
    }

    public void setAsociations(List<Asociation> asociations) {
        this.asociations = asociations;
    }


}
