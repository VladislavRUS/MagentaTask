package com.appmagenta.services;

import com.appmagenta.entities.City;
import com.appmagenta.entities.Distance;
import com.appmagenta.wrappers.XmlWrapper;
import com.appmagenta.repositories.CityRepository;
import com.appmagenta.repositories.DistanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class XmlProcessorService {

    @Autowired
    private DistanceRepository distanceRepository;

    @Autowired
    private CityRepository cityRepository;

    public void processFile(MultipartFile file) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlWrapper.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        XmlWrapper wrapper = (XmlWrapper) unmarshaller.unmarshal(file.getInputStream());
        //Make a map CityName / City
        Map<String, City> cityMap = wrapper.getCityWrapper()
                .getCityList().stream()
                .collect(Collectors.toMap(City::getName, city->city));

        //Create distances with full cities
        List<Distance> distanceList = wrapper.getDistanceWrapper().getDistanceList();
        distanceList.forEach(distance -> {
            //If any city among distances in xml file does not exist in xml nor in database then throw exception
            if(     !cityMap.containsKey(distance.getCityFrom().getName()) ||
                    !cityMap.containsKey(distance.getCityTo().getName()) ||
                    !cityRepository.existsByName(distance.getCityFrom().getName()) ||
                    !cityRepository.existsByName(distance.getCityTo().getName())) {
                throw new RuntimeException("City in distances is not present in cities");
            }
            City cityFrom = cityMap.get(distance.getCityFrom().getName());
            City cityTo = cityMap.get(distance.getCityTo().getName());
            distance.setCityFrom(cityFrom);
            distance.setCityTo(cityTo);
        });

        distanceList.forEach(distance ->{
            if(!cityRepository.existsByName(distance.getCityTo().getName())) {
                cityRepository.save(cityMap.get(distance.getCityTo().getName()));
            }else{
                distance.setCityTo(cityRepository.findByName(distance.getCityTo().getName()));
            }
            if(!cityRepository.existsByName(distance.getCityFrom().getName())) {
                cityRepository.save(cityMap.get(distance.getCityFrom().getName()));
            }else{
                distance.setCityFrom(cityRepository.findByName(distance.getCityFrom().getName()));
            }
            if(!distanceRepository.existsByFromToValue(distance.getCityFrom().getId(), distance.getCityTo().getId(), distance.getValue())){
                distanceRepository.save(distance);
            }
        });
        cityMap.forEach((name, city) ->{
            if(!cityRepository.existsByName(name)) {
                cityRepository.save(city);
            }
        });
    }
}
