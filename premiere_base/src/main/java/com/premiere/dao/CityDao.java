package com.premiere.dao;

import com.premiere.pojo.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CityDao extends JpaRepository<City,String>, JpaSpecificationExecutor<City> {
    City findCityById(String id);
}
