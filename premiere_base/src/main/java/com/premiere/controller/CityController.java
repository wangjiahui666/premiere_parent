package com.premiere.controller;

import com.premiere.common.PageResult;
import com.premiere.common.Result;
import com.premiere.common.StatusCode;
import com.premiere.pojo.City;
import com.premiere.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/city")
@CrossOrigin
public class CityController extends ExceptionController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }


    /**
     * 新增城市
     *
     * @param city 标签信息
     * @return 是否成功
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody City city) {
        cityService.save(city);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 返回城市列表
     *
     * @return Result
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "成功查询到数据", cityService.findAll());
    }

    /**
     * 根据ID查询城市
     *
     * @param cityId City 的 ID
     * @return Result
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{cityId}")
    public Result findCityById(@PathVariable String cityId) {
        return new Result(true, StatusCode.OK, "成功查询到数据", cityService.findCityById(cityId));
    }


    /**
     * 修改城市
     *
     * @param cityId City ID
     * @param city   City
     * @return Result
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{cityId}")
    public Result updateCityById(@PathVariable String cityId, @RequestBody City city) {
        city.setId(cityId);
        cityService.save(city);
        return new Result(true, StatusCode.OK, "成功响应");
    }

    /**
     * 删除城市
     *
     * @param cityId City ID
     * @return Result
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{cityId}")
    public Result deleteCityById(@PathVariable String cityId) {
        cityService.deleteCityById(cityId);
        return new Result(true, StatusCode.OK, "操作成功");
    }


    /**
     * 根据条件查询城市列表
     *
     * @param city 分页条件
     * @param page 页数
     * @param size 每页条数
     * @return Result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/search/{page}/{size}")
    public Result search(@RequestBody City city,
                         @PathVariable Integer page,
                         @PathVariable Integer size) {
        Page<City> list = cityService.search(city, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>((long) list.getPageable().getPageNumber(), list.getContent()));
    }

    /**
     * 根据条件查询城市列表
     *
     * @param city 分页条件
     * @return Result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/search")
    public Result searchByCity(@RequestBody City city) {
        return new Result(true, StatusCode.OK, "查询成功", cityService.searchByCity(city));
    }
}

