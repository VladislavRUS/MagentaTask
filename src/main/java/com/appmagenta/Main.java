package com.appmagenta;

import com.appmagenta.config.BeansConfiguration;
import com.appmagenta.entities.City;
import com.appmagenta.entities.Distance;
import com.appmagenta.repositories.CityRepository;
import com.appmagenta.repositories.DistanceRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.naming.*;

public class Main {

    public static void main(String[] args) throws NamingException {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeansConfiguration.class);
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        CityRepository cityRepository = context.getBean(CityRepository.class);
        DistanceRepository distanceRepository = context.getBean(DistanceRepository.class);
        City tlt = new City();
        tlt.setName("TLT");
        tlt.setLatitude(4.55);
        tlt.setLongitude(5.66);

        City samara = new City();
        samara.setName("SAMARA");
        samara.setLatitude(5.85);
        samara.setLongitude(5.66);

        City orenburg = new City();
        orenburg.setName("ORENBURG");
        orenburg.setLatitude(1.85);
        orenburg.setLongitude(9.36);

        City volgograd = new City();
        volgograd.setName("VOLGOGRAD");
        volgograd.setLatitude(1.83);
        volgograd.setLongitude(19.36);

        cityRepository.save(tlt);
        cityRepository.save(samara);
        cityRepository.save(orenburg);
        cityRepository.save(volgograd);

        cityRepository.list().forEach(city -> System.out.println(city.getName() + " " + city.getId()));
        samara = cityRepository.findByName("SAMARA");
        tlt = cityRepository.findByName("TLT");

        Distance distance = new Distance();
        distance.setCityTo(samara);
        distance.setCityFrom(tlt);
        distance.setDistance(666677);
        distanceRepository.save(distance);

        orenburg = cityRepository.findByName("ORENBURG");
        volgograd = cityRepository.findByName("VOLGOGRAD");

        System.out.println(orenburg.getName() + " " + orenburg.getId());
        System.out.println(volgograd.getName() + " " + volgograd.getId());
        Distance d = new Distance();
        distance.setCityTo(orenburg);
        distance.setCityFrom(volgograd);
        distance.setDistance(4344);
        distanceRepository.save(d);
    }
}
