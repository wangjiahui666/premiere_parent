package com.premiere.controller;

import java.util.HashMap;
import java.util.Map;

import com.premiere.service.AdminService;
import com.premiere.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.premiere.pojo.Admin;

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
@RequestMapping("/admin")
public class AdminController extends ExceptionController {

    private final AdminService adminService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AdminController(AdminService adminService, JwtUtil jwtUtil) {
        this.adminService = adminService;
        this.jwtUtil = jwtUtil;
    }


    /**
     * 查询全部数据
     *
     * @return Result
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return Result
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findById(id));
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
        Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap 查询条件
     * @return Result
     */
    @PostMapping(value = "/search")
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param admin admin
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Admin admin) {
        adminService.add(admin);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param admin admin
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Admin admin, @PathVariable String id) {
        admin.setId(id);
        adminService.update(admin);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        adminService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }


    /**
     * 登录
     *
     * @param admin
     * @return
     */
    @PostMapping(value = "/login")
    public Result login(@RequestBody Admin admin) {
        if (null == admin || StringUtils.isEmpty(admin.getLoginname()) || StringUtils.isEmpty(admin.getPassword())) {
            return new Result(false, StatusCode.LOGINERROR, "error 用户名或密码失败");
        }
        Admin login = adminService.login(admin);
        if (null != login) {
            String token = jwtUtil.createJWT(login.getId(), login.getLoginname(), "admin");
            HashMap<String, Object> map = new HashMap<>();
            map.put("token",token);
            map.put("username",login.getLoginname());
            return new Result(true, StatusCode.OK, "登陆成功", map);
        } else {
            return new Result(false, StatusCode.LOGINERROR, "参数有误");
        }
    }
}
