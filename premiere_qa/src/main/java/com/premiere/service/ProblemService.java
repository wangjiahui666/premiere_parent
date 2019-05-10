package com.premiere.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import com.premiere.common.IdWorker;
import com.premiere.mapper.ProblemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import com.premiere.dao.ProblemDao;
import com.premiere.pojo.Problem;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
@CacheConfig(cacheNames = "problem")
public class ProblemService {

    private final ProblemDao problemDao;
    private final IdWorker idWorker;
    private final ProblemMapper problemMapper;

    @Autowired
    public ProblemService(IdWorker idWorker, ProblemDao problemDao, ProblemMapper problemMapper) {
        this.idWorker = idWorker;
        this.problemDao = problemDao;
        this.problemMapper = problemMapper;
    }

    /**
     * 查询全部列表
     *
     * @return List<Problem>
     */
    public List<Problem> findAll() {
        return problemDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap whereMap
     * @param page     page
     * @param size     size
     * @return Page<Problem>
     */
    public Page<Problem> findSearch(Map whereMap, int page, int size) {
        Specification<Problem> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap whereMap
     * @return List<Problem>
     */
    public List<Problem> findSearch(Map whereMap) {
        Specification<Problem> specification = createSpecification(whereMap);
        return problemDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id id
     * @return Problem
     */
    @Cacheable(value = "Problem", key = "#id")
    public Problem findById(String id) {
        return problemMapper.selectByPrimaryKey(id);
    }

    /**
     * 增加
     *
     * @param problem problem
     */
    public void add(Problem problem) {
        problem.setId(idWorker.nextId() + "");
        problemDao.save(problem);
    }

    /**
     * 修改
     *
     * @param problem problem
     */
    @CacheEvict(value = "Problem", key = "#problem.id")
    public void update(Problem problem) {
        problemDao.save(problem);
    }

    /**
     * 删除
     *
     * @param id id
     */
    @CacheEvict(value = "Problem", key = "#id")
    public void deleteById(String id) {
        problemDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap searchMap
     * @return Specification<Problem>
     */
    private Specification<Problem> createSpecification(Map searchMap) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 标题
            if (searchMap.get("title") != null && !"".equals(searchMap.get("title"))) {
                predicateList.add(cb.like(root.get("title").as(String.class), "%" + searchMap.get("title") + "%"));
            }
            // 内容
            if (searchMap.get("content") != null && !"".equals(searchMap.get("content"))) {
                predicateList.add(cb.like(root.get("content").as(String.class), "%" + searchMap.get("content") + "%"));
            }
            // 用户ID
            if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                predicateList.add(cb.like(root.get("userid").as(String.class), "%" + searchMap.get("userid") + "%"));
            }
            // 昵称
            if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + searchMap.get("nickname") + "%"));
            }
            // 是否解决
            if (searchMap.get("solve") != null && !"".equals(searchMap.get("solve"))) {
                predicateList.add(cb.like(root.get("solve").as(String.class), "%" + searchMap.get("solve") + "%"));
            }
            // 回复人昵称
            if (searchMap.get("replyname") != null && !"".equals(searchMap.get("replyname"))) {
                predicateList.add(cb.like(root.get("replyname").as(String.class), "%" + searchMap.get("replyname") + "%"));
            }

            int size = predicateList.size();
            return cb.and(predicateList.toArray(new Predicate[size]));

        };

    }

    public Page<Problem> findProblemWaitlistByLabel(String label, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.waitlist(label, pageRequest);
    }

    public Page<Problem> findProblemNewlistByLabel(String label, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.newlist(label, pageRequest);
    }

    public Page<Problem> findProblemHotlistByLabel(String label, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.hotlist(label, pageRequest);
    }

    public Page<Problem> findProblemAllByLabel(String label, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.All(label, pageRequest);
    }
}
