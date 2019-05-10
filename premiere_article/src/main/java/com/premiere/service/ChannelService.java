package com.premiere.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

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
import com.premiere.dao.ChannelDao;
import com.premiere.pojo.Channel;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
@CacheConfig(cacheNames = "channel")
public class ChannelService {

    private final ChannelDao channelDao;
    private final IdWorker idWorker;

    @Autowired
    public ChannelService(IdWorker idWorker, ChannelDao channelDao) {
        this.idWorker = idWorker;
        this.channelDao = channelDao;
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Channel> findAll() {
        return channelDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Channel> findSearch(Map whereMap, int page, int size) {
        Specification<Channel> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return channelDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Channel> findSearch(Map whereMap) {
        Specification<Channel> specification = createSpecification(whereMap);
        return channelDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    @Cacheable(value = "Channel", key = "#id")
    public Channel findById(String id) {
        return channelDao.findOneById(id);
    }

    /**
     * 增加
     *
     * @param channel
     */
    public void add(Channel channel) {
        channel.setId(idWorker.nextId() + "");
        channelDao.save(channel);
    }

    /**
     * 修改
     *
     * @param channel
     */
    @CacheEvict(value = "Channel", key = "#channel.id")
    public void update(Channel channel) {
        channelDao.save(channel);
    }

    /**
     * 删除
     *
     * @param id
     */
    @CacheEvict(value = "Channel", key = "#id")
    public void deleteById(String id) {
        channelDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Channel> createSpecification(Map searchMap) {

        return (Specification<Channel>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 频道名称
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                predicateList.add(cb.like(root.get("name").as(String.class), "%" + searchMap.get("name") + "%"));
            }
            // 状态
            if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%" + searchMap.get("state") + "%"));
            }

            int size = predicateList.size();
            return cb.and(predicateList.toArray(new Predicate[size]));

        };

    }

}
