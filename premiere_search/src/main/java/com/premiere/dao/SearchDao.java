package com.premiere.dao;

import com.premiere.pojo.ArticleEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface SearchDao extends ElasticsearchRepository<ArticleEs,String> {

    Page<ArticleEs> findAllByTitleLikeOrContentLike(String title,String content, Pageable pageable);

}
