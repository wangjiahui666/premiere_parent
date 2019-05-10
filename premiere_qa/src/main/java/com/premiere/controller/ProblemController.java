package com.premiere.controller;

import java.util.Map;

import com.premiere.client.LabelClient;
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

import com.premiere.pojo.Problem;
import com.premiere.service.ProblemService;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController extends ExceptionController {

    private final ProblemService problemService;
    private final HttpServletRequest request;
    private final LabelClient labelClient;

    @Autowired
    public ProblemController(ProblemService problemService, HttpServletRequest request, LabelClient labelClient) {
        this.problemService = problemService;
        this.request = request;
        this.labelClient = labelClient;
    }

    @RequestMapping(value = "/label/{labelId}", method = RequestMethod.GET)
    public Result findLabelById(@PathVariable String labelId) {
        return labelClient.findById(labelId);
    }

    /**
     * 查询全部数据
     *
     * @return Result
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return Result
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findById(id));
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
        Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
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
        return new Result(true, StatusCode.OK, "查询成功", problemService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param problem 类
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Problem problem) {
        problem.setUserid(getUserId());
        problemService.add(problem);
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
     * @param problem 类
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Problem problem, @PathVariable String id) {
        problem.setId(id);
        problem.setUserid(getUserId());
        problemService.update(problem);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id 类的ID
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        Problem byId = problemService.findById(id);
        checkedUserRights(byId.getUserid());
        problemService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    private void checkedUserRights(String userid) {
        if (!StringUtils.equals(userid, getUserId()) && null != request.getAttribute("admin_claims")) {
            throw new RuntimeException("你无权删除其他人的问题");
        }
    }

    /**
     * 分页+多条件查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/newlist/{label}/{page}/{size}", method = RequestMethod.GET)
    public Result newlist(@PathVariable String label,
                          @PathVariable Integer page,
                          @PathVariable Integer size) {
        Page<Problem> pageList = problemService.findProblemNewlistByLabel(label, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }


    @RequestMapping(value = "/waitlist/{label}/{page}/{size}", method = RequestMethod.GET)
    public Result waitlist(@PathVariable String label,
                           @PathVariable Integer page,
                           @PathVariable Integer size) {
        Page<Problem> pageList = problemService.findProblemWaitlistByLabel(label, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }


    @RequestMapping(value = "/hotlist/{label}/{page}/{size}", method = RequestMethod.GET)
    public Result hotlist(@PathVariable String label,
                          @PathVariable Integer page,
                          @PathVariable Integer size) {
        Page<Problem> pageList = problemService.findProblemHotlistByLabel(label, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    @RequestMapping(value = "/all/{label}/{page}/{size}", method = RequestMethod.POST)
    public Result all(@PathVariable String label,
                      @PathVariable Integer page,
                      @PathVariable Integer size) {
        Page<Problem> pageList = problemService.findProblemAllByLabel(label, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }
}
