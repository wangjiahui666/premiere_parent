package com.premiere.controller;

import com.premiere.common.PageResult;
import com.premiere.common.Result;
import com.premiere.common.StatusCode;
import com.premiere.pojo.ArticleEs;
import com.premiere.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/search")
public class SearchController extends ExceptionController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody ArticleEs articleEs) {
        searchService.add(articleEs);
        return new Result(true, StatusCode.OK, "添加成功");

    }

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", searchService.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search/{keywords}/{page}/{size}")
    public Result searchArticleEs(@PathVariable String keywords,
                      @PathVariable Integer page,
                      @PathVariable Integer size) {
        Page<ArticleEs> pages=searchService.searchArticleEs(keywords,page,size);
        return new Result(true, StatusCode.OK, "查询成功",new PageResult<>(pages.getTotalElements(),pages.getContent()));
    }
}
