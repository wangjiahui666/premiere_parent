package com.premiere.controller;

import java.util.HashMap;
import java.util.Map;

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

import com.premiere.pojo.Reply;
import com.premiere.service.ReplyService;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/reply")
public class ReplyController extends ExceptionController {

    private final ReplyService replyService;
    private final HttpServletRequest request;

    @Autowired
    public ReplyController(ReplyService replyService, HttpServletRequest request) {
        this.replyService = replyService;
        this.request = request;
    }


    /**
     * 查询全部数据
     *
     * @return Result
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", replyService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return Result
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", replyService.findById(id));
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
    public Result findSearch(@RequestBody Map searchMap, @PathVariable Integer page, @PathVariable Integer size) {
        Page<Reply> pageList = replyService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap 类
     * @return Result
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", replyService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param reply 类的id
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Reply reply) {
        reply.setUserid(getUserId());
        replyService.add(reply);
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
     * @param reply 类的id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Reply reply, @PathVariable String id) {
        reply.setId(id);
        reply.setUserid(getUserId());
        replyService.update(reply);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id 类的id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        Reply byId = replyService.findById(id);
        checkedUserRights(byId.getUserid());
        replyService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    private void checkedUserRights(String userid) {
        if (!StringUtils.equals(userid, getUserId()) && null != request.getAttribute("admin_claims")) {
            throw new RuntimeException("你无权删除其他人的问题");
        }
    }


    /**
     * 根据问题ID查询回答列表
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/problem/{id}", method = RequestMethod.GET)
    public Result findReplyByProblemId(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查找成功", replyService.findReplyByProblemId(id));
    }


    /**
     * 回答问题
     *
     * @param reply
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody Reply reply) {
        replyService.add(reply);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 我的回答列表
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list/{page}/{size}", method = RequestMethod.GET)
    public Result findSearch(@PathVariable Integer page, @PathVariable Integer size) {
        HashMap<String, String> map = new HashMap<>();
        request.getSession().getAttribute("loginUser");
        // 添加用户的 ID 以后解决
        // map.put("userid","userid");
        Page<Reply> pageList = replyService.findSearch(map, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }
}
