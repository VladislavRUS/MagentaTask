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

    public List<Distance> traverse(City cityFrom, City cityTo) {
        List<Distance> path = new ArrayList<>();
        List<City> used = new ArrayList<>();
        try {
            if (!cityRepository.isInTheDistanceTable(cityFrom.getId())) {
                throw new RuntimeException(cityFrom.getName());
            } else if (!cityRepository.isInTheDistanceTable(cityTo.getId())) {
                throw new RuntimeException(cityTo.getName());
            }
            matrixDistanceBetweenTwoCities(cityFrom, cityTo, path, used);
        }catch (Exception e){
            logger.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            logger.debug("CITY: " + e.getMessage() + " IS NOT IN THE DISTANCE TABLE");
        }
        return path;
    }

    private void matrixDistanceBetweenTwoCities(City cityFrom, City cityTo, List<Distance> path, List<City> used){
        used.add(cityFrom);
        if(distanceRepository.existsByFromAndTo(cityFrom.getId(), cityTo.getId())){
            used.add(cityTo);
            path.add(distanceRepository.findByCities(cityFrom.getId(), cityTo.getId()));
            return;
        }
        List<City> adjacentCities = cityRepository.findAdjacencyCities(cityFrom.getId());
        for (City adjacentCity : adjacentCities) {
            if (!used.contains(adjacentCity) && !used.contains(cityTo)) {
                path.add(distanceRepository.findByCities(cityFrom.getId(), adjacentCity.getId()));
                matrixDistanceBetweenTwoCities(adjacentCity, cityTo, path, used);
            }
        }
    }

    public List<Distance> matrixDistancesBetweenCities() {
        List<City> cities = new ArrayList<>();
        cityRepository.findAll().forEach(cities::add);
        List<Distance> distances = new ArrayList<>();
        int size = cities.size();

        for(int i = 0; i < size; i++){
            for(int j = i; j < size; j++){
                if (!cities.get(i).getName().equals(cities.get(j).getName())) {
                    final City cityTo = cities.get(j);
                    List<Distance> path = traverse(cities.get(i), cityTo);
                    if(path.size() > 0){
                        Distance result = new Distance();
                        result.setCityFrom(cities.get(i));
                        result.setCityTo(cities.get(j));
                        path.forEach(distance -> result.setValue(result.getValue() + distance.getValue()));
                        distances.add(result);
                    }
                }
            }
        }
        return distances;
    }
}
