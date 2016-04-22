package com.appmagenta.repositories;

import com.appmagenta.entities.Distance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DistanceRepository extends CrudRepository<Distance, Long>{
}
