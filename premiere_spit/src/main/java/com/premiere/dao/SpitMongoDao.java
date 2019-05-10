package com.premiere.dao;

import com.premiere.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpitMongoDao extends MongoRepository<Spit,String> {

    Page<Spit> findByParentid(String parentid, Pageable pageable);

    Spit findSpitBy_id(String _id);
}
