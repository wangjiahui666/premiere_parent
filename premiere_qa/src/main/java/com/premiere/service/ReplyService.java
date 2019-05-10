package com.premiere.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import com.premiere.common.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import com.premiere.dao.ReplyDao;
import com.premiere.pojo.Reply;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
@CacheConfig(cacheNames = "reply")
public class ReplyService {

    private final ReplyDao replyDao;
    private final IdWorker idWorker;

    @Autowired
    public ReplyService(ReplyDao replyDao, IdWorker idWorker) {
        this.replyDao = replyDao;
        this.idWorker = idWorker;
    }

    /**
     * 查询全部列表
     *
     * @return List<Reply>
     */
    //@Cacheable(cacheNames = "ReplyList",key = "ReplyfindAll")
    public List<Reply> findAll() {
        return replyDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Reply> findSearch(Map whereMap, int page, int size) {
        Specification<Reply> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return replyDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Reply> findSearch(Map whereMap) {
        Specification<Reply> specification = createSpecification(whereMap);
        return replyDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    @Cacheable(value = "Reply", key = "#id")
    public Reply findById(String id) {
        return replyDao.findOneById(id);
    }

    /**
     * 增加
     *
     * @param reply
     */
    public void add(Reply reply) {
        reply.setId(idWorker.nextId() + "");
        replyDao.save(reply);
    }

    /**
     * 修改
     *
     * @param reply
     */
    @CacheEvict(value = "Reply", key = "#reply.id")
    public void update(Reply reply) {
        replyDao.save(reply);
    }

    /**
     * 删除
     *
     * @param id
     */
    @CacheEvict(value = "Reply", key = "Reply_#id")
    public void deleteById(String id) {
        replyDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Reply> createSpecification(Map searchMap) {

        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // 编号
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 问题ID
            if (searchMap.get("problemid") != null && !"".equals(searchMap.get("problemid"))) {
                predicateList.add(cb.like(root.get("problemid").as(String.class), "%" + searchMap.get("problemid") + "%"));
            }
            // 回答内容
            if (searchMap.get("content") != null && !"".equals(searchMap.get("content"))) {
                predicateList.add(cb.like(root.get("content").as(String.class), "%" + searchMap.get("content") + "%"));
            }
            // 回答人ID
            if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                predicateList.add(cb.like(root.get("userid").as(String.class), "%" + searchMap.get("userid") + "%"));
            }
            // 回答人昵称
            if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + searchMap.get("nickname") + "%"));
            }

            int size = predicateList.size();
            return cb.and(predicateList.toArray(new Predicate[size]));

        };

    }

    public List<Reply> findReplyByProblemId(String id) {
        return replyDao.findReplyByProblemid(id);
    }
}
