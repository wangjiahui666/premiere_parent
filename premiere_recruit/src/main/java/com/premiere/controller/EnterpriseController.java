package com.premiere.controller;

import java.util.Map;

import com.premiere.common.PageResult;
import com.premiere.common.Result;
import com.premiere.common.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.premiere.pojo.Enterprise;
import com.premiere.service.EnterpriseService;

@RestController
@RequestMapping("/enterprise")
@CrossOrigin
public class EnterpriseController extends ExceptionController {
    private final EnterpriseService enterpriseService;

    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    /**
     * 查询全部数据
     *
     * @return List<Enterprise>
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "成功", enterpriseService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return Enterprise
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findOne(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "成功", enterpriseService.findOne(id));
    }

    /**
     * 分页查询全部数据
     *
     * @param page 页码
     * @param size 页大小
     * @return PageResult
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.GET)
    public Result findPage(@PathVariable int page, @PathVariable int size) {
        Page<Enterprise> pageList = enterpriseService.findPage(page, size);
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
        Page<Enterprise> pageList = enterpriseService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result searchByEnterprise(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "成功",enterpriseService.searchByEnterprise(searchMap));
    }

    /**
     * 增加
     *
     * @param enterprise 类
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Enterprise enterprise) {
        enterpriseService.add(enterprise);
        return new Result(true, StatusCode.OK, "成功");
    }

    /**
     * 修改
     *
     * @param enterprise 类
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Enterprise enterprise, @PathVariable String id) {
        enterprise.setId(id);
        enterpriseService.update(enterprise);
        return new Result(true, StatusCode.OK, "成功");
    }

    /**
     * 删除
     *
     * @param id 类的id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        enterpriseService.delete(id);
        return new Result(true, StatusCode.OK, "成功");
    }


    @RequestMapping(value = "search/hotlist", method = RequestMethod.DELETE)
    public Result hotlist() {
        return new Result(true, StatusCode.OK, "成功",enterpriseService.findEnterpriseByIshot("1"));
    }

}
