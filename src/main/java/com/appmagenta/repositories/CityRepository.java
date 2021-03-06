package com.appmagenta.repositories;

import com.appmagenta.entities.City;
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

    //Query looks for all cities, adjacent with given one
    @Query("SELECT c FROM City c WHERE c.id IN (SELECT d.cityFrom.id FROM Distance d where d.cityTo.id =:id) OR " +
            " c.id IN (SELECT d.cityTo.id FROM Distance d WHERE d.cityFrom.id =:id)")
    List<City> findAdjacencyCities(@Param(value = "id") long id);

    //Query checks if the given city is in the distance table
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM City c WHERE c.id IN (SELECT d.cityFrom.id FROM Distance d where d.cityTo.id =:id) OR " +
            " c.id IN (SELECT d.cityTo.id FROM Distance d WHERE d.cityFrom.id =:id)")
    boolean isInTheDistanceTable(@Param(value = "id") long id);
}
