package com.premiere.dao;

import com.premiere.pojo.CommentMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentMongoDBDao extends MongoRepository<CommentMongoDB,String> {

    List<CommentMongoDB> findByArticleidOrderByPublishdateDesc(String articleid);

}
