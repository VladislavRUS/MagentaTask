package com.appmagenta.entities;

import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@Entity
@Table(name = "CITIES")
@XmlType(propOrder = {"name", "latitude", "longitude"}, name = "city")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class City {

    @Id
    @Column(name = "CITY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    @Index(name = "CITY_ID_INDEX")
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

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(!(obj instanceof City)){
            return false;
        }
        City another = (City)obj;
        return this.name.equals(another.name) && this.latitude == another.latitude && this.longitude == another.longitude;
    }

    @Override
    public int hashCode() {
        int result = 0;
        long latBits = Double.doubleToLongBits(latitude);
        long longBits = Double.doubleToLongBits(longitude);
        int intLatBits = (int)(latBits ^ (latBits >>> 32));
        int intLongBits = (int)(longBits ^ (longBits >>> 32));
        result += intLatBits;
        result += intLongBits;
        for(int i = 0; i < name.length(); i++){
            result += (int)name.charAt(i);
        }
        return result;
    }
}
