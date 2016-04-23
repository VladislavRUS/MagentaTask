package com.appmagenta.repositories;

import com.appmagenta.entities.Distance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DistanceRepository extends CrudRepository<Distance, Long>{
    @Query( "SELECT CASE WHEN COUNT(d) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Distance d WHERE (d.cityFrom.id =:cityFrom and d.cityTo.id =:cityTo) " +
            "OR (d.cityFrom.id =:cityTo and d.cityTo.id =:cityFrom) and d.value =:value")
    boolean existsByFromToValue(@Param("cityFrom") Long cityFrom, @Param("cityTo") Long cityTo, @Param("value") double value);

    @Query("SELECT d from Distance d where (d.cityFrom.id =:cityFrom and d.cityTo.id =:cityTo) or (d.cityFrom.id =:cityTo and d.cityTo.id =:cityFrom)")
    Distance findByCities(@Param("cityFrom") Long cityFrom, @Param("cityTo") Long cityTo);
}
