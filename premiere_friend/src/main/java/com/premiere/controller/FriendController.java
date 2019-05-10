package com.premiere.controller;

import com.premiere.common.Result;
import com.premiere.common.StatusCode;
import com.premiere.service.FriendService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
@CrossOrigin
public class FriendController extends ExceptionController {

    private final FriendService friendService;
    private final HttpServletRequest request;

    @Autowired
    private FriendController(FriendService friendService, HttpServletRequest request) {
        this.friendService = friendService;
        this.request = request;
    }


    /**
     * 添加好友或非好友
     *
     * @param friendid
     * @param type
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid,
                            @PathVariable String type) {
        if (StringUtils.isEmpty(friendid)) {
            throw new RuntimeException("");
        }
        if (StringUtils.equals("1", type)) {
            if (friendService.addFriend(getUserId(), friendid) == 0) {
                return new Result(false, StatusCode.REPERROR, "您已经进行过关注请勿重复提交");
            }
            return new Result(true, StatusCode.OK, "关注成功");
        } else {
            friendService.deleteFriend(getUserId(), friendid);
            return new Result(true, StatusCode.OK, "取消关注成功");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{friendid}")
    public Result deleteFriend(@PathVariable String friendid) {
        friendService.addNoFriend(getUserId(), friendid);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    private String getUserId() {
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (null == claims) {
            throw new RuntimeException("你无权访问");
        }
        return claims.getId();
    }

}
