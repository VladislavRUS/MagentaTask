package com.appmagenta.entities;

import javax.persistence.*;

@Entity
@Table(name = "DISTANCES")
public class Distance {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private City cityFrom;

    @ManyToOne(cascade = CascadeType.ALL)
    private City cityTo;

    @Column(name = "DISTANCE")
    private double distance;

    public Distance() {
    }

    public Distance(City cityFrom, double distance) {
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.distance = distance;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
