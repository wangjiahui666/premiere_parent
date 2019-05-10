package com.premiere.service;

import com.premiere.common.IdWorker;
import com.premiere.dao.CommentMongoDBDao;
import com.premiere.pojo.CommentMongoDB;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CommentMongoDBService {

    private final CommentMongoDBDao commentMongoDBDao;
    private final IdWorker idWorker;

    @Autowired
    public CommentMongoDBService(CommentMongoDBDao commentMongoDBDao, IdWorker idWorker) {
        this.commentMongoDBDao = commentMongoDBDao;
        this.idWorker = idWorker;
    }

    public void add(CommentMongoDB commentMongoDB) {
        commentMongoDB.set_id(String.valueOf(idWorker.nextId()));
        commentMongoDB.setPublishdate(new Date());
        commentMongoDBDao.save(commentMongoDB);
    }

    public List<CommentMongoDB> queryByArticleId(String articleId) {
        return commentMongoDBDao.findByArticleidOrderByPublishdateDesc(articleId);
    }

    public void deleteCommentMongoDB(String ids) {
        if (StringUtils.isNotEmpty(ids)){
            String[] split = ids.split("_");
            for (String id : split) {
                commentMongoDBDao.deleteById(id);
            }
        }
    }
}
