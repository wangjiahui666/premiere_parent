package com.premiere.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import com.premiere.common.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import com.premiere.dao.RecruitDao;
import com.premiere.pojo.Recruit;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
@CacheConfig(cacheNames = "recruit")
public class RecruitService {

    private final RecruitDao recruitDao;
    private final IdWorker idWorker;

    @Autowired
    public RecruitService(RecruitDao recruitDao, IdWorker idWorker) {
        this.recruitDao = recruitDao;
        this.idWorker = idWorker;
    }

    public List<Recruit> findAll() {

        return recruitDao.findAll();
    }

    public Page<Recruit> findPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return recruitDao.findAll(pageRequest);
    }

    private Specification<Recruit> where(Map searchMap) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 职位名称
            if (searchMap.get("jobname") != null && !"".equals(searchMap.get("jobname"))) {
                predicateList.add(cb.like(root.get("jobname").as(String.class), "%" + searchMap.get("jobname") + "%"));
            }
            // 薪资范围
            if (searchMap.get("salary") != null && !"".equals(searchMap.get("salary"))) {
                predicateList.add(cb.like(root.get("salary").as(String.class), "%" + searchMap.get("salary") + "%"));
            }
            // 经验要求
            if (searchMap.get("condition") != null && !"".equals(searchMap.get("condition"))) {
                predicateList.add(cb.like(root.get("condition").as(String.class), "%" + searchMap.get("condition") + "%"));
            }
            // 学历要求
            if (searchMap.get("education") != null && !"".equals(searchMap.get("education"))) {
                predicateList.add(cb.like(root.get("education").as(String.class), "%" + searchMap.get("education") + "%"));
            }
            // 任职方式
            if (searchMap.get("type") != null && !"".equals(searchMap.get("type"))) {
                predicateList.add(cb.like(root.get("type").as(String.class), "%" + searchMap.get("type") + "%"));
            }
            // 办公地址
            if (searchMap.get("address") != null && !"".equals(searchMap.get("address"))) {
                predicateList.add(cb.like(root.get("address").as(String.class), "%" + searchMap.get("address") + "%"));
            }
            // 企业ID
            if (searchMap.get("eid") != null && !"".equals(searchMap.get("eid"))) {
                predicateList.add(cb.like(root.get("eid").as(String.class), "%" + searchMap.get("eid") + "%"));
            }
            // 状态
            if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%" + searchMap.get("state") + "%"));
            }
            // 网址
            if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
                predicateList.add(cb.like(root.get("url").as(String.class), "%" + searchMap.get("url") + "%"));
            }
            // 标签
            if (searchMap.get("label") != null && !"".equals(searchMap.get("label"))) {
                predicateList.add(cb.like(root.get("label").as(String.class), "%" + searchMap.get("label") + "%"));
            }
            // 职位描述
            if (searchMap.get("content1") != null && !"".equals(searchMap.get("content1"))) {
                predicateList.add(cb.like(root.get("content1").as(String.class), "%" + searchMap.get("content1") + "%"));
            }
            // 职位要求
            if (searchMap.get("content2") != null && !"".equals(searchMap.get("content2"))) {
                predicateList.add(cb.like(root.get("content2").as(String.class), "%" + searchMap.get("content2") + "%"));
            }
            int size = predicateList.size();
            return cb.and(predicateList.toArray(new Predicate[size]));

        };

    }

    public Page<Recruit> findSearch(Map whereMap, int page, int size) {
        Specification<Recruit> specification = where(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return recruitDao.findAll(specification, pageRequest);
    }

    @Cacheable(value = "Recruit", key = "#id")
    public Recruit findOne(String id) {
        return recruitDao.findRecruitById(id);
    }

    public void add(Recruit recruit) {
        recruit.setId(String.valueOf(idWorker.nextId())); //主键值
        recruitDao.save(recruit);
    }

    @CacheEvict(value = "Recruit", key = "#recruit.id")
    public void update(Recruit recruit) {
        recruitDao.save(recruit);
    }

    @CacheEvict(value = "Recruit", key = "#id")
    public void delete(String id) {
        recruitDao.deleteById(id);
    }

    public List<Recruit> findAllByState(String state) {
        return recruitDao.findAllByState(state);
    }

    public List<Recruit> searchRecruit(Map searchMap) {
        return recruitDao.findAll(this.where(searchMap));
    }
}
