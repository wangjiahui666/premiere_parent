package com.premiere.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.premiere.pojo.Column;
import com.premiere.service.ColumnService;

import com.premiere.common.PageResult;
import com.premiere.common.Result;
import com.premiere.common.StatusCode;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/column")
public class ColumnController extends ExceptionController{

    private final ColumnService columnService;

    @Autowired
    public ColumnController(ColumnService columnService) {
        this.columnService = columnService;
    }


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", columnService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", columnService.findById(id));
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
        Page<Column> pageList = columnService.findSearch(searchMap, page, size);
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
        return new Result(true, StatusCode.OK, "查询成功", columnService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param column
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Column column) {
        columnService.add(column);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param column
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Column column, @PathVariable String id) {
        column.setId(id);
        columnService.update(column);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        columnService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }


    /**
     * 专栏申请
     * @param column column
     * @return Result
     */
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Result apply(@RequestBody Column column) {
        columnService.add(column);
        return new Result(true, StatusCode.OK, "专栏申请成功");
    }

    /**
     * 申请审核
     * @param columnid columnid
     * @return Result
     */
    @RequestMapping(value = "/examine/{columnid}", method = RequestMethod.PUT)
    public Result examineColumn(@PathVariable String columnid) {
        columnService.examineColumn(columnid);
        return new Result(true, StatusCode.OK, "专栏申请成功");
    }


    /**
     * 根据用户ID查询专栏列表
     * @param userid userid
     * @return Result
     */
    @RequestMapping(value = "/user/{userid}", method = RequestMethod.GET)
    public Result findColumnByUserid(@PathVariable String userid) {
        return new Result(true, StatusCode.OK, "查询专栏列表成功",columnService.findColumnByUserid(userid));
    }


}
