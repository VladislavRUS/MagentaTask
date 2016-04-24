package com.appmagenta.services;

import com.appmagenta.entities.City;
import com.appmagenta.entities.Distance;
import com.appmagenta.wrappers.XmlWrapper;
import com.appmagenta.repositories.CityRepository;
import com.appmagenta.repositories.DistanceRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class XmlProcessorService {

    private static final Logger logger = Logger.getLogger(XmlProcessorService.class);

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
        List<String> cityNames = new ArrayList<>();
        cityRepository.findAll().forEach(city -> cityNames.add(city.getName()));

        //First we make normal distances
        distanceList.forEach(distance -> {
            String cityFromName = distance.getCityFrom().getName();
            String cityToName = distance.getCityTo().getName();
            //If this is the first addition, then cityNames is empty,
            //so we need to check only cities from xml file that are in the map
            if(cityNames.size() == 0){
                if(!cityMap.containsKey(cityFromName) || !cityMap.containsKey(cityToName)){
                    throw new RuntimeException("City in distances is not present in cities");
                }
            }
            //If database already contains cities then we need to check xml and cityNames from db
            else{
                if(!cityMap.containsKey(cityFromName) || !cityMap.containsKey(cityToName)
                        || !cityNames.contains(cityFromName) || !cityNames.contains(cityToName)){
                    throw new RuntimeException("City in distances is not present in cities");
                }
            }
            City cityFrom = cityMap.get(cityFromName);
            City cityTo = cityMap.get(cityToName);
            distance.setCityFrom(cityFrom);
            distance.setCityTo(cityTo);
        });

        distanceList.forEach(distance ->{
            String cityFromName = distance.getCityFrom().getName();
            String cityToName = distance.getCityTo().getName();

            if(!cityNames.contains(cityFromName)){
                cityRepository.save(cityMap.get(cityFromName));
            }else{
                distance.setCityFrom(cityRepository.findByName(cityFromName));
            }
            if(!cityNames.contains(cityToName)){
                cityRepository.save(cityMap.get(cityToName));
            }else{
                distance.setCityTo(cityRepository.findByName(cityToName));
            }
            if(!distanceRepository.existsByFromAndTo(distance.getCityFrom().getId(), distance.getCityTo().getId())){
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
