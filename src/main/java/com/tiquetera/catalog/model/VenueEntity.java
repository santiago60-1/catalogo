package com.tiquetera.catalog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "venues")
public class VenueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del venue es obligatorio")
    @Size(max = 200)
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Size(max = 300)
    @Column(name = "address", length = 300)
    private String address;

    @Size(max = 100)
    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "capacity")
    private Integer capacity;

    protected VenueEntity() {}

    public VenueEntity(Long id, String name, String address, String city, Integer capacity) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.capacity = capacity;
    }

    public static VenueEntity of(Long id, String name, String address, String city, Integer capacity) {
        return new VenueEntity(id, name, address, city, capacity);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name == null ? null : name.trim(); }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VenueEntity)) return false;
        VenueEntity that = (VenueEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
