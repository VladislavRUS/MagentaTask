package com.appmagenta.services;

import com.appmagenta.entities.City;
import com.appmagenta.entities.Distance;
import com.appmagenta.repositories.CityRepository;
import com.appmagenta.repositories.DistanceRepository;
import com.appmagenta.wrappers.XmlWrapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
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

    public void processFile(MultipartFile file) throws JAXBException, IOException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlWrapper.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        ClassLoader classLoader = getClass().getClassLoader();
        File xmlSchema = new File(classLoader.getResource("schema/data_scheme.xml").getFile());
        Schema schema = schemaFactory.newSchema(xmlSchema);
        unmarshaller.setSchema(schema);
        XmlWrapper wrapper = (XmlWrapper) unmarshaller.unmarshal(file.getInputStream());
        //Make a map cityName <-> City
        Map<String, City> cityMap = wrapper.getCityWrapper()
                .getCityList().stream()
                .collect(Collectors.toMap(City::getName, city->city));

        //Create distances with full cities
        List<Distance> distanceList = wrapper.getDistanceWrapper().getDistanceList();
        List<String> cityNames = new ArrayList<>();
        cityRepository.findAll().forEach(city -> cityNames.add(city.getName()));

        //First we make normal distances
        //because in xml are only names and value
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
            else if (!cityMap.containsKey(cityFromName) || !cityMap.containsKey(cityToName)){
                if(!cityNames.contains(cityFromName) || !cityNames.contains(cityToName)){
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
