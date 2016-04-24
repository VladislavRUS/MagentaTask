package com.appmagenta.controllers;

import com.appmagenta.entities.Distance;
import com.appmagenta.services.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/matrix")
public class MatrixDistanceController {

    @Autowired
    private DistanceService distanceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Distance>> matrix(){
        List<Distance> distances = distanceService.matrixDistancesCities();
        return new ResponseEntity<>(distances, HttpStatus.OK);
    }
}
