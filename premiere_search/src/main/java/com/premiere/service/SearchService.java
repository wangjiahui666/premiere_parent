package com.premiere.service;

import com.premiere.common.IdWorker;
import com.premiere.dao.SearchDao;
import com.premiere.pojo.ArticleEs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SearchService {

    private final SearchDao searchDao;
    private final IdWorker idWorker;


    @Autowired
    public SearchService(SearchDao searchDao, IdWorker idWorker) {
        this.searchDao = searchDao;
        this.idWorker = idWorker;
    }

    public void add(ArticleEs articleEs) {
        articleEs.setId(String.valueOf(idWorker.nextId()));
        searchDao.save(articleEs);
    }

    public Iterable<ArticleEs> findAll() {
        return searchDao.findAll();
    }

    public Page<ArticleEs> searchArticleEs(String keywords, Integer page, Integer size) {
        PageRequest of = PageRequest.of(page - 1, size);
        return searchDao.findAllByTitleLikeOrContentLike(keywords, keywords, of);
    }
}
