package com.appmagenta.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@Entity
@Table(name = "CITIES")
@XmlType(propOrder = {"name", "latitude", "longitude"}, name = "city")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class City {

    @Id
    @Column(name = "CAR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private long id;

    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "LATITUDE")
    private double latitude;

    @Column(name = "LONGITUDE")
    private double longitude;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString(){
        return "ID: " + this.id + " NAME: " + this.name + " LATITUDE: " + this.latitude + " LONGITUDE: " + this.longitude;
    }
}
