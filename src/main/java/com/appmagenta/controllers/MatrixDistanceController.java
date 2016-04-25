package com.appmagenta.controllers;

import com.appmagenta.entities.City;
import com.appmagenta.entities.Distance;
import com.appmagenta.repositories.CityRepository;
import com.appmagenta.services.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/matrix")
public class MatrixDistanceController {

    @Autowired
    private DistanceService distanceService;

    @Autowired
    private CityRepository cityRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Distance>> matrix(){
        List<Distance> distances = distanceService.matrixDistancesBetweenCities();
        return new ResponseEntity<>(distances, HttpStatus.OK);
    }
    @RequestMapping(value = "/{first}&{second}", method = RequestMethod.GET)
    public ResponseEntity<List<City>> betweenTwo(@PathVariable(value = "first") long firstID, @PathVariable(value = "second") long secondID){
        City from = cityRepository.findById(firstID);
        City to = cityRepository.findById(secondID);
        List<Distance> list = distanceService.traverse(from, to);
        List<City> cities = new ArrayList<>();
        cities.add(from);
        City previous = from;
        for(Distance distance: list){
            if(!distance.getCityFrom().getName().equals(previous.getName()) && !cities.contains(distance.getCityFrom())){
                cities.add(distance.getCityFrom());
                previous = distance.getCityFrom();
            }
            else{
                cities.add(distance.getCityTo());
                previous = distance.getCityTo();
            }
        }
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
}
