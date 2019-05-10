package com.premiere.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.premiere.mapper.ArticleMapper;
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
import com.premiere.dao.ArticleDao;
import com.premiere.pojo.Article;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
@CacheConfig(cacheNames = "article")
public class ArticleService {

    private final ArticleDao articleDao;
    private final IdWorker idWorker;
    private final ArticleMapper articleMapper;
    @Autowired
    public ArticleService(ArticleDao articleDao, IdWorker idWorker, ArticleMapper articleMapper) {
        this.articleDao = articleDao;
        this.idWorker = idWorker;
        this.articleMapper = articleMapper;
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Article> findAll() {
        return articleDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findSearch(Map whereMap, Integer page, Integer size) {
        Specification<Article> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return articleDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Article> findSearch(Map whereMap) {
        Specification<Article> specification = createSpecification(whereMap);
        return articleDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    @Cacheable(value = "Article", key = "#id")
    public Article findById(String id) {
        return articleDao.findOneById(id);
    }

    /**
     * 增加
     *
     * @param article
     */
    public void add(Article article) {
        article.setId(idWorker.nextId() + "");
        articleDao.save(article);
    }

    /**
     * 修改
     *
     * @param article
     */
    @CacheEvict(value = "Article", key = "#article.id")
    public void update(Article article) {
        articleDao.save(article);
    }

    /**
     * 删除
     *
     * @param id
     */
    @CacheEvict(value = "Article", key = "#id")
    public void deleteById(String id) {
        articleDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Article> createSpecification(Map searchMap) {

        return (Specification<Article>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 专栏ID
            if (searchMap.get("columnid") != null && !"".equals(searchMap.get("columnid"))) {
                predicateList.add(cb.like(root.get("columnid").as(String.class), "%" + searchMap.get("columnid") + "%"));
            }
            // 用户ID
            if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                predicateList.add(cb.like(root.get("userid").as(String.class), "%" + searchMap.get("userid") + "%"));
            }
            // 标题
            if (searchMap.get("title") != null && !"".equals(searchMap.get("title"))) {
                predicateList.add(cb.like(root.get("title").as(String.class), "%" + searchMap.get("title") + "%"));
            }
            // 文章正文
            if (searchMap.get("content") != null && !"".equals(searchMap.get("content"))) {
                predicateList.add(cb.like(root.get("content").as(String.class), "%" + searchMap.get("content") + "%"));
            }
            // 文章封面
            if (searchMap.get("image") != null && !"".equals(searchMap.get("image"))) {
                predicateList.add(cb.like(root.get("image").as(String.class), "%" + searchMap.get("image") + "%"));
            }
            // 是否公开
            if (searchMap.get("ispublic") != null && !"".equals(searchMap.get("ispublic"))) {
                predicateList.add(cb.like(root.get("ispublic").as(String.class), "%" + searchMap.get("ispublic") + "%"));
            }
            // 是否置顶
            if (searchMap.get("istop") != null && !"".equals(searchMap.get("istop"))) {
                predicateList.add(cb.like(root.get("istop").as(String.class), "%" + searchMap.get("istop") + "%"));
            }
            // 审核状态
            if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%" + searchMap.get("state") + "%"));
            }
            // 所属频道
            if (searchMap.get("channelid") != null && !"".equals(searchMap.get("channelid"))) {
                predicateList.add(cb.like(root.get("channelid").as(String.class), "%" + searchMap.get("channelid") + "%"));
            }
            // URL
            if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
                predicateList.add(cb.like(root.get("url").as(String.class), "%" + searchMap.get("url") + "%"));
            }
            // 类型
            if (searchMap.get("type") != null && !"".equals(searchMap.get("type"))) {
                predicateList.add(cb.like(root.get("type").as(String.class), "%" + searchMap.get("type") + "%"));
            }

            int size = predicateList.size();
            return cb.and(predicateList.toArray(new Predicate[size]));

        };

    }

    public void thumbUp(String id) {
        articleMapper.thumbUp(id);
    }

    public PageInfo<Article> findChannelArticle(Integer channelId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Article> list = articleMapper.findChannelArticle(channelId);
        return new PageInfo<>(list);
    }

    public PageInfo<Article> findColumnArticle(Integer columnId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Article> list = articleMapper.findColumnArticle(columnId);
        return new PageInfo<>(list);
    }

    public List<Article> findTopArticle(String istop) {
        return articleMapper.findTopArticle(istop);
    }

    /**
     * 文章审核
     *
     * @param id
     */
    public void examineArticle(String id) {
        articleMapper.examineArticle(id);
    }
}
