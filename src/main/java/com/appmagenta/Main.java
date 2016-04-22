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

        cityRepository.save(tlt);
        cityRepository.save(samara);
        cityRepository.save(orenburg);

        samara = cityRepository.findByName("SAMARA");
        tlt = cityRepository.findByName("TLT");

        Distance distance = new Distance();
        distance.setCityTo(samara);
        distance.setCityFrom(tlt);
        distance.setDistance(666677);
        distanceRepository.save(distance);

        samara = cityRepository.findByName("SAMARA");
        orenburg = cityRepository.findByName("ORENBURG");

        Distance d = new Distance();
        distance.setCityTo(samara);
        distance.setCityFrom(orenburg);
        distance.setDistance(4344);
        distanceRepository.save(d);
    }
}
