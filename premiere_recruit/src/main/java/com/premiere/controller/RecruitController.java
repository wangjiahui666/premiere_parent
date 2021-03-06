package com.premiere.controller;

import java.util.Map;

import com.premiere.common.PageResult;
import com.premiere.common.Result;
import com.premiere.common.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.premiere.pojo.Recruit;
import com.premiere.service.RecruitService;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/recruit")
@CrossOrigin
public class RecruitController extends ExceptionController {

    private final RecruitService recruitService;

    @Autowired
    public RecruitController(RecruitService recruitService) {
        this.recruitService = recruitService;
    }

    /**
     * 查询全部数据
     *
     * @return List
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "成功", recruitService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return Recruit
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findOne(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "成功", recruitService.findOne(id));
    }

    /**
     * 分页查询全部数据
     *
     * @param page 页码
     * @param size 页大小
     * @return PageResult
     */
    @RequestMapping(value = "/{page}/{size}", method = RequestMethod.GET)
    public Result findPage(@PathVariable int page, @PathVariable int size) {
        Page<Recruit> pageList = recruitService.findPage(page, size);
        return new Result(true, StatusCode.OK, "成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
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
        Page<Recruit> pageList = recruitService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result searchRecruit(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "", recruitService.searchRecruit(searchMap));
    }

    /**
     * 增加
     *
     * @param recruit 类
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Recruit recruit) {
        recruitService.add(recruit);
        return new Result(true, StatusCode.OK, "成功");
    }

    /**
     * 修改
     *
     * @param recruit 类
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Recruit recruit, @PathVariable String id) {
        recruit.setId(id);
        recruitService.update(recruit);
        return new Result(true, StatusCode.OK, "成功");
    }

    /**
     * 删除
     *
     * @param id 类的ID
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        recruitService.delete(id);
        return new Result(true, StatusCode.OK, "成功");
    }

    @RequestMapping(value = "/search/recommend", method = RequestMethod.GET)
    public Result recommend() {
        return new Result(true, StatusCode.OK, "", recruitService.findAllByState("1"));
    }

    @RequestMapping(value = "/search/newlist", method = RequestMethod.GET)
    public Result newlist() {
        return new Result(true, StatusCode.OK, "", recruitService.findAllByState("1"));
    }


}
