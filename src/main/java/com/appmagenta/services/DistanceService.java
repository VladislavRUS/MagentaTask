package com.appmagenta.services;

import com.appmagenta.entities.City;
import com.appmagenta.entities.Distance;
import com.appmagenta.repositories.CityRepository;
import com.appmagenta.repositories.DistanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {
    private static final int RADIUS = 6371;

    @Autowired
    private DistanceRepository distanceRepository;

    @Autowired
    private CityRepository cityRepository;

    public Distance crowFlightDistanceBetweenTwoCities(String from, String to){
        City cityFrom = cityRepository.findByName(from);
        City cityTo = cityRepository.findByName(to);
        double latitudeFrom = cityFrom.getLatitude();
        double longitudeFrom = cityFrom.getLongitude();
        double latitudeTo = cityTo.getLatitude();
        double longitudeTo = cityTo.getLongitude();

        double cosD = Math.sin(latitudeFrom)*Math.sin(latitudeTo) + Math.cos(latitudeFrom)*Math.cos(latitudeTo)*Math.cos(longitudeFrom - longitudeTo);
        double D = Math.acos(cosD);
        double L = D * RADIUS;
        Distance distance = new Distance();
        distance.setCityFrom(cityFrom);
        distance.setCityTo(cityTo);
        distance.setValue(L);

        return distance;
    }

    public Distance matrixDistanceBetweenTwoCities(String from, String to){
        City cityFrom = cityRepository.findByName(from);
        City cityTo = cityRepository.findByName(to);
        return distanceRepository.findByCities(cityFrom.getId(), cityTo.getId());
    }
}
