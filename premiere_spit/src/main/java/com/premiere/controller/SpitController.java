package com.premiere.controller;

import com.premiere.common.PageResult;
import com.premiere.common.Result;
import com.premiere.common.StatusCode;
import com.premiere.pojo.Spit;
import com.premiere.service.SpitService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("spit")
@CrossOrigin
public class SpitController extends ExceptionController {

    private final SpitService spitService;
    private final HttpServletRequest request;

    @Autowired
    public SpitController(SpitService spitService, HttpServletRequest request) {
        this.spitService = spitService;
        this.request = request;
    }

    /**
     * 查找全部
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }


    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(id));
    }


    /**
     * 添加
     *
     * @param spit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestBody Spit spit) {
        spit.setUserid(getUserId());
        spitService.insert(spit);
        return new Result(true, StatusCode.OK, "添加成功");
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
     * @param spit
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Result update(@RequestBody Spit spit, @PathVariable String id) {
        spit.set_id(id);
        spit.setUserid(getUserId());
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Result deleteById(@PathVariable String id) {
        Spit byId = spitService.findById(id);
        checkedUserRights(byId.getUserid());
        spitService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    private void checkedUserRights(String userid) {
        if (!StringUtils.equals(userid, getUserId()) && null != request.getAttribute("admin_claims")) {
            throw new RuntimeException("你无权删除其他人的问题");
        }
    }

    /**
     * 根据父级ID查询子集
     *
     * @param parentId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/comment/{parentId}/{page}/{size}")
    public Result findByParentIdList(@PathVariable String parentId,
                                     @PathVariable Integer page,
                                     @PathVariable Integer size) {
        Page<Spit> spitList = spitService.findByParentIdList(parentId, page, size);
        return new Result(true, StatusCode.OK, "查找成功", new PageResult<>(spitList.getTotalElements(), spitList.getContent()));
    }


    /**
     * 分页
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/search/{page}/{size}")
    public Result searchSpit(@PathVariable Integer page,
                             @PathVariable Integer size) {
        Page<Spit> spitList = spitService.searchSpit(page, size);
        return new Result(true, StatusCode.OK, "查找成功", new PageResult<>(spitList.getTotalElements(), spitList.getContent()));
    }


    /**
     * 点赞
     *
     * @param spitId
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/thumbup/{spitId}")
    public Result thumbupSpit(@PathVariable String spitId) {
        return new Result(true, StatusCode.OK, "点赞成功", spitService.thumbupSpit(spitId));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/search")
    public Result searchBySpit(@RequestBody Spit spit) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.searchBySpit(spit));
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/share/{spitId}")
    public Result shareSpit(@PathVariable String spitId) {
        spitService.shareSpit(spitId);
        return new Result(true, StatusCode.OK, "查询成功");
    }


}
