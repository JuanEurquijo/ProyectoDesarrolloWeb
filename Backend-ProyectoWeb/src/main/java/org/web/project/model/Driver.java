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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
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
        Driver other = (Driver) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (identifier == null) {
            if (other.identifier != null)
                return false;
        } else if (!identifier.equals(other.identifier))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Driver [name=" + name + ", lastName=" + lastName + ", identifier=" + identifier + ", phone=" + phone
                + ", address=" + address + "]";
    }

    
}
