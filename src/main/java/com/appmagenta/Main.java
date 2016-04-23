package com.appmagenta;

import com.appmagenta.config.BeansConfiguration;
import com.appmagenta.entities.City;
import com.appmagenta.entities.Distance;
import com.appmagenta.entities.XmlWrapper;
import com.appmagenta.repositories.CityRepository;
import com.appmagenta.repositories.DistanceRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.naming.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws NamingException, JAXBException {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeansConfiguration.class);
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
/*
        CityRepository cityRepository = context.getBean(CityRepository.class);
        DistanceRepository distanceRepository = context.getBean(DistanceRepository.class);

        City orenburg = new City();
        orenburg.setName("ORENBURG");
        orenburg.setLatitude(1.85);
        orenburg.setLongitude(9.36);

        City volgograd = new City();
        volgograd.setName("VOLGOGRAD");
        volgograd.setLatitude(1.83);
        volgograd.setLongitude(19.36);

        Distance d = new Distance();
        d.setCityTo(orenburg);
        d.setCityFrom(volgograd);
        d.setDistance(4445);

        JAXBContext context1 = JAXBContext.newInstance(City.class, Distance.class, List.class);
        Marshaller marshaller = context1.createMarshaller();
        File file = new File("E:\\my.xml");
        List list = new ArrayList<>();
        list.add(orenburg);
        list.add(d);
        *//*
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlWrapper.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        XmlWrapper wrapper = (XmlWrapper) unmarshaller.unmarshal(new File("E:\\distances.xml"));
        wrapper.getDistanceWrapper().getDistanceList().forEach(distance -> System.out.println(distance));*/
        //wrapper.getCityWrapper().getCityList().forEach(city -> System.out.println(city));
        /*
        orenburg = cityRepository.findByName("ORENBURG");
        volgograd = cityRepository.findByName("VOLGOGRAD");

        System.out.println(orenburg.getName() + " " + orenburg.getId());
        System.out.println(volgograd.getName() + " " + volgograd.getId());
        Distance distance = new Distance();
        distance.setCityTo(orenburg);
        distance.setCityFrom(volgograd);
        distance.setDistance(4344);
        distanceRepository.save(distance);

        City samara = new City();
        samara.setName("SAMARA");
        samara.setLatitude(5.85);
        samara.setLongitude(5.66);

        City tlt = new City();
        tlt.setName("TLT");
        tlt.setLatitude(4.55);
        tlt.setLongitude(5.66);

        cityRepository.save(tlt);
        cityRepository.save(samara);
        cityRepository.save(orenburg);
        cityRepository.save(volgograd);

        samara = cityRepository.findByName("SAMARA");
        tlt = cityRepository.findByName("TLT");



        Distance d1 = new Distance();
        d1.setCityTo(tlt);
        d1.setCityFrom(orenburg);
        d1.setDistance(44465655);
        distanceRepository.save(d1);
        */
    }
}
