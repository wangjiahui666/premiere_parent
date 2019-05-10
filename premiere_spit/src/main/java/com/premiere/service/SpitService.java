package com.premiere.service;

import com.premiere.common.IdWorker;
import com.premiere.config.StringRedisUtil;
import com.premiere.dao.SpitMongoDao;
import com.premiere.pojo.Spit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SpitService {
    private final SpitMongoDao spitMongoDao;
    private final IdWorker idWorker;
    private final MongoTemplate mongoTemplate;
    private final StringRedisUtil stringRedisUtil;

    @Autowired
    public SpitService(SpitMongoDao spitMongoDao, IdWorker idWorker, MongoTemplate mongoTemplate, StringRedisUtil stringRedisUtil) {
        this.spitMongoDao = spitMongoDao;
        this.idWorker = idWorker;
        this.mongoTemplate = mongoTemplate;
        this.stringRedisUtil = stringRedisUtil;
    }


    public List<Spit> findAll() {
        return spitMongoDao.findAll();
    }

    public Spit findById(String id) {
        this.mongoUpdate("visits",id);
        return spitMongoDao.findSpitBy_id(id);
    }

    public void insert(Spit spit) {
        spit.set_id(String.valueOf(idWorker.nextId()));
        spit.setPublishtime(new Date());
        spit.setVisits(0);
        spit.setShare(0);
        spit.setComment(0);
        spit.setState("1");
        if (StringUtils.isNotEmpty(spit.getParentid())) {
            this.mongoUpdate("comment", spit.getParentid());
        }
        spitMongoDao.save(spit);
    }

    public void update(Spit spit) {
        spitMongoDao.save(spit);
    }

    public void delete(String id) {
        spitMongoDao.deleteById(id);
    }

    public Page<Spit> findByParentIdList(String parentId, Integer page, Integer size) {
        PageRequest of = PageRequest.of(page - 1, size);
        return spitMongoDao.findByParentid(parentId, of);
    }

    public Page<Spit> searchSpit(Integer page, Integer size) {
        PageRequest of = PageRequest.of(page - 1, size);
        return spitMongoDao.findAll(of);
    }

    public boolean thumbupSpit(String spitId) {
        String userId = "123";
        StringBuilder thumbId = this.getThumbId(spitId, userId);
        if (null == stringRedisUtil.get(String.valueOf(thumbId))) {
            stringRedisUtil.set(String.valueOf(thumbId), spitId);
        } else {
            return false;
        }
        return this.mongoUpdate("thumbup", spitId);
    }

    public List<Spit> searchBySpit(Spit spit) {
        return mongoTemplate.find(this.getSpitQuery(spit), Spit.class);
    }


    /**
     * 返回 吐槽ID 和 用户ID 组成的主键
     *
     * @param spitId
     * @param userid
     * @return
     */
    private StringBuilder getThumbId(String spitId, String userid) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("thumbUp_");
        stringBuilder.append(spitId);
        stringBuilder.append("_");
        stringBuilder.append(userid);
        return stringBuilder;
    }


    /**
     * 返回 Spit MongoDB模糊查询条件
     *
     * @param spit
     * @return Query
     */
    private Query getSpitQuery(Spit spit) {
        Query query = new Query();
        if (StringUtils.isNotEmpty(spit.get_id()))
            query.addCriteria(Criteria.where("_id").is(spit.get_id()));

        if (StringUtils.isNotEmpty(spit.getContent()))
            query.addCriteria(Criteria.where("content").is(spit.getContent()));

        if (null != spit.getPublishtime())
            query.addCriteria(Criteria.where("publishtime").is(spit.getPublishtime()));

        if (StringUtils.isNotEmpty(spit.getUserid()))
            query.addCriteria(Criteria.where("userid").is(spit.getUserid()));

        if (StringUtils.isNotEmpty(spit.getNickname()))
            query.addCriteria(Criteria.where("nickname").is(spit.getNickname()));

        if (null != spit.getVisits() && 0 != spit.getVisits())
            query.addCriteria(Criteria.where("visits").is(spit.getVisits()));

        if (null != spit.getThumbup() && 0 != spit.getThumbup())
            query.addCriteria(Criteria.where("thumbup").is(spit.getThumbup()));

        if (StringUtils.isNotEmpty(spit.getState()))
            query.addCriteria(Criteria.where("share").is(spit.getState()));

        if (null != spit.getComment() && 0 != spit.getComment())
            query.addCriteria(Criteria.where("comment").is(spit.getComment()));

        if (StringUtils.isNotEmpty(spit.getState()))
            query.addCriteria(Criteria.where("state").is(spit.getState()));

        if (StringUtils.isNotEmpty(spit.getParentid()))
            query.addCriteria(Criteria.where("parentid").is(spit.getParentid()));
        return query;
    }

    private boolean mongoUpdate(String keys, String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc(keys, 1);
        mongoTemplate.updateFirst(query, update, Spit.class);
        return true;
    }

    public void shareSpit(String spitId) {
        this.mongoUpdate("share",spitId);
    }
}
