package com.appmagenta.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class MainController {
    @RequestMapping(value = "/file_upload", method = RequestMethod.POST)
    public ResponseEntity<String> processFile(MultipartFile file){
        if(!file.isEmpty()){
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File("E:\\myfile.jpg")));
                outputStream.write(bytes);
                outputStream.close();
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
