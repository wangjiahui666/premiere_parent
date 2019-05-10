package com.premiere.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import com.premiere.mapper.ColumnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.premiere.common.IdWorker;
import org.springframework.transaction.annotation.Transactional;
import com.premiere.dao.ColumnDao;
import com.premiere.pojo.Column;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
@CacheConfig(cacheNames = "column")
public class ColumnService {

    private final ColumnDao columnDao;
    private final IdWorker idWorker;
    private final ColumnMapper columnMapper;

    @Autowired
    public ColumnService(ColumnDao columnDao, IdWorker idWorker, ColumnMapper columnMapper) {
        this.columnDao = columnDao;
        this.idWorker = idWorker;
        this.columnMapper = columnMapper;
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Column> findAll() {
        return columnDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Column> findSearch(Map whereMap, int page, int size) {
        Specification<Column> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return columnDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Column> findSearch(Map whereMap) {
        Specification<Column> specification = createSpecification(whereMap);
        return columnDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    @Cacheable(value = "Column",key = "#id")
    public Column findById(String id) {
        return columnDao.findOneById(id);
    }

    /**
     * 增加
     *
     * @param column
     */
    public void add(Column column) {
        column.setId(idWorker.nextId() + "");
        columnDao.save(column);
    }

    /**
     * 修改
     *
     * @param column
     */
    @CacheEvict(value = "Column",key = "#column.id")
    public void update(Column column) {
        columnDao.save(column);
    }

    /**
     * 删除
     *
     * @param id
     */
    @CacheEvict(value = "Column",key = "#id")
    public void deleteById(String id) {
        columnDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Column> createSpecification(Map searchMap) {

        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 专栏名称
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                predicateList.add(cb.like(root.get("name").as(String.class), "%" + searchMap.get("name") + "%"));
            }
            // 专栏简介
            if (searchMap.get("summary") != null && !"".equals(searchMap.get("summary"))) {
                predicateList.add(cb.like(root.get("summary").as(String.class), "%" + searchMap.get("summary") + "%"));
            }
            // 用户ID
            if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                predicateList.add(cb.like(root.get("userid").as(String.class), "%" + searchMap.get("userid") + "%"));
            }
            // 状态
            if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%" + searchMap.get("state") + "%"));
            }
            int size = predicateList.size();
            return cb.and(predicateList.toArray(new Predicate[size]));

        };

    }

    public void examineColumn(String columnid) {
        columnMapper.examineColumn(columnid);
    }

    public List<Column> findColumnByUserid(String userid) {
        return columnDao.findColumnByUserid(userid);
    }
}
