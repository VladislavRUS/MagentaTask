package com.appmagenta.repositories;

import com.appmagenta.entities.City;
import com.appmagenta.entities.Distance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CityRepository extends CrudRepository<City, Long> {
    City findByName(String name);
    City findById(long id);
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM City c WHERE c.name =:name")
    boolean existsByName(@Param("name") String name);

    //This query looks for all cities, adjacent with given one
    @Query("SELECT c from City c where c.id IN (SELECT d.cityFrom.id FROM Distance d where d.cityTo.id =:id) OR " +
            " c.id IN (SELECT d.cityTo.id FROM Distance d WHERE d.cityFrom.id =:id)")
    List<City> findAdjacencyCities(long id);
}
