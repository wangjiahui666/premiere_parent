package com.premiere.controller;

import com.premiere.common.Result;
import com.premiere.common.StatusCode;
import com.premiere.pojo.CommentMongoDB;
import com.premiere.service.CommentMongoDBService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentMongoDBController {

    private final CommentMongoDBService commentMongoDBService;

    @Autowired
    private CommentMongoDBController(CommentMongoDBService commentMongoDBService) {
        this.commentMongoDBService = commentMongoDBService;
    }


    /**
     * 添加评论
     *
     * @param commentMongoDB
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody CommentMongoDB commentMongoDB) {
        if (StringUtils.isEmpty(commentMongoDB.getArticleid())) {
            return new Result(true, StatusCode.OK, "参数有误");
        }
        commentMongoDBService.add(commentMongoDB);
        return new Result(true, StatusCode.OK, "添加成功");
    }


    /**
     * 根据文章的ID查询评论的列表
     *
     * @param articleId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/article/{articleId}")
    public Result queryByArticleId(@PathVariable String articleId) {
        return new Result(true, StatusCode.OK, "查询成功", commentMongoDBService.queryByArticleId(articleId));
    }

    /**
     * 删除列表
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE,value = "/{ids}")
    public Result deleteCommentMongoDB(@PathVariable String ids) {
        commentMongoDBService.deleteCommentMongoDB(ids);
        return new Result(true, StatusCode.OK, "查询成功");
    }
}
