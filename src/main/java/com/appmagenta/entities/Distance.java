package com.appmagenta.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@Entity
@Table(name = "DISTANCES")
@XmlType(propOrder = {"cityFrom", "cityTo", "value"}, name = "distance")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Distance {

    @Id
    @Column(name = "DISTANCE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private long id;

    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "CITY_FROM", referencedColumnName = "CITY_ID")
    private City cityFrom;

    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "CITY_TO", referencedColumnName = "CITY_ID")
    private City cityTo;

    @Column(name = "VALUE")
    private double value;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public City getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(City cityFrom) {
        this.cityFrom = cityFrom;
    }

    public City getCityTo() {
        return cityTo;
    }

    public void setCityTo(City cityTo) {
        this.cityTo = cityTo;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double distance) {
        this.value = distance;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + " VALUE: " + value;
    }
}
