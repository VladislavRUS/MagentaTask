package com.appmagenta.repositories;

import com.appmagenta.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CityRepository {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public void save(City city){
        hibernateTemplate.saveOrUpdate(city);
    }

    public List<City> list(){
        return hibernateTemplate.loadAll(City.class);
    }

    public City findByName(String cityName){
        List list = hibernateTemplate.find("from City city where city.name = ?", cityName);
        return (City) list.get(0);
    }
}
