package com.appmagenta.controllers;

import com.appmagenta.entities.Distance;
import com.appmagenta.repositories.DistanceRepository;
import com.appmagenta.services.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/distances")
public class DistanceController {

    @Autowired
    private DistanceRepository distanceRepository;

    @Autowired
    private DistanceService distanceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Distance>> all(){
        List<Distance> list = new ArrayList<>();
        distanceRepository.findAll().forEach(list::add);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Distance> getById(@PathVariable(value = "id") int id){
        Distance distance = distanceRepository.findById(id);
        return new ResponseEntity<>(distance, HttpStatus.OK);
    }
}
