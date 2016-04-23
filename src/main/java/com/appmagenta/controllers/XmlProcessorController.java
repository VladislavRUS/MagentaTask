package com.appmagenta.controllers;

import com.appmagenta.services.XmlProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class XmlProcessorController {

    @Autowired
    private XmlProcessorService xmlProcessorService;

    @RequestMapping(value = "/file_upload", method = RequestMethod.POST)
    public ResponseEntity<String> processFile(MultipartFile file){
        if(!file.isEmpty()){
            try {
                xmlProcessorService.processFile(file);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
