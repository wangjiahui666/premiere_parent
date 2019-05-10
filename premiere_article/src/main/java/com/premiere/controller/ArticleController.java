package com.premiere.controller;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.premiere.common.PageResult;
import com.premiere.common.Result;
import com.premiere.common.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.premiere.pojo.Article;
import com.premiere.service.ArticleService;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController extends ExceptionController {

    private final ArticleService articleService;
    private final HttpServletRequest request;

    @Autowired
    public ArticleController(ArticleService articleService, HttpServletRequest request) {
        this.articleService = articleService;
        this.request = request;
    }


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Article> pageList = articleService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param article
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article) {
        article.setUserid(getUserId());
        articleService.add(article);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    private String getUserId() {
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (null == claims) {
            throw new RuntimeException("无权访问");
        }
        return claims.getId();
    }

    /**
     * 修改
     *
     * @param article
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Article article, @PathVariable String id) {
        article.setId(id);
        article.setUserid(getUserId());
        articleService.update(article);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        Article byId = articleService.findById(id);
        checkedUserRights(byId.getUserid());
        articleService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    private void checkedUserRights(String userid) {
        if (!StringUtils.equals(userid, getUserId()) && null != request.getAttribute("admin_claims")) {
            throw new RuntimeException("你无权删除其他人的问题");
        }
    }

    /**
     * 点赞
     *
     * @param id id
     * @return Result
     */
    @RequestMapping(value = "/thumbup/{id}", method = RequestMethod.PUT)
    public Result thumbUp(@PathVariable String id) {
        articleService.thumbUp(id);
        return new Result(true, StatusCode.OK, "点赞成功");
    }


    /**
     * 根据频道ID获取文章列表
     *
     * @param channelId channelId
     * @param page      page
     * @param size      size
     * @return Result
     */
    @RequestMapping(value = "/channel/{channelId}/{page}/{size}", method = RequestMethod.POST)
    public Result channel(@PathVariable int channelId,
                          @PathVariable int page,
                          @PathVariable int size) {
        PageInfo<Article> pageInfo = articleService.findChannelArticle(channelId, page, size);
        return new Result(true, StatusCode.OK, "获取文章列表成功", new PageResult<>((long) pageInfo.getPageNum(), pageInfo.getList()));
    }

    /**
     * 根据专栏ID获取文章列表
     *
     * @param columnId channelId
     * @param page     page
     * @param size     size
     * @return Result
     */
    @RequestMapping(value = "/column/{columnId}/{page}/{size}", method = RequestMethod.POST)
    public Result column(@PathVariable int columnId,
                         @PathVariable int page,
                         @PathVariable int size) {
        PageInfo<Article> pageInfo = articleService.findColumnArticle(columnId, page, size);
        return new Result(true, StatusCode.OK, "获取文章列表成功", new PageResult<>((long) pageInfo.getPageNum(), pageInfo.getList()));
    }


    /**
     * 文章审核
     *
     * @param id id
     * @return Result
     */
    @RequestMapping(value = "/examine/{id}", method = RequestMethod.PUT)
    public Result examine(@PathVariable String id) {
        articleService.examineArticle(id);
        return new Result(true, StatusCode.OK, "文章审核成功");
    }

    /**
     * 头条文章
     *
     * @return Result
     */
    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public Result top() {
        return new Result(true, StatusCode.OK, "文章审核成功", articleService.findTopArticle("1"));
    }
}
