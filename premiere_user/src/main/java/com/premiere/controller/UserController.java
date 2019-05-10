package com.premiere.controller;

import java.util.HashMap;
import java.util.Map;

import com.premiere.service.UserService;
import com.premiere.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.premiere.pojo.User;

import com.premiere.common.PageResult;
import com.premiere.common.Result;
import com.premiere.common.StatusCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController extends ExceptionController {

    private final UserService userService;
    private final HttpServletRequest request;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, HttpServletRequest request, JwtUtil jwtUtil) {
        this.userService = userService;
        this.request = request;
        this.jwtUtil = jwtUtil;
    }


    /**
     * 查询全部数据
     *
     * @return Result
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return Result
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
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
    public Result findSearch(@RequestBody Map searchMap,
                             @PathVariable int page,
                             @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap 查询条件
     * @return Result
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param user user
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody User user) {
        checkedUserRights("");
        userService.add(user);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param user user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user,
                         @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        checkedUserRights(id);
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    private String getUserId() {
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (null == claims) {
            throw new RuntimeException("无权访问");
        }
        return claims.getId();
    }

    private void checkedUserRights(String userid) {
        if (!StringUtils.equals(userid, getUserId()) && null != request.getAttribute("admin_claims")) {
            throw new RuntimeException("你无权删除其他人的问题");
        }
    }
    /**
     * 查询登陆用户信息
     *
     * @return Result
     */
    @RequestMapping(method = RequestMethod.GET, value = "/info")
    public Result userInfo() {
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user != null) {
            user.setPassword("");
            return new Result(true, StatusCode.OK, "操作成功", user);
        }
        return new Result(true, StatusCode.OK, "请先登录或登录已过时!", null);
    }


    /**
     * 登陆
     *
     * @param user
     * @return Result
     */
    @PostMapping(value = "/login")
    public Result login(@RequestBody User user) {
        if (null == user || StringUtils.isEmpty(user.getMobile()) || StringUtils.isEmpty(user.getPassword())) {
            return new Result(false, StatusCode.LOGINERROR, "参数有误");
        }
        User login = userService.login(user);
        if (null != login) {
            String token = jwtUtil.createJWT(login.getId(), login.getNickname(), "user");
            HashMap<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("telPhone", login.getMobile());
            return new Result(true, StatusCode.OK, "登陆成功", map);
        } else {
            return new Result(false, StatusCode.LOGINERROR, "参数有误");
        }

    }

    /**
     * 发送短信
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/sendsms/{mobile}")
    public Result sendsms(@PathVariable String mobile) {
        userService.sendsms(mobile);
        return new Result(true, StatusCode.OK, "发送成功!", false);
    }

    /**
     * 注册用户
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/register/{code}")
    public Result registerUser(@RequestBody User user, @PathVariable String code) {
        userService.registerUser(user, code);
        return new Result(true, StatusCode.OK, "注册成功");
    }


    /**
     * 修改当前登陆用户信息
     *
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/saveinfo")
    public Result saveinfo(@RequestBody User user) {
        userService.saveinfo(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    /**
     * 关注某用户
     *
     * @param userId
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/follow/{userId}")
    public Result updadeFollow(@PathVariable String userId) {
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user != null) {
            userService.updadeFollow(user.getId(), userId);
        }
        return new Result(true, StatusCode.OK, "修改成功");
    }


    /**
     * 删除某用户关注
     *
     * @param userId
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/follow/{userId}")
    public Result deleteFollow(@PathVariable String userId) {
        String id = this.getUserId();
        if (StringUtils.isNotEmpty(id)) {
            userService.deleteFollow(id, userId);
        }
        return new Result(true, StatusCode.OK, "修改成功");
    }


    /**
     * 查询我的粉丝
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/follow/myfans")
    public Result followMyFans() {
        return new Result(true, StatusCode.OK, "修改成功", userService.followMyFans(this.getUserId()));
    }


    /**
     * 查询我的关注
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/follow/myfollow")
    public Result followMyFollow() {
        return new Result(true, StatusCode.OK, "修改成功", userService.followMyFollow(this.getUserId()));
    }


    /**
     * 查询我的关注ID列表
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/follow/myfollowid")
    public Result followMyFollowId() {
        return new Result(true, StatusCode.OK, "修改成功", userService.followMyFollowId(this.getUserId()));
    }


    @PutMapping(value = "/incfollow/{userId}/{num}")
    public Result incFollowCount(@PathVariable String userId ,@PathVariable Integer num){
        userService.incFollowCount(userId,num);
        return new Result(true, StatusCode.OK, "添加关注成功");
    }

    @PutMapping(value = "/incFansCount/{userId}/{num}")
    public Result incFansCount(@PathVariable String userId ,@PathVariable Integer num){
        userService.incFansCount(userId,num);
        return new Result(true, StatusCode.OK, "添加粉丝成功");
    }
}
