package com.appmagenta.controllers;

import com.appmagenta.entities.City;
import com.appmagenta.repositories.CityRepository;
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
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<City>> all(){
        List<City> list = new ArrayList<>();
        cityRepository.findAll().forEach(list::add);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<City> getById(@PathVariable(value = "id") long id){
        City city = cityRepository.findById(id);
        if(city != null){
            return new ResponseEntity<>(city, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
