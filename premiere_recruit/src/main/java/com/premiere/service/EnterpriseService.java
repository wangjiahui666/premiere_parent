package com.premiere.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import com.premiere.common.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import com.premiere.dao.EnterpriseDao;
import com.premiere.pojo.Enterprise;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
@CacheConfig(cacheNames = "enterprise")
public class EnterpriseService {

    private final EnterpriseDao enterpriseDao;
    private final IdWorker idWorker;

    @Autowired
    public EnterpriseService(EnterpriseDao enterpriseDao, IdWorker idWorker) {
        this.enterpriseDao = enterpriseDao;
        this.idWorker = idWorker;
    }

    public List<Enterprise> findAll() {
        return enterpriseDao.findAll();
    }

    public Page<Enterprise> findPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return enterpriseDao.findAll(pageRequest);
    }

    private Specification<Enterprise> where(Map searchMap) {

        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 企业名称
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                predicateList.add(cb.like(root.get("name").as(String.class), "%" + searchMap.get("name") + "%"));
            }
            // 企业简介
            if (searchMap.get("summary") != null && !"".equals(searchMap.get("summary"))) {
                predicateList.add(cb.like(root.get("summary").as(String.class), "%" + searchMap.get("summary") + "%"));
            }
            // 企业地址
            if (searchMap.get("address") != null && !"".equals(searchMap.get("address"))) {
                predicateList.add(cb.like(root.get("address").as(String.class), "%" + searchMap.get("address") + "%"));
            }
            // 标签列表
            if (searchMap.get("labels") != null && !"".equals(searchMap.get("labels"))) {
                predicateList.add(cb.like(root.get("labels").as(String.class), "%" + searchMap.get("labels") + "%"));
            }
            // 坐标
            if (searchMap.get("coordinate") != null && !"".equals(searchMap.get("coordinate"))) {
                predicateList.add(cb.like(root.get("coordinate").as(String.class), "%" + searchMap.get("coordinate") + "%"));
            }
            // 是否热门
            if (searchMap.get("ishot") != null && !"".equals(searchMap.get("ishot"))) {
                predicateList.add(cb.like(root.get("ishot").as(String.class), "%" + searchMap.get("ishot") + "%"));
            }
            // LOGO
            if (searchMap.get("logo") != null && !"".equals(searchMap.get("logo"))) {
                predicateList.add(cb.like(root.get("logo").as(String.class), "%" + searchMap.get("logo") + "%"));
            }
            // URL
            if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
                predicateList.add(cb.like(root.get("url").as(String.class), "%" + searchMap.get("url") + "%"));
            }

            int size = predicateList.size();
            return cb.and(predicateList.toArray(new Predicate[size]));

        };

    }

    public Page<Enterprise> findSearch(Map whereMap, int page, int size) {
        Specification<Enterprise> specification = where(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return enterpriseDao.findAll(specification, pageRequest);
    }

    @Cacheable(value = "Enterprise", key = "#id")
    public Enterprise findOne(String id) {
        return enterpriseDao.findEnterpriseById(id);
    }

    public void add(Enterprise enterprise) {
        enterprise.setId(idWorker.nextId() + ""); //主键值
        enterpriseDao.save(enterprise);
    }

    @Cacheable(value = "Enterprise", key = "#enterprise.id")
    public void update(Enterprise enterprise) {
        enterpriseDao.save(enterprise);
    }

    @Cacheable(value = "Enterprise", key = "#id")
    public void delete(String id) {
        enterpriseDao.deleteById(id);
    }

    public List<Enterprise> findEnterpriseByIshot(String ishst) {
        return enterpriseDao.findEnterpriseByIshot(ishst);
    }

    public List<Enterprise> searchByEnterprise(Map searchMap) {
        return enterpriseDao.findAll(this.where(searchMap));
    }
}
