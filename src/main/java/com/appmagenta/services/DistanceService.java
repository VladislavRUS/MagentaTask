package com.appmagenta.services;

import com.appmagenta.entities.City;
import com.appmagenta.entities.Distance;
import com.appmagenta.repositories.CityRepository;
import com.appmagenta.repositories.DistanceRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DistanceService {

    private static final Logger logger = Logger.getLogger(DistanceService.class);
    private static final int RADIUS = 6371;

    @Autowired
    private DistanceRepository distanceRepository;

    @Autowired
    private CityRepository cityRepository;

    public List<Distance> crowFlightDistanceBetweenCities(){
        List<City> cityList = new ArrayList<>();
        cityRepository.findAll().forEach(cityList::add);
        List<Distance> crowFlightDistances = new ArrayList<>();
        int size = cityList.size();
        for(int i = 0; i < size; i++){
            for(int j = i; j < size; j++){
                City cityFrom = cityList.get(i);
                City cityTo = cityList.get(j);
                if(!cityFrom.getName().equals(cityTo.getName())){
                    crowFlightDistances.add(crowFlightDistanceBetweenTwoCities(cityFrom, cityTo));
                }
            }
        }
        return crowFlightDistances;
    }

    private Distance crowFlightDistanceBetweenTwoCities(City cityFrom, City cityTo){

        double latitudeFrom = Math.toRadians(cityFrom.getLatitude());
        double longitudeFrom = Math.toRadians(cityFrom.getLongitude());
        double latitudeTo = Math.toRadians(cityTo.getLatitude());
        double longitudeTo = Math.toRadians(cityTo.getLongitude());

        double delta = Math.sin(latitudeFrom)*Math.sin(latitudeTo) + Math.cos(latitudeFrom)*Math.cos(latitudeTo)*Math.cos(Math.abs(longitudeFrom-longitudeTo));
        double L = Math.acos(delta) * RADIUS;
        Distance distance = new Distance();
        distance.setCityFrom(cityFrom);
        distance.setCityTo(cityTo);
        distance.setValue(L);
        return distance;
    }

    public Distance matrixDistanceBetweenTwoCities(City cityFrom, City cityTo){
        List<City> adjacentCities = cityRepository.findAdjacencyCities(cityFrom.getId());
        Distance distance = new Distance();
        distance.setCityFrom(cityFrom);
        distance.setCityTo(cityTo);
        distance.setValue(0.0);
        adjacentCities.forEach(city -> {
            if(city.getName().equals(cityTo.getName())){
                distance.setValue(distanceRepository.findByCities(cityFrom.getId(), city.getId()).getValue());
            }
        });
        return distance;
    }

    public List<Distance> matrixDistancesCities() {
        List<City> cities = new ArrayList<>();
        cityRepository.findAll().forEach(cities::add);
        List<Distance> distances = new ArrayList<>();
        int size = cities.size();
        for(int i = 0; i < size; i++){
            for(int j = i; j < size; j++){
                distances.add(matrixDistanceBetweenTwoCities(cities.get(i), cities.get(j)));
            }
        }
        return distances;
    }
}
