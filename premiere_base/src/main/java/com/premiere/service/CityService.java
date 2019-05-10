package com.premiere.service;

import com.premiere.common.IdWorker;
import com.premiere.dao.CityDao;
import com.premiere.mapper.CityMapper;
import com.premiere.pojo.City;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@CacheConfig(cacheNames = "city")
public class CityService {

    private final CityMapper cityMapper;
    private final CityDao cityDao;
    private final IdWorker idWorker;

    @Autowired
    public CityService(CityMapper cityMapper, CityDao cityDao, IdWorker idWorker) {
        this.cityMapper = cityMapper;
        this.cityDao = cityDao;
        this.idWorker = idWorker;
    }

    public void save(City City) {
        if (StringUtils.isEmpty(City.getId())) {
            City.setId(String.valueOf(idWorker.nextId()));
        }
        cityDao.save(City);
    }

    public List<City> findAll() {
        return cityDao.findAll();
    }

    @Cacheable(value = "City", key = "#id")
    public City findCityById(String id) {
        return cityDao.findCityById(id);
    }

    @CacheEvict(value = "City", key = "#id")
    public void deleteCityById(String id) {
        cityMapper.deleteByPrimaryKey(id);
    }

    public Page<City> search(City city, Integer page, Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        return cityDao.findAll(this.getCitySpecification(city), request);
    }

    public List<City> searchByCity(City city) {
        return cityDao.findAll(this.getCitySpecification(city));
    }

    private Specification<City> getCitySpecification(City city) {
        return (Specification<City>) (root, criteriaQuery, cb) -> {
            List<Predicate> pc = new ArrayList<>();
            if (null == city) return null;

            if (StringUtils.isNotEmpty(city.getName())) {
                pc.add(cb.like(root.get("name").as(String.class), "%" + city.getName() + "%"));
            }
            if (StringUtils.isNotEmpty(city.getId())) {
                pc.add(cb.equal(root.get("id").as(String.class), city.getId()));
            }
            if (StringUtils.isNotEmpty(city.getIshot())) {
                pc.add(cb.equal(root.get("ishost").as(String.class), city.getIshot()));
            }
            if (pc.size() == 0) return null;
            int size = pc.size();
            return cb.and(pc.toArray(new Predicate[size]));
        };
    }
}
